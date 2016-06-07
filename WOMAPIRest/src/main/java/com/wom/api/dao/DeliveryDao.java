package com.wom.api.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

public interface DeliveryDao {
	
	public JSONArray getBoxForScanning(String boxcode) throws Exception;
	public JSONArray submitScannedBox(String area, String boxcode, String boxweight, String staffcode, String salesordercode) throws Exception;
	public JSONArray updateDelivery(String truckcode, String staffcode, List<String> deliverydetails) throws Exception;
	public JSONArray getTruckInfo() throws Exception;
	public JSONArray getDeliveryDetails(String truckcode) throws Exception;
	public JSONArray getDeliveryCustomerOrder(String deliverycode, String salesordercode) throws Exception;
	public JSONArray getDeliveryJob(String staffcode) throws Exception;
	public JSONArray checkPostCode(String postCode) throws Exception;
	public JSONArray completeDeliveryCustomerOrder(HttpServletRequest request, HttpServletResponse response, String deliverycode, String staffcode, String customercode, String salesordercode,
			List<String> deliverycustomerorderList) throws Exception;
}
