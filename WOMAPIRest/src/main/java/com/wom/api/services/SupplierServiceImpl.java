package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.SupplierDao;

public class SupplierServiceImpl implements SupplierService {

	@Autowired
	SupplierDao supplierDao;
	
	@Override
	public JSONArray getSupplierList(String productcode) throws Exception {
		return supplierDao.getSupplierList(productcode);
	}
	
	public JSONArray getSupplierListAll() throws Exception{
		return supplierDao.getSupplierListAll();
	}
	@Override
	public JSONArray submitSupplier(String suppliername, String address, String phone, String fax, String website, 
			String email, String contactperson, String gstid, String contactpersonphone, String productcode) throws Exception{
		return supplierDao.submitSupplier(suppliername, address, phone, fax, website, email, contactperson, gstid, contactpersonphone, productcode);
	}

	@Override
	public JSONArray searchSupplier(String keyword) throws Exception {
		return supplierDao.searchSupplier(keyword);
	}
}
