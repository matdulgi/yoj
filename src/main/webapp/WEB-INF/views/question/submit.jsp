<%@page import="com.yura.yoj.dto.QuestionDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="submit.css">
</head>
<body>
<%-- <%@include file="questionListMenu.jsp" %> --%>
<%-- <%@include file="questionMenu.jsp" %> --%>
<%-- <c:set var="loginId" value="<%=session.getAttribute("loginId") %>"/> --%>
 <c:choose>
<c:when test="${loginId eq null}">
<jsp:forward page="/member/login.jsp"/>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>
<h3>제목</h3>
<form id=submit method="post" action="submit.do">

<p>언어</p>
<select name="lang">
	<option value="c" selected>c</option>
</select>
<div id="sourceDiv">
<p>소스 코드</p>
<!-- <input id="source" name="source" type="text" id="sourceInputBox" > -->
<textarea id="source" name="source" id="sourceInputBox" ></textarea> 
 <input type="hidden" name="questionNum" value="${param.questionNum}">

<%System.out.println("싸부밋 퀘스쳔넘 : " + request.getParameter("questionNum")); %>
 <button id="submitButton">제출</button>
</div>
</form>
</body>
</html>