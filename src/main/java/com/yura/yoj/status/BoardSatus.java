package com.yura.yoj.status;

public class BoardSatus {
	public enum String{
//		INFORMATION("information"), QUESTION("question"), CORRECT("correct");
		INFORMATION, QUESTION, CORRECT;
		
		//값을 가지려면 생성자 선언
		//enum은 인스턴스가 하나 뿐인 싱글톤같은 개념이어서 기본생성자가 자동으로 private 처리된다
		private String category;
		private String() {}
//		private PostCategory(String category) {
//			this.category = category;
//		}

//		public static PostCategory getCategory(String postCategory) {
//			switch(postCategory) {
//			case "information":return INFORMATION;
//			case "question":return QUESTION;
//			case "correct":return CORRECT;
//			default:return null;
//			}
//		}
		
	}
}
