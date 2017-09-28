package com.wom.api.dao;

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
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class StoreDaoImpl implements StoreDao{

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(StoreDaoImpl.class);
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public JSONArray getContactUsInfo() throws Exception {
		JSONArray contactusListArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> contactuslist = session.createSQLQuery(QueriesString.contactusQuery)
					.list();
			if (contactuslist.size() != 0){
				contactusListArray = ResultGeneratorUtil.populateresults(contactuslist, MainEnum.STORE);		
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				contactusListArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			contactusListArray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return contactusListArray;
	}
}
