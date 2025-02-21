<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home page!</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
</head>
<body>
<div class="common inter-font">
    <h1>${requestScope.winnerName == null ? 'Welcome to Tennis Scoreboard' : 'Winner is '.concat(requestScope.winnerName)}</h1>

    <form action="${pageContext.request.contextPath}/new-match" method="get">
        <button type="submit">Start match</button>
    </form>

    <form action="${pageContext.request.contextPath}/matches" method="get">
        <input type="hidden" name="page" value="1">
        <input type="hidden" name="filter_by_player_name" value="">
        <button type="submit">Matches</button>
    </form>
</div>
</body>
</html>
