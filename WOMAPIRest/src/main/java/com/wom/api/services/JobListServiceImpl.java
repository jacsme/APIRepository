package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.JobListDao;

public class JobListServiceImpl implements JobListService {

	@Autowired
	JobListDao joblistDao;
	
	@Override
	public JSONArray getJobList(String jobtype, String staffcode) throws Exception {
		return joblistDao.getJobList(jobtype, staffcode);
	}

	@Override
	public JSONArray getMyJobList(String jobtype, String staffcode) throws Exception {
		return joblistDao.getMyJobList(jobtype, staffcode);
	}

	@Override
	public JSONArray cancelMyJob(String jobid, String jobtype) throws Exception {
		return joblistDao.cancelMyJob(jobid, jobtype);
	}

}
