package com.wom.api.controller;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wom.api.constant.StatusCode;
import com.wom.api.services.InvoiceService;

@Controller
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	static final Logger logger = Logger.getLogger(InvoiceController.class);
	
	/** GET REquest 
	 * @throws JSONException **/ 
	
	@RequestMapping(value = "getInvoicePayable", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getInvoicePayableGet() throws JSONException{
		logger.info("Request to get getInvoicePayableGet");
		
		JSONArray invoicepayablearray =  new JSONArray();
		
		try { 
			invoicepayablearray = invoiceService.getInvoicePayable();
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			invoicepayablearray.put(exceptionobj);
		}
		return invoicepayablearray;
	}

	/** POST REquest 
	 * @throws JSONException **/ 
	
	@RequestMapping(value = "getInvoicePayable", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getInvoicePayablePOST() throws JSONException{
		logger.info("Request to get getInvoicePayablePOST");
		
		JSONArray invoicepayablearray =  new JSONArray();
		
		try { 
			invoicepayablearray = invoiceService.getInvoicePayable();
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			invoicepayablearray.put(exceptionobj);
		}
		return invoicepayablearray;
	}

}
