package com.yura.yoj.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import com.yura.yoj.dto.StatusDto;

import java.util.Set;

public class StatusDao implements Dao{

	private static StatusDao statusDao = new StatusDao();

	private StatusDao() { }
	
	public static StatusDao getInstance() {
		return statusDao;
	}
	

	@Override
	public void insert(Object dto) {
	}

	@Override
	public Collection<?> select() {
		return null;
	}

	@Override
	public Collection<?> select(String conditionColumn, Object value) {
		return null;
	}

	@Override
	public Collection<?> select(List<Entry<String, Object>> conditionList) {
		List<StatusDto> statusDtoList = new ArrayList<>(); 
		String sql = "select * from ";
		return statusDtoList;
	}

	@Override
	public void update(Object dto, String column, Object value) {
	}

	@Override
	public void update(Set<Object> dtoSet, String column, Object value) {
	}

	@Override
	public int delete(String conditionColumn, Object value) {
		return 0;
	}

	

}
