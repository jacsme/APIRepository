package com.wom.api.services;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.StockControlDao;

public class StockControlServiceImpl implements StockControlService {

	@Autowired
	StockControlDao stockcontrolDao;
	
	static final Logger logger = Logger.getLogger(StockControlServiceImpl.class);
	
	@Override
	public JSONArray getStockControl(String jobId, String productcode, String staffCode) throws Exception {
		return stockcontrolDao.getStockControl(jobId, productcode, staffCode);
	}
	
	@Override
	public JSONArray setStockControl(String jobid, String productcode, String storecode, String stockunit, String amount, String staffcode) throws Exception {
		return stockcontrolDao.setStockControl(jobid, productcode, storecode, stockunit, amount, staffcode);
	}
}
