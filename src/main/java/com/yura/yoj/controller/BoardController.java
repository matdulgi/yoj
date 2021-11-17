package com.yura.yoj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BoardController {

	public BoardController() {
	}


	@RequestMapping("/board.do")
	public String board(HttpServletRequest req, HttpServletResponse resp) {
		return "board/board";
	}

	@RequestMapping("/viewPost.do")
	public String viewPost(HttpServletRequest req, HttpServletResponse resp) {
		return "";
	}

	@RequestMapping("/write.do")
	public String write(HttpServletRequest req, HttpServletResponse resp) {
		return "";
	}

	@RequestMapping("/modify.do")
	public String modify(HttpServletRequest req, HttpServletResponse resp) {
		return "";
	}

	@RequestMapping("/delete.do")
	public String delete(HttpServletRequest req, HttpServletResponse resp) {
		return "";
	}




}
