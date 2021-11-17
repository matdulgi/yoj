<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="com.yura.yoj.status.MemberStatus.LoginStatus" %>
<%@ page import ="com.yura.yoj.status.MemberStatus.ChangePwStatus" %>
<%@ page import ="com.yura.yoj.status.MemberStatus.ModifyStatus" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/PJ_jsp/common/common.css">
<link rel="stylesheet" type="text/css" href="modify.css">
</head>
<body>
<%@ include file="menu.jsp" %>
<h3>비밀번호 변경</h3>
<form action="changePw.do" method="post">
<h6>현재 비밀번호</h6>
<input type="text" name="inputPw">
<% if(request.getAttribute("modifyStatus")==ModifyStatus.INCORRECTPW)
out.print("<p id='inconrrectPw'>잘못된 비밀번호입니다</p>");
%>
<h6>새로운 비밀번호</h6>
<input type="text" name="memberPw">
<h6>새로운 비밀번호 (확인)</h6>
<input type="text" name="memberPw_re">
<button>수정</button>
</form>
</body>
</html>