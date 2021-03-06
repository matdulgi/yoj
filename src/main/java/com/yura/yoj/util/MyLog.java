package com.yura.yoj.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MyLog {

	public MyLog() {
	}
	
	public static void logReqParamMap(Map<String, String[]> paramMap) {
     	System.out.println("----요청 파라미터 맵 정보---");
     	paramMap.entrySet().stream().forEach((e)->System.out.println("키 : " + e.getKey() + " 타입 : " +e.getClass().getSimpleName() +" 밸류 : " + Arrays.asList(e.getValue())));
		
	}
//	
	public static void logParamMap(Map<String, String> paramMap) {
     	System.out.println("----요청 파라미터 맵 정보---");
     	paramMap.entrySet().stream().forEach((e)->System.out.println("키 : " + e.getKey() + " 타입 : " +e.getClass().getSimpleName() +" 밸류 : " + Arrays.asList(e.getValue())));
		
	}
	
	public static void logMap(Map<String, ?> paramMap) {
     	System.out.println("----요청 파라미터 맵 정보---");
     	paramMap.entrySet().stream().forEach((e)->System.out.println("키 : " + e.getKey() + " 타입 : " +e.getClass().getSimpleName() +" 밸류 : " + Arrays.asList(e.getValue())));
		
	}
	public static void logList(List<?> list) {
     	System.out.println("----List 정보---");
     	list.stream().forEach((e)->System.out.println(e));
		
	}

}
