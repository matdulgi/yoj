package com.yura.yoj.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public interface Dao {
	//대개 insert나 update는 칼럼 개수가 변하기 때문에 쿼리문을 생성하는 메서드가 있어야함
	//select는 조회한 값을 dto에 담을 때 리플렉션 이용

	//ㅇ만약 commonDao를 쓰지 않는다면 
	// - PreparedStatement로 대입할 값이나 조건칼럼 List 외의 매개변수 필요 x 
	// - 즉, 값을 저장하거나(insert, update) return할(select) Dto나 값, List의 매개변수만 필요  
	
	void insert(Object dto);
	
	//조건절이 없는 경우, 있는 경우, 많이 있는 경우 
	Collection<?> select();
	Collection<?> select(String conditionColumn, Object value);
	Collection<?> select(List<Entry<String, Object>> conditionList);


	//update [table] set [column1]=[value1], [column2]=[value2] ,... where [condition] = [conditionValue]
	//한 행 업데이트
	void update(Object dto, String column, Object value);
	//여러행 업데이트
	void update(Set<Object> dtoSet, String column, Object value);

	int delete(String conditionColumn, Object value);
}
