package com.wom.api.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

public interface SalesOrderService {
	public JSONArray getSalesOrder(String storecode, String jobid, String staffcode) throws Exception;
	public JSONArray submitSalesOrder(HttpServletRequest request, HttpServletResponse response, String storecode, String address, String salesorderdate, String paymentmethod, String contactnumber, 
			String note, String userid, List<String> salesorderdetailsList, String addresstype, String combine, String postcode, String deliverytime, String deliverydate) throws Exception;
	public JSONArray completeSalesOrder(String storecode, String salesordercode, String jobid, String staffcode, 
			List<String> salesorderdetails) throws Exception;
	public JSONArray submitCustomerAddress(String customercode, String addresstype, String postcode, String state, String city, String	area,
			String street, String number, String building, String unit) throws Exception;
	public JSONArray getCurrentCustomerAddress(String customercode) throws Exception;
}
