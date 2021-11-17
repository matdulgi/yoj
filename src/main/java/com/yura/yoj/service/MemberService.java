package com.yura.yoj.service;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.yura.yoj.dao.MemberDao;
import com.yura.yoj.dto.DtoFunction;
import com.yura.yoj.dto.MemberDto;
import com.yura.yoj.status.MemberStatus.DelMemberStatus;
import com.yura.yoj.status.MemberStatus.JoinStatus;
import com.yura.yoj.status.MemberStatus.LoginStatus;
import com.yura.yoj.status.MemberStatus.ModifyStatus;

public class MemberService {
	MemberDao memberDao = MemberDao.getMemberDAO();

	public MemberService() {
	}

	public String joinAction(HttpServletRequest req) {
		HttpSession session = req.getSession();
		MemberDto memberDto = new MemberDto();

		System.out.println("JoinHandler process 호출");
		String parameter = null;// request로 넘어온 프로퍼티들
		String value = null;
		String property = null;
		String memberId = req.getParameter("memberID");

		if (memberDao.memberIDCheck(memberId)) {
			req.setAttribute("joinStatus", JoinStatus.DUPLICATEDID);
			System.out.println("중복된 ID");
		}

		DtoFunction.setPropertyFromRequest(req, memberDto);

		// 추가로 날짜 저장
		memberDto.setRegisteredDate(new Date(System.currentTimeMillis()));

		memberDao.insert(memberDto); // VO에 저장된 속성을 db에 저장
		JoinStatus joinStatus = JoinStatus.SUCCESS;
		session.setAttribute("loginId", memberId);
		session.setAttribute("loginStatus", LoginStatus.MEMBER);
		System.out.println("회원 등록 완료");

		return "member/join";
	}

	
	
	public String loginAction(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("\nLoginHandler process 실행");
		String inputId = req.getParameter("inputId");
		String inputPw = req.getParameter("inputPw");
		System.out.println(" - 로그인 프로세스 실행  id : " + inputId + "PW : " + inputPw);
		HttpSession session = req.getSession();
		String loginRefererUri = req.getParameter("loginRefererUri");
//		String path = referer.substring(request.getContextPath().length());

		LoginStatus loginStatus = memberDao.identifyMember(inputId, inputPw);
		session.setAttribute("loginStatus", loginStatus);
		System.out.println("로그인 상태 : " + loginStatus);

		switch (loginStatus) {
		case MANAGER:
			System.out.println("로그인 성공 - " + loginStatus);
			session.setAttribute("loginId", inputId);
			return "/manager/manager.jsp";
		case MEMBER:
			System.out.println("로그인 성공 - " + loginStatus);
			session.setAttribute("loginId", inputId);
			// 수정
			loginStatus = (LoginStatus) req.getAttribute("loginStatus");

//			System.out.println("돌아갈 페이지 : " + loginRefererUri);
			try {
				resp.sendRedirect(loginRefererUri);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		case FAILED:
			System.out.println("로그인 실패 " + loginStatus);
			session.setAttribute("loginStatus", LoginStatus.GUEST);
			req.setAttribute("loginStatus", loginStatus);
//			request.setAttribute("refererUri", loginRefererUri);
//			return "/member/login.jsp";
		}
		return loginRefererUri;
	}

	
	
	public String logout(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		HandlerFunction handlerFunction = HandlerFunction.getInstance();
		session.setAttribute("loginStatus", LoginStatus.GUEST);
		session.removeAttribute("loginId");
//		String referer = request.getHeader("referer");
//		String url = request.getRequestURL().toString().replace(request.getRequestURI(), "");
//		String refererUri= referer.substring(url.length());
		String refererUri = handlerFunction.getRefererUri(req);
		System.out.println("돌아갈 페이지 : " + refererUri);

		System.out.println("로그아웃 완료");
		try {
//			response.sendRedirect("/PJ_jsp/main.do");
			resp.sendRedirect(refererUri);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return "/logout.jsp";
		return "";
	}

	// 요청 파라미터로 dto를 통하여 db 업데이트
	// referer을 검사하여 ChangePwHandler도 같이 수행한다
	public String modMember(HttpServletRequest req) {
		MemberDto memberDto = new MemberDto();
		System.out.println("ModifyHandler process 실행");
		System.out.println("referer : " + req.getHeader("referer"));
		HttpSession session = req.getSession();
		String referer = req.getHeader("referer");

		String memberId = (String) session.getAttribute("loginId");
		String inputPW = req.getParameter("inputPw");

		if (memberDao.checkMemberPw(memberId, inputPW)) {
			DtoFunction.setPropertyFromRequest(req, memberDto);
			memberDao.update(memberDto, memberId);
		} else {
			req.setAttribute("modifyStatus", ModifyStatus.INCORRECTPW);
			System.out.println("잘못된 비밀번호");
		}
		if (referer.contains("changePw")) {
			System.out.println("/setting/changePW.jsp로 이동");
			return "/setting/changePw.jsp";
		} else {
			if (referer.contains("modify") || referer.contains("setting/")) {
				System.out.println("/setting/modify.jsp로 이동");
				return "/setting/modify.jsp";
			} else
				return "/";
		}

	}

	public String changePw(HttpServletRequest req) {
		System.out.println("ChangePwHandler process 실행");
		System.out.println(req.getHeader("referer"));
		MemberDto memberDto = new MemberDto();
		HttpSession session = req.getSession();
		// 현재 비밀번호와 새 비밀번호를 입력하여 비밀번호 업데이트
		String memberId = (String) session.getAttribute("loginId");
		String inputPw = req.getParameter("inputPw");

		if (memberDao.checkMemberPw(memberId, inputPw)) {
			// 비밀번호가 일치하면 dto에 값 저장, dto로 db 업데이트
			DtoFunction.setPropertyFromRequest(req, memberDto);
			memberDao.update(memberDto, memberId);
//			request.setAttribute("changePwStatus", ChangePwStatus.SUCCESS);
		} else {
			System.out.println("비밀번호 불일치");
			req.setAttribute("modifyStatus", ModifyStatus.INCORRECTPW);
		}
		return "changePw.jsp";
	}

	public String delMember(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String memberId = (String) session.getAttribute("loginID");
		String inputPw = req.getParameter("inputPW");

		// PW 일치여부를 확인하여, 일치하면 delete 실행
		if (memberDao.checkMemberPw(memberId, inputPw)) {
			memberDao.delete(memberId);
			// 세션 로그인 상태 변경
			session.removeAttribute("memberId");
			session.setAttribute("loginStatus", LoginStatus.GUEST);
			req.setAttribute("delMemberStatus", DelMemberStatus.SUCCESS);
			System.out.println("회원 삭제 완료");
			return "delMemberProcess.jsp";
		} else
			System.out.println("비밀번호 불일치");
		req.setAttribute("delMemberStatus", DelMemberStatus.INCORRECTPW);
		return "delMember.jsp";
	}

	// 과연 modify 기능들을 다 하나로 합쳐야 하는가?
	public void setLang() {

	}

}
