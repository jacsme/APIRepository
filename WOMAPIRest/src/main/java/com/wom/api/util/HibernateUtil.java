package com.wom.api.util;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.api.model.JobList;
import com.wom.api.model.JobListSO;
import com.wom.api.model.PurchaseOrder;
import com.wom.api.model.PurchaseOrderProducts;

public class HibernateUtil {

	public static Session session;
	public static Transaction tx;
	 
	public static Session callSession(SessionFactory sessionFactory){
		session = sessionFactory.openSession();
		//tx = session.getTransaction();
		//session.beginTransaction();
		
		return session;
	}
	
	public static void callCommit(Session session){
		//tx = session.getTransaction();
		//tx.commit();
		session.flush();
		session.clear();
	}
	
	public static void callClose(Session session){
		session.flush();
		session.clear();
		session.close();
	}
	
	public static void callCommitClose(Session session){
		session.flush();
		session.clear();
		session.close();
	}
	
	public static void callRollBack(SessionFactory sessionFactory){
		tx.rollback();
	}
	
	public static String getField(String tableName, String field, String swhere, String criteria, SessionFactory sessionFactory){
		Session session = sessionFactory.openSession();
		String recordqry = (String) session.createSQLQuery("SELECT " + field + " FROM " + tableName 
				+ " WHERE " + swhere + " = '" + criteria +"'").uniqueResult();		
		callCommitClose(session);
		return recordqry;
	}
	
	@SuppressWarnings("unused")
	public static void updateField(String tableName, String field, String svalue, String swhere, String criteria, SessionFactory sessionFactory){
		
		Session session = sessionFactory.openSession();
		int updaterecordqry = session.createSQLQuery("UPDATE " + tableName + " SET "
				+ field + "='" + svalue + "' WHERE " + swhere + " = '" + criteria +"'").executeUpdate();		
		callCommitClose(session);
		
	}
	
	@SuppressWarnings("unused")
	public static void insertField(String tableName, String fields, String svalues, SessionFactory sessionFactory){
		
		Session session = sessionFactory.openSession();
		int updaterecordqry = session.createSQLQuery("INSERT INTO " + tableName + "("+ fields + ")" 
				+ " VALUES('" + svalues + "')").executeUpdate();		
		callCommitClose(session);
		
	}
	
	@SuppressWarnings("unused")
	public static boolean updateJobList(String sjobId, String sjobType, String snewjobType,
			String sstaffCode, String sdepartmentCode, SessionFactory sessionFactory) throws Exception{
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		JobList joblist = null;
		
		Session session = sessionFactory.openSession();
		
		String jobId = HelperUtil.checkNullString(sjobId);
		String jobType = HelperUtil.checkNullString(sjobType);
		String newjobType = HelperUtil.checkNullString(snewjobType);
		String staffCode = HelperUtil.checkNullString(sstaffCode);
		String departmentCode = HelperUtil.checkNullString(sdepartmentCode);
		
		joblist = (JobList) session.createCriteria(JobList.class)
				.add(Restrictions.eq("jobId", new String(jobId)))
				.add(Restrictions.eq("jobType", new String(jobType)))
				.uniqueResult();
		
		if(joblist!=null){
			if(!staffCode.equals("")){
				if(!staffCode.equals(joblist.getStaffCode())){
					if (joblist.getStaffCode()!=null && !joblist.getStaffCode().equals("")){
						return false;			
					}
				}
			}
			
			int updaterecordqry = session.createSQLQuery("CALL SPUpdateJobList(:departmentcode, :newjobtype, :staffcode, :datecreated, :jobid, :jobtype)")
					.setString("departmentcode", departmentCode) 
					.setString("newjobtype", newjobType)
					.setString("staffcode", staffCode)
					.setString("datecreated", currdatenow)
					.setString("jobid", jobId)
					.setString("jobtype", jobType)
					.executeUpdate();	
			
			callCommitClose(session);
		}
		return true;
	}
	
	public static boolean updateJobListSO(String sjobId, String sjobType, String snewjobType,
			String sstaffCode, String sdepartmentCode, SessionFactory sessionFactory) throws Exception{
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		JobListSO joblistSO = null;
		
		Session session = sessionFactory.openSession();
		
		String jobId = HelperUtil.checkNullString(sjobId);
		String jobType = HelperUtil.checkNullString(sjobType);
		String newjobType = HelperUtil.checkNullString(snewjobType);
		String staffCode = HelperUtil.checkNullString(sstaffCode);
		String departmentCode = HelperUtil.checkNullString(sdepartmentCode);
		
		joblistSO = (JobListSO) session.createCriteria(JobListSO.class)
				.add(Restrictions.eq("jobId", new String(jobId)))
				.add(Restrictions.eq("jobType", new String(jobType)))
				.uniqueResult();
		
		if(joblistSO!=null){
			if(!staffCode.equals("")){
				if(!staffCode.equals(joblistSO.getStaffCode())){
					if (joblistSO.getStaffCode()!=null && !joblistSO.getStaffCode().equals("")){
						return false;			
					}
				}
			}
			
			joblistSO.setDepartmentCode(departmentCode);
			joblistSO.setJobType(newjobType);
			joblistSO.setStaffCode(staffCode);
			if(!(jobType.equalsIgnoreCase(newjobType))){joblistSO.setDateCreated(currdatenow);}
			session.update(joblistSO);
			callCommitClose(session);
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	public static void updateSalesOrder(String boxcode, SessionFactory sessionFactory) throws Exception{
		Session session = sessionFactory.openSession();
		int updaterecordqry = session.createSQLQuery("CALL updateSalesOrder(:boxcode)")
				.setString("boxcode", boxcode) 
				.executeUpdate();		
		callCommitClose(session);
	}
	
	@SuppressWarnings("unused")
	public static void updateStockProducts(String sstockcode, Integer availableunit, Integer soldunit, SessionFactory sessionFactory) throws Exception{
		Session session = sessionFactory.openSession();
		
		String stockcode = HelperUtil.checkNullString(sstockcode);
		
		int updaterecordqry = session.createSQLQuery("CALL updateStockProducts(:stockcode, :availableunit, :soldunit)")
				.setString("stockcode", stockcode) 
				.setInteger("availableunit", availableunit)
				.setInteger("soldunit", soldunit)
				.executeUpdate();		
		callCommitClose(session);
	}
	
	public static boolean updatestockingproductjob(String sproductsource, String ssourcecode, String sproductcode, 
			String sstaffCode, SessionFactory sessionFactory) throws Exception{
		
		PurchaseOrder purchaseorder = null;
		List<PurchaseOrderProducts> purchaseorderprods = null;
		
		Session session = sessionFactory.openSession();
		String sourcecode = HelperUtil.checkNullString(ssourcecode);
		String productcode = HelperUtil.checkNullString(sproductcode);
		String staffCode = HelperUtil.checkNullString(sstaffCode);
		
		purchaseorder = (PurchaseOrder) session.createCriteria(PurchaseOrder.class)
				.add(Restrictions.eq("purchaseOrderCode", new String(sourcecode)))
				.uniqueResult();
		
		if(purchaseorder!=null){
			if(!staffCode.equals("")){
				purchaseorderprods = purchaseorder.getPurchaseOrderProducts();
				for(PurchaseOrderProducts poproducts : purchaseorderprods){
					if(productcode.equalsIgnoreCase(poproducts.getProductCode())){
						if(!staffCode.equalsIgnoreCase(poproducts.getStaffCode())){
							if (poproducts.getStaffCode()!=null && !poproducts.getStaffCode().equals("")){
								return false;
							} else{
								poproducts.setStaffCode(staffCode);
								purchaseorder.setPurchaseOrderProducts(purchaseorderprods);
							}
						}
					}
				}
				session.update(purchaseorder);
				callCommitClose(session);
			}
		}
		return true;
	}
}

