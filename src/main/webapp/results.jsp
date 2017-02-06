<%@ page import="database.DBWorker" %>
<%@ page import="model.User" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.TestResult" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="results.css" />
</jsp:include>
<c:if test="${sessionScope.user == null}">
    <c:set scope="request" var="message" value="Please, login first before reviewing results." />
    <jsp:forward page="signIn.jsp" />
</c:if>
<%
    DBWorker dbWorker = (DBWorker) application.getAttribute("DBWorker");
    User user = (User) session.getAttribute("user");
    try {
        ArrayList<TestResult> results = dbWorker.getAllUsersResults(user);
        if (!results.isEmpty())
            pageContext.setAttribute("results", dbWorker.getAllUsersResults(user));
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendError(500, "SQL Exception");
        return;
    }
%>
<c:choose>
    <c:when test="${results != null}">
    <table class="align-center">
        <thead>
        <tr>
            <th>№</th>
            <th>Date</th>
            <th>Time</th>
            <th>Result</th>
            <th class="last">Duration</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${results}" var="result" varStatus="count">
                <tr>
                    <td>${count.count}</td>
                    <td>${result.date}</td>
                    <td>${result.time}</td>
                    <td>${result.result}</td>
                    <td>${result.duration}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </c:when>
    <c:otherwise>
        <p class="align-center">No results yet.</p>
    </c:otherwise>
</c:choose>
<jsp:include page="WEB-INF/footer.jsp" />
