<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Matches Form</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
</head>
<body>

<h1>Matches</h1>

<div class="common inter-font">
    <div class="filter-area">
        <form action="${pageContext.request.contextPath}/matches" method="get">
            <label for="filter">Filter by name:</label>
            <input type="text" id="filter" name="filter_by_player_name" placeholder="bananas" value="${param.filter_by_player_name}">
            <input type="hidden" name="page" value="1">
            <button type="submit">Apply Filter</button>
        </form>
    </div>

    <div class="table-area">
        <c:set var="page" value="${empty param.page ? 1 : param.page}"/>
        <c:set var="filter" value="${param.filter_by_player_name}"/>

        <table>
            <tr>
                <th>PLAYER I</th>
                <th>PLAYER II</th>
                <th>WINNER</th>
            </tr>
            <c:forEach var="match" items="${requestScope.matches}">
                <tr>
                    <td>${match.firstPlayer.name}</td>
                    <td>${match.secondPlayer.name}</td>
                    <td>${match.winner.name}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="pagination-area">
        <form action="${pageContext.request.contextPath}/matches" method="get">
            <input type="hidden" name="page" value="${page - 1}">
            <input type="hidden" name="filter_by_player_name" value="${filter}">
            <button type="submit" ${page <= 1 ? 'disabled' : ''}>Previous</button>
        </form>

        <span>Page ${page} of ${requestScope.pageCount}</span>

        <form action="${pageContext.request.contextPath}/matches" method="get">
            <input type="hidden" name="page" value="${page + 1}">
            <input type="hidden" name="filter_by_player_name" value="${filter}">
            <button type="submit" ${page >= requestScope.pageCount ? 'disabled' : ''}>Next</button>
        </form>
    </div>
</div>
</body>
</html>