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
import com.wom.api.services.ReportService;
import com.wom.api.util.HelperUtil;

@Controller
public class ReportController {

	@Autowired
	ReportService reportService;
	
	static final Logger logger = Logger.getLogger(ReportController.class);
	
	@RequestMapping(value = "getProductCategoryCounts", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getProductCountsGet() throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getProductCounts");
		
		JSONArray productlistarray = new JSONArray();
		
		try {
			productlistarray = reportService.generateProductCategoryCounts();
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getProductCountsGet() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			productlistarray.put(exceptionobj);
		}
		return productlistarray;
	}
	
	@RequestMapping(value = "getJobListCounts/{staffcode}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getJoblistCountsGET(@PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getJobListCountsGET");
		
		JSONArray joblistarray = new JSONArray();
		
		try {
			joblistarray = reportService.generateJobCounts(staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getJoblistCountsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			joblistarray.put(exceptionobj);
		}
		return joblistarray;
	}
	
	@RequestMapping(value = "getDepartmentJobListCounts/{departmentname}/{staffcode}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getDepartmentJobListCountsGET(@PathVariable("departmentname") String departmentname, @PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getDepartmentJobListCountsGET");
		
		JSONArray departmentjoblistarray = new JSONArray();
		
		try {
			departmentjoblistarray = reportService.generateDepartmentJobCounts(departmentname, staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getDepartmentJobListCountsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			departmentjoblistarray.put(exceptionobj);
		}
		return departmentjoblistarray;
	}
	
	@RequestMapping(value = "getMyJobListCounts/{staffcode}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getMyJobListCountsGET(@PathVariable("staffcode") String staffcode) throws Exception{
		logger.info("Request for getMyJobListCountsGET");
		
		JSONArray myjoblistarray = new JSONArray();
		
		try {
			myjoblistarray = reportService.generateMyJobCounts(staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getMyJobListCountsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			myjoblistarray.put(exceptionobj);
		}
		return myjoblistarray;
	}
	
	@RequestMapping(value = "getDepartmentMyJobListCounts/{staffcode}/{departmentname}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getMyJobListCountsGET(@PathVariable("staffcode") String staffcode, @PathVariable("departmentname") String departmentname) throws Exception{
		logger.info("Request for getMyJobListCountsGET");
		
		JSONArray departmentmyjoblistarray = new JSONArray();
		
		try {
			departmentmyjoblistarray = reportService.generateDepartmentMyJobCounts(staffcode, departmentname);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getMyJobListCountsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			departmentmyjoblistarray.put(exceptionobj);
		}
		return departmentmyjoblistarray;
	}
	
	@RequestMapping(value = "getManagementCounts/{staffcode}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getManagementCountsGET(@PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getManagementCountsGET");
		
		JSONArray managementarray = new JSONArray();
		
		try {
			managementarray = reportService.generateManagementCounts(staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getManagementCountsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			managementarray.put(exceptionobj);
		}
		return managementarray;
	}
	
	/** POST Methods **/
	
	@RequestMapping(value = "getJobListCounts/{staffcode}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getJoblistCountsPOST(@PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getJobListCountsPOST");
		
		JSONArray joblistarray = new JSONArray();
		
		try {
			joblistarray = reportService.generateJobCounts(staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getJoblistCountsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			joblistarray.put(exceptionobj);
		}
		return joblistarray;
	}
	
	@RequestMapping(value = "getDepartmentJobListCounts/{departmentname}/{staffcode}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getDepartmentJobListCountsPOST(@PathVariable("departmentname") String departmentname, @PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getDepartmentJobListCountsPOST");
		
		JSONArray departmentjoblistarray = new JSONArray();
		
		try {
			departmentjoblistarray = reportService.generateDepartmentJobCounts(departmentname, staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getDepartmentJobListCountsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			departmentjoblistarray.put(exceptionobj);
		}
		return departmentjoblistarray;
	}
	
	@RequestMapping(value = "getMyJobListCounts/{staffcode}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getMyJobListCountsPOST(@PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getMyJobListCountsPOST");
		
		JSONArray myjoblistarray = new JSONArray();
		
		try {
			myjoblistarray = reportService.generateMyJobCounts(staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getMyJobListCountsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			myjoblistarray.put(exceptionobj);
		}
		return myjoblistarray;
	}
	
	@RequestMapping(value = "getDepartmentMyJobListCounts/{staffcode}/{departmentname}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getMyJobListCountsPOST(@PathVariable("staffcode") String staffcode, @PathVariable("departmentname") String departmentname) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getMyJobListCountsPOST");
		
		JSONArray departmentmyjoblistarray = new JSONArray();
		
		try {
			departmentmyjoblistarray = reportService.generateDepartmentMyJobCounts(staffcode, departmentname);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getMyJobListCountsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			departmentmyjoblistarray.put(exceptionobj);
		}
		return departmentmyjoblistarray;
	}
	@RequestMapping(value = "getManagementCounts/{staffcode}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getManagementCountsPOST(@PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getManagementCountsPOST");
		
		JSONArray managementarray = new JSONArray();
		
		try {
			managementarray = reportService.generateManagementCounts(staffcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getManagementCountsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			managementarray.put(exceptionobj);
		}
		return managementarray;
	}
}
