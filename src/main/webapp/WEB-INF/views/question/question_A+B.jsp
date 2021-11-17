<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="../sources/tabIcon.png">

<script src="../jquery.js"></script>
<link rel="stylesheet" type="text/css" href="../css/common.css">
<link rel="stylesheet" type="text/css" href="../css/question.css">
</head>
<body>

	<script>
		$(document).ready(function() {
			$("#header").load("header.html");
			$(".ad").load("ad.html");
			$("#footer").load("footer.html");
		});
	</script>
	<header id="header"></header>
	<div class="ad"></div>
	<div id="question">
	<h2 id="questtitle">A+B</h2>
	<table id="requirements">
		<thead>
			<tr>
				<th>시간 제한</th>
				<th>메모리 제한</th>
				<th>제출</th>
				<th>정답</th>
				<th>맞은 사람</th>
				<th>정답 비율</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>2<span>초</span></td>
				<td>128<span>MB</span></td>
				<td>430742</td>
				<td>183375</td>
				<td>132288</td>
				<td>43.486%</td>
			</tr>
		</tbody>
	</table>
	<h3 class="head">
		<span>문제</span>
	</h3>
	<p>두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오.</p>
	<h3 class="head">
		<span>입력</span>
	</h3>
	<p>첫째 줄에 A와 B가 주어진다. (0 &#60; A, B &#62; 10)</p>
	<h3 class="head">
		<span>출력</span>
	</h3>
	<p>첫째 줄에 A+B를 출력한다</p>
	<h3 class="head">
		<span>예제 입력1</span>
	</h3>
	<div class="inputEx">1 2</div>
	<h3 class="head">
		<span>예제 출력1</span>
	</h3>
	<div class="outputEx">3</div>
	</div>
	<div class="ad"></div>
	<footer id="footer"></footer>
</body>
</html>