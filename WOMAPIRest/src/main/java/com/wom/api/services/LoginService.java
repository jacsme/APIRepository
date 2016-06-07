package com.wom.api.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

public interface LoginService {
	public JSONArray getAppVersion() throws Exception;
	public JSONArray getLoginUser(String userId, String password, String app) throws Exception;
	public JSONArray getStaffInfo(String userId) throws Exception;
	public JSONArray submitForgotPassword(HttpServletRequest request, HttpServletResponse response, String emailaddress, String app) throws Exception;
}
