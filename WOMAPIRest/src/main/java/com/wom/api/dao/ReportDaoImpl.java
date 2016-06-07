package com.wom.api.dao;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.model.JobList;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
@Transactional
public class ReportDaoImpl implements ReportDao{

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(ReportDaoImpl.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray generateProductCategoryCounts() throws Exception {
		JSONArray productcountarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> totalcategory = session.createSQLQuery("CALL SPProductCategoryCounts()")
					.list();	
			if(totalcategory.size() != 0 && totalcategory != null){
				for (Iterator it = totalcategory.iterator(); it.hasNext();){
					Object[] totalcategoryresult = (Object[]) it.next();
					
					JSONObject catreportListobj = new JSONObject();
					
					String categoryname = HelperUtil.checkNullString(totalcategoryresult[0]);
					
					catreportListobj.put("CategoryName", HelperUtil.checkNullString(categoryname));
					catreportListobj.put("TotalCategoryProducts", HelperUtil.checkNullNumbers(totalcategoryresult[1]));
					productcountarray.put(catreportListobj);
					
					List<String> totalsubcategory = session.createSQLQuery("CALL SPSubcategoryProductCounts(:categoryname)")
							.setString("categoryname", HelperUtil.checkNullString(categoryname))
							.list();
					
					JSONObject subcatreportListObj = new JSONObject();
					if(totalsubcategory.size() != 0){
						for (Iterator iet = totalsubcategory.iterator(); iet.hasNext();){
							Object[] totalsubcategoryresult = (Object[]) iet.next();
							subcatreportListObj.put(HelperUtil.checkNullString(totalsubcategoryresult[0]), HelperUtil.checkNullNumbers(totalsubcategoryresult[1]));
						}
						productcountarray.put(subcatreportListObj);
					}
				}
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				logger.info("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
				productcountarray.put(exceptionobj);
			}
			
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			productcountarray.put(exceptionobj);
			logger.info("generateProductCategoryCounts() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return productcountarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray generateJobCounts(String staffcode) throws Exception {
		
		JSONArray departmentcountarray = new JSONArray();
		JSONObject departmentListobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		List<JobList> joblist= null;
		
		BigInteger totaldepartmentcounts = BigInteger.valueOf(0);
		BigInteger finaltotal = BigInteger.valueOf(0);
		Session session = sessionFactory.openSession();
		try {
			joblist = session.createSQLQuery("CALL SPGetPendingJobList(:staffcode)")
					.setString("staffcode", HelperUtil.checkNullString(staffcode))
					.list();
			if (joblist.size()!=0){
				for (Iterator it = joblist.iterator(); it.hasNext();){
					Object[] joblistresult = (Object[]) it.next();
					//A.JOBID, A.PRODUCTCODE, A.JOBTYPE, A.STAFFCODE, A.SUPPLIERCODE
					//if(!"Stacking Product".equalsIgnoreCase(HelperUtil.checkNullString(joblistresult[2]))){
					departmentListobj.put("AllocatedJobId", HelperUtil.checkNullString(joblistresult[0]));
					
					if(HelperUtil.checkNullString(joblistresult[1])!=null && !HelperUtil.checkNullString(joblistresult[1]).equals("")){
						departmentListobj.put("ProductCode", HelperUtil.checkNullKeyString(joblistresult[1]));
					}else if(HelperUtil.checkNullString(joblistresult[4])!=null && !HelperUtil.checkNullString(joblistresult[4]).equals("")){
						departmentListobj.put("ProductCode", HelperUtil.checkNullKeyString(joblistresult[4]));
					}else{
						departmentListobj.put("ProductCode", "0");
					}
					departmentListobj.put("AllocatedJobType", HelperUtil.checkNullString(joblistresult[2]));
					break;
					//}else{
					//	departmentListobj.put("AllocatedJobId", "0");
					//	departmentListobj.put("ProductCode", "0");
					//	departmentListobj.put("AllocatedJobType", "none");
					//}
				}
			}else{
				departmentListobj.put("AllocatedJobId", "0");
				departmentListobj.put("ProductCode", "0");
				departmentListobj.put("AllocatedJobType", "none");
			}
			
			List<String> totaldepartment = session.createSQLQuery(QueriesString.reportjoblistQuery).list();	
			
			if(totaldepartment.size() != 0 ){
				for (Iterator it = totaldepartment.iterator(); it.hasNext();){
					Object[] totaldepartmentresult = (Object[]) it.next();
					String departmentname = totaldepartmentresult[0].toString();
					
					//if("Store".equalsIgnoreCase(departmentname)){
					//	totalstockingproduct = (BigInteger) session.createSQLQuery(QueriesString.stockingproductcountQuery)
					//			.uniqueResult();
					//	totaldepartmentcounts = new BigInteger((String) HelperUtil.checkNullNumbers(totaldepartmentresult[1]));
					//finaltotal = totaldepartmentcounts; //.add(totalstockingproduct);
					//	departmentListobj.put(departmentname, HelperUtil.checkNullNumbers(finaltotal));
					
					if ("Delivery".equalsIgnoreCase(departmentname)){
						BigInteger totaldeliveryproduct = (BigInteger) session.createSQLQuery(QueriesString.deliveryjoblistcountQuery)
								.uniqueResult();
						totaldepartmentcounts = new BigInteger((String) HelperUtil.checkNullNumbers(totaldepartmentresult));
						finaltotal = totaldepartmentcounts.add(totaldeliveryproduct);
						departmentListobj.put(HelperUtil.checkNullString(departmentname), HelperUtil.checkNullNumbers(finaltotal));
					}else{
						departmentListobj.put(HelperUtil.checkNullString(departmentname), HelperUtil.checkNullNumbers(totaldepartmentresult[1]));
					}
				}
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				logger.info("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
				departmentcountarray.put(exceptionobj);
			}
			
			departmentcountarray.put(departmentListobj);
			departmentcountarray.put(getPermision(staffcode, session));
			
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			departmentcountarray.put(exceptionobj);
			logger.info("generateJobCounts() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return departmentcountarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray generateDepartmentJobCounts(String departmentname, String staffcode) throws Exception {
		
		JSONArray departmentcountarray = new JSONArray();
		JSONObject jobtypeListObj = new JSONObject();
		
		//BigInteger totalstockingproduct = BigInteger.valueOf(0);
		BigInteger totaldeliveryproduct = BigInteger.valueOf(0);
		List<String> totaljobtypelist;
		Session session = sessionFactory.openSession();
		try {
			if(departmentname.equalsIgnoreCase("Inventory")){
				totaljobtypelist = session.createSQLQuery("CALL SPJobTypeCountsSO(:departmentname)")
						.setString("departmentname", HelperUtil.checkNullString(departmentname)).list();
			}else{
				totaljobtypelist = session.createSQLQuery("CALL SPJobTypeCounts(:departmentname)")
						.setString("departmentname", HelperUtil.checkNullString(departmentname)).list();
			}
			
			if(!(totaljobtypelist.size() == 0)){
				for (Iterator iet = totaljobtypelist.iterator(); iet.hasNext();){
					Object[] totaljobtypelistresult = (Object[]) iet.next();
					jobtypeListObj.put( HelperUtil.checkNullString(totaljobtypelistresult[0]), HelperUtil.checkNullNumbers(totaljobtypelistresult[1]));
				}
			}else{
				jobtypeListObj.put( departmentname, "0");
			}
			
			//if("Store".equalsIgnoreCase(departmentname)){
			//	totalstockingproduct = (BigInteger) session.createSQLQuery(QueriesString.stockingproductcountQuery)
			//			.uniqueResult();
			//	jobtypeListObj.put("Stacking Product", totalstockingproduct);
				
			//}else 
			if ("Delivery".equalsIgnoreCase(HelperUtil.checkNullString(departmentname))){
				totaldeliveryproduct = (BigInteger) session.createSQLQuery(QueriesString.deliveryjoblistcountQuery)
						.uniqueResult();
				jobtypeListObj.put(HelperUtil.checkNullString(departmentname), HelperUtil.checkNullNumbers(totaldeliveryproduct));
			}
			
			departmentcountarray.put(jobtypeListObj);
			departmentcountarray.put(getPermision(staffcode, session));
			logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			departmentcountarray.put(exceptionobj);
			logger.info("generateDepartmentJobCounts() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return departmentcountarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray generateMyJobCounts(String staffcode) throws Exception {
		
		JSONArray departmentcountarray = new JSONArray();
		JSONObject departmentListobj = new JSONObject();
		
		String departmentname = null;
		Session session = sessionFactory.openSession();
		try {
			List<String> totaldepartment = session.createSQLQuery(QueriesString.reportmyjoblistQuery)
					.setString("staffcode", HelperUtil.checkNullString(staffcode)).list();	
			
			if (!(totaldepartment.size() == 0)){
				for (Iterator it = totaldepartment.iterator(); it.hasNext();){
					Object[] totaldepartmentresult = (Object[]) it.next();
					
					departmentname = HelperUtil.checkNullString(totaldepartmentresult[0]);
					departmentListobj.put(departmentname, HelperUtil.checkNullNumbers(totaldepartmentresult[1]));
				}
			}else{
				departmentListobj.put("-", "0");
			}
			
			departmentcountarray.put(departmentListobj);
			departmentcountarray.put(getPermision(staffcode, session));
			logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			departmentcountarray.put(exceptionobj);
			logger.info("generateMyJobCounts() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return departmentcountarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray generateDepartmentMyJobCounts(String staffcode, String departmentname) throws Exception {
		JSONArray departmentcountarray = new JSONArray();
		Session session = sessionFactory.openSession();
		try {
			List<String> totaljobtypelist = session.createSQLQuery(QueriesString.reportjobtypeQuery 
					+ QueriesString.reportmyjobtypeWhere + QueriesString.reportjobtypeGroupby)
					.setString("departmentname", HelperUtil.checkNullString(departmentname))
					.setString("staffcode", HelperUtil.checkNullString(staffcode))
					.list();
			JSONObject jobtypeListObj = new JSONObject();
			if(!(totaljobtypelist.size() == 0)){
				for (Iterator iet = totaljobtypelist.iterator(); iet.hasNext();){
					Object[] totaljobtypelistresult = (Object[]) iet.next();
					jobtypeListObj.put(HelperUtil.checkNullString(totaljobtypelistresult[0]), HelperUtil.checkNullNumbers(totaljobtypelistresult[1]));
				}
			}else{
				jobtypeListObj.put("-", "0");
			}
			departmentcountarray.put(jobtypeListObj);
			departmentcountarray.put(getPermision(staffcode, session));
			logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			departmentcountarray.put(exceptionobj);
			logger.info("generateDepartmentMyJobCounts() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return departmentcountarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray generateManagementCounts(String staffcode) throws Exception {
				
		JSONArray managementcountsarray = new JSONArray();
		JSONObject managementcountsObj = new JSONObject();
		BigInteger stocktotal = BigInteger.valueOf(0);
		BigInteger totaldeliveryproduct = BigInteger.valueOf(0);
		BigInteger finaltotal = BigInteger.valueOf(0);
		BigInteger totalallmyjoblist = BigInteger.valueOf(0);
		BigInteger totalApproval = BigInteger.valueOf(0);
		Session session = sessionFactory.openSession();
		try {
			BigInteger totalalljoblist = (BigInteger) session.createSQLQuery(
					QueriesString.reportalljoblistcountQuery)
					.uniqueResult();
			
			//totalstockingproduct = (BigInteger) session.createSQLQuery(QueriesString.stockingproductcountQuery)
			//			.setCacheable(true)
			//			.uniqueResult();
			stocktotal = totalalljoblist; //.add(totalstockingproduct);
			
			totaldeliveryproduct = (BigInteger) session.createSQLQuery(QueriesString.deliveryjoblistcountQuery)
					.uniqueResult();
			
			finaltotal = totaldeliveryproduct.add(stocktotal);
			managementcountsObj.put("TotalAllJoblist", HelperUtil.checkNullNumbers(finaltotal));
			
			totalallmyjoblist = (BigInteger) session.createSQLQuery(
					QueriesString.reportallmyjoblistcountQuery)
					.setString("staffcode", staffcode)
					.uniqueResult();
			managementcountsObj.put("TotalAllMyJoblist", HelperUtil.checkNullNumbers(totalallmyjoblist));
			
			totalApproval = (BigInteger) session.createSQLQuery(
					QueriesString.reportapprovalQuery)
					.uniqueResult();
			
			managementcountsObj.put("TotalApproval", HelperUtil.checkNullNumbers(totalApproval));
			
			List<String> stafflist = session.createSQLQuery(QueriesString.staffinfoQuery
					+ QueriesString.staffinfoWhere + QueriesString.staffinfoGroupby)
					.setString("staffcode", staffcode).list();
			
			if(!(stafflist.size() == 0)){
				for (Iterator it = stafflist.iterator(); it.hasNext();){
					Object[] staffListRecord = (Object[]) it.next();
				
					managementcountsObj.put("StaffName", HelperUtil.checkNullString(staffListRecord[1]));
					managementcountsObj.put("Position", HelperUtil.checkNullString(staffListRecord[3]));
					managementcountsObj.put("ImageURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(staffListRecord[11]) + "." 
						+ HelperUtil.checkNullString(staffListRecord[12]));
				}
			}else{
				managementcountsObj.put("StaffName", "");
				managementcountsObj.put("Position", "");
				managementcountsObj.put("ImageURL", "");
			}
			
			managementcountsarray.put(managementcountsObj);
			logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			managementcountsarray.put(exceptionobj);
			logger.info("generateManagementCounts() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return managementcountsarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray generateProductCounts() throws Exception {
		JSONArray productcountarray = new JSONArray();
		JSONObject productreportListObj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> totalcounts = session.createSQLQuery("CALL SPProductCounts()")
					.list();
			if (totalcounts.size() != 0){
				for (Iterator it = totalcounts.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					productreportListObj.put("TotalProducts", HelperUtil.checkNullNumbers(resultListRecord[0]));
					productreportListObj.put("TotalCustomers", HelperUtil.checkNullNumbers(resultListRecord[1]));
					productcountarray.put(productreportListObj);
				}
				logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			productcountarray.put(exceptionobj);
			logger.info("generateProductCounts() ---- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return productcountarray;
	}
	
	/** Permission **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject getPermision(String staffcode, Session session) throws Exception {
		
		JSONObject permissionobj = new JSONObject();
		List<String> permissionlist = session.createSQLQuery(QueriesString.permissionQuery +
				QueriesString.permissionWhere + QueriesString.permissionGroupby)
				.setString("staffcode", HelperUtil.checkNullString(staffcode)).list();
		
		for (Iterator it = permissionlist.iterator(); it.hasNext();){
			Object[] permissionresult = (Object[]) it.next();
			String departmentcode = HelperUtil.checkNullString(permissionresult[0]);
			String rolecode = HelperUtil.checkNullString(permissionresult[1]);
			permissionobj.put(departmentcode, rolecode);
		}
		return permissionobj;
	}
}
