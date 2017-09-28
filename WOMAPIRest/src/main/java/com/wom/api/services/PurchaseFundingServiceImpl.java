package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.PurchaseFundingDao;

public class PurchaseFundingServiceImpl implements PurchaseFundingService{
	
	@Autowired
	PurchaseFundingDao purchasefundingDao;

	@Override
	public JSONArray getPurchaseFunding(String jobId, String productCode, String storecode, String staffcode) throws Exception {
		return purchasefundingDao.getPurchaseFunding(jobId, productCode, storecode, staffcode);
	}
	@Override
	public JSONArray submitPurchaseFunding(String purchaseFundingCode, String jobId, String productCode, 
			String originalbudget, String budgetBalance, String staffCode, String strpurchasedate, String storecode) throws Exception{
		return purchasefundingDao.submitPurchaseFunding(purchaseFundingCode, jobId, productCode, originalbudget, budgetBalance, staffCode, strpurchasedate, storecode);
	}
}
