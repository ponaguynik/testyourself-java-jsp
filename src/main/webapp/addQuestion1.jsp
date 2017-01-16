<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="addQuestion1.css" />
</jsp:include>
<main class="flex-container">
    <form action="addQuestion" method="post" class="flex-container config-form">
       <input type="hidden" name="num" value="${param.num}">
       <input type="hidden" name="choiceType" value="${param.choiceType}">
       <label for="question-input">Question:</label>
       <input class="text" id="question-input" type="text" name="question" maxlength="200" required>
       <c:if test="${not empty param.code}">
           <label>Code:</label>
           <textarea name="code" class="code-field" rows="10" cols="50" maxlength="1000"></textarea>
       </c:if>
       <label>Choice:</label>
       <c:forEach begin="1" end="${param.num}" varStatus="count">
           <div class="hor">
               <input type="${param.choiceType}" name="option" value="${count.count}">
               <input class="text" type="text" name="option${count.count}" maxlength="200" required>
           </div>
       </c:forEach>
       <input class="btn submit-btn" type="submit" value="Submit">
    </form>
</main>
<jsp:include page="WEB-INF/footer.jsp" />
