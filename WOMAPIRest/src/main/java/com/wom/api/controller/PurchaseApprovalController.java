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
import com.wom.api.services.PurchaseApprovalService;
import com.wom.api.util.HelperUtil;

@Controller
public class PurchaseApprovalController {

	@Autowired
	PurchaseApprovalService purchaseapprovalService;
	
	static final Logger logger = Logger.getLogger(PurchaseApprovalController.class);
	
	/** GET Request 
	 * @throws JSONException **/ 

	@RequestMapping(value = "getPurchaseApproval/{jobid}/{productcode}/{staffcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getPurchaseApprovalGET(@PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, 
			@PathVariable("staffcode") String staffcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getPurchaseApprovalGET()");
		
		JSONArray purchaseapprovallistarray = new JSONArray();
		
		try{
			purchaseapprovallistarray = purchaseapprovalService.getPurchaseApproval(jobid, productcode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getPurchaseApprovalGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			purchaseapprovallistarray.put(exceptionobj);
		}
		return purchaseapprovallistarray;
	}
	
	@RequestMapping(value = "approvePurchaseApproval/{purchaseapprovalcode}/{jobid}/{productcode}/{suppliercode}/{staffcode}/{requestquantity:.+}/{requestunit:.+}/{totalamount:.+}/{storecode}/{balancebudget:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray approvePurchaseApprovalGET(@PathVariable("purchaseapprovalcode") String purchaseapprovalcode, @PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, @PathVariable("suppliercode") String suppliercode,
			@PathVariable("staffcode") String staffcode, @PathVariable("requestquantity") String requestquantity, @PathVariable("requestunit") String requestunit, @PathVariable("totalamount") String totalamount, @PathVariable("storecode") String storecode, 
			@PathVariable("balancebudget") String balancebudget) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " approvePurchaseApproval/" + purchaseapprovalcode + "/" + jobid + "/" + productcode + "/" + suppliercode + "/" + staffcode + "/" + requestquantity + "/" + requestunit + "/" + totalamount + "/" + storecode + "/" + balancebudget);
		
		JSONArray approvepurchaseapprovalListarray = new JSONArray();
		
		try{
			approvepurchaseapprovalListarray = purchaseapprovalService.approvePurchaseApproval(purchaseapprovalcode, jobid, productcode, suppliercode, 
					staffcode, requestquantity, requestunit, totalamount, storecode, balancebudget);
			
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("approvePurchaseApprovalGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			approvepurchaseapprovalListarray.put(exceptionobj);
		}
		return approvepurchaseapprovalListarray;
	}
	
	@RequestMapping(value = "denyPurchaseApproval/{purchaseapprovalcode}/{jobid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray denyPurchaseApprovalGET(@PathVariable("purchaseapprovalcode") String purchaseapprovalcode, @PathVariable("jobid") String jobid) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for denyPurchaseApprovalGET()");
		
		JSONArray denyPurchaseApprovalListarray = new JSONArray();
		
		try{
			denyPurchaseApprovalListarray = purchaseapprovalService.denyPurchaseApproval(purchaseapprovalcode, jobid);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("denyPurchaseApprovalGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			denyPurchaseApprovalListarray.put(exceptionobj);
		}
		return denyPurchaseApprovalListarray;
	}
	
	
	/** POST Request 
	 * @throws JSONException **/ 

	@RequestMapping(value = "getPurchaseApproval/{jobid}/{productcode}/{staffcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getPurchaseApprovalPOST(@PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, 
			@PathVariable("staffcode") String staffcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getPurchaseApprovalPOST()");
		
		JSONArray purchaseapprovallistarray = new JSONArray();
		
		try{
			purchaseapprovallistarray = purchaseapprovalService.getPurchaseApproval(jobid, productcode, staffcode);
			
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getPurchaseApprovalPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			purchaseapprovallistarray.put(exceptionobj);
		}
		return purchaseapprovallistarray;
	}
	
	@RequestMapping(value = "approvePurchaseApproval/{purchaseapprovalcode}/{jobid}/{productcode}/{suppliercode}/{staffcode}/{requestquantity:.+}/{requestunit:.+}/{totalamount:.+}/{storecode}/{balancebudget:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray approvePurchaseApprovalPOST(@PathVariable("purchaseapprovalcode") String purchaseapprovalcode, @PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, @PathVariable("suppliercode") String suppliercode,
			@PathVariable("staffcode") String staffcode, @PathVariable("requestquantity") String requestquantity, @PathVariable("requestunit") String requestunit, @PathVariable("totalamount") String totalamount, @PathVariable("storecode") String storecode, 
			@PathVariable("balancebudget") String balancebudget) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " approvePurchaseApproval/" + purchaseapprovalcode + "/" + jobid + "/" + productcode + "/" + suppliercode + "/" + staffcode + "/" + requestquantity + "/" + requestunit + "/" + totalamount + "/" + storecode + "/" + balancebudget);
		
		JSONArray approvepurchaseapprovalListarray = new JSONArray();
		
		try{
			approvepurchaseapprovalListarray = purchaseapprovalService.approvePurchaseApproval(purchaseapprovalcode, jobid, productcode, suppliercode, 
					staffcode, requestquantity, requestunit, totalamount, storecode, balancebudget);
			
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("approvePurchaseApprovalPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			approvepurchaseapprovalListarray.put(exceptionobj);
		}
		return approvepurchaseapprovalListarray;
	}
	
	@RequestMapping(value = "denyPurchaseApproval/{purchaseapprovalcode}/{jobid}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray denyPurchaseApprovalPOST(@PathVariable("purchaseapprovalcode") String purchaseapprovalcode, @PathVariable("jobid") String jobid) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for denyPurchaseApprovalPOST()");
		
		JSONArray denyPurchaseApprovalListarray = new JSONArray();
		
		try{
			denyPurchaseApprovalListarray = purchaseapprovalService.denyPurchaseApproval(purchaseapprovalcode, jobid);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("denyPurchaseApprovalPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			denyPurchaseApprovalListarray.put(exceptionobj);
		}
		return denyPurchaseApprovalListarray;
	}
}
