<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form id=filter>
	<input type="text" placeholder="문제">
	<input type="text" placeholder="아이디">
	<select>
	<option selected>모든 언어</option>
	<option>C++</option>
	<option>C</option>
	<option>Java</option>
	<option>Python</option>
	</select>
	<select>
	<option selected>모든 결과</option>
	<option>맞았습니다!!</option>
	<option>출력 형식이 잘못되었습니다</option>
	<option>틀렸습니다</option>
	</select>
	<button class="searchBtn">검색</button>
</form>
</body>
</html>