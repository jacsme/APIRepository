package com.wom.api.dao;

import org.codehaus.jettison.json.JSONArray;

public interface PurchaseFundingDao {
	
	public JSONArray getPurchaseFunding(String jobId, String productCode, String storecode, String staffcode) throws Exception;
	public JSONArray submitPurchaseFunding(String purchaseFundingCode, String jobId, String productCode, 
			String originalbudget, String budgetBalance, String staffCode, String strpurchasedate, String storecode) throws Exception;

}
