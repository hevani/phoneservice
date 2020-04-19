package com.phoneservice.util;

import java.util.List;

import org.thymeleaf.util.ListUtils;

import com.google.common.collect.Lists;
import com.mongodb.DBObject;

public  class PaginationUtil {
	private PaginationUtil() {
		super();
	}

	public static List<DBObject> paginateDbObjListHelper(List<DBObject> inputList, int pageNum, int pageSize) {
		if( ListUtils.isEmpty(inputList) ) {
			return inputList ;
		}
		List<List<DBObject>> subList = Lists.partition(inputList, pageSize);
	    if( subList.size() < pageNum) {
	    	return inputList ;
	    }
	    return subList.get(pageNum-1);
	}
}
