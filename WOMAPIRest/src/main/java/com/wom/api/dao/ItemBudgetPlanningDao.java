package com.wom.api.dao;

import org.codehaus.jettison.json.JSONArray;

public interface ItemBudgetPlanningDao {
	
	public JSONArray getItemBudgetPlanning(String jobId, String productcode, String staffCode) throws Exception ;
	public JSONArray submitProductQuotation(String itembudgetcode, String jobid, String suppliercode, String productcode, 
			String packingunit, String packingprice, String packingquantity, String moq, String gst,
			String shippingdays, String paymentterms, String staffcode, String requestquantity, 
			String requesttotalunit, String requesttotalamount, String storecode, String requesttotalamountwithgst, 
			String requestedpackingweight, String requestedpackingmass) throws Exception;
}
