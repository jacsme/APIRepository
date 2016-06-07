package com.wom.api.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wom.api.constant.StatusCode;
import com.wom.api.services.CustomerCartServiceImpl;
import com.wom.api.services.ReportService;
import com.wom.api.util.HelperUtil;

@Controller
public class CustomerCartController {

	@Autowired
	CustomerCartServiceImpl customerservice;
	
	@Autowired
	ReportService reportServices;
	
	static final Logger logger = Logger.getLogger(CustomerCartController.class);
	
	/** GET Request 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getCustomerCart/{customercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getCustomerCartGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getCustomerCart/" + customercode);
		logger.info("getCustomerCart/" + customercode);
		
		JSONArray customercartlistarray = new JSONArray();
		
		try{
			customercartlistarray = customerservice.getCustomerCartList(customercode);
			customercartlistarray.put(reportServices.generateProductCounts());
		} catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customercartlistarray.put(exceptionobj);
		}
		return customercartlistarray;
	}
	
	@RequestMapping(value = "addCustomerCart/{productcode}/{quantity:.+}/{price:.+}/{customercode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray addCustomerCartGET(@PathVariable("productcode") String productcode, @PathVariable("quantity") String quantity,
			@PathVariable("price") String price, @PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Submit for Adding Customer Cart Record(s)");	
		logger.info("addCustomerCart/" + productcode + "/" + quantity + "/" + price + "/" + customercode);
		
		JSONArray addcustomerarray = new JSONArray();
		
		try{
			addcustomerarray = customerservice.addCustomerCart(productcode, quantity, price, customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			addcustomerarray.put(exceptionobj);
		}
		return addcustomerarray;
	}
	
	@RequestMapping(value = "deleteCustomerCart/{productcode}/{customercode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray deleteCustomerCartGET(@PathVariable("productcode") String productcode, @PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " deleteCustomerCart/" + productcode + "/" + customercode);
		
		JSONArray deletecustomercartarray = new JSONArray();
		try{
			deletecustomercartarray = customerservice.deleteCustomerCart(productcode, customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deletecustomercartarray.put(exceptionobj);
		}
		return deletecustomercartarray;
	}
	
	@RequestMapping(value = "updateCustomerCart/{productcode:.+}/{customercode:.+}/{quantity:.+}/{price:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray updateCustomerCartGET(@PathVariable("productcode") String productcode, @PathVariable("customercode") String customercode,
			@PathVariable("quantity") String quantity, @PathVariable("price") String price) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + "updateCustomerCart/" + productcode + "/" + customercode + "/" + quantity + "/" + price);
		
		JSONArray updatecustomercartarray = new JSONArray();
		try{
			updatecustomercartarray = customerservice.updateCustomerCart(productcode, customercode, quantity, price);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			updatecustomercartarray.put(exceptionobj);
		}
		return updatecustomercartarray;
	}
	
	@RequestMapping(value = "getCustomerAddressList/{customercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getCustomerAddressListGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getCustomerAddressList/" + customercode);
		logger.info("getCustomerAddressList/" + customercode);
		
		JSONArray customeraddresslistarray = new JSONArray();
		try{
			customeraddresslistarray = customerservice.getCustomerAddresstList(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customeraddresslistarray.put(exceptionobj);
		}
		return customeraddresslistarray;
	}
	
	
	@RequestMapping(value = "getDeliverySlot/{address:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getDeliverySlotGET(@PathVariable("address") String address) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getDeliverySlot/" + address);
		logger.info("getDeliverySlot/" + address);
		
		JSONArray deliveryslotarray = new JSONArray();
		try{
			deliveryslotarray = customerservice.getDeliverySlot(address);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deliveryslotarray.put(exceptionobj);
		}
		return deliveryslotarray;
	}
	
	@RequestMapping(value = "checkAvailableStocks/{storecode:.+}/{productcodelist:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray checkAvailableStocksGET(@PathVariable("storecode") String storecode, @PathVariable("productcodelist") List<String> productcodelist) throws JSONException{
			logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " checkAvailableStocks/" + storecode + "/" + productcodelist );
			
			JSONArray checkAvailableStocksarray = new JSONArray();
			try{
				checkAvailableStocksarray = customerservice.checkAvailableStocks(storecode, productcodelist);
			}catch(Exception e){
				JSONObject exceptionobj = new JSONObject();
				exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
				exceptionobj.put("Error Message", e.getMessage());
				logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
				checkAvailableStocksarray.put(exceptionobj);
			}
			return checkAvailableStocksarray;
	}

	/** POST Request 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getCustomerCart/{customercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getCustomerCartPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getCustomerCart/" +customercode);
		logger.info("getCustomerCart/" + customercode);
		
		JSONArray customercartlistarray = new JSONArray();
		
		try{
			customercartlistarray = customerservice.getCustomerCartList(customercode);
			customercartlistarray.put(reportServices.generateProductCounts());
			
		} catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customercartlistarray.put(exceptionobj);
		}
		return customercartlistarray;
	}
	
	@RequestMapping(value = "addCustomerCart/{productcode:.+}/{quantity:.+}/{price:.+}/{customercode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray addCustomerCartPOST(@PathVariable("productcode") String productcode, @PathVariable("quantity") String quantity,
			@PathVariable("price") String price, @PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " addCustomerCart/" + productcode + "/" + quantity + "/" + price + "/" + customercode);
		
		JSONArray addcustomerarray = new JSONArray();
		
		try{
			addcustomerarray = customerservice.addCustomerCart(productcode, quantity, price, customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			addcustomerarray.put(exceptionobj);
		}
		return addcustomerarray;
	}
	
	@RequestMapping(value = "deleteCustomerCart/{productcode:.+}/{customercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray deleteCutomerCartPOST(@PathVariable("productcode") String productcode, @PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " deleteCustomerCart/" + productcode + "/" + customercode);
		
		JSONArray deletecustomercartarray = new JSONArray();
		try{
			deletecustomercartarray = customerservice.deleteCustomerCart(productcode, customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deletecustomercartarray.put(exceptionobj);
		}
		return deletecustomercartarray;
	}
	
	@RequestMapping(value = "updateCustomerCart/{productcode}/{customercode}/{quantity:.+}/{price:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray updateCustomerCartPOST(@PathVariable("productcode") String productcode, @PathVariable("customercode") String customercode,
			@PathVariable("quantity") String quantity, @PathVariable("price") String price) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request to update the Customer Cart Product");
		logger.info("updateCustomerCart/" + productcode + "/" + customercode + "/" + quantity + "/" + price);
		
		JSONArray updatecustomercartarray = new JSONArray();
		try{
			updatecustomercartarray = customerservice.updateCustomerCart(productcode, customercode, quantity, price);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			updatecustomercartarray.put(exceptionobj);
		}
		return updatecustomercartarray;
	}
	
	@RequestMapping(value = "getCustomerAddressList/{customercode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getCustomerAddressListPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getCustomerAddressList/" + customercode);
		logger.info("getCustomerAddressList/" + customercode);
		
		JSONArray customeraddresslistarray = new JSONArray();
		try{
			customeraddresslistarray = customerservice.getCustomerAddresstList(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customeraddresslistarray.put(exceptionobj);
		}
		return customeraddresslistarray;
	}
	
	@RequestMapping(value = "getDeliverySlot/{address:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getDeliverySlotPOST(@PathVariable("address") String address) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getDeliverySlot/" + address);
		
		JSONArray deliveryslotarray = new JSONArray();
		try{
			deliveryslotarray = customerservice.getDeliverySlot(address);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deliveryslotarray.put(exceptionobj);
		}
		return deliveryslotarray;
	}
	
	@RequestMapping(value = "checkAvailableStocks/{storecode:.+}/{productcodelist:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray checkAvailableStocksPOST(@PathVariable("storecode") String storecode, @PathVariable("productcodelist") List<String> productcodelist) throws JSONException{
			logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " checkAvailableStocks/" + storecode + "/" + productcodelist );
			
			JSONArray checkAvailableStocksarray = new JSONArray();
			try{
				checkAvailableStocksarray = customerservice.checkAvailableStocks(storecode, productcodelist);
			}catch(Exception e){
				JSONObject exceptionobj = new JSONObject();
				exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
				exceptionobj.put("Error Message", e.getMessage());
				logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
				checkAvailableStocksarray.put(exceptionobj);
			}
			return checkAvailableStocksarray;
	}
}
