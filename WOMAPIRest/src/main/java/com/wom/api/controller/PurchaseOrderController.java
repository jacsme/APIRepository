package com.wom.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wom.api.constant.StatusCode;
import com.wom.api.services.PurchaseOrderService;
import com.wom.api.util.HelperUtil;

@Controller
public class PurchaseOrderController {
	
	@Autowired
	PurchaseOrderService purchaseorderService;
	
	static final Logger logger = Logger.getLogger(PurchaseOrderController.class);
	
	/** GET Methods **/
	@RequestMapping(value = "getApprovedPurchaseOrder/{jobid}/{suppliercode}/{staffcode}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getApprovedPurchaseOrderGET(@PathVariable("jobid") String jobid, @PathVariable("suppliercode") String suppliercode, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getApprovedPurchaseOrderGET");
		
		JSONArray approvedpurchaseorderarray = new JSONArray();
		
		try{
			approvedpurchaseorderarray = purchaseorderService.getApprovedPurchaseOrder(jobid, suppliercode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getApprovedPurchaseOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			approvedpurchaseorderarray.put(exceptionobj);
		}
		return approvedpurchaseorderarray;
	}
	
	@RequestMapping(value="emailPurchaseOrder/{storecode}/{purchaseordercode}/{jobid}/{suppliercode}/{staffcode}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray emailPurchaseOrderGET(@Context final HttpServletRequest request,  @Context HttpServletResponse response, @PathVariable("storecode") String storecode,
			  @PathVariable("purchaseordercode") String purchaseordercode, @PathVariable("jobid") String jobid, @PathVariable("suppliercode") String suppliercode, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " emailPurchaseOrder/" + storecode + "/" + purchaseordercode + "/" + jobid + "/" + suppliercode + "/" + staffcode);

		JSONArray emailpurchaseorderarray = new JSONArray();
		
		try{
			emailpurchaseorderarray = purchaseorderService.emailPurchaseOrder(request, response, storecode, purchaseordercode, jobid, suppliercode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("emailPurchaseOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			emailpurchaseorderarray.put(exceptionobj);
		}
		return emailpurchaseorderarray;
	}
	
	
	@RequestMapping(value="submitNewPO/{storecode}/{productcode}/{stockunit}/{staffcode}/{uom}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray submitNewPOGET(@PathVariable("storecode") String storecode, @PathVariable("productcode") String productcode, 
			@PathVariable("stockunit") String stockunit, @PathVariable("staffcode") String staffcode, @PathVariable("uom") String uom) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " submitNewPO/" + storecode + "/" + productcode + "/" + stockunit + "/" + staffcode + "/" + uom);
		
		JSONArray submitNewPOerarray = new JSONArray();
		
		try{
			submitNewPOerarray = purchaseorderService.submitNewPO(storecode, productcode, stockunit, staffcode, uom);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitNewPOGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitNewPOerarray.put(exceptionobj);
		}
		return submitNewPOerarray;
	}
	
	@RequestMapping(value="emailPOLogin/{staffcode:.+}/{password:.+}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray emailPOLoginGET(@PathVariable("staffcode") String staffcode, @PathVariable("password") String password) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " emailPOLogin/" + staffcode + "/" + password);
		
		JSONArray emailpologinarray = new JSONArray();
		
		try{
			emailpologinarray = purchaseorderService.emailPOLogin(staffcode, password);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("emailPOLoginGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			emailpologinarray.put(exceptionobj);
		}
		return emailpologinarray;
	}
	
	/** POST Methods **/
	@RequestMapping(value = "getApprovedPurchaseOrder/{jobid}/{suppliercode}/{staffcode}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getApprovedPurchaseOrderPOST(@PathVariable("jobid") String jobid, @PathVariable("suppliercode") String suppliercode, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getApprovedPurchaseOrderPOST");
		
		JSONArray approvedpurchaseorderarray = new JSONArray();
		
		try{
			approvedpurchaseorderarray = purchaseorderService.getApprovedPurchaseOrder(jobid, suppliercode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getApprovedPurchaseOrderPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			approvedpurchaseorderarray.put(exceptionobj);
		}
		return approvedpurchaseorderarray;
	}
	
	@RequestMapping(value = "emailPurchaseOrder/{storecode}/{purchaseordercode}/{jobid}/{suppliercode}/{staffcode}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray emailPurchaseOrderPOST(@Context final HttpServletRequest request,  @Context HttpServletResponse response, @PathVariable("storecode") String storecode,
			  @PathVariable("purchaseordercode") String purchaseordercode, @PathVariable("jobid") String jobid, @PathVariable("suppliercode") String suppliercode, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " emailPurchaseOrder/" + storecode + "/" + purchaseordercode + "/" + jobid + "/" + suppliercode + "/" + staffcode);
		
		JSONArray emailpurchaseorderarray = new JSONArray();
		
		try{
			emailpurchaseorderarray = purchaseorderService.emailPurchaseOrder(request, response, storecode, purchaseordercode, jobid, suppliercode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("emailPurchaseOrderPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			emailpurchaseorderarray.put(exceptionobj);
		}
		return emailpurchaseorderarray;
	}
	
	@RequestMapping(value="submitNewPO/{storecode}/{productcode}/{stockunit}/{staffcode}/{uom}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray submitNewPOPOST(@PathVariable("storecode") String storecode, @PathVariable("productcode") String productcode, 
			@PathVariable("stockunit") String stockunit, @PathVariable("staffcode") String staffcode,  @PathVariable("uom") String uom) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " submitNewPO/" + storecode + "/" + productcode + "/" + stockunit + "/" + staffcode + "/" + uom);
		
		JSONArray submitNewPOerarray = new JSONArray();
		
		try{
			submitNewPOerarray = purchaseorderService.submitNewPO(storecode, productcode, stockunit, staffcode, uom);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitNewPOPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitNewPOerarray.put(exceptionobj);
		}
		return submitNewPOerarray;
	}
	
	@RequestMapping(value="emailPOLogin/{staffcode:.+}/{password:.+}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray emailPOLoginPOST(@PathVariable("staffcode") String staffcode, @PathVariable("password") String password) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " emailPOLogin/" + staffcode + "/" + password);
		
		JSONArray emailpologinarray = new JSONArray();
		
		try{
			emailpologinarray = purchaseorderService.emailPOLogin(staffcode, password);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("emailPOLoginPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			emailpologinarray.put(exceptionobj);
		}
		return emailpologinarray;
	}
}
