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
import com.wom.api.services.StoreService;
import com.wom.api.util.HelperUtil;

@Controller
public class StoreController {
	@Autowired
	StoreService storeService;

	static final Logger logger = Logger.getLogger(StoreController.class);

	/** GET Methods 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getContactUsInfo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getContactUsInfoGET() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getContactUsInfoGET");
		
		JSONArray contactuslistarray =  new JSONArray();
		
		try { 
			contactuslistarray = storeService.getContactUsInfo();
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getContactUsInfoGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			contactuslistarray.put(exceptionobj);
		}
		return contactuslistarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getContactUsInfo", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getContactUsInfoPOST() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getContactUsInfoPOST");
		
		JSONArray contactuslistarray =  new JSONArray();
		
		try { 
			contactuslistarray = storeService.getContactUsInfo();
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getContactUsInfoPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			contactuslistarray.put(exceptionobj);
		}
		return contactuslistarray;
	}
}
