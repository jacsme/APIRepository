package com.wom.api.dao;

import org.codehaus.jettison.json.JSONArray;

public interface ReportDao {
	
	public JSONArray generateProductCategoryCounts() throws Exception;
	public JSONArray generateProductCounts() throws Exception;
	public JSONArray generateJobCounts(String staffcode) throws Exception;
	public JSONArray generateDepartmentJobCounts(String departmentname, String staffcode) throws Exception;
	public JSONArray generateMyJobCounts(String staffcode) throws Exception;
	public JSONArray generateDepartmentMyJobCounts(String staffcode, String departmentname) throws Exception;
	public JSONArray generateManagementCounts(String staffcode) throws Exception;

}
