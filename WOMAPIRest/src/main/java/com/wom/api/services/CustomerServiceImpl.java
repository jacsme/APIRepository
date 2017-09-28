package com.wom.api.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.CustomerDao;

public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerDao customerDao;
	
	@Override
	public JSONArray getCustomerInfo(String customercode) throws Exception {
		return customerDao.getCustomerInfo(customercode);
	}
	
	@Override
	public JSONArray getCustomerAddress(String customercode) throws Exception{
		return customerDao.getCustomerAddress(customercode);
	}
	
	@Override
	public JSONArray submitCustomer(HttpServletRequest request, HttpServletResponse response,String emailaddress, String phonenumber, String password, String postcode) throws Exception{
		return customerDao.submitCustomer(request, response, emailaddress, phonenumber, password, postcode);
	}
	@Override
	public JSONArray verifiedCustomer(HttpServletRequest request, HttpServletResponse response, String emailaddress, String generatedcode) throws Exception{
		return customerDao.verifiedCustomer(request, response, emailaddress, generatedcode);
	}
	
	@Override
	public JSONArray getAvailableWOMCoins(String customercode) throws Exception {
		return customerDao.getAvailableWOMCoins(customercode);
	}

	@Override
	public JSONArray rechargeWOMCoins(String customercode, String amount, String womcoin, String paymenttype, String vouchernumber) throws Exception {
		return customerDao.rechargeWOMCoins(customercode, amount, womcoin, paymenttype, vouchernumber);
	}
	
	@Override
	public JSONArray getRechargeHistory(String customercode) throws Exception{
		return customerDao.getRechargeHistory(customercode);
	}
	
	@Override
	public JSONArray getVoucherRedeemed(String customercode) throws Exception{
		return customerDao.getVoucherRedeemed(customercode);
	}

	@Override
	public JSONArray getOrderReceipt(String customercode) throws Exception {
		return customerDao.getOrderReceipt(customercode);
	}

	@Override
	public JSONArray editCustomerDetails(String customercode, String emailaddress, String phonenumber) throws Exception {
		return customerDao.editCustomerDetails(customercode, emailaddress, phonenumber);
	}

	@Override
	public JSONArray getShoppingRanking(String customercode) throws Exception {
		return customerDao.getShoppingRanking(customercode);
	}

	@Override
	public JSONArray resendcode(HttpServletRequest request, HttpServletResponse response, String emailaddress)
			throws Exception {
		return customerDao.resendcode(request, response, emailaddress);
	}
}
