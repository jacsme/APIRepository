package com.wom.api.dao;

import org.codehaus.jettison.json.JSONArray;

public interface MailBoxDao {
	public JSONArray getMailBox(String customercode) throws Exception;
}
