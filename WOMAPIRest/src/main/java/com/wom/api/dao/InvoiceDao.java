package com.wom.api.dao;

import org.codehaus.jettison.json.JSONArray;

public interface InvoiceDao {
	
	public JSONArray getInvoicePayable() throws Exception;

}
