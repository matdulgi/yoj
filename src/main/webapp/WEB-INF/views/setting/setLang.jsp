<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="com.yura.yoj.status.MemberStatus.LoginStatus" %>" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="menu.jsp" %>
언어 설정

<form action="" method="post">
<h6>아이디</h6>
<input type="text">
<h6>상태 메시지</h6>
<input type="text">
<h6>비밀번호</h6>
<input type="text">
<h6>학교</h6>
<input type="text">
<h6>이메일</h6>
<input type="text">
<button>수정</button>
</body>
</html>