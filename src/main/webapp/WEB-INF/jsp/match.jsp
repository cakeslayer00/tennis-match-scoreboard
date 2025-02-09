
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Match Scoreboard</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/match-score" method="post">

    <table>
        <tr>
            <th>player name</th>
            <th>set</th>
            <th>game</th>
            <th>point</th>
        </tr>
        <tr>
            <td></td>
        </tr>
    </table>
</form>

<%--<!-- Form for Player 2 -->--%>
<%--<form action="${pageContext.request.contextPath}/match-score" method="post">--%>
<%--    <label>--%>
<%--        <%=request.getAttribute("second_player_name")%> POINTS <%=request.getAttribute("second_player_points")%>--%>
<%--        <input type="hidden" name="uuid" value="<%= request.getAttribute("uuid") %>">--%>
<%--        <input type="hidden" name="winnerId" value="2">--%>
<%--        <button type="submit">add</button><br>--%>
<%--        <%=request.getAttribute("second_player_name")%> GAMES <%=request.getAttribute("second_player_games")%><br>--%>
<%--        <%=request.getAttribute("second_player_name")%> SETS <%=request.getAttribute("second_player_sets")%><br>--%>
<%--    </label>--%>
<%--</form>--%>
</body>
</html>