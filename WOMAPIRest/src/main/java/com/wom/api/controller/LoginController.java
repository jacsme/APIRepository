package com.wom.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

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
import com.wom.api.services.LoginService;
import com.wom.api.services.ReportService;
import com.wom.api.util.HelperUtil;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	ReportService reportService;
	
	static final Logger logger = Logger.getLogger(LoginController.class);

	/** GET Request **/
	
	@RequestMapping(value = "getAppVersion", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getAppVersionGET() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT +" Request for App Version");
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY +" Request for App Version");
		
		JSONArray appversionarray = new JSONArray();
		
		try{
			appversionarray = loginService.getAppVersion();
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getAppVersionGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			appversionarray.put(exceptionobj);
		}
		return appversionarray;
	}
	
	/** App - [Management or Grocery] **/
	@RequestMapping(value = "login/{emailaddress:.+}/{password:.+}/{app}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray logEmployeeGET(@PathVariable("emailaddress") String emailaddress, 
			@PathVariable("password") String password, @PathVariable("app") String app) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for Authentication");
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for Authentication");
		
		JSONArray loginuserArray = new JSONArray();
		
		try {
			loginuserArray = loginService.getLoginUser(emailaddress, password, app);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("logEmployeeGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			loginuserArray.put(exceptionobj);
		}
		return loginuserArray;
	}
	
	@RequestMapping(value = "getStaffInfo/{staffcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getStaffInfoGET(@PathVariable("staffcode") String staffcode) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for Authentication");
		
		JSONArray staffinfoarray = new JSONArray();
		
		try {
			staffinfoarray = loginService.getStaffInfo(staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getStaffInfoGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			staffinfoarray.put(exceptionobj);
		}
		return staffinfoarray;
	}
	
	@RequestMapping(value = "submitForgotPassword/{emailaddress:.+}/{app:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray submitForgotPasswordGET(@Context final HttpServletRequest request, @Context HttpServletResponse response, 
			@PathVariable("emailaddress") String emailaddress, @PathVariable("app") String app) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " submitForgotPassword/" + emailaddress + "/" + app );
		
		JSONArray forgotpasswordarray = new JSONArray();
		
		try {
			forgotpasswordarray = loginService.submitForgotPassword(request, response, emailaddress, app);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitForgotPasswordGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			forgotpasswordarray.put(exceptionobj);
		}
		return forgotpasswordarray;
	}
	
	/** POST Request 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getAppVersion", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getAppVersionPOST() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for App Version");
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for App Version");
		
		JSONArray appversionarray = new JSONArray();
		
		try{
			appversionarray = loginService.getAppVersion();
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getAppVersionPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			appversionarray.put(exceptionobj);
		}
		return appversionarray;
	}
	
	@RequestMapping(value = "login/{emailaddress:.+}/{password:.+}/{app}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray logEmployeePOST(@PathVariable("emailaddress") String emailaddress, 
			@PathVariable("password") String password, @PathVariable("app") String app) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for Authentication");
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for Authentication");
		
		JSONArray loginuserArray = new JSONArray();
		
		try {
			loginuserArray = loginService.getLoginUser(emailaddress, password, app);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("logEmployeePOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			loginuserArray.put(exceptionobj);
		}
		return loginuserArray;
	}
	
	@RequestMapping(value = "getStaffInfo/{staffcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getStaffInfoPOST(@PathVariable("staffcode") String staffcode) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for Staff Info");
		
		JSONArray staffinfoarray = new JSONArray();
		
		try {
			staffinfoarray = loginService.getStaffInfo(staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getStaffInfoPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			staffinfoarray.put(exceptionobj);
		}
		return staffinfoarray;
	}
	
	@RequestMapping(value = "submitForgotPassword/{emailaddress:.+}/{app:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray submitForgotPasswordPOST(@Context final HttpServletRequest request, @Context HttpServletResponse response, 
			@PathVariable("emailaddress") String emailaddress, @PathVariable("app") String app) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " submitForgotPassword/" + emailaddress + "/" + app );
		
		JSONArray forgotpasswordarray = new JSONArray();
		
		try {
			forgotpasswordarray = loginService.submitForgotPassword(request, response, emailaddress, app);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitForgotPasswordPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			forgotpasswordarray.put(exceptionobj);
		}
		return forgotpasswordarray;
	}
}
