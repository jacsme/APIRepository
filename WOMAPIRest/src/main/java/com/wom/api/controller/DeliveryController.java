package com.wom.api.controller;

import java.util.List;

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
import com.wom.api.services.DeliveryService;
import com.wom.api.util.HelperUtil;

@Controller
public class DeliveryController {

	@Autowired
	DeliveryService deliveryService;
	
	static final Logger logger = Logger.getLogger(DeliveryController.class);
	
	/** GET Method 
	 * @throws JSONException **/
	
	@RequestMapping(value="getBoxForScanning/{boxcode}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getBoxForScanningGET(@PathVariable("boxcode") String boxcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for getBoxForScanningGET - getBoxForScanning/" + boxcode);
		
		JSONArray boxforscanningarray = new JSONArray();
		
		try{
			boxforscanningarray = deliveryService.getBoxForScanning(boxcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getBoxForScanningGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			boxforscanningarray.put(exceptionobj);
		}
		return boxforscanningarray;
	}
	
	@RequestMapping(value="submitScannedBox/{area}/{boxcode}/{boxweight:.+}/{staffcode}/{salesordercode}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray submitScannedBoxGET(@PathVariable("area") String area, @PathVariable("boxcode") String boxcode, 
			@PathVariable("boxweight") String boxweight, @PathVariable("staffcode") String staffcode, @PathVariable("salesordercode") String salesordercode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for submitScannedBoxGET - submitScannedBox/" + area + "/" + boxcode + "/" + boxweight + "/" + staffcode + "/" + salesordercode);
		
		JSONArray scannedboxarray = new JSONArray();
		
		try{
			scannedboxarray = deliveryService.submitScannedBox(area, boxcode, boxweight, staffcode, salesordercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitScannedBoxGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			scannedboxarray.put(exceptionobj);
		}
		return scannedboxarray;
	}
	
	//deliverydetails - [deliverycode, area, box]
	@RequestMapping(value="updateDelivery/{truckcode}/{staffcode}/{deliverydetails:.+}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody 
	JSONArray updateDeliveryGET(@PathVariable("truckcode") String truckcode, @PathVariable("staffcode") String staffcode, @PathVariable("deliverydetails") List<String> deliverydetails) throws JSONException{
				
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for updateDeliveryGET - updateDelivery/" + staffcode+ "/" + deliverydetails);
		
		JSONArray updatedeliveryarray = new JSONArray();
		
		try{
			updatedeliveryarray = deliveryService.updateDelivery(truckcode, staffcode, deliverydetails);
			
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("updateDeliveryGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			updatedeliveryarray.put(exceptionobj);
		}
		return updatedeliveryarray;
	}
	
	@RequestMapping(value="getTruckInfo", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getTruckInfoGET() throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for getTruckInfoGET");
		
		JSONArray truckarray = new JSONArray();
		
		try{
			truckarray = deliveryService.getTruckInfo();
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getTruckInfoGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			truckarray.put(exceptionobj);
		}
		return truckarray;
	}
	
	@RequestMapping(value="getDeliveryDetails/{truckcode:.+}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getDeliveryDetailsGET(@PathVariable("truckcode") String truckcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " getDeliveryDetails/" + truckcode);
		
		JSONArray deliverydetailsarray = new JSONArray();
		
		try{
			deliverydetailsarray = deliveryService.getDeliveryDetails(truckcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getDeliveryDetailsGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deliverydetailsarray.put(exceptionobj);
		}
		return deliverydetailsarray;
	}
	
	@RequestMapping(value="getDeliveryJob/{staffcode}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getDeliveryJobGET(@PathVariable("staffcode") String staffcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for getDeliveryJobGET");
		
		JSONArray deliveryjobarray = new JSONArray();
		
		try{
			deliveryjobarray = deliveryService.getDeliveryJob(staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getDeliveryJobGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deliveryjobarray.put(exceptionobj);
		}
		return deliveryjobarray;
	}
	
	@RequestMapping(value="getDeliveryCustomerOrder/{deliverycode}/{salesordercode}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getDeliveryCustomerOrderGET(@PathVariable("deliverycode") String deliverycode, @PathVariable("salesordercode") String salesordercode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for getDeliveryCustomerOrderGET");
		
		JSONArray deliverycustoerarray = new JSONArray();
		
		try{
			deliverycustoerarray = deliveryService.getDeliveryCustomerOrder(deliverycode, salesordercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getDeliveryCustomerOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deliverycustoerarray.put(exceptionobj);
		}
		return deliverycustoerarray;
	}
	
	@RequestMapping(value="checkPostCode/{postcode}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray checkPostCodeGET(@PathVariable("postcode") String postCode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for checkPostCodeGET");
		
		JSONArray checkpostcodearray = new JSONArray();
		
		try{
			checkpostcodearray = deliveryService.checkPostCode(postCode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("checkPostCodeGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			checkpostcodearray.put(exceptionobj);
		}
		return checkpostcodearray;
	}
	
	//deliverycustomerorderList [ProductCode, ReturnQuantity, ReturnPrice]
	@RequestMapping(value="completeDeliveryCustomerOrder/{deliverycode}/{staffcode}/{customercode}/{salesordercode}/{deliverycustomerorderList:.+}", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray completeDeliveryCustomerOrderGET(@Context final HttpServletRequest request, @Context HttpServletResponse response, @PathVariable("deliverycode") String deliverycode, @PathVariable("staffcode") String staffcode,
			@PathVariable("customercode") String customercode, @PathVariable("salesordercode") String salesordercode, @PathVariable("deliverycustomerorderList") List<String> deliverycustomerorderList) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for completeDeliveryCustomerOrderGET - completeDeliveryCustomerOrder/" + deliverycode + "/" + staffcode + "/" + customercode + "/" + salesordercode + "/" + deliverycustomerorderList);
		
		JSONArray completeDeliveryCustomerOrderarray = new JSONArray();
		
		try{
			completeDeliveryCustomerOrderarray = deliveryService.completeDeliveryCustomerOrder(request, response, deliverycode, staffcode, customercode, salesordercode, deliverycustomerorderList);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("completeDeliveryCustomerOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			completeDeliveryCustomerOrderarray.put(exceptionobj);
		}
		return completeDeliveryCustomerOrderarray;
	}
			
	/** POST Method 
	 * @throws JSONException **/
	
	@RequestMapping(value="getBoxForScanning/{boxcode}", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getBoxForScanningPOST(@PathVariable("boxcode") String boxcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for getBoxForScanningGET - getBoxForScanning/" + boxcode);
		
		JSONArray boxforscanningarray = new JSONArray();
		
		try{
			boxforscanningarray = deliveryService.getBoxForScanning(boxcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getBoxForScanningPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			boxforscanningarray.put(exceptionobj);
		}
		return boxforscanningarray;
	}
	
	@RequestMapping(value="submitScannedBox/{area}/{boxcode}/{boxweight:.+}/{staffcode}/{salesordercode}", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray submitScannedBoxPOST(@PathVariable("area") String area, @PathVariable("boxcode") String boxcode, 
			@PathVariable("boxweight") String boxweight, @PathVariable("staffcode") String staffcode, @PathVariable("salesordercode") String salesordercode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for submitScannedBoxPOST - submitScannedBox/" + area + "/" + boxcode + "/" + boxweight + "/" + staffcode + "/" + salesordercode);
		
		JSONArray scannedboxarray = new JSONArray();
		
		try{
			scannedboxarray = deliveryService.submitScannedBox(area, boxcode, boxweight, staffcode, salesordercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitScannedBoxPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			scannedboxarray.put(exceptionobj);
		}
		return scannedboxarray;
	}
	
	//deliverydetails - [salesordercode, box]
	@RequestMapping(value="updateDelivery/{truckcode}/{staffcode}/{deliverydetails:.+}", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody 
	JSONArray updateDeliveryPOST(@PathVariable("truckcode") String truckcode, @PathVariable("staffcode") String staffcode, @PathVariable("deliverydetails") List<String> deliverydetails) throws JSONException{
				
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for updateDeliveryPOST - updateDelivery/" + staffcode+ "/" + deliverydetails);
		
		JSONArray updatedeliveryarray = new JSONArray();
		
		try{
			updatedeliveryarray = deliveryService.updateDelivery(truckcode, staffcode, deliverydetails);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("updateDeliveryPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			updatedeliveryarray.put(exceptionobj);
		}
		return updatedeliveryarray;
	}
	
	@RequestMapping(value="getTruckInfo", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getTruckInfoPOST() throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for getTruckInfoPOST");
		
		JSONArray truckarray = new JSONArray();
		
		try{
			truckarray = deliveryService.getTruckInfo();
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getTruckInfoPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			truckarray.put(exceptionobj);
		}
		return truckarray;
	}
	
	@RequestMapping(value="getDeliveryDetails/{truckcode:.+}", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getDeliveryDetailsPOST(@PathVariable("truckcode") String truckcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " getDeliveryDetails/" + truckcode);
		
		JSONArray deliverydetailsarray = new JSONArray();
		
		try{
			deliverydetailsarray = deliveryService.getDeliveryDetails(truckcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getDeliveryDetailsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deliverydetailsarray.put(exceptionobj);
		}
		return deliverydetailsarray;
	}
	
	@RequestMapping(value="getDeliveryJob/{staffcode}", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getDeliveryJobPOST(@PathVariable("staffcode") String staffcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for getDeliveryJobPOST");
		
		JSONArray deliveryjobearray = new JSONArray();
		
		try{
			deliveryjobearray = deliveryService.getDeliveryJob(staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getDeliveryJobPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deliveryjobearray.put(exceptionobj);
		}
		return deliveryjobearray;
	}
	
	@RequestMapping(value="getDeliveryCustomerOrder/{deliverycode}/{salesordercode}", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getDeliveryCustomerOrderPOST(@PathVariable("deliverycode") String deliverycode, @PathVariable("salesordercode") String salesordercode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for getDeliveryCustomerOrderPOST");
		
		JSONArray deliverycustoerarray = new JSONArray();
		
		try{
			deliverycustoerarray = deliveryService.getDeliveryCustomerOrder(deliverycode, salesordercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getDeliveryCustomerOrderPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			deliverycustoerarray.put(exceptionobj);
		}
		return deliverycustoerarray;
	}
	
	@RequestMapping(value="checkPostCode/{postcode}", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray checkPostCodePOST(@PathVariable("postcode") String postCode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for checkPostCodePOST");
		
		JSONArray checkpostcodearray = new JSONArray();
		
		try{
			checkpostcodearray = deliveryService.checkPostCode(postCode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("checkPostCodePOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			checkpostcodearray.put(exceptionobj);
		}
		return checkpostcodearray;
	}
	
	//deliverycustomerorderList [ProductCode, ReturnQuantity, ReturnPrice]
	@RequestMapping(value="completeDeliveryCustomerOrder/{deliverycode}/{staffcode}/{customercode}/{salesordercode}/{deliverycustomerorderList:.+}", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray completeDeliveryCustomerOrderPOST(@Context final HttpServletRequest request, @Context HttpServletResponse response, @PathVariable("deliverycode") String deliverycode, @PathVariable("staffcode") String staffcode,
			@PathVariable("customercode") String customercode, @PathVariable("salesordercode") String salesordercode, @PathVariable("deliverycustomerorderList") List<String> deliverycustomerorderList) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Requested for completeDeliveryCustomerOrderPOST - completeDeliveryCustomerOrder/" + deliverycode + "/" + staffcode + "/" + customercode + "/" + salesordercode + "/" + deliverycustomerorderList);
		
		JSONArray completeDeliveryCustomerOrderarray = new JSONArray();
		
		try{
			completeDeliveryCustomerOrderarray = deliveryService.completeDeliveryCustomerOrder(request, response, deliverycode, staffcode, customercode, salesordercode, deliverycustomerorderList);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("completeDeliveryCustomerOrderPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			completeDeliveryCustomerOrderarray.put(exceptionobj);
		}
		return completeDeliveryCustomerOrderarray;
	}
}
