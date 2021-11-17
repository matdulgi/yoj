package com.yura.yoj.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionDto {
    private int questionNum;//게시글 번호
    private String questionTitle;//제목
	private String questionContent;//내용
	private String inputCondition;
	private String outputCondition;
	
	private int timeLimit;
	private int memoryLimit;
	private int submitCnt;
	private int solveCnt;
	private int solverCnt;
	private float solveRatio;

	private ArrayList<String> inputExList = new ArrayList<>();
	private ArrayList<String> outputExList = new ArrayList<>();

//    private Timestamp questionRegDate;//등록일


	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public String getInputCondition() {
		return inputCondition;
	}
	public void setInputCondition(String inputCondition) {
		this.inputCondition = inputCondition;
	}
	public String getOutputCondition() {
		return outputCondition;
	}
	public void setOutputCondition(String outputCondition) {
		this.outputCondition = outputCondition;
	}
	public int getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	public int getMemoryLimit() {
		return memoryLimit;
	}
	public void setMemoryLimit(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}
	public int getSubmitCnt() {
		return submitCnt;
	}
	public void setSubmitCnt(int submitCnt) {
		this.submitCnt = submitCnt;
	}
	public int getSolveCnt() {
		return solveCnt;
	}
	public void setSolveCnt(int solveCnt) {
		this.solveCnt = solveCnt;
	}
	public int getSolverCnt() {
		return solverCnt;
	}
	public void setSolverCnt(int solverCnt) {
		this.solverCnt = solverCnt;
	}
	public float getSolveRatio() {
		return solveRatio;
	}
	public void setSolveRatio(float solveRatio) {
		this.solveRatio = solveRatio;
	}
	public ArrayList<String> getInputExList() {
		return inputExList;
	}
	public void setInputExList(String inputEx) {
//	public void setInputExList(ArrayList<String> inputExList) {
		this.inputExList.add(inputEx);
//		this.inputExList = inputExList;
	}
	public ArrayList<String> getOutputExList() {
		return outputExList;
	}
	public void setOutputExList(String outputEx) {
//	public void setOutputExList(ArrayList<String> outputExList) {
		this.outputExList.add(outputEx);
//		this.outputExList = outputExList;
	}


}