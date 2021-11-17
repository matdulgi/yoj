<%@page import="com.yura.yoj.status.MemberStatus.DelMemberStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="menu.jsp"%>
<form action="delMember.do" method="post">
<h3>회원 탈퇴</h3>
<h6>비밀번호</h6>
<input type="text" name="inputPw">
<% if(request.getAttribute("delMemberStatus") == DelMemberStatus.INCORRECTPW)
	out.println("<p id='incorrectPw')>잘못된 비밀번호입니다</p>");%>
<p>정말로 탈퇴하실껀가용</p>
<button>탈퇴하기</button>

</form>
</body>
</html>