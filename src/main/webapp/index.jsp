<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="index.css" />
</jsp:include>
<main class="info">
    <article>
        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
        quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
        cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
        proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
    </article>
</main>
<aside class="user-info">
    <div id="user-box" class="flex-container">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <span>Hello, <span><c:out value="${sessionScope.user.username}" />!</span></span>
                <br>
                <p>Your last result: <c:out value="${sessionScope.user.lastResult}%"/></p>
                <p>Your best result: <c:out value="${sessionScope.user.bestResult}%"/></p>
                <br>
                <form action="<%= response.encodeURL("logout")%>" method="post">
                    <input type="submit" value="Logout">
                </form>
            </c:when>
            <c:otherwise>
                <span>Hello, <span>Guest!</span></span>
                <br>
                <p>Please, login first to test yourself.</p>
                <br>
                <a href="signIn.jsp">Login</a>
            </c:otherwise>
        </c:choose>

    </div>
</aside>
<jsp:include page="WEB-INF/footer.jsp" />
