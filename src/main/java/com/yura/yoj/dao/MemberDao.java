package com.yura.yoj.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.yura.yoj.dto.DtoFunction;
import com.yura.yoj.dto.MemberDto;
import com.yura.yoj.sql.MemberSql;
import com.yura.yoj.status.MemberStatus.LoginStatus;

public class MemberDao extends CommonDao {
//	private Connection conn = null;
//	private Statement stmt = null;
//	private PreparedStatement pstmt = null;
//	private ResultSet rs = null;
//	private String sql = null;
	private MemberSql memberSql = MemberSql.getInstance();
//	private DtoFunction DtoFunction = DtoFunction.getInstatnce();

//	private Map<String, String> sqlMap = Controller.sqlMap; 뭐지이거
	private static MemberDao memberDao;// = new MemberDAO();

	private MemberDao() {}

	public static MemberDao getMemberDAO() {
		if (memberDao == null) {
			return new MemberDao();
		} else
			return memberDao;
	}

//조회

	// 칼럼 조건에 맞는 로우 조회
	// 칼럼과 값들의 Map을 반환
	public Map<String, String> selectByColumn(String column, String con) {
		Map<String, String> columnMap = new HashMap<>();
		System.out.println("selectByColumn 호출");
		sql = "select * from ? where ? = ?";
		try {
			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setString(2, column);
//			pstmt.setString(3, con);
			rs = pstmt.executeQuery();
			String columnName;
			String value;

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

	// 조건 칼럼과 값을 받아와서 검색하여 dto 반환
	public MemberDto selectByColumn(String selectColumn, Object selectValue) {
		System.out.println(" 메서드 selectByColumn 실행");
		MemberDto memberDto = new MemberDto();
		Set<String> propertySet = DtoFunction.getAllPropertySet(memberDto);
		String column;
		Object columnValue;
//		int i = 1;

		try {
			Connection conn = getConnection();
			sql = memberSql.getSelectByColumnSql(selectColumn, selectValue);
			rs = stmt.executeQuery(sql);// select를 실행하여 칼럼들이 튀어나왔다.
			int size = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= size; i++) {
					column = rs.getMetaData().getColumnName(i);// 대문자로 튀어나온다
					columnValue = rs.getObject(i);
					DtoFunction.setPropertyWithColumn(memberDto, column, columnValue);
//				i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return memberDto;
	}

	// VO로 DB에 회원정보 추가 (현재 member 칼럼: memberID, memberPW, message, group, email)
	// 칼럼들이 추가/삭제되어도 코드 수정 없게 작성
	public void insert(MemberDto memberDto) {
		System.out.println(" - MemberDAO.join 메서드 실행 - ");
		int i;
		String propKey = null;
		Object value = null;
		String column;
		Set<String> columnSet = getColumnSet("member");// member테이블의 칼럼셋 생서
		sql = memberSql.getInsertMemberSQL(columnSet);// 실행할 sql 생성
		Map<String, Object> propertiesMap = DtoFunction.getAllPropertyMap(memberDto);
		// VO의 프로퍼티 맵

		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			// insert into 의 와일드카드에 VO의 프로퍼티 값 매핑
			Iterator<String> columnItr = columnSet.iterator();
			i = 1;
			while (columnItr.hasNext()) {
				column = columnItr.next();
				Iterator<String> propKeyItr = propertiesMap.keySet().iterator();
				while (propKeyItr.hasNext()) {
					propKey = propKeyItr.next();
					if (propKey.toUpperCase().equals(column)) {
						value = propertiesMap.get(propKey);
						System.out.println("칼럼 : " + column + " 프로퍼티 : " + propKey + "값 : " + value);
						// 와일드 카드에 칼럼 지정
						if (value.getClass().getSimpleName().equals("String")) {
							pstmt.setString(i, (String) value);
							System.out.println(
									"pstmt " + i + "번째 요소 : " + value.getClass().getSimpleName() + propKey + value);
						} else if (value.getClass().getSimpleName().equals("Date")) {
							pstmt.setDate(i, (Date) value);
							System.out.println("pstmt " + i + "번째 요소 : " + value.getClass().getSimpleName() + value);
						} else if (value.getClass().getSimpleName().equals("Integer")) {
							pstmt.setInt(i, (int) value);
							System.out.println("pstmt " + i + "번째 요소 : " + value.getClass().getSimpleName() + value);
						}
						i++;
					}
				}
			}

			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	// 로그인
	public boolean searchMember(String inputID) {
		System.out.println("selectMember 호출");
		sql = "select " + inputID + " from member";
		String selectedMemberID = null;

		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
//				selectedMemberID = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
		return false;
	}

	public boolean memberIDCheck(String memberID) {
		System.out.println("메서드 memberIDCheck 호출");
		sql = "select * from member where memberid = '" + memberID + "'";
		boolean isduplicatedID = true;

		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				isduplicatedID = true;
			} else
				isduplicatedID = false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
		return isduplicatedID;
	}

	// 아이디로 비밀번호 가져오기
	public boolean checkMemberPw(String memberID, String inputPW) {
		System.out.println(" - 메서드 checkmemberPW 실행");
		boolean correctPW = false;
		try {
			Connection conn = getConnection();
			sql = "select memberPW from member where memberID = '" + memberID + "'";
			System.out.println("쿼리문 : " + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				String memberPW = rs.getString(1);
				System.out.println("inputPW : " + inputPW + ", memberPW : " + rs.getString(1));
				if (memberPW.equals(inputPW)) {
					System.out.println("비밀번호 일치");
					correctPW = true;
				} else
					System.out.println("비밀번호 불일치");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
		return correctPW;
	}

	public LoginStatus identifyMember(String inputID, String inputPW) {
			sql = memberSql.getCheckMemberPWSql();
			LoginStatus loginStatus = LoginStatus.GUEST;
//			HttpSession session = request.getSession();
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputID);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String memberPW = rs.getString(1);
				System.out.println("멤버 PW : " + memberPW);
				if (inputID.equals("host") && inputPW.equals(memberPW)) {
					System.out.println("관리자 로그인");
					loginStatus = LoginStatus.MANAGER;
//					request.setAttribute("loginStatus", LoginStatus.MANAGER);// 관리자
				} else if (!inputID.equals("host") && inputPW.equals(memberPW)) {
					System.out.println("멤버 로그인");
//					request.setAttribute("loginStatus", LoginStatus.MEMBER);// 관리자
//					request.setAttribute("memberID", inputID);
					loginStatus = LoginStatus.MEMBER;
				} else {// 비밀번호가 틀릴 경우
					System.out.println("비밀번호 불일치");
//					request.setAttribute("loginStatus", LoginStatus.GUEST);
					loginStatus = LoginStatus.FAILED;
				}
			} else {// 아이디가 존재하지 않을 경우
				System.out.println("잘못된 ID");
				loginStatus = LoginStatus.FAILED;
//				request.setAttribute("loginStatus", LoginStatus.GUEST);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return loginStatus;
	}

	// 회원 정보 수정
	public void update(Object vo, String memberID) {
		System.out.println("updateMemberRow 메서드 실행");
		try {
			Connection conn = getConnection();
			sql = memberSql.getUpdateSql(vo, "memberID", memberID);
//					System.out.println("저장된 Map 키 : " + " 값 : " + memberTableMap.get(column));

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
	}

	// 탈퇴
	public void delete(String inputID) {
		try {
			Connection conn = getConnection();
			sql = "delete from member where memberID = '" + inputID + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
	}
}
