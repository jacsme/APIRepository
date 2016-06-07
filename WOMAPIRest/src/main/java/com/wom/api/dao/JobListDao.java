package com.wom.api.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.hibernate.Session;

import com.wom.api.model.JobList;
import com.wom.api.model.JobListSO;

public interface JobListDao {

	public JSONArray getJobList(String jobtype, String staffcode) throws Exception;
	public JSONArray getMyJobList(String jobtype, String staffcode) throws Exception;
	public String createJobList(String jobid, String productcode, String departmentcode, String jobtype, String suppliercode, Session session) throws Exception;
	public String createJobListSO(String jobid, String productcode, String departmentcode, String jobtype, String suppliercode, Session session) throws Exception;
	public JobList getProductJobList(String jobid, String productcode, String departmentcode, String jobtype, String suppliercode, Session session) throws Exception;
	public JobListSO getProductJobListSO(String sjobid, String sproductcode, String sdepartmentcode, String sjobtype, String ssuppliercode, Session session) throws Exception;
	public JobList getStackingProductJobList(String productcode, String departmentcode, String jobtype, String suppliercode, Session session) throws Exception;
	public List<JobList> checkProductRequest(String productcode, Session session) throws Exception;
	public JSONArray cancelMyJob(String jobid, String jobtype) throws Exception;
}
