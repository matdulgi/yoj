<%@ page import="com.yura.yoj.status.MemberStatus.LoginStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	//호출된 페이지로 돌아가기
		String referedPage = request.getParameter("referedPage");
/* 		LoginStatus loginStatus = (LoginStatus) request.getAttribute("loginStatus");//요청으로부터 속성을 받아오는데 없다....
 */		
 		out.print("로그인 성공");

		if (referedPage != null)
			response.sendRedirect(referedPage);
		else
			response.sendRedirect("/PJ_jsp/main.do");
	%>
</body>
</html>