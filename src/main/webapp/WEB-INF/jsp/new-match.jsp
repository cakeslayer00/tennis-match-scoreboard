<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
    <title>Player Names Form</title>
</head>
<body>
<h1>New Match</h1>
<div class="common inter-font">
    <form action="${pageContext.request.contextPath}/new-match" method="POST">
        <label for="playerOne">Player one:</label>
        <input type="text" id="playerOne" name="firstPlayer" placeholder="Blue Heavy" required>
        <br>
        <label for="playerTwo">Player two:</label>
        <input type="text" id="playerTwo" name="secondPlayer" placeholder="Red Heavy" required>
        <br>
        <button type="submit" >Submit</button>
    </form>
</div>

<c:if test="${requestScope.errorMessage != null}">
    <p style="color: #ff0000;">${requestScope.errorMessage}</p>
</c:if>
</body>
</html>