<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="WEB-INF/static/header.jsp"%>
<div id="login-form">
    <h2>Sign In</h2>
    <c:if test="${not empty requestScope.message}">
        <br>
        <p style="color: red;"><c:out value="${requestScope.message}" /></p>
    </c:if>
    <form action="sign-in" method="post">
        <div id="form-container" class="flex-container">
            <label for="username-input">Username:</label>
            <input id="username-input" type="text" name="username" size="15" required>
            <br>
            <label for="password-input">Password:</label>
            <input id="password-input" type="password" name="password" size="15" required>
            <br>
            <input id="submit" type="submit" value="Sign In">
            <a href="sign-up.jsp" id="sign-up">Sign Up</a>
        </div>
    </form>
</div>
<%@ include file="WEB-INF/static/footer.jsp"%>