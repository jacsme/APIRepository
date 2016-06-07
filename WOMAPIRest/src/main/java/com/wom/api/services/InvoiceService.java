package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;

public interface InvoiceService {
	
	public JSONArray getInvoicePayable() throws Exception;

}
