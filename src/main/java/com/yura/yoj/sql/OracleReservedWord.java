package com.yura.yoj.sql;

import java.util.HashSet;
import java.util.Set;

public enum OracleReservedWord {
	//상수를 선언하는 것은 생성자를 호출하는 것과 마찬가지이다
	//등록된 상수는 해당 enum 클래스??의 모든 요소를 멤버로 가진다
	ORACLE_RESERVED_WORD();

	Set<String> oracleReservedWordSet = new HashSet<>();
	
	private OracleReservedWord() {
		oracleReservedWordSet.add("group");
		oracleReservedWordSet.add("groups");
	}
	
}
