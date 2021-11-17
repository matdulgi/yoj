package com.yura.yoj.dao;

import com.yura.yoj.dto.DtoFunction;
import com.yura.yoj.dto.PostDto;
import com.yura.yoj.sql.BoardSql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BoardDao extends CommonDao {
	private BoardSql boardSql = BoardSql.getInstance();

	private static BoardDao boardDao = new BoardDao();

	private BoardDao() {
	}

	public static BoardDao getInstance() {
		return boardDao;
	}

	// dto의 프로퍼티들을 DB에 저장
	// 프로퍼티 컬렉션을 List로 처리하여 setString에서 인덱스로 가져올 때 순서가 맞을수 있게 해보자
	// PreparedStatement를 쓰기 때문에 모든 값을 sql 클래스에서 처리할 수가 없다
	// PreparedStatement 자체를 넘기는 방법도 있을 것 같지만..
	// 근데 PreparedStatement는 쿼리문이 미리 저장되어 컴파일되는 원리인데 Sql 클래스를 통해서 동적으로 Sql을 생성해도
	// 되는걸까?
	// 한번 동작 순서를 짚어 보면, 자바의 소스 코드는 컴파일 되어 호춮시에 로딩된다
	// 근데 컴파일이라는건 번역 작업으로 무슨 값을 가지는지는 알지 않는다?

	// 글쓰기 메서드 insert
	// 게시글 번호, 등록일 추가하기
	@Override
	public void insert(Object postDto) {
		System.out.println("BoardDao insert 실행");
		int i = 1;
		Object propertyValue = null;
		try {
			Connection conn = getConnection();
			// sql문은 사용자의 입력시에 만들어진다
			String sql = boardSql.getInsertSql(postDto);
			pstmt = conn.prepareStatement(sql);
			List<Object> postPropertyValueList = DtoFunction.getValidPropertyValueList(postDto);

			Iterator<Object> postPropertyValueItr = postPropertyValueList.iterator();
			while (postPropertyValueItr.hasNext()) {
				propertyValue = postPropertyValueItr.next();
				System.out.println(i + "번째 값 : " + propertyValue);
				pstmt.setObject(i, propertyValue);
				i++;
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public Collection<PostDto> select() {
		System.out.println("\nBoardDao selectByColumn 실헹");
		List<PostDto> postList = new ArrayList<>();
		sql = "select * from post";
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			// post객체를 하나로 다 돌려써서 List의 모든 요소가 값을 공유했다
			// 글의 순서는 List에 저장된 순서이고, 거꾸로 가져오기 위해 rs를 조작
			rs.afterLast();
			while (rs.previous()) {
				PostDto postDto = new PostDto();
				DtoFunction.setDtoWithResultSetRow(postDto, rs);

				postList.add(postDto);
				System.out.print("dto 저장. ");
				DtoFunction.printDtoInfo(postDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return postList;
	}

	// 페이지 처리, 10개만 가져오기, 후방탐색
	// 10개만 가져오기 위한 방법
	// 1. select시 between 사용. 번호순으로 가져오기 때문에 중간에 번호가 삭제되면 10개가 다 안가져와진다. 추가로 알고리즘을
	// 통해서 더 가져오기
	// 2. rownum <=10으로 제한하고 마지막 글 번호를 저장해서 페이징에 사용하기
	// 채택~~ 3. ResultSet에서 자르기
	// 4. ResultSet에서 데이터 조작
	// orderby는 우선순위가 rowNum 보다 낮아서 뷰를 따로 만들어야함
	//
	// 리플처리
	// 현재의 문제점 - 리플을 작성할 때마다 모든 게시글의 ref가 밀리는게 마음에 안든다
	// 1. step을 체크하여 plsql로 무한 조인..?
	// 2. ResultSet에서 체크
	// 만약 step이 높으면 추가하지 않고, 아 근데 자기를 참조하는 게시글이 있는지 어떻게 확인하지?
	// 답글을 쓰면 refNum을 1씩 늘리는 방식? 일단 refSteP을 대신 사용
	// refNum이 늘어나있으면 join이나 다른거 실행
	public List<PostDto> getBoard(int pageNum) {
		System.out.println("\ngetBoard호출");
		List<PostDto> postList = new ArrayList<>();
		sql = "select * from postview where rnum between ? and ?";
		int startRef = 10 * (pageNum - 1) + 1;

		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRef);
			pstmt.setInt(2, startRef + 9);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PostDto postDto = new PostDto();
				DtoFunction.setDtoWithResultSetRow(postDto, rs);
				postList.add(postDto);
				System.out.print("dto 저장. ");
				DtoFunction.printDtoInfo(postDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return postList;
	}

	// 페이지 처리, 10개만 가져오기, 후방탐색
	public List<PostDto> getBoard(String category, int pageNum) {
		System.out.println("\ngetPage(String, Object) 호출");
		List<PostDto> postList = new ArrayList<>();
		sql = "select * from " + "(select rownum rnum, p.* " + "from (" + "select * from post "
				+ "where postCategory = ? " + "order by postRef desc, postRef_step) p" + ") "
				+ "where rnum between ? and ?";
		int startRef = 10 * (pageNum - 1) + 1;

		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, startRef);
			pstmt.setInt(3, startRef + 9);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PostDto postDto = new PostDto();
				DtoFunction.setDtoWithResultSetRow(postDto, rs);
				postList.add(postDto);
				System.out.print("dto 저장");
				DtoFunction.printDtoInfo(postDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return postList;
	}

	// 조건 쿼리로 db 조회하여 Collection 반환
	public List<PostDto> selectByColumn(Object value) {
		System.out.println("BoardDao selectByColumn 실행");
		List<PostDto> postList = new ArrayList<>();
		String sql = "select * from post where ? = ?";

//		Object columnValue;
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setObject(1, value);
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
//			System.out.println("총 칼럼 수 : " + size);
			// 결과를 dto에 저장해야한다

			rs.afterLast();
			while (rs.previous()) {
				PostDto postDto = new PostDto();
				DtoFunction.setDtoWithResultSetRow(postDto, rs);

				postList.add(postDto);
				System.out.print("dto 저장. ");
				DtoFunction.printDtoInfo(postDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return postList;
	}

	// row 하나만 뽑아서 dto에 값 저장하여 반환
	//
	@Override
	public PostDto selectOne(int postNum) {
		System.out.println("\nBoardDad selectOne(int) 실행");
		PostDto postDto = new PostDto();

		String sql = "select * from post where postNum = ?";

		Object columnValue;
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNum);
			rs = pstmt.executeQuery();
			int size;
			// 결과를 dto에 저장해야한다

			while (rs.next()) {
				size = rs.getMetaData().getColumnCount();
				System.out.println("총 칼럼 수 : " + size);
				for (int i = 1; i <= size; i++) {
					String column = rs.getMetaData().getColumnName(i);
					columnValue = rs.getObject(i);
					System.out.println("저장할 칼럼 : " + column + " 값 : " + columnValue);
					DtoFunction.setPropertyWithColumn(postDto, column, columnValue);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return postDto;
	}

	// db의 모든 게시글 번호만 반환
	public int selectMaxValue(String maxColumn, String table) {
		System.out.println("BoardDao selectMaxValue 실행");
//		String sql = "select max(postNum) from post";
		String sql = boardSql.getMaxValueSql(maxColumn, table);
		int maxPostNum = 0;
		try {
			Connection conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				maxPostNum = rs.getInt(1);
			System.out.println("현재 글 마지막 번호는 " + maxPostNum);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return maxPostNum;
	}

	public int selectMaxValue(String maxColumn, String table, String conditionColumn, Object value) {
		System.out.println("BoardDao selectMaxValue 실행");
//		String sql = "select max([maxColumn]) from [table] where [conditionColumn] = ? ";
		String sql = boardSql.getMaxValueSql(maxColumn, table, conditionColumn);
		int maxValue = 0;

		try {
			Connection conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				maxValue = rs.getInt(1);
			System.out.println(maxColumn + "의 최댓값은" + maxValue);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return maxValue;
	}

	public int getPostCount() {
		System.out.println("BoardDao getPostCount 실행");
		String sql = "select count(*) from post";
		int postCount = 0;
		try {
			Connection conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				postCount = rs.getInt(1);
			System.out.println("전체 글 수" + postCount);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return postCount;
	}

	public int getPostCount(String value) {
		System.out.println("BoardDao getPostCount(String) 실행");
		String sql = "select count(*) from post where postCategory = ?";
		int postCount = 0;
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();
			if (rs.next())
				postCount = rs.getInt(1);
			System.out.println("전체 글 수" + postCount);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return postCount;
	}

	// 재귀함수처리
	// List를 받아와서 List에 답글 추가, 10이 넘으면 안됨
	// reply 테이블에서 해당 postNum의 답글들 가져오기
	// 다답글은 처리목록에서 빼고 재귀처리한다
	// 현재 level보다 높은 level은 다답글
//	public int addReply(int postNum, int refLevel, List<PostDto> postList) {
//		System.out.println("BoardDao getReply 실행");
////		List<PostDto> replyList = new ArrayList<>();
//		// 커넥션 등 참조주소 새로 생성... 어떻게 할지는 고민
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		int replyNum;// 답글번호
//		int replyRefLevel;// 얻어올 답글레벨
//		int replyRefCnt;// 답글의 참조횟수
//		int listSize = postList.size();
//		int replyCnt = 0;
//		sql = "select * from replyview where postRef = ?";
//		try {
//			conn = getConnection();
//			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			pstmt.setInt(1, postNum);
//			rs = pstmt.executeQuery();
//			while (rs.next() && listSize <= 10) { // 기본적으로 level이 같은 답글들을 다 가져온다
//				PostDto postDto = new PostDto();
//				replyNum = rs.getInt("postNum");
//				replyRefLevel = rs.getInt("postRef_level");
//				replyRefCnt = rs.getInt("postref_step");
//				if (replyRefLevel > refLevel)
//					continue;
//				DtoFunction.setDtoWithResultSetRow(postDto, rs);
//				postList.add(postDto);
//				replyCnt++;
//				if (replyRefCnt > 0)
//					addReply(replyNum, refLevel + 1, postList);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//			}
//		}
//
//		return replyCnt;
//	}

	public int getReplyCntInPage() {
		int replyCntInPage = 0;

		return replyCntInPage;
	}

	@Override
	public void update(Set<Object> dtoSet, String column, Object value) {
//		String sql = boardSql.getUpdateSql(column, value);
			Connection conn = getConnection();
			close();
	}

	// dto의 값으로 update
	// sql : update 테이블 set column1=?, column2=? where conditionColumn,
	// conditionValue
	@Override
	public void update(Object dto, String conditionColumn, Object conditionValue) {
		String sql = boardSql.getUpdateSql(dto, conditionColumn, conditionValue);
//		PostDto postDto = (PostDto) dto;
//		Map<String, Object> propertyMap = postDto.settedPropertyMap;
		Map<String, Object> propertyMap = DtoFunction.getValidPropertyMap(dto);
		Iterator<String> propertyItr = propertyMap.keySet().iterator();
		String property;
		Object propertyValue;
		int i = 1;
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			while (propertyItr.hasNext()) {
				property = propertyItr.next();
				propertyValue = propertyMap.get(property);
				pstmt.setObject(i, propertyValue);
				i++;
			}
			pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	// db와 dto의 readCnt를 둘 다 올림
	public void addReadCnt(PostDto postDto) {
		System.out.println("\nPostDto addReadCnt 실행");
		sql = "update post set readCnt = ? where postNum = ?";
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postDto.getReadCnt() + 1);
			pstmt.setInt(2, postDto.getPostNum());
			pstmt.executeQuery();
			System.out.println(postDto.getPostNum() + "번 게시글의 조회수가" + (postDto.getReadCnt() + 1) + "로 증가");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	// 게시글의 자식 게시글 개수 구하기
	// 레벨이 하나 더 높은 글중에 더 낮아지기 전까지를 구해야함
	// 특정 답글에 달린 답글을 자바에서 구하려면 ...'
	// 우선 루트 답글의 level을 뽑아서 다음 요소의 level이 같아질 때까지 비교
	public int getPostRefCnt(int postNum, int postRef_level) {
		System.out.println("\nBoardDao getPostRefCnt 실행");
		int count = 0;
//		int postNum = postDto.getPostNum();
//		int postRef_level = postDto.getPostRef_level();
		String sql = "select postRef_level from " + "(select p.postRef_level " + "from ( select * from post "
				+ "where postNum > ? " + "order by postRef desc, postRef_step) p" + ")";
		System.out.println("sql : " + sql);
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int nextPostRefLevel = rs.getInt("postRef_level");
				if (nextPostRefLevel > postRef_level)
					count++;
				// 현재 레벨과 같은 글이 나오면 트리 끝
				else
					break;
			}
			System.out.println("하위 답글 개수" + count);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}

	// 답글 삽입
	public void pushRefStep(int postRef, int postRef_step) {
		System.out.println("\nBoardDao pustRefStep 실행");
		sql = "update post set postRef_step = postRef_step+1 " + "where postRef = ? and postRef_step >= ?";
		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postRef);
			pstmt.setInt(2, postRef_step);
//			pstmt.setInt(3, postRef_level);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void pullRefStep(int postRef, int postRefStep) {
		System.out.println("\nBoardDao pullRerStep 실행");
		sql = "update post set PostRef_step = postRef_step-1" + "where postRef = ? and postRef_step > ?";

		try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postRef);
			pstmt.setInt(2, postRefStep);
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

}
