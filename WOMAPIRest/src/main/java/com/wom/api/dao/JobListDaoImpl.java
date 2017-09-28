package com.wom.api.dao;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
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
import com.wom.api.model.JobList;
import com.wom.api.model.JobListSO;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class JobListDaoImpl implements JobListDao{

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(JobListDaoImpl.class);
	
	public static final String joblistinnerjoin = " INNER JOIN ";
	public static final String joblistimageinner = " C ON C.JOBID=A.JOBID LEFT JOIN tblimages D ON D.CODE = C.STAFFCODE ";
	public static final String joblistimageandroid = " LEFT JOIN tblimages D ON D.CODE = 'AD000001' ";
	public static final String jobliststaffinfo = " LEFT JOIN tblstaff G ON G.STAFFCODE = C.STAFFCODE ";
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getJobList(String jobtype, String staffcode) throws Exception {
		JSONArray jobListArray = new JSONArray();
		List<String> jobList = null;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID( "Asia/Kuala_Lumpur" ) );
		String currdatenow = HelperUtil.checkNullDateZone(dateTimeKL);
		Session session = sessionFactory.openSession();
		try {
			if(jobtype.equals("Stock Control")){
				jobList = session.createSQLQuery("CALL SPStockControlJob(:jobtype)")
						 .setString("jobtype", HelperUtil.checkNullString(jobtype))
						 .list();
				if(!(jobList.size() == 0)){
					jobListArray = ResultGeneratorUtil.populateresults(jobList, MainEnum.SYSTEMJOBLIST);	
				}else{
					JSONObject exceptionobj = new JSONObject();
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					jobListArray.put(exceptionobj);
				}
			}else if((jobtype.equals("Approved Purchase Order")) || (jobtype.equals("Receiving Invoice")) || (jobtype.equals("Receiving Delivery Order"))){
				jobList = session.createSQLQuery("CALL SPPOJob(:jobtype)")
						 .setString("jobtype", HelperUtil.checkNullString(jobtype)).list();
				
				if(!(jobList.size() == 0)){
					jobListArray = ResultGeneratorUtil.populateresults(jobList, MainEnum.SYSTEMJOBLIST);	
				}else{
					JSONObject exceptionobj = new JSONObject();
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					jobListArray.put(exceptionobj);
				}
			
			}else if(jobtype.equals("Sales Order")){
					jobList = session.createSQLQuery("Call SPSalesOrderJob(:jobtype, :currdatenow)")
							.setString("jobtype", HelperUtil.checkNullString(jobtype))
							.setString("currdatenow", HelperUtil.checkNullString(currdatenow)).list();
					
				if(!(jobList.size() == 0)){
					jobListArray = ResultGeneratorUtil.populateresults(jobList, MainEnum.SALESORDERJOBLIST);	
				}else{
					JSONObject exceptionobj = new JSONObject();
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					jobListArray.put(exceptionobj);
				}
					
			}else if (jobtype.equals("Purchase Approval")){
				jobList = session.createSQLQuery("CALL SPPurchaseApprovalJob(:jobtype, :staffcode)")
						.setString("jobtype", HelperUtil.checkNullString(jobtype))
						.setString("staffcode", HelperUtil.checkNullString(staffcode))
						.list();
				if(!(jobList.size() == 0)){
					jobListArray = ResultGeneratorUtil.populateresults(jobList, MainEnum.JOBLIST);	
				}else{
					JSONObject exceptionobj = new JSONObject();
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					jobListArray.put(exceptionobj);
				}

			}else if (jobtype.equals("Item Budget Planning")){
				
				jobList = session.createSQLQuery("CALL SPItemBudgetPlanningJob(:jobtype)")
						 .setString("jobtype", HelperUtil.checkNullString(jobtype)).list();
				if(!(jobList.size() == 0)){
					jobListArray = ResultGeneratorUtil.populateresults(jobList, MainEnum.JOBLIST);		
				}else{
					JSONObject exceptionobj = new JSONObject();
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					jobListArray.put(exceptionobj);
				}
			}else if (jobtype.equals("Purchase Funding Planning")){
				
				jobList = session.createSQLQuery("CALL SPPurchaseFundingPlanningJob(:jobtype)")
						 .setString("jobtype", HelperUtil.checkNullString(jobtype)).list();
				if(!(jobList.size() == 0)){
					jobListArray = ResultGeneratorUtil.populateresults(jobList, MainEnum.JOBLIST);		
				}else{
					JSONObject exceptionobj = new JSONObject();
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					jobListArray.put(exceptionobj);
				}
			}
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			jobListArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return jobListArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getMyJobList(String jobtype, String staffcode) throws Exception {
		JSONArray myjobListArray = new JSONArray();
		List<String> myjobList = null;
		Session session = sessionFactory.openSession();
		try {
			if(jobtype.equals("Sales Order")){
				myjobList = session.createSQLQuery(QueriesString.salesorderjoblistQuery 
						+ QueriesString.salesordermyjoblistWhere
						+ QueriesString.salesorderjoblistOrderby)
						.setString("staffcode", HelperUtil.checkNullString(staffcode))
						.list();
				myjobListArray = ResultGeneratorUtil.populateresults(myjobList, MainEnum.SALESORDERJOBLIST);
			
			}else if (jobtype.equals("Approved Purchase Order")){
				myjobList = session.createSQLQuery(QueriesString.purchaseorderjoblistQuery
						+ QueriesString.purchaseordermyjoblistWhere)
						.setString("staffcode", HelperUtil.checkNullString(staffcode)).list();
				myjobListArray = ResultGeneratorUtil.populateresults(myjobList, MainEnum.PURCHASEORDERJOBLIST);	
			
			}else if (jobtype.equals("Receiving Invoice")){
				myjobList = session.createSQLQuery(QueriesString.purchaseorderjoblistQuery
						+ QueriesString.receivinginvoicemyjoblistWhere)
						.setString("staffcode", HelperUtil.checkNullString(staffcode)).list();
				myjobListArray = ResultGeneratorUtil.populateresults(myjobList, MainEnum.RECEIVINGINVOICEJOBLIST);
			
			}else if (jobtype.equals("Receiving Delivery Order")){
				myjobList = session.createSQLQuery(QueriesString.purchaseorderjoblistQuery
						+ QueriesString.receivingdeliveryordermyjoblistWhere)
						.setString("staffcode", HelperUtil.checkNullString(staffcode)).list();
				myjobListArray = ResultGeneratorUtil.populateresults(myjobList, MainEnum.RECEIVINGINVOICEJOBLIST);
			}else{
				myjobList = session.createSQLQuery(QueriesString.joblistProductQuery 
						+ QueriesString.myjoblistWhere + QueriesString.joblistProductGroupby 
						+ QueriesString.joblistOrderby)
						.setString("staffcode", HelperUtil.checkNullString(staffcode))
						.setString("jobtype", HelperUtil.checkNullString(jobtype)).list();
				myjobListArray = ResultGeneratorUtil.populateresults(myjobList, 
						MainEnum.JOBLIST);	
			}
		
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			myjobListArray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		
		return myjobListArray;
	}
	//JOBID, SUPPLIERCODE, DEPARTMENTCODE, JOBTYPE
	
	@Override
	public String createJobList(String jobid, String productcode, String departmentcode, String jobtype, String suppliercode, Session session) throws Exception {
		JobList joblist = null; 
		String jobcode = ResultGeneratorUtil.codeGenerator(jobid, "sq_joblist_code", "JO22", session);
		BigInteger joblistid = ResultGeneratorUtil.idGenerator("", "sq_joblist_id", session);
		
		if(jobid.equals("")){
			joblist = new JobList(joblistid, jobcode, productcode, suppliercode, departmentcode, jobtype, "");
		}else{
			joblist = new JobList(joblistid, jobid, productcode, suppliercode, departmentcode, jobtype, "");
		}
		session.save(joblist);
		return jobcode;
	}

	@Override
	public String createJobListSO(String jobid, String productcode, String departmentcode, String jobtype, String suppliercode, Session session) throws Exception {
		JobListSO joblistSO = null; 
		String jobcode = ResultGeneratorUtil.codeGenerator(jobid, "sq_joblistso_code", "JO22", session);
		
		if(jobid.equals("")){
			joblistSO = new JobListSO(jobcode, productcode, suppliercode, departmentcode, jobtype, "");
		}else{
			joblistSO = new JobListSO(jobid, productcode, suppliercode, departmentcode, jobtype, "");
		}
		session.save(joblistSO);
		return jobcode;
	}
	
	@Override
	public JobList getProductJobList(String sjobid, String sproductcode, String sdepartmentcode, String sjobtype, String ssuppliercode, Session session) throws Exception{
		
		String jobid = HelperUtil.checkNullString(sjobid);
		String productcode = HelperUtil.checkNullString(sproductcode);
		String departmentcode = HelperUtil.checkNullString(sdepartmentcode); 
		String jobtype = HelperUtil.checkNullString(sjobtype);
		String suppliercode = HelperUtil.checkNullString(ssuppliercode); 
		
		JobList joblist = (JobList) session.createCriteria(JobList.class)
				.add(Restrictions.eq("jobId", new String(jobid)))
				.add(Restrictions.eq("productCode", new String(productcode)))
				.add(Restrictions.eq("departmentCode", new String(departmentcode)))
				.add(Restrictions.eq("jobType", new String(jobtype)))
				.add(Restrictions.eq("supplierCode", new String(suppliercode))).uniqueResult();
		return joblist;
	}
	
	@Override
	public JobListSO getProductJobListSO(String sjobid, String sproductcode, String sdepartmentcode, String sjobtype, String ssuppliercode, Session session) throws Exception{
		
		String jobid = HelperUtil.checkNullString(sjobid);
		String productcode = HelperUtil.checkNullString(sproductcode);
		String departmentcode = HelperUtil.checkNullString(sdepartmentcode); 
		String jobtype = HelperUtil.checkNullString(sjobtype);
		String suppliercode = HelperUtil.checkNullString(ssuppliercode); 
		
		JobListSO joblistSO = (JobListSO) session.createCriteria(JobListSO.class)
				.add(Restrictions.eq("jobId", new String(jobid)))
				.add(Restrictions.eq("productCode", new String(productcode)))
				.add(Restrictions.eq("departmentCode", new String(departmentcode)))
				.add(Restrictions.eq("jobType", new String(jobtype)))
				.add(Restrictions.eq("supplierCode", new String(suppliercode))).uniqueResult();
		return joblistSO;
	}
	
	@Override
	public JobList getStackingProductJobList(String sproductcode, String sdepartmentcode, String sjobtype, String ssuppliercode, Session session) throws Exception{
		
		String productcode = HelperUtil.checkNullString(sproductcode);
		String departmentcode = HelperUtil.checkNullString(sdepartmentcode);
		String jobtype = HelperUtil.checkNullString(sjobtype);
		String suppliercode = HelperUtil.checkNullString(ssuppliercode);
		
		JobList joblist = (JobList) session.createCriteria(JobList.class)
				.add(Restrictions.eq("productCode", new String(productcode)))
				.add(Restrictions.eq("departmentCode", new String(departmentcode)))
				.add(Restrictions.eq("jobType", new String(jobtype)))
				.add(Restrictions.eq("supplierCode", new String(suppliercode))).uniqueResult();
		return joblist;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JobList> checkProductRequest(String sproductcode, Session session) throws Exception{
		
		String productcode = HelperUtil.checkNullString(sproductcode);
		List<JobList> joblist = (List<JobList>) session.createCriteria(JobList.class)
				.add(Restrictions.eq("productCode", new String(productcode)))
				.list();
		
		return joblist;
	}
	
	@Override
	public JSONArray cancelMyJob(String jobid, String jobtype) throws Exception {
		
		logger.info("Cancel Job "+ jobid + " " + jobtype);
		
		JSONObject canceljobobj = new JSONObject();
		JSONArray canceljobarray = new JSONArray();
		
		String departmentCode = null;
		
		try {
			if(jobtype.equals("Receiving Invoice")) {
					departmentCode = "DP000001";
			}else if (jobtype.equals("Purchase Funding Planning")){
					departmentCode = "DP000002";
			}else if (jobtype.equals("Stock Control") || jobtype.equals("Item Budget Planning") || jobtype.equals("Approved Purchase Order")){
					departmentCode = "DP000003";
			}else if (jobtype.equals("Receiving Delivery Order") ||
					jobtype.equals("Stacking Product") || jobtype.equals("Inventory Control")) {
					departmentCode = "DP000004";
			}
			
			if (jobtype.equals("Sales Order")){
				departmentCode = "DP000005";
				HibernateUtil.updateJobListSO(jobid, jobtype, jobtype, "", departmentCode, sessionFactory);
			}else{
				HibernateUtil.updateJobList(jobid, jobtype, jobtype, "", departmentCode, sessionFactory);
			}
			
			canceljobobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			canceljobobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			canceljobarray.put(canceljobobj);
			logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message:" + Messages.SAVE_RECORDS_SUCCESSFUL);
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return canceljobarray;
	}
}
