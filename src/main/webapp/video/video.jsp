<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <%--制作jqgrid表--%>
    <script type="text/javascript">
        $(function () {
            $("#videoTable").jqGrid({
                url:"${path}/video/queryByPage",
                styleUI:"Bootstrap",
                datatype:"json",
                colNames:["id","标题","简介","视频封面","发布时间","操作"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",align:"center",editable:true},
                    {name:"brief",align:"center",editable:true},
                    {name:"path",align:"center",editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                            return "<video src='"+cellvalue+"' style='height:200px;width:400px' controls/>";
                        }},
                    {name:"uploadTime",align:"center"},
                    {name: "control", align: "center",
                        formatter: function (cellvalue, options, rowObject) {
                            return "<button class='btn btn-danger' onclick='fun(\""+rowObject.id+"\")'>删除</button>";
                        }
                    }
                ],
                pager:"#videoPager",
                rowNum:5,
                rowList:[5,10,15],
                viewrecords:true,
                autowidth:true,
                editurl:"${path}/video/edit"
            }).jqGrid("navGrid","#videoPager",{edit : true,add : true,del : true},
                {},{
                    closeAfterAdd:true,
                    afterSubmit:function (data) {
                        $.ajaxFileUpload({
                            fileElementId:"path",
                            url:"${path}/video/uploadFile",
                            type:"post",
                            dataType:"text",
                            data:{id:data.responseText},
                            success:function (data) {
                                //刷新页面
                                $("#videoTable").trigger("reloadGrid");
                            }
                        });
                        return "success";
                    }
                },
            {}
            )
        });
        function fun(id) {
           $.ajax({
                url:"${path}/video/edit",
                type:"post",
                data:{"id":id,"oper":"del"},
                dataType:"json",
                success:function (data) {
                    //刷新页面
                    $("#videoTable").trigger("reloadGrid");
                }
            });
        }
    </script>

</head>
<body>
    <%--面板--%>
    <div class="panel panel-primary">
        <div class="panel-heading">视频管理</div>
        <div class="panel-body">
            <%--选项卡--%>
            <div class="nav nav-tabs">
                <li class="active"><a href="">视频信息</a></li>
            </div>

            <table id="videoTable"/>
            <div id="videoPager"/>
        </div>
    </div>
</body>
</html>