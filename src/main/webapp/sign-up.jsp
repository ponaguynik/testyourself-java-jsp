<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/static/header.jsp">
    <jsp:param name="css" value="sign-up.css" />
</jsp:include>
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
            <label for="username-input">Username:</label>
            <input id="username-input" type="text" name="username" size="15" required>
            <br>
            <label for="password-input">Password:</label>
            <input id="password-input" type="password" name="password" size="15" required>
            <br>
            <label for="conf-password-input">Confirm Password:</label>
            <input id="conf-password-input" type="password" name="confPassword" required>
            <br>
            <input id="submit" type="submit" value="Confirm">
            <a href="sign-in.jsp" id="sign-in">Sign In</a>
        </div>
    </form>
</div>
<jsp:include page="WEB-INF/static/footer.jsp" />
