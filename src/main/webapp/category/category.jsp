<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <%--制作jqgrid表--%>
    <script type="text/javascript">
        $(function(){
            $("#cTable").jqGrid(
                {
                    url : "${path}/category/queryByPageAndLevels",
                    datatype : "json",
                    height : "auto",
                    autowidth:true,
                    colNames : ['Id', '名称', '级别'],
                    colModel : [
                        {name : 'id',editable:true,edithidden:true},
                        {name : 'name',editable:true},
                        {name : 'levels',editable:true}
                    ],
                    rowNum : 5,
                    rowList : [5, 10, 20],
                    pager : '#cPager',
                    viewrecords : true,
                    styleUI:"Bootstrap",
                    subGrid : true,
                    subGridRowExpanded : function(subgridId, rowId) {
                        var subgridTableId= subgridId + "Table";
                        var pagerId= subgridId+"Page";

                        //在父表格创建出的div中创建子表格的table,工具栏
                        $("#"+subgridId).html("<table id='"+subgridTableId+"' /><div id='"+pagerId+"' />");

                        //配置子表格的属性
                        $("#" + subgridTableId).jqGrid({
                            //url : "/category/queryByTwoPage?parentId" + rowId, //rowId 以及类别id 根据以及类别id查询所对应的二级类别
                            url : "${path}/category/queryByPageAndParentId?id="+rowId,
                            datatype : "json",
                            rowNum : 5,
                            rowList : [ 5, 10, 20, 30 ],
                            pager : "#"+pagerId,
                            styleUI:"Bootstrap",
                            height : "auto",
                            viewrecords:true,
                            autowidth:true,
                            colNames : [ 'Id', '名称', '级别',"父类别id"],
                            colModel : [
                                {name : 'id'},
                                {name : 'name'},
                                {name : 'levels'},
                                {name : 'parentId'},
                            ],
                        });

                        //子表格的工具栏
                        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId, {edit : false,add : false,del : false});
                    }
                });
            $("#cTable").jqGrid('navGrid', '#cPager', {add : true, edit : true, del : true,edittext:"修改",addtext:"添加",deltext:"删除"});
        });
    </script>

</head>
<body>
<%--面板--%>
<div class="panel panel-primary">
    <div class="panel-heading">分类管理</div>
    <div class="panel-body">
        <%--选项卡--%>
        <div class="nav nav-tabs">
            <li class="active"><a href="">类别信息</a></li>
        </div>

        <table id="cTable"/>
        <div id="cPager"/>
    </div>
</div>
</body>
</html>