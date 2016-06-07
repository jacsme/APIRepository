package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;

public interface MailBoxService {
	public JSONArray getMailBox(String customercode) throws Exception;
}
