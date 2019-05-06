<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/25
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/lib/jquery.min.js"></script>
</head>
<body>

    <button>握手</button>
    <form action="${pageContext.request.contextPath}/dologin" method="post">
        <input value="" name="username" />
        <input value="" name="password" />
        <input type="submit" value="登录">
    </form>
</body>
<script type="text/javascript">

    function handshake() {

        $.ajax({
            url: '${pageContext.request.contextPath}/static/json/request/handshake.json',
            async: false,
            success: function (data) {
                debugger
                $.ajax({
                    type: "POST",
                    url: "http://test.icitic.net/app/handshake",
                    data: data,
                    contentType:"application/json;charset=utf-8",
                    dataType: "json",
                    success: function(data){
                        debugger
                    }
                });
            }
        });


    }

</script>
</html>
