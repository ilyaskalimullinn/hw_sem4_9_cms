<%--
  Created by IntelliJ IDEA.
  User: ilyas
  Date: 13.04.2023
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>


<h1>Register</h1>

<c:if test="${not empty message}">
    <h3 style="color: red;">
        <c:out value="${message}" />
    </h3>
</c:if>

<form:form method="post" modelAttribute="user">
    <form:label path="fullName">Name</form:label>
    <form:input path="fullName"/>
    <form:errors path="fullName" /><br>

    <form:label path="username">Email</form:label>
    <form:input type="email" path="username"/>
    <form:errors path="username" /><br>

    <form:label path="password">Password</form:label>
    <form:password path="password"/>
    <form:errors path="password" /><br>

    <form:label path="passwordRepeat">Password again</form:label>
    <form:password path="passwordRepeat"/>
    <form:errors path="passwordRepeat" /><br>

    <input type="submit" value="Submit" />
</form:form>
</body>
</html>
