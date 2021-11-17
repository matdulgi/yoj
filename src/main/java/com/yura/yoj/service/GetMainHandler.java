package com.yura.yoj.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yura.yoj.status.MemberStatus.LoginStatus;

//@Service
//@Log4j
//@Controller
//public class GetMainHandler {
//	@RequestMapping(value = {"/main.do", "/"}, method = RequestMethod.GET) 
//	public String service(HttpServletRequest request, HttpServletResponse response) {
//		if(request.getAttribute("loginStatus")==null)
//			request.setAttribute("loginStatus", LoginStatus.GUEST);
//		
//		
//		return "main";
//	}
//
//}
