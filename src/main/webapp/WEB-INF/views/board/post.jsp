<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.yura.yoj.dto.PostDto" %>
<%@ page import="java.sql.Timestamp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="post.css">
</head>
<body>
<%
PostDto postDto = (PostDto)request.getAttribute("postDto");
int postNum = postDto.getPostNum();
int postRef = postDto.getPostRef();
int postRefStep = postDto.getPostRef_step();
String title = postDto.getPostTitle();
String postWriter = postDto.getPostWriter();
String content = postDto.getPostContent();
int readCnt = postDto.getReadCnt();
int like = postDto.getLikeCnt();
Timestamp regDate = postDto.getPostRegDate();
//int pageNum = (int)request.getAttribute("pageNum");
int pageNum = Integer.parseInt(request.getParameter("pageNum"));
String boardCategory = request.getParameter("boardCategory");


//String loginId = (String)session.getAttribute("loginId");

out.print("<h3>" + title +"</h3>");
%>
<p><span>조회수 : <%=readCnt %></span></p>
<div><%=content %></div>
<a href="write.jsp?writeType=reply&refPostNum=<%=postNum%>" class="button">답글 쓰기</a>
<%
if(loginId != null && loginId.equals(postWriter)){
	out.print("<a href='modifyPost.do?writetype=modify&postRef='" + postRef + "&boardCategory="+boardCategory+" class='button'>글 수정</a>");
	out.print("<a href='deletePost.do?postNum=" + postNum + "&boardCategory="+boardCategory+"&pageNum="+pageNum+"&postRef="+postRef+"&postRefStep="+postRefStep+"' class='button'>글 삭제</a>");
}
 %>
</body>
</html>