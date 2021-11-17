<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="../jquery.js"></script>

<link rel="stylesheet" type="text/css" href="/PJ_jsp/common/common.css">
</head>
<body>

<script>
$(document).ready(function(){
	$("#header").load("header.html")
	$("#footer").load("footer.html");;
});
</script>
<header id="header"></header>
<nav id="nav"></nav>
<div id="div"></div>
<nav id="problemlistNav"></nav>
<table>
	<thead>
		<tr><th>문제</th><th>문제 제목</th><th>정보</th><th>맞은 사람</th><th>제출</th><th>정답 비율</th></tr>
	</thead>
	<tbody>
	</tbody>
</table>

</body>
</html>