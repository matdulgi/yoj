<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.yura.yoj.dto.QuestionDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="../sources/tabIcon.png">

<script src="/PJ_jsp/common/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="/PJ_jsp/common/common.css">
<link rel="stylesheet" type="text/css" href="questionList.css">
</head>
<body class=table>
<%
int questionNum;
String questionTitle;
String questionContent;
int solverCnt;
int solveCnt;
float solveRatio;

List<QuestionDto> questionList = (ArrayList<QuestionDto>)request.getAttribute("questionList");
for(QuestionDto questionDto : questionList){
//게시판 출력 목록 : 문제번호, 문제제목, 맞은 사람, 정답, 정답 비율
questionNum = questionDto.getQuestionNum();
questionTitle = questionDto.getQuestionTitle();
solverCnt = questionDto.getSolverCnt();
solveCnt = questionDto.getSolveCnt();
solveRatio = questionDto.getSolveRatio();
%>

<header id="header"></header>
<div class="ad"></div>
<div id="div"></div>
<%-- <jsp:include page="questionListMenu.jsp"/> --%>
<table class="boardtable">
	<thead>
		<tr><th class="defaultCell">문제</th><th id=questTitle>제목</th><th class="defaultCell">맞은 사람</th><th class="defaultCell">제출</th><th class="defaultCell">정답 비율</th></tr>
	</thead>
	<tbody class="tablebody">
	<%out.print("<tr><td>"+questionNum+"</td>"
			+"<td><a href='viewQuestion.do?questionNum="+questionNum+"'>"+questionTitle+"</a></td>"
			+"<td>"+solverCnt+"</td>"
			+"<td>"+solveCnt+"</td>"
			+"<td>"+solveRatio+"</td></tr>");
}
	%>
<!-- 		<tr><td>1000</td><td><a href="question_A+B.html" class="questTitle">A+B</a></td><td>0</td><td>0</td><td>0</td></tr>
		<tr><td>1001</td><td><a href="question_A-B.html" class="questTitle">A-B</a></td><td>0</td><td>0</td><td>0</td></tr>
		<tr><td>1002</td><td><a href="#" class="questTitle">터렛</a></td><td>0</td><td>0</td><td>0</td></tr>
		<tr><td>1003</td><td><a href="#" class="questTitle">파보나치 함수</a></td><td>0</td><td>0</td><td>0</td></tr>
		<tr><td>1004</td><td><a href="#" class="questTitle">어린 왕자</a></td><td>0</td><td>0</td><td>0</td></tr>
		<tr><td>1005</td><td><a href="#" class="questTitle">ACM Craft</a></td><td>0</td><td>0</td><td>0</td></tr>
		<tr><td>1006</td><td><a href="#" class="questTitle">습격자 초라기</a></td><td>0</td><td>0</td><td>0</td></tr>
		<tr><td>1007</td><td><a href="#" class="questTitle">벡터 매칭</a></td><td>0</td><td>0</td><td>0</td></tr> 	
		<tr><td></td><td><a href="#" class="questTitle">습격자 초라기</a></td><td>0</td><td>0</td><td>0</td></tr>-->
</tbody>
</table>
<div class="ad"></div>
</body>
</html>