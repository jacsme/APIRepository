package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;


public interface StockControlService {

	public JSONArray getStockControl(String jobId, String productcode, String staffCode) throws Exception;
	public JSONArray setStockControl(String jobid, String productcode, String storecode, String stockunit, String amount, String staffcode) throws Exception;
}
