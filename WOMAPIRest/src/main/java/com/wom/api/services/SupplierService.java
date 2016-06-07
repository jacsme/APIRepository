package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;

public interface SupplierService {
	
	public JSONArray getSupplierList(String productcode) throws Exception;
	public JSONArray getSupplierListAll() throws Exception;
	public JSONArray submitSupplier(String suppliername, String address, String phone, String fax, String website, 
			String email, String contactperson, String gstid, String contactpersonphone, String productcode) throws Exception;
	public JSONArray searchSupplier(String keyword) throws Exception;
}
