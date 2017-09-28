package com.wom.api.services;


import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.ItemBudgetPlanningDao;

public class ItemBudgetPlanningServiceImpl implements ItemBudgetPlanningService{

	@Autowired
	ItemBudgetPlanningDao itembudgetplanningDao;
	
	@Override
	public JSONArray getItemBudgetPlanning(String jobId, String productcode, String staffCode) throws Exception{
		return itembudgetplanningDao.getItemBudgetPlanning(jobId, productcode, staffCode);
	}
	
	@Override
	public JSONArray submitProductQuotation(String itembudgetcode, String jobid, String suppliercode, String productcode, 
			String packingunit, String packingprice, String packingquantity, String moq, String gst,
			String shippingdays, String paymentterms, String staffcode, String requestquantity, 
			String requesttotalunit, String requesttotalamount, String storecode, String requesttotalamountwithgst,
			String requestedpackingweight, String requestedpackingmass) throws Exception{
				
		return itembudgetplanningDao.submitProductQuotation(itembudgetcode, jobid, suppliercode, productcode, packingunit, 
				packingprice, packingquantity, moq, gst, shippingdays, paymentterms, staffcode, requestquantity, requesttotalunit, 
				requesttotalamount, storecode, requesttotalamountwithgst, requestedpackingweight, requestedpackingmass);
	}

}
