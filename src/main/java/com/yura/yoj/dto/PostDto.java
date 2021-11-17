package com.yura.yoj.dto;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class PostDto {
    private int postNum;//게시글 번호
    private String postWriter;//작성자
    private String postCategory;//분류
    private String postTitle;//제목
	private String postContent;//내용
    private int readCnt;//조회수
    private int likeCnt;
    private int commentCnt;//댓글수
    private Timestamp postRegDate;//등록일
    private int questionNum;//문제 번호
    private int postRef;//페이지 내 게시글번호?
    private int postRef_step;//페이지번호
    private int postRef_level;//답글레벨
//    public Map<String, Object> settedPropertyMap = new HashMap<>();

	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}
	public String getPostWriter() {
		return postWriter;
	}
	public void setPostWriter(String postWriter) {
		this.postWriter = postWriter;
//		System.out.println("PostDto setPostWriter 실행");
//		settedPropertyMap.put("postWriter", postWriter);
	}
	public String getPostCategory() {
		return postCategory;
	}
	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
//		System.out.println("PostDto setPostCategory 실행");
//		settedPropertyMap.put("postCategory", postCategory);
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
//		System.out.println("PostDto setPostTitle 실행");
//		settedPropertyMap.put("postTitle", postTitle);
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
//		System.out.println("PostDto setPostContent 실행");
//		settedPropertyMap.put("postContent", postContent);
	}
	public int getReadCnt() {
		return readCnt;
	}
	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
//		System.out.println("PostDto setReadCnt 실행");
//		settedPropertyMap.put("readCnt", readCnt);
	}
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
//		System.out.println("PostDto setLikeCnt 실행");
//		settedPropertyMap.put("readCnt", readCnt);
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
//		System.out.println("PostDto setCommentCnt 실행");
//		settedPropertyMap.put("commentCnt", commentCnt);
	}
	public int getPostRef() {
		return postRef;
	}
	public void setPostRef(int postRef) {
		this.postRef = postRef;
//		System.out.println("PostDto setPostRef 실행");
//		settedPropertyMap.put("postRef", postRef);
	}
	public int getPostRef_step() {
		return postRef_step;
	}
	public void setPostRef_step(int postRef_step) {
		this.postRef_step = postRef_step;
//		System.out.println("PostDto setPostRef_step 실행");
//		settedPropertyMap.put("postRef_step", postRef_step);
	}
	public int getPostRef_level() {
		return postRef_level;
	}
	public void setPostRef_level(int postRef_level) {
		this.postRef_level = postRef_level;
//		System.out.println("PostDto setPostRef_level 실행");
//		settedPropertyMap.put("postRef_level", postRef_level);
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
//		System.out.println("PostDto setQuestionNum 실행");
//		settedPropertyMap.put("questionNum", questionNum);
	}
	public Timestamp getPostRegDate() {
		return postRegDate;
	}
	public void setPostRegDate(Timestamp postRegDate) {
		this.postRegDate = postRegDate;
//		System.out.println("PostDto setPostRegDate 실행");
//		settedPropertyMap.put("postRegDate", postRegDate);
	}
}