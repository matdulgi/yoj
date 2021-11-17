package com.yura.yoj.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

//dto Function을 사용하는 방법 3가지
//1. 싱글톤으로 사용  2. 상속하여 사용  3.정적 메소드로 사용
// - dto에 다형성을 적용해야 하는 상황인가??
// - 싱글톤과 정적메소드 사용시 각각의 장단점
// - 관점. 캡슐로 사용할지 메서드처럼 사용할지
// - 객체지향에서 static을 너무 남발하면 객체지향 언어의 장점을 잃는다
public class DtoFunction {
	// setter나 getter 메서드 이름으로 카멜 표기법 프로퍼티 이름 반환
	
	public static String methodNameToProperty(String methodName) {
		char[] methodNameArray = methodName.substring(3).toCharArray();
		methodNameArray[0] = (char) (methodNameArray[0] + 32);
		return new String(methodNameArray);
	}

	// setter나 getter메서드의 이름을 칼럼명으로 변경. 칼럼명은 대문자로 튀어나오기 때문에 대문자로 지정
	public static String methodNameToColumn(String methodName) {
		return methodName.substring(3).toUpperCase();
	}

	// Number을 getObject로 받으면 BicDecimal이 되기 때문에 integer로 바꾸어 주어야함
	// 혹시 문제생기면 instanceof로 바꾸기
	// dao쪽으로 옮길까 고민중
	public static Object convertColumnType(Object columnValue) {
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

	// 요청 파라미터로 Dto 초기화(insert 하기 전 dto에 데이터 담을 때 사용)
	// 요청 파라미터의 name 과 프로퍼티 이름은 같아야 한다
	// set한 프로퍼티의 목록을 Set으로 리턴하면 좋을것같다
	public static Set<String> setPropertyFromRequest(HttpServletRequest request, Object dto) {
		System.out.println("DtoFunction setPropertyFromRequest 실행");
		Enumeration<String> parameterEnum = request.getParameterNames();
		String parameter;
		String parameterValue;
		String property;
		Set<String> settedPropertySet = new HashSet<>();

		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		// 파라미터 이름,값 가져오기
		while (parameterEnum.hasMoreElements()) {
			parameter = parameterEnum.nextElement();
			parameterValue = request.getParameter(parameter);
			System.out.println("요청 파라미터 : " + parameter + " 값 : " + parameterValue);

			// dto에서 프로퍼티 가져오기
			for (Method method : methods) {
				property = methodNameToProperty(method.getName());
				// parameterValue값이 있는 경우에만 setter 사용. 빈 폼은 null이 아닌 ""로 전달된다
				// parameter 의 값은 항상 String이라는 것을 항상 잊지 말자
				if (method.getName().contains("set") && property.equals(parameter) && !parameterValue.equals("")) {
					System.out.println(" - " + method.getName() + "메서드 실행 - " + parameterValue + "을 " + property + "에");
					try {
						method.invoke(dto, parameterValue);
						settedPropertySet.add(property);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 확인용
		checkSettedProperties(dto);
		return settedPropertySet;
	}
	
	

	// db조회 결과 ResultSet로 dto를 초기화
	public static void setDtoWithResultSetRow(Object dto, ResultSet rs) {
		System.out.println("DtoFunction setDtoWithResultSet 실행");
		String column;
		String columnType;// 확인용
		Object columnValue;
		try {
			int columnCount = rs.getMetaData().getColumnCount();

			// resultSet의 다음 값을 불러와 dto에 저장
			// !주의 : getObject로 NUMBER을 가져오면 int가 아닌 BicDemical로 전달된다
			for (int i = 1; i <= columnCount; i++) {
				column = rs.getMetaData().getColumnName(i);
				columnType = rs.getMetaData().getColumnTypeName(i);
				switch (columnType) {
				case "NUMBER":
					columnValue = rs.getInt(i);
					break;
				case "VARCHAR2":
					columnValue = rs.getString(i);
					break;
				default:
					columnValue = rs.getObject(i);
					break;
				}
				System.out.println(i + "번째 칼럼 : " + column + " 타입 : " + columnType + " 값 : " + columnValue);
				setPropertyWithColumn(dto, column, columnValue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// dto와 칼럼이름, 값만 가지고오면 알아서 매칭해서 저장
	// Collection 변수이면  add하기 추가....
	// 리플렉션을 사용한 이유... 만약 객체를 넣어서 메소를 사용한 후 
	// 아냐 어차피 정보는 알아야한다
	// 메타데이터는 알아야돼
	public static void setPropertyWithColumn(Object dto, String column, Object columnValue) {
		System.out.println("DtoFunction setPropertyWithColumn 실행");
		// dto에서 메서드 이름 뽑아오기
		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		String property;
		String methodName = "";
		Object convertedColumnValue = columnValue;

		for (Method method : methods) {
			try {
				methodName = method.getName();
				property = methodNameToColumn(methodName);
//				if (methodName.contains("set") && property.equals(column) && columnValue!=null) {// 칼럼이름은 대문자로 튀어나온다
				if (methodName.contains("set") && property.contains(column) && columnValue!=null) {// 칼럼이름은 대문자로 튀어나온다
					convertedColumnValue = convertColumnType(columnValue);
					System.out.println("칼럼 " + column + "과 일치하는 메서드 발견 : " + methodName + "실행");
					System.out.println(property + "의 값을 " + convertedColumnValue + "로 초기화");
					method.invoke(dto, convertedColumnValue);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	// dto의 모든 프로퍼티 셋 반환
	public static Set<String> getAllPropertySet(Object dto) {
		System.out.println(" 메소드 getPropertySet 실행");
		Set<String> propertySet = new HashSet<>();
		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		String methodName;
		String property;

		for (Method method : methods) {
			methodName = method.getName();
			property = methodNameToProperty(methodName);
			propertySet.add(property);
		}
		return propertySet;
	}

	// VO의 모든 프로퍼티 키, 값 Map 반환
	public static Map<String, Object> getAllPropertyMap(Object dto) {
		System.out.println(" - getAllPropertyMap 실행- ");
		String property = "";
		Object propertyValue;
		Map<String, Object> propertieMap = new HashMap<>();

		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		System.out.println("memberDto의 프로퍼티 목록");
		for (Method method : methods) {
			// Class에는 다른 get메서드가 존재한다
			if (method.getName().contains("get")) {
				property = methodNameToProperty(method.getName());

				try {
					propertyValue = method.invoke(dto);
					System.out.print("프로퍼티 : " + property + " 값 : " + propertyValue);
					propertieMap.put(property, propertyValue);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return propertieMap;
	}

	// 값이 있는 프로퍼티만 Map 으로 반환
	public static Map<String, Object> getValidPropertyMap(Object dto) {
		System.out.println(" - getValidPropertyMap 실행- ");
		String property;
		Object propertyValue;
		Map<String, Object> propertieMap = new HashMap<>();

		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		System.out.println("memberDto의 프로퍼티 목록");
		for (Method method : methods) {
			// Class에는 다른 get메서드가 존재한다
			if (method.getName().contains("get")) {
				property = methodNameToProperty(method.getName());

				try {
					propertyValue = method.invoke(dto);
					if (propertyValue != null && propertyValue != "") {
						System.out.println("프로퍼티 : " + property + " 값 : " + method.invoke(dto));
						propertieMap.put(property, method.invoke(dto));
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return propertieMap;
	}

	// PreparedStatement에 순서를 맞추기 위해 List 사용
	// 프로퍼티 값이 존재하는지 미리 확인.. 근데 이러면 자원 낭비가 너무 많이 된다
	public static List<String> getValidPropertyList(Object dto) {
		System.out.println("DtoFunction getValidPropertyList 실행");
		String property;
		Object propertyValue;
		List<String> propertyList = new ArrayList<>();

		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		System.out.println("memberDto의 프로퍼티 목록");
		for (Method method : methods) {
			if (method.getName().contains("get")) {
				try {
					property = methodNameToProperty(method.getName());
					propertyValue = method.invoke(dto);
					if (propertyValue != null && propertyValue != "") {
						System.out.println("프로퍼티 : " + property + " 값 : " + method.invoke(dto));
						propertyList.add(property);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return propertyList;
	}

	public static List<Object> getValidPropertyValueList(Object dto) {
		System.out.println("DtoFunction getValidPropertyValueList 실행");
		String property;
		Object propertyValue;
		List<Object> propertyValueList = new ArrayList<>();

		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();

		System.out.println("memberDto의 프로퍼티 목록");
		for (Method method : methods) {
			if (method.getName().contains("get")) {
				try {
					property = methodNameToProperty(method.getName());
					propertyValue = method.invoke(dto);
					if (propertyValue != null && propertyValue != "") {
						System.out.println("프로퍼티 : " + property + " 값 : " + method.invoke(dto));
						propertyValueList.add(propertyValue);
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		// 확인
		List<String> propertyList = getValidPropertyList(dto);
		Iterator<String> propertyItr = propertyList.iterator();
		Iterator<Object> propertyValueItr = propertyValueList.iterator();
		System.out.println("ㅇList에 저장된 프로퍼티 값 ");
		while (propertyValueItr.hasNext() && propertyItr.hasNext()) {
			System.out.println("프로퍼티 : " + propertyItr.next() + " 값 : " + propertyValueItr.next());
		}
		return propertyValueList;
	}

	public static void checkSettedProperties(Object dto) {
		System.out.println("DtoFunction checkSettedProperties 실행");
		String checkPropertyName;
		Object checkPropertyValue;
		Map<String, Object> dtoPropertyMap = getValidPropertyMap(dto);
		Iterator<String> dtoPropertyItr = dtoPropertyMap.keySet().iterator();
		while (dtoPropertyItr.hasNext()) {
			checkPropertyName = dtoPropertyItr.next();
			checkPropertyValue = dtoPropertyMap.get(checkPropertyName);
			System.out.println("저장된 프로퍼티 : " + checkPropertyName + " 값 : " + checkPropertyValue);
		}
	}

	public static void printDtoInfo(Object dto) {
		Class clazz = dto.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		String methodName;
		System.out.print("dto 정보 : ");
		try {
			for (Method method : methods) {
				methodName = method.getName();
				if (methodName.contains("get"))
					System.out.print(methodName +" : " + method.invoke(dto) + " ");
			}
			System.out.println("");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
