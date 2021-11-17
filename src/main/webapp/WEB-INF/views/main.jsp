<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>유라의 알고리즘</title>
<link rel="shortcut icon" href="${sources}/tabIcon.png">
<link rel="stylesheet" type="text/css" href="${css}/common/main.css">
</head>
<body>
	<div id="slideWrapper">
		<img class="slide" src="${sources }/slide1.jpg">
	</div>

	<div id="mainDiv1">
		<p id="div1head">Yura Online Judge</p>
		<br>
		<hr id=midline>
		<br>
		<p>문제를 플고 온라인으로 채점받을 수 있는 곳입니다.</p>
	</div>

	<div id="questInfo_container">
		<div id=questInfo>
			<div class=questInfoDiv>
				<div class=questInfoBox>
					<span class="status">0</span><br> <span class="questsub">전체문제</span>
				</div>
				<div class=questInfoBox>
					<span class="status">0</span><br> <span class="questsub">채점
						가능한 문제</span>
				</div>
			</div>
			<div class=questInfoDiv>
				<div class=questInfoBox>
					<span class="status">0</span><br> <span class="questsub">풀린
						문제</span>
				</div>
				<div class=questInfoBox>
					<span class="status">0</span><br> <span class="questsub">채점
						가능한 언어</span>
				</div>
			</div>
		</div>
	</div>

	<div id="mainboards">
		<div class="mainboard">
			<span class="mainboardTitle">새로운 글</span>
			<ul>
				<li><span class="memberID">@lim1142</span><span
					class="postTitle">자바 진짜 재밌는거 같지 않아요?</span></li>
				<li><span class="memberID">@ddongjjan</span><span
					class="postTitle">고구마가 제철이래요</span></li>
				<li><span class="memberID">@kosmo88</span><span
					class="postTitle">새 글이에요~~</span></li>
			</ul>
		</div>

		<div class="mainboard">
			<span class="mainboardTitle">문제</span>
			<ul>
				<li><span class="quesNum"></span><span>새 문제입니다~~</span></li>
				<li><span class="quesNum"></span><span>새 문제입니다~~</span></li>
				<li><span class="quesNum"></span><span>새 문제입니다~~</span></li>
			</ul>
		</div>
	</div>
	<footer>
    </footer>
<!--  	<script src="/PJ_jsp/common/common.js"></script> --> 
</body>
</html>
