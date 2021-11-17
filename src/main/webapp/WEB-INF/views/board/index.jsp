<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:forward page="board.do">
<jsp:param name="boardCategory" value="all"/>
<jsp:param name="pageNum" value="1"/>
</jsp:forward>
</body>
</html>