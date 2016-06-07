package com.wom.api.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.AddressInfo;
import com.wom.api.model.CustomerCart;
import com.wom.api.model.ProductImage;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResourceUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class CustomerCartDaoImpl implements CustomerCartDao{
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(CustomerCartDaoImpl.class);
	public static String serverloc = ResourceUtil.getMessage("server.location");
	FactoryEntityService<AddressInfo> factoryentityAddService = new FactoryEntityServiceImpl<AddressInfo>();
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public JSONArray getCustomerCartList(String customercode) throws Exception {
		JSONArray customercartArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> customercartlist = session.createSQLQuery("CALL SPCustomerCart(:customercode)")
					.setString("customercode", HelperUtil.checkNullString(customercode))
					.list();
			if(customercartlist.size() != 0 && customercartlist != null){
				customercartArray = generateProductListing(customercartlist, session);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				customercartArray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			customercartArray.put(exceptionobj);
			logger.info("getCustomerCartList() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return customercartArray;
	}

	@Override
	public JSONArray addCustomerCart(String productcode, String quantity, String price, String customercode) throws Exception {
		
		JSONObject customercartobj = new JSONObject();
		JSONArray customercartarray = new JSONArray();
		Session session = sessionFactory.openSession();
		try {
			BigInteger customercartid = ResultGeneratorUtil.idGenerator("", "sq_customercart_id", session);
			CustomerCart customercart = new CustomerCart(customercartid, productcode, quantity, price, customercode);
			session.save(customercart);
			customercartobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			customercartobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			customercartarray.put(customercartobj);
			logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
			HibernateUtil.callCommitClose(session);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			customercartarray.put(exceptionobj);
			logger.info("addCustomerCart() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return customercartarray;
	}
	
	@SuppressWarnings("unused")
	@Override
	public JSONArray deleteCustomerCart(String productcode, String customercode) throws Exception {
		
		JSONObject deletecustomercartObj = new JSONObject();
		JSONArray deletecustomercartArray = new JSONArray();
		Session session = sessionFactory.openSession();
		try {
			
			int deleteresult = session.createSQLQuery(QueriesString.deletecustomerCart)
					.setString("productcode", HelperUtil.checkNullString(productcode))
					.setString("customercode", HelperUtil.checkNullString(customercode))
					.executeUpdate();
			HibernateUtil.callCommitClose(session);
			
			deletecustomercartObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			deletecustomercartObj.put("Message", Messages.DELETE_RECORD_SUCCESSFUL);
			deletecustomercartArray.put(deletecustomercartObj);
			logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
		} catch (HibernateException e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deletecustomercartArray.put(exceptionobj);
			logger.info("deleteCustomerCart() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return deletecustomercartArray;
	}
	
	@SuppressWarnings("unused")
	@Override
	public JSONArray updateCustomerCart(String productcode, String customercode, String quantity, String price) throws Exception{
		JSONObject updatecustomercartObj = new JSONObject();
		JSONArray updatecustomercartArray = new JSONArray();
		Session session = sessionFactory.openSession();
		try {
			int updateresult = session.createSQLQuery(QueriesString.updatecustomerCart)
					.setString("quantity", HelperUtil.checkNullString(quantity))
					.setString("price", HelperUtil.checkNullString(price))
					.setString("productcode", HelperUtil.checkNullString(productcode))
					.setString("customercode", HelperUtil.checkNullString(customercode)).executeUpdate();
			HibernateUtil.callCommitClose(session);
			
			updatecustomercartObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			updatecustomercartObj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			updatecustomercartArray.put(updatecustomercartObj);
			logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
		} catch (HibernateException e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			updatecustomercartArray.put(exceptionobj);
			logger.info("updateCustomerCart() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return updatecustomercartArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getCustomerAddresstList(String scustomercode) throws Exception {
		Map<String, String> mapaddressinfoList = new HashMap<String, String>();
		JSONArray customeraddressArray = new JSONArray();
		Session session = sessionFactory.openSession();
		try {
			String customercode = HelperUtil.checkNullString(scustomercode);
			List<AddressInfo> customeraddresslist = session.createCriteria(AddressInfo.class)
						.add(Restrictions.eq("customerCode", new String(customercode)))
						.add(Restrictions.eq("active", new String("YES")))
						.list();
			
			if(customeraddresslist!=null){
				for (AddressInfo addressinfo : customeraddresslist){
					mapaddressinfoList.put("AddressCode", addressinfo.getAddressCode());
					mapaddressinfoList.put("AddressInfo", addressinfo.getAddressInfo());
					mapaddressinfoList.put("AddressType", addressinfo.getAddressType());
					customeraddressArray.put(mapaddressinfoList);
				}
			}else{
				mapaddressinfoList.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				mapaddressinfoList.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				customeraddressArray.put(mapaddressinfoList);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (HibernateException e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			customeraddressArray.put(exceptionobj);
			logger.info("getCustomerAddresstList() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return customeraddressArray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getDeliverySlot(String saddress) throws Exception {
		 
		JSONArray deliveryslotarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		String townname = null; 
		
		Map<String, Map<String, String>> mapdeliveryslotmain = new HashMap<String, Map<String, String>>();
		Map<String, String> mapdeliveryslotList = new HashMap<String, String>();
		StringBuffer sbdelieveryslot = new StringBuffer();
		Session session = sessionFactory.openSession();
		
		try {
			String address = HelperUtil.checkNullString(saddress);
			AddressInfo addressinfo = factoryentityAddService.getEntity(MainEnum.ADDRESSINFO, address, session);
			
			if(addressinfo != null ){
				townname = addressinfo.getCity();
			}
			
			DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
	        String datetoday = HelperUtil.checkNullDateZone(dateTimeKL);
						
			List<String> deliveryslotList = session.createSQLQuery("CALL SPDeliveryTime(:townname, :currdatenow)")
					.setString("townname", HelperUtil.checkNullString(townname))
					.setString("currdatenow", HelperUtil.checkNullString(datetoday))
					.list();
        	
	        DateTime timestartA = new DateTime(datetoday + HelperUtil.PICKUPSTART, DateTimeZone.forID("Asia/Kuala_Lumpur"));
	        DateTime timestartB = new DateTime(datetoday + HelperUtil.PICKUPEND, DateTimeZone.forID("Asia/Kuala_Lumpur"));
			
			//0A.DELIVERYDATE,
			//1A.DELIVERYGROUP,
			//2A.TOWNNAME,
			//3SUM(A.DELTIME) AS TOTALSLOTS
			//4A.SLOT, 5A.MAX
	        int i = 1;
			for (Iterator it = deliveryslotList.iterator(); it.hasNext();){
				Object[] resultListRecord = (Object[]) it.next();
				
				String deliveryDate = HelperUtil.checkNullDateZone(resultListRecord[0]);
				String deliveryGroup = HelperUtil.checkNullString(resultListRecord[1]);
				String timeslotvalue = HelperUtil.checkNullString(resultListRecord[4]);
				String maxslot = HelperUtil.checkNullString(resultListRecord[5]);
				String currslot = HelperUtil.checkNullString(resultListRecord[3]);
				
				if(maxslot.equalsIgnoreCase(currslot)){ timeslotvalue = "-"; }
				if(sbdelieveryslot.length() == 0){ 
					sbdelieveryslot.append(timeslotvalue); 
				}else{ 
					sbdelieveryslot.append(", " + timeslotvalue); 
				}
				
				if(i == 3){	
					mapdeliveryslotList.put(deliveryGroup, sbdelieveryslot.toString());
					mapdeliveryslotmain.put(deliveryDate, mapdeliveryslotList);
					sbdelieveryslot = new StringBuffer();
					i = 0;
				}
				i++;
			}
				
			DateTime mpdeliveryDateTime = null;
			String mpdeliveryDate = null;
			String mpdeliveryGroup = null;
			String mpdeliverySlot = null;
			
			Map<String, Map<String, String>> sortedmap = new TreeMap<String, Map<String, String>>(mapdeliveryslotmain); 
			for (Map.Entry<String, Map<String, String>> delslotmainentry : sortedmap.entrySet()) {
				
				mpdeliveryDateTime = new DateTime(delslotmainentry.getKey());
				mpdeliveryDate = delslotmainentry.getKey();
				
				Map<String, String> entries = delslotmainentry.getValue();
				
				for (Map.Entry<String, String> delslotdetails : entries.entrySet()){
					JSONObject deliveryslotobj = new JSONObject();
					mpdeliveryGroup = delslotdetails.getKey();
					mpdeliverySlot = delslotdetails.getValue();
				
			        if(datetoday.equals(mpdeliveryDate)){
			        	if(dateTimeKL.isBefore(timestartA) && dateTimeKL.isBefore(timestartB)){
			        		if("A".equalsIgnoreCase(HelperUtil.checkNullString(mpdeliveryGroup))){
			        			deliveryslotobj.put("DeliveryDate", HelperUtil.checkNullDateSlot(mpdeliveryDateTime));	
								deliveryslotobj.put("DeliverySlotsA", mpdeliverySlot);
								deliveryslotarray.put(deliveryslotobj);
							}
			        	}else if(dateTimeKL.isAfter(timestartA) && dateTimeKL.isBefore(timestartB) && "A".equalsIgnoreCase(mpdeliveryGroup)){
			        			deliveryslotobj.put("DeliveryDate", HelperUtil.checkNullDateSlot(mpdeliveryDateTime));	
								deliveryslotobj.put("DeliverySlotsA", "");
								deliveryslotarray.put(deliveryslotobj);
			        	
			        	}else if(dateTimeKL.isAfter(timestartA) && dateTimeKL.isBefore(timestartB) && "B".equalsIgnoreCase(mpdeliveryGroup)){
			        			deliveryslotobj.put("DeliveryDate", HelperUtil.checkNullDateSlot(mpdeliveryDateTime));	
								deliveryslotobj.put("DeliverySlotsB", mpdeliverySlot);
								deliveryslotarray.put(deliveryslotobj);
			        	}
			        }else{
						if("A".equalsIgnoreCase(mpdeliveryGroup)){
							deliveryslotobj.put("DeliveryDate", HelperUtil.checkNullDateSlot(mpdeliveryDateTime));
							deliveryslotobj.put("DeliverySlotsA", mpdeliverySlot);
							deliveryslotarray.put(deliveryslotobj);
						}else if ("B".equalsIgnoreCase(mpdeliveryGroup)){
							deliveryslotobj.put("DeliveryDate", HelperUtil.checkNullDateSlot(mpdeliveryDateTime));
							deliveryslotobj.put("DeliverySlotsB", mpdeliverySlot);
							deliveryslotarray.put(deliveryslotobj);
						}
			        }
				}
			}
		} catch (HibernateException e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deliveryslotarray.put(exceptionobj);
			logger.info("getDeliverySlot() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return deliveryslotarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray checkAvailableStocks(String storecode, List<String> productcodelist) throws Exception{
		
		JSONObject exceptionobj = new JSONObject();
		String tmpproductcode = null;
		JSONArray resultArray = new JSONArray();
		Session session = sessionFactory.openSession();
		try{
			
			for(String productcode:productcodelist){
				List<String> stockproductlist = null;
				Map<String, String> resultmap = new HashMap<String, String>();
				if(productcode.contains("&")){
					tmpproductcode = productcode.substring(1, productcode.length());
				}else{
					tmpproductcode = productcode.substring(0, productcode.length());
				}
				stockproductlist = session.createSQLQuery("CALL SPAvailableProductUnit(:productcode, :storecode)")
						.setString("productcode", HelperUtil.checkNullString(tmpproductcode))
						.setString("storecode", HelperUtil.checkNullString(storecode))
						.list();
				
				if(stockproductlist.size() != 0){
					for (Iterator it = stockproductlist.iterator(); it.hasNext();){
						Object[] stockproductslistRecord = (Object[]) it.next();
						
						//0F.STOCKLOCATION, 1F.PRODUCTCODE, 2F.STOCKCODE, 
						//3AVAILABLESTOCKQTY
						resultmap.put(tmpproductcode, HelperUtil.checkNullNumbers(stockproductslistRecord[3]));
						resultArray.put(resultmap);
					}
				}else{
					resultmap.put(tmpproductcode, "0");
					resultArray.put(resultmap);
				}
			}
		}catch(Exception e){
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			resultArray.put(exceptionobj);
			logger.info("checkAvailableStocks() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return resultArray;
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
		BigDecimal unitquantity = new BigDecimal(0.00);
		
		//0B.STORECODE, 1A.STORENAME, 
		//2B.PRODUCTCODE, 3B.PRODUCTNAME, 4B.BRAND, 
		//5B.CATEGORYCODE, 6B.PACKPRICE, 7B.PACKQUANTITY, 
		//8B.PACKFORMULA, 9B.PACKWEIGHT, 10B.PACKMASS, 
		//11'1' AS UNITPRICE, 
		//12B.UNITQUANTITY,
		//13'1' AS UNITWEIGHT,
		//14'1' AS COMPAREPRICE, 
		//15B.COMPAREWEIGHT, 16C.FILENAME, 17C.FILETYPE, 
		//18'1' AS PACKCONTENT, 
		//19B.GST, 20.B.PHOTOCODE, 21E.AVAILABLESTOCKQTY
		//22A.QUANTITY, 23A.PRICE, 24A.CUSTOMERCODE, 25A.STATUS  - FOR CUSTOMERCART
		//26B.DESCRIPTION, 27B.KEEPFRESH, 28B.DISCOUNT, 29BCOMPAREMASS
		//30B.DISCOUNTAMOUNT, 31B.PROMOTIONALPRICE, 32B.RRPRICE
	    
		for (Iterator it = resultList.iterator(); it.hasNext();){
			StringBuffer packcontentssb = new StringBuffer();
			Object[] resultListRecord = (Object[]) it.next();
			
			strpackmass = HelperUtil.checkNullMass(resultListRecord[10]);
			packprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[6]));
			rrprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[32]));
			packweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[9]));
			compareweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[15]));
			unitquantity = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[12]));
			
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
			
			discountamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[30]));
			promotionalprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[31]));
			//COMPAREPRICE
			
			if (strpackmass.equalsIgnoreCase("EACH") || strpackmass.equalsIgnoreCase("BOXES") || strpackmass.equalsIgnoreCase("PIECES")
					 || strpackmass.equalsIgnoreCase("PACK") || strpackmass.equalsIgnoreCase("TABLETS") || strpackmass.equalsIgnoreCase("BOX")
					 || strpackmass.equalsIgnoreCase("ROLL") || strpackmass.equalsIgnoreCase("DOZEN") || strpackmass.equalsIgnoreCase("UNIT")){
				
				newcompareprice = packprice.divide(packweight, 2, RoundingMode.HALF_UP);
				oldcompareprice = (packprice.add(discountamount)).divide(packweight, 2, RoundingMode.HALF_UP);
				resultmap.put("PromoComparePrice", newcompareprice.toString());
				resultmap.put("ComparePrice", oldcompareprice.toString());
				compareweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[15]));
				
			}else if (strpackmass.equalsIgnoreCase("GRAM") || strpackmass.equalsIgnoreCase("ML") || strpackmass.equalsIgnoreCase("MG")){
				compareweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[15]));
				//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
				if (packweight.compareTo(new BigDecimal(100.00)) == -1){
					newcompareprice = packprice.divide(packweight, 4, RoundingMode.HALF_UP).multiply(compareweight).divide(unitquantity, 2, RoundingMode.HALF_UP);
					oldcompareprice = (packprice.add(discountamount)).divide(packweight, 4, RoundingMode.HALF_UP).multiply(compareweight).divide(unitquantity, 2, RoundingMode.HALF_UP);
					resultmap.put("PromoComparePrice", newcompareprice.toString());
					resultmap.put("ComparePrice", oldcompareprice.toString());
				}else{
					newcompareprice = packprice.divide(packweight.divide(compareweight, 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP).divide(unitquantity, 2, RoundingMode.HALF_UP);
					oldcompareprice = (packprice.add(discountamount)).divide(packweight.divide(compareweight, 2, RoundingMode.HALF_UP), 2, RoundingMode.HALF_UP).divide(unitquantity, 2, RoundingMode.HALF_UP);
					resultmap.put("PromoComparePrice", newcompareprice.toString());
					resultmap.put("ComparePrice", oldcompareprice.toString());
				}
			}else{
				compareweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[15]));
				newcompareprice = packprice.divide(packweight.divide(compareweight, 2, RoundingMode.HALF_EVEN), 2, RoundingMode.HALF_EVEN).divide(unitquantity, 2, RoundingMode.HALF_EVEN);
				oldcompareprice = (packprice.add(discountamount)).divide(packweight.divide(compareweight, 2, RoundingMode.HALF_EVEN), 2, RoundingMode.HALF_EVEN).divide(unitquantity, 2, RoundingMode.HALF_EVEN);
				resultmap.put("PromoComparePrice", newcompareprice.toString());
				resultmap.put("ComparePrice", oldcompareprice.toString());
			}
			
			resultmap.put("CompareMass", HelperUtil.checkNullString(resultListRecord[29]));
			resultmap.put("CompareWeight", compareweight.toString());
			
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
			resultmap.put("AvailableStockQty",  HelperUtil.checkNullNumbers(resultListRecord[21]));
			resultmap.put("PackContents", packcontentssb.toString());
			
			resultmap.put("Quantity", HelperUtil.checkNullNumbers(resultListRecord[22]));
			resultmap.put("Price", HelperUtil.checkNullAmount(resultListRecord[23]));
			resultmap.put("CustomerCode", HelperUtil.checkNullString(resultListRecord[24]));
			resultmap.put("Status", HelperUtil.checkNullString(resultListRecord[25]));
			resultmap.put("Description", HelperUtil.checkNullString(resultListRecord[26]));
			resultmap.put("KeepFresh", HelperUtil.checkNullString(resultListRecord[27]));
			resultmap.put("Discount", HelperUtil.checkNullNumbers(resultListRecord[28]));
			resultmap.put("DiscountAmount", discountamount.toString());
			resultmap.put("PromotionalPrice", promotionalprice.toString());
			resultmap.put("RRPrice", rrprice.toString());
			resultArray.put(resultmap);
		}
		return resultArray;
	}
}
