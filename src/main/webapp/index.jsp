<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Player Names Form</title>
</head>
<body>
<h1>Enter Player Names</h1>
<form action="${pageContext.request.contextPath}/new-match" method="POST">
    <label for="playerOne">Player One:</label>
    <input type="text" id="playerOne" name="firstPlayer" placeholder="Amigo" required>
    <br>
    <label for="playerTwo">Player Two:</label>
    <input type="text" id="playerTwo" name="secondPlayer" placeholder="Pedro" required>
    <br>
    <input type="submit" value="Submit">
</form>

<% if (request.getAttribute("error") != null) { %>
<p style="color: #ff0000;"><%= request.getAttribute("error") %></p>
<% } %>
</body>
</html>