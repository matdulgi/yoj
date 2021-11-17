package com.yura.yoj.service;

import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.yura.yoj.dao.StatusDao;
import com.yura.yoj.dto.StatusDto;


public class StatusService {

	public StatusService() {
	}
	
	//status에는 	분류별 많은 문제들의 List가 있다

	public void status(HttpServletRequest req) {
		StatusDao statusDao = StatusDao.getInstance();
		List<Entry<String, Object>> conditionList = (List<Entry<String, Object>>) req.getAttribute("conditionList");
		
		StatusDto statusDto = (StatusDto)statusDao.select(conditionList);
		//select * from status where mem
		req.setAttribute("submit", statusDto);
	}

}
