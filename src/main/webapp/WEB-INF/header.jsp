<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/reset.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/${param.css}">
    <c:if test="${param.js == 'highlighter'}">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/highlighter/default.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles/highlighter/github-gist.css">
        <script src="${pageContext.request.contextPath}/resources/js/highlight.pack.js"></script>
        <script>hljs.initHighlightingOnLoad();</script>
    </c:if>
    <title>TestYourself</title>
</head>
<body>
<div id="content">
    <header class="flex-container">
        <a href="<%= response.encodeURL("index.jsp") %>"><img src="${pageContext.request.contextPath}/resources/images/testyourself-logo.png" alt="TestYourself-logo"></a>
    </header>
    <nav class="flex-container">
        <a href="<%= response.encodeURL("index.jsp") %>">Home</a>
        <a href="<%= response.encodeURL("test.jsp") %>">Test</a>
        <a href="<%= response.encodeURL("results.jsp") %>">My Results</a>
        <a href="<%= response.encodeURL("about.jsp") %>">About</a>
        <c:if test="${sessionScope.user.username == 'admin'}">
            <a href="<%= response.encodeURL("addQuestion.jsp") %>">Add Question</a>
        </c:if>
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <a href="signIn.jsp" class="login-menu-item"><img src="${pageContext.request.contextPath}/resources/images/login.png" alt="Login"></a>
            </c:when>
            <c:otherwise>
                <form class="login-menu-item" action="<%= response.encodeURL("logout") %>" method="post">
                    <input type="image" src="${pageContext.request.contextPath}/resources/images/logout.png" alt="Logout">
                </form>
            </c:otherwise>
        </c:choose>
    </nav>
    <section class="flex-container">
