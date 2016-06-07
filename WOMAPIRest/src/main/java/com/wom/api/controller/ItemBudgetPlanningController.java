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
import com.wom.api.services.ItemBudgetPlanningService;
import com.wom.api.util.HelperUtil;

@Controller
public class ItemBudgetPlanningController {
	
	@Autowired
	ItemBudgetPlanningService itembudgetplanningService;
	
	static final Logger logger = Logger.getLogger(ItemBudgetPlanningController.class);
	
	/** GET REquest 
	 * @throws JSONException **/ 
	
	@RequestMapping(value = "getItemBudgetPlanning/{jobid}/{productcode}/{staffcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getItemBudgetPlanningGet(@PathVariable("jobid") String jobId, @PathVariable("productcode") String productcode,
			@PathVariable("staffcode") String staffCode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT +" Request to get ItemBudgetplanningGET");
		logger.info("getItemBudgetPlanning/" + jobId + "/" + productcode + "/" + staffCode) ;
		
		JSONArray itembudgetplanningarray =  new JSONArray();
		
		try { 
			itembudgetplanningarray = itembudgetplanningService.getItemBudgetPlanning(jobId, productcode, staffCode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getItemBudgetPlanningGet() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			itembudgetplanningarray.put(exceptionobj);
		}
		return itembudgetplanningarray;
	}
	
	@RequestMapping(value = "submitProductQuotation/{itembudgetcode}/{jobid}/{suppliercode}/{productcode}/{packingunit:.+}/{packingprice:.+}/{packingquantity:.+}/{moq:.+}/{gst:.+}/{shippingdays:.+}/{paymentterms:.+}/{staffcode}/{requestquantity:.+}/{requesttotalunit:.+}/{requesttotalamount:.+}/{storecode}/{requesttotalamountwithgst:.+}/{requestedpackingweight:.+}/{requestedpackingmass:.+}", 
			method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray submitProductQuotationGET(@PathVariable("itembudgetcode") String itembudgetcode, @PathVariable("jobid") String jobid, @PathVariable("suppliercode") String suppliercode, @PathVariable("productcode") String productcode, 
			@PathVariable("packingunit") String packingunit, @PathVariable("packingprice") String packingprice, @PathVariable("packingquantity") String packingquantity, 
			@PathVariable("moq") String moq, @PathVariable("gst") String gst, @PathVariable("shippingdays") String shippingdays, @PathVariable("paymentterms") String paymentterms, @PathVariable("staffcode") String staffcode, 
			@PathVariable("requestquantity") String requestquantity, @PathVariable("requesttotalunit") String requesttotalunit, @PathVariable("requesttotalamount") String requesttotalamount, @PathVariable("storecode") String storecode, 
			@PathVariable("requesttotalamountwithgst") String requesttotalamountwithgst, @PathVariable("requestedpackingweight") String requestedpackingweight, @PathVariable("requestedpackingmass") String requestedpackingmass) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for submitProductQuotationGET()");
		logger.info("submitProductQuotation/" + itembudgetcode + "/" + jobid + "/" + suppliercode + "/" + productcode + "/" + packingunit + "/" + packingprice + "/" + packingquantity + "/" + moq + "/" + gst + "/" + shippingdays + "/" + paymentterms + "/" + staffcode + "/" + requestquantity + "/" + requesttotalunit + "/" + requesttotalamount + "/" + storecode + "/" + requesttotalamountwithgst + "/"+ requestedpackingweight + "/" + requestedpackingmass);
		
		JSONArray productquotationarray = new JSONArray();
		
		try{
			productquotationarray = itembudgetplanningService.submitProductQuotation(itembudgetcode, jobid, suppliercode, productcode, packingunit, packingprice, packingquantity, moq, gst, shippingdays, 
					paymentterms, staffcode, requestquantity, requesttotalunit, requesttotalamount, storecode, requesttotalamountwithgst, requestedpackingweight, requestedpackingmass);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitProductQuotationGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			productquotationarray.put(exceptionobj);
		}
		return productquotationarray;
	}
	
	/** POST Request 
	 * @throws JSONException **/ 
	
	@RequestMapping(value = "getItemBudgetPlanning/{jobid}/{productcode}/{staffcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getItemBudgetPlanningPOST(@PathVariable("jobid") String jobId, @PathVariable("productcode") String productcode,
			@PathVariable("staffcode") String staffCode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT +" Request to get ItemBudgetplanningPOST");
		logger.info("getItemBudgetPlanning/" + jobId + "/" + productcode + "/" + staffCode) ;
		
		JSONArray itembudgetplanningarray =  new JSONArray();
		
		try { 
			itembudgetplanningarray = itembudgetplanningService.getItemBudgetPlanning(jobId, productcode, staffCode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			itembudgetplanningarray.put(exceptionobj);
			logger.error("getItemBudgetPlanningPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return itembudgetplanningarray;
	}
	
	@RequestMapping(value = "submitProductQuotation/{itembudgetcode}/{jobid}/{suppliercode}/{productcode}/{packingunit:.+}/{packingprice:.+}/{packingquantity:.+}/{moq:.+}/{gst:.+}/{shippingdays:.+}/{paymentterms:.+}/{staffcode}/{requestquantity:.+}/{requesttotalunit:.+}/{requesttotalamount:.+}/{storecode}/{requesttotalamountwithgst:.+}/{requestedpackingweight:.+}/{requestedpackingmass:.+}", 
			method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray submitProductQuotationPOST(@PathVariable("itembudgetcode") String itembudgetcode, @PathVariable("jobid") String jobid, @PathVariable("suppliercode") String suppliercode, @PathVariable("productcode") String productcode, 
			@PathVariable("packingunit") String packingunit, @PathVariable("packingprice") String packingprice, @PathVariable("packingquantity") String packingquantity, 
			@PathVariable("moq") String moq, @PathVariable("gst") String gst, @PathVariable("shippingdays") String shippingdays, @PathVariable("paymentterms") String paymentterms, @PathVariable("staffcode") String staffcode, 
			@PathVariable("requestquantity") String requestquantity, @PathVariable("requesttotalunit") String requesttotalunit, @PathVariable("requesttotalamount") String requesttotalamount, @PathVariable("storecode") String storecode, 
			@PathVariable("requesttotalamountwithgst") String requesttotalamountwithgst, @PathVariable("requestedpackingweight") String requestedpackingweight, @PathVariable("requestedpackingmass") String requestedpackingmass) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for submitProductQuotationPOST()");
		logger.info("submitProductQuotation/" + itembudgetcode + "/" + jobid + "/" + suppliercode + "/" + productcode + "/" + packingunit + "/" + packingprice + "/" + packingquantity + "/" + moq + "/" + gst + "/" + shippingdays + "/" + paymentterms + "/" + staffcode + "/" + requestquantity + "/" + requesttotalunit + "/" + requesttotalamount + "/" + storecode + "/" + requesttotalamountwithgst + "/"+ requestedpackingweight + "/" + requestedpackingmass);
		
		JSONArray productquotationarray = new JSONArray();
		
		try{
			productquotationarray = itembudgetplanningService.submitProductQuotation(itembudgetcode, jobid, suppliercode, productcode, packingunit, packingprice, packingquantity, moq, gst, shippingdays, 
					paymentterms, staffcode, requestquantity, requesttotalunit, requesttotalamount, storecode, requesttotalamountwithgst, requestedpackingweight, requestedpackingmass);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitProductQuotationPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			productquotationarray.put(exceptionobj);
		}
		return productquotationarray;
	}
	
}
