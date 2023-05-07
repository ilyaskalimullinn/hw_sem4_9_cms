<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: ilyas
  Date: 18.04.2023
  Time: 08:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <c:choose>
            <c:when test="${not empty article}">
                <c:out value="${article.title}" />
            </c:when>
            <c:otherwise>
                New document
            </c:otherwise>
        </c:choose>
    </title>
    <script src="https://cdn.tiny.cloud/1/obwd9gkebp7butdqsoi2n9zgfi8xcd8mio9rbzkpb0v7313m/tinymce/6/tinymce.min.js"
            referrerpolicy="origin"></script>
</head>
<body>

    <form action="" method="post">
        <textarea name="content" id="editor" cols="30" rows="10">
            <c:if test="${not empty article}">
                ${article.content}
            </c:if>
        </textarea>
        <security:csrfInput/>
        <input type="submit" value="Save" />
    </form>

<%--    <form:form modelAttribute="articleForm">--%>
<%--        <form:textarea id="editor" path="content"/>--%>
<%--        <input type="submit" value="Save" />--%>
<%--    </form:form>--%>

    <c:if test="${not empty error}">
        <div style="color: red;">
            <c:out value="${error}" />
        </div>
    </c:if>

    <a href="${s:mvcUrl("AC#list").build()}">All articles</a>

<script>
    tinymce.init({
        selector: '#editor',
        plugins: '',
        toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
        tinycomments_mode: 'embedded',
        tinycomments_author: 'Author name',
        mergetags_list: [
            {value: 'First.Name', title: 'First Name'},
            {value: 'Email', title: 'Email'},
        ],
    });
</script>
<%--<script>--%>
<%--    document.addEventListener('DOMContentLoaded', () => {--%>
<%--        const editorForm = document.getElementById("#editorForm");--%>
<%--        editorForm.onsubmit((event) => {--%>
<%--            event.preventDefault();--%>
<%--            let content = tinymce.get("editor").getContent();--%>

<%--            editorForm.submit--%>
<%--        })--%>
<%--    })--%>

<%--</script>--%>
</body>
</html>
