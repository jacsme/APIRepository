package com.wom.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wom.api.constant.StatusCode;
import com.wom.api.services.SalesOrderService;
import com.wom.api.util.HelperUtil;

@Controller
public class SalesOrderController {

	@Autowired
	SalesOrderService salesorderService;
	
	static final Logger logger = Logger.getLogger(SalesOrderController.class);
	
	/** GET Method **/
	//details - [productcode, price, quantity, gst, discount]
	@RequestMapping(value = "submitsalesorder/{storecode:.+}/{address:.+}/{salesorderdate:.+}/{paymentmethod:.+}/{contactno:.+}/{note:.+}/{userid:.+}/{addresstype:.+}/{combine:.+}/{postcode:.+}/{deliverytime:.+}/{deliverydate:.+}/{details:.+}", method=RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray submitSalesOrderGET(@Context final HttpServletRequest request,  @Context HttpServletResponse response, @PathVariable("storecode") String storecode, @PathVariable("address") String address, @PathVariable("salesorderdate") String salesorderdate,
			@PathVariable("paymentmethod") String paymentmethod, @PathVariable("contactno") String contactno, 
			@PathVariable("note") String note, @PathVariable("userid") String userid,  @PathVariable("details") List<String> salesorderdetails,  @PathVariable("addresstype") String addresstype, @PathVariable("combine") String combine,
			@PathVariable("postcode") String postcode, @PathVariable("deliverytime") String deliverytime, @PathVariable("deliverydate") String deliverydate) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Submit Sales Order " + " submitsalesorder/" + storecode + "/" + address + "/" + salesorderdate + "/" + paymentmethod + "/" + contactno + "/" + note + "/" + userid + "/" + addresstype + "/" + combine + "/" + postcode + "/" + deliverytime + "/" + deliverydate + "/" + salesorderdetails);
		
		JSONArray submitsalesorderarray = new JSONArray();
		
		try{
			submitsalesorderarray = salesorderService.submitSalesOrder(request, response,storecode, address, salesorderdate, paymentmethod, 
					contactno, note, userid, salesorderdetails, addresstype, combine, postcode, deliverytime, deliverydate);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitSalesOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitsalesorderarray.put(exceptionobj);
		}
		return submitsalesorderarray;
	}
	
	@RequestMapping(value = "getSalesOrder/{storecode}/{jobid}/{staffcode}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody JSONArray getSalesOrderGET(@PathVariable("storecode") String storecode, @PathVariable("jobid") String jobid, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " getSalesOrder/" + storecode +"/" + jobid +"/" + staffcode );
		
		JSONArray salesorderarray = new JSONArray();
		
		try{
			salesorderarray = salesorderService.getSalesOrder(storecode, jobid, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getSalesOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			salesorderarray.put(exceptionobj);
		}
		return salesorderarray;
	}
	
	// salesorderdetails -  [productcode, box, stocklocation, quantity, stockcode]
	@RequestMapping(value = "completeSalesOrder/{storecode:.+}/{salesordercode:.+}/{jobid:.+}/{staffcode:.+}/{salesorderdetails:.+}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody JSONArray completeSalesOrderGET(@PathVariable("storecode") String storecode, @PathVariable("salesordercode") String salesordercode,
			@PathVariable("jobid") String jobid, @PathVariable("staffcode") String staffcode, @PathVariable("salesorderdetails") List<String> salesorderdetails) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " completeSalesOrder/" + storecode + "/" + salesordercode + "/" + jobid + "/" + staffcode + "/" + salesorderdetails);
		
		JSONArray completesalesorderarray = new JSONArray();
		
		try{
			completesalesorderarray = salesorderService.completeSalesOrder(storecode, salesordercode, jobid, staffcode, salesorderdetails);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("completeSalesOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			completesalesorderarray.put(exceptionobj);
		}
		return completesalesorderarray;
	}
	
	@RequestMapping(value = "submitCustomerAddress/{customercode:.+}/{addresstype:.+}/{postcode:.+}/{state:.+}/{city:.+}/{area:.+}/{street:.+}/{number:.+}/{building:.+}/{unit:.+}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody JSONArray submitCustomerAddressGET(@PathVariable("customercode") String customercode, @PathVariable("addresstype") String addresstype,
			@PathVariable("postcode") String postcode, @PathVariable("state") String state, @PathVariable("city") String city,
			@PathVariable("area") String area, @PathVariable("street") String street, @PathVariable("number") String number, 
			@PathVariable("building") String building, @PathVariable("unit") String unit) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " submitCustomerAddress/" + customercode + "/" + addresstype + "/" + postcode + "/" + state + "/" + city + "/" + area + "/" + street + "/" + number + "/" + building  + "/" + unit);
		
		JSONArray submitcustomeraddressarray = new JSONArray();
		
		try{
			submitcustomeraddressarray = salesorderService.submitCustomerAddress(customercode, addresstype, postcode, state, city, area, street, number, building, unit);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitCustomerAddressGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitcustomeraddressarray.put(exceptionobj);
		}
		return submitcustomeraddressarray;
	}
	
	@RequestMapping(value="submitCustomerAddress", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody JSONArray submitCustomerAddressPageGET(
			@RequestParam(value="customercode", required=true) String customercode,
			@RequestParam(value="addresstype", required=true) String addresstype,
			@RequestParam(value="postcode", required=true) String postcode,
			@RequestParam(value="state", required=true) String state,
			@RequestParam(value="city", required=true) String city,
			@RequestParam(value="area", required=false) String area,
			@RequestParam(value="street", required=false) String street,
			@RequestParam(value="number", required=false) String number,
			@RequestParam(value="building", required=false) String building,
			@RequestParam(value="unit", required=false) String unit
			) throws Exception {
		
		logger.info(" submitCustomerAddress Parameter - " 
				+ "customercode = " + customercode 
				+ "addresstype = " + addresstype 
				+ "postcode = " + postcode 
				+ "state = " + state 
				+ "city = " + city 
				+ "area = " + area 
				+ "street = " + street 
				+ "number = " + number 
				+ "building = " + building  
				+ "unit = " + unit);
		JSONArray submitcustomeraddressarray = new JSONArray();
		
		try{
			submitcustomeraddressarray = salesorderService.submitCustomerAddress(customercode, addresstype, postcode, state, city, area, street, number, building, unit);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitCustomerAddress() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitcustomeraddressarray.put(exceptionobj);
		}
		return submitcustomeraddressarray;
	}
	
	@RequestMapping(value = "getCurrentCustomerAddress/{customercode:.+}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody JSONArray getCurrentCustomerAddressGET(@PathVariable("customercode") String customercode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " getCurrentCustomerAddress/" + customercode);
		
		JSONArray customeraddressarray = new JSONArray();
		
		try{
			customeraddressarray = salesorderService.getCurrentCustomerAddress(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getCurrentCustomerAddressGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customeraddressarray.put(exceptionobj);
		}
		return customeraddressarray;
	}
	
	/** POST Method **/
	//details - [productcode, price, quantity, gst, discount]
	@RequestMapping(value = "submitsalesorder/{storecode:.+}/{address:.+}/{salesorderdate:.+}/{paymentmethod:.+}/{contactno:.+}/{note:.+}/{userid}/{addresstype:.+}/{combine:.+}/{postcode:.+}/{deliverytime:.+}/{deliverydate:.+}/{details:.+}", method=RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray submitSalesOrderPOST(@Context final HttpServletRequest request,  @Context HttpServletResponse response, @PathVariable("storecode") String storecode, @PathVariable("address") String address, @PathVariable("salesorderdate") String salesorderdate,
			@PathVariable("paymentmethod") String paymentmethod, @PathVariable("contactno") String contactno, 
			@PathVariable("note") String note, @PathVariable("userid") String userid,  @PathVariable("details") List<String> salesorderdetails,  @PathVariable("addresstype") String addresstype, @PathVariable("combine") String combine,
			@PathVariable("postcode") String postcode, @PathVariable("deliverytime") String deliverytime, @PathVariable("deliverydate") String deliverydate) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " submitsalesorder/" + storecode + "/" + address + "/" + salesorderdate + "/" + paymentmethod + "/" + contactno + "/" + note + "/" + userid + "/" + addresstype + "/" + combine + "/" + postcode + "/" + deliverytime + "/" + deliverydate + "/" + salesorderdetails);
		
		JSONArray submitsalesorderarray = new JSONArray();
		
		try{
			submitsalesorderarray = salesorderService.submitSalesOrder(request, response,storecode, address, salesorderdate, paymentmethod, 
					contactno, note, userid, salesorderdetails, addresstype, combine, postcode, deliverytime, deliverydate);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitSalesOrderPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitsalesorderarray.put(exceptionobj);
		}
		return submitsalesorderarray;
	}
	
	@RequestMapping(value = "getSalesOrder/{storecode:.+}/{jobid:.+}/{staffcode:.+}", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody JSONArray getSalesOrderPOST(@PathVariable("storecode") String storecode, @PathVariable("jobid") String jobid, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " getSalesOrder/" + storecode +"/" + jobid +"/" + staffcode );
		
		JSONArray salesorderarray = new JSONArray();
		
		try{
			salesorderarray = salesorderService.getSalesOrder(storecode, jobid, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getSalesOrderPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			salesorderarray.put(exceptionobj);
		}
		return salesorderarray;
	}
	
	// salesorderdetails - [productcode, box, stocklocation, quantity]
	@RequestMapping(value = "completeSalesOrder/{storecode:.+}/{salesordercode:.+}/{jobid:.+}/{staffcode:.+}/{salesorderdetails:.+}", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody JSONArray completeSalesOrderPOST(@PathVariable("storecode") String storecode, @PathVariable("salesordercode") String salesordercode,
			@PathVariable("jobid") String jobid, @PathVariable("staffcode") String staffcode, @PathVariable("salesorderdetails") List<String> salesorderdetails) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " completeSalesOrder/" + storecode + "/" + salesordercode + "/" + jobid + "/" + staffcode + "/" + salesorderdetails);
		
		JSONArray completesalesorderarray = new JSONArray();
		
		try{
			completesalesorderarray = salesorderService.completeSalesOrder(storecode, salesordercode, jobid, staffcode, salesorderdetails);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("completeSalesOrderPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			completesalesorderarray.put(exceptionobj);
		}
		return completesalesorderarray;
	}
	
	@RequestMapping(value = "submitCustomerAddress/{customercode:.+}/{addresstype:.+}/{postcode:.+}/{state:.+}/{city:.+}/{area:.+}/{street:.+}/{number:.+}/{building:.+}/{unit:.+}", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody JSONArray submitCustomerAddressPOST(@PathVariable("customercode") String customercode, @PathVariable("addresstype") String addresstype,
			@PathVariable("postcode") String postcode, @PathVariable("state") String state, @PathVariable("city") String city,
			@PathVariable("area") String area, @PathVariable("street") String street, @PathVariable("number") String number, 
			@PathVariable("building") String building, @PathVariable("unit") String unit) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " submitCustomerAddress/" + customercode + "/" + addresstype + "/" + postcode + "/" + state + "/" + city + "/" + area + "/" + street + "/" + number + "/" + building  + "/" + unit);
		
		JSONArray submitcustomeraddressarray = new JSONArray();
		
		try{
			submitcustomeraddressarray = salesorderService.submitCustomerAddress(customercode, addresstype, postcode, state, city, area, street, number, building, unit);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitCustomerAddressPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitcustomeraddressarray.put(exceptionobj);
		}
		return submitcustomeraddressarray;
	}
	
	@RequestMapping(value="submitCustomerAddress", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody JSONArray submitCustomerAddressPagePOST(
			@RequestParam(value="customercode", required=true) String customercode,
			@RequestParam(value="addresstype", required=true) String addresstype,
			@RequestParam(value="postcode", required=true) String postcode,
			@RequestParam(value="state", required=true) String state,
			@RequestParam(value="city", required=true) String city,
			@RequestParam(value="area", required=false) String area,
			@RequestParam(value="street", required=false) String street,
			@RequestParam(value="number", required=false) String number,
			@RequestParam(value="building", required=false) String building,
			@RequestParam(value="unit", required=false) String unit
			) throws Exception {
		
		logger.info(" submitCustomerAddress Parameter - " 
				+ "customercode = " + customercode 
				+ "addresstype = " + addresstype 
				+ "postcode = " + postcode 
				+ "state = " + state 
				+ "city = " + city 
				+ "area = " + area 
				+ "street = " + street 
				+ "number = " + number 
				+ "building = " + building  
				+ "unit = " + unit);
		
		JSONArray submitcustomeraddressarray = new JSONArray();
		
		try{
			submitcustomeraddressarray = salesorderService.submitCustomerAddress(customercode, addresstype, postcode, state, city, area, street, number, building, unit);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitCustomerAddress() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitcustomeraddressarray.put(exceptionobj);
		}
		return submitcustomeraddressarray;
	}
	
	@RequestMapping(value = "getCurrentCustomerAddress/{customercode:.+}", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody JSONArray getCurrentCustomerAddressPOST(@PathVariable("customercode") String customercode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " getCurrentCustomerAddress/" + customercode);
		
		JSONArray customeraddressarray = new JSONArray();
		
		try{
			customeraddressarray = salesorderService.getCurrentCustomerAddress(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getCurrentCustomerAddressPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customeraddressarray.put(exceptionobj);
		}
		return customeraddressarray;
	}

}
