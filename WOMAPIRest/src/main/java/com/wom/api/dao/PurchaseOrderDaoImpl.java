package com.wom.api.dao;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.StatusCode;
import com.wom.api.crypt.DecryptionUtil;
import com.wom.api.email.EmailMainUtil;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.ItemBudgetPlanning;
import com.wom.api.model.JobList;
import com.wom.api.model.LoginUser;
import com.wom.api.model.Product;
import com.wom.api.model.ProductSupplier;
import com.wom.api.model.PurchaseOrder;
import com.wom.api.model.PurchaseRequest;
import com.wom.api.model.ReceivingInvoice;
import com.wom.api.model.StockControl;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class PurchaseOrderDaoImpl implements PurchaseOrderDao{
	
	FactoryEntityService<PurchaseOrder> factoryentitypoService = new FactoryEntityServiceImpl<PurchaseOrder>();
	FactoryEntityService<PurchaseRequest> factoryentityprService = new FactoryEntityServiceImpl<PurchaseRequest>();
	FactoryEntityService<ProductSupplier> factoryentitypsService = new FactoryEntityServiceImpl<ProductSupplier>();
	
	static final Logger logger = Logger.getLogger(PurchaseOrderDaoImpl.class);
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	JobListDao joblistDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getApprovedPurchaseOrder(String jobid, String suppliercode, String staffcode) throws Exception{
		JSONArray purchaseorderArray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			if(HibernateUtil.updateJobList(jobid, "Approved Purchase Order", "Approved Purchase Order",staffcode, "DP000003", sessionFactory)==true){
				List<String> purchaseorderList = session.createSQLQuery("CALL SPGetApprovedPurchaseOrder(:suppliercode,:jobid)")
						.setString("suppliercode", HelperUtil.checkNullString(suppliercode))
						.setString("jobid", HelperUtil.checkNullString(jobid))
						.list();
				if (purchaseorderList.size() != 0){
					purchaseorderArray = ResultGeneratorUtil.populateresults(purchaseorderList, MainEnum.PURCHASEORDER);
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					purchaseorderArray.put(exceptionobj);
				}
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				purchaseorderArray.put(takenobj);
				logger.info("StatusCode:" + StatusCode.ALREADY_EXIST + " Error Message:" + Messages.JOB_ALREADY_IN_USE_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			purchaseorderArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
			
		}finally{
			HibernateUtil.callClose(session);
		}
		return purchaseorderArray;
	}

	@Override
	public JSONArray emailPurchaseOrder(HttpServletRequest request, HttpServletResponse response, String storecode, 
			String purchaseordercode, String jobid, String suppliercode, String staffcode) throws JSONException{
		
		JSONArray emailpurchaseorderarray = new JSONArray();
		JSONObject emailpurchaseorderobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			/** PDF document generator here **/
			if(!jobid.equalsIgnoreCase("700")){
				if(EmailMainUtil.generateandemail(request, response, purchaseordercode, suppliercode, staffcode, "", sessionFactory) == true){
					
					
					PurchaseOrder purchaseorder = factoryentitypoService.getEntity(MainEnum.PURCHASEORDER, purchaseordercode, suppliercode, session);
					purchaseorder.setStatus("Emailed");
					purchaseorder.setStaffCode(staffcode);
					purchaseorder.setStoreCode(storecode);
					session.update(purchaseorder);
					
					String invoicecode = ResultGeneratorUtil.codeGenerator("", "sq_receiving_invoice", "RI22", session);
					BigInteger receivinginvoiceid = ResultGeneratorUtil.idGenerator("", "sq_receiving_invoice_id", session);
					
					ReceivingInvoice receivinginvoice = new ReceivingInvoice(receivinginvoiceid, invoicecode, purchaseordercode, jobid, "", "");
					session.save(receivinginvoice);
					
					HibernateUtil.callCommitClose(session);
					HibernateUtil.updateJobList(jobid, "Approved Purchase Order", "Receiving Delivery Order", "", "DP000004", sessionFactory);
					
					emailpurchaseorderobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
					emailpurchaseorderobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
					emailpurchaseorderarray.put(emailpurchaseorderobj);
				}else{
					emailpurchaseorderobj.put("StatusCode", StatusCode.EMAIL_ERROR_CODE);
					emailpurchaseorderobj.put("Message", Messages.EMAIL_ERROR_MESSAGE);
					emailpurchaseorderarray.put(emailpurchaseorderobj);
					logger.info("StatusCode:" + StatusCode.EMAIL_ERROR_CODE + " Message:" + Messages.EMAIL_ERROR_MESSAGE);
				}
			}else{
				emailpurchaseorderobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				emailpurchaseorderobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				emailpurchaseorderarray.put(emailpurchaseorderobj);
				logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
			}
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			emailpurchaseorderarray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return emailpurchaseorderarray;
	}
	
	@Override
	public JSONArray submitNewPO(String storecode, String productcode, String stockunit, String staffcode, String uom) throws Exception {
		
		JSONArray setstockcontrolArray = new JSONArray();
		JSONObject setstockcontrolObj = new JSONObject();
		FactoryEntityService<Product> factoryentityProducts = new FactoryEntityServiceImpl<Product>();
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		boolean canrequest = false;
		Session session = sessionFactory.openSession();
		try {
			List<JobList> checkProduct = joblistDao.checkProductRequest(productcode, session);
			if(checkProduct.size() != 0){
				for(JobList jobproduct:checkProduct){
					//check the other supplier
					List<ProductSupplier> productsupplier = factoryentitypsService.getEntityList(MainEnum.PRODUCTSUPPLIER, productcode, session);
					if(productsupplier.size() > 1){
						canrequest = true;
					}else{
						if("Receiving Invoice".equalsIgnoreCase(jobproduct.getJobtype())){
							canrequest = true;
						}else if("STI".equalsIgnoreCase(jobproduct.getSupplierCode())){
							canrequest = true;
						}else if("RFC".equalsIgnoreCase(jobproduct.getSupplierCode())){
							canrequest = true;
						}else if("RTS".equalsIgnoreCase(jobproduct.getSupplierCode())){
							canrequest = true;
						}else if("GR".equalsIgnoreCase(jobproduct.getSupplierCode())){
							canrequest = true;
						}else{
							canrequest = false;
						}
					}
				}
			}else{
				canrequest = true;
			}
			
			if(canrequest){
				Product product = factoryentityProducts.getEntity(MainEnum.PRODUCT, productcode, session);
				if(product != null){
					product.setUom(uom);
					String jobid = joblistDao.createJobList("", product.getProductCode(), "DP000003", "Item Budget Planning", "", session);
					String stockcontrolcode = ResultGeneratorUtil.codeGenerator("", "sq_stock_control", "SC22", session);
					String itembudgetcode = ResultGeneratorUtil.codeGenerator("", "sq_item_budget", "IB22", session);
					
					BigInteger stockcontrolid = ResultGeneratorUtil.idGenerator("", "sq_stock_control_id", session);
					StockControl stockcontrol  = new StockControl(stockcontrolid, stockcontrolcode, jobid, product.getProductCode(),  
							storecode, stockunit, staffcode);
					
					BigInteger itembudgetid = ResultGeneratorUtil.idGenerator("", "sq_item_budget_id", session);
					ItemBudgetPlanning itembudgetplanning = new ItemBudgetPlanning(itembudgetid, itembudgetcode, stockcontrolcode,
							jobid, product.getProductCode(), "0.00", staffcode, currdatenow);
					
					session.save(product);
					session.save(stockcontrol);
					session.save(itembudgetplanning);
					
					HibernateUtil.callCommitClose(session);
					setstockcontrolObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
					setstockcontrolObj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				}else{
					setstockcontrolObj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					setstockcontrolObj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				}
			}else{
				setstockcontrolObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				setstockcontrolObj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			}
			
			setstockcontrolArray.put(setstockcontrolObj);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			setstockcontrolArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		
		return setstockcontrolArray;
	}
	
	@Override
	public JSONArray emailPOLogin(String staffcode, String password) throws Exception {
		JSONArray loginarray = new JSONArray();
		JSONObject loginobj = new JSONObject();
		LoginUser loginuser = null;
		FactoryEntityService<LoginUser> factoryentityService = new FactoryEntityServiceImpl<LoginUser>();
		Session session = sessionFactory.openSession();
		try {
			loginuser = factoryentityService.getEntity(MainEnum.LOGINMAN, staffcode, session);
			if (loginuser==null){
				loginobj.put("StatusCode", StatusCode.LOGIN_PASSWORD_ERROR_CODE);
				loginobj.put("Message", Messages.LOGIN_USER_ERROR);
				loginarray.put(loginobj);
			}else{
				if(DecryptionUtil.decrypt(loginuser.getPassword()).equals(password)){
					loginobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
					loginobj.put("Message", Messages.LOGIN_SUCCESSFUL_MESSAGE);
					logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.LOGIN_SUCCESSFUL_MESSAGE);
					loginarray.put(loginobj);
				}else{
					loginobj.put("StatusCode", StatusCode.LOGIN_PASSWORD_ERROR_CODE);
					loginobj.put("Message", Messages.LOGIN_PASSWORD_ERROR);
					logger.info("StatusCode " + StatusCode.LOGIN_PASSWORD_ERROR_CODE + " Message " + Messages.LOGIN_PASSWORD_ERROR);
					loginarray.put(loginobj);
				}
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
			loginarray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return loginarray;
	}
}
