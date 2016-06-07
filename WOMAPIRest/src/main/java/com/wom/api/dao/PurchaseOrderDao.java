package com.wom.api.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

public interface PurchaseOrderDao {
	
	public JSONArray getApprovedPurchaseOrder(String jobid, String suppliercode, String staffcode) throws Exception;
	public JSONArray emailPurchaseOrder(HttpServletRequest request, HttpServletResponse response, String storecode, String purchaseordercode, String jobid, String suppliercode, String staffcode) throws Exception;
	public JSONArray submitNewPO(String storecode, String productcode, String stockunit, String staffcode, String uom) throws Exception;
	public JSONArray emailPOLogin(String staffcode, String password) throws Exception;
}
