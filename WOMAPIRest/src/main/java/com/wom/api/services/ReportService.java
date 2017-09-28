package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;

public interface ReportService {

	public JSONArray generateProductCategoryCounts() throws Exception;
	public JSONArray generateProductCounts() throws Exception;
	public JSONArray generateJobCounts(String staffcode) throws Exception;
	public JSONArray generateDepartmentJobCounts(String departmentname, String staffcode) throws Exception;
	public JSONArray generateMyJobCounts(String staffcode) throws Exception;
	public JSONArray generateDepartmentMyJobCounts(String staffcode, String departmentname) throws Exception;
	public JSONArray generateManagementCounts(String staffcode) throws Exception;
}
