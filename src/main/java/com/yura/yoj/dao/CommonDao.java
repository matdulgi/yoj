package com.yura.yoj.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.yura.yoj.dto.DtoFunction;
import com.yura.yoj.dto.PostDto;
import com.yura.yoj.sql.CommonSql;

//폐기


public class CommonDao {
	public Connection conn = null;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public ResultSet rs = null;
	public String sql = null;
	public CommonSql commonSql = new CommonSql();

	
	public CommonDao() {}
	
	public static Connection getConnection() {
		Connection connection = null;
		DataSource dataSource = null;
		try {
			InitialContext initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:comp/env");
//			dataSource = (DataSource) initialContext.lookup("jdbc/myOracle");
			dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/myOracle");
			connection = dataSource.getConnection();
		} catch (NamingException|SQLException e) {
			e.printStackTrace();
		}
		return connection;

	}
	

	// DB의 테이블의 컬럼들 조회하는 쿼리 실행하여 컬럼 Set 반환
	public Set<String> getColumnSet(String tableName) {
		System.out.println(" - getColumns 메서드 실행 -");
		Set<String> columnSet = new HashSet<>();
		try {
			sql = "select column_name from user_tab_columns where table_name = upper(?)";

			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				columnSet.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		Iterator<String> columnItr = columnSet.iterator();
		System.out.println(" - 테이블 컬럼 목록 - ");
		while (columnItr.hasNext()) {
			System.out.println(columnItr.next());
		}
		return columnSet;
	}

	public Map<String, String> selectAll(String table) {
		Map<String, String> columnMap = new HashMap<>();
		System.out.println("selectAll 호출");
		sql = "select * from member";
		String columnName;
		String value;

		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, "TESTTABLE");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				for (int i = 1; i <= 3; i++) {
					columnName = rs.getMetaData().getColumnName(i);
					value = rs.getString(i);
					columnMap.put(columnName, value);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return columnMap;
	}


	// 연결 해제
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
		}
	}

	public void insert(Object dto) {
	}


	//하나의 메서드로 다양한 경우를 처리할 수 있으면 그게 다형성이다..
	//select [*] from [table] [where ] [order by] 
	//pstmt를 사용했던건, Statement의 sql중 특정 부분을 매개변수로 가져오기 때문이었다(예를들어 가져올 게시글 번호 등)
	//어차피 조건절의 값부분은 사용자의 입력에 의해 쿼리문이 만들어지게 된다
	//만약 sql문을 따로 얻어와서 처리한다면.... 그것도 sqlInjection 문제가 있다
	//결국 sql문을 사용자의 입력만으로 완성한다면 보만문제가 있다-
	//그러면 결국 해당하는 part들을 전부 매개변수로 받아와야 한다는 말인데 받아올 게 너무 많다(dto, List도 넣는다면 )
	//결국, 조건이 까다로워지는 메서드의 경우에는 새로 만들어 사용하는 수밖에 없다..
	//방법 1. 만약 조건을의 일부를 sql에 넣어 부분적으로 지정된 sql을 넣어 dao에서는 (sql, 값, 받아올 객체)만 전달하는 경우
	//방법 2. 그냥 상속하지 않고 새로 메소드를 만드는 경우
	//그럼 commonDao를 상속하는 방식은 잘못된 방식인가?
	public Collection<?> select() {
		return null;
	}

	public Collection selectByColumn(Object value) {
		return null;
	}

	
	public void selectOne(String table, String conditionColumn, Object value, Object dto) {
		System.out.println("\nCommmonDao selectOne 실행");
		String sql = "select * from "+table+" where "+conditionColumn+" = ?";
		System.out.println("완성된 sql : " + sql);
//		Object dto = null;
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, value);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				DtoFunction.setDtoWithResultSetRow(dto, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//조건절 Map을 받아서 select
	public void selectOne(String table, List<Entry<String, Object>> conditionList, Object dto) {
		System.out.println("\nCommanDao selectOne(String, Map, Object) 실행");
		String conditionPart = "where ";
		String conditionColumn = "";
		Object conditionValue = "";
//		Set<String> conditionColumnSet = conditionMap.keySet();
//		Iterator<String> conditionColumnItr = conditionMap.keySet().iterator();
		Iterator<Entry<String, Object>> conditionColumnItr = conditionList.iterator();

//		for(String conditionColumn : conditionColumnSet) {
		while(conditionColumnItr.hasNext()) {
			Entry<String, Object> conditionEntry = conditionColumnItr.next();
			conditionColumn = conditionEntry.getKey();
			conditionPart = conditionPart + conditionColumn + "= ? ";
			if(conditionColumnItr.hasNext())
				conditionPart = conditionPart + " and ";
		}
		String sql = "select * from " + conditionPart;
		System.out.println("완성된 sql : " + sql);
		
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			for(int i = 1; i<=conditionList.size(); i++) {
				conditionValue = conditionList.get(i-1).getValue();
				pstmt.setObject(i, conditionValue);
			}
			rs = pstmt.executeQuery();
			if(rs.next())
			DtoFunction.setDtoWithResultSetRow(dto, rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	//
	public Object getMaxValue(String column, String table) {
		String sql = commonSql.getMaxValueSql(column, table);
		Object maxValue = null;

		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				maxValue = convertColumnType(rs.getObject(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return maxValue;
	}

	public Object getMaxValue(String maxColumn, String table, String conditionColumn, Object value) {
		String sql = commonSql.getMaxValueSql(maxColumn, table, conditionColumn);
		Object maxValue = null;

		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, value);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				maxValue = convertColumnType(rs.getObject(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return maxValue;
	}

	public void update(Object dto, String column, Object value) {
	}

	public void update(Set<Object> dtoSet, String column, Object value) {
	}

	
	public Object convertColumnType(Object columnValue) {
		String type = columnValue.getClass().getSimpleName();
		Object convertedValue = null;
		switch (type) {
		case "BigDecimal":
			BigDecimal bigDecimal = (BigDecimal) columnValue;
			convertedValue = bigDecimal.intValue();
			break;
		default:
			convertedValue = columnValue;
		}
		return convertedValue;
	}

	public int delete(String conditionColumn, Object conditionValue) {
		return 0;
	}

	public int delete(String table, String conditionColumn, Object conditionValue) {
		System.out.println("\nboardDao delete 실행");
		String sql = commonSql.getDeleteSql(table, conditionColumn);
		int count=0;
		try {
			Connection conn = getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setObject(1, conditionValue);
			rs = pstmt.executeQuery();
			count = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return count;
	}

	public PostDto selectOne(int postNum) {
		return null;
	}

}