package com.yura.yoj.service;

import com.yura.yoj.dao.BoardDao;
import com.yura.yoj.dto.DtoFunction;
import com.yura.yoj.dto.PostDto;
import com.yura.yoj.status.MemberStatus.LoginStatus;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class BoardService {
	BoardDao boardDao = BoardDao.getInstance();
	
	public BoardService() { }
	
	
	@RequestMapping("/board/board.do")
	public String board(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BoardHandler process 실행");
		List<PostDto> postList;
		int maxPageNum;
		String boardCategory = request.getParameter("boardCategory");
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		System.out.println("게시판 category : " + boardCategory);

		if (boardCategory.equals("all")) {
			postList = (ArrayList<PostDto>) boardDao.getBoard(pageNum);
			maxPageNum = boardDao.getPostCount() / 10 + 1;
			System.out.println("현재 페이지 : " + pageNum + "마지막 페이지 : " + maxPageNum);
		} else {
			postList = boardDao.getBoard(boardCategory, pageNum);
			maxPageNum = boardDao.getPostCount(boardCategory) / 10 + 1;
			System.out.println("현재 페이지 : " + pageNum + "마지막 페이지 : " + maxPageNum);
		}
		for (PostDto postDto : postList) {
			DtoFunction.printDtoInfo(postDto);
		}
		request.setAttribute("postList", postList);
		request.setAttribute("maxPageNum", maxPageNum);
		return "/board/board.jsp";
	}
	
	
	@RequestMapping("/board/delPost.do")
	public String deletePost(HttpServletRequest request, HttpServletResponse response) {
		String boardCategory = request.getParameter("boardCategory");
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int postNum=Integer.parseInt(request.getParameter("postNum"));

		//postNum 으로 조회하여 삭제
		BoardDao boardDao = BoardDao.getInstance();
		boardDao.delete("post", "postNum", postNum);

		//최종답글 삭제시 step다시 다 당기기
		int postRef = Integer.parseInt(request.getParameter("postRef"));
		int postRefStep = Integer.parseInt(request.getParameter("postRefStep"));
		boardDao.pullRefStep(postRef, postRefStep);
		
		//만약 하위 트리가 있는 답글 삭제시 다 같이 삭제
		
//		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/board/board.do?boardCategoty="+boardCategory+"&pageNum"+pageNum);
		try {
			response.sendRedirect("/PJ_jsp/board/board.do?boardCategory="+boardCategory+"&pageNum="+pageNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "";
	}
	
	
	@RequestMapping("board/modPost.do")
	public String modifyPost(HttpServletRequest request, HttpServletResponse response) {
		String boardCategory = "";
		int postNum=Integer.parseInt(request.getParameter("postNum"));
		
		return "";
	}
	

	@RequestMapping("board/viewPost.do")
	public String viewPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("\nViewPostHandler 실행");
		BoardDao boardDao = BoardDao.getInstance();
//		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int postNum = Integer.parseInt(request.getParameter("postNum"));
		PostDto postDto = boardDao.selectOne(postNum);
		postDto.setReadCnt(postDto.getReadCnt() + 1);
//		boardDao.addReadCnt(postDto);
		boardDao.update(postDto, "postNum", postNum);

		request.setAttribute("postDto", postDto);
//		request.setAttribute("pageNum", pageNum);
		return "post.jsp";
	}

	
	@RequestMapping("board/writePost.do")
	public String writePost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("\nWriteBoardHandler 실행");
		HttpSession session = request.getSession();
		LoginStatus loginStatus = (LoginStatus) session.getAttribute("loginStatus");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		//guest는 글쓰기 금지
		if (loginStatus == LoginStatus.GUEST || loginStatus == null) {
			return "/member/login.jsp";
		}

		String writeType = request.getParameter("writeType");

		// 새 게시글 생성하여 기본정보 등록. db조회가 필요한 글 번호는 핸들러에서 처리
		PostDto postDto = new PostDto();
		DtoFunction.setPropertyFromRequest(request, postDto);
		postDto.setPostNum(boardDao.selectMaxValue("postNum", "post") + 1);
		postDto.setPostRegDate(timestamp);

		if (writeType.equals("reply")) {// 답글 작성
			int refPostNum = Integer.parseInt(request.getParameter("refPostNum"));
			PostDto refPost = boardDao.selectOne(refPostNum);// 원글
			int postRef = refPost.getPostRef();
			int refPostRefLevel = refPost.getPostRef_level();

			postDto.setPostRef(postRef);//답글이면 ref 맞추고 레벨은 1올림
			postDto.setPostRef_level(refPostRefLevel + 1);

			if (refPostRefLevel > 0) {// 다답글일 경우
				int refPostRefCnt = boardDao.getPostRefCnt(refPostNum, refPostRefLevel);
				int refPostRefStep = refPost.getPostRef_step();
				int newPostRefStep = refPostRefStep + refPostRefCnt + 1;
				//자식댓그이 있ㄷ을 때와 없을 때를 굳이 구분할 이유는 없음
				//자식 답글이 없으면 어차피 카운트가 0으로 나오기 때문에  분기를 나눌 필요가 없다
				//기본적으로 답글을 달면 참조 글의 step+ 자식 답글 수+ 1 증가
				boardDao.pushRefStep(postRef, newPostRefStep);
				postDto.setPostRef_step(newPostRefStep);
			} else {
				int lastRefStep = boardDao.selectMaxValue("postRef_Step", "post", "postRef", postRef);
				postDto.setPostRef_step(lastRefStep + 1);
				//답글의 위치는 한칸 밑이 아닌 자식 글의 제일 뒤이다. 자식 답글 체크하기
			}
		} else { // 답글이 아닐 경우
			postDto.setPostRef(postDto.getPostNum());
		}

		boardDao.insert(postDto);
		request.setAttribute("postDto", postDto);
		return "post.jsp";
	}

}
