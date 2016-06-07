package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;

public interface PurchaseFundingService {

	public JSONArray getPurchaseFunding(String jobId, String productCode, String storecode, String staffcode) throws Exception;
	public JSONArray submitPurchaseFunding(String purchaseFundingCode, String jobId, String productCode, 
			String originalbudget, String budgetBalance, String staffCode, String strpurchasedate, String storecode) throws Exception;
}
