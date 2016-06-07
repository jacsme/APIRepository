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
import org.springframework.web.bind.annotation.ResponseBody;

import com.wom.api.constant.StatusCode;
import com.wom.api.services.ReceivingInvoiceService;
import com.wom.api.util.HelperUtil;

@Controller
public class ReceivingInvoiceController {

	@Autowired
	ReceivingInvoiceService receivinginvoiceService;
	
	static final Logger logger = Logger.getLogger(ReceivingInvoiceController.class);
	
	/** GET Methods **/
	@RequestMapping(value = "getReceivingInvoice/{suppliercode}/{jobid}/{staffcode}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getReceivingInvoiceGET(@PathVariable("suppliercode") String suppliercode, @PathVariable("jobid") String jobid, @PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getReceivingInvoiceGET");
		JSONArray receivinginvoicearray = new JSONArray();
		
		try{
			receivinginvoicearray = receivinginvoiceService.getReceivingInvoice(suppliercode, jobid, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getReceivingInvoiceGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			receivinginvoicearray.put(exceptionobj);
		}
		return receivinginvoicearray;
	}

	@RequestMapping(value = "getReceivingDeliveryOrder/{suppliercode}/{jobid}/{staffcode}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getReceivingDeliveryOrderGET(@PathVariable("suppliercode") String suppliercode, @PathVariable("jobid") String jobid, @PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getReceivingDeliveryOrderGET getReceivingDeliveryOrder/" +suppliercode + "/" + jobid + "/" + staffcode);
		JSONArray receivingdeliveryorderarray = new JSONArray();
		
		try{
			receivingdeliveryorderarray = receivinginvoiceService.getReceivingDeliveryOrder(suppliercode, jobid, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getReceivingDeliveryOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			receivingdeliveryorderarray.put(exceptionobj);
		}
		return receivingdeliveryorderarray;
	}
	
	@RequestMapping(value = "receiveDeliveryOrder/{invoicecode}/{purchaseordercode}/{jobid}/{staffcode}/{suppliercode}/{deliveryorderlist:.+}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray receiveDeliveryOrderGET(@Context final HttpServletRequest request,  @Context HttpServletResponse response, @PathVariable("invoicecode") String invoicecode, @PathVariable("purchaseordercode") String purchaseordercode,  @PathVariable("jobid") String jobid, 
			@PathVariable("staffcode") String staffcode, @PathVariable("suppliercode") String suppliercode, @PathVariable("deliveryorderlist") List<String> deliveryorderlist) throws Exception{
		logger.info("Request for receiveDeliveryOrderGET receiveDeliveryOrder/" + invoicecode + "/" + purchaseordercode + "/" + jobid + "/" + staffcode + "/" + suppliercode + "/" + deliveryorderlist);
		JSONArray receivedeliveryorderarray = new JSONArray();
		
		try{
			receivedeliveryorderarray = receivinginvoiceService.receiveDeliveryOrder(request, response, invoicecode, purchaseordercode, jobid, staffcode, suppliercode, deliveryorderlist);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("receiveDeliveryOrderGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			receivedeliveryorderarray.put(exceptionobj);
		}
		return receivedeliveryorderarray;
	}
	
	@RequestMapping(value = "importToAccount/{jobid}/{storecode}/{rinvoicecode}/{purchaseordercode}/{suppliercode}/{purchasedate:.+}/{staffcode}/{invoicenumber:.+}/{duedate:.+}/{subtotalamount:.+}/{totalgst:.+}/{maintotal:.+}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray importToAccountGET(@PathVariable("jobid") String jobid, @PathVariable("storecode") String storecode,@PathVariable("rinvoicecode") String rinvoicecode, @PathVariable("purchaseordercode") String purchaseordercode,  
			@PathVariable("suppliercode") String suppliercode,  @PathVariable("purchasedate") String purchasedate, 
			@PathVariable("staffcode") String staffcode, @PathVariable("invoicenumber") String invoicenumber, @PathVariable("duedate") String duedate, 
			@PathVariable("subtotalamount") String subtotalamount, @PathVariable("totalgst") String totalgst, @PathVariable("maintotal") String maintotal) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for importToAccountGET");
		logger.info("importToAccount/" + jobid + "/" +storecode+ "/" + rinvoicecode+ "/" + purchaseordercode + "/" + suppliercode + "/" +purchasedate + "/" + staffcode + "/" + invoicenumber + "/" + duedate + "/" + subtotalamount + "/" + totalgst + "/" + maintotal);
		JSONArray importtoaccountarray = new JSONArray();
		
		try{
			importtoaccountarray = receivinginvoiceService.importToAccount(jobid, storecode, rinvoicecode, purchaseordercode, suppliercode, purchasedate, staffcode, invoicenumber, duedate,
					subtotalamount, totalgst, maintotal);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("importToAccountGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			importtoaccountarray.put(exceptionobj);
		}
		return importtoaccountarray;
	}
	
	@RequestMapping(value = "getStockingProduct", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getStockingProductGET() throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getStockingProductGET");
		JSONArray stockproductsarray = new JSONArray();
		
		try{
			stockproductsarray = receivinginvoiceService.getStockingProduct();
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getStockingProductGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			stockproductsarray.put(exceptionobj);
		}
		return stockproductsarray;
	}
	
	@RequestMapping(value = "submitScannedProduct/{purchaseordercode}/{productcode}/{stocklocation:.+}/{productsource:.+}/{submittedunit:.+}/{staffcode}/{storecode}/{storelocation}/{jobid}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray submitScannedProductGET(@PathVariable("purchaseordercode") String purchaseordercode, @PathVariable("productcode") String productcode, @PathVariable("stocklocation") String stocklocation, @PathVariable("productsource") String productsource, 
			@PathVariable("submittedunit") String submittedunit, @PathVariable("staffcode") String staffcode, @PathVariable("storecode") String storecode, @PathVariable("storelocation") String storelocation, @PathVariable("jobid") String jobid) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " submitScannedProduct/"+ purchaseordercode + "/" + productcode + "/" + stocklocation + "/" + productsource + "/" + submittedunit + "/" + staffcode + "/" + storecode + "/" + storelocation + "/" + jobid);
		
		JSONArray submitscannedproductsarray = new JSONArray();
		
		try{
			submitscannedproductsarray = receivinginvoiceService.submitScannedProduct(purchaseordercode, productcode, stocklocation, productsource, submittedunit, staffcode, storecode, storelocation, jobid);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitScannedProductGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitscannedproductsarray.put(exceptionobj);
		}
		return submitscannedproductsarray;
	}
	
	@RequestMapping(value = "updatestockingproductjob/{productsource:.+}/{jobid:.+}/{productcode:.+}/{staffcode:.+}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray updatestockingproductjobGET(@PathVariable("productsource") String productsource, 
			@PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " updatestockingproductjob/"+ productsource + "/" + jobid + "/" + productcode + "/" + productsource + "/" + staffcode);
		JSONArray updatestockingproductarray = new JSONArray();
		
		try{
			updatestockingproductarray = receivinginvoiceService.updatestockingproductjob(productsource, jobid, productcode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("updatestockingproductjobGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			updatestockingproductarray.put(exceptionobj);
		}
		return updatestockingproductarray;
	}
	
	@RequestMapping(value = "getGRSummary/{jobid:.+}/{productcode:.+}/{staffcode:.+}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody JSONArray getGRSummaryGET( @PathVariable("jobid") String jobid, 
			@PathVariable("productcode") String productcode, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " getGRSummary/"+ jobid + "/" + productcode + "/" + staffcode);
		JSONArray getsummaryarray = new JSONArray();
		
		try{
			getsummaryarray = receivinginvoiceService.getGRSummary(jobid, productcode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getGRSummaryGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			getsummaryarray.put(exceptionobj);
		}
		return getsummaryarray;
	}
	
	/** POST Methods **/
	@RequestMapping(value = "getReceivingInvoice/{suppliercode}/{jobid}/{staffcode}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getReceivingInvoicePOST(@PathVariable("suppliercode") String suppliercode, @PathVariable("jobid") String jobid, @PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getReceivingInvoicePOST");
		JSONArray receivinginvoicearray = new JSONArray();
		
		try{
			receivinginvoicearray = receivinginvoiceService.getReceivingInvoice(suppliercode, jobid, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			receivinginvoicearray.put(exceptionobj);
			logger.error("getReceivingInvoicePOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return receivinginvoicearray;
	}
	
	@RequestMapping(value = "getReceivingDeliveryOrder/{suppliercode}/{jobid}/{staffcode}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getReceivingDeliveryOrderPOST(@PathVariable("suppliercode") String suppliercode, @PathVariable("jobid") String jobid, @PathVariable("staffcode") String staffcode) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getReceivingDeliveryOrderPOST getReceivingDeliveryOrder/" +suppliercode + "/" + jobid + "/" + staffcode);
		JSONArray receivingdeliveryorderarray = new JSONArray();
		
		try{
			receivingdeliveryorderarray = receivinginvoiceService.getReceivingDeliveryOrder(suppliercode, jobid, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			receivingdeliveryorderarray.put(exceptionobj);
			logger.error("getReceivingInvoicePOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return receivingdeliveryorderarray;
	}
	
	@RequestMapping(value = "receiveDeliveryOrder/{invoicecode}/{purchaseordercode}/{jobid}/{staffcode}/{suppliercode}/{deliveryorderlist:.+}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray receiveDeliveryOrderPOST(@Context final HttpServletRequest request,  @Context HttpServletResponse response, @PathVariable("invoicecode") String invoicecode, @PathVariable("purchaseordercode") String purchaseordercode,  @PathVariable("jobid") String jobid, 
			@PathVariable("staffcode") String staffcode, @PathVariable("suppliercode") String suppliercode, @PathVariable("deliveryorderlist") List<String> deliveryorderlist) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for receiveDeliveryOrderPOST receiveDeliveryOrder/" + invoicecode + "/" + purchaseordercode + "/" + jobid + "/" + staffcode + "/" + suppliercode + "/" + deliveryorderlist);
		JSONArray receivedeliveryorderarray = new JSONArray();
		
		try{
			receivedeliveryorderarray = receivinginvoiceService.receiveDeliveryOrder(request, response, invoicecode, purchaseordercode, jobid, staffcode, suppliercode, deliveryorderlist);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("receiveDeliveryOrderPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			receivedeliveryorderarray.put(exceptionobj);
		}
		return receivedeliveryorderarray;
	}
	
	@RequestMapping(value = "importToAccount/{jobid}/{storecode}/{rinvoicecode}/{purchaseordercode}/{suppliercode}/{purchasedate}/{staffcode}/{invoicenumber:.+}/{duedate:.+}/{subtotalamount:.+}/{totalgst:.+}/{maintotal:.+}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray importToAccountPOST(@PathVariable("jobid") String jobid, @PathVariable("storecode") String storecode,@PathVariable("rinvoicecode") String rinvoicecode, @PathVariable("purchaseordercode") String purchaseordercode,  
			@PathVariable("suppliercode") String suppliercode,  @PathVariable("purchasedate") String purchasedate, 
			@PathVariable("staffcode") String staffcode, @PathVariable("invoicenumber") String invoicenumber, @PathVariable("duedate") String duedate, 
			@PathVariable("subtotalamount") String subtotalamount, @PathVariable("totalgst") String totalgst, @PathVariable("maintotal") String maintotal) throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for importToAccountPOST");
		logger.info("importToAccount/" + jobid + "/" +storecode+ "/" + rinvoicecode+ "/" + purchaseordercode + "/" + suppliercode + "/" +purchasedate + "/" + staffcode + "/" + invoicenumber + "/" + duedate + "/" + subtotalamount + "/" + totalgst + "/" + maintotal);
		JSONArray importtoaccountarray = new JSONArray();
		
		try{
			importtoaccountarray = receivinginvoiceService.importToAccount(jobid, storecode, rinvoicecode, purchaseordercode, suppliercode, purchasedate, staffcode, invoicenumber, duedate, 
					subtotalamount, totalgst, maintotal);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("importToAccountPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			importtoaccountarray.put(exceptionobj);
		}
		return importtoaccountarray;
	}
	
	
	@RequestMapping(value = "getStockingProduct", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getStockingProductPOST() throws Exception{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getStockingProductPOST");
		JSONArray stockproductsarray = new JSONArray();
		
		try{
			stockproductsarray = receivinginvoiceService.getStockingProduct();
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getStockingProductPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			stockproductsarray.put(exceptionobj);
		}
		return stockproductsarray;
	}
	
	@RequestMapping(value = "submitScannedProduct/{purchaseordercode}/{productcode}/{stocklocation:.+}/{productsource:.+}/{submittedunit:.+}/{staffcode}/{storecode}/{storelocation}/{jobid}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray submitScannedProductPOST(@PathVariable("purchaseordercode") String purchaseordercode, @PathVariable("productcode") String productcode, @PathVariable("stocklocation") String stocklocation, @PathVariable("productsource") String productsource, 
			@PathVariable("submittedunit") String submittedunit, @PathVariable("staffcode") String staffcode, @PathVariable("storecode") String storecode, @PathVariable("storelocation") String storelocation, @PathVariable("jobid") String jobid) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " submitScannedProduct/"+ purchaseordercode + "/" + productcode + "/" + stocklocation + "/" + productsource + "/" + submittedunit + "/" + staffcode + "/" + storecode + "/" + storelocation + "/" + jobid);
		JSONArray submitscannedproductsarray = new JSONArray();
		
		try{
			submitscannedproductsarray = receivinginvoiceService.submitScannedProduct(purchaseordercode, productcode, stocklocation, productsource, submittedunit, staffcode, storecode, storelocation, jobid);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitScannedProductPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitscannedproductsarray.put(exceptionobj);
		}
		return submitscannedproductsarray;
	}
	
	@RequestMapping(value = "updatestockingproductjob/{productsource:.+}/{jobid:.+}/{productcode:.+}/{staffcode:.+}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray updatestockingproductjobPOST(@PathVariable("productsource") String productsource, 
			@PathVariable("jobid") String jobid, @PathVariable("productcode") String productcode, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " updatestockingproductjob/"+ productsource + "/" + jobid + "/" + productcode + "/" + productsource + "/" + staffcode);
		JSONArray updatestockingproductarray = new JSONArray();
		
		try{
			updatestockingproductarray = receivinginvoiceService.updatestockingproductjob(productsource, jobid, productcode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("updatestockingproductjobPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			updatestockingproductarray.put(exceptionobj);
		}
		return updatestockingproductarray;
	}
	
	@RequestMapping(value = "getGRSummary/{jobid:.+}/{productcode:.+}/{staffcode:.+}", method = RequestMethod.POST, produces = "Application/json")
	public @ResponseBody JSONArray getGRSummaryPOST( @PathVariable("jobid") String jobid, 
			@PathVariable("productcode") String productcode, @PathVariable("staffcode") String staffcode) throws Exception{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " getGRSummary/"+ jobid + "/" + productcode + "/" + staffcode);
		JSONArray getsummaryarray = new JSONArray();
		
		try{
			getsummaryarray = receivinginvoiceService.getGRSummary(jobid, productcode, staffcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getGRSummaryPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			getsummaryarray.put(exceptionobj);
		}
		return getsummaryarray;
	}
	
}
