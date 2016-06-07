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
import com.wom.api.services.MailBoxService;

@Controller
public class MailBoxController {

	@Autowired
	MailBoxService mailboxService;

	static final Logger logger = Logger.getLogger(MailBoxController.class);

	/** GET Methods 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getMailBox/{customercode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getMailBoxGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info("Request for getMailBoxGET");
		
		JSONArray mailboxlistarray =  new JSONArray();
		
		try { 
			mailboxlistarray = mailboxService.getMailBox(customercode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			mailboxlistarray.put(exceptionobj);
		}
		return mailboxlistarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getMailBox/{customercode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getMailBoxPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info("Request for getMailBoxPOST");
		
		JSONArray mailboxlistarray =  new JSONArray();
		
		try { 
			mailboxlistarray = mailboxService.getMailBox(customercode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			mailboxlistarray.put(exceptionobj);
		}
		return mailboxlistarray;
	}
}
