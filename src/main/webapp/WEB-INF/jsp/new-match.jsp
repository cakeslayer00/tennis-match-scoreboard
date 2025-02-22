<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css?v=1">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
    <title>Player Names Form</title>
</head>
<body>
<div class="common inter-font new-match">
    <h1>New Match</h1>
    <form action="${pageContext.request.contextPath}/new-match" method="POST">
        <label>
            Player one: <input type="text" id="playerOne" name="firstPlayer" placeholder="Blue Heavy" required>
        </label>
        <label>
            Player two: <input type="text" id="playerTwo" name="secondPlayer" placeholder="Red Heavy" required>
        </label>
        <c:if test="${requestScope.errorMessage != null}">
            <p style="color: #ff0000;">${requestScope.errorMessage}</p>
        </c:if>
        <button type="submit" >Submit</button>
    </form>
</div>
</body>
</html>