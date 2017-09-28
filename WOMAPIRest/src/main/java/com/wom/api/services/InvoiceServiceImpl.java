package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.InvoiceDao;

public class InvoiceServiceImpl implements InvoiceService{

	@Autowired
	InvoiceDao invoiceDao;
	
	@Override
	public JSONArray getInvoicePayable() throws Exception {
		// TODO Auto-generated method stub
		return invoiceDao.getInvoicePayable();
	}

}
