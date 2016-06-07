package com.wom.api.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import com.wom.api.model.BoxDelivery;
import com.wom.api.model.BudgetList;
import com.wom.api.model.Customer;
import com.wom.api.model.Delivery;
import com.wom.api.model.Inventory;
import com.wom.api.model.Product;
import com.wom.api.model.SalesOrderDetails;
import com.wom.api.model.SalesOrderInfo;
import com.wom.api.model.Store;
import com.wom.api.pdf.PDFGenerator;
import com.wom.api.pdf.UploadPurchaseOrder;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class DeliveryDaoImpl implements DeliveryDao{
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(DeliveryDaoImpl.class);
	FactoryEntityService<SalesOrderInfo> factoryentityService = new FactoryEntityServiceImpl<SalesOrderInfo>();
	FactoryEntityService<SalesOrderDetails> factoryentitydetailsService = new FactoryEntityServiceImpl<SalesOrderDetails>();
	FactoryEntityService<BoxDelivery> factoryentityboxService = new FactoryEntityServiceImpl<BoxDelivery>();
	FactoryEntityService<Delivery> factoryentitydeliveryService = new FactoryEntityServiceImpl<Delivery>();
	FactoryEntityService<Product> factoryproductService = new FactoryEntityServiceImpl<Product>();
	FactoryEntityService<Store> factorystoreService = new FactoryEntityServiceImpl<Store>();
	FactoryEntityService<AddressInfo> factoryentityAddService = new FactoryEntityServiceImpl<AddressInfo>();
	FactoryEntityService<Inventory> factoryentityInventory = new FactoryEntityServiceImpl<Inventory>();
	FactoryEntityService<Customer> factoryentityCustomerService = new FactoryEntityServiceImpl<Customer>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getBoxForScanning(String boxcode) throws Exception{
		logger.info("getBoxForScanning");
		
		JSONArray boxforscanningArray = new JSONArray();
		Map<String, String> resultmap = new HashMap<String, String>();
		String salesordercode = null;
		Session session = HibernateUtil.callSession(sessionFactory);
		try {
			List<String> boxforscanninglist = session.createSQLQuery(QueriesString.boxforscanningQuery 
					+ QueriesString.boxforscanningWhere + QueriesString.boxforscanningGroupby)
					.setString("boxcode", HelperUtil.checkNullString(boxcode))
					.list();
			
			//0A.SALESORDERCODE, 1A.BOXCODE,
			//2BOXWEIGHT,
			//3D.PACKMASS, 4CONCAT(E.SIDE, '', C.DELIVERYGROUP) AS DELIVERYRACKING, "
			//5CONCAT(D.BRAND, ' ', D.PRODUCTNAME) AS PRODUCTNAME, 6A.QUANTITY, 7D.CHECKOUTWEIGHT, 
			//8D.PRODUCTCODE, 9D.PHOTOCODE, "
			//10CONCAT(D.UNITQUANTITY, ' X ', D.PACKWEIGHT, ' ', D.PACKMASS) AS PACKCONTENTS "
			
			BigDecimal baseweight = new BigDecimal(1000.00);
			BigDecimal newboxweight = new BigDecimal(0.00);
			String newpackmass = "KG";
			
			if (!(boxforscanninglist.size() == 0)){
				for (Iterator it = boxforscanninglist.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					BigDecimal boxweight = new BigDecimal( (String) HelperUtil.checkNullString(resultListRecord[2]));
					
					salesordercode = HelperUtil.checkNullString(resultListRecord[0]);
					String packmass = HelperUtil.checkNullString(resultListRecord[3]);
					
					if (packmass.equalsIgnoreCase("GRAM") || packmass.equalsIgnoreCase("G") || packmass.equalsIgnoreCase("ML") || packmass.equalsIgnoreCase("MG")){
						newboxweight = boxweight.divide(baseweight);
					}else if (packmass.equalsIgnoreCase("KG") || packmass.equalsIgnoreCase("LITRE") || packmass.equalsIgnoreCase("L")){
						newboxweight = boxweight;
					}else 		if (packmass.equalsIgnoreCase("EACH") || packmass.equalsIgnoreCase("BOXES") || packmass.equalsIgnoreCase("PIECES")
							 || packmass.equalsIgnoreCase("PACK") || packmass.equalsIgnoreCase("TABLETS") || packmass.equalsIgnoreCase("BOX")
							 || packmass.equalsIgnoreCase("ROLL") || packmass.equalsIgnoreCase("DOZEN") || packmass.equalsIgnoreCase("UNIT")){

						newboxweight = new BigDecimal(0.00);
					}
					
					resultmap.put("SalesOrderCode", salesordercode);
					resultmap.put("Box", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("BoxWeight", HelperUtil.checkNullAmount(newboxweight));
					resultmap.put("PackMass", HelperUtil.checkNullString(newpackmass));
					resultmap.put("DeliveryRacking", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("Quantity", HelperUtil.checkNullNumbers(resultListRecord[6]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[8]));
					resultmap.put("PhotoCode", HelperUtil.checkNullString(resultListRecord[9]));
					resultmap.put("PackContents", HelperUtil.checkNullString(resultListRecord[10]));
					boxforscanningArray.put(resultmap);
				}
			}else{
				resultmap.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				resultmap.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
				boxforscanningArray.put(resultmap);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			boxforscanningArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return boxforscanningArray;
	}
	
	/** Add salesordercode as parameter here**/
	@Override
	public JSONArray submitScannedBox(String area, String boxcode, String boxweight, String staffcode, 
			String salesordercode) throws Exception{
		logger.info("Submit Scanned Box");
		
		JSONArray scannedboxarray = new JSONArray();
		JSONObject scannedboxobj = new JSONObject();
		String productstatus = "Scanned";
		String sostatus = "Processing";
		Session session = sessionFactory.openSession();
		try {
			List<BoxDelivery> boxdelivery = factoryentityboxService.getEntityList(MainEnum.BOXDELIVERYSCAN, boxcode, salesordercode, session);
			List<SalesOrderDetails> salesorderdetails = factoryentitydetailsService.getEntityList(MainEnum.SALESORDERDETAILS, salesordercode, session); //use the sales order code instead
			if(boxdelivery.size() != 0){
				for(BoxDelivery bdelivery:boxdelivery){
					//compare the box in the list then set scanned
					//put an else here to check if the status of other items are scanned. inf not.. make a variable stating that it is in progress.
					if(salesorderdetails.size() !=0){
						for(SalesOrderDetails sales : salesorderdetails){
							if(bdelivery.getProductCode().equalsIgnoreCase(sales.getProductCode())){
								sales.setSalesOrderCode(salesordercode);
								sales.setStatus(productstatus);
								break;
							}
						}
					}
					
					bdelivery.setArea(area);
					bdelivery.setBoxWeight(boxweight);
					bdelivery.setScannedBy(staffcode);
					bdelivery.setStatus(productstatus);
					session.update(bdelivery);
				}
				session.flush();
				
				//if the variable is still in progress. dont update the salesorderinfo for scanned status.
				//therefore this will not be included in the list of the delivery. it should be all scanned for a particular salesorder.
				SalesOrderInfo salesorderinfo = factoryentityService.getEntity(MainEnum.SALESORDER, salesordercode, session);
				List<SalesOrderDetails> salesorderdetailsprocessing = factoryentitydetailsService.getEntityList(MainEnum.SALESORDERDETAILSPROCESS, salesordercode, session);
				
				if(salesorderdetailsprocessing.size() != 0){
					salesorderinfo.setStatus(sostatus);
					salesorderinfo.setStaffCode(staffcode);
				}else{
					salesorderinfo.setStatus(productstatus);
					salesorderinfo.setStaffCode(staffcode);
				}
				
				salesorderinfo.setSalesorderdetails(salesorderdetails);
				session.update(salesorderinfo);
				
				HibernateUtil.callCommitClose(session);
				
				scannedboxobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				scannedboxobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message:" + Messages.SAVE_RECORDS_SUCCESSFUL);
				scannedboxarray.put(scannedboxobj);
				
			}else{
				JSONObject exceptionobj = new JSONObject();
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
				scannedboxarray.put(exceptionobj);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			scannedboxarray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
			
		}
		return scannedboxarray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getTruckInfo() throws Exception{
		logger.info("getTruckInfo");
		
		JSONArray truckinfoArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> truckinfolist = session.createSQLQuery(QueriesString.truckQuery)
					.list();
			if(truckinfolist.size() != 0){
				truckinfoArray = ResultGeneratorUtil.populateresults(truckinfolist, MainEnum.TRUCKINFO);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
				truckinfoArray.put(exceptionobj);
			}
		} catch (Exception e) {
			
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			truckinfoArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return truckinfoArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getDeliveryDetails(String truckcode) throws Exception{
		JSONArray deliveryDetailArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> deliveryDetailList = session.createSQLQuery("CALL SPGetDeliveryDetails(:truckcode)")
					.setString("truckcode", HelperUtil.checkNullString(truckcode))
					.list();
			
			if (deliveryDetailList.size() != 0){
				deliveryDetailArray.put(ResultGeneratorUtil.populateresults(deliveryDetailList, MainEnum.DELIVERYDETAILS));
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
				deliveryDetailArray.put(exceptionobj);
			}
		} catch (Exception e) {
			
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deliveryDetailArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return deliveryDetailArray;
	}
	
	//deliverydetails - [salesordercode, area, box, truckrackingcode]
	@Override
	public JSONArray updateDelivery(String truckcode, String staffcode, List<String> deliverydetails) throws Exception {
		logger.info("updateDelivery");
		
		JSONObject deliveryObj = new JSONObject();
		JSONArray deliveryArray = new JSONArray();
		String tmpsalesordercode = null;
		String tmparea = null;
		String tmpbox = null;
		String tmptruckrackingcode = null;
		boolean success = true;
		String deliverycode = null;
		int i = 0;
		Session session = sessionFactory.openSession();
		try {
			for (String deliver:deliverydetails){
				if(deliver.contains("&")){
					tmpsalesordercode = deliver.substring(1, deliver.length());
					i = 1;
				}else{
					if (i==3){
						tmptruckrackingcode = deliver.toString();
						List<BoxDelivery> boxdelivery = factoryentityboxService.getEntityList(MainEnum.BOXDELIVERY, tmparea, tmpbox, tmpsalesordercode, session);
						if(boxdelivery.size() != 0){
							for(BoxDelivery bdelivery:boxdelivery){
								if(!bdelivery.getStatus().equalsIgnoreCase("On Delivery")){
									
									//check if this Sales order has previous delivery code already
									if(deliverycode == null){
										deliverycode = getExistingDeliveryCode(tmpsalesordercode, session);
										if(deliverycode == null || deliverycode.equalsIgnoreCase("") || deliverycode.isEmpty() || deliverycode == ""){
											deliverycode = ResultGeneratorUtil.codeGenerator("", "sq_delivery_code", "DL22", session);
											BigInteger deliveryid = ResultGeneratorUtil.idGenerator("", "sq_delivery_id", session);
											Delivery delivery = new Delivery(deliveryid, truckcode, deliverycode, staffcode);
											session.save(delivery);
										//}else{
										//	deliverycode = "";
										}
									}
									
									bdelivery.setDeliveryCode(deliverycode);
									bdelivery.setTruckRackingCode(tmptruckrackingcode);
									bdelivery.setStatus("On Delivery");
									session.update(bdelivery);
									success = true;
									
								}else{
									deliveryObj.put("StatusCode", StatusCode.BOX_IS_ON_DELIVERY);
									deliveryObj.put("Message", Messages.BOX_ALREADY_TAKEN_MESSAGE);
									logger.info("StatusCode:" + StatusCode.BOX_IS_ON_DELIVERY + " Message:" + Messages.BOX_ALREADY_TAKEN_MESSAGE);
									deliveryArray.put(deliveryObj);
									success = false;
									break;
								}
							}
						}
						SalesOrderInfo salesorderinfo = factoryentityService.getEntity(MainEnum.SALESORDER, tmpsalesordercode, session);
						salesorderinfo.setStatus("On Delivery");
						salesorderinfo.setStaffCode(staffcode);
						session.update(salesorderinfo);
					
					}else{
						if (i==2){
							tmpbox = deliver.toString();
						}else if (i==0){
							tmpsalesordercode = deliver.toString();
						}else{
							tmparea = deliver.toString();
						}
					}
					i = i+1;
				}
			}
			
			if(success){
				HibernateUtil.callCommitClose(session);
				deliveryObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				deliveryObj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message:" + Messages.SAVE_RECORDS_SUCCESSFUL);
				deliveryArray.put(deliveryObj);
			}
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deliveryArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return deliveryArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getDeliveryJob(String staffcode) throws Exception{
		logger.info("getDeliveryJob");
		
		JSONArray deliveryjobArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try{
			List<String> deliveryDetailList = session.createSQLQuery("CALL SPGetDeliveryJob(:staffcode)")
					.setString("staffcode", HelperUtil.checkNullString(staffcode)).list();
			if (deliveryDetailList.size() != 0){
				deliveryjobArray = ResultGeneratorUtil.populateresults(deliveryDetailList, MainEnum.DELIVERYJOBLIST);
				deliveryjobArray.put(getDeliveryRoutes());
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE); 
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				deliveryjobArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deliveryjobArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return deliveryjobArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getDeliveryCustomerOrder(String deliverycode, String salesordercode) throws Exception{
		logger.info("getDeliveryCustomerOrder");
		
		JSONArray deliverycustromerorderArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try{
			List<String> deliverycustromerorderList = session.createSQLQuery("CALL SPGetCustomerDeliveryOrder(:deliverycode, :salesordercode)")
					.setString("deliverycode", HelperUtil.checkNullString(deliverycode))
					.setString("salesordercode", HelperUtil.checkNullString(salesordercode))
					.list();
			if (deliverycustromerorderList.size() != 0){
				deliverycustromerorderArray = ResultGeneratorUtil.populateresults(deliverycustromerorderList, MainEnum.DELIVERYCUSTOMERLIST);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				deliverycustromerorderArray.put(exceptionobj);
				logger.warn("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.ORDER_ALREADY_DELIVERED_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deliverycustromerorderArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return deliverycustromerorderArray;
	}
	
	//deliverycustomerorderList [ProductCode, ReturnQuantity, ReturnPrice]
	@Override
	public JSONArray completeDeliveryCustomerOrder(HttpServletRequest request, HttpServletResponse response, String deliverycode, String staffcode, String customercode, String salesordercode,
			List<String> deliverycustomerorderList) throws Exception {
		
		logger.info("completeDeliveryCustomerOrder");
		
		JSONArray deliverycustomerorderArray = new JSONArray();
		JSONObject deliverycustomerorderObj = new JSONObject();
		List<SalesOrderDetails> salesorderdetailslist = null;
		BoxDelivery boxdelivery = new BoxDelivery();
		String strstatus = null;
		String strboxstatus = "Delivered";
		String strboxstatusfinal = "Delivered";
		String tmpproductcode = null;
		
		BigDecimal tmppurchaseitems = new BigDecimal(0.00);
		BigDecimal tmpreturnitems = new BigDecimal(0.00);
		BigDecimal tmprefundamount = new BigDecimal(0.00);
		BigDecimal tmprefundgst = new BigDecimal(0.00);
		BigDecimal tmprefundgstfinal = new BigDecimal(0.00);
		BigDecimal tmpreturndiscountfinal = new BigDecimal(0.00);
		BigDecimal tmprefundamountfinal = new BigDecimal(0.00);
		
		BigDecimal refundamount = new BigDecimal(0.00);
		
		BigDecimal totalcurrentpurchasenogst = new BigDecimal(0.00);
		BigDecimal totalcurrentpurchaseexgst = new BigDecimal(0.00);
		BigDecimal totalcurrentpurchasegst = new BigDecimal(0.00);
		BigDecimal totalcurrentpurchasefinal = new BigDecimal(0.00);
		
		BigDecimal returnitems = new BigDecimal(0.00);
		BigDecimal returnamount = new BigDecimal(0.00);
		BigDecimal returngst = new BigDecimal(0.00);
		
		BigDecimal discount = new BigDecimal(0.00);
		BigDecimal gsttotal = new BigDecimal(0.00);
		BigDecimal gstamount = new BigDecimal(0.00);
		
		BigDecimal purchaseitems = new BigDecimal(0.00);
		BigDecimal purchaseprice = new BigDecimal(0.00);
		BigDecimal purchasegst = new BigDecimal(0.00);
		
		BigDecimal purchasepricefinal = new BigDecimal(0.00);
		BigDecimal purchasegstfinal = new BigDecimal(0.00);
		String maincontactnumber = null;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		Session session = sessionFactory.openSession();
		try {
			Delivery delivery = factoryentitydeliveryService.getEntity(MainEnum.DELIVERY, deliverycode, "", session);
			if(delivery != null){
				
				SalesOrderInfo salesorderinfo = factoryentityService.getEntity(MainEnum.SALESORDER, salesordercode, session);
				salesorderdetailslist = salesorderinfo.getSalesorderdetails();
				
				int i = 0;
				
				for (String deliveryorder:deliverycustomerorderList){
					if(deliveryorder.contains("&")){
						tmpproductcode = deliveryorder.substring(1, deliveryorder.length());
						i = 1;
					}else{
						if (i==2){
							for (SalesOrderDetails salesorderdetail: salesorderdetailslist){
								if(salesorderdetail.getProductCode().equals(tmpproductcode)){
									
									if(tmpreturnitems.compareTo(new BigDecimal(0.00)) == 1){ 
										
										tmppurchaseitems = new BigDecimal((String) HelperUtil.checkNullNumbers(salesorderdetail.getQuantity()));
										gstamount = new BigDecimal((String) HelperUtil.checkNullAmount(salesorderdetail.getGst()));
										discount = new BigDecimal((String) HelperUtil.checkNullAmount(salesorderdetail.getDiscount()));
										
										if(discount.compareTo(new BigDecimal(0.00)) == 1){
											tmpreturndiscountfinal = discount.divide(tmppurchaseitems, 2, RoundingMode.HALF_UP).multiply(tmpreturnitems);
										}
										
										tmprefundgst = gstamount.divide(tmppurchaseitems, 2, RoundingMode.HALF_UP);
										tmprefundgstfinal = tmprefundgst.multiply(tmpreturnitems);
										 
										tmprefundamount = new BigDecimal((String) HelperUtil.checkNullAmount(salesorderdetail.getPrice()));
										tmprefundamountfinal = tmprefundamount.divide(tmppurchaseitems, 2, RoundingMode.HALF_UP).multiply(tmpreturnitems);
										refundamount = refundamount.add(tmprefundamountfinal).add(tmprefundgstfinal);

										strstatus = "Returned"; 
										strboxstatus = "Delivered";
										
										salesorderdetail.setStatus(strstatus);
										salesorderdetail.setReturnQuantity(tmpreturnitems.toString());
										salesorderdetail.setReturnPrice(tmprefundamountfinal.toString());
										salesorderdetail.setReturnGST(tmprefundgstfinal.toString());
										salesorderdetail.setReturnDiscount(tmpreturndiscountfinal.toString());

										BigInteger inventoryid = ResultGeneratorUtil.idGenerator("", "sq_inventory_id", session);
										Inventory inventory = factoryentityInventory.getEntity(MainEnum.INVENTORYSO, salesordercode, tmpproductcode, session);
										if(inventory !=null){
											Inventory inventoryinput = new Inventory(inventoryid, salesordercode, tmpproductcode, HelperUtil.STORE_CODE, "0", inventory.getStockLocation(), 
													tmpreturnitems.toString(), "RFC", "RFC", staffcode, inventory.getStockCode(), "Items Returned", "");
											session.save(inventoryinput);
										}

									}else{
										strstatus = "Delivered";
										salesorderdetail.setStatus(strstatus);
									}
									
									boxdelivery = factoryentityboxService.getEntity(MainEnum.BOXDELIVERY, deliverycode, tmpproductcode, salesordercode, session);
									boxdelivery.setStatus(strboxstatus);
									
									break;
								}
							}
						}else{
							if (i==0){
								tmpproductcode = deliveryorder.toString();
							}else{
								tmpreturnitems = new BigDecimal((String) HelperUtil.checkNullNumbers(deliveryorder.toString()));
							}
						}
						i = i+1;
					}
				}
				
				session.save(boxdelivery);
				//List<SalesOrderDetails> salesorderdetailscheck = factoryentitydetailsService.getEntityList(MainEnum.SALESORDERDETAILSSCANNED, salesordercode, session);
				//if(salesorderdetailscheck.size() == 0){
				salesorderinfo.setStatus(strboxstatus);
				//}
				
				salesorderinfo.setRefundAmount(refundamount.toString());
				salesorderinfo.setSalesorderdetails(salesorderdetailslist);
				session.save(salesorderinfo);
				BigInteger budgetid = ResultGeneratorUtil.idGenerator("", "sq_budget_id", session);
				//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
				if(refundamount.compareTo(new BigDecimal(0.00)) == 1){
					//insert to budget
					BudgetList budgetlistcc = new BudgetList(budgetid, HelperUtil.STORE_CODE,  "0.00", refundamount.toString(), salesordercode, "-", staffcode, "RFC");
					session.save(budgetlistcc);
				}
				
				//generate a new tax invoice
				for(SalesOrderDetails salesorderdetails:salesorderdetailslist){
					//make a condition here
					
					returnitems = new BigDecimal((String) HelperUtil.checkNullNumbers(salesorderdetails.getReturnQuantity()));
					returnamount = new BigDecimal((String) HelperUtil.checkNullAmount(salesorderdetails.getReturnPrice()));
					returngst = new BigDecimal((String) HelperUtil.checkNullAmount(salesorderdetails.getReturnGST()));
					
					purchaseitems = new BigDecimal((String) HelperUtil.checkNullNumbers(salesorderdetails.getQuantity()));
					purchaseprice = new BigDecimal((String) HelperUtil.checkNullAmount(salesorderdetails.getPrice()));
					purchasegst = new BigDecimal((String) HelperUtil.checkNullAmount(salesorderdetails.getGst()));
					
					//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
					if(purchaseitems.compareTo(returnitems) > -1){				
						purchasepricefinal = purchaseprice.subtract(returnamount);
						purchasegstfinal = purchasegst.subtract(returngst);
					}else{
						purchasepricefinal = returnamount.subtract(purchaseprice);
						purchasegstfinal = returngst.subtract(purchasegst);
					}
					
					if(purchasegstfinal.compareTo(new BigDecimal(0.00)) == 0){ totalcurrentpurchasenogst = totalcurrentpurchasenogst.add(purchasepricefinal);}
					if(purchasegstfinal.compareTo(new BigDecimal(0.00)) == 1){ totalcurrentpurchasegst = totalcurrentpurchasegst.add(purchasepricefinal.add(purchasegstfinal));}
					gsttotal = gsttotal.add(purchasegstfinal);
					totalcurrentpurchasefinal = totalcurrentpurchasefinal.add(purchasepricefinal).add(purchasegstfinal);
					totalcurrentpurchaseexgst = totalcurrentpurchaseexgst.add(purchasepricefinal);
				}
				
				Customer customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMER, customercode, session);
				maincontactnumber = customer.getPhoneNumber();
				
				Store store = factorystoreService.getEntity(MainEnum.STORE, HelperUtil.STORE_CODE, session);
				AddressInfo addressinfo = factoryentityAddService.getEntity(MainEnum.ADDRESSINFO, customercode, salesorderinfo.getAddress(), session);
				
				if(addressinfo != null){
					if (PDFGenerator.generateTaxInvoicePDF(request, salesordercode, store.getGst(), customercode,
							addressinfo.getAddressInfo(), salesorderinfo.getSalesDate(), salesorderdetailslist, totalcurrentpurchasefinal, gsttotal, 
							totalcurrentpurchasenogst, totalcurrentpurchaseexgst, totalcurrentpurchasegst, "TAX INVOICE", maincontactnumber, session)==true){
							
						if(UploadPurchaseOrder.uploadtoS3(salesordercode, HelperUtil.SO_IMAGE_LOCATION + salesordercode + ".pdf", HelperUtil.SO_BUCKET_NAME) == true){
							session.flush();
							
							Delivery deliveryfinal = factoryentitydeliveryService.getEntity(MainEnum.DELIVERY, deliverycode, "", session);
							if(deliveryfinal != null){
								List<BoxDelivery> boxdeliverylist = factoryentityboxService.getEntityList(MainEnum.BOXDELIVERY, deliveryfinal.getDeliveryCode(), session);
								if(boxdeliverylist.size() != 0){
									for(BoxDelivery deliverylist : boxdeliverylist){
										if(deliverylist.getStatus().equalsIgnoreCase("On Delivery")){
											strboxstatusfinal = "On Delivery";
											break;
										}
									}
								}
								deliveryfinal.setDeliveredBy(HelperUtil.checkNullString(staffcode));
								deliveryfinal.setDeliveredDate(currdatenow);
								deliveryfinal.setStatus(HelperUtil.checkNullString(strboxstatusfinal));
								session.update(deliveryfinal);
								
								HibernateUtil.callCommitClose(session);
								
								deliverycustomerorderObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
								deliverycustomerorderObj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
								deliverycustomerorderArray.put(deliverycustomerorderObj);
								logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message:" + Messages.SAVE_RECORDS_SUCCESSFUL);
							}else{
								deliverycustomerorderObj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
								deliverycustomerorderObj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
								deliverycustomerorderArray.put(deliverycustomerorderObj);
								logger.warn("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
							}
						}else{
							
							deliverycustomerorderObj.put("StatusCode", StatusCode.UPLOADING_ERROR_CODE);
							deliverycustomerorderObj.put("Message", Messages.UPLOADING_ERROR_MESSAGE);
							deliverycustomerorderArray.put(deliverycustomerorderObj);
							logger.warn("StatusCode:" + StatusCode.UPLOADING_ERROR_CODE + " Message:" + Messages.UPLOADING_ERROR_MESSAGE);
						}
					}
				}else{
					deliverycustomerorderObj.put("StatusCode", StatusCode.ADDRESS_NOT_EXIST_CODE);
					deliverycustomerorderObj.put("Message", Messages.ADDRESS_NOT_EXIST_MESSAGE);
					deliverycustomerorderArray.put(deliverycustomerorderObj);
					logger.warn("StatusCode:" + StatusCode.ADDRESS_NOT_EXIST_CODE + " Message:" + Messages.ADDRESS_NOT_EXIST_MESSAGE);
				}

			}else{
				deliverycustomerorderObj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				deliverycustomerorderObj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				deliverycustomerorderArray.put(deliverycustomerorderObj);
				logger.warn("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deliverycustomerorderArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return deliverycustomerorderArray;
	}
	
	@Override
	public JSONArray checkPostCode(String postCode) throws Exception{
		logger.info("checkPostCode");
		
		JSONArray checkpostcodeArray = new JSONArray();
		JSONObject checkpostcodeobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			Query query = session.createSQLQuery(QueriesString.checkpostcodeQuery 
					+ QueriesString.checkpostcodeWhere + QueriesString.checkpostcodeGroupby)
					.setString("postcode", HelperUtil.checkNullString(postCode));
			if (query.uniqueResult()!=null){
				checkpostcodeobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				checkpostcodeobj.put("Message", Messages.RECORD_FOUND_MESSAGE);
			}else{
				checkpostcodeobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				checkpostcodeobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
			}
			checkpostcodeArray.put(checkpostcodeobj);
		
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			checkpostcodeArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return checkpostcodeArray;
		
	}
	
	public String getExistingDeliveryCode(String salesordercode, Session session){
		String deliverycode = null;
		
		deliverycode = (String) session.createSQLQuery("CALL SPGetExistingDeliveryCode(:salesordercode)")
				.setString("salesordercode", HelperUtil.checkNullString(salesordercode))
				.uniqueResult();
		
		return deliverycode;
		
	}
	
	public JSONArray getDeliveryRoutes() throws Exception{
		
		logger.info("getDeliveryRoutes");
		
		String origin = HelperUtil.getOfficeaddress();
		String destination = HelperUtil.getOfficeaddress();
		String customersroutes = HelperUtil.getDeliveryaddress();
		
		String stringUrl = HelperUtil.MAIN_URL + "origin=" + origin + "&destination=" +destination + "&waypoints=optimize:true" + customersroutes + "&sensor=false";
		StringBuilder response = new StringBuilder();
		JSONArray deliveryroutesArray = new JSONArray();

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
		Map<String, String> legsmap = new HashMap<String, String>();
		
		// Grab first leg
		for (int i = 0; i<legs.length(); i++){
			JSONObject leg = legs.getJSONObject(i);
			
			JSONObject durationObject = leg.getJSONObject("duration");
			legsmap.put("duration", durationObject.getString("text"));
			
			JSONObject distanceObject = leg.getJSONObject("distance");
			legsmap.put("distance", distanceObject.getString("text"));
			
			legsmap.put("end_address", leg.getString("end_address"));
			deliveryroutesArray.put(legsmap);
		}
		return deliveryroutesArray;
	}
}
