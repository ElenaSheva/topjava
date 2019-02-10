<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<style>
    .green {
        background-color: lightgreen
    }
    .red {
        background-color: lightpink
    }
</style>
<h3><a href="index.html">Home</a></h3>
<div>
    <h2>Meals</h2>
    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Excess</th>
        </tr>

        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess == true ? 'red':'green'}">
                <td>${fn:formatDateTime(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>${meal.excess == true ? 'Yes' : 'No'}</td>
            </tr>
        </c:forEach>
    </table>
</div>


</body>
</html>