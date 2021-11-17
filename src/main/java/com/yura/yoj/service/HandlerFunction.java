package com.yura.yoj.service;

import javax.servlet.http.HttpServletRequest;

public class HandlerFunction {

	private static HandlerFunction handlerFunction = new HandlerFunction();
	
	private HandlerFunction() {}
	
	public static HandlerFunction getInstance() {
		return handlerFunction;
	}
	
	public String getRefererUri(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		String url = request.getRequestURL().toString().replace(request.getRequestURI(), "");
		String refererUri= referer.substring(url.length());
		return refererUri;
	}
}
