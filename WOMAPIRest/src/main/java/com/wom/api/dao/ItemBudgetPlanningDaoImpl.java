package com.wom.api.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.ProductQuotation;
import com.wom.api.model.PurchaseFundingPlanning;
import com.wom.api.model.PurchaseRequest;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class ItemBudgetPlanningDaoImpl implements ItemBudgetPlanningDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	FactoryEntityService<PurchaseFundingPlanning> factoryentityPFService = new FactoryEntityServiceImpl<PurchaseFundingPlanning>();
	FactoryEntityService<PurchaseRequest> factoryentityPRService = new FactoryEntityServiceImpl<PurchaseRequest>();
	FactoryEntityService<ProductQuotation> factoryentityPQService = new FactoryEntityServiceImpl<ProductQuotation>();
	
	static final Logger logger = Logger.getLogger(ItemBudgetPlanningDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getItemBudgetPlanning(String jobId, String productcode, String staffCode) throws Exception {
		JSONArray itembudgetplanningArray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			if (HibernateUtil.updateJobList(jobId, "Item Budget Planning", "Item Budget Planning", staffCode, "DP000003", sessionFactory)==true){
				List<String> itembudgetPlanningList = session.createSQLQuery(QueriesString.itembudgetplanningQuery 
						+ QueriesString.itembudgetplanningWhere)
						.setParameter("jobid", HelperUtil.checkNullString(jobId))
						.setParameter("productcode", HelperUtil.checkNullString(productcode)).list();
				if (itembudgetPlanningList.size() != 0){
					itembudgetplanningArray = ResultGeneratorUtil.populateresults(itembudgetPlanningList, MainEnum.ITEMBUDGETPLANNING);
					itembudgetplanningArray.put(getSupplierList(productcode, session));
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					itembudgetplanningArray.put(exceptionobj);
				}
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				itembudgetplanningArray.put(takenobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			itembudgetplanningArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return itembudgetplanningArray;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getItemBudgetPlanningHistory(String productcode, Session session) throws Exception{
		JSONArray itembudgetplanninghistoryArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		List<String> itembudgetplanninghistoryList = session.createSQLQuery(QueriesString.itembudgetplanninghistoryQuery +
				QueriesString.itembudgetplanninghistoryWhere)
				.setString("productcode", productcode).list();
		if (itembudgetplanninghistoryList.size() != 0){
			itembudgetplanninghistoryArray = ResultGeneratorUtil.populateresults(itembudgetplanninghistoryList, MainEnum.ITEMBUDGETPLANNINGHISTORY);
		}else{
			exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
			exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
			itembudgetplanninghistoryArray.put(exceptionobj);
		}
		return itembudgetplanninghistoryArray;
	}
	
	@Override
	public JSONArray submitProductQuotation(String itembudgetcode, String jobid, String suppliercode, String productcode, 
			String packingunit, String packingprice, String packingquantity, String moq, String gst,
			String shippingdays, String paymentterms, String staffcode, String requestquantity, 
			String requesttotalunit, String requesttotalamount, String storecode, String requesttotalamountwithgst, 
			String requestedpackingweight, String requestedpackingmass) throws Exception{
		
		JSONArray productquotationarray = new JSONArray();
		JSONObject productquotationobj = new JSONObject();
		
		if (requesttotalunit.equalsIgnoreCase("Infinity")) { requesttotalunit = "1";}
		if (requesttotalamount.equalsIgnoreCase("Infinity")) { requesttotalamount = "1";}
		
		BigDecimal drequesttotalamount = new BigDecimal(0.00);
		drequesttotalamount = new BigDecimal((String) requesttotalamount).setScale(2, BigDecimal.ROUND_HALF_UP);;
		
		BigDecimal gstamount = new BigDecimal(0.00);
		BigDecimal retailprice = new BigDecimal((String) packingprice);
		BigDecimal retailpricewithgst = new BigDecimal((String) packingprice);
		BigDecimal drequesttotalamountwithgst = new BigDecimal(0.00);
		BigDecimal requestpackingqty = new BigDecimal((String) packingquantity);
		BigDecimal requestpackingunit = new BigDecimal((String) packingunit);
		BigDecimal requesttotalpackingunit = new BigDecimal(0.00);
		String purchaseFundingcode = null;
		String strshippingdate = HelperUtil.parsejsondate("1", shippingdays);
		Session session = sessionFactory.openSession();
		try {
			//compute gst
			if("S".equalsIgnoreCase(gst)){
				BigDecimal gstrate = new BigDecimal((String) HelperUtil.GST_RATE);
				gstamount = drequesttotalamount.multiply(gstrate);
				drequesttotalamountwithgst = drequesttotalamount.add(gstamount).setScale(2, BigDecimal.ROUND_HALF_UP);
				retailpricewithgst = retailprice.add(retailprice).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
				drequesttotalamountwithgst = drequesttotalamount;
				retailpricewithgst = retailprice;
			}
			
			requesttotalpackingunit = requestpackingqty.multiply(requestpackingunit).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			PurchaseFundingPlanning purchasefundingplanning = factoryentityPFService.getEntity(MainEnum.PURCHASEFUNDING, itembudgetcode, jobid, session);
			if(purchasefundingplanning == null){
				purchaseFundingcode = ResultGeneratorUtil.codeGenerator("", "sq_purchase_funding", "PF22", session);
				BigInteger purchasefundingid = ResultGeneratorUtil.idGenerator("", "sq_purchase_funding_id", session);
				
				purchasefundingplanning = new PurchaseFundingPlanning(purchasefundingid, purchaseFundingcode, jobid, 
						itembudgetcode, staffcode);
				session.save(purchasefundingplanning);
			}else{
				purchaseFundingcode = purchasefundingplanning.getPurchaseFundingCode();
			}
			
			PurchaseRequest purchaserequest = factoryentityPRService.getEntity(MainEnum.PURCHASEREQUEST, itembudgetcode, session);
			if(purchaserequest == null){
				String purchaserequestcode = ResultGeneratorUtil.codeGenerator("", "sq_purchase_request", "RE22", session);
				BigInteger purchaserequestid = ResultGeneratorUtil.idGenerator("", "sq_purchase_request_id", session);
				
				purchaserequest = new PurchaseRequest(purchaserequestid, purchaserequestcode, itembudgetcode, suppliercode,
						 storecode, productcode, packingquantity, packingunit, requesttotalpackingunit.toString(), retailprice.toString(), 
						 gstamount.toString(), drequesttotalamount.toString(), paymentterms, 
						 drequesttotalamountwithgst.toString(), requestedpackingweight, requestedpackingmass, strshippingdate);
				session.save(purchaserequest);
			}

			ProductQuotation productquotation = factoryentityPQService.getEntity(MainEnum.PRODUCTQUOTATION, itembudgetcode, session);
			if(productquotation == null){
				BigInteger productquotationid = ResultGeneratorUtil.idGenerator("", "sq_product_quotation_id", session);
				
				productquotation = new ProductQuotation(productquotationid, itembudgetcode, suppliercode, productcode, 
						packingunit, packingprice, packingquantity, moq, gstamount.toString(), strshippingdate, retailpricewithgst.toString());
				session.save(productquotation);
			}
			
			HibernateUtil.callCommitClose(session);
			
			HibernateUtil.updateField("tblitembudgetplanning", "StaffCode", HelperUtil.checkNullString(staffcode), "ITEMBUDGETCODE", HelperUtil.checkNullString(itembudgetcode), sessionFactory);
			HibernateUtil.updateJobList(jobid, "Item Budget Planning", "Purchase Funding Planning", "", "DP000002", sessionFactory);
			
			productquotationobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			productquotationobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			productquotationarray.put(productquotationobj);
		
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			productquotationarray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return productquotationarray;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getSupplierList(String productcode, Session session) throws Exception {
		JSONArray supplierlistarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		try {
			List<String> supplierlist = session.createSQLQuery(QueriesString.supplierListQuery 
					+ QueriesString.supplierListWhere + QueriesString.supplierlistGroupby)
					.setString("productcode", HelperUtil.checkNullString(productcode))
					.list();
			if(supplierlist.size() != 0){
				supplierlistarray = ResultGeneratorUtil.populateresults(supplierlist, MainEnum.SUPPLIERLIST);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				supplierlistarray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			supplierlistarray.put(exceptionobj);
		}
		return supplierlistarray;
	}
}
