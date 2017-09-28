package com.wom.api.controller;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wom.api.constant.StatusCode;
import com.wom.api.services.PurchaseFundingService;
import com.wom.api.util.HelperUtil;

@Controller
public class PurchaseFundingController {

	@Autowired
	PurchaseFundingService purchasefundingService;
	
	static final Logger logger = Logger.getLogger(PurchaseFundingController.class);
	
	/** GET Request 
	 * @throws JSONException **/ 
	
	@RequestMapping(value = "getPurchaseFunding/{jobid}/{productcode}/{storecode}/{staffcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getPurchaseFundingGET(@PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, 
			@PathVariable("storecode") String storecode, @PathVariable("staffcode") String staffcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getPurchaseFundingGET()");
		logger.info("getPurchaseFunding/" + jobid + "/" + productcode + "/" + storecode + "/" + staffcode);
		
		JSONArray purchasefundinglistarray = new JSONArray();
		
		try{
			purchasefundinglistarray = purchasefundingService.getPurchaseFunding(jobid, productcode, storecode, staffcode);
			
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getPurchaseFundingGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			purchasefundinglistarray.put(exceptionobj);
		}
		return purchasefundinglistarray;
	}
	
	@RequestMapping(value = "submitPurchaseFunding/{purchasefundingcode}/{jobid}/{productcode}/{originalbudget:.+}/{budgetbalance:.+}/{staffcode}/{purchasedate}/{storecode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray submitPurchaseFundingGET(@PathVariable("purchasefundingcode") String purchasefundingcode, @PathVariable("jobid") String jobid, 
			@PathVariable("productcode") String productcode, @PathVariable("originalbudget") String originalbudget, @PathVariable("budgetbalance") String budgetbalance, @PathVariable("staffcode") String staffcode, @PathVariable("purchasedate") String purchasedate, @PathVariable("storecode") String storecode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for submitPurchaseFundingGET()");
		logger.info("submitPurchaseFunding/" + purchasefundingcode + "/" + jobid + "/" + productcode + "/" + originalbudget + "/" + budgetbalance + "/" + purchasedate  + "/" + storecode) ;
		
		JSONArray submitpurchasefundinglistarray = new JSONArray();
		
		try{
			submitpurchasefundinglistarray = purchasefundingService.submitPurchaseFunding(purchasefundingcode, jobid, productcode, originalbudget, budgetbalance, staffcode, purchasedate, storecode);
			
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitPurchaseFundingGET() ------ StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitpurchasefundinglistarray.put(exceptionobj);
		}
		return submitpurchasefundinglistarray;
	}
	
	/** POST Request 
	 * @throws JSONException **/ 
	
	@RequestMapping(value = "getPurchaseFunding/{jobid}/{productcode}/{storecode}/{staffcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getPurchaseFundingPOST(@PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, 
			@PathVariable("storecode") String storecode, @PathVariable("staffcode") String staffcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getPurchaseFundingPOST()");
		logger.info("getPurchaseFunding/" + jobid + "/" + productcode + "/" + storecode + "/" + staffcode);
		JSONArray purchasefundinglistarray = new JSONArray();
		
		try{
			purchasefundinglistarray = purchasefundingService.getPurchaseFunding(jobid, productcode, storecode, staffcode);
			
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getPurchaseFundingPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			purchasefundinglistarray.put(exceptionobj);
		}
		return purchasefundinglistarray;
	}
	
	@RequestMapping(value = "submitPurchaseFunding/{purchasefundingcode}/{jobid}/{productcode}/{originalbudget:.+}/{budgetbalance:.+}/{staffcode}/{purchasedate}/{storecode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray submitPurchaseFundingPOST(@PathVariable("purchasefundingcode") String purchasefundingcode, @PathVariable("jobid") String jobid, 
			@PathVariable("productcode") String productcode, @PathVariable("originalbudget") String originalbudget, @PathVariable("budgetbalance") String budgetbalance, @PathVariable("staffcode") String staffcode, @PathVariable("purchasedate") String purchasedate, @PathVariable("storecode") String storecode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for submitPurchaseFundingPOST()");
		logger.info("submitPurchaseFunding/" + purchasefundingcode + "/" + jobid + "/" + productcode + "/" + originalbudget + "/" + budgetbalance + "/" + purchasedate  + "/" + storecode) ;
		
		JSONArray submitpurchasefundinglistarray = new JSONArray();
		
		try{
			submitpurchasefundinglistarray = purchasefundingService.submitPurchaseFunding(purchasefundingcode, jobid, productcode, originalbudget, budgetbalance, staffcode, purchasedate, storecode);
			
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitPurchaseFundingPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitpurchasefundinglistarray.put(exceptionobj);
		}
		return submitpurchasefundinglistarray;
	}
	
}
