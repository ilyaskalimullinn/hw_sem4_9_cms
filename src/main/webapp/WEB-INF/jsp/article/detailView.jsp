<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><c:out value="${article.title}" /></title>
</head>
<body>
    <a href="${s:mvcUrl("AC#list").build()}">All articles</a> |
    <security:authorize access="hasRole('ADMIN')">
        <a href="${s:mvcUrl("AC#edit").arg(0, article.slug).build()}">Edit</a>
    </security:authorize>
    <hr />
    <article class="article">
        ${article.content}
    </article>
</body>
</html>
