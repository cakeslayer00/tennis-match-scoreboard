<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Player Names Form</title>
</head>
<body>
<h1>Enter player names</h1>

<form action="${pageContext.request.contextPath}/new-match" method="POST">
    <label for="playerOne">Player one:</label>
    <input type="text" id="playerOne" name="firstPlayer" placeholder="Blue Heavy" required>
    <br>
    <label for="playerTwo">Player two:</label>
    <input type="text" id="playerTwo" name="secondPlayer" placeholder="Red Heavy" required>
    <br>
    <input type="submit" value="Submit">
</form>

<c:if test="${requestScope.error != null}">
    <p style="color: #ff0000;">${requestScope.error}</p>
</c:if>
</body>
</html>