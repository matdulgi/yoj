<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" import="com.yura.yoj.dto.PostDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="shortcut icon" href="../sources/tabIcon.png">

<script src="/PJ_jsp/common/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="/PJ_jsp/common/common.css">
<link rel="stylesheet" type="text/css" href="board.css">

</head>
<body>
<div class="ad"></div>
<table class="boardtable">
	<thead>
		<tr><th class="postTitle">제목<th class="category">카테고리</th><th class="userID">글쓴이</th><th class="comment">댓글</th><th class="like"><img src="../sources/like.png" width=20px;></th><th class="date" colspan="2">작성일</th></tr>
	</thead>
	<tbody class="tablebody">
<% List<PostDto> postList = (ArrayList<PostDto>)request.getAttribute("postList");
int postNum;
String postTitle;
String postCategory;
String postWriter;
int likeCnt;
int commentCnt;
int postRef_level;
Timestamp timestamp;
Iterator<PostDto> postItr = postList.iterator();
String boardCategory = request.getParameter("boardCategory");
//int pageNum = (int)request.getAttribute("pageNum");
int pageNum = Integer.parseInt(request.getParameter("pageNum"));
System.out.println("현재 카테고리 : " + boardCategory + " 페이지 번호 : " + pageNum);

for(PostDto post: postList){
	postNum = post.getPostNum();
	postTitle = post.getPostTitle();
	switch(post.getPostCategory()){
	case "information" : postCategory = "공지"; break;
	case "correct" : postCategory = "오타/오역/요청"; break;
	case "question" : postCategory = "질문"; break;
	case "free" : postCategory = "자유"; break;
	default : postCategory = "기타";
	};
	postWriter = post.getPostWriter();
	likeCnt = post.getLikeCnt();
	commentCnt = post.getCommentCnt();
	timestamp = post.getPostRegDate();
	postRef_level = post.getPostRef_level();
	if(postRef_level>0) postTitle = "└" + postTitle;
	for(int i = 1; i<postRef_level; i++){
	postTitle = "　	" + postTitle;
	}

	out.print("<tr><td><a href='viewPost.do?boardCategory=" + boardCategory + "&pageNum=" + pageNum + "&postNum="+postNum+"'>" + postTitle + "</a></td>");
	out.print("<td>" + postCategory + "</td>");
	out.print("<td>" + postWriter + "</td>");
	out.print("<td>" + likeCnt + "</td>");
	out.print("<td>" + commentCnt + "</td>");
	out.print("<td>" + timestamp + "</td></tr>");
}
%>
</table>

<p><a href="#">	&lt;</a>
<% int maxPageNum = (int)request.getAttribute("maxPageNum");
for(int i = 1; i<= maxPageNum; i++){
	out.print("<a class='pageNum' href='board.do?boardCategory=" + boardCategory + "&pageNum=" + i + "'>" + i + "</a>");
}
%>

<a href="#">&gt;</a></p>
<div class="ad"></div>
</body>
</html>