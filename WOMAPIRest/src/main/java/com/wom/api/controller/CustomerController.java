package com.wom.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

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
import com.wom.api.services.CustomerService;
import com.wom.api.util.HelperUtil;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	static final Logger logger = Logger.getLogger(CustomerController.class);
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getCustomerInfo/{customercode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getCustomerInfoGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getCustomerInfoGET");
		logger.info("getCustomerInfo/" + customercode);
		
		JSONArray customerarray = new JSONArray();
		
		try{
			customerarray = customerService.getCustomerInfo(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getCustomerInfoGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customerarray.put(exceptionobj);
		}
		return customerarray;
	}

	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getCustomerAddress/{customercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getCustomerAddressGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getCustomerAddressGET");
		logger.info("getCustomerAddress/" + customercode);
		
		JSONArray addressarray = new JSONArray();
		
		try{
			addressarray = customerService.getCustomerAddress(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getCustomerAddressGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			addressarray.put(exceptionobj);
		}
		return addressarray;
	}
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "submitCustomer/{emailaddess:.+}/{phonenumber:.+}/{password:.+}/{postcode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray submitCustomerGET(@Context final HttpServletRequest request, @Context HttpServletResponse response, @PathVariable("emailaddess") String emailaddress,
			@PathVariable("phonenumber") String phonenumber, @PathVariable("password") String password,
			@PathVariable("postcode") String postcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for submitMembershipGET");
		logger.info("submitCustomer/" + emailaddress + "/" + phonenumber + "/" + password  + "/" + postcode );
		
		JSONArray customerarray = new JSONArray();
		
		try{
			customerarray = customerService.submitCustomer(request, response, emailaddress, phonenumber, password, postcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitCustomerGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customerarray.put(exceptionobj);
		}
		return customerarray;
	}
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "verifiedCustomer/{emailaddess:.+}/{generatedcode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray verifiedCustomerGET(@Context final HttpServletRequest request, @Context HttpServletResponse response, @PathVariable("emailaddess") String emailaddress,
			@PathVariable("generatedcode") String generatedcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for verifiedCustomerGET");
		logger.info("verifiedCustomer/" + emailaddress + "/" + generatedcode);
		
		JSONArray verifiedcustomerarray = new JSONArray();
		
		try{
			verifiedcustomerarray = customerService.verifiedCustomer(request, response, emailaddress, generatedcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("verifiedCustomerGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			verifiedcustomerarray.put(exceptionobj);
		}
		return verifiedcustomerarray;
	}
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "resendcode/{emailaddess:.+}/{app:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray resendcodeGET(@Context final HttpServletRequest request, @Context HttpServletResponse response, @PathVariable("emailaddess") String emailaddress, @PathVariable("app") String app
			) throws JSONException{
		
		logger.info("resendcode/" + emailaddress + "/" + app);
		
		JSONArray resendcodearray = new JSONArray();
		
		try{
			resendcodearray = customerService.resendcode(request, response, emailaddress);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("resendcodeGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			resendcodearray.put(exceptionobj);
		}
		return resendcodearray;
	}
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getAvailableWOMCoins/{customercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getAvailableWOMCoinsGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getWOMCoinsGET");
		logger.info("getAvailableWOMCoins/" + customercode);
		
		JSONArray getwomcoinsarray = new JSONArray();
		
		try{
			getwomcoinsarray = customerService.getAvailableWOMCoins(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getAvailableWOMCoinsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			getwomcoinsarray.put(exceptionobj);
		}
		return getwomcoinsarray;
	}
	
	/** GET Methods 
	 * @throws JSONException 
	 **/
	@RequestMapping(value = "rechargeWOMCoins/{customercode:.+}/{amount:.+}/{womcoin:.+}/{paymenttype}/{vouchernumber:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray rechargeWOMCoinsGET(@PathVariable("customercode") String customercode, @PathVariable("amount") String amount, @PathVariable("womcoin") String womcoin,
			@PathVariable("paymenttype") String paymenttype, @PathVariable("vouchernumber") String vouchernumber) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for rechargeWOMCoinsGET");
		logger.info("rechargeWOMCoins/" + customercode + "/" + amount + "/" + womcoin + "/" + paymenttype + "/" + vouchernumber);
		
		JSONArray rechargewomcoinsarray = new JSONArray();
		
		try{
			rechargewomcoinsarray = customerService.rechargeWOMCoins(customercode, amount, womcoin, paymenttype, vouchernumber);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("rechargeWOMCoinsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			rechargewomcoinsarray.put(exceptionobj);
		}
		return rechargewomcoinsarray;
	}
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getRechargeHistory/{customercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getRechargeHistoryGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for rechargehistoryGET");
		logger.info("getRechargeHistory/" + customercode);
		
		JSONArray rechargehistoryarray = new JSONArray();
		
		try{
			rechargehistoryarray = customerService.getRechargeHistory(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getRechargeHistoryGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			rechargehistoryarray.put(exceptionobj);
		}
		return rechargehistoryarray;
	}
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getVoucherRedeemed/{customercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getVoucherRedeemedGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getVoucherRedeemedGET");
		logger.info("getVoucherRedeemed/" + customercode);
		
		JSONArray voucherredeemedarray = new JSONArray();
		
		try{
			voucherredeemedarray = customerService.getVoucherRedeemed(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getVoucherRedeemedGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			voucherredeemedarray.put(exceptionobj);
		}
		return voucherredeemedarray;
	}
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getOrderReceipt/{customercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getOrderReceiptGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getOrderReceiptGET");
		logger.info("getOrderReceipt/" + customercode);
		
		JSONArray orderreceiptarray = new JSONArray();
		
		try{
			orderreceiptarray = customerService.getOrderReceipt(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getOrderReceiptGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			orderreceiptarray.put(exceptionobj);
		}
		return orderreceiptarray;
	}

	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getShoppingRanking/{customercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getShoppingRankingGET(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getShoppingRankingGET");
		logger.info("getShoppingRanking/" + customercode);
		
		JSONArray shoppingrankingarray = new JSONArray();
		
		try{
			shoppingrankingarray = customerService.getShoppingRanking(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getShoppingRankingGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			shoppingrankingarray.put(exceptionobj);
		}
		return shoppingrankingarray;
	}
	
	/** GET Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "editCustomerDetails/{customercode:.+}/{emailaddress:.+}/{phonenumber:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray editCustomerDetailsGET(@PathVariable("customercode") String customercode,
			@PathVariable("emailaddress") String emailaddress, @PathVariable("phonenumber") String phonenumber
			) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for editCustomerDetailsGET");
		logger.info("editCustomerDetails/" + customercode + "/" + emailaddress + "/" + phonenumber );
		
		JSONArray editcustomerdetailsarray = new JSONArray();
		
		try{
			editcustomerdetailsarray = customerService.editCustomerDetails(customercode, emailaddress, phonenumber);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("editCustomerDetailsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			editcustomerdetailsarray.put(exceptionobj);
		}
		return editcustomerdetailsarray;
	}
		
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getCustomerInfo/{customercode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getCustomerInfoPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getCustomerInfoPOST");
		JSONArray customerarray = new JSONArray();
		
		try{
			customerarray = customerService.getCustomerInfo(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getCustomerInfoPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customerarray.put(exceptionobj);
		}
		return customerarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getCustomerAddress/{customercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getCustomerAddressPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getCustomerAddressPOST");
		logger.info("getCustomerInfo/" + customercode);
		
		JSONArray addressarray = new JSONArray();
		
		try{
			addressarray = customerService.getCustomerAddress(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getCustomerAddressPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			addressarray.put(exceptionobj);
		}
		return addressarray;
	}
	
	
	/** POST Method 
	 * @throws JSONException **/
	@RequestMapping(value = "submitCustomer/{emailaddess:.+}/{phonenumber:.+}/{password:.+}/{postcode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray submitCustomerPOST(@Context final HttpServletRequest request, @Context HttpServletResponse response, @PathVariable("emailaddess") String emailaddress,
			@PathVariable("phonenumber") String phonenumber, @PathVariable("password") String password, 
			@PathVariable("postcode") String postcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for submitMembershipPOST");
		logger.info("submitCustomer/" + emailaddress + "/" + phonenumber + "/" + password  + "/" + postcode );
		
		JSONArray customerarray = new JSONArray();
		
		try{
			customerarray = customerService.submitCustomer(request, response, emailaddress, phonenumber, password, postcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitCustomerPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			customerarray.put(exceptionobj);
		}
		return customerarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "verifiedCustomer/{emailaddess:.+}/{generatedcode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray verifiedCustomerPOST(@Context final HttpServletRequest request, @Context HttpServletResponse response, @PathVariable("emailaddess") String emailaddress,
			@PathVariable("generatedcode") String generatedcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for verifiedCustomerGET");
		logger.info("verifiedCustomer/" + emailaddress + "/" + generatedcode);
		
		JSONArray verifiedcustomerarray = new JSONArray();
		
		try{
			verifiedcustomerarray = customerService.verifiedCustomer(request, response, emailaddress, generatedcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("verifiedCustomerPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			verifiedcustomerarray.put(exceptionobj);
		}
		return verifiedcustomerarray;
	}
	
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getAvailableWOMCoins/{customercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getWOMCoinsPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getAvailableWOMCoinsPOST");
		logger.info("getAvailableWOMCoins/" + customercode);
		
		JSONArray getwomcoinsarray = new JSONArray();
		
		try{
			getwomcoinsarray = customerService.getAvailableWOMCoins(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getWOMCoinsPOST() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			getwomcoinsarray.put(exceptionobj);
		}
		return getwomcoinsarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "rechargeWOMCoins/{customercode:.+}/{amount:.+}/{womcoin:.+}/{paymenttype}/{vouchernumber:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray rechargeWOMCoinsPOST(@PathVariable("customercode") String customercode, @PathVariable("amount") String amount, @PathVariable("womcoin") String womcoin,
			@PathVariable("paymenttype") String paymenttype, @PathVariable("vouchernumber") String vouchernumber) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for rechargeWOMCoinsPOST");
		logger.info("rechargeWOMCoins/" + customercode + "/" + amount + "/" + womcoin + "/" + paymenttype + "/" + vouchernumber);
		
		JSONArray rechargewomcoinsarray = new JSONArray();
		
		try{
			rechargewomcoinsarray = customerService.rechargeWOMCoins(customercode, amount, womcoin, paymenttype, vouchernumber);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("rechargeWOMCoinsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			rechargewomcoinsarray.put(exceptionobj);
		}
		return rechargewomcoinsarray;
	}	
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getRechargeHistory/{customercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getRechargeHistoryPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for rechargehistoryPOST");
		logger.info("getRechargeHistory/" + customercode);
		
		JSONArray rechargehistoryarray = new JSONArray();
		
		try{
			rechargehistoryarray = customerService.getRechargeHistory(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getRechargeHistoryPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			rechargehistoryarray.put(exceptionobj);
		}
		return rechargehistoryarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getVoucherRedeemed/{customercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getVoucherRedeemedPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getVoucherRedeemedPOST");
		logger.info("getVoucherRedeemed/" + customercode);
		
		JSONArray voucherredeemedarray = new JSONArray();
		
		try{
			voucherredeemedarray = customerService.getVoucherRedeemed(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getVoucherRedeemedPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			voucherredeemedarray.put(exceptionobj);
		}
		return voucherredeemedarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getOrderReceipt/{customercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getOrderReceiptPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getOrderReceiptPOST");
		logger.info("getOrderReceipt/" + customercode);
		
		JSONArray orderreceiptarray = new JSONArray();
		
		try{
			orderreceiptarray = customerService.getOrderReceipt(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			orderreceiptarray.put(exceptionobj);
			logger.error("getOrderReceiptPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return orderreceiptarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "getShoppingRanking/{customercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getShoppingRankingPOST(@PathVariable("customercode") String customercode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getShoppingRankingPOST");
		logger.info("getShoppingRanking/" + customercode);
		
		JSONArray shoppingrankingarray = new JSONArray();
		
		try{
			shoppingrankingarray = customerService.getShoppingRanking(customercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getShoppingRankingPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			shoppingrankingarray.put(exceptionobj);
		}
		return shoppingrankingarray;
	}
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "editCustomerDetails/{customercode:.+}/{emailaddress:.+}/{phonenumber:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray editCustomerDetailsPOST(@PathVariable("customercode") String customercode,
			@PathVariable("emailaddress") String emailaddress, @PathVariable("phonenumber") String phonenumber) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for editCustomerDetailsPOST");
		logger.info("editCustomerDetails/" + customercode + "/" + emailaddress + "/" + phonenumber );
		
		JSONArray editcustomerdetailsarray = new JSONArray();
		
		try{
			editcustomerdetailsarray = customerService.editCustomerDetails(customercode, emailaddress, phonenumber);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("editCustomerDetailsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			editcustomerdetailsarray.put(exceptionobj);
		}
		return editcustomerdetailsarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	@RequestMapping(value = "resendcode/{emailaddess:.+}/{app:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray resendcodePOST(@Context final HttpServletRequest request, @Context HttpServletResponse response, @PathVariable("emailaddess") String emailaddress, 
			@PathVariable("app") String app) throws JSONException{
		
		logger.info("resendcode/" + emailaddress );
		
		JSONArray resendcodearray = new JSONArray();
		
		try{
			resendcodearray = customerService.resendcode(request, response, emailaddress);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("resendcodeGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			resendcodearray.put(exceptionobj);
		}
		return resendcodearray;
	}
}
