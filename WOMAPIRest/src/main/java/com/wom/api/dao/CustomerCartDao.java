package com.wom.api.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

public interface CustomerCartDao {
	
	public JSONArray getCustomerCartList(String customercode) throws Exception;
	public JSONArray addCustomerCart(String productcode, String quantity, String price, String customercode) throws Exception;
	public JSONArray deleteCustomerCart(String productcode, String customercode) throws Exception;
	public JSONArray updateCustomerCart(String productcode, String customercode, String quantity, String price) throws Exception;
	public JSONArray getCustomerAddresstList(String customercode) throws Exception;
	public JSONArray getDeliverySlot(String address) throws Exception;
	public JSONArray checkAvailableStocks(String storecode, List<String> productcodelist) throws Exception;

}
