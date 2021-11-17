<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="com.yura.yoj.status.MemberStatus.LoginStatus" %>
<%@ page import ="com.yura.yoj.status.MemberStatus.ModifyStatus" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/PJ_jsp/common/common.css">
<link rel="stylesheet" type="text/css" href="modify.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="menu.jsp"%>
<h3>정보수정</h3>
<form action="modify.do" method="post">
<h6>아이디</h6>
<input type="text" name="inputId" value="<%= session.getAttribute("loginId") %>" disabled>
<h6>상태 메시지</h6>
<input type="text" name="message">
<h6>비밀번호</h6>
<input type="text" name="inputPw">
<%if(request.getAttribute("modifyStatus")==ModifyStatus.INCORRECTPW) 
out.println("<p id='incorrectPW'>잘못된 비밀번호입니다</p>");
%>
<h6>학교</h6>
<input type="text" name="group">
<h6>이메일</h6>
<input type="email" name="email">
<button>수정</button>
</form> 
</body>
</html>