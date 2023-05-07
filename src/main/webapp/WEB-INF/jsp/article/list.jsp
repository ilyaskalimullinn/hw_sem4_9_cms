<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: ilyas
  Date: 20.04.2023
  Time: 09:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Articles</title>
</head>
<body>
    <security:authorize access="isAnonymous()">
        <a href="${s:mvcUrl("UC#login").build()}">Log in</a> |
        <a href="${s:mvcUrl("UC#register").build()}">Register</a>
    </security:authorize>
    <security:authorize access="isAuthenticated()">
        <p>Logged as <security:authentication property="principal.username" /></p>
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        <p>
            <a href="${s:mvcUrl("AC#create").build()}">Create new</a>
        </p>
    </security:authorize>

    <h1>Articles</h1>
    <ul class="articles">
        <c:forEach var="article" items="${articles}">
            <li>
                <a href="${s:mvcUrl("AC#detailView").arg(0, article.slug).build()}">
                    <c:out value="${article.title}" />
                </a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
