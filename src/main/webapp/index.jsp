<%--
  Created by IntelliJ IDEA.
  User: vladsv
  Date: 2/3/2025
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tennis Scoreboard Service</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/new-match" method="post">
    <label>Player 1: <input type="text" name="firstPlayer"></label><br>
    <label>Player 2: <input type="text" name="secondPlayer"></label><br>
    <input type="submit">
</form>
</body>
</html>
