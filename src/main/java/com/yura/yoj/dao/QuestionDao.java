package com.yura.yoj.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yura.yoj.dto.DtoFunction;
import com.yura.yoj.dto.QuestionDto;

public class QuestionDao extends CommonDao {

	private static QuestionDao questionDao = new QuestionDao();

	private QuestionDao() {
	}

	public static QuestionDao getInstance() {
		return questionDao;
	}

	public List<QuestionDto> getQuestionList() {
		List<QuestionDto> questionList = new ArrayList<>();
		String sql = "select * from questionView";// 임시
		int questionNum = 0;
		int nextQuestionNum = 0;
		QuestionDto questionDto = null;
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			// dto의 프로퍼티가 Map이면 어떻게 초기화를 시킬 것인가
			// 각 요소는 String 형태로 db의 여러 로우에 걸쳐 저장
			// dto 하나에 로우를 반복 저장
			// 방법 1. num을 체크하여 add여부 결정
			// - 다음 num이랑 현재 num이 같으면 다시 추가...
			// - 근데 다음 num이랑 현재 num을 어떻게 비교할까
			// - 아니면 next 넘어가서
			// 방법 2. 한번에 Map 생성...
			// 방법 3. 커서 조작하기
			while (rs.next()) {
				if(questionDto==null) {
				questionDto = new QuestionDto();
				}
				DtoFunction.setDtoWithResultSetRow(questionDto, rs);
				questionNum = questionDto.getQuestionNum();
				if (rs.next()) {
					nextQuestionNum = rs.getInt("questionNum");
					if (questionNum != nextQuestionNum) {
						questionList.add(questionDto);
						questionDto = null;
					}
					rs.previous();
				} else {
					questionList.add(questionDto);
					questionDto = null;
				}
			}
		} catch ( SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return questionList;
	}
	
	public QuestionDto viewQuestion(int questionNum) {
//		List<QuestionDto> questionList = new ArrayList<>();
		String sql = "select * from questionView where questionNum = ?";// 임시
//		int nextQuestionNum = 0;
//		QuestionDto questionDto = null;
		QuestionDto questionDto = new QuestionDto();
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setInt(1, questionNum);
			rs = pstmt.executeQuery();

			while (rs.next()) {
//				if(questionDto==null) {
//				questionDto = new QuestionDto();
//				}
				DtoFunction.setDtoWithResultSetRow(questionDto, rs);
//				questionNum = questionDto.getQuestionNum();
//				if (rs.next()) {
//					nextQuestionNum = rs.getInt("questionNum");
//					if (questionNum != nextQuestionNum) {
//						questionList.add(questionDto);
//						questionDto = null;
//					}
//					rs.previous();
//				} else {
//					questionList.add(questionDto);
//					questionDto = null;
//				}
			}
		} catch ( SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return questionDto;
	}

}