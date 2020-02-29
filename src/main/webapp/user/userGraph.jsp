<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <%--第一次请求走该信息--%>
   <script type="text/javascript">
        $(function () {
            var myChart = echarts.init(document.getElementById('graph'));
            $.ajax({
                url:"${path}/user/makeGraph",
                type:"get",
                dataType:"json",
                success:function (map) {
                    //成功后
                    // 基于准备好的dom，初始化echarts实例
                    //var myChart = echarts.init(document.getElementById('main'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {//标题
                            text: '用户注册统计'
                        },
                        tooltip: {},//鼠标提示
                        legend: {//选项卡
                            data:['男用户','女用户']
                        },
                        xAxis: {//横坐标
                            data: map.month
                        },
                        yAxis: {},//纵坐标
                        series: [{//数据系列
                            name: '男用户',//选项卡名字
                            type: 'bar',//显现图形
                            data: map.male//数据
                        },{//数据系列
                            name: '女用户',//选项卡名字
                            type: 'bar',//显现图形
                            data: map.female//数据
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
        });
    </script>

    <%--以后通过goeasy在用户注册时传递--%>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io',//应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
            appkey: "BC-081bbf854e0e4dc4bcbf8ad1fa773068",//替换为您的应用appkey
            forceTLS:false, //如果需要使用HTTPS/WSS，请设置为true，默认为false
            onConnected: function() {
                console.log('连接成功！')
            },
            onDisconnected: function() {
                console.log('连接断开！')
            },
            onConnectFailed: function(error) {
                console.log('连接失败或错误！')
            }
        });

        $(function () {
            var myChart = echarts.init(document.getElementById('graph'));

            //接收信息
            goEasy.subscribe({
                channel: "我的channel",//替换为您自己的channel
                onMessage: function (message) {
                    //alert("Channel:" + message.channel + " content:" + message.content);
                     var map = JSON.parse(message.content);
                     // 基于准备好的dom，初始化echarts实例
                     //var myChart = echarts.init(document.getElementById('graph'));

                     // 指定图表的配置项和数据
                     var option = {
                         title: {//标题
                             text: '用户注册统计'
                         },
                         tooltip: {},//鼠标提示
                         legend: {//选项卡
                             data:['男用户','女用户']
                         },
                         xAxis: {//横坐标
                             data: map.month
                         },
                         yAxis: {},//纵坐标
                         series: [{//数据系列
                             name: '男用户',//选项卡名字
                             type: 'bar',//显现图形
                             data: map.male//数据
                         },{//数据系列
                             name: '女用户',//选项卡名字
                             type: 'bar',//显现图形
                             data: map.female//数据
                         }]
                     };

                     // 使用刚指定的配置项和数据显示图表。
                     myChart.setOption(option);
                }
            });
        });
    </script>
</head>
<body>
这是我的图形页面
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="graph" style="width: 1200px;height:400px;"></div>
</body>
