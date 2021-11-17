<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="com.yura.yoj.status.MemberStatus.LoginStatus" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="menu.jsp" %>
<form>
<h3>문의하기</h3>
<h6>이메일</h6>
<input type="text">
<h6>제목</h6>
<input type="text">
<h6>내용</h6>
<input type="text">
<button>문의하기</button>
</form>
</body>
</html>