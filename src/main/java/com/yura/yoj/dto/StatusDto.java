package com.yura.yoj.dto;

import java.sql.Timestamp;

public class StatusDto {

	int submitNum;
	String memberId;
	int questionNum;
	int result;
	int allocMemory;
	int compileTime;
	String lang;
	int codeLength;
	Timestamp timestamp;
	
	public int getSubmitNum() {
		return submitNum;
	}
	public void setSubmitNum(int submitNum) {
		this.submitNum = submitNum;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getAllocMemory() {
		return allocMemory;
	}
	public void setAllocMemory(int allocMemory) {
		this.allocMemory = allocMemory;
	}
	public int getCompileTime() {
		return compileTime;
	}
	public void setCompileTime(int compileTime) {
		this.compileTime = compileTime;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public int getCodeLength() {
		return codeLength;
	}
	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
