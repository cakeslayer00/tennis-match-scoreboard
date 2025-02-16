<%--
  Created by IntelliJ IDEA.
  User: vladsv
  Date: 2/10/2025
  Time: 12:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Match Results</title>
</head>
<body>
<table>
    <tr>
        <th>player name</th>
        <th>set</th>
        <th>game</th>
        <th>point</th>
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
    </tr>
</table>
</body>
</html>
