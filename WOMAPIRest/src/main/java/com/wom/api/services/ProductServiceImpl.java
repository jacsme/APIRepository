package com.wom.api.services;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.ProductDao;

public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDao productDao;
	
	@Override
	public JSONArray getProductDetails(String productcode) throws Exception{
		return productDao.getProductDetails(productcode);
	}
	
	@Override
	public JSONArray getProductList(String categorycode, int orderby) throws Exception {
		return productDao.getProductList(categorycode, orderby);
	}

	@Override
	public JSONArray getProductCategoryList() throws Exception {
		return productDao.getProductCategoryList();
	}
	
	public JSONArray getProductListAll() throws Exception{
		return productDao.getProductListAll();
	}

	@Override
	public JSONArray searchProducts(String keyword) throws Exception {
		return productDao.searchProducts(keyword);
	}
	
	@Override
	public JSONArray searchProductCode(String productcode) throws Exception{
		return productDao.searchProductCode(productcode);
	}
	
	@Override
	public JSONArray submitNewProduct(String productname, String brand, String categorycode, 
			String storecode, String photocode, String unitquantity, String packquantity, String packformula, String packprice,
			String packweight, String packmass, String stockleveldays, String gst, String uom, String suppliercode) throws Exception{
		return productDao.submitNewProduct(productname, brand, categorycode, storecode, photocode, unitquantity, packquantity, packformula, packprice, packweight, packmass, stockleveldays, gst, uom, suppliercode);
	}

	@Override
	public JSONArray editProductDetails(String productcode, String brand, String productname, String price, String gst,
			String description, String inventorylevel, String checkoutweight, String stockleveldays, String keepfresh, String active) throws Exception {
		return productDao.editProductDetails(productcode, brand, productname, price, gst, description, inventorylevel, checkoutweight, stockleveldays, keepfresh, active);
	}

	@Override
	public JSONArray getCategoryListAll() throws Exception {
		return productDao.getCategoryListAll();
	}
}
