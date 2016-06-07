package com.wom.api.dao;

import org.codehaus.jettison.json.JSONArray;

public interface StockControlDao {
	
	public JSONArray getStockControl(String jobId, String productcode, String staffCode) throws Exception;
	public JSONArray setStockControl(String jobid, String productcode, String storecode, String stockunit, String amount, String staffcode) throws Exception;
	
}
