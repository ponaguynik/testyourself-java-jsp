<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="error.css" />
</jsp:include>
<main class="flex-container">
    <div>
        <% if (response.getStatus() == 404) { %>
            <h2><span>404</span><br>Page not found.</h2>
        <% } else { %>
            <h2>Oops... Something went wrong</h2>
        <% } %>
    </div>
</main>
<jsp:include page="WEB-INF/footer.jsp" />