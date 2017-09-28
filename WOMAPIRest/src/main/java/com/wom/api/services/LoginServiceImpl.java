package com.wom.api.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.LoginDao;

public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;
	
	@Override
	public JSONArray getAppVersion() throws Exception{
		return loginDao.getAppVersion();
	}
	
	@Override
	public JSONArray getLoginUser(String userId, String password, String app) throws Exception {
		return loginDao.getLoginUser(userId, password, app);
	}

	@Override
	public JSONArray getStaffInfo(String userId) throws Exception {
		return loginDao.getStaffInfo(userId);
	}

	@Override
	public JSONArray submitForgotPassword(HttpServletRequest request, HttpServletResponse response,
			String emailaddress, String app) throws Exception {
		return loginDao.submitForgotPassword(request, response, emailaddress, app);
	}
}
