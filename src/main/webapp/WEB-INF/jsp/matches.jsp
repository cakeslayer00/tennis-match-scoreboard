<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Matches Form</title>
    <style>
        .pagination-form {
            display: inline;
        }

        .pagination-form button {
            padding: 5px 10px;
            margin: 0 5px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .pagination-form button:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }
    </style>
</head>
<body>

<h1>Matches Form</h1>

<form class="pagination-form" action="${pageContext.request.contextPath}/matches" method="get">
    <label for="filter">Filter by name:</label>
    <input type="text" id="filter" name="filter_by_player_name" value="${param.filter_by_player_name}">
    <input type="hidden" name="page" value="${empty param.page ? 1 : param.page}">
    <button type="submit">Apply Filter</button>
</form>

<div>
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


    <form class="pagination-form" action="${pageContext.request.contextPath}/matches" method="get">
        <input type="hidden" name="page" value="${page - 1}">
        <input type="hidden" name="filter_by_player_name" value="${filter}">
        <button type="submit" ${page <= 1 ? 'disabled' : ''}>Previous</button>
    </form>

    <span>Page ${page}</span>

    <form class="pagination-form" action="${pageContext.request.contextPath}/matches" method="get">
        <input type="hidden" name="page" value="${page + 1}">
        <input type="hidden" name="filter_by_player_name" value="${filter}">
        <button type="submit">Next</button>
    </form>
</div>
</body>
</html>