<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="test.css" />
    <jsp:param name="js" value="highlighter" />
</jsp:include>

<c:if test="${sessionScope.user == null}">
    <c:set scope="request" var="message" value="Please, login first before starting test." />
    <jsp:forward page="signIn.jsp" />
</c:if>
<c:if test="${sessionScope.questions == null}">
    <form id="trueForm" action="startTest" method="post">
        <input type="hidden" name="confirmed" value="true">
    </form>
    <form id="falseForm" action="startTest" method="post">
        <input type="hidden" name="confirmed" value="false">
    </form>
    <script type="text/javascript">
        function getConfirmation() {
            return confirm("Do you want to start the test?");
        }

        if (getConfirmation()) {
            document.getElementById("trueForm").submit();
        } else {
            document.getElementById("falseForm").submit();
        }
    </script>
</c:if>
<aside class="questions">
    <form action="test" method="post">
        <c:forEach items="${sessionScope.questions}" var="question">
            <c:choose>
                <c:when test="${question.active}">
                    <button class="btn active" type="submit" name="qnNum" value="${question.num}">${question.num}</button>
                </c:when>
                <c:otherwise>
                    <button class="btn" type="submit" name="qnNum" value="${question.num}">${question.num}</button>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <button class="btn finish-btn" type="submit" value="finish">Finish</button>
    </form>
</aside>
<main class="question-main">
    <div class="question">
        <p><c:out value="${requestScope.currentQn.question}"/></p>
        <c:if test="${requestScope.currentQn.code != null}">
            <br>
            <pre><code class="java"><c:out value="${requestScope.currentQn.code}"/></code></pre>
        </c:if>
    </div>
    <br>
    <div class="answer">
        <form action="" method="post">
            <c:forEach items="${requestScope.currentQn.choice}" var="item" varStatus="count">
                <input id="opt1" type="${requestScope.currentQn.choiceType}" name="option" value="${count}">
                <label for="opt1">${item}</label>
                <br>
            </c:forEach>
            <br>
            <input class="btn answer-btn" type="submit" value="Answer">
        </form>
    </div>
</main>
<jsp:include page="WEB-INF/footer.jsp" />
