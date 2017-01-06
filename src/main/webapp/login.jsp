<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/static/header.html"%>
<div id="login-form">
    <h2>Sign In</h2>
    <form action="" method="post">
        <div id="form-container" class="flex-container">
            <label for="login-input">Login:</label>
            <input id="login-input" type="text" name="login">
            <br>
            <label for="password-input">Password:</label>
            <input id="password-input" type="password" name="password">
            <br>
            <input id="submit" type="submit" value="Sign In">
            <a href="sign-up.jsp" id="sign-up">Sign Up</a>
        </div>
    </form>
</div>
<%@ include file="WEB-INF/static/footer.html"%>
