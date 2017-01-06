<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="WEB-INF/static/header.html" %>
<div id="login-form">
    <h2>Sign Up</h2>
    <c:if test="${not empty requestScope.messages}">
        <c:forEach items="${requestScope.messages}" var="message">
            <br>
            <p style="color: red;"><c:out value="${message}"/></p>
        </c:forEach>
    </c:if>
    <form action="sign-up" method="post">
        <div id="form-container" class="flex-container">
            <label for="username-input">Login:</label>
            <input id="username-input" type="text" name="username" size="15" required>
            <br>
            <label for="password-input">Password:</label>
            <input id="password-input" type="password" name="password" size="15" required>
            <br>
            <label for="conf-password-input">Confirm Password:</label>
            <input id="conf-password-input" type="password" name="confPassword" required>
            <br>
            <input id="submit" type="submit" value="Confirm">
            <a href="login.jsp" id="sign-in">Sign In</a>
        </div>
    </form>
</div>
<%@ include file="WEB-INF/static/footer.html"%>
