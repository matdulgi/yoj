<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채점 현황</title>
<link rel="shortcut icon" href="../sources/tabIcon.png">
<script src="/PJ_jsp/common/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="/PJ_jsp/common/common.css">
<link rel="stylesheet" type="text/css" href="/PJ_jsp/status/status.css">
</head>
<body>
<%@include file="statusFilter.jsp" %>
<table class="boardtable">
	<thead>
		<tr><th>제출 번호</th><th>아이디</th><th>문제</th><th>결과</th><th>메모리</th><th>시간</th><th>언어</th><th>코드 길이</th><th>제출한 시간</th></tr>
	</thead>
	<tbody>
	
<!-- 		<tr><td>1682</td><td><a href="#">jinji001</a></td><td><a href="#">9153</a></td><td>맞았습니다!!</td><td>12852<span class="point">KB</span></td><td>124<span class="point">ms</span></td><td>C++</td><td>1289<span class="point">B</span><td><span class="time">1분 전</span></td></tr>
		<tr><td>1681</td><td><a href="#">jinji001</a></td><td><a href="#">9153</a></td><td>틀렸습니다</td><td><span class="point"></span></td><td><span class="point"></span></td><td>C++</td><td>2053<span class="point">B</span><td><span class="time">1분 전</span></td></tr>
		<tr><td>1680</td><td><a href="#">haha</a></td><td><a href="#">15123</a></td><td>틀렸습니다<td><span class="point"></span></td><td><span class="point"></span></td><td>Java</td><td>993<span class="point">B</span><td><span class="time">2분 전</span></td></tr>
		<tr><td>1679</td><td><a href="#">minibite</a></td><td><a href="#">7123</a></td><td>맞았습니다!!</td><td>14272<span class="point">KB</span></td><td>544<span class="point">ms</span></td><td>C++</td><td>1432<span class="point">B</span><td><span class="time">2분 전</span></td></tr>
		<tr><td>1678</td><td><a href="#">kelog129</a></td><td><a href="#">5123</a></td><td>틀렸습니다</td><td><span class="point"></span></td><td><span class="point"></span></td><td>Python</td><td>168<span class="point">B</span><td><span class="time">2분 전</span></td></tr>
		<tr><td>1677</td><td><a href="#">jinji001</a></td><td><a href="#">1005</a></td><td>틀렸습니다</td><td><span class="point"></span></td><td><span class="point"></span></td><td>Python</td><td>586<span class="point">B</span><td><span class="time">2분 전</span></td></tr>
		<tr><td>1676</td><td><a href="#">limlim</a></td><td><a href="#">7123</a></td><td>배고픕니다</td><td><span class="point"></span></td><td><span class="point"></span></td><td>PyPy</td><td>1333<span class="point">B</span><td><span class="time">3분 전</span></td></tr>
		<tr><td>1675</td><td><a href="#">helloworld</a></td><td><a href="#">9123</a></td><td>맞았습니다!!</td><td>7724<span class="point">KB</span></td><td>202<span class="point">ms</span></td><td>Java</td><td>143<span class="point">B</span><td><span class="time">3분 전</span></td></tr> -->	
 </tbody>
</table>
<div class="ad"></div>
</body>
</html>