<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Match Scoreboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
          rel="stylesheet">
</head>
<body>
<div class="common inter-font">
    <h1>Scoreboard:</h1>
    <table>
        <tr>
            <th>PLAYER</th>
            <th>SET</th>
            <th>GAME</th>
            <th>POINT</th>
            <th>ACTION</th>
        </tr>
        <tr>
            <td>${requestScope.ongoingMatch.firstPlayer.name}</td>
            <td>${requestScope.ongoingMatch.firstPlayerScore.sets}</td>
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
                    <button type="submit">add</button>
                </form>
            </td>
        </tr>
        <tr>
            <td>${requestScope.ongoingMatch.secondPlayer.name}</td>
            <td>${requestScope.ongoingMatch.secondPlayerScore.sets}</td>
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
                    <button type="submit">add</button>
                </form>
            </td>
        </tr>
    </table>
</div>
</body>
</html>