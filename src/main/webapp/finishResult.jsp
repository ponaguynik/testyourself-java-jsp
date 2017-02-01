<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="finishResult.css" />
    <jsp:param name="js" value="highlighter" />
</jsp:include>
<main class="flex-container">
    <div class="results">
        <table>
            <thead>
                <tr>
                    <th colspan="2">Result: <c:out value="${requestScope.testResult.result}"/></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Number of unanswered questions:</td>
                    <td class="align-center"><c:out value="${requestScope.testResult.unansweredQnsCount}"/></td>
                </tr>
                <tr>
                    <td>Duration:</td>
                    <td class="align-center"><c:out value="${requestScope.testResult.duration}"/></td>
                </tr>
                <tr>
                    <td>Date:</td>
                    <td class="align-center"><c:out value="${requestScope.testResult.date}"/></td>
                </tr>
                <tr>
                    <td>Time:</td>
                    <td class="align-center"><c:out value="${requestScope.testResult.time}"/></td>
                </tr>
            </tbody>
        </table>
    </div>
    <c:forEach items="${requestScope.questions}" var="question">
        <div class="item">
            <div class="question">
                <div class="question-text flex-container">
                    <p><c:out value="${question.num}"/>. <c:out value="${question.question}"/></p>
                    <c:choose>
                        <c:when test="${question.correct}">
                            <img src="resources/images/correct.png" alt="correct">
                        </c:when>
                        <c:otherwise>
                            <img src="resources/images/incorrect.png" alt="incorrect">
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${question.code != null}">
                    <br>
                    <pre><code class="java"><c:out value="${question.code}"/></code></pre>
                </c:if>
            </div>
            <br>
            <div class="answer">
                <form>
                    <c:set var="idNum" value="1" />
                    <c:forEach items="${question.choice}" var="item" varStatus="count">
                        <c:set var="checked" value="false" />
                        <c:set var="correct" value="false" />
                        <c:forEach var="itm" items="${question.answers}">
                            <c:if test="${count.index+1 eq itm}">
                                <c:set var="checked" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="itm" items="${question.correctAnswers}">
                            <c:if test="${count.index+1 eq itm}">
                                <c:set var="correct" value="true"/>
                            </c:if>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${correct}">
                                <div class="image">
                                    <img src="resources/images/checked.png">
                                </div>
                            </c:when>
                            <c:when test="${checked and not correct}">
                                <div class="image">
                                    <img src="resources/images/cross.png">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="image">

                                </div>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${checked}">
                                <input id="opt${idNum}" type="${question.choiceType}" name="answer" value="${count.index+1}" checked disabled>
                            </c:when>
                            <c:otherwise>
                                <input id="opt${idNum}" type="${question.choiceType}" name="answer" value="${count.index+1}" disabled>
                            </c:otherwise>
                        </c:choose>
                        <label for="opt${idNum}">${item}</label>
                        <c:set var="idNum" value="${idNum+1}" />
                        <br>
                    </c:forEach>
                </form>
            </div>
        </div>
    </c:forEach>
    <br>
    <a class="btn" href="<%= response.encodeURL("test.jsp") %>">Try Again</a>
</main>
<jsp:include page="WEB-INF/footer.jsp" />
