package com.wom.api.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.SalesOrderDao;

public class SalesOrderServiceImpl implements SalesOrderService {

	@Autowired
	SalesOrderDao salesorderDao;
	
	@Override
	public JSONArray submitSalesOrder(HttpServletRequest request, HttpServletResponse response, String storecode, String address, String salesorderdate, String paymentmethod, String contactnumber,
			String note, String userid, List<String> salesorderdetailsList, String addresstype, String combine, String postcode, String deliverytime, String deliverydate) throws Exception {
		
		return salesorderDao.submitSalesOrder(request, response, storecode, address, salesorderdate, paymentmethod, contactnumber,
			note, userid, salesorderdetailsList, addresstype, combine, postcode, deliverytime, deliverydate);
	}
	
	@Override
	public JSONArray getSalesOrder(String storecode, String jobid, String staffcode) throws Exception{
		return salesorderDao.getSalesOrder(storecode, jobid, staffcode);
	}
	
	@Override
	public JSONArray completeSalesOrder(String storecode, String salesordercode, String jobid, String staffcode, 
			List<String> salesorderdetails) throws Exception{
		return salesorderDao.completeSalesOrder(storecode, salesordercode, jobid, staffcode, salesorderdetails);
	}

	@Override
	public JSONArray submitCustomerAddress(String customercode, String addresstype, String postcode, String state,
			String city, String area, String street, String number, String building, String unit) throws Exception {
		return salesorderDao.submitCustomerAddress(customercode, addresstype, postcode, state, city, area, street, number, building, unit);
	}

	@Override
	public JSONArray getCurrentCustomerAddress(String customercode) throws Exception {
		return salesorderDao.getCurrentCustomerAddress(customercode);
	}
}
