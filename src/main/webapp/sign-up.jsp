<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/static/header.html" %>
<div id="login-form">
    <h2>Sign Up</h2>
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
