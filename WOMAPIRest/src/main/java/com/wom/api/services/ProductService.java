package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;

public interface ProductService {
	public JSONArray getProductDetails(String productcode) throws Exception;
	public JSONArray getProductList(String categorycode, int orderby) throws Exception;
	public JSONArray getProductCategoryList() throws Exception;
	public JSONArray getProductListAll() throws Exception;
	public JSONArray searchProducts(String keyword) throws Exception;
	public JSONArray searchProductCode(String productcode) throws Exception;
	public JSONArray submitNewProduct(String productname, String brand, String categorycode, 
			String storecode, String photocode, String unitquantity, String packquantity, String packformula, String packprice,
			String packweight, String packmass, String stockleveldays, String gst, String uom, String suppliercode) throws Exception;
	public JSONArray editProductDetails(String productcode, String brand, String productname, String price, String gst, 
			String description, String inventorylevel, String checkoutweight, String stockleveldays, String keepfresh, String active) throws Exception;
	public JSONArray getCategoryListAll() throws Exception;
}
