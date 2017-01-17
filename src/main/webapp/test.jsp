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
    <ul>
        <li><button class="btn active">1</button></li>
        <li><button class="btn">2</button></li>
        <li><button class="btn">3</button></li>
        <li><button class="btn">4</button></li>
        <li><button class="btn">5</button></li>
        <li><button class="btn">6</button></li>
        <li><button class="btn">7</button></li>
        <li><button class="btn">8</button></li>
        <li><button class="btn">9</button></li>
        <li><button class="btn">10</button></li>
        <li><button class="btn" id="finish-btn">Finish</button></li>
    </ul>
</aside>
<main class="question-main">
    <div class="question">
        <p>This is a question.</p>
        <br>
        <pre><code class="java">public static void main(String[] args) {
            System.out.println("Hello, World!");
        }</code></pre>
    </div>
    <br>
    <div class="answer">
        <form action="" method="post">
            <input id="opt1" type="radio" name="option" value="1">
            <label for="opt1">Option 1</label>
            <br>
            <input id="opt2" type="radio" name="option" value="2">
            <label for="opt2">Option 2</label>
            <br>
            <input id="opt3" type="radio" name="option" value="3">
            <label for="opt3">Option 3</label>
            <br>
            <input id="opt4" type="radio" name="option" value="4">
            <label for="opt4">Option 4</label>
            <br><br>
            <input id="answer-btn" class="btn" type="submit" value="Answer">
        </form>
    </div>
</main>
<jsp:include page="WEB-INF/footer.jsp" />
