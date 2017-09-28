package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.PurchaseApprovalDao;

public class PurchaseApprovalServiceImpl implements PurchaseApprovalService {

	@Autowired
	PurchaseApprovalDao purchaseapprovaldao;
	
	@Override
	public JSONArray getPurchaseApproval(String jobId, String productCode, String staffcode) throws Exception {
		return purchaseapprovaldao.getPurchaseApproval(jobId, productCode, staffcode);
	}

	@Override
	public JSONArray approvePurchaseApproval(String purchaseapprovalcode, String jobId, String productCode, String suppliercode,
			String staffCode, String requestquantity, String requestunit, String totalamount, String storeCode, String balanceBudget) throws Exception {
		return purchaseapprovaldao.approvePurchaseApproval(purchaseapprovalcode, jobId, productCode, suppliercode, staffCode, requestquantity, requestunit, totalamount, storeCode, balanceBudget);
	}
	
	@Override
	public JSONArray denyPurchaseApproval(String purchaseapprovalcode, String jobid) throws Exception{
		return purchaseapprovaldao.denyPurchaseApproval(purchaseapprovalcode, jobid);
	}
}
