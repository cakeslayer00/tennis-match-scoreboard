<%--
  Created by IntelliJ IDEA.
  User: vladsv
  Date: 2/10/2025
  Time: 12:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Match Results</title>
</head>
<body>
<label>
    Winner is ${requestScope.winnerName}<br>
    <form action="${pageContext.request.contextPath}/matches" method="get">
        <input type="hidden" name="page" value="1">
        <input type="hidden" name="filter_by_player_name" value="">
        <button type="submit">Matches</button>
        <br>
    </form>
</label>

</body>
</html>
