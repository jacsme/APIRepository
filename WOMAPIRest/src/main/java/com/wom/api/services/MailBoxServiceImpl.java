package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.MailBoxDao;

public class MailBoxServiceImpl implements MailBoxService {
	
	@Autowired
	MailBoxDao mailboxDao;
	
	@Override
	public JSONArray getMailBox(String customercode) throws Exception{
		return mailboxDao.getMailBox(customercode);
	}

}
