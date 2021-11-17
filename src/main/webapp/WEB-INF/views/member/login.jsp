<%@page import="com.yura.yoj.status.MemberStatus.LoginStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" type="text/css" href="${css}/member/login.css">
</head>
<body>
<%
//referer - 우선 저장
String refererUrl = request.getHeader("referer");
String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
String loginRefererUri = refererUrl.substring(host.length());
String rootPath = (String)pageContext.getAttribute("rootPath");

if(request.getAttribute("loginStatus")==LoginStatus.FAILED){}
else {
	if(loginRefererUri.contains("login")) {
		loginRefererUri = (String)session.getAttribute("loginRefererUri");
	}
	else if(loginRefererUri.contains("join")){
		loginRefererUri = rootPath + "/main.do";
	}
	else {//아무일도 없는 경우
		session.setAttribute("loginRefererUri", loginRefererUri);//세션에 저장	
	}
	
}
	

//기본적으로 파라미터로 사용, 특정 상황시에는 속성에 저장해놓고 사용
//String refererUri = (String)request.getAttribute("refererUri");



//refererUri = (String)request.getAttribute("refererUri");


//
System.out.println("돌아갈 페이지 : " + loginRefererUri);%> 

	<div id="headline">
	<h3>로그인</h3></div>
	<div id="login_container">
		<h3 id="loginHead">로그인</h3>
		<hr>
		<form id="loginform" method="post" action="${rootPath}/loginAction.do">
<!-- 		<form id="loginform" method="post"> -->
			<label><img src="${sources}/login_ID.png"><input class="input" id="id" name="inputId" type="text"></label><br> 
			<label><img src="${sources}/login_PW.png"><input class="input" id="pw" name="inputPw" type="password"></label><br> 
			<label class="checkbox"><input type="checkbox">로그인 상태 유지</label><br>
			<div id="submitBox">
			<input type="hidden" name="loginRefererUri" value="<%=loginRefererUri%>">
<!-- 				<button class="submit" onclick="login()">로그인</button> -->
				<button class="submit" >로그인</button>
			</div>
		</form>
		<hr>
		<div id="readme">
		<%
		loginStatus = (LoginStatus)session.getAttribute("loginStatus");
		out.println(loginStatus);
		if(session.getAttribute("loginStatus")==LoginStatus.MEMBER) response.sendRedirect(rootPath);
		if(request.getAttribute("loginStatus")==LoginStatus.FAILED)
			out.println("<p id='loginfailed'>아이디/이메일 또는 비밀번호가 잘못되었습니다</p>");%>
			<p>아이디나 비밀번호를 잊었을 때는, <a href="#">여기</a>를 눌러주세요.</p>
			<p>회원 가입은 <a href="${rootPath}/member/join.do">여기</a>에서 할 수 있습니다</p>
		</div>
	</div>
	<footer id="footer"></footer>

<script src="${js}/member/login.js"></script>
</body>
</html>