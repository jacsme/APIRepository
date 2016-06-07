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
import com.wom.api.services.StockControlService;
import com.wom.api.util.HelperUtil;

@Controller
public class StockControlController {

	@Autowired
	StockControlService stockcontrolService;

	static final Logger logger = Logger.getLogger(StockControlController.class);

	/** GET Request 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getStockControl/{jobid}/{productcode}/{staffcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getStockPlanningGet(@PathVariable("jobid") String jobId, @PathVariable("productcode") String productcode,
			@PathVariable("staffcode") String staffCode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for StockControl");
		
		JSONArray stockplanninglistarray =  new JSONArray();
		
		try { 
			stockplanninglistarray = stockcontrolService.getStockControl(jobId, productcode, staffCode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getStockPlanningGet() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			stockplanninglistarray.put(exceptionobj);
		}
		return stockplanninglistarray;
	}
	
	@RequestMapping(value = "submitStockControl/{jobid}/{productcode}/{storecode}/{stockunit:.+}/{amount:.+}/{staffcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray submitStockPlanningGET(@PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, 
			@PathVariable("storecode") String storecode, @PathVariable("stockunit") String stockunit, @PathVariable("amount") String amount,
			@PathVariable("staffcode") String staffcode) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Submit to Stock Control Table");
		
		JSONArray stockcontrolstatusarray =  new JSONArray();
		try {
			stockcontrolstatusarray = stockcontrolService.setStockControl(jobid, productcode, storecode, stockunit, amount, staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitStockPlanningGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			stockcontrolstatusarray.put(exceptionobj);
		}
		return stockcontrolstatusarray;
	}
	
	/** Post Request 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getStockControl/{jobid}/{productcode}/{staffcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getStockPlanningPOST(@PathVariable("jobid") String jobId, @PathVariable("productcode") String productcode,
			@PathVariable("staffcode") String staffCode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for Stock Control");
		
		JSONArray stockcontrollistarray =  new JSONArray();
		
		try { 
			stockcontrollistarray = stockcontrolService.getStockControl(jobId, productcode, staffCode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getStockPlanningPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			stockcontrollistarray.put(exceptionobj);
		}
		return stockcontrollistarray;
	}
	
	@RequestMapping(value = "submitStockControl/{jobid}/{productcode}/{storecode}/{stockunit:.+}/{amount:.+}/{staffcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray submitStockPlanningPOST(@PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, 
			@PathVariable("storecode") String storecode, @PathVariable("stockunit") String stockunit, 
			@PathVariable("amount") String amount, @PathVariable("staffcode") String staffcode) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Submit to Stock Control Table");
		
		JSONArray stockcontrolgstatusarray =  new JSONArray();
		try {
			
			stockcontrolgstatusarray = stockcontrolService.setStockControl(jobid, productcode, storecode, stockunit, amount, staffcode);
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitStockPlanningPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			stockcontrolgstatusarray.put(exceptionobj);
		}
		return stockcontrolgstatusarray;
	}
	
}
