package com.wom.api.dao;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.crypt.DecryptionUtil;
import com.wom.api.email.EmailSimpleUtil;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.Customer;
import com.wom.api.model.LoginUser;
import com.wom.api.model.ProductImage;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class LoginDaoImpl implements LoginDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(LoginDaoImpl.class);
	FactoryEntityService<Customer> factoryentityCustomerService = new FactoryEntityServiceImpl<Customer>();
	FactoryEntityService<LoginUser> factoryentityService = new FactoryEntityServiceImpl<LoginUser>();
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getAppVersion() throws Exception{
		JSONArray appversionarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			List<String> appversionlist = session.createSQLQuery(QueriesString.appversionQuery)
					.list();
			if (appversionlist.size() != 0){
				appversionarray = ResultGeneratorUtil.populateresults(appversionlist, MainEnum.VERSION);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				appversionarray.put(exceptionobj);
			}
		} catch (HibernateException e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			appversionarray.put(exceptionobj);
			logger.error("getAppVersion() --- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return appversionarray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getLoginUser(String login, String password, String app) throws Exception {
		JSONArray loginarray = new JSONArray();
		JSONObject loginobj = new JSONObject();
		LoginUser loginuser = null;
		Session session = sessionFactory.openSession();
		try {
			if (app.equals("Management")){
				loginuser = factoryentityService.getEntity(MainEnum.LOGINMAN, login, session);
			}else{
				loginuser = factoryentityService.getEntity(MainEnum.LOGINGROC, login, session); 
			}
			if (loginuser==null){
				loginobj.put("StatusCode", StatusCode.LOGIN_USER_ERROR_CODE);
				loginobj.put("Message", Messages.LOGIN_USER_ERROR);
				loginarray.put(loginobj);
			}else{
				if(DecryptionUtil.decrypt(loginuser.getPassword()).equals(password)){
					loginobj.put("Code", loginuser.getUserCode());
					
					if (app.equals("Management")){
						List<String> stafflist = session.createSQLQuery(QueriesString.staffinfoQuery
								+ QueriesString.staffinfoWhere + QueriesString.staffinfoGroupby)
								.setString("staffcode", HelperUtil.checkNullString(loginuser.getUserCode()))
								.list();
						
						for (Iterator it = stafflist.iterator(); it.hasNext();){
							Object[] staffListRecord = (Object[]) it.next();
						
							loginobj.put("StaffName", HelperUtil.checkNullString(staffListRecord[1]));
							loginobj.put("Position", HelperUtil.checkNullString(staffListRecord[3]));
							loginobj.put("ImageURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(staffListRecord[11]) + "." 
								+ HelperUtil.checkNullString(staffListRecord[12]));
						}
						loginobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
						loginobj.put("Message", Messages.LOGIN_SUCCESSFUL_MESSAGE);
						logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.LOGIN_SUCCESSFUL_MESSAGE);
					}else{
						if(loginuser.getActive().equalsIgnoreCase("NO")){
							loginobj.put("StatusCode", StatusCode.LOGIN_NOTYETVERIFIED_CODE);
							loginobj.put("Message", Messages.LOGIN_NOTYETVERIFIED_MESSAGE);
							logger.info("StatusCode " + StatusCode.LOGIN_NOTYETVERIFIED_CODE + " Message " + Messages.LOGIN_NOTYETVERIFIED_MESSAGE);
						}else{
							loginobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
							loginobj.put("Message", Messages.LOGIN_SUCCESSFUL_MESSAGE);
							logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.LOGIN_SUCCESSFUL_MESSAGE);
						}
					}
					loginarray.put(loginobj);
				}else{
					loginobj.put("StatusCode", StatusCode.LOGIN_PASSWORD_ERROR_CODE);
					loginobj.put("Message", Messages.LOGIN_PASSWORD_ERROR);
					logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.LOGIN_PASSWORD_ERROR);
					loginarray.put(loginobj);
				}
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			loginarray.put(exceptionobj);
			logger.error("getLoginUser() ---- StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return loginarray;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getStaffInfo(String staffcode) throws Exception {
		
		JSONArray staffarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			
			List<String> staffInfoList = session.createSQLQuery(QueriesString.staffinfoQuery 
					+ QueriesString.staffinfoWhere + QueriesString.staffinfoGroupby)
					.setString("staffcode", HelperUtil.checkNullString(staffcode))
					.list();
			if (staffInfoList.size() != 0){
				staffarray = ResultGeneratorUtil.populateresults(staffInfoList, MainEnum.STAFF);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				staffarray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			staffarray.put(exceptionobj);
			logger.error("getStaffInfo() ----- StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return staffarray;
	}
	
	@Override
	public JSONArray submitForgotPassword(HttpServletRequest request, HttpServletResponse response, String emailaddress, String app) throws Exception{
		JSONArray forgetpasswordarray = new JSONArray();
		JSONObject forgetpasswordobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			LoginUser loginuser = factoryentityService.getEntity(MainEnum.LOGINFORGOT, emailaddress, app, session);
			if (loginuser!=null){
				String decyptpwd = DecryptionUtil.decrypt(loginuser.getPassword());
				if(EmailSimpleUtil.sendEmailRegister(request, emailaddress, emailaddress, decyptpwd, "")){
					forgetpasswordobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
					forgetpasswordobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
					forgetpasswordarray.put(forgetpasswordobj);
					logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Message :" + Messages.SUCCESSFUL_EMAIL_SENT_MESSAGE);
				}else{
					forgetpasswordobj.put("StatusCode", StatusCode.INVALID_EMAIL_CODE);
					forgetpasswordobj.put("Message", Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
					forgetpasswordarray.put(forgetpasswordobj);
					logger.warn("StatusCode:" + StatusCode.INVALID_EMAIL_CODE + " Message :" + Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
				}
			}else{
				forgetpasswordobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				forgetpasswordobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				forgetpasswordarray.put(forgetpasswordobj);
				logger.warn("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message :" + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			forgetpasswordarray.put(exceptionobj);
			logger.error("submitForgotPassword() ----- StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return forgetpasswordarray;
	}
}
