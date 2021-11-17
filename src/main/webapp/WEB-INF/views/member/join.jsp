<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.yura.yoj.status.MemberStatus.JoinStatus" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<link rel="stylesheet" type="text/css" href="${css}/member/join.css">
</head>
<body>
	<div id="headline">
	<h3>회원가입</h3></div>
	<%if(request.getAttribute("joinStatus")==JoinStatus.DUPLICATEDID)
	out.println("<p id='duplicatedId'>가입할 수 없는 아이디입니다</p>");%>
	<div class="join_container">
		<div id="readme">
			<h3>회원가입</h3>
			<p>
				계정이 이미 있는 경우에는 <a href="login.html">로그인</a>해주세요.
			</p>
			<p> 가입을 하면 유라 온라인 저지의 <a href="#">이용 약관</a>, <a href="#">개인정보취급방침 및 개인정보3자제공</a>에 동의하게 됩니다.
			<p>아이디가 구글 검색에 노출되는 것을 원치않는 분은 다른 곳에서 사용하지 않는 아이디롤 사용해주세요.</p>
			<p>아이디의 구글 검색 노출은 회원가입 후 정보 수정에서 변경할 수 있습니다.
			<p>가입 후 아이디는 변경할 수 없습니다.</p>	
		</div>
		<hr>

		<!-- action = /JP_jsp/member/join.do -->
		<form id="joinform" method="post" action="joinAction.do">
			<div id="inputBox">
				<p>아이디</p> <input id="memberID" class="input" name="memberId" type="text">
				<p>상태 메시지(다른 사람에게 보이고 싶은 한마디)</p> <input id="message" class="input" name="message" type="text">
				<p>비밀번호</p> <input class="input pw" name="memberPW" id="memberPw" type="password">
				<p>비밀번호 (확인)</p> <input class="input pw" name="memberPw_re" id="memberPW_re" type="text">
				<p id="wrongPw"></p>
				<p>학교/회사</p> <input id="group" class="input" name="group" type="text">
				<p>이메일</p> <input id="email" class="input" name="email" type="email">
			</div>
			<hr>
			<div id="submitBox">
				<button class="submit">회원가입</button>
			</div>
		</form>
	</div>
	
<footer id="footer"></footer>
<script src="${js}/member/join.js"></script>
</body>
</html>