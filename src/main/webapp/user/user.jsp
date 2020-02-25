<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <%--制作jqgrid表--%>
    <script type="text/javascript">
        $(function () {
            $("#uTable").jqGrid({
                url:"${path}/user/queryAll",
                styleUI:"Bootstrap",
                datatype:"json",
                colNames:["id","username","sex","phone","picImg","sign","wechat","createDate","score","status"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"username",align:"center",editable:true},
                    {name:"sex",align:"center",editable:true,edittype:"select",editoptions:{value:{"m":"男","f":"女"}}},
                    {name:"phone",align:"center",editable:true},
                    {name:"picImg",align:"center",editable:true,edittype:"file",
                        formatter:function(cellvalue, options, rowObject){
                            return "<img src='${path}/upload/img/"+cellvalue+"' class='height:100px' />";
                        }},
                    {name:"sign",align:"center",editable:true},
                    {name:"wechat",align:"center",editable:true},
                    {name:"createDate",align:"center"},
                    {name:"score",align:"center"},
                    {name:"status",align:"center",
                        formatter:function(cellvalue, options, rowObject){
                            if(cellvalue=="1"){
                                return "<button class='btn btn-success' onclick='fun1(\""+rowObject.id+"\",\""+cellvalue+"\")'>激活</button>";
                            }else{
                                return "<button class='btn btn-danger' onclick='fun1(\""+rowObject.id+"\",\""+cellvalue+"\")'>冻结</button>";
                            }
                        }}
                    ],
                pager:"#uPager",
                rowNum:5,
                rowList:[5,10,15],
                viewrecords:true,
                autowidth:true,
                editurl:"${path}/user/edit"
            }).jqGrid("navGrid","#uPager",{edit : true,add : true,del : true,edittext:"修改",addtext:"添加",deltext:"删除"},
                {},//执行修改后的操作
                {
                    closeAfterAdd:true,
                    afterSubmit:function (data) {
                        $.ajaxFileUpload({
                            fileElementId:"picImg",
                            url:"${path}/user/uploadFile",
                            type:"post",
                            dataType:"text",
                            data:{id:data.responseText},
                            success:function (data) {
                                //刷新页面
                                $("#uTable").trigger("reloadGrid");
                            }
                        });
                        return "success";
                    }
                },//执行添加后的操作
                {}//执行删除后的操作
                );

            //制作短信验证
            $("#sendPhoneCode").click(function () {
                var value = $("#phone").val();
                $.ajax({
                    url:"${path}/user/sendMessage",
                    type:"post",
                    data:{"phone":value},
                    dataType:"text",
                    success:function (data) {
                        //成功后清空它的值
                        $("#phone").val("");
                    }
                })
            });

            //导出用户数据
            $("#export").click(function () {
                $.ajax({
                    url:"${path}/user/exportMessage",
                    type:"get",
                    dataType:"text",
                    success:function (data) {
                        //成功后
                    }
                });
            });
        });

        function fun1(id,status) {
            if(id!=null){
                $.ajax({
                    url:"${path}/user/editStatus",
                    type:"post",
                    data:{"id":id,"status":status},
                    dataType:"text",
                    success:function (tag) {
                        //刷新页面
                        $("#uTable").trigger("reloadGrid");
                    }
                });
            }
        }
    </script>

</head>
<body>
    <%--面板--%>
    <div class="panel panel-primary">
        <div class="panel-heading">用户管理</div>
        <div class="panel-body">
            <%--发送验证码--%>
            <div class="input-group" style="float:right;width:30%">
                <input id="phone" type="text" class="form-control" placeholder="请输入手机号" />
                <span class="input-group-addon"><a href="javascript:void(0)" id="sendPhoneCode">发送验证码</a></span>
            </div>
            <%--选项卡--%>
            <div class="nav nav-tabs">
                <li class="active"><a href="">用户信息</a></li>
            </div>
                <%--导出数据按钮--%>
            <div><button id="export" class="btn btn-success">导出用户数据</button></div>
            <table id="uTable"/>
            <div id="uPager"/>
        </div>
    </div>
</body>
</html>