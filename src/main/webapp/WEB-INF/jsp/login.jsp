<%--
  Created by IntelliJ IDEA.
  User: ilyas
  Date: 17.04.2023
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>

    <c:if test="${not empty error}" >
        <div class="error" style="color: red; ">
            <c:out value="${error}" />
        </div>
    </c:if>

    <form:form method="post" modelAttribute="loginForm">
        <form:label path="username">Email</form:label>
        <form:input type="email" path="username"/>
        <form:errors path="username" /><br>

        <form:label path="password">Password</form:label>
        <form:password path="password"/>
        <form:errors path="password" /><br>

        <input type="submit" value="Submit" />
    </form:form>
</body>
</html>
