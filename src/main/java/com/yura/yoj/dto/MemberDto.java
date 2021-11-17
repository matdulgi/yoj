package com.yura.yoj.dto;

import java.sql.Date;

public class MemberDto {
	private String memberId = null;
	private String memberPw = null;
	private String message = null;
	private String group = null;
	private String email = null;
	private Date registeredDate;// = new Date(System.currentTimeMillis());
	
	public String getMemberId() {
		return memberId;
	}
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getMemberPw() {
		return memberPw;
	}
	
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
}
