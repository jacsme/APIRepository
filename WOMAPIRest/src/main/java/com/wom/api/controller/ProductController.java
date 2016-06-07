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
import com.wom.api.services.ReportService;
import com.wom.api.util.HelperUtil;

@Controller
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ReportService reportServices;
	
	static final Logger logger = Logger.getLogger(ProductController.class);

	/** GET Request 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getProductList/{categogycode:.+}/{orderby}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getProductListGet(@PathVariable("categogycode") String categogycode, @PathVariable("orderby") int orderby) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getProductList/" + categogycode + "/" + orderby );
		
		JSONArray productlistarray = new JSONArray();
		
		try {
			productlistarray = productService.getProductList(categogycode, orderby);
			productlistarray.put(reportServices.generateProductCategoryCounts());
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getProductListGet() -----StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			productlistarray.put(exceptionobj);
		}
		return productlistarray;
	}
	
	@RequestMapping(value = "getProductListAll", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	JSONArray getProductListAllGET() throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getProductListAllGET()");
		
		JSONArray productlistarray = new JSONArray();
		
		try {
			productlistarray = productService.getProductListAll();
			productlistarray.put(reportServices.generateProductCategoryCounts());
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			productlistarray.put(exceptionobj);
			logger.error("getProductListAllGET() -----StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return productlistarray;
	}
	
	@RequestMapping(value = "getProductCategoryList", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getCatgoryListGET() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getCategoryListGET()");
		
		JSONArray categorylistarray = new JSONArray();
		
		try{
			categorylistarray = productService.getProductCategoryList();
			categorylistarray.put(reportServices.generateProductCategoryCounts());
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			categorylistarray.put(exceptionobj);
			logger.error("getCatgoryListGET() -----StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return categorylistarray;
	}
	
	@RequestMapping(value = "getCategoryListAll", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getCategoryListAllGET() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getCategoryListAllGET()");
		
		JSONArray categorylistallarray = new JSONArray();
		
		try{
			categorylistallarray = productService.getCategoryListAll();
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			categorylistallarray.put(exceptionobj);
			logger.error("getCategoryListAllGET() -----StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return categorylistallarray;
	}
	
	@RequestMapping(value = "getProductDetails/{productcode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray getProductDetailsGET(@PathVariable("productcode") String productcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getProductDetails/" + productcode );
		
		JSONArray categorylistarray = new JSONArray();
		
		try{
			categorylistarray = productService.getProductDetails(productcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getProductDetailsGET() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			categorylistarray.put(exceptionobj);
		}
		return categorylistarray;
	}
	
	@RequestMapping(value = "searchProducts/{keyword:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray searchProductGET(@PathVariable("keyword") String keyword) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " searchProducts/" + keyword);
		
		JSONArray searchProductArray = new JSONArray();
		
		try{
			searchProductArray = productService.searchProducts(keyword);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("searchProductGET() -----StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			searchProductArray.put(exceptionobj);
		}
		return searchProductArray;
	}
	
	@RequestMapping(value = "searchProductCode/{productcode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray searchProductCodeGET(@PathVariable("productcode") String productcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " searchProductCode/" + productcode );
		
		JSONArray searchProductCodeArray = new JSONArray();
		
		try{
			searchProductCodeArray = productService.searchProductCode(productcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("searchProductCodeGET() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			searchProductCodeArray.put(exceptionobj);
		}
		return searchProductCodeArray;
	}
	
	@RequestMapping(value = "submitNewProduct/{productname:.+}/{brand:.+}/{categorycode}/{storecode}/{photocode:.+}/{unitquantity:.+}/{packquantity:.+}/{packformula:.+}/{packprice:.+}/{packweight:.+}/{packmass:.+}/{stockleveldays:.+}/{gst:.+}/{uom:.+}/{supppliercode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray submitNewProductGET(@PathVariable("productname") String productname, @PathVariable("brand") String brand, 
			@PathVariable("categorycode") String categorycode, @PathVariable("storecode") String storecode, @PathVariable("photocode") String photocode, @PathVariable("unitquantity") String unitquantity,
			@PathVariable("packquantity") String packquantity, @PathVariable("packformula") String packformula, @PathVariable("packprice") String packprice, @PathVariable("packweight") String packweight,
			@PathVariable("packmass") String packmass, @PathVariable("stockleveldays") String stockleveldays, @PathVariable("gst") String gst, @PathVariable("uom") String uom, @PathVariable("supppliercode") String supppliercode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for submitNewProductGET()");
		
		JSONArray newProductArray = new JSONArray();
		
		try{
			newProductArray = productService.submitNewProduct(productname, brand, categorycode, storecode, photocode, unitquantity, packquantity, packformula, packprice, packweight, packmass, stockleveldays, gst, uom, supppliercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			newProductArray.put(exceptionobj);
			logger.error("submitNewProductGET() -----StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return newProductArray;
	}	
			
	@RequestMapping(value = "editProductDetails/{productcode}/{brand}/{productname:.+}/{price:.+}/{gst:.+}/{description:.+}/{inventorylevel:.+}/{checkoutweight:.+}/{keepfresh:.+}/{active:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONArray submitNewProductGET(@PathVariable("productcode") String productcode, @PathVariable("brand") String brand, @PathVariable("productname") String productname, @PathVariable("price") String price, 
			@PathVariable("gst") String gst, @PathVariable("description") String description, @PathVariable("inventorylevel") String inventorylevel, 
			@PathVariable("checkoutweight") String checkoutweight, @PathVariable("stockleveldays") String stockleveldays, @PathVariable("keepfresh") String keepfresh, @PathVariable("active") String active) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + "  Request for editProductDetailsGET()");
		
		JSONArray editProductArray = new JSONArray();
		
		try{
			editProductArray = productService.editProductDetails(productcode, brand, productname, price, gst, description, inventorylevel, checkoutweight, stockleveldays, keepfresh, active);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitNewProductGET() -----StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			editProductArray.put(exceptionobj);
		}
		return editProductArray;
	}	
	
	/** POST Request 
	 * @throws JSONException **/
	
	@RequestMapping(value = "getProductList/{categogycode}/{orderby}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getProductListPOST(@PathVariable("categogycode") String categogycode, @PathVariable("orderby") int orderby) throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getProductList/"+ categogycode + "/" + orderby);
		
		JSONArray productlistarray = new JSONArray();
		
		try {
			productlistarray = productService.getProductList(categogycode, orderby);
			productlistarray.put(reportServices.generateProductCategoryCounts());
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getProductListPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			productlistarray.put(exceptionobj);
			
		}
		return productlistarray;
	}
	
	@RequestMapping(value = "getProductListAll", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	JSONArray getProductListAllPOST() throws JSONException {
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getProductListAllPOST()");
		
		JSONArray productlistarray = new JSONArray();
		
		try {
			productlistarray = productService.getProductListAll();
			productlistarray.put(reportServices.generateProductCategoryCounts());
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getProductListAllPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			productlistarray.put(exceptionobj);
		}
		return productlistarray;
	}
	
	@RequestMapping(value = "getCategoryListAll", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getCategoryListAllPOST() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getCategoryListAllPOST()");
		
		JSONArray categorylistallarray = new JSONArray();
		
		try{
			categorylistallarray = productService.getCategoryListAll();
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			categorylistallarray.put(exceptionobj);
			logger.error("getCategoryListAllPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return categorylistallarray;
	}
	
	@RequestMapping(value = "getProductCategoryList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getProductCatgoryListPOST() throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " Request for getCategoryListPOST()");
		
		JSONArray categorylistarray = new JSONArray();
		
		try{
			categorylistarray = productService.getProductCategoryList();
			categorylistarray.put(reportServices.generateProductCategoryCounts());
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getProductCatgoryListPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			categorylistarray.put(exceptionobj);
		}
		return categorylistarray;
	}
	
	@RequestMapping(value = "getProductDetails/{productcode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray getProductDetailsPOST(@PathVariable("productcode") String productcode) throws JSONException{
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " getProductDetails/" + productcode);
		
		JSONArray productcategorylistarray = new JSONArray();
		
		try{
			productcategorylistarray = productService.getProductDetails(productcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("getProductDetailsPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			productcategorylistarray.put(exceptionobj);
		}
		return productcategorylistarray;
	}
	
	@RequestMapping(value = "searchProducts/{keyword:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray searchProductPOST(@PathVariable("keyword") String keyword) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_GROCERY + " searchProducts/" + keyword);
		
		JSONArray searchProductArray = new JSONArray();
		
		try{
			searchProductArray = productService.searchProducts(keyword);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("searchProductPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			searchProductArray.put(exceptionobj);
		}
		return searchProductArray;
	}
	
	@RequestMapping(value = "searchProductCode/{productcode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray searchProductCodePOST(@PathVariable("productcode") String productcode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " searchProductCode/" + productcode);
		
		JSONArray searchProductCodeArray = new JSONArray();
		
		try{
			searchProductCodeArray = productService.searchProductCode(productcode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("searchProductCodePOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			searchProductCodeArray.put(exceptionobj);
		}
		return searchProductCodeArray;
		
	}
	
	@RequestMapping(value = "submitNewProduct/{productname:.+}/{brand:.+}/{categorycode}/{storecode}/{photocode:.+}/{unitquantity:.+}/{packquantity:.+}/{packformula:.+}/{packprice:.+}/{packweight:.+}/{packmass:.+}/{stockleveldays:.+}/{gst:.+}/{uom:.+}/{supppliercode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray submitNewProductPOST(@PathVariable("productname") String productname, @PathVariable("brand") String brand, 
			@PathVariable("categorycode") String categorycode, @PathVariable("storecode") String storecode, @PathVariable("photocode") String photocode, @PathVariable("unitquantity") String unitquantity,
			@PathVariable("packquantity") String packquantity, @PathVariable("packformula") String packformula, @PathVariable("packprice") String packprice, @PathVariable("packweight") String packweight,
			@PathVariable("packmass") String packmass, @PathVariable("stockleveldays") String stockleveldays, @PathVariable("gst") String gst, @PathVariable("uom") String uom, @PathVariable("supppliercode") String supppliercode) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for submitNewProductPOST()");
		
		JSONArray newProductArray = new JSONArray();
		
		try{
			newProductArray = productService.submitNewProduct(productname, brand, categorycode, storecode, photocode, unitquantity, packquantity, packformula, packprice, packweight, packmass, stockleveldays, gst, uom, supppliercode);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			newProductArray.put(exceptionobj);
			logger.error("submitNewProductPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return newProductArray;
	}
	
	@RequestMapping(value = "editProductDetails/{productcode}/{brand}/{productname:.+}/{price:.+}/{gst:.+}/{description:.+}/{inventorylevel:.+}/{checkoutweight:.+}/{stockleveldays}/{keepfresh:.+}/{active:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONArray submitNewProductPOST(@PathVariable("productcode") String productcode, @PathVariable("brand") String brand, @PathVariable("productname") String productname, @PathVariable("price") String price, 
			@PathVariable("gst") String gst, @PathVariable("description") String description, @PathVariable("inventorylevel") String inventorylevel, 
			@PathVariable("checkoutweight") String checkoutweight, @PathVariable("stockleveldays") String stockleveldays, @PathVariable("keepfresh") String keepfresh, @PathVariable("active") String active) throws JSONException{
		
		logger.info(HelperUtil.SERVER_DOMAIN_MANAGEMENT + " Request for editProductDetails()");
		
		JSONArray editProductArray = new JSONArray();
		
		try{
			editProductArray = productService.editProductDetails(productcode, brand, productname, price, gst, description, inventorylevel, checkoutweight, stockleveldays,  keepfresh, active);
		}catch(Exception e){
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("submitNewProductPOST() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			editProductArray.put(exceptionobj);
		}
		return editProductArray;
	}	
}
