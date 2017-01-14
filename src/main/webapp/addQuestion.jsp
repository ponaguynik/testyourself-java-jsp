<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="css" value="addQuestion.css" />
</jsp:include>
<main class="flex-container">
    <h2>Question Configuration</h2>
        <form class="flex-container" id="form" action="addQuestion1.jsp" method="post">
            <div class="hor">
                <label for="code-cb">Field for code:</label>
                <input id="code-cb" type="checkbox" name="code-field">
            </div>
            <div class="hor">
                <label for="num-select">Number of choices:</label>
                <select id="num-select" name="num">
                    <option value="2" selected>2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
            <div class="hor">
                <label for="type-select">Type:</label>
                <select id="type-select" name="type">
                    <option value="radio" selected>radio</option>
                    <option value="checkbox">checkbox</option>
                </select>
            </div>
        </form>
    <input class="btn" form="form" type="submit" value="Next >>">
</main>
<jsp:include page="WEB-INF/footer.jsp" />