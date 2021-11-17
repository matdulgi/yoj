<%@page import="org.apache.jasper.runtime.PageContextImpl"%>
<%@page import="java.util.List"%>
<%@page import="com.yura.yoj.dto.QuestionDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%--  <jsp:include page="questionListMenu.jsp"/> --%>
<%-- <%@include file="questionMenu.jsp"%> --%>
<c:set var="questionDto" value="${requestScope.questionDto }"/>
<c:set var="timeLimit" value="${questionDto.timeLimit }"/>
<%QuestionDto questionDto = (QuestionDto)request.getAttribute("questionDto"); %>
<c:set var="memoryLimitDigit" value="<%=(int)Math.log10(questionDto.getMemoryLimit()) %>"/>
<fmt:parseNumber var="memoryLimitGrade" integerOnly="true" value="${memoryLimitDigit/3}"/>
<c:choose>
<c:when test="${memoryLimitGrade eq 0}">
<%System.out.println("1번왔오~"); %>
<c:set var="memoryLimit" value="${questionDto.memoryLimit}"/>
<c:set var="memoryUnit" value="B"/>
</c:when>
<c:when test="${memoryLimitGrade eq 1}">
<%System.out.println("2번왔오~"); %>
<fmt:parseNumber var="memoryLimit" integerOnly="true" value="${questionDto.memoryLimit/1000 }"/>
<c:set var="memoryUnit" value="KB"/>
</c:when>
<c:when test="${memoryLimitGrade eq 2}">
<%System.out.println("3번왔오~"); %>
<fmt:parseNumber var="memoryLimit" integerOnly="true" value="${questionDto.memoryLimit/1000000 }"/>
<c:set var="memoryUnit" value="MB"/>
</c:when>
</c:choose>
  	<div class="ad"></div>
	<div id="question">
	<h2 id="questtitle">${questionDto.questionTitle}</h2>
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
				<td>${questionDto.timeLimit}<span>초</span></td>
				<td><span>${memoryLimit}${memoryUnit}</span></td>
				<td>${questionDto.submitCnt}</td>
				<td>${questionDto.solveCnt}</td>
				<td>${questionDto.solverCnt}</td>
				<td>${questionDto.solveRatio}</td>
			</tr>
		</tbody>
	</table>
	<h3 class="head">
		<span>${questionDto.questionTitle}</span>
	</h3>
	<p>${questionDto.questionContent}</p>
	<h3 class="head">
		<span>입력</span>
	</h3>
	<p>${questionDto.inputCondition}</p>
	<h3 class="head">
		<span>출력</span>
	</h3>
	<p>${questionDto.outputCondition}</p>

	<c:set var="inputExList" value="${questionDto.inputExList}"/>
	<c:set var="outputExList" value="${questionDto.outputExList}"/>
 
	
	<c:forEach var="i" begin="1" end="${fn:length(questionDto.inputExList)}" step="1">
			<h3 class='head'>
			<span>예제 입력 ${i}</span>
			</h3>
			<div class='inputEx'>${inputExList[i-1]}</div>
			<%System.out.println(pageContext.getAttribute("inputExList")); %>
			<%System.out.println(pageContext.getAttribute("outputExList")); %>
			<h3 class='head'>
			<span>예제 출력 ${i}</span>
			</h3>
			<div class='outputEx'>${outputExList[i-1]}</div>
			</div>
	</c:forEach>
 </div>
	<div class="ad"></div>
</body>
</html>