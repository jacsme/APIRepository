package com.wom.api.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
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
import com.wom.api.model.PurchaseApproval;
import com.wom.api.model.PurchaseApproved;
import com.wom.api.model.RoleAssign;
import com.wom.api.model.Store;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class PurchaseFundingDaoImpl implements PurchaseFundingDao{
	
	FactoryEntityService<Store> factoryentityService = new FactoryEntityServiceImpl<Store>();
	FactoryEntityService<RoleAssign> factoryentitylistService = new FactoryEntityServiceImpl<RoleAssign>();
	FactoryEntityService<PurchaseApproval> factoryPurchaseApproval = new FactoryEntityServiceImpl<PurchaseApproval>();
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(PurchaseFundingDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getPurchaseFunding(String jobId, String productCode, String storecode, String staffcode) throws Exception{
		JSONArray purchasefundingArray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			if (HibernateUtil.updateJobList(jobId, "Purchase Funding Planning", "Purchase Funding Planning", staffcode, "DP000002", sessionFactory)==true){
				List<String> purchasefundingList = session.createSQLQuery(QueriesString.purchasefundingQuery 
						+ QueriesString.purchasefundingWhere + QueriesString.purchasefundingGroupby)
						.setString("jobid", HelperUtil.checkNullString(jobId))
						.setString("productcode", HelperUtil.checkNullString(productCode))
						.list();
				if (purchasefundingList.size() != 0){
					purchasefundingArray = ResultGeneratorUtil.populateresults(purchasefundingList, MainEnum.PURCHASEFUNDING);
					purchasefundingArray.put(getPurchaseFundingHistory(storecode, productCode, session));
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					purchasefundingArray.put(exceptionobj);
					logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
				}
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				purchasefundingArray.put(takenobj);
				logger.info("StatusCode:" + StatusCode.ALREADY_EXIST + " Message:" + Messages.JOB_ALREADY_IN_USE_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			purchasefundingArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return purchasefundingArray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray getPurchaseFundingHistory(String storecode, String productcode, Session session) throws Exception{
		Map<String, String> resultmap = new HashMap<String, String>();
		JSONArray purchasefundinghistoryArray = new JSONArray();
		
		List<String> purchasefundinghistoryList = session.createSQLQuery("CALL SPPurchaseFundingHistory(:storecode)")
				.setString("storecode", HelperUtil.checkNullString(storecode)).list();
		
		BigDecimal newBudget = new BigDecimal(0.00);
		BigDecimal originalBudget = new BigDecimal(0.00);
		BigDecimal currentBudget = new BigDecimal(0.00);
		BigDecimal prevcurrBudget = new BigDecimal(0.00);
		BigDecimal prevorigBudget = new BigDecimal(0.00);
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullDateZone(dateTimeKL);
		
		if(purchasefundinghistoryList.size() !=0){
			for (Iterator it = purchasefundinghistoryList.iterator(); it.hasNext();){
				Object[] resultListRecord = (Object[]) it.next();
				
				String budgetdatetoday = HelperUtil.checkNullDateZone(resultListRecord[0]);
		        
				newBudget = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[3])).setScale(2, BigDecimal.ROUND_HALF_UP);
				currentBudget = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[2])).setScale(2, BigDecimal.ROUND_HALF_UP);
				originalBudget = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[1])).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				if(currdatenow.equals(budgetdatetoday)){
		        	prevcurrBudget = newBudget;
					prevorigBudget = newBudget;
		        }else{			
					if(currentBudget.compareTo(new BigDecimal(0.00)) == 1){
						prevcurrBudget = currentBudget;
						prevorigBudget = originalBudget;
					}
		        }	
				resultmap.put("OriginalBudget", prevorigBudget.toString());
				resultmap.put("CurrentBudget", prevcurrBudget.toString());
				resultmap.put("BudgetDate", HelperUtil.checkNullDate(resultListRecord[0]));
				purchasefundinghistoryArray.put(resultmap);
			}
		}else{
			resultmap.put("OriginalBudget", "0.00");
			resultmap.put("CurrentBudget", "0.00");
			resultmap.put("BudgetDate", currdatenow);
			purchasefundinghistoryArray.put(resultmap);
		}
			
		return purchasefundinghistoryArray;
	}
	
	@Override
	public JSONArray submitPurchaseFunding(String purchaseFundingCode, String jobId, String productCode, 
			String originalbudget, String budgetBalance, String staffCode, String purchasedate, String storecode) throws Exception{
		JSONArray productquotationarray = new JSONArray();
		JSONObject productquotationobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			String purchaseAppovalCode = ResultGeneratorUtil.codeGenerator("", "sq_purchase_approval", "PA", session);
			BigInteger purchasapprovalid = ResultGeneratorUtil.idGenerator("", "sq_purchase_approval_id", session);
			
			PurchaseApproval purchaseapproval = new PurchaseApproval(purchasapprovalid, purchaseAppovalCode, purchaseFundingCode, jobId, originalbudget, budgetBalance, storecode);
			
			List<PurchaseApproved> purchaseapprovedlist = new ArrayList<PurchaseApproved>();
			List<RoleAssign> roleassignlist = factoryentitylistService.getEntityList(MainEnum.ROLEASSIGN, "", session);
			if(roleassignlist.size() !=0) {
				for(RoleAssign roleassign:roleassignlist){
					
					PurchaseApproved purchaseapproved = new PurchaseApproved();
					purchaseapproved.setPurchaseApprovalCode(purchaseAppovalCode);
					purchaseapproved.setApprovedBy(roleassign.getAssignTo());
					purchaseapproved.setStatus("Pending");
					purchaseapprovedlist.add(purchaseapproved);
				}
				purchaseapproval.setPurchaseApproved(purchaseapprovedlist);
				session.save(purchaseapproval);
				HibernateUtil.callCommitClose(session);
				
				HibernateUtil.updateField("tblpurchasefundingplanning", "StaffCode", staffCode, "PURCHASEFUNDINGCODE", purchaseFundingCode, sessionFactory);
				String spurchasedate = HelperUtil.parsejsondate("1", purchasedate);
				HibernateUtil.updateField("tblpurchasefundingplanning", "FundingDate", spurchasedate, "PURCHASEFUNDINGCODE", purchaseFundingCode, sessionFactory);
				HibernateUtil.updateJobList(jobId, "Purchase Funding Planning", "Purchase Approval", "", "DP000009", sessionFactory);
				
				productquotationobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
				productquotationobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
				productquotationarray.put(productquotationobj);
				
			}else{
				JSONObject exceptionobj = new JSONObject();
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				productquotationarray.put(exceptionobj);
				logger.info("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message:" + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			productquotationarray.put(exceptionobj);
			logger.error("submitPurchaseFunding() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return productquotationarray;
	}
}
