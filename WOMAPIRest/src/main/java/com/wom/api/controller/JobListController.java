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
import com.wom.api.services.JobListService;
import com.wom.api.services.ReportService;
import com.wom.api.util.HelperUtil;

@Controller
public class JobListController {
	
	@Autowired
	JobListService joblistService;
	
	@Autowired
	ReportService reportService;
	
	static final Logger logger = Logger.getLogger(JobListController.class);
	
	/** GET REquest 
	 * @throws JSONException **/ 
	
	@RequestMapping(value = "getJobList/{jobtype}/{staffcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getJobListGET(@PathVariable("jobtype") String jobtype, @PathVariable("staffcode") String staffcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT +" Request for getJobListGET()");
		
		JSONArray joblistarray = new JSONArray();
		
		try{
			joblistarray = joblistService.getJobList(jobtype, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getJobListGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			joblistarray.put(exceptionobj);
		}
		return joblistarray;
	}
	
	@RequestMapping(value = "getMyJobList/{jobtype}/{staffcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getMyJobListGET(@PathVariable("jobtype") String jobtype, @PathVariable("staffcode") String staffcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getMyJobListGET()");
		
		JSONArray joblistarray = new JSONArray();
		
		try{
			joblistarray = joblistService.getMyJobList(jobtype, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getMyJobListGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			joblistarray.put(exceptionobj);
		}
		return joblistarray;
	}
	
	@RequestMapping(value = "cancelMyJob/{jobid}/{jobtype}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray cancelMyJobGET(@PathVariable("jobid") String jobid, @PathVariable("jobtype") String jobtype) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for cancelMyJobGET()");
		logger.info("cancelMyJob/" + jobid + "/" +jobtype);
		
		JSONArray cancelmyjobarray = new JSONArray();
		
		try{
			cancelmyjobarray = joblistService.cancelMyJob(jobid, jobtype);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("cancelMyJobGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			cancelmyjobarray.put(exceptionobj);
		}
		return cancelmyjobarray;
	}
	
	/** POST REquest 
	 * @throws JSONException **/ 
	
	@RequestMapping(value = "getJobList/{jobtype}/{staffcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getJobListPOST(@PathVariable("jobtype") String jobtype, @PathVariable("staffcode") String staffcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getJobListPOST()");
		
		JSONArray joblistarray = new JSONArray();
		
		try{
			joblistarray = joblistService.getJobList(jobtype, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getJobListPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			joblistarray.put(exceptionobj);
		}
		return joblistarray;
	}
	
	@RequestMapping(value = "getMyJobList/{jobtype}/{staffcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getMyJobListPOST(@PathVariable("jobtype") String jobtype, @PathVariable("staffcode") String staffcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getMyJobListPOST()");
		
		JSONArray joblistarray = new JSONArray();
		
		try{
			joblistarray = joblistService.getMyJobList(jobtype, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getMyJobListPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			joblistarray.put(exceptionobj);
		}
		return joblistarray;
	}
	
	@RequestMapping(value = "cancelMyJob/{jobid}/{jobtype}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray cancelMyJobPOST(@PathVariable("jobid") String jobid, @PathVariable("jobtype") String jobtype) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for cancelMyJobPOST()");
		logger.info("cancelMyJob/" + jobid + "/" +jobtype);
		
		JSONArray cancelmyjobarray = new JSONArray();
		
		try{
			cancelmyjobarray = joblistService.cancelMyJob(jobid, jobtype);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("cancelMyJobPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			cancelmyjobarray.put(exceptionobj);
		}
		return cancelmyjobarray;
	}

}
