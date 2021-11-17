package com.yura.yoj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yura.yoj.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;

	public MemberController(HttpServletRequest req, HttpServletResponse resp) {
	}

	@RequestMapping("/join.do")
	public String join(HttpServletRequest req, HttpServletResponse resp) {
		return "/member/join";
	}
	
	@RequestMapping("/joinAction.do")
	public String joinAction(HttpServletRequest req, HttpServletResponse resp) {
		return memberService.joinAction(req);
	}
	
	
	// RequestMapping을 메소드나 클래스 위에 붙이면 해당 url에 대하여 자동 실행
	// /**/login.do ...?
	@RequestMapping("/login.do")
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		return "/member/login";
	}

	
	//보류
	@RequestMapping("/loginAction.do")
	public String loginAction(HttpServletRequest req, HttpServletResponse resp) {
		return memberService.loginAction(req, resp);
	}

	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest req, HttpServletResponse resp) {
		return memberService.logout(req, resp);
		
	}


	// /changePw.do 도 같이?
	@RequestMapping("/modMember.do")
	public String modify(HttpServletRequest req, HttpServletResponse resp) {
//		return "/";
		return memberService.modMember(req);
	}

//	@RequestMapping("/modMemberAction.do")
//	public String modifyAcion(HttpServletRequest req, HttpServletResponse resp) {
//		return "";
//	}

	@RequestMapping("/delMember.do")
	public String delete(HttpServletRequest req, HttpServletResponse resp) {
		String path = memberService.delMember(req);
		return path;
//		return "delMember.jsp";
	}

//	@RequestMapping("/delMemberAction.do")
//	public String deleteAction(HttpServletRequest req, HttpServletResponse resp) {
//		return "";
//	}
}
