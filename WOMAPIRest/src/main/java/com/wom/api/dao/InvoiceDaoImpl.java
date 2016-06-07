package com.wom.api.dao;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.StatusCode;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.AccountTransaction;
import com.wom.api.model.BudgetList;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class InvoiceDaoImpl implements InvoiceDao{
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(InvoiceDaoImpl.class);
	FactoryEntityService<AccountTransaction> factoryentityAccountTransaction = new FactoryEntityServiceImpl<AccountTransaction>();
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getInvoicePayable() throws Exception {
		
		JSONArray invoicepayableArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> invoicepayableList = session.createSQLQuery("CALL SPInvoicePayable()").list();
			
			if (invoicepayableList.size() != 0){
				invoicepayableArray = ResultGeneratorUtil.populateresults(invoicepayableList, MainEnum.INVOICEPAYABLE);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				invoicepayableArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			invoicepayableArray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return invoicepayableArray;
	}
	
	public JSONArray submitInvoicePayment(String sourcecode, String totalamountpaid, String storecode,
			String staffcode, String paymentmode, String paymentdate) throws Exception{
		
		JSONArray invoicepaymentarray = new JSONArray();
		JSONObject invoicepaymentobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			//UPDATE tblaccounttransaction
			String paymentdatestr = HelperUtil.parsejsondate("1",paymentdate);
			List<AccountTransaction> accounttransaction = factoryentityAccountTransaction.getEntityList(MainEnum.ACCOUNTTRANSACTION, sourcecode, session);
			
			for(AccountTransaction accountlist:accounttransaction){
				accountlist.setAmountPaid(totalamountpaid);
				accountlist.setPaymentDate(paymentdatestr);
				accountlist.setStatus("Paid");
			}
			session.update(accounttransaction);	
			
			//insert to our budget table
			BigInteger budgetid = ResultGeneratorUtil.idGenerator("", "sq_budget_id", session);
			BudgetList budgetlistPO = new BudgetList(budgetid, storecode, "0.00", totalamountpaid, sourcecode, "", staffcode, "RI");
			session.save(budgetlistPO);
			
			HibernateUtil.callCommitClose(session);
			
			invoicepaymentobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			invoicepaymentobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			invoicepaymentarray.put(invoicepaymentobj);
		
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			invoicepaymentarray.put(exceptionobj);
		}
		return invoicepaymentarray;
	}
}
