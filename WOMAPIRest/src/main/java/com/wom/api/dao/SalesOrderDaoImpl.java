package com.wom.api.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.AddressInfo;
import com.wom.api.model.BoxDelivery;
import com.wom.api.model.BudgetList;
import com.wom.api.model.Customer;
import com.wom.api.model.CustomerCard;
import com.wom.api.model.CustomerCart;
import com.wom.api.model.DeliveryCoverage;
import com.wom.api.model.Inventory;
import com.wom.api.model.JobList;
import com.wom.api.model.JobListSO;
import com.wom.api.model.Product;
import com.wom.api.model.PurchaseOrder;
import com.wom.api.model.SalesOrderDetails;
import com.wom.api.model.SalesOrderInfo;
import com.wom.api.model.StockControl;
import com.wom.api.model.Store;
import com.wom.api.pdf.PDFGenerator;
import com.wom.api.pdf.UploadPurchaseOrder;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class SalesOrderDaoImpl implements SalesOrderDao {

	FactoryEntityService<Store> factoryentityService = new FactoryEntityServiceImpl<Store>();
	FactoryEntityService<PurchaseOrder> factoryentitypoService = new FactoryEntityServiceImpl<PurchaseOrder>();
	FactoryEntityService<CustomerCart> factoryentitylistService = new FactoryEntityServiceImpl<CustomerCart>();
	FactoryEntityService<AddressInfo> factoryentityAddService = new FactoryEntityServiceImpl<AddressInfo>();
	FactoryEntityService<Customer> factoryentityCustomerService = new FactoryEntityServiceImpl<Customer>();
	FactoryEntityService<SalesOrderInfo> factoryentitySalesOrder = new FactoryEntityServiceImpl<SalesOrderInfo>();
	FactoryEntityService<CustomerCard> factoryentityCCard = new FactoryEntityServiceImpl<CustomerCard>();
	FactoryEntityService<Store> factorystoreService = new FactoryEntityServiceImpl<Store>();
	FactoryEntityService<BoxDelivery> factoryentityboxService = new FactoryEntityServiceImpl<BoxDelivery>();
	FactoryEntityService<DeliveryCoverage> factoryentityDeliveryCoverage = new FactoryEntityServiceImpl<DeliveryCoverage>();
	FactoryEntityService<Inventory> factoryentityInventory = new FactoryEntityServiceImpl<Inventory>();
	FactoryEntityService<SalesOrderDetails> factoryentitySalesOrderDetails = new FactoryEntityServiceImpl<SalesOrderDetails>();
	FactoryEntityService<StockControl> factoryentityStockControlService = new FactoryEntityServiceImpl<StockControl>();
	FactoryEntityService<Product> factoryproductService = new FactoryEntityServiceImpl<Product>();
	
	@Autowired
	JobListDao joblistDao;
	
	static final Logger logger = Logger.getLogger(SalesOrderDaoImpl.class);
	static boolean boxondelivery = false;
	static boolean withavailablestocks = true;
	static String insufficientproductcode = null;
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getSalesOrder(String storecode, String jobid, String staffcode) throws Exception{
		Map<String, String> resultmap = new HashMap<String, String>();
		
		JSONArray salesorderjoblistArray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			if(HibernateUtil.updateJobListSO(jobid, "Sales Order", "Sales Order", staffcode, "DP000005", sessionFactory)==true){
				List<String> salesorderList = session.createSQLQuery("CALL SPGetSalesOrder(:jobid, :storecode)")
						.setString("jobid", HelperUtil.checkNullString(jobid))
						.setString("storecode", HelperUtil.checkNullString(storecode)).list();
				
				if (salesorderList.size() != 0){
					
					//0A.JOBID, 1B.SALESORDERCODE, 2B.QUANTITY, 3C.PRODUCTCODE, 4C.PRODUCTNAME, 5C.BRAND,
					//6C.UNITQUANTITY, 7D.AVAILABLEUNIT, 8D.STOCKLOCATION, 9C.PACKWEIGHT, 10C.PACKMASS, 
					//11A.STORECODE, 12A.DELIVERYDATE, 13A.DELIVERYTIME, 14A.DELIVERYGROUP, 15E.SIDE
					
					for (Iterator it = salesorderList.iterator(); it.hasNext();){
						Object[] resultListRecord = (Object[]) it.next();
						
						resultmap.put("JobId",  HelperUtil.checkNullString(resultListRecord[0]));
						resultmap.put("SalesOrderCode", HelperUtil.checkNullString(resultListRecord[1]));
						resultmap.put("Quantity", HelperUtil.checkNullNumbers(resultListRecord[7]));
						resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[3]));
						resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[4]));
						resultmap.put("Brand", HelperUtil.checkNullString(resultListRecord[5]));
						resultmap.put("AvailableUnit", HelperUtil.checkNullNumbers(resultListRecord[7]));
						resultmap.put("ProductRackingCode", HelperUtil.checkNullString(resultListRecord[8]));
						resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[9]));
						resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[10]));
						resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[11]));
						resultmap.put("DeliveryDate", HelperUtil.checkNullString(resultListRecord[12]));
						resultmap.put("DeliveryTime", HelperUtil.checkNullString(resultListRecord[13]));
						resultmap.put("DeliveryGroup", HelperUtil.checkNullString(resultListRecord[14]));
						resultmap.put("DeliveryRackingCode", HelperUtil.checkNullString(resultListRecord[15]) + HelperUtil.checkNullString(resultListRecord[14]));
						resultmap.put("StockCode", HelperUtil.checkNullString(resultListRecord[16]));
						salesorderjoblistArray.put(resultmap);
					}
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					salesorderjoblistArray.put(exceptionobj);
				}
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				salesorderjoblistArray.put(takenobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			salesorderjoblistArray.put(exceptionobj);
			logger.info("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return salesorderjoblistArray;
	}

	/** Make a new parameter Combine (if combine is YES, then use the WOMcoin first and the remaining will be charge to CC 
	 * if combine is no then charge all to Credit card **/
	//combine
	// salesorderdetails- [productcode, price, quantity, gst, disc]
	// address should be addresscode
	@Override
	public JSONArray submitSalesOrder(HttpServletRequest request, HttpServletResponse response, String storecode, String address, String salesorderdate, String paymentmethod, String contactnumber,
			String note, String customercode, List<String> salesorderdetailsList, String addresstype, String combine, String postcode, String deliverytime, String deliverydate) throws Exception {
		
		logger.info("SalesOrderDaoImpl " + salesorderdetailsList);

		JSONArray salesorderArray = new JSONArray();
		JSONObject salesorderObject = new JSONObject();
		BigDecimal availablewomcoins = new BigDecimal(0.00);
		BigDecimal currentpurchaseamount = new BigDecimal(0.00);
		
		BigDecimal previouspurchaseamount = new BigDecimal(0.00);
		BigDecimal totalcurrentpurchasenogst = new BigDecimal(0.00);
		BigDecimal totalcurrentpurchaseexgst = new BigDecimal(0.00);
		BigDecimal totalcurrentpurchaseall = new BigDecimal(0.00);
		BigDecimal totalcurrentpurchasegst = new BigDecimal(0.00);
		
		BigDecimal totalpreviouspurchase = new BigDecimal(0.00);
		boolean insufficientwomcoin = false;
		String wcpaidamount = "0.00";
		String ccpaidamount = "0.00";
		String spaymentmethod = null;
		
		BigDecimal gsttotal = new BigDecimal(0.00);
		BigDecimal gst = new BigDecimal(0.00);
		BigDecimal gstamount = new BigDecimal(0.00);
		
		BigDecimal percentdiscount = new BigDecimal(0.00);
		BigDecimal percentdiscountamount = new BigDecimal(0.00);
		BigDecimal quantity = new BigDecimal(0.00);
		
		BigDecimal discountamount = new BigDecimal(0.00);
		BigDecimal discountcurrentpurchase = new BigDecimal(0.00);
		BigDecimal discountcurrentpurchasegst = new BigDecimal(0.00);
		String salesordercode = null;
		String addressinfodetails = null;
		String maincontactnumber = null;
		Session session = sessionFactory.openSession();
		try {
			salesordercode = ResultGeneratorUtil.codeGenerator("", "sq_sales_order", "SO22", session);
			//[productcode, price, quantity, gst, disc]
			List<SalesOrderDetails> salesorderdetailslist = setSalesOrderDetails(salesordercode, storecode, salesorderdetailsList, session);
			if (!withavailablestocks){
				
				salesorderObject.put("ProductCode", insufficientproductcode);
				salesorderObject.put("StatusCode", StatusCode.INSUFFICIENT_STOCKS);
				salesorderObject.put("Message", Messages.INSUFFICIENT_STOCKS_MESSAGE);
				logger.info("ProductCode " + insufficientproductcode + "StatusCode:" + StatusCode.INSUFFICIENT_STOCKS + " Message:" + Messages.INSUFFICIENT_STOCKS_MESSAGE);
				withavailablestocks = false;
				insufficientproductcode = null;
				
			}else{
				
				for(SalesOrderDetails salesorderdetails:salesorderdetailslist){
					//compute gst per item
					Product productname= factoryproductService.getEntity(MainEnum.PRODUCT, salesorderdetails.getProductCode(), session);
					percentdiscount = new BigDecimal((String) HelperUtil.checkNullAmount(productname.getDiscount()));
					percentdiscountamount = new BigDecimal((String) HelperUtil.checkNullAmount(productname.getDiscountAmount()));
					currentpurchaseamount = new BigDecimal((String) salesorderdetails.getPrice());
					quantity = new BigDecimal((String) HelperUtil.checkNullNumbers(salesorderdetails.getQuantity()));
					
					//get the discount Compare equal value == 0, Compare Left > right == 1, Left < right == -1
					if(percentdiscount.compareTo(new BigDecimal(0.00)) == 1){
						percentdiscount = percentdiscount.divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP);
						discountamount = currentpurchaseamount.multiply(percentdiscount);
						discountcurrentpurchase	= currentpurchaseamount.subtract(discountamount);
					
					}else if(percentdiscount.compareTo(new BigDecimal(0.00)) == 0 && percentdiscountamount.compareTo(new BigDecimal(0.00)) == 1) {
						discountamount = quantity.multiply(percentdiscountamount);
						discountcurrentpurchase	= currentpurchaseamount.subtract(discountamount);
						
					}else{
						discountamount = new BigDecimal(0.00);
						discountcurrentpurchase = currentpurchaseamount;
					}
					
					if("S".equalsIgnoreCase(productname.getGst())){
						
						gst = new BigDecimal((String) HelperUtil.checkNullAmount(HelperUtil.GST_RATE));	
						gstamount = discountcurrentpurchase.multiply(gst).setScale(2, RoundingMode.HALF_UP);
						discountcurrentpurchasegst = discountcurrentpurchase.add(gstamount);
						
						totalcurrentpurchasegst = totalcurrentpurchasegst.add(discountcurrentpurchasegst);
						
					}else{
						gstamount = new BigDecimal(0.00);
						totalcurrentpurchasenogst = totalcurrentpurchasenogst.add(discountcurrentpurchase);
					}
					
					salesorderdetails.setPrice(discountcurrentpurchase.toString());
					salesorderdetails.setGst(gstamount.toString());
					salesorderdetails.setDiscount(discountamount.toString());
					
					totalcurrentpurchaseexgst = totalcurrentpurchaseexgst.add(discountcurrentpurchase);
					totalcurrentpurchaseall = totalcurrentpurchaseall.add(discountcurrentpurchase.add(gstamount));
					gsttotal = gsttotal.add(gstamount);
				}
				
				//check the previous purchase
				//TODO check to cyrus the equivalent of womcoin
				List<SalesOrderInfo> salesorderinfolist = factoryentitySalesOrder.getEntityList(MainEnum.SALESORDER, customercode, "WOMcoin", session);
				for(SalesOrderInfo salesinfo:salesorderinfolist){
					if(!salesinfo.getSalesOrderCode().equalsIgnoreCase(salesordercode)){
						previouspurchaseamount = new BigDecimal((String) salesinfo.getWcPaidAmount()); //CustomerDaoImpl.computePurchaseAmount(salesinfo, customercode);
						totalpreviouspurchase = totalpreviouspurchase.add(previouspurchaseamount);
					}
				}
				
				Customer customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMER, customercode, session);
				maincontactnumber = customer.getPhoneNumber();
				availablewomcoins = CustomerDaoImpl.computeAvailableWOMCoins(customer, totalpreviouspurchase, customercode);
				
				//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
				if("WOMcoin".equalsIgnoreCase(paymentmethod)){
					if(availablewomcoins.compareTo(totalcurrentpurchaseall) == 1 || (availablewomcoins.compareTo(totalcurrentpurchaseall) == 0)){
						wcpaidamount = totalcurrentpurchaseall.toString();
						ccpaidamount = "0.00";
					}else{
						if(availablewomcoins.compareTo(new BigDecimal(0.00)) == -1){
							ccpaidamount = totalcurrentpurchaseall.toString();
							wcpaidamount = "0.00";
						}else{
							wcpaidamount = availablewomcoins.toString();
							ccpaidamount = totalcurrentpurchaseall.subtract(availablewomcoins).toString();
						}
					}
					spaymentmethod = paymentmethod;
				}else{			
					ccpaidamount = totalcurrentpurchaseall.toString();
					spaymentmethod = paymentmethod;
				}
				
				if(insufficientwomcoin){
					salesorderObject.put("StatusCode", StatusCode.UNSUCCESSFUL_CODE);
					salesorderObject.put("Message", Messages.INSUFFICIENT_WOMCOINS_MESSAGE);
					logger.info("StatusCode:" + StatusCode.UNSUCCESSFUL_CODE + " Message:" + Messages.INSUFFICIENT_WOMCOINS_MESSAGE);
				}else{
					
					SalesOrderInfo salesorderinfo;
					String strsalesorderdate = HelperUtil.parsejsondate("1", salesorderdate);
					String strdeliverydate = HelperUtil.parsejsondate("1", deliverydate);
					
					String prioritynumber = null;
					String deliverygroup = null;
					
					if(HelperUtil.DELIVERYSLOT_A.contains(deliverytime)){ prioritynumber = "1"; deliverygroup = "A";}
					if(HelperUtil.DELIVERYSLOT_B.contains(deliverytime)){ prioritynumber = "2"; deliverygroup = "B";}
					
					String townname = null;		
					String mpostcode = null;
					
					AddressInfo addressinfo = factoryentityAddService.getEntity(MainEnum.ADDRESSINFO, customercode, address, session);
					
					if(addressinfo != null ){
						townname = addressinfo.getCity();
						mpostcode = addressinfo.getPostCode();
						addressinfodetails = addressinfo.getAddressInfo();
					}
					
					BigInteger salesorderid = ResultGeneratorUtil.idGenerator("", "sq_sales_order_id", session);
					String joblistid = joblistDao.createJobListSO("", "", "DP000005", "Sales Order", "", session);
					
					salesorderinfo = new SalesOrderInfo(salesorderid, salesordercode, storecode, 
							address, strsalesorderdate, spaymentmethod, contactnumber, note, joblistid, 
							customercode, wcpaidamount, ccpaidamount, mpostcode,
							strdeliverydate, deliverytime, prioritynumber, townname, deliverygroup);
					
					salesorderinfo.setSalesorderdetails(salesorderdetailslist);
					
					List<CustomerCart> customercartlist = factoryentitylistService.getEntityList(MainEnum.CUSTOMERCART, customercode, session);
					if(customercartlist.size() !=0){
						for(CustomerCart cart : customercartlist){
							session.delete(cart);
						}
					}
					
					Store store = factorystoreService.getEntity(MainEnum.STORE, HelperUtil.STORE_CODE, session);
					session.save(salesorderinfo);
					
					//insert to our budget table
					if(!wcpaidamount.equalsIgnoreCase("0.00")){
						BigInteger budgetid1 = ResultGeneratorUtil.idGenerator("", "sq_budget_id", session);
						//TODO verify to cyrus if womcoin payment counted as sales. because it is already paid and counted as sales before.
						BudgetList budgetlistwomcoin = new BudgetList(budgetid1, storecode, wcpaidamount, "0.00", salesordercode, "WOMCoin", "", "SO");
						session.save(budgetlistwomcoin);
					}
					if(!ccpaidamount.equalsIgnoreCase("0.00")){
						BigInteger budgetid2 = ResultGeneratorUtil.idGenerator("", "sq_budget_id", session);
						BudgetList budgetlistcc = new BudgetList(budgetid2, storecode, ccpaidamount, "0.00", salesordercode, "Credit Card", "", "SO");
						session.save(budgetlistcc);
					}
					
					if (PDFGenerator.generateTaxInvoicePDF(request, salesordercode, store.getGst(), customercode,
							addressinfodetails, strsalesorderdate, salesorderdetailslist, totalcurrentpurchaseall, gsttotal, 
							totalcurrentpurchasenogst, totalcurrentpurchaseexgst, totalcurrentpurchasegst, "PROFORMA INVOICE", maincontactnumber, session)==true){
							
						if(UploadPurchaseOrder.uploadtoS3(salesordercode, HelperUtil.SO_IMAGE_LOCATION + salesordercode + ".pdf", HelperUtil.SO_BUCKET_NAME) == true){
							HibernateUtil.callCommitClose(session);
							salesorderObject.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
							salesorderObject.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
							salesorderObject.put("CCardAmountDue", ccpaidamount);
						}
					}
				}
			}
			salesorderArray.put(salesorderObject);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			salesorderArray.put(exceptionobj);
			logger.info("submitSalesOrder() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return salesorderArray;
	}
	
	public String setCustomerAddress(String storecode, String address, String customercode, String addresstype, Session session) throws Exception {
		String addresscode = null;
		try {
			String completeaddess = address;
			if(!completeaddess.contains( HelperUtil.SERVER_COUNTRY)){
				completeaddess = address + ", " + HelperUtil.SERVER_COUNTRY;
			}
			
			AddressInfo addressinfo = null;
			List<AddressInfo> addressinfolist = factoryentityAddService.getEntityList(MainEnum.ADDRESSINFO, customercode, session);
			
			Store storeaddress = factoryentityService.getEntity(MainEnum.STORE, storecode, session);
			String googleaddress = getGoogleAddress(completeaddess, storeaddress.getAddress());
			BigInteger addressid = ResultGeneratorUtil.idGenerator("", "sq_address_id", session);
			
			if(addressinfolist.size()==0){
				
				addresscode = ResultGeneratorUtil.codeGenerator("", "sq_address_code", "AD22", session);
				addressinfo = new AddressInfo(addressid, addresscode, customercode, completeaddess, googleaddress, addresstype);
				session.save(addressinfo);
			}else{
				for(AddressInfo addresslist:addressinfolist){
					if(address.equals(addresslist.getAddressInfo())){
						addresscode = addresslist.getAddressCode();
						break;
					}
				}
				if(addresscode==null){
					addresscode = ResultGeneratorUtil.codeGenerator("", "sq_address_code", "AD22", session);
					addressinfo = new AddressInfo(addressid, addresscode, customercode, completeaddess, googleaddress, addresstype);
					session.save(addressinfo);
				}
			}
		} catch (Exception e) {
			logger.info("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return addresscode;
	}
	
	@Override
	public JSONArray submitCustomerAddress(String customercode, String addresstype, String postcode, String state, String city, String	area,
			String street, String number, String building, String unit) throws Exception {
		Map<String, String> resultmap = new HashMap<String, String>();
		StringBuffer completeaddess = new StringBuffer();
		
		JSONArray addressArray = new JSONArray();
		String addresscode = null;
		boolean existingaddress = false;
		Session session = sessionFactory.openSession();
		try {
			if(!unit.equalsIgnoreCase("0")){ completeaddess.append(unit + ", "); }
			if(!building.equalsIgnoreCase("0")){ completeaddess.append(building + ", "); }
			if(!number.equalsIgnoreCase("0")){ completeaddess.append(number + ", "); }
			if(!street.equalsIgnoreCase("0")){ completeaddess.append(street + ", "); }
			if(!area.equalsIgnoreCase("0")){ completeaddess.append(" " + area + ", "); }
			if(!city.equalsIgnoreCase("0") && !postcode.equalsIgnoreCase("0")) {  completeaddess.append(postcode + " " + city + ", ");} 
			if(!state.equalsIgnoreCase("0")){ completeaddess.append(state + ", " + HelperUtil.SERVER_COUNTRY); }
			
			AddressInfo addressinfo = null;
			List<AddressInfo> addressinfolist = factoryentityAddService.getEntityList(MainEnum.ADDRESSINFO, customercode, session);
			
			Store storeaddress = factoryentityService.getEntity(MainEnum.STORE, HelperUtil.STORE_CODE, session);
			String googleaddress = getGoogleAddress(completeaddess.toString(), storeaddress.getAddress());
			BigInteger addressid = ResultGeneratorUtil.idGenerator("", "sq_address_id", session);
			
			if(addressinfolist.size()==0){
				addresscode = ResultGeneratorUtil.codeGenerator("", "sq_address_code", "AD22", session);
				
				addressinfo = new AddressInfo(addressid, addresscode, customercode, completeaddess.toString(),  
						postcode, state, city, area, street, number, building, unit, addresstype, googleaddress);
				session.save(addressinfo);
				
				resultmap.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				resultmap.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				
			}else{
				for(AddressInfo addresslist:addressinfolist){
					if(postcode.equalsIgnoreCase(addresslist.getPostCode()) && state.equalsIgnoreCase(addresslist.getState()) 
							&& city.equalsIgnoreCase(addresslist.getCity()) && area.equalsIgnoreCase(addresslist.getArea())
							&& street.equalsIgnoreCase(addresslist.getStreet()) && number.equalsIgnoreCase(addresslist.getNumber())
							&& building.equalsIgnoreCase(addresslist.getBuilding()) && unit.equalsIgnoreCase(addresslist.getUnit()) 
							){
						
						resultmap.put("StatusCode", StatusCode.ALREADY_EXIST);
						resultmap.put("Message", Messages.RECORD_ALREADY_EXIST_MESSAGE);
						existingaddress = true;
						logger.warn("StatusCode:" + StatusCode.ALREADY_EXIST + " Message:" + Messages.RECORD_ALREADY_EXIST_MESSAGE);
						break;
					}
				}
				
				if(!existingaddress){
					addresscode = ResultGeneratorUtil.codeGenerator("", "sq_address_code", "AD22", session);
					addressinfo = new AddressInfo(addressid, addresscode, customercode, completeaddess.toString(),  
							postcode, state, city, area, street, number, building, unit, addresstype, googleaddress);
					session.save(addressinfo);
					
					resultmap.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
					resultmap.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
					logger.info("submitCustomerAddres() ---- StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message:" + Messages.SAVE_RECORDS_SUCCESSFUL);
				}
			}
			HibernateUtil.callCommitClose(session);
			addressArray.put(resultmap);
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			addressArray.put(exceptionobj);
			logger.error("submitCustomerAddres() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return addressArray;
	}
	
	// salesorderdetails - [productcode, box, stocklocation, quantity]
	@Override
	public JSONArray completeSalesOrder(String storecode, String salesordercode, String jobid, String staffcode, 
			List<String> salesorderdetails) throws Exception {
		JSONObject deliveryObj = new JSONObject();
		JSONArray deliveryArray = new JSONArray();
		Session session = sessionFactory.openSession();
		try {
			//include storecode here
			SalesOrderInfo saleorderinfo = factoryentitySalesOrder.getEntity(MainEnum.SALESORDER, salesordercode, session);
					
			// salesorderdetails - [productcode, box, stocklocation, quantity]
			saleorderinfo.setSalesorderdetails(setSalesOrderDetailsBox(salesordercode,
					saleorderinfo.getSalesorderdetails(), salesorderdetails, storecode, staffcode,session));
					
			if (boxondelivery == true) {
				deliveryObj.put("StatusCode", StatusCode.BOX_IS_ON_DELIVERY);
				deliveryObj.put("Message", Messages.BOX_IS_IN_USED_MESSAGE);
				logger.info("StatusCode:" + StatusCode.BOX_IS_ON_DELIVERY + " Message:" + Messages.BOX_IS_IN_USED_MESSAGE);
				
				boxondelivery = false;
			}else{
				saleorderinfo.setStaffCode(staffcode);
				saleorderinfo.setStoreCode(storecode);
				saleorderinfo.setStatus("Processing");
				JobListSO joblist = joblistDao.getProductJobListSO(jobid, "", "DP000005", "Sales Order",  "", session);
				if(joblist!=null) { session.delete(joblist);}
				session.save(saleorderinfo);
				HibernateUtil.callCommitClose(session);
				deliveryObj.put("StatusCode", StatusCode.FOR_DELIVERY_CODE);
				deliveryObj.put("Message", Messages.FOR_DELIVERY_MESSAGE);
				logger.info("StatusCode:" + StatusCode.FOR_DELIVERY_CODE + " Message:" + Messages.FOR_DELIVERY_MESSAGE);
			}
			deliveryArray.put(deliveryObj);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deliveryArray.put(exceptionobj);
			logger.error("completeSalesOrder() --- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return deliveryArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getCurrentCustomerAddress(String customercode) throws Exception{
		
		JSONArray addressarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> addressinfolist = session.createSQLQuery(QueriesString.currentaddressQuery)
					.setString("customercode", HelperUtil.checkNullString(customercode)).list();
			
			if (addressinfolist != null){
				addressarray = ResultGeneratorUtil.populateresults(addressinfolist, MainEnum.ADDRESSINFO);
				logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message:" + "Found Address Code in 5 Minutes");
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				addressarray.put(exceptionobj);
				logger.warn("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			addressarray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return addressarray;
	}

	// salesorderdetails - [productcode, box, stocklocation, quantity, stockcode]
	@SuppressWarnings("unused")
	public List<SalesOrderDetails> setSalesOrderDetailsBox(String salesordercode, List<SalesOrderDetails> salesorderdetailsentity, 
			List<String> salesorderdetailsList, String storecode, String staffcode, Session session) throws Exception{
		
		String tmpproductcode = null;
		String tmpbox = null;
		String tmplocation = null;
		String tmpstockcode = null;
		String tmpquantity = null;
		Integer tmppounit = 0;
		Integer tmpsounit = 0;
		Integer tmpsoretunit = 0;
		Integer tmpporetunit = 0;
		
		//for stock control STORE
		BigDecimal tmppounitscsinit = new BigDecimal(0.00);
		BigDecimal tmppounitscs = new BigDecimal(0.00);
		BigDecimal tmpsounitscs =  new BigDecimal(0.00);
		BigDecimal tmpsoretunitscs =  new BigDecimal(0.00);
		BigDecimal tmpporetunitscs =  new BigDecimal(0.00);
		BigDecimal tmppounitscsfinal =  new BigDecimal(0.00);
		String tmplocationstoreA = null;
		String tmplocationstoreB = null;
		BigDecimal storebaseunitA =  new BigDecimal(0.00);
		BigDecimal storebaseunitB =  new BigDecimal(0.00);
		BigDecimal percentageunitstore =  new BigDecimal(0.00);
		BigDecimal percentageunitstorefinal = new BigDecimal(0.00);
		
		//for stock control INVENTORY
		BigDecimal tmpunitquantity = new BigDecimal(0.00);
		
		BigDecimal tmppounitsciinit = new BigDecimal(0.00);
		BigDecimal tmppounitsci = new BigDecimal(0.00);
		BigDecimal tmpsounitsci = new BigDecimal(0.00);
		BigDecimal tmpsoretunitsci = new BigDecimal(0.00);
		BigDecimal tmpporetunitsci = new BigDecimal(0.00);
		BigDecimal tmppounitscifinal = new BigDecimal(0.00);
		String tmplocationinventory = null;
		BigDecimal inventorybaseunit = new BigDecimal(0.00);
		BigDecimal percentageunitinv = new BigDecimal(0.00);
		BigDecimal percentageunitinvfinal = new BigDecimal(0.00);
		BigDecimal percent =  new BigDecimal(100.00);
		//Percentage status
		Boolean storelevelislow = false;
		
		int i = 0;
		for (String sales:salesorderdetailsList){
			if(sales.contains("&")){
				tmpproductcode = sales.substring(1, sales.length());
				i = 1;
			}else{
				if (i==4){
					tmpstockcode = sales.toString();
					for (SalesOrderDetails listentity: salesorderdetailsentity){
						
						if(listentity.getProductCode().equals(tmpproductcode)){
							//listentity.setBoxCode(tmpbox);
							listentity.setStatus("Processing");
							BigInteger boxdeliveryid = ResultGeneratorUtil.idGenerator("", "sq_boxdelivery_id", session);
							BoxDelivery boxdelivery = new BoxDelivery(boxdeliveryid, salesordercode, tmpproductcode, tmpbox, tmpquantity, "Processing");
							session.save(boxdelivery);
						}
					}
					
					Product product = factoryproductService.getEntity(MainEnum.PRODUCT, tmpproductcode, session);
					List<Inventory> inventory = factoryentityInventory.getEntityList(MainEnum.INVENTORY, salesordercode, tmpproductcode, session);
					if(inventory.size() !=0){
						for(Inventory inv:inventory ){
							inv.setStaffCode(staffcode);
							//tmplocation = inv.getStockLocation();
							tmpstockcode = inv.getStockCode();
							
							session.save(inv);
							List<Inventory> inventorystockcode = factoryentityInventory.getEntityList(MainEnum.INVENTORYSTOCK, tmpproductcode, session);
							
							if(inventorystockcode.size() !=0){
								for (Inventory invstock:inventorystockcode){
									if (!("Finished".equals(invstock.getStatus()))){
										tmppounit = tmppounit + (Integer.parseInt(HelperUtil.checkNullNumbers(invstock.getpOUnit()))/Integer.parseInt(HelperUtil.checkNullNumbers(product.getUnitQuantity())));
										tmpsoretunit = tmpsoretunit + Integer.parseInt(HelperUtil.checkNullNumbers(invstock.getsOReturnUnit()));
										tmpporetunit = tmpporetunit + Integer.parseInt(HelperUtil.checkNullNumbers(invstock.getpOReturnUnit()));
										
										tmpsounit = (tmpsounit + Integer.parseInt(invstock.getsOUnit()) - tmpsoretunit) + tmpporetunit;
									}
								}
								if(tmpsounit >= tmppounit){
									List<Inventory> inventoryupdate = factoryentityInventory.getEntityList(MainEnum.INVENTORYSTOCK, tmpproductcode, session);
									for(Inventory inup : inventoryupdate){
										//inup.setStockLocation(tmplocation);
										//inup.setStockCode(tmpstockcode);
										inup.setStatus("Finished");
										session.save(inup);
									}
								}
							}
							
							//FOR STOCKCONTROL
							List<Inventory> inventorystockcontrol = factoryentityInventory.getEntityList(MainEnum.INVENTORYLOC, tmpproductcode, session);
							if(inventorystockcontrol.size() !=0 ){
								for (Inventory invstockcontrol:inventorystockcontrol){
									if (!("Finished".equals(invstockcontrol.getStatus()))){
										
										tmpunitquantity = new BigDecimal((String) HelperUtil.checkNullNumbers(product.getUnitQuantity()));
										
										if(invstockcontrol.getStockLocation().length() == 4){
											if (tmplocationstoreA == null) { 
												tmplocationstoreA = invstockcontrol.getStockLocation();
											}else{
												if(!tmplocationstoreA.equalsIgnoreCase(invstockcontrol.getStockLocation())){
													tmplocationstoreB = invstockcontrol.getStockLocation();
												}
											}
											
											tmppounitscsinit = new  BigDecimal((String) invstockcontrol.getpOUnit());
											tmppounitscs = tmppounitscs.add(tmppounitscsinit.divide(tmpunitquantity, 2, RoundingMode.HALF_UP));
											tmpsoretunitscs = tmpsoretunitscs.add(new  BigDecimal((String) invstockcontrol.getsOReturnUnit()));
											tmpporetunitscs = tmpporetunitscs.add(new  BigDecimal((String) invstockcontrol.getpOReturnUnit()));
											
											tmpsounitscs = (tmpsounitscs.add(new  BigDecimal((String) invstockcontrol.getsOUnit())).subtract(tmpsoretunitscs)).add(tmpporetunitscs);
										}
										
										if(invstockcontrol.getStockLocation().length() == 9){
											
											tmplocationinventory = invstockcontrol.getStockLocation();
											tmppounitsciinit = new  BigDecimal((String) invstockcontrol.getpOUnit());
											tmppounitsci = tmppounitsci.add(tmppounitsciinit.divide(tmpunitquantity, 2, RoundingMode.HALF_UP));
											tmpsoretunitsci = tmpsoretunitsci.add(new  BigDecimal((String) invstockcontrol.getsOReturnUnit()));
											tmpporetunitsci = tmpporetunitsci.add(new  BigDecimal((String) invstockcontrol.getpOReturnUnit()));
											
											tmpsounitsci = (tmpsounitsci.add(new  BigDecimal((String) invstockcontrol.getsOUnit())).subtract(tmpsoretunitsci)).add(tmpporetunitsci);
										}
									}
								}
							}
							
							tmppounitscsfinal = tmppounitscs.subtract(tmpsounitscs); //store
							tmppounitscifinal = tmppounitsci.subtract(tmpsounitsci); //inventory
							
							//this would be the BASEUNIT
							if(tmppounitscsfinal.compareTo(new BigDecimal(0.00)) == 1){
								if(tmplocationstoreA != null){
									storebaseunitA = (BigDecimal) session.createSQLQuery(QueriesString.checkstockavailableQuery)
											.setString("productcode", HelperUtil.checkNullString(tmpproductcode))
											.setString("stocklocation", HelperUtil.checkNullString(tmplocationstoreA))
											.uniqueResult();
									if(storebaseunitA != null){
										storebaseunitA = storebaseunitA.add(storebaseunitB);
										percentageunitstore = tmppounitscsfinal.divide(storebaseunitA, 2, RoundingMode.HALF_UP);
										percentageunitstorefinal = percentageunitstore.multiply(percent);
										
										if(percentageunitstorefinal.compareTo(new BigDecimal(23.00)) == -1){
											storelevelislow = true;
										}else{
											storelevelislow = false;
										}
									}else{
										storebaseunitA =  new BigDecimal(0.00);
										storebaseunitB =  new BigDecimal(0.00);
									}
								}
								
								if(tmplocationstoreB!= null){
									storebaseunitB = (BigDecimal) session.createSQLQuery(QueriesString.checkstockavailableQuery)
											.setString("productcode", HelperUtil.checkNullString(tmpproductcode))
											.setString("stocklocation", HelperUtil.checkNullString(tmplocationstoreB))
											.uniqueResult();
									if(storebaseunitB !=null){
										storebaseunitA = storebaseunitA.add(storebaseunitB);
										percentageunitstore = tmppounitscsfinal.divide(storebaseunitA, 2, RoundingMode.HALF_UP);
										percentageunitstorefinal = percentageunitstore.multiply(percent);
										
										if(percentageunitstorefinal.compareTo(new BigDecimal(23.00)) == -1){
											storelevelislow = true;
										}else{
											storelevelislow = false;
										}
									}else{
										storebaseunitA =  new BigDecimal(0.00);
										storebaseunitB =  new BigDecimal(0.00);
									}
								}
								
								storebaseunitA = storebaseunitA.add(storebaseunitB);
								percentageunitstore = tmppounitscsfinal.divide(storebaseunitA, 2, RoundingMode.HALF_UP);
								percentageunitstorefinal = percentageunitstore.multiply(percent);
								
								if(percentageunitstorefinal.compareTo(new BigDecimal(23.00)) == -1){
									storelevelislow = true;
								}else{
									storelevelislow = false;
								}
							}else{
								storelevelislow = true;
							}
							
							List<StockControl> stockcontrollist = null;
							if(storelevelislow){
								//check if the Inventory is <= 20% 
								//if not 0  - Compare equal value == 0, Compare Left > right == 1, Left < right == -1
								if(tmppounitscifinal.compareTo(new BigDecimal(0.00)) == 1){
									//get the baseunit (or the last requested unit)
									inventorybaseunit = (BigDecimal) session.createSQLQuery(QueriesString.checkstockavailableQuery)
											.setString("productcode", HelperUtil.checkNullString(tmpproductcode))
											.setString("stocklocation", HelperUtil.checkNullString(tmplocationinventory))
											.uniqueResult();
									//calculate the available stocks
									if(inventorybaseunit != null){
										percentageunitinv = tmppounitscifinal.divide(inventorybaseunit, 2, RoundingMode.HALF_UP);
										percentageunitinvfinal = percentageunitinv.multiply(percent);
									}else{
										percentageunitinv = new BigDecimal(0.00);
										percentageunitinvfinal = new BigDecimal(0.00);
									}
									
									if(percentageunitinvfinal.compareTo(new BigDecimal(23.00)) == -1){
										//check if the product is already requested before making stockcontrol
										System.out.println("Stop 04 " + tmpproductcode + " " +  tmplocationinventory);
										stockcontrollist = factoryentityStockControlService.getEntityList(MainEnum.STOCKCONTROL, tmpproductcode, session);
										if(stockcontrollist.size() == 0){
											List<JobList> joblistrequest = joblistDao.checkProductRequest(tmpproductcode,  session);
											if (joblistrequest.size() == 0){
												joblistDao.createJobList("", tmpproductcode, "DP000003", "Stock Control", "", session);
												logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message:" + Messages.STOCKCONTROL_CREATED_MESSAGE);
											}else{
												logger.warn("StatusCode:" + StatusCode.STOCKCONTROL_EXIST_CODE + " Message:" + Messages.STOCKCONTROL_EXIST_MESSAGE);
											}
										}else{
											logger.warn("StatusCode:" + StatusCode.STOCKCONTROL_EXIST_CODE + " Message:" + Messages.STOCKCONTROL_EXIST_MESSAGE);
										}
									}
								}else{
									stockcontrollist = factoryentityStockControlService.getEntityList(MainEnum.STOCKCONTROL, tmpproductcode, session);
									if(stockcontrollist.size() == 0){
										List<JobList> joblistrequest = joblistDao.checkProductRequest(tmpproductcode,  session);
										if (joblistrequest.size() == 0){
											joblistDao.createJobList("", tmpproductcode, "DP000003", "Stock Control", "", session);
											logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message:" + Messages.STOCKCONTROL_CREATED_MESSAGE);
										}else{
											logger.warn("StatusCode:" + StatusCode.STOCKCONTROL_EXIST_CODE + " Message:" + Messages.STOCKCONTROL_EXIST_MESSAGE);
										}
									}else{
										logger.warn("StatusCode:" + StatusCode.STOCKCONTROL_EXIST_CODE + " Message:" + Messages.STOCKCONTROL_EXIST_MESSAGE);
									}
								}
							}
						}
					}
				}else{
					if (i==0){
						tmpproductcode = sales.toString();
					}else if (i==1){
						//check the box
						tmpbox = sales.toString();
						List<BoxDelivery> boxdelivery = factoryentityboxService.getEntityList(MainEnum.BOXDELIVERYSCAN, tmpbox, session);
						
						if(boxdelivery.size() !=0){
							for(BoxDelivery box:boxdelivery){
								if(box.getBoxCode().equals(tmpbox) && !box.getStatus().equalsIgnoreCase("Delivered") && !box.getStatus().equalsIgnoreCase("Delivered With Returned")
										&& !box.getStatus().equalsIgnoreCase("On Delivery")
										&& !box.getSalesOrderCode().equalsIgnoreCase(salesordercode)){
									boxondelivery = true;
									break;
								}
							}
						}else{
							boxondelivery = false;
						}
					}else if  (i==2){
						tmplocation = sales.toString();
					}else if  (i==3){
						tmpquantity = sales.toString();
					}
				}
				i = i+1;
			}
		}
		return salesorderdetailsentity;
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean deductInStocks(String productcode, String orderquantity, String storecode, String salesordercode, Session session) throws Exception{
		
		BigDecimal orderunit = new BigDecimal(0.00);
		BigDecimal availableunit = new BigDecimal(0.00);
		BigDecimal totalavailableunit = new BigDecimal(0.00);
		BigDecimal submittedunit = new BigDecimal(0.00);
		BigDecimal balanceunit = new BigDecimal(0.00);
		boolean breakit = false;
		
		orderunit = new BigDecimal( (String) orderquantity);
		BigDecimal orderquantityDec = new BigDecimal((String) orderquantity);
		List<String> stockproductlist = session.createSQLQuery("CALL SPAvailableProductUnit(:productcode, :storecode)")
				.setString("productcode", HelperUtil.checkNullString(productcode))
				.setString("storecode", HelperUtil.checkNullString(storecode))
				.list();
		
		if(stockproductlist.size() != 0){
			for (Iterator it = stockproductlist.iterator(); it.hasNext();){
				Object[] stockproductslistRecord = (Object[]) it.next();
				
				//0F.STOCKLOCATION, 1F.PRODUCTCODE, 2F.STOCKCODE, 
				//3AVAILABLESTOCKQTY
				availableunit = new BigDecimal((String) HelperUtil.checkNullNumbers(stockproductslistRecord[3]));
				totalavailableunit = totalavailableunit.add(availableunit);
				
				//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
				submittedunit = new BigDecimal(0.00);
				if(availableunit.compareTo(new BigDecimal(0.00)) == 0){
					withavailablestocks = false;
					break;
				}else if (balanceunit.compareTo(new BigDecimal(0.00)) ==0) {
					if(orderquantityDec.compareTo(availableunit) == 1){
						submittedunit = availableunit;
						balanceunit = orderquantityDec.subtract(availableunit);
					}else{
						submittedunit = orderunit;
						breakit = true;
					}
					
				}else if (balanceunit.compareTo(new BigDecimal(0.00)) == 1){
					if(balanceunit.compareTo(availableunit) == 1){
						submittedunit = availableunit;
						balanceunit = balanceunit.subtract(availableunit);
					}else{
						submittedunit = balanceunit;
						balanceunit = new BigDecimal(0.00);
						breakit = true;
					}
				}
				Product product = factoryproductService.getEntity(MainEnum.PRODUCT,productcode, session);
				BigInteger inventoryid = ResultGeneratorUtil.idGenerator("", "sq_inventory_id", session);
				Inventory inventory = factoryentityInventory.getEntity(MainEnum.INVENTORY, salesordercode, productcode, session);
				if (inventory == null){
					inventory = new Inventory(inventoryid, salesordercode, productcode, storecode, product.getUnitQuantity(), HelperUtil.checkNullString(stockproductslistRecord[0]), 
							submittedunit.toString(), "SO", "SO", "", HelperUtil.checkNullString(stockproductslistRecord[2]), "Items Sold", "");
					session.save(inventory);
				}
				if (breakit){break;}
			}
			
			if(totalavailableunit.compareTo(orderunit) > -1){
				withavailablestocks = true;
			}else{
				withavailablestocks = false;
			}
		}else{
			withavailablestocks = false;
		}
		return withavailablestocks;
	}
	
	//[productcode, price, quantity, gst, discount]
	public List<SalesOrderDetails> setSalesOrderDetails(String salesordercode, String storecode, List<String> salesorderdetailsList, Session session) throws Exception{
		
		List<SalesOrderDetails> salesorderdetailarray = new ArrayList<SalesOrderDetails>();
		SalesOrderDetails salesorderdet = new SalesOrderDetails();
		//BigDecimal totalprice = new BigDecimal(0.00);
		BigDecimal tmpquantity = new BigDecimal(0.00);
		BigDecimal currentpurchaseamount = new BigDecimal(0.00);
		BigDecimal rrprice = new BigDecimal(0.00);
		String tmpproductcode = null;
		int i = 0;
		for (String sales:salesorderdetailsList){
			if(sales.contains("&")){
				tmpproductcode = sales.substring(1, sales.length());
				salesorderdet = new SalesOrderDetails();
				salesorderdet.setSalesOrderCode(salesordercode);
				salesorderdet.setProductCode(tmpproductcode);
				i = 1;
			}else{
				if (i==4){
					
					if(deductInStocks(tmpproductcode, tmpquantity.toString(), storecode, salesordercode, session) == true){ //revertit back
						salesorderdet.setDiscount(sales.toString());
						salesorderdet.setBoxCode("-");
						salesorderdetailarray.add(salesorderdet);
						salesorderdet.setStatus("New Order");
						tmpquantity= new BigDecimal(0.00);
					}else{
						insufficientproductcode = tmpproductcode;
						break;
					}
					
				}else{
					if (i==0){
						tmpproductcode =sales.toString();
						salesorderdet.setSalesOrderCode(salesordercode);
						salesorderdet.setProductCode(tmpproductcode);
					}else if(i==1){
						//salesorderdet.setPrice(sales.toString());
					}else if (i==2){
						tmpquantity = new BigDecimal((String) sales.toString());
						
						Product product = factoryproductService.getEntity(MainEnum.PRODUCT, tmpproductcode, session);
						rrprice = new BigDecimal((String) product.getPackPrice());
						currentpurchaseamount = rrprice.multiply(tmpquantity);
						
						salesorderdet.setQuantity(sales.toString());
						salesorderdet.setPrice(currentpurchaseamount.toString());
					}else if (i==3){
						salesorderdet.setGst(sales.toString());
					}
				}
				i = i+1;
			}
		}
		return salesorderdetailarray; 
	}
	
	@SuppressWarnings("unused")
	public void updateInventory(Integer soldunit, String stocklocation, String stockcode, String productcode, Session session) throws Exception{
		int updaterecordqry = session.createSQLQuery("CALL SPUpdateInventory(:soldunit, :stocklocation, :stockcode, :productcode)")
				.setInteger("soldunit", soldunit)
				.setString("stocklocation", HelperUtil.checkNullString(stocklocation)) 
				.setString("stockcode", HelperUtil.checkNullString(stockcode))
				.setString("productcode", HelperUtil.checkNullString(productcode))
				.executeUpdate();		
	}
	
	public String getGoogleAddress(String address, String storeaddress) throws URISyntaxException, IOException, JSONException{
	
		String origin = storeaddress;
		String destination =storeaddress;
		String customersroutes = address;
	
		String stringUrl = HelperUtil.MAIN_URL + "origin=" + origin + "&destination=" +destination + "&waypoints=optimize:true|" + customersroutes + "&sensor=false";
		StringBuilder response = new StringBuilder();

	    URL url = new URL(stringUrl);
	    URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		url = uri.toURL();
	
	    HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
	    if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
	    {
	    	BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
	        String strLine = null;
	        while ((strLine = input.readLine()) != null)
	        {
	        	response.append(strLine);
	        }
	        input.close();
	    }
	    String jsonOutput = response.toString();
	    JSONObject jsonObject = new JSONObject(jsonOutput);

		// routesArray contains ALL routes
		JSONArray routesArray = jsonObject.getJSONArray("routes");
	
		// Grab the first route
		JSONObject route = routesArray.getJSONObject(0);
	
		// Take all legs from the route
		JSONArray legs = route.getJSONArray("legs");
		//Map<String, String> legsmap = new HashMap<String, String>();
	
		// Grab first leg
		JSONObject leg = legs.getJSONObject(0);
		String googleaddress = leg.getString("end_address");
	
		return googleaddress;
	}
}
