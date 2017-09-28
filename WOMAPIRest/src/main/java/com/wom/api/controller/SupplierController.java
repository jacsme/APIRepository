package com.wom.api.controller;

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
import com.wom.api.services.ProductService;
import com.wom.api.services.SupplierService;
import com.wom.api.util.HelperUtil;

@Controller
public class SupplierController {
	
	@Autowired
	SupplierService supplierService;

	@Autowired
	ProductService productService;
	
	static final Logger logger = Logger.getLogger(SupplierController.class);

	/** GET Methods 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getSupplierList/{productcode}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getSupplierListGET(@PathVariable("productcode") String productcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getSupplierListGET");
		
		JSONArray supplierlistarray =  new JSONArray();
		
		try { 
			supplierlistarray = supplierService.getSupplierList(productcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getSupplierListGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			supplierlistarray.put(exceptionobj);
		}
		return supplierlistarray;
	}
	
	@RequestMapping(value = "getSupplierListAll", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getSupplierListAllGET() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getSupplierListAllGET");
		
		JSONArray supplierlistallarray =  new JSONArray();
		
		try { 
			supplierlistallarray = supplierService.getSupplierListAll();
			supplierlistallarray.put(productService.getProductCategoryList());
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getSupplierListAllGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			supplierlistallarray.put(exceptionobj);
		}
		return supplierlistallarray;
	}
	
	@RequestMapping(value = "submitSupplier/{suppliername:.+}/{address:.+}/{phone:.+}/{fax:.+}/{website:.+}/{email:.+}/{contactperson:.+}/{gstid}/{contactpersonphone:.+}/{productcode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray submitSupplierGET(@PathVariable("suppliername") String suppliername, @PathVariable("address") String address, @PathVariable("phone") String phone, @PathVariable("fax") String fax, @PathVariable("website") String website, 
			@PathVariable("email") String email, @PathVariable("contactperson") String contactperson, @PathVariable("gstid") String gstid, @PathVariable("contactpersonphone") String contactpersonphone, @PathVariable("productcode") String productcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for submitSupplierGET  - submitSupplier/" + suppliername + "/" + address + "/" + phone + "/" + fax + "/" + website + "/" + email + "/" + contactperson + "/" + gstid + "/" + contactpersonphone + "/" + productcode);
		
		JSONArray submitsupplierarray =  new JSONArray();
		
		try { 
			submitsupplierarray = supplierService.submitSupplier(suppliername, address, phone, fax, website, email, contactperson, gstid, contactpersonphone, productcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			submitsupplierarray.put(exceptionobj);
			logger.error("submitSupplierGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return submitsupplierarray;
	}
	
	@RequestMapping(value = "searchSupplier/{keyword:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray searchSupplierGET(@PathVariable("keyword") String keyword) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for seachSupplierGET");
		
		JSONArray seacrhsupplierarray =  new JSONArray();
		
		try { 
			seacrhsupplierarray = supplierService.searchSupplier(keyword);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			seacrhsupplierarray.put(exceptionobj);
			logger.error("searchSupplierGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return seacrhsupplierarray;
	}
	
	/** POST Methods 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getSupplierList/{productcode}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getSupplierListPOST(@PathVariable("productcode") String productcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getSupplierListPOST");
		
		JSONArray supplierlistarray =  new JSONArray();
		
		try { 
			supplierlistarray = supplierService.getSupplierList(productcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getSupplierListPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			supplierlistarray.put(exceptionobj);
		}
		return supplierlistarray;
	}
	
	@RequestMapping(value = "getSupplierListAll", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getSupplierListAllPOST() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for getSupplierListAllPOST");
		
		JSONArray supplierlistallarray =  new JSONArray();
		
		try { 
			supplierlistallarray = supplierService.getSupplierListAll();
			supplierlistallarray.put(productService.getProductCategoryList());
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getSupplierListAllPOS() ----- TStatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			supplierlistallarray.put(exceptionobj);
		}
		return supplierlistallarray;
	}
	
	@RequestMapping(value = "submitSupplier/{suppliername:.+}/{address:.+}/{phone:.+}/{fax:.+}/{website:.+}/{email:.+}/{contactperson:.+}/{gstid:.+}/{contactpersonphone:.+}/{productcode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray submitSupplierPOST(@PathVariable("suppliername") String suppliername, @PathVariable("address") String address, @PathVariable("phone") String phone, @PathVariable("fax") String fax, @PathVariable("website") String website, 
			@PathVariable("email") String email, @PathVariable("contactperson") String contactperson, @PathVariable("gstid") String gstid, @PathVariable("contactpersonphone") String contactpersonphone, @PathVariable("productcode") String productcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for submitSupplierPOST - submitSupplier/" + suppliername + "/" + address + "/" + phone + "/" + fax + "/" + website + "/" + email + "/" + contactperson + "/" + gstid + "/" + contactpersonphone + "/" + productcode);
		
		JSONArray submitsupplierarray =  new JSONArray();
		
		try { 
			submitsupplierarray = supplierService.submitSupplier(suppliername, address, phone, fax, website, email, contactperson, gstid, contactpersonphone, productcode);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitSupplierPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			submitsupplierarray.put(exceptionobj);
		}
		return submitsupplierarray;
	}
	
	@RequestMapping(value = "searchSupplier/{keyword:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray searchSupplierPOST(@PathVariable("keyword") String keyword) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for seachSupplierPOST");
		
		JSONArray seacrhsupplierarray =  new JSONArray();
		
		try { 
			seacrhsupplierarray = supplierService.searchSupplier(keyword);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("searchSupplierPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			seacrhsupplierarray.put(exceptionobj);
		}
		return seacrhsupplierarray;
	}
}
