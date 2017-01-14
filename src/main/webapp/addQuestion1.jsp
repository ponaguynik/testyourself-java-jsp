<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="addQuestion1.css" />
</jsp:include>
<main class="flex-container">
    <div class="form-container flex-container">
       <form action="" method="post" class="flex-container">
           <label for="question-input">Question:</label>
           <input class="text" id="question-input" type="text" name="question">
           <label>Code:</label>
           <textarea class="code-field" rows="10" cols="50"></textarea>
           <label>Choices:</label>
           <div class="hor">
               <input type="radio" name="option" value="1">
               <input class="text" type="text" name="option1">
           </div>
           <div class="hor">
               <input type="radio" name="option" value="2">
               <input class="text" type="text" name="answer-text2">
           </div>
           <div class="hor">
               <input type="radio" name="option" value="3">
               <input class="text" type="text" name="answer-text3">
           </div>
           <input class="btn" type="submit" value="Submit">
        </form>
    </div>
</main>
<jsp:include page="WEB-INF/footer.jsp" />
