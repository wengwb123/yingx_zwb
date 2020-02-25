<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
    <head>
        <%--引入jquery--%>
        <script src="${path}/bootstrap/js/jquery.min.js"></script>
        <%--引入echarts--%>
        <script type="text/javascript" src="${path}/js/echarts.min.js"></script>

        <script type="text/javascript">
            $(function () {
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                // 指定图表的配置项和数据
                var option = {
                    title: {//标题
                        text: 'ECharts 入门示例'
                    },
                    tooltip: {},//鼠标提示
                    legend: {//选项卡
                        data:['销量']
                    },
                    xAxis: {//横坐标
                        data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                    },
                    yAxis: {},//纵坐标
                    series: [{//数据系列
                        name: '销量',//选项卡名字
                        type: 'bar',//显现图形
                        data: [5, 20, 36, 10, 10, 20]//数据
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            });
        </script>
    </head>
    <body>
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
        <div id="main" style="width: 600px;height:400px;"></div>
    </body>
</html>