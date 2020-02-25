<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <%--制作jqgrid表--%>
    <script type="text/javascript">
        $(function () {
            $("#logTable").jqGrid({
                url:"${path}/log/queryByPage",
                styleUI:"Bootstrap",
                datatype:"json",
                colNames:["id","管理员名","时间","行为","状态"],
                colModel:[
                    {name:"id",align:"center"},
                    {name:"adminName",align:"center"},
                    {name:"time",align:"center"},
                    {name:"action",align:"center"},
                    {name:"status",align:"center"}
                    ],
                pager:"#logPager",
                rowNum:5,
                rowList:[5,10,15],
                viewrecords:true,
                autowidth:true,
                editurl:"${path}/user/edit"
            }).jqGrid("navGrid","#logPager",{edit : false,add : false,del : false})
        });
    </script>

</head>
<body>
    <%--面板--%>
    <div class="panel panel-primary">
        <div class="panel-heading">日志管理</div>
        <div class="panel-body">
            <%--选项卡--%>
            <div class="nav nav-tabs">
                <li class="active"><a href="">日志信息</a></li>
            </div>

            <table id="logTable"/>
            <div id="logPager"/>
        </div>
    </div>
</body>
</html>