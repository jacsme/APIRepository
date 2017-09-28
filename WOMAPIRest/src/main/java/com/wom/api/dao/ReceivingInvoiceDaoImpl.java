package com.wom.api.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.email.EmailMainUtil;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.AccountTransaction;
import com.wom.api.model.Inventory;
import com.wom.api.model.JobList;
import com.wom.api.model.Product;
import com.wom.api.model.PurchaseOrder;
import com.wom.api.model.PurchaseOrderProducts;
import com.wom.api.model.SalesOrderDetails;
import com.wom.api.model.SalesOrderInfo;
import com.wom.api.model.StockControl;
import com.wom.api.model.StockProducts;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class ReceivingInvoiceDaoImpl implements ReceivingInvoiceDao{

	FactoryEntityService<StockProducts> factoryentityService = new FactoryEntityServiceImpl<StockProducts>();
	FactoryEntityService<PurchaseOrder> factoryentitypoService = new FactoryEntityServiceImpl<PurchaseOrder>();
	FactoryEntityService<PurchaseOrderProducts> factoryentitypoproductsService = new FactoryEntityServiceImpl<PurchaseOrderProducts>();
	FactoryEntityService<SalesOrderInfo> factoryentitySalesOrder = new FactoryEntityServiceImpl<SalesOrderInfo>();
	FactoryEntityService<Product> factoryentityProduct = new FactoryEntityServiceImpl<Product>();
	FactoryEntityService<Inventory> factoryentityInventory = new FactoryEntityServiceImpl<Inventory>();
	FactoryEntityService<StockControl> factoryentityStockControlService = new FactoryEntityServiceImpl<StockControl>();
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	JobListDao joblistDao;
	
	static final Logger logger = Logger.getLogger(ReceivingInvoiceDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getReceivingInvoice(String suppliercode, String jobid, String staffcode) throws Exception{
		JSONArray receivinginvoicearray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			if(HibernateUtil.updateJobList(jobid, "Receiving Invoice", "Receiving Invoice", staffcode, "DP000001", sessionFactory)==true){
				List<String> receivinginvoicelist = session.createSQLQuery("CALL SPGetReceivingInvoice(:suppliercode,:jobid)")
						.setString("suppliercode", HelperUtil.checkNullString(suppliercode))
						.setString("jobid", HelperUtil.checkNullString(jobid))
						.list();
				if (receivinginvoicelist.size() != 0){
					receivinginvoicearray = ResultGeneratorUtil.populateresults(receivinginvoicelist, MainEnum.RECEIVINGINVOICE);
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					receivinginvoicearray.put(exceptionobj);
				}
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				receivinginvoicearray.put(takenobj);
				logger.info("StatusCode:" + StatusCode.ALREADY_EXIST + " Message:" + Messages.JOB_ALREADY_IN_USE_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			receivinginvoicearray.put(exceptionobj);
			logger.error("getReceivingInvoice() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return receivinginvoicearray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getReceivingDeliveryOrder(String suppliercode, String jobid, String staffcode) throws Exception{
		JSONArray receivinginvoicearray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			if(HibernateUtil.updateJobList(jobid, "Receiving Delivery Order", "Receiving Delivery Order", staffcode, "DP000004", sessionFactory)==true){
				List<String> receivinginvoicelist = session.createSQLQuery("CALL SPGetReceivingDeliveryOrder(:suppliercode,:jobid)")
						.setString("suppliercode", HelperUtil.checkNullString(suppliercode))
						.setString("jobid", HelperUtil.checkNullString(jobid))
						.list();
				if (receivinginvoicelist.size() != 0){
					receivinginvoicearray = ResultGeneratorUtil.populateresults(receivinginvoicelist, MainEnum.RECEIVINGDELIVERYORDER);
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					receivinginvoicearray.put(exceptionobj);
				}
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				receivinginvoicearray.put(takenobj);
				logger.info("StatusCode:" + StatusCode.ALREADY_EXIST + " Error Message:" + Messages.JOB_ALREADY_IN_USE_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			receivinginvoicearray.put(exceptionobj);
			logger.error("getReceivingDeliveryOrder() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			
		}finally{
			HibernateUtil.callClose(session);
		}
		return receivinginvoicearray;
	}
	
	//String productcode,  String purchasequantity, String purchaseunit, String purchasetotalamount, String purchasetotalamountwgst
	@SuppressWarnings("unused")
	@Override
	public JSONArray importToAccount(String jobid, String storecode, String rinvoicecode, String purchaseordercode,
			String suppliercode, String purchasedate, String staffcode, String invoicenumber, 
			String duedate, String subtotalamount, String totalgst, String maintotal) throws Exception{
		
		JSONArray accountarray = new JSONArray();
		JSONObject accountobj = new JSONObject();
		String purchasedatestr = null;
		String duedatestr = null;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		Session session = sessionFactory.openSession();
		try {
			if (purchasedate==null){
				purchasedatestr = currdatenow;
			}else{
				purchasedatestr = HelperUtil.parsejsondate("1",purchasedate);
			}
			duedatestr = HelperUtil.parsejsondate("1",duedate);
			BigInteger accounttransactionid = ResultGeneratorUtil.idGenerator("", "sq_account_transaction_id", session);
			
			AccountTransaction accounttransactionobj = new AccountTransaction(accounttransactionid,
					storecode, rinvoicecode, purchaseordercode,
					suppliercode, purchasedatestr,
					subtotalamount, totalgst, maintotal, staffcode, duedatestr, invoicenumber);
			session.save(accounttransactionobj);
				
			JobList joblist = joblistDao.getProductJobList(jobid, "", "DP000001", "Receiving Invoice",  suppliercode, session);
			if(joblist != null) { 
				session.delete(joblist); 
			}
			PurchaseOrder purchaseorder = factoryentitypoService.getEntity(MainEnum.RECEIVINGINVOICE, purchaseordercode, session);
			for(PurchaseOrderProducts poproducts : purchaseorder.getPurchaseOrderProducts()){
				int updaterecordqry = session.createSQLQuery(QueriesString.deleteprocessproduct)
						.setString("purchaseapprovalcode", poproducts.getPurchaseApprovalCode())
						.executeUpdate();	
			}
			HibernateUtil.callCommitClose(session);
			HibernateUtil.updateField("tblreceivinginvoice", "Imported", "YES", "INVOICECODE", rinvoicecode, sessionFactory);
			HibernateUtil.updateField("tblreceivinginvoice", "InvoiceNumber", invoicenumber, "INVOICECODE", 
					rinvoicecode, sessionFactory);
			
			accountobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			accountobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			accountarray.put(accountobj);
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("importToAccount() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			accountarray.put(exceptionobj);
		}
		return accountarray;
	}
	
	/**
	 * 
	 * @param invoicecode
	 * @param purchaseordercode
	 * @param jobid
	 * @param staffcode
	 * @param storeCode
	 * @param deliveryorderlist ( purchaseordercode + productcode + actualorderqty ) this is the value of the List
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public JSONArray receiveDeliveryOrder(HttpServletRequest request, HttpServletResponse response, String invoicecode, String purchaseordercode, String jobid, String staffcode, String suppliercode, List<String> deliveryorderlist) throws Exception{
		
		JSONArray deliveryorderarray = new JSONArray();
		JSONObject deliveryorderobj = new JSONObject();
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		Session session = sessionFactory.openSession();
		try {
			PurchaseOrder purchaseorder = factoryentitypoService.getEntity(MainEnum.RECEIVINGINVOICE, purchaseordercode, session);
			purchaseorder.setDateReceived(currdatenow);
			purchaseorder.setPurchaseOrderProducts(setDeliveryOrder(
					purchaseorder.getPurchaseOrderProducts(), deliveryorderlist, session));
			session.save(purchaseorder);
			
			List<PurchaseOrderProducts> checkpurchaseorderproducts = session.createSQLQuery("CALL SPGetPurchaseOrderProducts(:purchaseordercode)")
					.setString("purchaseordercode", purchaseordercode)
					.list();	
			if(checkpurchaseorderproducts.size() == 0 || checkpurchaseorderproducts == null){
				List<PurchaseOrderProducts> checkpurchaseorderproductsrejected = session.createSQLQuery("CALL SPGetPurchaseOrderProductsDel(:purchaseordercode)")
						.setString("purchaseordercode", purchaseordercode)
						.list();	
				if(checkpurchaseorderproductsrejected.size() != 0 || checkpurchaseorderproductsrejected != null){
					HibernateUtil.callCommitClose(session);
					//email the supplier for the rejects
					EmailMainUtil.generateandemail(request, response, purchaseordercode, suppliercode, staffcode, "DEL", sessionFactory);
				}else{
					HibernateUtil.callCommitClose(session);
				}
				HibernateUtil.updateField("tblreceivinginvoice", "Status", "Received", "INVOICECODE", invoicecode, sessionFactory);
				HibernateUtil.updateJobList(jobid, "Receiving Delivery Order", "Receiving Invoice", "", "DP000001", sessionFactory);
			}else{
				HibernateUtil.callCommitClose(session);
				HibernateUtil.updateJobList(jobid, "Receiving Delivery Order", "Receiving Delivery Order", "", "DP000004", sessionFactory);
				HibernateUtil.updateField("tblreceivinginvoice", "StaffCode", staffcode, "INVOICECODE", invoicecode, sessionFactory);
			}
			deliveryorderobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			deliveryorderobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			deliveryorderarray.put(deliveryorderobj);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			deliveryorderarray.put(exceptionobj);
			logger.error("receiveDeliveryOrder() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return deliveryorderarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Override
	public JSONArray getStockingProduct() throws Exception{
		
		JSONArray stockingproductarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		String joblistcode = null;
		String productcode = null;
		
		Map<String, String> resultmap = new HashMap<String, String>();
		Session session = sessionFactory.openSession();
		try {
			List<String> stockproductslist = session.createSQLQuery("CALL SPStockingProduct()")
					.list();
			if (stockproductslist.size() != 0 && stockproductslist != null){
				//0I.SOURCECODE, 1I.PRODUCTCODE, 2I.PRODUCTNAME,
				//3I.REQUESTUNIT, 4I.ACTUALORDERQTY, 5I.STORECODE, 
				//6I.PRODUCTSOURCE, 7I.TRANSDATE, 8I.PERC, 
				//9I.STOCKLOCATION AS STORELOCATION, 10N.JOBID
				//11N.INVENTORYLEVEL
				//12N.PHOTOCODE, 13N.BRAND, 14N.PACKCONTENTS
				
				for (Iterator it = stockproductslist.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					
					BigDecimal percentage = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[8]));
					//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
					if ("GR".equalsIgnoreCase(HelperUtil.checkNullString(resultListRecord[6]))){
						
						resultmap.put("SourceCode", HelperUtil.checkNullString(resultListRecord[0]));
						resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[1]));
						resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[2]));
						resultmap.put("PurchaseUnit", HelperUtil.checkNullNumbers(resultListRecord[3]));
						resultmap.put("ActualOrderQty", HelperUtil.checkNullNumbers(resultListRecord[4]));
						resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[5]));
						resultmap.put("ProductSource", HelperUtil.checkNullString(resultListRecord[6]));
						resultmap.put("Percentage", HelperUtil.checkNullAmount(resultListRecord[8]) + "%");
						resultmap.put("StoreLocation", HelperUtil.checkNullString(resultListRecord[9]));
						resultmap.put("JobId", HelperUtil.checkNullString(resultListRecord[10]));
						resultmap.put("InventoryLevel", HelperUtil.checkNullNumbers(resultListRecord[11]));
						resultmap.put("PhotoCode", HelperUtil.checkNullString(resultListRecord[12]));
						resultmap.put("Brand", HelperUtil.checkNullString(resultListRecord[13]));
						resultmap.put("PackContents", HelperUtil.checkNullString(resultListRecord[14]));
						
						stockingproductarray.put(resultmap);
					//}else if("STI".equalsIgnoreCase(HelperUtil.checkNullString(resultListRecord[6]))){
					//	if(percentage.compareTo(new BigDecimal(21.0)) == -1){
					//		if(!"0".equalsIgnoreCase(HelperUtil.checkNullNumbers(resultListRecord[4]))){
								
								//resultmap.put("SourceCode", HelperUtil.checkNullString(resultListRecord[0]));
								//resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[1]));
								//resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[2]));
								//resultmap.put("PurchaseUnit", HelperUtil.checkNullNumbers(resultListRecord[3]));
								//resultmap.put("ActualOrderQty", HelperUtil.checkNullNumbers(resultListRecord[4]));
								//resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[5]));
								//resultmap.put("ProductSource", HelperUtil.checkNullString(resultListRecord[6]));
								//resultmap.put("Percentage", HelperUtil.checkNullAmount(resultListRecord[8]) + "%");
								//resultmap.put("StoreLocation", HelperUtil.checkNullString(resultListRecord[9]));
								
								//JobList joblist = new JobList();
								//REVERT IT BACK WHEN STI FUNCTION IS READY
								/*List<JobList> productjob = (List<JobList>) session.createCriteria(JobList.class)
										.add(Restrictions.eq("productCode", HelperUtil.checkNullString(resultListRecord[1])))
										.add(Restrictions.ne("supplierCode", "RFC"))
										.add(Restrictions.ne("supplierCode", "RTS"))
										.list();
								if(productjob.size() == 0){
									joblistcode = joblistDao.createJobList("", HelperUtil.checkNullString(resultListRecord[1]), "DP000003", "Stock Control", "", session);
									List<Inventory> inventorylist = factoryentityInventory.getEntityList(MainEnum.INVENTORYJOB, HelperUtil.checkNullString(resultListRecord[1]), HelperUtil.checkNullString(resultListRecord[9]), session);
									if(inventorylist.size() != 0){
										for(Inventory inv:inventorylist){
											inv.setJobId(joblistcode);
											session.save(inv);
										}
									}
									HibernateUtil.callCommitClose(session);
									//resultmap.put("JobId", joblistcode);
								}*/
								
								//joblist = joblistDao.getStackingProductJobList(HelperUtil.checkNullString(resultListRecord[1]), "DP000004", "Stacking Product", HelperUtil.checkNullString(resultListRecord[6]), session);
								
								//if(joblist == null) {
								//joblistcode = joblistDao.createJobList("", HelperUtil.checkNullString(resultListRecord[1]), "DP000004", "Stacking Product", HelperUtil.checkNullString(resultListRecord[6]), session);
										
										//List<Inventory> inventorylist = factoryentityInventory.getEntityList(MainEnum.INVENTORYJOB, HelperUtil.checkNullString(resultListRecord[1]), HelperUtil.checkNullString(resultListRecord[9]), session);
										//if(inventorylist.size() != 0){
										//	for(Inventory inv:inventorylist){
										//		inv.setJobId(joblistcode);
										//		session.save(inv);
										//	}
										//}
										//resultmap.put("JobId", joblistcode);
									//}
								//}else{
									//resultmap.put("JobId", joblist.getJobId());
								//}
								
								//resultmap.put("InventoryLevel", HelperUtil.checkNullNumbers(resultListRecord[11]));
								//resultmap.put("PhotoCode", HelperUtil.checkNullString(resultListRecord[12]));
								//resultmap.put("Brand", HelperUtil.checkNullString(resultListRecord[13]));
								//resultmap.put("PackContents", HelperUtil.checkNullString(resultListRecord[14]));
								//stockingproductarray.put(resultmap);
						//	}
					//	}
					/*}else if("RFC".equalsIgnoreCase(HelperUtil.checkNullString(resultListRecord[6]))){
						if(!"0".equalsIgnoreCase(HelperUtil.checkNullNumbers(resultListRecord[4]))){
							resultmap.put("SourceCode", HelperUtil.checkNullString(resultListRecord[0]));
							resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[1]));
							resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[2]));
							resultmap.put("PurchaseUnit", HelperUtil.checkNullNumbers(resultListRecord[3]));
							resultmap.put("ActualOrderQty", HelperUtil.checkNullNumbers(resultListRecord[4]));
							resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[5]));
							resultmap.put("ProductSource", HelperUtil.checkNullString(resultListRecord[6]));
							resultmap.put("Percentage", HelperUtil.checkNullAmount(resultListRecord[8]) + "%");
							resultmap.put("StoreLocation", HelperUtil.checkNullString(resultListRecord[9]));
							
							JobList joblist = new JobList();
							joblist = joblistDao.getStackingProductJobList(HelperUtil.checkNullString(resultListRecord[1]), "DP000004", "Stacking Product", HelperUtil.checkNullString(resultListRecord[6]), session);
							
							if(joblist == null) {
								joblistcode = joblistDao.createJobList("", HelperUtil.checkNullString(resultListRecord[1]), "DP000004", "Stacking Product", HelperUtil.checkNullString(resultListRecord[6]), session);
								List<Inventory> inventorylist = factoryentityInventory.getEntityList(MainEnum.INVENTORYJOB, HelperUtil.checkNullString(resultListRecord[1]), HelperUtil.checkNullString(resultListRecord[9]), session);
								if(inventorylist.size() != 0){
									for(Inventory inv:inventorylist){
										inv.setJobId(joblistcode);
										session.save(inv);
									}
								}
								resultmap.put("JobId", joblistcode);
								HibernateUtil.callCommitClose(session);
							}else{
								resultmap.put("JobId", joblist.getJobId());
							}
							resultmap.put("InventoryLevel", HelperUtil.checkNullNumbers(resultListRecord[11]));
							resultmap.put("PhotoCode", HelperUtil.checkNullString(resultListRecord[12]));
							resultmap.put("Brand", HelperUtil.checkNullString(resultListRecord[13]));
							resultmap.put("PackContents", HelperUtil.checkNullString(resultListRecord[14]));
							stockingproductarray.put(resultmap);
						}*/
					}
				}
				
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				stockingproductarray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			stockingproductarray.put(exceptionobj);
			logger.error("getStockingProduct() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return stockingproductarray;
	}
	
	//Make the return to vendor POR Purchase Order Return
	//ProductSource GR, STI, RFC, SPW
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray submitScannedProduct(String sourcecode, String productcode, String stocklocation, 
			String productsource, String submittedunit, String staffcode, String storecode, String storelocation, String jobid) throws Exception{
		
		logger.info("Product Source" + productsource + "Source Code "+sourcecode + "Product Code "+productcode + "Stock Location " +stocklocation);
		
		JSONArray scannedproductarray = new JSONArray();
		JSONObject scannedproductobj = new JSONObject();
		
		JSONObject exceptionobj = new JSONObject();
		
		Integer submittoinventorydb = 0;
		Integer submittoinventoryparam = 0;
		Integer actualforsubmittoinventorydb = 0;
		Integer balanceforsubmittoinventorydb = 0;
		
		BigDecimal bdinventorydb = new BigDecimal(0.00);
		BigDecimal bdinventoryparam = new BigDecimal(0.00);
		BigDecimal finalsubmitunit = new BigDecimal(0.00);
		BigDecimal balanceunit = new BigDecimal(0.00);
		BigDecimal baseunit = new BigDecimal(0.00);
		BigDecimal totalstoreunit = new BigDecimal(0.00);
		BigDecimal totalbaseunit = new BigDecimal(0.00);
		BigDecimal percentageunit = new BigDecimal(0.00);
		
		Inventory inventory = new Inventory();
		Inventory inventoryinsert = new Inventory();
		String stockcode = null;
		String oldstockcode = null;
		boolean breakit = false;
		boolean canallocate = true;
		int i4=1;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		List<PurchaseOrderProducts> purchaseorderproductslist = null;
		Session session = sessionFactory.openSession();
		String trimstocklocation = stocklocation.trim();
		
		try {
			JobList joblist = new JobList();
			Product product = factoryentityProduct.getEntity(MainEnum.PRODUCT, productcode, session);
			if("GR".equalsIgnoreCase(productsource)){
				List<Inventory> inventorylocation = factoryentityInventory.getEntityList(MainEnum.INVENTORYLOC, productcode, session); 
				
				if(inventorylocation.size() == 0 ) {
					canallocate = true;
				}else{
					for(Inventory inv:inventorylocation){
						if(inv.getStockLocation().equalsIgnoreCase(trimstocklocation)){
							canallocate =true;
							oldstockcode = inv.getStockCode();
							break;
						}else if (inv.getStockLocation().length() == 4 && trimstocklocation.length() == 4){
							if(i4 == 2){
								canallocate = false;
								break;
							}
							i4 = i4 + 1;
						}else if (inv.getStockLocation().length() > 9 && trimstocklocation.length() > 9 ){
							canallocate = false;
							break;
						}
					}
				}
				
				if (canallocate) {
					PurchaseOrder purchaseorder = factoryentitypoService.getEntity(MainEnum.RECEIVINGINVOICE, sourcecode, session);
					purchaseorderproductslist = purchaseorder.getPurchaseOrderProducts();
					for (PurchaseOrderProducts purchaseorderproductsrec:purchaseorderproductslist){
						if(purchaseorderproductsrec.getPurchaseOrderCode().equals(sourcecode) && purchaseorderproductsrec.getProductCode().equals(productcode)){
							//if there is old value add it.
													
							submittoinventorydb = Integer.parseInt(HelperUtil.checkNullNumbers(purchaseorderproductsrec.getSubmittedToInventory()));
							submittoinventoryparam = Integer.parseInt(submittedunit);
							if(submittoinventorydb != 0){
								submittoinventoryparam = submittoinventoryparam + submittoinventorydb;
							}
							
							purchaseorderproductsrec.setSubmittedToInventory(submittoinventoryparam.toString());
							purchaseorderproductsrec.setStaffCode(staffcode);
							purchaseorderproductsrec.setScanned("YES");
							purchaseorderproductsrec.setScannedDate(currdatenow);
							actualforsubmittoinventorydb = (Integer.parseInt(HelperUtil.checkNullNumbers(purchaseorderproductsrec.getRequestUnit()))/Integer.parseInt(HelperUtil.checkNullNumbers(purchaseorderproductsrec.getRequestQuantity()))) * Integer.parseInt(HelperUtil.checkNullNumbers(purchaseorderproductsrec.getActualOrderQty()));
							
							balanceforsubmittoinventorydb = actualforsubmittoinventorydb - submittoinventoryparam;
						}
					}
					
					if(oldstockcode == null){
						stockcode = ResultGeneratorUtil.codeGenerator("", "sq_stock_code", "SC22", session);
					}else{
						stockcode = oldstockcode;
					}
					
					BigInteger inventoryid = ResultGeneratorUtil.idGenerator("", "sq_inventory_id", session);
					inventory = new Inventory(inventoryid, sourcecode, productcode, storecode, product.getUnitQuantity(), stocklocation, 
							submittedunit, productsource, productsource, staffcode, stockcode, "Items on Hand", "");
					
					purchaseorder.setPurchaseOrderProducts(purchaseorderproductslist);
					logger.info("Balance Unit:" + balanceforsubmittoinventorydb);
					
					//if(balanceforsubmittoinventorydb.equals(0)){
					joblist = joblistDao.getProductJobList(jobid, productcode, "DP000004", "Stacking Product",  "GR", session);
					if (joblist != null) { session.delete(joblist); }
					//}
					
					List<StockControl> stockcontrol = factoryentityStockControlService.getEntityList(MainEnum.STOCKCONTROL, productcode, session);
					if(stockcontrol.size() != 0){
						for(StockControl stockcont: stockcontrol) {
							stockcont.setStatus("Completed");
							session.save(stockcont);
						}
					}
					
					session.update(purchaseorder);
					session.save(inventory);
					
					HibernateUtil.callCommitClose(session);
					//if(!balanceforsubmittoinventorydb.equals(0)){
					//	HibernateUtil.updateJobList(jobid, "Stacking Product", "Stacking Product", "", "DP000004", sessionFactory);
					//}
					
					scannedproductobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
					scannedproductobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
					scannedproductarray.put(scannedproductobj);
				}else{
					exceptionobj.put("StatusCode", StatusCode.WRONG_STOCKLOCATION_CODE);
					exceptionobj.put("Error Message", Messages.WRONG_STOCKLOCATION_MESSAGE);
					scannedproductarray.put(exceptionobj);
					logger.info("StatusCode:" + StatusCode.WRONG_STOCKLOCATION_CODE + " Message:" + Messages.WRONG_STOCKLOCATION_MESSAGE);
			
				}
				
			}else if("RFC".equalsIgnoreCase(productsource)){
				
				SalesOrderInfo salesorderinfo = factoryentitySalesOrder.getEntity(MainEnum.SALESORDER, sourcecode, session);
				for(SalesOrderDetails salesinfo:salesorderinfo.getSalesorderdetails()){
					if(productcode.equalsIgnoreCase(salesinfo.getProductCode())){
						salesinfo.setStatus("Returned to Inventory");
						break;
					}
				}
				
				salesorderinfo.setSalesorderdetails(salesorderinfo.getSalesorderdetails());
				inventory = factoryentityInventory.getEntity(MainEnum.INVENTORY, sourcecode, productcode, session);
				if(inventory != null){
					BigInteger inventoryid = ResultGeneratorUtil.idGenerator("", "sq_inventory_id", session);
					inventoryinsert = new Inventory(inventoryid, sourcecode, productcode, storecode, product.getUnitQuantity(), stocklocation, 
						submittedunit, "GR", "GR", staffcode, inventory.getStockCode(), "Items on Hand", "Returned from Customer"); 
						session.save(inventoryinsert);
				}
				
				joblist = joblistDao.getProductJobList(jobid, productcode, "DP000004", "Stacking Product",  "RFC", session);
				if (joblist != null) {
					session.delete(joblist);
				}
				session.update(salesorderinfo);
				HibernateUtil.callCommitClose(session);
				
				scannedproductobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				scannedproductobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				scannedproductarray.put(scannedproductobj);
				
			}else if("STI".equalsIgnoreCase(productsource)){
				//change the status th Transferred from Store

				bdinventorydb = new BigDecimal(0.00);
				bdinventoryparam = new BigDecimal((String) submittedunit);
							
				//0F.STOCKCODE, 1F.PRODUCTCODE, 2B.PRODUCTNAME, 
				//3REQUESTUNIT, 4ACTUALORDERQTY, 5F.STORECODE,
	            //6'STI' AS PRODUCTSOURCE, 
	            //7TRANSDATE, 8F.STOCKLOCATION
				
				List<Inventory> storeunitlist = session.createSQLQuery(QueriesString.checkstoreunitQuery)
						 .setString("productcode", HelperUtil.checkNullString(productcode))
						 .setString("storelocation", HelperUtil.checkNullString(storelocation))
						 .list();
				
				if(storeunitlist.size() != 0){
					
					for (Iterator it = storeunitlist.iterator(); it.hasNext();){
						Object[] resultListRecord = (Object[]) it.next();
						
						baseunit = new BigDecimal((String) HelperUtil.checkNullNumbers(resultListRecord[3]));
						bdinventorydb = new BigDecimal((String) HelperUtil.checkNullNumbers(resultListRecord[4]));
						totalstoreunit = totalstoreunit.add(bdinventorydb);
						totalbaseunit = totalbaseunit.add(baseunit);
						
						//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
						if((bdinventorydb.compareTo(bdinventoryparam) == 1) && (balanceunit.compareTo(new BigDecimal(0.00)) == 0)){
							finalsubmitunit = bdinventoryparam;
							breakit = true;
						
						}else if((bdinventorydb.compareTo(bdinventoryparam) == -1) && (balanceunit.compareTo(new BigDecimal(0.00)) == 0)){
							finalsubmitunit = bdinventorydb;
							balanceunit = bdinventoryparam.subtract(bdinventorydb);
							
							Inventory inventorylist = factoryentityInventory.getEntity(MainEnum.INVENTORYSTOCK, HelperUtil.checkNullNumbers(resultListRecord[0]), HelperUtil.checkNullNumbers(resultListRecord[1]), session);
							if(inventorylist != null){
								inventorylist.setStockCode(inventorylist.getStockCode());
								inventorylist.setProductCode(inventorylist.getProductCode());
								inventorylist.setStatus("Finished");
								session.save(inventorylist);
							}
							breakit = false;
						
						}else if (balanceunit.compareTo(bdinventorydb) == 1) {
							finalsubmitunit = bdinventorydb;
							balanceunit = balanceunit.subtract(bdinventorydb);
							Inventory inventorylist2 = factoryentityInventory.getEntity(MainEnum.INVENTORYSTOCK, HelperUtil.checkNullNumbers(resultListRecord[0]), HelperUtil.checkNullNumbers(resultListRecord[1]), session);
							if(inventorylist2 != null){
								inventorylist2.setStockCode(inventorylist2.getStockCode());
								inventorylist2.setProductCode(inventorylist2.getProductCode());
								inventorylist2.setStatus("Finished");
								session.save(inventorylist2);
							}
							breakit = false;
						}else{
							finalsubmitunit = balanceunit;
							balanceunit = new BigDecimal(0.00);
							breakit = true;
						}
						BigInteger inventoryid = ResultGeneratorUtil.idGenerator("", "sq_inventory_id", session);
						
						inventory = new Inventory(inventoryid, "-", productcode, storecode, "0", stocklocation, 
								finalsubmitunit.toString(), "STI", "STI", staffcode, HelperUtil.checkNullString(resultListRecord[0]), "Items on Hand", ""); 
						session.save(inventory);
						if (breakit){break;}
					}
					
					totalstoreunit = totalstoreunit.subtract(bdinventoryparam);
					
					percentageunit = totalstoreunit.divide(totalbaseunit).multiply(new BigDecimal(100.00));
					if(percentageunit.compareTo(new BigDecimal(23.00)) == -1){
						//check if the product is already requested before making stockcontrol
						StockControl stockcontrol = factoryentityStockControlService.getEntity(MainEnum.STOCKCONTROL, productcode, session);
						if(stockcontrol == null){
							joblistDao.createJobList("", productcode, "DP000003", "Stock Control", "", session);
							Inventory inventorylisttag = factoryentityInventory.getEntity(MainEnum.INVENTORYTAG, productcode, jobid, session);
							if (inventorylisttag != null){
								inventorylisttag.setRequested("YES");
								session.save(inventorylisttag);
							}
						}
					}
					joblist = joblistDao.getProductJobList(jobid, productcode, "DP000004", "Stacking Product",  "STI", session);
					if (joblist != null) {
						session.delete(joblist);
					}
				}
				
				HibernateUtil.callCommitClose(session);
				
				scannedproductobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				scannedproductobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				scannedproductarray.put(scannedproductobj);
			}
			
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			scannedproductarray.put(exceptionobj);
			logger.error("submitScannedProduct() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return scannedproductarray;
	}
	
	public List<PurchaseOrderProducts> setDeliveryOrder(List<PurchaseOrderProducts> stockproductsentity, List<String> deliveryorderList, Session session) throws Exception{
		String tmppurchaseordercode = null;
		String tmpproductcode = null;
		BigDecimal tmpdeliveryorder = new BigDecimal(0.00);
		BigDecimal actualorderqty = new BigDecimal(0.00);
		BigDecimal tmpactualorderqty = new BigDecimal(0.00);
		
		int i = 0;
		for (String deliveryorder:deliveryorderList){
			if(deliveryorder.contains("&")){
				tmppurchaseordercode = deliveryorder.substring(1, deliveryorder.length());
				i = 1;
			}else{
				if (i==2){
					tmpdeliveryorder = new BigDecimal((String) deliveryorder.toString());
					for (PurchaseOrderProducts listentity: stockproductsentity){
						if(listentity.getPurchaseOrderCode().equals(tmppurchaseordercode) && listentity.getProductCode().equals(tmpproductcode)){
							
							actualorderqty = new BigDecimal((String) listentity.getActualOrderQty());
							tmpactualorderqty = tmpdeliveryorder;
							
							BigDecimal originalRequest = new BigDecimal((String) listentity.getRequestQuantity());
							
							if(actualorderqty.compareTo(new BigDecimal(0.00)) == 0){
								if(originalRequest.compareTo(tmpactualorderqty) == 0){
									listentity.setFullItem("YES");
									listentity.setActualOrderQty(tmpactualorderqty.toString());
									String joblist = joblistDao.createJobList("", tmpproductcode, "DP000004", "Stacking Product", "GR", session);
									listentity.setJobId(joblist);
								}else if(tmpdeliveryorder.compareTo(new BigDecimal(0.00)) == 0) {
									listentity.setFullItem("DEL");
									listentity.setActualOrderQty("0");
								}else{
									listentity.setFullItem("NO");
									listentity.setActualOrderQty(tmpactualorderqty.toString());
									String joblist = joblistDao.createJobList("", tmpproductcode, "DP000004", "Stacking Product", "GR", session);
									listentity.setJobId(joblist);
								}
							}
						}
					}
				}else{
					if (i==0){
						tmppurchaseordercode = deliveryorder.toString();
					}else{
						tmpproductcode = deliveryorder.toString();
					}
				}
				i = i+1;
			}
		}
		return stockproductsentity;
	}
	
	@Override
	public JSONArray updatestockingproductjob(String productsource, String jobid, String productcode, 
			String staffcode) throws Exception{
		
		JSONArray stackingproductarray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
			
		try {
			if(HibernateUtil.updateJobList(jobid, "Stacking Product", "Stacking Product", staffcode, "DP000004", sessionFactory)==true){
				takenobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				takenobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				stackingproductarray.put(takenobj);
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				stackingproductarray.put(takenobj);
				logger.info("StatusCode:" + StatusCode.ALREADY_EXIST + " Message:" + Messages.JOB_ALREADY_IN_USE_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			stackingproductarray.put(exceptionobj);
			logger.error("updatestockingproductjob() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return stackingproductarray;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public JSONArray getGRSummary(String jobid, String productcode, String staffcode) throws Exception{
		
		Map<String, String> resultmap = new HashMap<String, String>();
		
		JSONArray grsummaryarray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			if(HibernateUtil.updateJobList(jobid, "Stacking Product", "Stacking Product", staffcode, "DP000004", sessionFactory)==true){
				List<String> grsummarylist = session.createSQLQuery("CALL SPGetGRSummary(:productcode)")
						 .setString("productcode", HelperUtil.checkNullString(productcode)).list();
				
				if(grsummarylist.size() !=0){
					for (Iterator itgr = grsummarylist.iterator(); itgr.hasNext();){
						Object[] grsummarylistresult = (Object[]) itgr.next();
						resultmap.put("StoreLocation", HelperUtil.checkNullString(grsummarylistresult[1]));
						resultmap.put("StoreAvailableUnit", HelperUtil.checkNullNumbers(grsummarylistresult[3]));
						resultmap.put("InventoryLocation", HelperUtil.checkNullString(grsummarylistresult[4]));
						resultmap.put("InventoryAvailableUnit", HelperUtil.checkNullNumbers(grsummarylistresult[5]));
						grsummaryarray.put(resultmap);
					}
					
				}else{
					resultmap.put("StoreLocation", "-");
					resultmap.put("StoreAvailableUnit", "0");
					resultmap.put("InventoryLocation", "-");
					resultmap.put("InventoryAvailableUnit", "0");
					grsummaryarray.put(resultmap);
				}
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				grsummaryarray.put(takenobj);
				logger.info(" StatusCode:" + StatusCode.ALREADY_EXIST + " Error Message:" + Messages.JOB_ALREADY_IN_USE_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			grsummaryarray.put(exceptionobj);
			logger.error("getGRSummary() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			
		}finally{
			HibernateUtil.callClose(session);
		}
		return grsummaryarray;
	}
}
