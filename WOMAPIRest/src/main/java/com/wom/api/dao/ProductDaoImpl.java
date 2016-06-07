package com.wom.api.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.Category;
import com.wom.api.model.Product;
import com.wom.api.model.ProductImage;
import com.wom.api.model.ProductSupplier;
import com.wom.api.model.Supplier;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResourceUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class ProductDaoImpl implements ProductDao{
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	JobListDao joblistDao;

	static final Logger logger = Logger.getLogger(ProductDaoImpl.class);
	public static String serverloc = ResourceUtil.getMessage("server.location");
	FactoryEntityService<Product> factoryentityProducts = new FactoryEntityServiceImpl<Product>();
	FactoryEntityService<Supplier> factoryentitySupplier = new FactoryEntityServiceImpl<Supplier>();
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public JSONArray getProductCategoryList() throws Exception {
		JSONArray categoryListArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			
			List<String> categoryList = session.createSQLQuery("CALL SPCategoryList()")
					.list();
			if (categoryList.size() != 0 && categoryList != null){
				categoryListArray = ResultGeneratorUtil.populateresults(categoryList, MainEnum.CATEGORY);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				categoryListArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			categoryListArray.put(exceptionobj);
			logger.info("getProductCategoryList() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return categoryListArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getCategoryListAll() throws Exception {
		JSONArray categoryListAllArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Map<String, String> resultmap = new HashMap<String, String>();
		Session session = sessionFactory.openSession();
		try {
			
			List<Category> categoryListAll = (List<Category>) session.createCriteria(Category.class)
					.add(Restrictions.eq("active", new String("YES")))
					.list();

			if (categoryListAll.size() != 0 && categoryListAll != null){
				for(Category category : categoryListAll){
					resultmap.put("CategoryCode", HelperUtil.checkNullString(category.getCategoryCode()));
					resultmap.put("CategoryName", HelperUtil.checkNullString(category.getCategoryName()));
					resultmap.put("SubCategory", HelperUtil.checkNullString(category.getSubCategory()));
					categoryListAllArray.put(resultmap);	
				}
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				categoryListAllArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			categoryListAllArray.put(exceptionobj);
			logger.info("getCategoryListAll() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return categoryListAllArray;
	}
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public JSONArray getProductList(String categorycode, int orderby) throws Exception {
		JSONArray productListArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> productList = session.createSQLQuery("CALL SPProductList(:categorycode, :orderby)")
					.setString("categorycode", HelperUtil.checkNullString(categorycode))
					.setString("orderby", Integer.toString(orderby))
					.list();
			if (productList.size() != 0 && productList != null){
				productListArray = generateProductListing(productList, session);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				productListArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			productListArray.put(exceptionobj);
			logger.info("getProductList() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return productListArray;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public JSONArray getProductListAll() throws Exception {
		JSONArray productListAllArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> productListAll = session.createSQLQuery("CALL SPProductListAll()")
					.list();
			if (productListAll.size() != 0 && productListAll != null){
				productListAllArray = generateProductListing(productListAll, session);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				productListAllArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			productListAllArray.put(exceptionobj);
			logger.info("getProductListAll() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return productListAllArray;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public JSONArray searchProducts(String keyword) throws Exception {
		JSONArray searchProductListArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> searchProductList = session.createSQLQuery(QueriesString.productQuery  
					+ QueriesString.productGroupby
					+ QueriesString.searchProductsWhere
					+ QueriesString.productOrderby)
					.setString("keyproductname", HelperUtil.checkNullString(keyword))
					.setString("keybrand", HelperUtil.checkNullString(keyword)).list();
			if (searchProductList.size() != 0 && searchProductList != null){
				searchProductListArray = generateProductListing(searchProductList, session);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				searchProductListArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			searchProductListArray.put(exceptionobj);
			logger.info("searchProducts() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return searchProductListArray;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public JSONArray searchProductCode(String productcode) throws Exception {
		JSONArray searchProductCodeListArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> searchProductList = session.createSQLQuery("CALL SPProductDetails(:productcode)")
					.setString("productcode", HelperUtil.checkNullString(productcode))
					.list();
			if (searchProductList.size() != 0 && searchProductList != null){
				searchProductCodeListArray = generateProductListing(searchProductList, session);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				searchProductCodeListArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			searchProductCodeListArray.put(exceptionobj);
			logger.info("searchProductCode() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return searchProductCodeListArray;
	}
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public JSONArray getProductDetails(String productcode) throws Exception {
		
		JSONArray productListArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> productList = session.createSQLQuery("CALL SPProductDetails(:productcode)")
					.setString("productcode", HelperUtil.checkNullString(productcode))
					.list();
			if (productList.size() != 0 && productList != null){
				productListArray = generateProductListing(productList, session);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				productListArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			productListArray.put(exceptionobj);
			logger.info("getProductDetails() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return productListArray;
	}
	
	/**
	private String productName;
	private String brand;
	private String categoryCode;
	private String storeCode;
	private String photoCode;
	private String unitQuantity;
	private String packQuantity;
	private String packFormula;
	private String packPrice;
	private String packWeight;
	private String packMass;
	private String stockLevelDays;
	private String gst;
	private String uom;
	**/
	
	@Override
	public JSONArray submitNewProduct(String productname, String brand, String categorycode, 
			String storecode, String photocode, String unitquantity, String packquantity, String packformula, String packprice,
			String packweight, String packmass, String stockleveldays, String gst, String uom, String suppliercode) throws Exception{
		JSONArray newproductarray = new JSONArray();
		JSONObject newproductobj = new JSONObject();
		String yearnow = HelperUtil.yearformat.format(new Date());
		Session session = sessionFactory.openSession();
		try {
			
			String productcode = ResultGeneratorUtil.codeGenerator("", "sq_product_code", "22", session);
			String strproductcode = productcode + yearnow;
			Product product = new Product(strproductcode, productname, brand, categorycode, storecode, photocode, unitquantity,
					 packquantity, packformula, packprice, packweight, packmass, stockleveldays, gst, uom);
			session.save(product);
			
			Supplier supplier = factoryentitySupplier.getEntity(MainEnum.SUPPLIERCODE, suppliercode, session);
			
			if (supplier != null){
				
				ProductSupplier productsupplier = new ProductSupplier();
				productsupplier.setProductCode(strproductcode);
				productsupplier.setSupplierCode(suppliercode);
				
				Session session2 = HibernateUtil.callSession(sessionFactory);
			
				joblistDao.createJobList("", productcode + yearnow, "DP000003", "Stock Control", "", session2);
				HibernateUtil.callCommitClose(session);
			
				newproductobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				newproductobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				newproductarray.put(newproductobj);
			}else{
				newproductobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				newproductobj.put("Message", Messages.SUPPLIER_NOT_EXIST);
				newproductarray.put(newproductobj);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			newproductarray.put(exceptionobj);
			logger.info("submitNewProduct() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
			
		}
		return newproductarray;
	}
	
	@Override
	public JSONArray editProductDetails(String productcode, String brand, String productname, String price, String gst, 
			String description, String inventorylevel, String checkoutweight, String stockleveldays, String keepfresh, String active) throws Exception{
		
		JSONArray editProductDetailsarray = new JSONArray();
		JSONObject editProductDetailsobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			
			Product product = factoryentityProducts.getEntity(MainEnum.PRODUCT, productcode, session);
			if (product != null){
				
				if(!"0".equalsIgnoreCase(brand)){ product.setBrand(brand); }
				if(!"0".equalsIgnoreCase(productname)){ product.setProductName(productname); }
				if(!"0".equalsIgnoreCase(price)){ product.setPackPrice(price); }
				if(!"0".equalsIgnoreCase(gst)){ product.setGst(gst); }
				if(!"0".equalsIgnoreCase(description)){ product.setDescription(description); }
				if(!"0".equalsIgnoreCase(inventorylevel)){ product.setInventoryLevel(inventorylevel); }
				if(!"0".equalsIgnoreCase(checkoutweight)){ product.setCheckoutWeight(checkoutweight); }
				if(!"0".equalsIgnoreCase(stockleveldays)){ product.setStockLevelDays(stockleveldays); }
				if(!"0".equalsIgnoreCase(keepfresh)){ product.setKeepFresh(keepfresh); }
				if(!"0".equalsIgnoreCase(active)){ product.setActive(active); }
				
				session.update(product);
				HibernateUtil.callCommitClose(session);
				
				editProductDetailsobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				editProductDetailsobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				editProductDetailsarray.put(editProductDetailsobj);
				
			}else{
				editProductDetailsobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				editProductDetailsobj.put("Message", Messages.SUPPLIER_NOT_EXIST);
				editProductDetailsarray.put(editProductDetailsobj);
			}
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			editProductDetailsarray.put(exceptionobj);
			
		}
		return editProductDetailsarray;
	}
	
	public JSONArray submitPromotional(String productcode, String brand, String productname, String price, String gst, 
			String description, String inventorylevel, String checkoutweight, String stockleveldays, String keepfresh, String active) throws Exception{
		
		JSONArray editProductDetailsarray = new JSONArray();
		JSONObject editProductDetailsobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			Product product = factoryentityProducts.getEntity(MainEnum.PRODUCT, productcode, session);
			if (product != null){
				
				if(!"0".equalsIgnoreCase(brand)){ product.setBrand(brand); }
				if(!"0".equalsIgnoreCase(productname)){ product.setProductName(productname); }
				if(!"0".equalsIgnoreCase(price)){ product.setPackPrice(price); }
				if(!"0".equalsIgnoreCase(gst)){ product.setGst(gst); }
				if(!"0".equalsIgnoreCase(description)){ product.setDescription(description); }
				if(!"0".equalsIgnoreCase(inventorylevel)){ product.setInventoryLevel(inventorylevel); }
				if(!"0".equalsIgnoreCase(checkoutweight)){ product.setCheckoutWeight(checkoutweight); }
				if(!"0".equalsIgnoreCase(stockleveldays)){ product.setStockLevelDays(stockleveldays); }
				if(!"0".equalsIgnoreCase(keepfresh)){ product.setKeepFresh(keepfresh); }
				if(!"0".equalsIgnoreCase(active)){ product.setActive(active); }
				
				session.update(product);
				HibernateUtil.callCommitClose(session);
				
				editProductDetailsobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				editProductDetailsobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				editProductDetailsarray.put(editProductDetailsobj);
				
			}else{
				editProductDetailsobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				editProductDetailsobj.put("Message", Messages.SUPPLIER_NOT_EXIST);
				editProductDetailsarray.put(editProductDetailsobj);
			}
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			editProductDetailsarray.put(exceptionobj);
			HibernateUtil.callClose(session);
		}
		return editProductDetailsarray;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONArray generateProductListing(List<String> resultList, Session session){
		
		Map<String, String> resultmap = new HashMap<String, String>();
		JSONArray resultArray = new JSONArray();
		
		String strpackmass = null;
		BigDecimal newcompareprice = new BigDecimal(0.00);
		BigDecimal oldcompareprice = new BigDecimal(0.00);
		BigDecimal discountamount = new BigDecimal(0.00);
		BigDecimal promotionalprice = new BigDecimal(0.00);
		
		BigDecimal gstamount = new BigDecimal(0.00);
		BigDecimal packprice = new BigDecimal(0.00);
		BigDecimal rrprice = new BigDecimal(0.00);
		BigDecimal packweight = new BigDecimal(0.00);
		BigDecimal compareweight = new BigDecimal(0.00);
		String newcompareweight = "";
		BigDecimal unitquantity = new BigDecimal(0.00);
		BigDecimal packquantity = new BigDecimal(0.00);
		
		//0B.STORECODE, 1A.STORENAME, 
		//2B.PRODUCTCODE, 3B.PRODUCTNAME, 4B.BRAND, 
		//5B.CATEGORYCODE, 6B.PACKPRICE, 7B.PACKQUANTITY, 
		//8B.PACKFORMULA, 9B.PACKWEIGHT, 10B.PACKMASS, 
		//11'1' AS UNITPRICE, 
		//12B.UNITQUANTITY,
		//13'1' AS UNITWEIGHT,
		//14'1' AS COMPAREPRICE, 
		//15B.COMPAREWEIGHT, 
		//16C.FILENAME, 17C.FILETYPE, 
		//18'1' AS PACKCONTENT, 
		//19B.GST, 20.B.PHOTOCODE, 21E.AVAILABLESTOCKQTY
		//22B.DESCRIPTION, 23B.KEEPFRESH, 24B.DISCOUNT, 25B.COMPAREMASS
		//26B.DISCOUNTAMOUNT, 27B.PROMOTIONALPRICE
	    
		for (Iterator it = resultList.iterator(); it.hasNext();){
			StringBuffer packcontentssb = new StringBuffer();
			Object[] resultListRecord = (Object[]) it.next();
			
			packprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[6]));
			rrprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[28]));
			packweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[9]));
			unitquantity = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[12]));
			packquantity = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[7]));
			
			resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[0]));
			resultmap.put("StoreName", HelperUtil.checkNullString(resultListRecord[1]));
			resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[2]));
			resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[3]));
			resultmap.put("Brand", HelperUtil.checkNullString(resultListRecord[4]));
			resultmap.put("CategoryCode", HelperUtil.checkNullString(resultListRecord[5]));
			resultmap.put("PackPrice", HelperUtil.checkNullAmount(resultListRecord[6]));
			resultmap.put("PackQuantity", HelperUtil.checkNullNumbers(resultListRecord[7]));
			resultmap.put("PackFormula", HelperUtil.checkNullString(resultListRecord[8]));
			resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[9]));
			resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[10]));
			resultmap.put("UnitPrice", HelperUtil.checkNullAmount(resultListRecord[6]));
			resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[12]));
			resultmap.put("UnitWeight", HelperUtil.checkNullString(resultListRecord[9]));
			
			//COMPAREPRICE
			discountamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[26]));
			promotionalprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[27]));
			
			strpackmass = HelperUtil.checkNullMass(resultListRecord[25]);
			if (strpackmass.equalsIgnoreCase("BOXES") || strpackmass.equalsIgnoreCase("PIECES")
					 || strpackmass.equalsIgnoreCase("PACK") || strpackmass.equalsIgnoreCase("TABLETS") || strpackmass.equalsIgnoreCase("BOX")
					 || strpackmass.equalsIgnoreCase("ROLL") || strpackmass.equalsIgnoreCase("DOZEN") || strpackmass.equalsIgnoreCase("UNIT")){
				compareweight = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[15]));
				if(packquantity.compareTo(new BigDecimal(0.00)) == 1){
					newcompareprice = packprice.divide(packquantity, 2, RoundingMode.HALF_UP);
					oldcompareprice = (rrprice.add(discountamount)).divide(packquantity, 2, RoundingMode.HALF_UP);
				}else{
					newcompareprice = packprice.divide(compareweight, 2, RoundingMode.HALF_UP);
					oldcompareprice = (rrprice.add(discountamount)).divide(compareweight, 2, RoundingMode.HALF_UP);
				}
				
				resultmap.put("PromoComparePrice", newcompareprice.toString());
				resultmap.put("ComparePrice", oldcompareprice.toString());
				
				resultmap.put("CompareMass", HelperUtil.checkNullString(resultListRecord[25]));
				resultmap.put("CompareWeight", newcompareweight);
				
			}else if (strpackmass.equalsIgnoreCase("GRAM") || strpackmass.equalsIgnoreCase("ML") || strpackmass.equalsIgnoreCase("MG") || strpackmass.equalsIgnoreCase("EACH")){
				compareweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[15]));
				//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
				if (packweight.compareTo(new BigDecimal(100.00)) == -1){
					newcompareprice = packprice.divide(packweight, 4, RoundingMode.HALF_UP).multiply(compareweight).divide(unitquantity, 2, RoundingMode.HALF_UP);
					oldcompareprice = (rrprice.add(discountamount)).divide(packweight, 4, RoundingMode.HALF_UP).multiply(compareweight).divide(unitquantity, 2, RoundingMode.HALF_UP);
					resultmap.put("PromoComparePrice", newcompareprice.toString());
					resultmap.put("ComparePrice", oldcompareprice.toString());
				}else{
					newcompareprice = packprice.divide(packweight.divide(compareweight, 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP).divide(unitquantity, 2, RoundingMode.HALF_UP);
					oldcompareprice = (rrprice.add(discountamount)).divide(packweight.divide(compareweight, 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP).divide(unitquantity, 2, RoundingMode.HALF_UP);
					resultmap.put("PromoComparePrice", newcompareprice.toString());
					resultmap.put("ComparePrice", oldcompareprice.toString());
				}
				resultmap.put("CompareMass", HelperUtil.checkNullString(resultListRecord[25]));
				resultmap.put("CompareWeight", newcompareweight);	
			}else{
				compareweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[15]));
				if(compareweight.compareTo(new BigDecimal(100.00)) == 0){ compareweight = new BigDecimal(0.1);}
				
				newcompareprice = packprice.divide(packweight.divide(compareweight, 2, RoundingMode.HALF_EVEN), 2, RoundingMode.HALF_EVEN).divide(unitquantity, 2, RoundingMode.HALF_EVEN);
				oldcompareprice = (rrprice.add(discountamount)).divide(packweight.divide(compareweight, 2, RoundingMode.HALF_EVEN), 2, RoundingMode.HALF_EVEN).divide(unitquantity, 2, RoundingMode.HALF_EVEN);
				resultmap.put("PromoComparePrice", newcompareprice.toString());
				resultmap.put("ComparePrice", oldcompareprice.toString());
				
				resultmap.put("CompareWeight", "100");
				if(HelperUtil.checkNullMass(resultListRecord[10]).equals("KG")){ resultmap.put("CompareMass", "GRAM");}
				if(HelperUtil.checkNullMass(resultListRecord[10]).equals("LITRE")){ resultmap.put("CompareMass", "ML");}
			}
			
			if (StringUtils.substringAfter(compareweight.toString(), ".").equals("00")){
				newcompareweight = StringUtils.substringBefore(compareweight.toString(), ".");
			}else{
				newcompareweight = compareweight.toString();
			}
			
			//connect to productimage
			List<ProductImage> productimage = null;
			productimage = (List<ProductImage>) session.createCriteria(ProductImage.class)
					.add(Restrictions.eq("productCode", new String(HelperUtil.checkNullString(resultListRecord[2]))))
					.list();
			if(productimage.size() != 0){
				for(ProductImage imageslist:productimage){
					if (imageslist.getFileSize().equalsIgnoreCase("144")){
						resultmap.put("ImageURL", HelperUtil.PRODUCT_IMAGE_LOCATION + HelperUtil.checkNullString(imageslist.getProductCode() + "_" + imageslist.getFileSize() + "_" + imageslist.getSide() + "." + imageslist.getFileType()));	
					}else{
						if(imageslist.getExist().equalsIgnoreCase("YES")){
							resultmap.put("ImageURL" + imageslist.getFileSize() + imageslist.getSide(), HelperUtil.PRODUCT_IMAGE_LOCATION + HelperUtil.checkNullString(imageslist.getProductCode() + "_" + imageslist.getFileSize() + "_" + imageslist.getSide() + "." + imageslist.getFileType()));
						}else{
							resultmap.put("ImageURL" + imageslist.getFileSize() + imageslist.getSide(), "-");
						}
					}
				}
			}else{
				resultmap.put("ImageURL", "-");
				resultmap.put("ImageURL720A", "-");
				resultmap.put("ImageURL720B", "-");
				resultmap.put("ImageURL720C", "-");
				resultmap.put("ImageURL720D", "-");
				resultmap.put("ImageURL720E", "-");
				resultmap.put("ImageURL720F", "-");
				resultmap.put("ImageURL720G", "-");
				resultmap.put("ImageURL1600A", "-");
				resultmap.put("ImageURL1600B", "-");
				resultmap.put("ImageURL1600C", "-");
				resultmap.put("ImageURL1600D", "-");
				resultmap.put("ImageURL1600E", "-");
				resultmap.put("ImageURL1600F", "-");
				resultmap.put("ImageURL1600G", "-");
			}
			
			if(!(HelperUtil.checkNullString(resultListRecord[9]).equalsIgnoreCase("0"))){
				
				if(HelperUtil.checkNullNumbers(resultListRecord[12]).equalsIgnoreCase("1")){
					packcontentssb.append(HelperUtil.checkNullString(resultListRecord[9]) + " " + HelperUtil.checkNullMass(resultListRecord[10]));
				}else{
					packcontentssb.append(HelperUtil.checkNullNumbers(resultListRecord[12]));
					packcontentssb.append(" x " + HelperUtil.checkNullString(resultListRecord[9]) + " " + HelperUtil.checkNullMass(resultListRecord[10]));
					if(!(HelperUtil.checkNullNumbers(resultListRecord[7]).equalsIgnoreCase("0"))){
						packcontentssb.append(" (" + HelperUtil.checkNullNumbers(resultListRecord[7]) + " Pack)");
					}
				}
			}else{
				if((HelperUtil.checkNullNumbers(resultListRecord[12]).equalsIgnoreCase("0")) && !(HelperUtil.checkNullNumbers(resultListRecord[10]).equalsIgnoreCase("0"))){
					packcontentssb.append("(" + HelperUtil.checkNullNumbers(resultListRecord[7]) + " Pack)");
				}else{
					packcontentssb.append(HelperUtil.checkNullNumbers(resultListRecord[12]) + " Each");
				}
			}
			
			if("S".equalsIgnoreCase(HelperUtil.checkNullString(resultListRecord[19]))){
				BigDecimal gstrate = new BigDecimal((String) HelperUtil.GST_RATE);
				gstamount = packprice.multiply(gstrate).setScale(2, BigDecimal.ROUND_HALF_UP);
				resultmap.put("GST", gstamount.toString());
			}else{
				resultmap.put("GST", "0.00");
			}
			
			resultmap.put("PhotoCode", HelperUtil.checkNullString(resultListRecord[20]));
			resultmap.put("AvailableStockQty", HelperUtil.checkNullNumbers(resultListRecord[21]));
			resultmap.put("Description", HelperUtil.checkNullString(resultListRecord[22]));
			resultmap.put("KeepFresh", HelperUtil.checkNullString(resultListRecord[23]));
			resultmap.put("PackContents", packcontentssb.toString());
			resultmap.put("Discount", HelperUtil.checkNullNumbers(resultListRecord[24]));
			resultmap.put("DiscountAmount", discountamount.toString());
			resultmap.put("PromotionalPrice", promotionalprice.toString());
			resultmap.put("RRPrice", rrprice.toString());
			resultArray.put(resultmap);
		}
		return resultArray;
	}
}
