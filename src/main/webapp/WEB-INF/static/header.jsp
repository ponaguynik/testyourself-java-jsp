<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/sign-in.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/sign-up.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/test.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/about.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/results.css">
    <title>TestYourself</title>
</head>
<body>
<div id="content">
    <header class="flex-container">
        <a href="<%= response.encodeURL("index.jsp") %>"><img src="${pageContext.request.contextPath}/resources/images/testyourself-logo.png"></a>
    </header>
    <nav class="flex-container">
        <a href="<%= response.encodeURL("index.jsp") %>">Home</a>
        <a href="<%= response.encodeURL("test.jsp") %>">Test</a>
        <a href="<%= response.encodeURL("results.jsp") %>">My Results</a>
        <a href="<%= response.encodeURL("about.jsp") %>">About</a>
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <a href="<%= response.encodeURL("sign-in.jsp") %>" class="login-menu-item"><img src="${pageContext.request.contextPath}/resources/images/login.png"></a>
            </c:when>
            <c:otherwise>
                <a href="<%= response.encodeURL("logout") %>" class="login-menu-item"><img src="${pageContext.request.contextPath}/resources/images/logout.png"></a>
            </c:otherwise>
        </c:choose>
    </nav>
    <section class="flex-container">
