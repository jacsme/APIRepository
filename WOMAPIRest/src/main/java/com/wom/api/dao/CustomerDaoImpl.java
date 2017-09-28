package com.wom.api.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.transaction.annotation.Transactional;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.QueriesString;
import com.wom.api.constant.StatusCode;
import com.wom.api.crypt.DecryptionUtil;
import com.wom.api.crypt.EncryptionUtil;
import com.wom.api.email.EmailSimpleUtil;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.AddressInfo;
import com.wom.api.model.Customer;
import com.wom.api.model.CustomerCard;
import com.wom.api.model.CustomerRecharge;
import com.wom.api.model.LoginUser;
import com.wom.api.model.SalesOrderDetails;
import com.wom.api.model.SalesOrderInfo;
import com.wom.api.model.Voucher;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;
import com.wom.api.util.ResultGeneratorUtil;
@Transactional
public class CustomerDaoImpl implements CustomerDao{
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(CustomerDaoImpl.class);
	FactoryEntityService<LoginUser> factoryentityService = new FactoryEntityServiceImpl<LoginUser>();
	FactoryEntityService<Customer> factoryentityCustomerService = new FactoryEntityServiceImpl<Customer>();
	FactoryEntityService<CustomerRecharge> factoryentityCustomerRecharge = new FactoryEntityServiceImpl<CustomerRecharge>();
	FactoryEntityService<AddressInfo> factoryentityAddress = new FactoryEntityServiceImpl<AddressInfo>();
	FactoryEntityService<CustomerCard> factoryentityCCard = new FactoryEntityServiceImpl<CustomerCard>();
	FactoryEntityService<SalesOrderInfo> factoryentitySalesOrder = new FactoryEntityServiceImpl<SalesOrderInfo>();
	FactoryEntityService<Voucher> factoryentityVoucher = new FactoryEntityServiceImpl<Voucher>();
	FactoryEntityService<SalesOrderInfo> factoryentityOrderReceipt = new FactoryEntityServiceImpl<SalesOrderInfo>();
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getCustomerInfo(String customercode) throws Exception {
		 
		JSONArray customerarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Map<String, String> mapcustomerList = new HashMap<String, String>();
		Session session = sessionFactory.openSession();
		try {
			List<String> customerInfoList = session.createSQLQuery(QueriesString.customerinfoQuery 
					+ QueriesString.customerinfoWhere + QueriesString.customerinfoGroupby)
					.setString("customercode", HelperUtil.checkNullString(customercode))
					.list();
			if (customerInfoList.size() != 0){
				customerarray = ResultGeneratorUtil.populateresults(customerInfoList, MainEnum.CUSTOMER);
				List<AddressInfo> addressinfolist = factoryentityAddress.getEntityList(MainEnum.ADDRESSINFO, customercode, session); 
				if (addressinfolist != null){
					for(AddressInfo addressinfo:addressinfolist){
						mapcustomerList.put("AddressCode", addressinfo.getAddressCode());
						mapcustomerList.put("AddressName", addressinfo.getAddressInfo());
						mapcustomerList.put("AddressType", addressinfo.getAddressType());
						customerarray.put(mapcustomerList);
					}
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					customerarray.put(exceptionobj);
					logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
				}
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				customerarray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			customerarray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return customerarray;
	}
	
	@Override
	public JSONArray getCustomerAddress(String scustomercode) throws Exception{
		
		JSONArray addressarray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Map<String, String> mapaddressList = new HashMap<String, String>();
		Session session = sessionFactory.openSession();
		try {
			String customercode = HelperUtil.checkNullString(scustomercode);
			List<AddressInfo> addressinfolist = factoryentityAddress.getEntityList(MainEnum.ADDRESSINFO, customercode, session); 
			if (addressinfolist != null){
				for(AddressInfo addressinfo:addressinfolist){
					mapaddressList.put("AddressCode", addressinfo.getAddressCode());
					mapaddressList.put("AddressName", addressinfo.getAddressInfo());
					mapaddressList.put("AddressType", addressinfo.getAddressType());
					addressarray.put(mapaddressList);
				}
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				addressarray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			addressarray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return addressarray;
	}
	
	@Override
	public JSONArray submitCustomer(HttpServletRequest request, HttpServletResponse response, String emailaddress, String phonenumber, String password, String postcode) throws Exception{
		JSONArray mebershiparray = new JSONArray();
		JSONObject membershipobj = new JSONObject();
		Customer customer = null;
		String registrationcode = null;
		Session session = sessionFactory.openSession();
		try {
			
			LoginUser loginuser = factoryentityService.getEntity(MainEnum.LOGINREG, emailaddress, session);
			String encyptpwd = EncryptionUtil.encrypt(password);
			
			if (loginuser==null){
				String usercode = ResultGeneratorUtil.codeGenerator("", "sq_user_code", "22" + postcode, session);
				BigInteger userid = ResultGeneratorUtil.idGenerator("", "sq_user_id", session);
				loginuser = new LoginUser(userid, usercode, encyptpwd, emailaddress, "Grocery");
				
				registrationcode = HelperUtil.generateRandomCode();

				BigInteger customerid = ResultGeneratorUtil.idGenerator("", "sq_user_id", session);
				customer = new Customer(customerid, usercode, phonenumber, registrationcode);
				
				if(EmailSimpleUtil.sendEmailRegister(request, emailaddress, emailaddress, password, registrationcode)){
					String smsstatus = sendSMS(phonenumber, registrationcode);
					
					if(smsstatus.equalsIgnoreCase("Success")){
						session.save(loginuser);
						session.save(customer);
						HibernateUtil.callCommitClose(session);
						
						membershipobj.put("RegistrationCode", registrationcode);
						membershipobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
						membershipobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
						mebershiparray.put(membershipobj);
						
						logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Verification Code :" + registrationcode);
					}else{
						membershipobj.put("StatusCode", StatusCode.SMS_ERROR_CODE);
						membershipobj.put("Message", Messages.SMS_NOT_SUCCESSFUL_MESSAGE);
						mebershiparray.put(membershipobj);
						logger.warn("StatusCode:" + StatusCode.SMS_ERROR_CODE + " Message :" + Messages.SMS_NOT_SUCCESSFUL_MESSAGE);
					}
				}else{
					membershipobj.put("StatusCode", StatusCode.INVALID_EMAIL_CODE);
					membershipobj.put("Message", Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
					mebershiparray.put(membershipobj);
					logger.warn("StatusCode:" + StatusCode.INVALID_EMAIL_CODE + " Message :" + Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
				}
			}else{
				if(loginuser.getActive().equalsIgnoreCase("NO")){
					customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMEREMOBILE, phonenumber, loginuser.getUserCode(), session);
					
					if(customer != null){
						registrationcode = customer.getVerifiedCode();
						if(EmailSimpleUtil.sendEmailRegister(request, emailaddress, emailaddress, password, registrationcode)){
							String smsstatus = sendSMS(phonenumber, registrationcode);
							
							if(smsstatus.equalsIgnoreCase("Success")){
								loginuser.setPassword(encyptpwd);
								session.save(loginuser);
								HibernateUtil.callCommitClose(session);
							
								membershipobj.put("RegistrationCode", registrationcode);
								membershipobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
								membershipobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
								mebershiparray.put(membershipobj);
								logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Verification Code :" + registrationcode);
								
							}else{
								membershipobj.put("StatusCode", StatusCode.SMS_ERROR_CODE);
								membershipobj.put("Message", Messages.SMS_NOT_SUCCESSFUL_MESSAGE);
								mebershiparray.put(membershipobj);
								logger.warn("StatusCode:" + StatusCode.SMS_ERROR_CODE + " Message :" + Messages.SMS_NOT_SUCCESSFUL_MESSAGE);
							}
						}else{
							membershipobj.put("StatusCode", StatusCode.INVALID_EMAIL_CODE);
							membershipobj.put("Message", Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
							mebershiparray.put(membershipobj);
							logger.warn("StatusCode:" + StatusCode.INVALID_EMAIL_CODE + " Message :" + Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
						}
					}else{
						membershipobj.put("StatusCode", StatusCode.ALREADY_EXIST);
						membershipobj.put("Message", Messages.EMAIL_ALREADY_IN_USE_MESSAGE);
						mebershiparray.put(membershipobj);
						logger.warn("StatusCode:" + StatusCode.ALREADY_EXIST + " Message :" + Messages.EMAIL_ALREADY_IN_USE_MESSAGE);
					}
				}else{
					membershipobj.put("StatusCode", StatusCode.ALREADY_EXIST);
					membershipobj.put("Message", Messages.EMAIL_ALREADY_IN_USE_MESSAGE);
					mebershiparray.put(membershipobj);
					logger.warn("StatusCode:" + StatusCode.ALREADY_EXIST + " Message :" + Messages.EMAIL_ALREADY_IN_USE_MESSAGE);
				}
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			mebershiparray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return mebershiparray;
	}
	
	@SuppressWarnings("unused")
	@Override
	public JSONArray verifiedCustomer(HttpServletRequest request, HttpServletResponse response, String emailaddress, String generatedcode) throws Exception{
		JSONArray verifycustomerarray = new JSONArray();
		JSONObject verifycustomerarrayobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			LoginUser loginuser = factoryentityService.getEntity(MainEnum.LOGINGROC, emailaddress, "NO", session);
			String decyptpwd = DecryptionUtil.decrypt(loginuser.getPassword());
			
			if (loginuser!=null){
				loginuser.setActive("YES");
				Customer customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMER, loginuser.getUserCode(), session);
				if(customer!=null){
					customer.setVerifiedCode(generatedcode);
					session.save(loginuser);
					session.save(customer);
					
					if(EmailSimpleUtil.sendEmailRegister(request, emailaddress, emailaddress, decyptpwd, "")){
						HibernateUtil.callCommitClose(session);
						verifycustomerarrayobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
						verifycustomerarrayobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
						verifycustomerarray.put(verifycustomerarrayobj);
						logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
					}else{
						verifycustomerarrayobj.put("StatusCode", StatusCode.INVALID_EMAIL_CODE);
						verifycustomerarrayobj.put("Message", Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
						verifycustomerarray.put(verifycustomerarrayobj);
						logger.warn("StatusCode:" + StatusCode.INVALID_EMAIL_CODE + " Message :" + Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
					}
				}
			}else{
				verifycustomerarrayobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				verifycustomerarrayobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				verifycustomerarray.put(verifycustomerarrayobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			verifycustomerarray.put(exceptionobj);
			logger.error("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}
		return verifycustomerarray;
	}

	@Override
	public JSONArray resendcode(HttpServletRequest request, HttpServletResponse response, String emailaddress) throws Exception{
		JSONArray mebershiparray = new JSONArray();
		JSONObject membershipobj = new JSONObject();
		Customer customer = null;
		Session session = sessionFactory.openSession();
		try {
			
			LoginUser loginuser = factoryentityService.getEntity(MainEnum.LOGINREG, emailaddress, session);
			
			if (loginuser!=null){
				customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMER, loginuser.getUserCode(), session);
				String decyptpwd = DecryptionUtil.decrypt(loginuser.getPassword());
				if(customer!=null){
					if(EmailSimpleUtil.sendEmailRegister(request, emailaddress, emailaddress, decyptpwd, customer.getVerifiedCode())){
						String smsstatus = sendSMS(customer.getPhoneNumber(), customer.getVerifiedCode());
						if(smsstatus.equalsIgnoreCase("Success")){
							
							membershipobj.put("RegistrationCode", customer.getVerifiedCode());
							membershipobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
							membershipobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
							mebershiparray.put(membershipobj);
							
							logger.info("StatusCode:" + StatusCode.SUCCESSFUL_CODE + " Verification Code :" + customer.getVerifiedCode());
						}else{
							membershipobj.put("StatusCode", StatusCode.SMS_ERROR_CODE);
							membershipobj.put("Message", Messages.SMS_NOT_SUCCESSFUL_MESSAGE);
							mebershiparray.put(membershipobj);
							logger.warn("StatusCode:" + StatusCode.SMS_ERROR_CODE + " Message :" + Messages.SMS_NOT_SUCCESSFUL_MESSAGE);
						}
					}else{
						membershipobj.put("StatusCode", StatusCode.INVALID_EMAIL_CODE);
						membershipobj.put("Message", Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
						mebershiparray.put(membershipobj);
						logger.warn("StatusCode:" + StatusCode.INVALID_EMAIL_CODE + " Message :" + Messages.INVALID_EMAIL_ADDRESS_MESSAGE);
					}
				}else{
					membershipobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					membershipobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					mebershiparray.put(membershipobj);
					logger.warn("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message :" + Messages.NO_RECORD_FOUND_MESSAGE);
				}
			}else{
				membershipobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				membershipobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				mebershiparray.put(membershipobj);
				logger.warn("StatusCode:" + StatusCode.NO_RECORD_FOUND_CODE + " Message :" + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			mebershiparray.put(exceptionobj);
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Error Message:" + e.getMessage());
		}
		return mebershiparray;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getAvailableWOMCoins(String customercode) throws Exception {
		JSONArray womcoinsArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		BigDecimal availablewomcoins = new BigDecimal(0.00);
		BigDecimal purchaseamount = new BigDecimal(0.00);
		BigDecimal totalpurchase = new BigDecimal(0.00);
		Session session = sessionFactory.openSession();
		Map<String, String> mapcustomerList = new HashMap<String, String>();
		try {
			Customer customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMER, customercode, session); 
			List<String> customeremail = session.createSQLQuery(QueriesString.customerQuery 
					+ QueriesString.customerinfoWhere)
					.setString("customercode", customercode)
					.list();
			if (customeremail.size() != 0){
				for (Iterator it = customeremail.iterator(); it.hasNext();){
					Object[] customerRecord = (Object[]) it.next();
					List<SalesOrderInfo> salesorderinfolist = factoryentitySalesOrder.getEntityList(MainEnum.SALESORDER, HelperUtil.checkNullString(customerRecord[0]), session);
					for(SalesOrderInfo salesinfo:salesorderinfolist){
						purchaseamount = new BigDecimal((String) salesinfo.getWcPaidAmount()); //computePurchaseAmount(salesinfo, customercode);
						totalpurchase = totalpurchase.add(purchaseamount);
					}
					availablewomcoins = computeAvailableWOMCoins(customer, totalpurchase, customercode);
					
					mapcustomerList.put("CustomerCode", HelperUtil.checkNullString(customerRecord[0]));
					mapcustomerList.put("EmailAddress", HelperUtil.checkNullString(customerRecord[1]));
					mapcustomerList.put("CustomerURL", HelperUtil.CUSTOMER_IMAGE_LOCATION + HelperUtil.checkNullString(HelperUtil.checkNullString(customerRecord[0])) + ".png");
					mapcustomerList.put("WOMCoins", availablewomcoins.toString());
					womcoinsArray.put(mapcustomerList);
				}
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				womcoinsArray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (HibernateException e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			womcoinsArray.put(exceptionobj);
			logger.error("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return womcoinsArray;
	}
	
	@Override
	public JSONArray rechargeWOMCoins(String customercode, String amount, String womcoin, String paymenttype,
			String vouchernumber) throws Exception {
		
		JSONArray womcoinsArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		JSONObject womcoinsObj = new JSONObject();
		List <CustomerRecharge> customerrechargelist = new ArrayList<CustomerRecharge>() ;
		CustomerRecharge rechargelisting = new CustomerRecharge();
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
	    String datetoday = HelperUtil.checkNullTimeZone(dateTimeKL);
	    String creditamount = null;
		
		BigInteger totalreleased = BigInteger.valueOf(0);
		BigInteger totalredeemed = BigInteger.valueOf(0);
		CustomerRecharge customerrecharge = null;
		Session session = sessionFactory.openSession();
		try {
			
			Customer customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMER, customercode, session);
			if (customer != null){
				
				/**Check if payment is Voucher, check the card number if exist in the database **/
				if("Voucher".equals(paymenttype)){
					customerrecharge = factoryentityCustomerRecharge.getEntity(MainEnum.CUSTOMERRECHARGE, customercode, vouchernumber, session);
					if(customerrecharge == null){
						Voucher voucher = factoryentityVoucher.getEntity(MainEnum.VOUCHER, vouchernumber, session);
						
						if(voucher != null){
							/**Check if the voucher is not yet expired **/
							creditamount = voucher.getAmount();
		
							Date strvoucherexpiry = HelperUtil.dbformat.parse(voucher.getExpiryDate());
							DateTime voucherexpiry = new DateTime(strvoucherexpiry);
							
							if(dateTimeKL.isAfter(voucherexpiry)){
								exceptionobj.put("StatusCode", StatusCode.VOUCHER_ALREADY_EXPIRED);
								exceptionobj.put("Message", Messages.VOUCHER_ALREADY_EXPIRED_MESSAGE);
								womcoinsArray.put(exceptionobj);
								logger.warn("StatusCode " + StatusCode.VOUCHER_ALREADY_EXPIRED + " Message " + Messages.VOUCHER_ALREADY_EXPIRED_MESSAGE);
							}else{
								
								totalredeemed = new BigInteger((String) voucher.getTotalRedeemed());
								totalreleased = new BigInteger((String) voucher.getTotalReleased());
								BigInteger add1 = new BigInteger("1");
								
								totalredeemed = totalredeemed.add(add1);
								
								//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
								voucher.setTotalRedeemed(totalredeemed.toString());
								if(totalredeemed.equals(totalreleased)){
									voucher.setRedeemed("YES");
								}
								
								session.save(voucher);
								
								rechargelisting.setCustomerCode(customercode);
								rechargelisting.setRechargeAmount(creditamount);
								rechargelisting.setwOMCoin(creditamount);
								rechargelisting.setPaymentType(paymenttype);
								rechargelisting.setCardNumber(vouchernumber);
								rechargelisting.setRechargeDate(datetoday);
								
								customerrechargelist.add(rechargelisting);
								customer.setCustomerRechargeDetails(customerrechargelist);
							
								session.update(customer);
							
								HibernateUtil.callCommitClose(session);
							
								womcoinsObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
								womcoinsObj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
								womcoinsArray.put(womcoinsObj);
								logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.LOGIN_SUCCESSFUL_MESSAGE);
							}
						}else{
							exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
							exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
							womcoinsArray.put(exceptionobj);
							logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
						}
						
					}else{
						womcoinsObj.put("StatusCode", StatusCode.VOUCHER_ALREADY_REDEEMED);
						womcoinsObj.put("Message", Messages.VOUCHER_ALREADY_REDEEMED_MESSAGE);
						womcoinsArray.put(womcoinsObj);
						logger.warn("StatusCode " + StatusCode.VOUCHER_ALREADY_REDEEMED + " Message " + Messages.VOUCHER_ALREADY_REDEEMED_MESSAGE);
					}
				}else if("Credit Card".equals(paymenttype)){
					customerrecharge = new CustomerRecharge(customercode, amount, amount, 
							paymenttype, datetoday);
					session.save(customerrecharge);
					HibernateUtil.callCommitClose(session);
					
					womcoinsObj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
					womcoinsObj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
					womcoinsArray.put(womcoinsObj);
					logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.LOGIN_SUCCESSFUL_MESSAGE);
				
				}else{
					exceptionobj.put("StatusCode", StatusCode.INVALID_PAYMENT_TYPE);
					exceptionobj.put("Message", Messages.INVALID_PAYMENTTYPE_MESSAGE);
					womcoinsArray.put(exceptionobj);
					logger.warn("StatusCode " + StatusCode.INVALID_PAYMENT_TYPE + " Message " + Messages.INVALID_PAYMENTTYPE_MESSAGE);
				}
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				womcoinsArray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);

			}
		} catch (Exception e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			womcoinsArray.put(exceptionobj);
			logger.warn("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}
		return womcoinsArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getRechargeHistory(String customercode) throws Exception {
		JSONArray rechargehistoryArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			
			List<String> rechargehistoryList = session.createSQLQuery(QueriesString.rechargehistoryQuery
					+ QueriesString.customerinfoWhere + QueriesString.rechargehistoryGroupby
					+ QueriesString.rechargehistoryOrderby)
					.setParameter("customercode", HelperUtil.checkNullString(customercode))
					.list();
			if (rechargehistoryList.size() != 0){
				rechargehistoryArray = ResultGeneratorUtil.populateresults(rechargehistoryList, MainEnum.RECHARGEHISTORY);
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				rechargehistoryArray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (HibernateException e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			rechargehistoryArray.put(exceptionobj);
			logger.warn("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return rechargehistoryArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getVoucherRedeemed(String customercode) throws Exception {
		JSONArray voucherredeemedArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			
			List<String> voucherredeemedList = session.createSQLQuery(QueriesString.voucherredeemedQuery
					+ QueriesString.voucherredeemedWhere + QueriesString.voucherredeemedGroupby
					+ QueriesString.voucherredeemedOrderby)
					.setParameter("customercode", HelperUtil.checkNullString(customercode))
					.list();
			if (voucherredeemedList.size() != 0){
				voucherredeemedArray = ResultGeneratorUtil.populateresults(voucherredeemedList, MainEnum.VOUCHERREDEEMED); //generate this
			}else{
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				voucherredeemedArray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (HibernateException e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			voucherredeemedArray.put(exceptionobj);
			logger.warn("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return voucherredeemedArray;
	}
	
	@Override
	public JSONArray getOrderReceipt(String customercode) throws Exception {
		JSONArray orderreceiptArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		
		Map<String, String> maporderreceiptList = new HashMap<String, String>();
		Session session = sessionFactory.openSession();
		try {
			
			Customer customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMER, customercode, session); 
			
			if (customer != null){
				List<SalesOrderInfo> salesorderinfolist = factoryentityOrderReceipt.getEntityList(MainEnum.ORDERRECEIPT, customercode, session); 
				if(salesorderinfolist.size() != 0){
					for(SalesOrderInfo salesinfo:salesorderinfolist){
						maporderreceiptList.put("CustomerCode", customer.getCustomerCode());
						maporderreceiptList.put("SalesOrderNumber", salesinfo.getSalesOrderCode());
						maporderreceiptList.put("PurchaseDate", salesinfo.getSalesDate());
						maporderreceiptList.put("Status", salesinfo.getStatus());
						maporderreceiptList.put("TaxInvoiceLink", HelperUtil.SO_TAXINVOICE_LOCATION + salesinfo.getSalesOrderCode() + ".pdf");
						orderreceiptArray.put(maporderreceiptList);
					}
				}else{
					exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					orderreceiptArray.put(exceptionobj);
					logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
				}
			}else{		
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				orderreceiptArray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (HibernateException e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			orderreceiptArray.put(exceptionobj);
			logger.error("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return orderreceiptArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getShoppingRanking(String customercode) throws Exception {
		JSONArray shoppingrankArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		Session session = sessionFactory.openSession();
		try {
			
			List<String> shoppingranklist = session.createSQLQuery("CALL SPGetShoppingRank(:customercode)")
					.setString("customercode", customercode)
					.list();	
			
			if (shoppingranklist.size() != 0){
				shoppingrankArray = ResultGeneratorUtil.populateresults(shoppingranklist, MainEnum.SHOPPINGRANK);
				shoppingrankArray.put(getRankingList(session));
			}else{	
				exceptionobj.put("MyRank", "0");
				exceptionobj.put("MyCustomerCode", customercode);
				exceptionobj.put("MyEmailAddress", "-");
				exceptionobj.put("MyTotalPoints", "0");
				shoppingrankArray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (HibernateException e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			shoppingrankArray.put(exceptionobj);
			logger.error("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}
		return shoppingrankArray;
	}
	
	@Override
	public JSONArray editCustomerDetails(String customercode, String emailaddress, String phonenumber) throws Exception{
		
		JSONArray editCustomerDetailsarray = new JSONArray();
		JSONObject editCustomerDetailsobj = new JSONObject();
		boolean cansave = false;
		Session session = sessionFactory.openSession();
		try {
			
			Customer customer = factoryentityCustomerService.getEntity(MainEnum.CUSTOMER, customercode, session);
			if(customer != null){
				if(!phonenumber.equalsIgnoreCase("-")){ 
					customer.setPhoneNumber(phonenumber); 
					session.update(customer);
					cansave = true;
				}
				
				if(!emailaddress.equalsIgnoreCase("-")){
					LoginUser loginuser = factoryentityService.getEntity(MainEnum.LOGINEDIT, customercode, session);
					if(loginuser !=null){
						loginuser.setEmailAddress(emailaddress); 
						session.update(loginuser);
						cansave = true;
					}else{
						cansave = false;
					}
				}
				if(cansave){
					HibernateUtil.callCommitClose(session);
					editCustomerDetailsobj.put("StatusCode", StatusCode.SUCCESSFUL_CODE);
					editCustomerDetailsobj.put("Message", Messages.SAVE_RECORDS_SUCCESSFUL);
					editCustomerDetailsarray.put(editCustomerDetailsobj);
					logger.info("StatusCode " + StatusCode.SUCCESSFUL_CODE + " Message " + Messages.SAVE_RECORDS_SUCCESSFUL);
				}else{
					editCustomerDetailsobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
					editCustomerDetailsobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
					editCustomerDetailsarray.put(editCustomerDetailsobj);
					logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
				}
			}else{
				editCustomerDetailsobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				editCustomerDetailsobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				editCustomerDetailsarray.put(editCustomerDetailsobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
			
		} catch (Exception e) {
			JSONObject exceptionobj = new JSONObject();
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			editCustomerDetailsarray.put(exceptionobj);
			logger.error("editCustomerDetails() ----- StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}
		return editCustomerDetailsarray;
	}
	
	public static BigDecimal computeAvailableWOMCoins(Customer customer, BigDecimal totalpurchase, String customercode){
		BigDecimal currentwomcoins = new BigDecimal(0.00);
		BigDecimal rechargewomcoins = new BigDecimal(0.00);
		BigDecimal totalwomcoins = new BigDecimal(0.00);
		
		List<CustomerRecharge> listwomcoin = customer.getCustomerRechargeDetails();
		for (CustomerRecharge womcoin : listwomcoin) {
			currentwomcoins = new BigDecimal((String) womcoin.getwOMCoin());
			rechargewomcoins = rechargewomcoins.add(currentwomcoins);
		}
		//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
		if(totalpurchase.compareTo(rechargewomcoins) == -1){
			totalwomcoins = rechargewomcoins.subtract(totalpurchase);
		}
		return totalwomcoins;
	}
	
	public static BigDecimal computePurchaseAmount(SalesOrderInfo salesorderinfo, String customercode){
		BigDecimal pricepurchase = new BigDecimal(0.00);
		BigDecimal totalpurchase = new BigDecimal(0.00);
		
		//Change this to salesorderinfo.wcpaidamount and not salesorderinfo.ccpaidamount
		
		if (salesorderinfo!=null){
			List<SalesOrderDetails> salesorderdetailslist = salesorderinfo.getSalesorderdetails();
			for (SalesOrderDetails salesorderdetails : salesorderdetailslist){
				if (!salesorderdetails.getStatus().equalsIgnoreCase("Returned")){
					pricepurchase = new BigDecimal((String) salesorderdetails.getPrice());
					totalpurchase = totalpurchase.add(pricepurchase);
				}
			}
		}
		return totalpurchase;
	}
	
	public String sendSMS(String phonenumber, String registrationcode) throws URISyntaxException, IOException {
	    
		String smsstatus = null;
	    String smsURL = HelperUtil.SMS_URL + "apiusername=" +  HelperUtil.SMS_API_USERNAME + "&apipassword=" + HelperUtil.SMS_API_PASSWROD + "&mobileno=" + phonenumber + "&senderid=" + HelperUtil.SMS_SENDER + "&languagetype=1&message=RM0.00 Your WOM verification is - "  + registrationcode;
	    StringBuilder response = new StringBuilder();
	    
	    URL url;
		try {
			url = new URL(smsURL);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			url = uri.toURL();
		
		    HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
		    if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
		    {
		    	BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
		        String strLine = null;
		        while ((strLine = input.readLine()) != null)
		        {
		        	response.append(strLine);
		        }
		        input.close();
		        logger.info("SMS Sending Successful " + response.toString() + " Code > " + registrationcode);
	            smsstatus = "Success";
		    }else{
		    	logger.error("SMS Sending fail " + response.toString());
	            smsstatus = "Fail";
		    }
		} catch (MalformedURLException e) {
			logger.error("SMS Sending fail " + e.getMessage());
		}
		return smsstatus;  
	   }
	
	@SuppressWarnings("unchecked")
	public JSONArray getRankingList(Session session) throws Exception{
		JSONArray shoppingranklistArray = new JSONArray();
		JSONObject exceptionobj = new JSONObject();
		
		try{	
			List<String> rankinglist = session.createSQLQuery("CALL SPGetShoppingRanklist()")
					.list();
			if (rankinglist.size() != 0){
				shoppingranklistArray = ResultGeneratorUtil.populateresults(rankinglist, MainEnum.SHOPPINGRANKLIST);
			}else{		
				exceptionobj.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
				exceptionobj.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
				shoppingranklistArray.put(exceptionobj);
				logger.warn("StatusCode " + StatusCode.NO_RECORD_FOUND_CODE + " Message " + Messages.NO_RECORD_FOUND_MESSAGE);
			}
		} catch (HibernateException e) {
			exceptionobj.put("StatusCode", StatusCode.EXCEPTION_ERROR_CODE);
			exceptionobj.put("Error Message", e.getMessage());
			shoppingranklistArray.put(exceptionobj);
			logger.error("StatusCode " + StatusCode.EXCEPTION_ERROR_CODE + " Error Message " + e.getMessage());
		}
		return shoppingranklistArray;
		
	}
}
