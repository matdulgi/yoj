package com.yura.yoj.sql;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yura.yoj.dto.DtoFunction;

public class BoardSql extends CommonSql {

	public static BoardSql boardSql = new BoardSql();

	private BoardSql() {
	};

	public static BoardSql getInstance() {
		return boardSql;
	}

	// 프로퍼티부는 컬럼 이름, 값은 ? 와일드카드로 처리하여 PreparedStatement로 처리
	// 값이 존재하는 프로퍼티 숫자만큼만 가져온다
	// 그냥 모든 칼럼을 다 가져오고, 값이 없는건 null처리 해도 될거같다
	public String getInsertSql(Object postDto) {// 일단 PostDto에서 Object로 바꿈..
		System.out.println("BoardSql getInsertSql 실행");
		List<String> postPropertyList = DtoFunction.getValidPropertyList(postDto);
		List<Object> postPropertyValueList = DtoFunction.getValidPropertyValueList(postDto);

		Iterator<String> postPropertyItr = postPropertyList.iterator();
		Iterator<Object> postPropertyValueItr = postPropertyValueList.iterator();

		String property = "";
		String columnPart = "";
		String valuePart = "";

		while (postPropertyItr.hasNext()) {
			property = postPropertyItr.next();
			columnPart = columnPart + property;
			valuePart = valuePart + "? ";
			if (postPropertyItr.hasNext()) {
				columnPart = columnPart + ", ";
				valuePart = valuePart + ", ";
			}
		}

		String sql = "insert into post (" + columnPart + ") values (" + valuePart + ")";
		System.out.println("완성된 sql : " + sql);
		return sql;
	}

	//질문 게시판에는 답변도 출력. 근데 이걸 여기서 처리하는게 맞는지는 모르겠다
//	public String getBoardSql(String category) {
//		System.out.println("\nBoardSql getBoardSql(String) 실행");
////		select * from postview where postCategory = ? and rnum between ? and ?;
//		String additional = "";
//		String sql = "select * from " + 
//			"(select rownum rnum, p.*" + 
//			"from (" +
//				"select * from post " +
//				"where ? = 'information'" +
//				"order by postref desc, postref_step " +
//				") p" +
//	        ")" +
//	        "where rnum between ? and ?";
//		switch (category) {
//		case "question":
//			additional = " or postCategory = 'answer'";// 일단 하드코딩..
//		}
//
//		sql = sql + additional;
//		System.out.println("완성된 sql : " + sql);
//		return sql;
//	}

	// 문제점. 현재 dto에서 유효한 값을 확인할 때 null이나 ""체크를 하고 있다
	// dto의 기본타입은 자동으로 0으로 초기화돼서 자칫하면 값이 0으로 초기화될 우려가 있다
	// 방법 1. 0인 값 제외... 0으로 초기화해야 하는 값은 따로 처리
	// 방법 2. dto 초기화시 처리...
	// 방법 2-1. dto를 생성할 때, 값을 대입하지 않은 항목을 배열로 처리.
	// => dto Function을 통해서 객체를 생성하기. 시도
	// 방법 2-2. 자동으로 처리된 값만 분류하는 방법이 있을까?
	// 결론 - update문은 처음 가져온 값이 그대로 들어가도 상관 없다..
	public String getUpdateSql(Object dto, String conditionColumn, Object conditionValue) {
		// update 테이블명 set col1=val1, col2=val2, ... where con=conval
		System.out.println("\nBoardSql getUpdateSql 메서드 실행");
		String property;
		String propertyPart = "";
		String conditionPart = conditionColumn + " = " + conditionValue;
//		PostDto postDto = (PostDto) dto;

		Map<String, Object> propertyMap = DtoFunction.getValidPropertyMap(dto);
		Iterator<String> propertyItr = propertyMap.keySet().iterator();

		while (propertyItr.hasNext()) {
			property = propertyItr.next();

			// 예약어 체크?? prepareStatement도 예약어를 체크하나...?
//			if (oracleReservedWordSet.contains(property)) {
//				property = "\"" + property.toUpperCase() + "\"";
//			}
			propertyPart = propertyPart + property + " = ? ";
			if (propertyItr.hasNext())
				propertyPart = propertyPart + ", ";
		}

		String query = "update post set " + propertyPart + " where " + conditionPart;
		System.out.println("완성된 sql문 : " + query);
		return query;
	}

}
