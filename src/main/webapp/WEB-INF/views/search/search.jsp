<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="../sources/tabIcon.png">

<link rel="stylesheet" type="text/css" href="/PJ_jsp/common/common.css">
<link rel="stylesheet" type="text/css" href="/PJ_jsp/search/search.css">
<script src="/PJ_jsp/common/jquery.js"></script>
</head>
<body>
<script>
$(document).ready(function(){
	$("#header").load("/PJ_jsp/common/header.jsp");
	$("#footer").load("/PJ_jsp/common/footer.jsp");
	$(".ad").load("/PJ_jsp/ad/ad1.jsp");
});
</script>
<header id="header"></header>
<div class="ad"></div>
<div class="ad"></div>
<footer id="footer"></footer>
</body>
</html>