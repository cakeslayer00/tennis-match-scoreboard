<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Match Scoreboard</title>
</head>
<body>
<table>
    <tr>
        <th>player name</th>
        <th>set</th>
        <th>game</th>
        <th>point</th>
        <th>action</th>
    </tr>
    <tr>
        <td>${requestScope.ongoingMatch.firstPlayer.name}</td>
        <td>${requestScope.ongoingMatch.firstPlayerScore.sets}/></td>
        <td>${requestScope.ongoingMatch.firstPlayerScore.games}</td>
        <td>
            <c:choose>
                <c:when test="${requestScope.ongoingMatch.tieBreak}">
                    <c:out value="${requestScope.ongoingMatch.firstPlayerScore.tieBreakCounter}"/>
                </c:when>
                <c:otherwise>
                    <c:out value="${requestScope.ongoingMatch.firstPlayerScore.point}"/>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/match-score" method="post">
                <input type="hidden" name="uuid" value="${requestScope.uuid}">
                <input type="hidden" name="winnerId" value="${requestScope.ongoingMatch.firstPlayer.id}">
                <button type="submit">add</button><br>
            </form>
        </td>
    </tr>
    <tr>
        <td>${requestScope.ongoingMatch.secondPlayer.name}</td>
        <td>${requestScope.ongoingMatch.secondPlayerScore.sets}/></td>
        <td>${requestScope.ongoingMatch.secondPlayerScore.games}</td>
        <td>
            <c:choose>
                <c:when test="${requestScope.ongoingMatch.tieBreak}">
                    <c:out value="${requestScope.ongoingMatch.secondPlayerScore.tieBreakCounter}"/>
                </c:when>
                <c:otherwise>
                    <c:out value="${requestScope.ongoingMatch.secondPlayerScore.point}"/>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/match-score" method="post">
                <input type="hidden" name="uuid" value="${requestScope.uuid}">
                <input type="hidden" name="winnerId" value="${requestScope.ongoingMatch.secondPlayer.id}">
                <button type="submit">add</button><br>
            </form>
        </td>
    </tr>
</table>
</body>
</html>