package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;

public interface JobListService {
	
	public JSONArray getJobList(String jobtype, String staffcode) throws Exception;
	public JSONArray getMyJobList(String jobtype, String staffcode) throws Exception;
	public JSONArray cancelMyJob(String jobid, String jobtype) throws Exception;

}
