package com.wom.api.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

public interface CustomerDao {
	
	public JSONArray getCustomerInfo(String customercode) throws Exception;
	public JSONArray getCustomerAddress(String customercode) throws Exception;
	public JSONArray submitCustomer(HttpServletRequest request, HttpServletResponse response, String emailaddress, String phonenumber, String password, String postcode) throws Exception;
	public JSONArray verifiedCustomer(HttpServletRequest request, HttpServletResponse response, String emailaddress, String generatedcode) throws Exception;
	public JSONArray resendcode(HttpServletRequest request, HttpServletResponse response, String emailaddress) throws Exception;
	public JSONArray getAvailableWOMCoins(String customercode) throws Exception;
	public JSONArray rechargeWOMCoins(String customercode, String amount, String womcoin, String paymenttype, String vouchernumber) throws Exception;
	public JSONArray getRechargeHistory(String customercode) throws Exception;
	public JSONArray getVoucherRedeemed(String customercode) throws Exception;
	public JSONArray getOrderReceipt(String customercode) throws Exception;
	public JSONArray getShoppingRanking(String customercode) throws Exception;
	public JSONArray editCustomerDetails(String customercode, String emailaddress, String phonenumber) throws Exception;
}
