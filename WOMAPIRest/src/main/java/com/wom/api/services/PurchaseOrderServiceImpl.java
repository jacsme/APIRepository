package com.wom.api.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.PurchaseOrderDao;

public class PurchaseOrderServiceImpl implements PurchaseOrderService{

	@Autowired
	PurchaseOrderDao purchaseorderDao;
	
	@Override
	public JSONArray getApprovedPurchaseOrder(String jobid, String suppliercode, String staffcode) throws Exception {
		return purchaseorderDao.getApprovedPurchaseOrder(jobid, suppliercode, staffcode);
	}

	@Override
	public JSONArray emailPurchaseOrder(HttpServletRequest request, HttpServletResponse response, String storecode, String purchaseordercode, String jobid, String suppliercode, String staffcode) throws Exception {
		return purchaseorderDao.emailPurchaseOrder(request, response, storecode,purchaseordercode, jobid, suppliercode, staffcode);
	}

	@Override
	public JSONArray submitNewPO(String storecode, String productcode, String stockunit, String staffcode, String uom)
			throws Exception {
		return purchaseorderDao.submitNewPO(storecode, productcode, stockunit, staffcode, uom);
	}

	@Override
	public JSONArray emailPOLogin(String staffcode, String password) throws Exception {
		return purchaseorderDao.emailPOLogin(staffcode, password);
	}
	

}
