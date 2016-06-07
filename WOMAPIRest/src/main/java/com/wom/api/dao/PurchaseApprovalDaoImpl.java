package com.wom.api.dao;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
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
import com.wom.api.model.ItemBudgetPlanning;
import com.wom.api.model.JobList;
import com.wom.api.model.PurchaseApproval;
import com.wom.api.model.PurchaseApproved;
import com.wom.api.model.PurchaseOrder;
import com.wom.api.model.PurchaseOrderProducts;
import com.wom.api.model.PurchaseRequest;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class PurchaseApprovalDaoImpl implements PurchaseApprovalDao{
	
	static final Logger logger = Logger.getLogger(PurchaseApprovalDaoImpl.class);
	FactoryEntityService<ItemBudgetPlanning> factoryentityService = new FactoryEntityServiceImpl<ItemBudgetPlanning>();
	FactoryEntityService<PurchaseOrder> factoryentitypoService = new FactoryEntityServiceImpl<PurchaseOrder>();
	FactoryEntityService<PurchaseApproved> factoryentitylistService = new FactoryEntityServiceImpl<PurchaseApproved>();
	FactoryEntityService<PurchaseApproval> factoryentitylistServicePA = new FactoryEntityServiceImpl<PurchaseApproval>();
	FactoryEntityService<PurchaseRequest> factoryentityServicePR = new FactoryEntityServiceImpl<PurchaseRequest>();
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	JobListDao joblistDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getPurchaseApproval(String jobId, String productCode, String staffcode) throws Exception{
		
		HibernateUtil.updateJobList(jobId, "Purchase Approval", "Purchase Approval", staffcode, "DP000009", sessionFactory);
		JSONArray purchaseapprovalArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> purchasefundingList = session.createSQLQuery("CALL SPGetPurchaseApproval(:jobid, :productcode)")
					.setString("jobid", HelperUtil.checkNullString(jobId))
					.setString("productcode", HelperUtil.checkNullString(productCode)).list();
			if (purchasefundingList.size() != 0){
				purchaseapprovalArray = ResultGeneratorUtil.populateresults(purchasefundingList, MainEnum.PURCHASEAPPROVAL);
				purchaseapprovalArray.put(getApprovers(HelperUtil.getPurchaseapprovalcode(), session));
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				purchaseapprovalArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			purchaseapprovalArray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return purchaseapprovalArray;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getApprovers(String purchaseapprovalcode, Session session) throws Exception{
		JSONArray approversArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		List<String> approversList = session.createSQLQuery(QueriesString.approversQuery +
				QueriesString.approversWhere)
				.setString("purchaseapprovalcode", HelperUtil.checkNullString(purchaseapprovalcode)).list();
		if (approversList.size() != 0){
			approversArray = ResultGeneratorUtil.populateresults(approversList, MainEnum.APPROVERS);
		}else{
			exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
			exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
			approversArray.put(exceptionobj);
		}
		return approversArray;
	}
	
	@Override
	public JSONArray approvePurchaseApproval(String purchaseapprovalcode, String jobId, String productCode, String suppliercode, String staffCode, 
			String requestquantity, String requestunit, String totalamount, String storeCode, String balanceBudget) throws Exception{
		JSONArray purchaseapprovalarray = new JSONArray();
		JSONObject purchaseapprovalobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<PurchaseApproved> approvers = factoryentitylistService.getEntityList(MainEnum.PURCHASEAPPROVED, purchaseapprovalcode, session);
			PurchaseApproval purcahseapproval = factoryentitylistServicePA.getEntity(MainEnum.PURCHASEAPPROVAL, purchaseapprovalcode, session);
			if(!(approvers.size()==0)){
				for(PurchaseApproved purchaseapprove:approvers){
					if(purchaseapprove.getApprovedBy().equals(staffCode)){
						purchaseapprove.setStatus("Approved");
						purcahseapproval.setCurrentBudget(balanceBudget);
						session.update(purchaseapprove);
						session.update(purcahseapproval);
						
						if(approvers.size()==1){
							PurchaseOrder purchaseorder = factoryentitypoService.getEntity(MainEnum.PURCHASEORDER, suppliercode, session);
							
							String purchaseordercode = null;
							if (purchaseorder==null){
								String joblistid = joblistDao.createJobList("", "", "DP000003", "Approved Purchase Order", suppliercode, session);
								purchaseordercode = ResultGeneratorUtil.codeGenerator("", "sq_purchase_order", "PO22", session);
								BigInteger purchaseorderid = ResultGeneratorUtil.idGenerator("", "sq_purchaseorder_id", session);
								
								purchaseorder = new PurchaseOrder(purchaseorderid, purchaseordercode, suppliercode, joblistid, storeCode, staffCode); 
								session.save(purchaseorder);
							}else{
								purchaseordercode = purchaseorder.getPurchaseOrderCode();
							}
							
							/** Get the requester from Item Budget **/
							
							ItemBudgetPlanning itembudget = factoryentityService.getEntity(MainEnum.ITEMBUDGETPLANNING, jobId, session);
							if(itembudget != null){
								PurchaseRequest purchaserequest = factoryentityServicePR.getEntity(MainEnum.PURCHASEREQUEST, itembudget.getItemBudgetCode(), session);
								purchaserequest.setPurchaseOrderCode(purchaseordercode);
								session.update(purchaserequest);
							
								//itembudget.getStaffCode()
								BigInteger purchaseorderproductsid = ResultGeneratorUtil.idGenerator("", "sq_purchaseorder_products_id", session);
								PurchaseOrderProducts purchaseorderproducts = new PurchaseOrderProducts(purchaseorderproductsid, purchaseorder, purchaseordercode, 
										productCode, "", requestquantity, requestunit, totalamount, "0", purchaseapprovalcode);
								purchaseorder.getPurchaseOrderProducts().add(purchaseorderproducts);
								
								JobList joblist = joblistDao.getProductJobList(jobId, productCode, "DP000009", "Purchase Approval",  "", session);
								if(joblist != null){session.delete(joblist);}
								
								session.save(purchaseorderproducts);
								
								HibernateUtil.callCommitClose(session);
								HibernateUtil.updateField("tblpurchaseapproval", "Status", "Approved", "PURCHASEAPPROVALCODE", 
										purchaseapprovalcode, sessionFactory);
							}else{
								purchaseapprovalobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
								purchaseapprovalobj.put("Message", Messages.NO_RECORD_TO_APPROVE_MESSAGE);
								purchaseapprovalarray.put(purchaseapprovalobj);
								logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
							}
						}else{
							HibernateUtil.callCommitClose(session);
						}
					}
				}
				purchaseapprovalobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				purchaseapprovalobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				purchaseapprovalarray.put(purchaseapprovalobj);
			}else{
				purchaseapprovalobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				purchaseapprovalobj.put("Message", Messages.NO_RECORD_TO_APPROVE_MESSAGE);
				purchaseapprovalarray.put(purchaseapprovalobj);
				logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			purchaseapprovalarray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + "Error Message:" + e.getMessage());
		}
		return purchaseapprovalarray;
	}
	
	@SuppressWarnings("unused")
	@Override
	public JSONArray denyPurchaseApproval(String purchaseapprovalcode, String jobid) throws Exception{
		JSONObject denypurchaseapprovalobj = new JSONObject();
		JSONArray denypurchaseapprovalArray = new JSONArray();
		Session session = sessionFactory.openSession();
		try {
			int updaterecordqry = session.createSQLQuery(QueriesString.deletepurchaseapproval)
					.setString("purchaseapprovalcode", HelperUtil.checkNullString(purchaseapprovalcode))
					.executeUpdate();		
			HibernateUtil.callCommitClose(session);
			//HibernateUtil.updateJobList(jobid, "Purchase Approval", "Stock Control", "", "DP000003", sessionFactory);
			denypurchaseapprovalobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			denypurchaseapprovalobj.put("Message", Messages.ROLLBACK_RECORDS_SUCCESSFUL);
			denypurchaseapprovalArray.put(denypurchaseapprovalobj);
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			denypurchaseapprovalArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + "Error Message:" + e.getMessage());
		}
		return denypurchaseapprovalArray;
	}
}
