package com.wom.api.services;


import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.CustomerCartDaoImpl;

public class CustomerCartServiceImpl implements CustomerCartService{

	@Autowired
	CustomerCartDaoImpl customerDao;
	
	@Override
	public JSONArray getCustomerCartList(String customercode) throws Exception {
		return customerDao.getCustomerCartList(customercode);
	}
	
	@Override
	public JSONArray addCustomerCart(String productcode, String quantity, String price, String userid) throws Exception {
		return customerDao.addCustomerCart(productcode, quantity, price, userid);
	}

	@Override
	public JSONArray deleteCustomerCart(String productcode, String customercode) throws Exception {
		return customerDao.deleteCustomerCart(productcode, customercode);
	}

	@Override
	public JSONArray updateCustomerCart(String productcode, String customercode, String quantity, String price) throws Exception {
		return customerDao.updateCustomerCart(productcode, customercode, quantity, price);
	}

	@Override
	public JSONArray getCustomerAddresstList(String customercode) throws Exception {
		return customerDao.getCustomerAddresstList(customercode);
	}

	@Override
	public JSONArray getDeliverySlot(String address) throws Exception {
		return customerDao.getDeliverySlot(address);
	}
	
	@Override
	public JSONArray checkAvailableStocks(String storecode, List<String> productcodelist) throws Exception{
		return customerDao.checkAvailableStocks(storecode, productcodelist);
	}
	
}
