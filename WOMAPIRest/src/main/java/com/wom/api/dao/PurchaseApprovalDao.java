package com.wom.api.dao;

import org.codehaus.jettison.json.JSONArray;

public interface PurchaseApprovalDao {

	public JSONArray getPurchaseApproval(String jobId, String productCode, String staffcode) throws Exception;
	public JSONArray approvePurchaseApproval(String purchaseapprovalcode, String jobId, String productCode, String 
			suppliercode, String staffCode, String requestquantity, String requestunit, String totalamount,
			String storeCode, String balanceBudget) throws Exception;
	public JSONArray denyPurchaseApproval(String purchaseapprovalcode, String jobid) throws Exception;
}
