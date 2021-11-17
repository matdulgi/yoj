<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>랭킹</title>
<link rel="shortcut icon" href="../sources/tabIcon.png">

<script src="/PJ_jsp/common/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="/PJ_jsp/common/common.css">
<link rel="stylesheet" type="text/css" href="/PJ_jsp/ranklist/ranklist.css">
</head>
<body>
<script>
$(document).ready(function(){
	$("#header").load("/PJ_jsp/common/header.jsp");
	$("#footer").load("/PJ_jsp/common/footer.jsp");
	$(".ad").load("/PJ_jsp/common/ad1.jsp");
});
</script>
<header id="header"></header>
<div class="ad"></div>
<nav id="problemlistNav"></nav>
<table class="boardtable">
	<thead>
		<tr><th>등수</th><th>아이디</th><th>상태 메시지</th><th>맞은 문제</th><th>제출</th><th>정답 비율</th></tr>
		<tr><td>1</td><td><a href="#">lskw22</a></td><td>여러분 이해 되셨나요?</td><td>9033</td><td>18277</td><td>54.181%</td></tr>
		<tr><td>2</td><td><a href="#">awoiejf0</a></td><td>저는 프로틴을 밥처럼 먹어요</td><td>7842</td><td>11262</td><td>72.430%</td></tr>
		<tr><td>3</td><td><a href="#">l829u2</a></td><td>훌륭한 프로그래머가 될게요</td><td>7565</td><td>13384</td><td>61.459%</td></tr>

	</thead>
	<tbody>
	</tbody>
</table>

<div class="ad"></div>
<footer id="footer"></footer>
</body>
</html>