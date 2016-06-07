package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.StoreDao;


public class StoreServiceImpl implements StoreService{

	@Autowired
	StoreDao storeDao;
	
	@Override
	public JSONArray getContactUsInfo() throws Exception {
		return storeDao.getContactUsInfo();
	}

}
