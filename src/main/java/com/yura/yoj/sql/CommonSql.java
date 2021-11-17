package com.yura.yoj.sql;

import java.util.HashSet;
import java.util.Set;

import com.yura.yoj.dto.DtoFunction;

public class CommonSql{
	Set<String> oracleReservedWordSet = OracleReservedWord.ORACLE_RESERVED_WORD.oracleReservedWordSet;

	//프로퍼티의 타입이 String, Date 등이면 sql에 사용하기 위해 ''큐오테이션 붙이기
	public void addQuotes(Object value) {
		String classSimpleName = value.getClass().getSimpleName();
		value = (String)value;
		if(classSimpleName.equals("String")||classSimpleName.equals("Date"))
			value = "'" + value + "'";
	}

	public void checkReservedWord(String word) {
		if(oracleReservedWordSet.contains(word))
			word="\"" + word + "\"";
	}
	
	public String getMaxValueSql(String maxColumn, String table) {
		//select max([column]) from [table] where [conditionColumn] = ?
		checkReservedWord(maxColumn);
		checkReservedWord(table);
		String sql = "select max(" + maxColumn + ") from " + table;
		System.out.println("완성된 sql : " + sql);
		return sql;
	}
	
	public String getMaxValueSql(String maxColumn, String table, String conditionColumn) {
		//select max([column]) from [table] where [conditionColumn] = ?
		checkReservedWord(maxColumn);
		checkReservedWord(table);
		checkReservedWord(conditionColumn);
		String sql = "select max(" + maxColumn + ") from " + table + " where " + conditionColumn + " = ?";
		System.out.println("완성된 sql : " + sql);
		return sql;
	}

	public String getDeleteSql(String table, String conditionColumn) {
		//delete from [테이블] where [조건칼럼 = 값]
		checkReservedWord(table);
		checkReservedWord(conditionColumn);
		String sql = "delete from " + table + " where " + conditionColumn + " = ?";
		System.out.println("완성된 sql : " + sql);
		return sql;
	}
}
