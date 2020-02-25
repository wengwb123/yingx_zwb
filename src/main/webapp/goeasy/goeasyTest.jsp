<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
    <head>
        <%--引入goeasy的js文件--%>
        <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
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
                //接收信息
                goEasy.subscribe({
                    channel: "我的channel",//替换为您自己的channel
                    onMessage: function (message) {

                        //alert("Channel:" + message.channel + " content:" + message.content);
                    }
                });
            </script>
    </head>
    <body>

    </body>
</html>