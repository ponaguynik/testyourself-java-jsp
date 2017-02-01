<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="test.css" />
    <jsp:param name="js" value="highlighter" />
</jsp:include>
<script type="text/javascript">
    function startTest() {
        return confirm("Do you want to start the test?");
    }

    function finishTest() {
        if (confirm("Do you want to finish the test?")) {
            document.getElementById("finishTestForm").submit();
        }
    }

    function finishTestConfirm() {
        if (confirm("${requestScope.finishMessage}")) {
            document.getElementById("finishTestAnyway").submit();
        }
    }
</script>
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
        if (startTest()) {
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
                    <input class="btn active" type="submit" name="qnNum" value="${question.num}">
                </c:when>
                <c:otherwise>
                    <input class="btn" type="submit" name="qnNum" value="${question.num}">
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </form>
    <form id="finishTestForm" action="finish" method="post" ></form>
    <button class="btn finish-btn" onclick="finishTest()" value="finish">Finish</button>
    <c:if test="${requestScope.finishMessage != null}">
        <form id="finishTestAnyway" action="finish" method="post">
            <input type="hidden" name="finishTestAnyway" value="true">
        </form>
        <script type="text/javascript">
            finishTestConfirm();
        </script>
    </c:if>
</aside>
<main class="question-main">
    <div class="question">
        <p><c:out value="${sessionScope.currentQn.question}"/></p>
        <c:if test="${sessionScope.currentQn.code != null}">
            <br>
            <pre><code class="java"><c:out value="${sessionScope.currentQn.code}"/></code></pre>
        </c:if>
    </div>
    <br>
    <div class="answer">
        <c:if test="${requestScope.message != null}">
            <p style="color: red"><c:out value="${requestScope.message}"/></p>
            <br>
        </c:if>
        <c:choose>
            <c:when test="${!sessionScope.currentQn.answered}">
                <form action="answer" method="post">
                    <c:forEach items="${sessionScope.currentQn.choice}" var="item" varStatus="count">
                        <input id="opt${count.index+1}" type="${sessionScope.currentQn.choiceType}" name="answer" value="${count.index+1}">
                        <label for="opt${count.index+1}">${item}</label>
                        <br>
                    </c:forEach>
                    <br>
                    <input class="btn answer-btn" type="submit" value="Answer">
                </form>
            </c:when>
            <c:otherwise>
                <form action="cancel" method="post">
                    <c:forEach items="${sessionScope.currentQn.choice}" var="item" varStatus="count">
                        <c:set var="contains" value="false" />
                        <c:forEach var="itm" items="${sessionScope.currentQn.answers}">
                            <c:if test="${count.index+1 eq itm}">
                                <c:set var="contains" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${contains}">
                                <input id="opt${count.index+1}" type="${sessionScope.currentQn.choiceType}" name="answer" value="${count.index+1}" checked disabled>
                            </c:when>
                            <c:otherwise>
                                <input id="opt${count.index+1}" type="${sessionScope.currentQn.choiceType}" name="answer" value="${count.index+1}" disabled>
                            </c:otherwise>
                        </c:choose>
                        <label for="opt${count.index+1}">${item}</label>
                        <br>
                    </c:forEach>
                    <br>
                    <input class="btn answer-btn" type="submit" value="Cancel">
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<jsp:include page="WEB-INF/footer.jsp" />
