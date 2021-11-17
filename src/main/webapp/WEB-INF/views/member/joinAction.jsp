<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.yura.yoj.status.MemberStatus.JoinStatus" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%if(request.getAttribute("joinStatus")==JoinStatus.DUPLICATEDID) { %>
<script>alert("중복된 아이디 입니다")</script>
<% response.sendRedirect("join.jsp"); %>
<%} else {%>
<script>alert("회원가입 되었습니다.")</script>
<% 
String loginID = (String)request.getParameter("memberId");
session.setAttribute("joinStarus", JoinStatus.SUCCESS);
session.setAttribute("loginId", loginID);
response.sendRedirect("/PJ_jsp/index.jsp"); }%>
</body>
</html>