package com.yura.yoj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yura.yoj.status.MemberStatus.LoginStatus;

@Controller
public class MainController {
	@RequestMapping(value = {"/main.do", "/"}, method = RequestMethod.GET)
	public String getMain(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if (request.getAttribute("loginStatus") == null)
			request.setAttribute("loginStatus", LoginStatus.GUEST);
			session.setAttribute("loginStatus", LoginStatus.GUEST);

		return "main";
	}
}
