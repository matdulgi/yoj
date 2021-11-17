package com.yura.yoj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yura.yoj.service.QuestionService;

@Controller
public class QuestionContoller {

	@Autowired
	QuestionService questionService;


	public QuestionContoller() { }

	@RequestMapping("/question.do")
	public String question(HttpServletRequest req, HttpServletResponse resp) {
		return questionService.questionList(req);
	}
	@RequestMapping("/addQues.do")
	public String add(HttpServletRequest req, HttpServletResponse resp) {
		questionService.addQuestion();
		return "";
	}
	@RequestMapping("/delQues.do")
	public String delete(HttpServletRequest req, HttpServletResponse resp) {
		questionService.delQuestion();
		return "";
	}
	@RequestMapping("/modQues.do")
	public String modify(HttpServletRequest req, HttpServletResponse resp) {
		questionService.questionList(req);
		return "";
	}
	@RequestMapping("/viewQues.do")
	public String viewQuestion(HttpServletRequest req, HttpServletResponse resp) {
		return questionService.viewQuestion(req);
	}
	@RequestMapping("/submit.do")
	public String submit(HttpServletRequest req, HttpServletResponse resp) {
		return questionService.submit(req);
	}
	
}
