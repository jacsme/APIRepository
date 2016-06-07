package com.wom.api.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.DeliveryDao;

public class DeliveryServiceImpl implements DeliveryService{

	@Autowired
	DeliveryDao deliveryDao;
	
	@Override
	public JSONArray getBoxForScanning(String boxcode) throws Exception{
		return deliveryDao.getBoxForScanning(boxcode);
	}
	
	@Override
	public JSONArray submitScannedBox(String area, String boxcode, String boxweight, String staffcode, String salesordercode) throws Exception{
		return deliveryDao.submitScannedBox(area, boxcode, boxweight, staffcode, salesordercode);
	}
	
	@Override
	public JSONArray updateDelivery(String truckcode, String staffcode, List<String> deliverydetails) throws Exception {
		return deliveryDao.updateDelivery(truckcode, staffcode, deliverydetails);
	}

	@Override
	public JSONArray getDeliveryDetails(String truckcode) throws Exception {
		return deliveryDao.getDeliveryDetails(truckcode);
	}
	
	@Override
	public JSONArray checkPostCode(String postCode) throws Exception{
		return deliveryDao.checkPostCode(postCode);
	}

	@Override
	public JSONArray getDeliveryCustomerOrder(String deliverycode, String salesordercode) throws Exception {
		return deliveryDao.getDeliveryCustomerOrder(deliverycode, salesordercode);
	}

	@Override
	public JSONArray getDeliveryJob(String staffcode) throws Exception {
		return deliveryDao.getDeliveryJob(staffcode);
	}

	@Override
	public JSONArray completeDeliveryCustomerOrder(HttpServletRequest request, HttpServletResponse response, String deliverycode, String staffcode, String customercode, String salesordercode,
			List<String> deliverycustomerorderList) throws Exception {
		return deliveryDao.completeDeliveryCustomerOrder(request, response, deliverycode, staffcode, customercode, salesordercode, deliverycustomerorderList);
	}

	@Override
	public JSONArray getTruckInfo() throws Exception {
		return deliveryDao.getTruckInfo();
	}
}
