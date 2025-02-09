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
        <td><c:out value="${requestScope.matchScoreViewDto.firstPlayerName}"/></td>
        <td><c:out value="${requestScope.matchScoreViewDto.firstPlayerScore.sets}"/></td>
        <td><c:out value="${requestScope.matchScoreViewDto.firstPlayerScore.games}"/></td>
        <td>
            <c:choose>
                <c:when test="${requestScope.matchScoreViewDto.tieBreak}">
                    <c:out value="${requestScope.matchScoreViewDto.firstPlayerScore.tieBreakCounter}"/>
                </c:when>
                <c:otherwise>
                    <c:out value="${requestScope.matchScoreViewDto.firstPlayerScore.point}"/>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/match-score" method="post">
                <input type="hidden" name="uuid" value="${requestScope.uuid}">
                <input type="hidden" name="winnerId" value="1">
                <button type="submit">add</button><br>
            </form>
        </td>
    </tr>
    <tr>
        <td><c:out value="${requestScope.matchScoreViewDto.secondPlayerName}"/></td>
        <td><c:out value="${requestScope.matchScoreViewDto.secondPlayerScore.sets}"/></td>
        <td><c:out value="${requestScope.matchScoreViewDto.secondPlayerScore.games}"/></td>
        <td>
            <c:choose>
                <c:when test="${requestScope.matchScoreViewDto.tieBreak}">
                    <c:out value="${requestScope.matchScoreViewDto.secondPlayerScore.tieBreakCounter}"/>
                </c:when>
                <c:otherwise>
                    <c:out value="${requestScope.matchScoreViewDto.secondPlayerScore.point}"/>
                </c:otherwise>
            </c:choose>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/match-score" method="post">
                <input type="hidden" name="uuid" value="${requestScope.uuid}">
                <input type="hidden" name="winnerId" value="2">
                <button type="submit">add</button><br>
            </form>
        </td>
    </tr>
</table>
</body>
</html>