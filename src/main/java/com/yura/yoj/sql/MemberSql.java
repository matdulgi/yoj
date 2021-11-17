package com.yura.yoj.sql;

import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.yura.yoj.dto.DtoFunction;

public class MemberSql extends CommonSql{
	private Set<String> oracleReservedWordSet = new HashSet<>();

	private static MemberSql memberSql = new MemberSql();

	private MemberSql() {
		oracleReservedWordSet.add("group");
		oracleReservedWordSet.add("groups");
	}

	public static MemberSql getInstance() {
		return memberSql;
	}


	//테이블 이름을 받아와서 칼럼세 ㅅ조회..? 이상한데
	//그럴거면 칼럼 셋을 받는게 낫지..
	public String getInsertMemberSQL(Set<String> columnSet) {
		String column = "";
		String columns = "";
		String values = "";
		int count = 0;

		Iterator<String> colomsItr = columnSet.iterator();
		while (colomsItr.hasNext()) {
			column = colomsItr.next();
			if (column.equals("GROUP"))// 오라클 예약어 체크. 추후에 enum으로 처리하고싶다
				column = "\"GROUP\"";
			if (count == 0)
				columns = columns + column;
			else {
				columns = columns + ", " + column;
			}
			count++;
		}
		for (int i = 0; i < count; i++) {
			if (i == 0)
				values = values + "?";
			else
				values = values + ", ?";
		}

		String query = "insert into member(" + columns + ") values(" + values + ")";
		System.out.println("완성된 쿼리 : " + query);
		return query;
	}

	public String getCheckMemberPWSql() {
		String query = "select MEMBERPW from MEMBER where MEMBERID=?";
		return query;
	}

	
	public String getSelectByColumnSql(String column, Object value) {
		addQuotes(value);
		String query = "select * from member where " + column + " = " + value;	
		System.out.println("완성된 쿼리문");
		return query;

	}
	
	// vo에서 값이 저장된 항목만 들고와서 sql 생성
	// 여기에 안뜨는건 값이 저장 안됐다는것..?
	public String getUpdateSql(Object dto, String conditionColumn, Object conditionValue) {
		System.out.println("getUpdateSql 메서드 실행");
		String property;
		Object propertyValue;
		String propertyPart = "";
		String conditionPart = "";

		//dto에서 값이 유효한 프로퍼티만 들고오기
		Map<String, Object> propertyMap = DtoFunction.getValidPropertyMap(dto);
		Iterator<String> propertyItr = propertyMap.keySet().iterator();
		//매개변수의 검색 조건값이 String이거나 Date시 큐오테이션 붙여줌 
		String conditionVlaueClassType = conditionValue.getClass().getSimpleName();

		if (conditionVlaueClassType.equals("String") || conditionVlaueClassType.equals("Date"))
			conditionPart = conditionColumn + " = '" + conditionValue + "'";
		else
			conditionPart = conditionColumn + " = " + conditionValue;

		while (propertyItr.hasNext()) {
			property = propertyItr.next();
			propertyValue = propertyMap.get(property);
			if (propertyValue != null && propertyValue != "") {
				String classSimpleName = propertyValue.getClass().getSimpleName();
				//예약어 체크
				if (oracleReservedWordSet.contains(property)) {
					property = "\"" + property.toUpperCase() + "\"";
				}
				if (classSimpleName instanceof String || conditionValue instanceof Date)
					propertyPart = propertyPart + property + " = '" + propertyValue + "'";
				else
					propertyPart = propertyPart + property + " = " + propertyValue;
				if (propertyItr.hasNext())
					propertyPart = propertyPart + ", ";
			}
		}

		// update 테이블명 set col1=val1, col2=val2, ... where con=conval
		String query = "update member set " + propertyPart + " where " + conditionPart;
		System.out.println("완성된 sql문 : " + query);
		return query;
	}
	
}
