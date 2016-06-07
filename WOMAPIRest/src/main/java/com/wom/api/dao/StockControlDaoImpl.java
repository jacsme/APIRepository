package com.wom.api.dao;

import java.math.BigInteger;
import java.util.List;

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
import com.wom.api.model.ItemBudgetPlanning;
import com.wom.api.model.StockControl;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResourceUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class StockControlDaoImpl implements StockControlDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(StockControlDaoImpl.class);
	public static String serverloc = ResourceUtil.getMessage("server.location");
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public JSONArray getStockControl(String jobId, String productcode, String staffCode) throws Exception {
		
		JSONArray stockcontrolArray = new JSONArray();
		JSONObject takenobj = new JSONObject();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			if (HibernateUtil.updateJobList(jobId, "Stock Control", "Stock Control", staffCode, "DP000003", sessionFactory) == true){
				List<String> stockcontrolList = session.createSQLQuery(QueriesString.stockcontrolQuery 
						+ QueriesString.stockcontrolWhere)
					    .setParameter("productcode1", HelperUtil.checkNullString(productcode))
						.setParameter("productcode2", HelperUtil.checkNullString(productcode))
						.setParameter("jobid", HelperUtil.checkNullString(jobId))
						.setParameter("productcode3", HelperUtil.checkNullString(productcode)).list();
				if (stockcontrolList.size() != 0){
					stockcontrolArray = ResultGeneratorUtil.populateresults(stockcontrolList, MainEnum.STOCKCONTROL);
					stockcontrolArray.put(getStockControlHistory(productcode, session));
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					stockcontrolArray.put(exceptionobj);
				}
			}else{
				takenobj.put("StatusCode", StatusCode.ALREADY_EXIST);
				takenobj.put("Message", Messages.JOB_ALREADY_IN_USE_MESSAGE);
				stockcontrolArray.put(takenobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			stockcontrolArray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return stockcontrolArray;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getStockControlHistory(String productcode, Session session) throws Exception{
		JSONArray stockcontrolhistoryArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		
		List<String> stockcontrolhistoryList = session.createSQLQuery("CALL SPGetStockControlHistory(:productcode);")
				.setString("productcode", HelperUtil.checkNullString(productcode)).list();
		if (stockcontrolhistoryList.size() != 0){
			stockcontrolhistoryArray = ResultGeneratorUtil.populateresults(stockcontrolhistoryList, MainEnum.STOCKCONTROLHISTORY);
		}else{
			exceptionobj.put("SupplierName", "-");
			exceptionobj.put("SupplierCode", "-");
			exceptionobj.put("ProductCode", "-");
			exceptionobj.put("PurchaseDate", "-");
			exceptionobj.put("PurchaseUnit", "-");
			
			stockcontrolhistoryArray.put(exceptionobj);
		}
		return stockcontrolhistoryArray;
	}
	
	@Override
	public JSONArray setStockControl(String jobid, String productcode, String storecode, 
			String stockunit, String amount, String staffcode)
			throws Exception {
		
		JSONArray setstockcontrolArray = new JSONArray();
		JSONObject setstockcontrolObj = new JSONObject();
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		Session session = sessionFactory.openSession();
		try {
			String stockcontrolcode = ResultGeneratorUtil.codeGenerator("", "sq_stock_control", "SC22", session);
			String itembudgetcode = ResultGeneratorUtil.codeGenerator("", "sq_item_budget", "IB22", session);
			
			BigInteger stockcontrolid = ResultGeneratorUtil.idGenerator("", "sq_stock_control_id", session);
			StockControl stockcontrol  = new StockControl(stockcontrolid, stockcontrolcode, jobid, productcode,  
					storecode, stockunit, staffcode);
			
			BigInteger itembudgetid = ResultGeneratorUtil.idGenerator("", "sq_item_budget_id", session);
			ItemBudgetPlanning itembudgetplanning = new ItemBudgetPlanning(itembudgetid, itembudgetcode, stockcontrolcode,
					jobid, productcode, amount, staffcode, currdatenow);
			
			session.save(stockcontrol);
			session.save(itembudgetplanning);
			
			HibernateUtil.callCommitClose(session);
			HibernateUtil.updateJobList(jobid, "Stock Control", "Item Budget Planning", "", "DP000003", sessionFactory);
			
			setstockcontrolObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
			setstockcontrolObj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
			
			setstockcontrolArray.put(setstockcontrolObj);
		
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			setstockcontrolArray.put(exceptionobj);
		}
		
		return setstockcontrolArray;
	}
}
