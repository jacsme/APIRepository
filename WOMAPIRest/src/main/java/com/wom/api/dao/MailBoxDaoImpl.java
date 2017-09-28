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
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class MailBoxDaoImpl implements MailBoxDao{

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(MailBoxDaoImpl.class);
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public JSONArray getMailBox(String customercode) throws Exception {
		JSONArray mailboxListArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> mailboxlist = session.createSQLQuery(QueriesString.mailboxQuery 
					+ QueriesString.mailboxWhere)
					.setString("customercode", HelperUtil.checkNullString(customercode))
					.list();
			if (mailboxlist.size() != 0){
				mailboxListArray = ResultGeneratorUtil.populateresults(mailboxlist, MainEnum.MAILBOX);		
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				mailboxListArray.put(exceptionobj);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			mailboxListArray.put(exceptionobj);
		}finally{
			HibernateUtil.callClose(session);
		}
		return mailboxListArray;
	}
}
