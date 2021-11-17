<%@page import="com.yura.yoj.dao.BoardDao"%>
<%@page import="com.yura.yoj.dto.PostDto"%>
<%@page import="com.yura.yoj.status.MemberStatus.LoginStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="write.css">
</head>
<body <jsp:include page="boardMenu.jsp" />>
	<%
		//LoginStatus loginStatus = (LoginStatus) session.getAttribute("loginStatus");
		if (loginStatus == LoginStatus.GUEST || loginStatus == null) {
	%>
	<jsp:forward page="/member/login.jsp">
		<jsp:param value="/PJ_jsp/board/write.jsp" name="referedPage" />
	</jsp:forward>
	<%
		}
		BoardDao boardDao = BoardDao.getInstance();
		String writeType = request.getParameter("writeType");
		String postRef = request.getParameter("postRef");
		System.out.println("글쓰기 타입 : " + writeType + " postRef : " + postRef);
		String action = "writePost.do";
		PostDto postDto = null;

		RequestDispatcher requestDispatcher;
		if (writeType != null)//일단 귀찮아서 조건체크했지만 그냥 글쓰기에도 조건 줄까 고민
			switch (writeType) {
			case "reply":
				action = "writePost.do?writeType=reply";
				break;
			case "modify":
				//postDto = (PostDto) boardDao.selectOne(postRef);
				boardDao.selectOne("post", "postRef", postRef, postDto);
				action = "modifyPost.do";
				break;
			default:
				action = "writePost.do?writeType=normal";
				break;
			}
	%>
	<h3>
		<%
			switch (writeType) {
			case "reply":
				out.print("답글 쓰기");
				break;
			case "modify":
				out.print("수정하기");
				break;
			default:
				out.print("글쓰기");
			}
		%>
	</h3>

	<form action=<%=action%> method="post" id="write">
		제목<input type="text" name="postTitle" id="title"
			<%if (writeType.equals("modify"))
				out.print("value='" + postDto.getPostTitle() + "'");%>>
		카테고리<select name='postCategory' id='category'>
			<%
				if (writeType.equals("reply"))
					out.print("<option value='answer' selected>답변</option>");
				else {
					out.print("<option value='all'>자유</option>" + "<option value='question' selected>질문</option>"
							+ "<option value='correct'>오타/오역/요청</option>");
				}
			%>
		</select> 
		문제 번호<input name="questionNum" id="questionNum"> 
		내용<input type="text" name="postContent" id="content"> 
		언어<select name="lang" id="lang">
			<option value="C">C</option>
			<option value="Java">Java</option>
		</select> 소스 코드<input type="text" id="source">
		<button>
			<%
				switch (writeType) {
				case "reply": out.print("답글 쓰기"); break;
				case "modify": out.print("수정하기"); break;
				default: out.print("글쓰기"); }
			%>
		</button>
		<input type="hidden" name="writeType" value="<%=writeType%>">
		<input type="hidden" name="postWriter" value="<%=session.getAttribute("loginId")%>">
		<%if(writeType.equals("reply")){
			out.print("<input type='hidden' name='refPostNum' value=" + request.getParameter("refPostNum") +">");
			out.print("<input type='hidden' name='postRef' value=" + postRef +">");
		}%>

	</form>

</body>
</html>