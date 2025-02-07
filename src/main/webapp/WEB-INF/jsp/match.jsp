<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Match Scoreboard</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/match-score" method="post">
    <input type="hidden" name="uuid" value="<%= request.getAttribute("uuid") %>">
    <input type="hidden" name="winnerId" value="1">
    <button type="submit">add</button><br>
</form>

<!-- Form for Player 2 -->
<form action="${pageContext.request.contextPath}/match-score" method="post">
    <input type="hidden" name="uuid" value="<%= request.getAttribute("uuid") %>">
    <input type="hidden" name="winnerId" value="2">
    <button type="submit">add</button>
</form>
</body>
</html>