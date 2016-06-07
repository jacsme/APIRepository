package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.ReportDao;

public class ReportServiceImpl implements ReportService{

	@Autowired
	ReportDao reportDao;
	
	@Override
	public JSONArray generateProductCategoryCounts() throws Exception {
		return reportDao.generateProductCategoryCounts();
	}
	
	@Override
	public JSONArray generateProductCounts() throws Exception{
		return reportDao.generateProductCounts();
	}
	
	@Override
	public JSONArray generateJobCounts(String staffcode) throws Exception{
		return reportDao.generateJobCounts(staffcode);
	}
	
	@Override
	public JSONArray generateMyJobCounts(String staffcode) throws Exception{
		return reportDao.generateMyJobCounts(staffcode);
	}
	
	@Override
	public JSONArray generateDepartmentJobCounts(String departmentname, String staffcode) throws Exception{
		return reportDao.generateDepartmentJobCounts(departmentname, staffcode);
	}

	@Override
	public JSONArray generateDepartmentMyJobCounts(String staffcode, String departmentname) throws Exception {
		return reportDao.generateDepartmentMyJobCounts(staffcode, departmentname);
	}

	@Override
	public JSONArray generateManagementCounts(String staffcode) throws Exception {
		return reportDao.generateManagementCounts(staffcode);
	}
}
