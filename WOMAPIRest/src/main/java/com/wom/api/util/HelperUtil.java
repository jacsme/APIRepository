package com.wom.api.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

public class HelperUtil {

	public static SimpleDateFormat fullformat = new SimpleDateFormat("dd-MM-yyyy");
	public static SimpleDateFormat shortformat = new SimpleDateFormat("dd-MM-yy");
	public static SimpleDateFormat timeformat = new SimpleDateFormat("dd-MM-yy' 'HH:mm:ss:S");
	public static SimpleDateFormat timeonlyformat = new SimpleDateFormat("HH:mm:ss:S");
	public static SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
	public static SimpleDateFormat dbformat = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat yearformat = new SimpleDateFormat("yy");
	
	public static DecimalFormat dfamount = new DecimalFormat("###,###,##0.00");
	public static DecimalFormat dfdecamount = new DecimalFormat("0.00");
	public static DecimalFormat dfnumber = new DecimalFormat("#");

	public static String purchaseapprovalcode;
	public static String officeaddress;
	public static String deliveryaddress;
	
	/** Use for amazon connectivity **/
	public static final String AMAZON_KEY_ID = ResourceUtil.getMessage("amazon.keyid");
	public static final String AMAZON_SECRET_KEY = ResourceUtil.getMessage("amazon.secretkey");
	
	/** Amazon S3 Bucket name **/
	public static final String PO_BUCKET_NAME = ResourceUtil.getMessage("amazon.pobucket.name");
	public static final String SO_BUCKET_NAME = ResourceUtil.getMessage("amazon.sobucket.name");
	public static final String CM_BUCKET_NAME = ResourceUtil.getMessage("amazon.cmbucket.name");
	
	/** Amazon SES parameters **/
	public static final String PO_EMAIL_MESSAGE = ResourceUtil.getMessage("po.email.message");
	public static final String PO_EMAIL_ADDRESS = ResourceUtil.getMessage("po.email.address");
	public static final String CM_EMAIL_ADDRESS_TO = ResourceUtil.getMessage("cm.email.address.to");
	public static final String ORDER_EMAIL_MESSAGE1 = ResourceUtil.getMessage("orders.email.message1");
	public static final String ORDER_EMAIL_MESSAGE2 = ResourceUtil.getMessage("orders.email.message2");
	public static final String REGISTRATION_EMAIL_MESSAGE1 = ResourceUtil.getMessage("registration.email.message1");
	public static final String REGISTRATION_EMAIL_MESSAGE2 = ResourceUtil.getMessage("registration.email.message2");
	public static final String VERIFICATION_EMAIL_MESSAGE = ResourceUtil.getMessage("verification.email.message");
	
	public static final String EMAIL_SUBJECT = ResourceUtil.getMessage("po.email.subject");
	public static final String PO_IMAGE_LOCATION = ResourceUtil.getMessage("po.image.location");
	public static final String SO_IMAGE_LOCATION = ResourceUtil.getMessage("so.image.location");
	public static final String CM_IMAGE_LOCATION = ResourceUtil.getMessage("cm.image.location");
	
	/** Amazon Images Location **/
	public static final String STAFF_IMAGE_LOCATION = ResourceUtil.getMessage("server.staffimages.location");
	public static final String PRODUCT_IMAGE_LOCATION = ResourceUtil.getMessage("server.productimages.location");
	public static final String SUPPLIER_IMAGE_LOCATION = ResourceUtil.getMessage("server.supplierimages.location");
	public static final String CUSTOMER_IMAGE_LOCATION = ResourceUtil.getMessage("server.customerimages.location");
	public static final String SO_TAXINVOICE_LOCATION = ResourceUtil.getMessage("server.taxinvoice.location");
	public static final String PO_CREDITMEMO_LOCATION = ResourceUtil.getMessage("server.creditmemopdf.location");
	
	/** OneWaySMS SID and Auth Key **/
	public static final String SMS_URL = ResourceUtil.getMessage("registration.url");
	public static final String SMS_API_USERNAME = ResourceUtil.getMessage("registration.username");
	public static final String SMS_API_PASSWROD = ResourceUtil.getMessage("registration.password");
	public static final String SMS_SENDER = ResourceUtil.getMessage("registration.sender"); 
	
	public static final String ACCOUNT_SID = ResourceUtil.getMessage("registration.sid"); 
	public static final String AUTH_TOKEN = ResourceUtil.getMessage("registration.token");  
	public static final String CODE_SENDER = ResourceUtil.getMessage("registration.sender"); 
	
	/** Encryption Key **/
	public static final String SECRET_KEY = "XMzDdG4D03CKm2IxIWQw7g==";
	
	/** googgle map **/
	public static final String MAIN_URL = ResourceUtil.getMessage("googlemap.site");
	public static final String THIRD_PARTY_URL = ResourceUtil.getMessage("geoservices.site");
	public static final String SERVER_COUNTRY = ResourceUtil.getMessage("server.country");
	public static final String STORE_CODE = ResourceUtil.getMessage("store.code");
	
	public static final String COUNTRY_CODE = ResourceUtil.getMessage("country.code");
	public static final String PURCHASING_DEPT_CODE = ResourceUtil.getMessage("purchasing.dept.code");
	public static final String PO_NOTE = ResourceUtil.getMessage("po.note");
	public static final String GST_RATE = ResourceUtil.getMessage("gst.rate");
	
	/** Server Identification **/
	public static final String SERVER_DOMAIN_MANAGEMENT = ResourceUtil.getMessage("server.domain.management");
	public static final String SERVER_DOMAIN_GROCERY = ResourceUtil.getMessage("server.domain.grocery");
	
	/** Delivery Slot Parameter **/
	public static final String DELIVERYSLOT_A = ResourceUtil.getMessage("delivery.slot.A");
	public static final String DELIVERYSLOT_B = ResourceUtil.getMessage("delivery.slot.B");
	public static final String PICKUPSTART = ResourceUtil.getMessage("pickup.starttime");
	public static final String PICKUPEND = ResourceUtil.getMessage("pickup.endtime");
	
	public static synchronized String getPurchaseapprovalcode() {
		return purchaseapprovalcode;
	}

	public static void setPurchaseapprovalcode(String purchaseapprovalcode) {
		HelperUtil.purchaseapprovalcode = purchaseapprovalcode;
	}

	public static String getOfficeaddress() {
		return officeaddress;
	}

	public static void setOfficeaddress(String officeaddress) {
		HelperUtil.officeaddress = officeaddress;
	}

	public static String getDeliveryaddress() {
		return deliveryaddress;
	}

	public static void setDeliveryaddress(String deliveryaddress) {
		HelperUtil.deliveryaddress = deliveryaddress;
	}

	public static String checkNullNumbers(Object objvalue){
		String strnewValue = "0";
		BigDecimal objdecimal = null;
		
		if (objvalue != null){
			if (objvalue instanceof Integer){
				Integer objInt = ((Integer) objvalue).intValue();
				strnewValue = dfnumber.format(objInt);
			}else if (objvalue instanceof String){
				if (((String) objvalue).contains(".")){
					objdecimal = new BigDecimal( (String) objvalue );
					strnewValue = dfamount.format(objdecimal);
				}else{
					Integer objInt = Integer.parseInt((String) objvalue);
					strnewValue = dfnumber.format(objInt);
				}
				
			}else if( objvalue instanceof Number ) {
				double objdbl = ((Number)objvalue).doubleValue();
				strnewValue = dfnumber.format(objdbl);
			}else if( objvalue instanceof BigDecimal ) {
				objdecimal = (BigDecimal) objvalue;
				strnewValue = dfamount.format(objdecimal);
			}
		}
		return strnewValue;		
	}
	
	public static String checkNullWeight(Object objvalue){
		String strnewValue = "1";
		BigDecimal objdecimal = null;
		
		if (objvalue != null){
			if (objvalue instanceof Integer){
				Integer objInt = ((Integer) objvalue).intValue();
				strnewValue = dfnumber.format(objInt);
			}else if (objvalue instanceof String){
				if (((String) objvalue).contains(".")){
					objdecimal = new BigDecimal( (String) objvalue );
					strnewValue = dfdecamount.format(objdecimal);
				}else{
					Integer objInt = Integer.parseInt((String) objvalue);
					strnewValue = dfnumber.format(objInt);
				}
				
			}else if( objvalue instanceof Number ) {
				double objdbl = ((Number)objvalue).doubleValue();
				strnewValue = dfnumber.format(objdbl);
			}else if( objvalue instanceof BigDecimal ) {
				objdecimal = (BigDecimal) objvalue;
				strnewValue = dfdecamount.format(objdecimal);
			}
		}
		return strnewValue;		
	}
	
	public static String checkNullDigits(Object objvalue){
		String strnewValue = "0";
		BigDecimal objdecimal = null;
		
		if (objvalue != null){
			if (objvalue instanceof String){
				objdecimal = new BigDecimal( (String) objvalue );
				strnewValue = dfamount.format(objdecimal);
				
			}else if (objvalue instanceof Number){
				objdecimal = new BigDecimal( ((Number)objvalue).doubleValue() );
				strnewValue = dfamount.format(objdecimal);
			}else if( objvalue instanceof BigDecimal ) {
				objdecimal = (BigDecimal) objvalue;
				strnewValue = dfamount.format(objdecimal);
			}
		}
		return strnewValue;		
	}
	
	public static String convertMass(Object objvalue){
		String strnewValue = objvalue.toString();
		
		if (objvalue != null){
			if (objvalue.toString().equals("G")){
				strnewValue = "GRAM";
			}else if (objvalue.toString().equals("K")){
				strnewValue = "KILO";
			}else if (objvalue.toString().equals("L")){
				strnewValue = "LITER";
			}
		}
		return strnewValue;		
	}
	public static String checkNullAmount(Object objvalue){
		String dblnewValue = "0.00";
		BigDecimal objdecimal = null;
		
		if (objvalue != null){
			if( objvalue instanceof BigDecimal ) {
				objdecimal = (BigDecimal) objvalue;
				
            } else if( objvalue instanceof String ) {
            	objdecimal = new BigDecimal( (String) objvalue );
                
            } else if( objvalue instanceof Integer ) {
            	objdecimal = new BigDecimal( (Integer) objvalue );
                
            } else if( objvalue instanceof Number ) {
            	objdecimal = new BigDecimal( ((Number)objvalue).doubleValue() );
            }
			dblnewValue = dfdecamount.format(objdecimal);
		}
		return dblnewValue;		
	}
	
	public static String checkNullPDFAmount(Object objvalue){
		String dblnewValue = "0.00";
		BigDecimal objdecimal = null;
		
		if (objvalue != null){
			if( objvalue instanceof BigDecimal ) {
				objdecimal = (BigDecimal) objvalue;
				
            } else if( objvalue instanceof String ) {
            	objdecimal = new BigDecimal( (String) objvalue );
                
            } else if( objvalue instanceof Integer ) {
            	objdecimal = new BigDecimal( (Integer) objvalue );
                
            } else if( objvalue instanceof Number ) {
            	objdecimal = new BigDecimal( ((Number)objvalue).doubleValue() );
            }
			dblnewValue = dfamount.format(objdecimal);
		}
		return dblnewValue;		
	}
	
	public static String checkNullDate(Object objvalue){
		
		String currdate = shortformat.format(new Date());
		
		if (objvalue != null){
			if( objvalue instanceof Timestamp ) {
				long millisec = ((Timestamp) objvalue).getTime() + (((Timestamp) objvalue).getNanos() / 1000000);
				Date date = new Date(millisec);
				currdate = shortformat.format(date);
			}else if( objvalue instanceof Date ) {
				currdate = shortformat.format(objvalue);
			}else if( objvalue instanceof DateTime ) {
				int mnt = ((DateTime) objvalue).getMonthOfYear();
				int dd = ((DateTime) objvalue).getDayOfMonth();
				int yy = ((DateTime) objvalue).getYear();
				String dt =  StringUtils.leftPad("" + String.valueOf(dd), 2, '0') + "-" + StringUtils.leftPad("" + String.valueOf(mnt), 2, '0') + "-" + String.valueOf(yy);
				currdate = dt;
			}else{
				currdate = shortformat.format(objvalue.toString());
			}
		}
		return currdate;		
	}
	
	public static String checkNullDateSlot(Object objvalue){
		
		String currdate = shortformat.format(new Date());
		
		if (objvalue != null){
			if( objvalue instanceof Timestamp ) {
				long millisec = ((Timestamp) objvalue).getTime() + (((Timestamp) objvalue).getNanos() / 1000000);
				Date date = new Date(millisec);
				currdate = shortformat.format(date);
			}else if( objvalue instanceof Date ) {
				currdate = shortformat.format(objvalue);
			}else if( objvalue instanceof DateTime ) {
				int mnt = ((DateTime) objvalue).getMonthOfYear();
				int dd = ((DateTime) objvalue).getDayOfMonth();
				int yy = ((DateTime) objvalue).getYear();
				String dt =  StringUtils.leftPad("" + String.valueOf(dd), 2, '0') + "-" + StringUtils.leftPad("" + String.valueOf(mnt), 2, '0') + "-" + String.valueOf(yy);
				currdate = dt;
			}else{
				currdate = shortformat.format(objvalue.toString());
			}
		}
		return currdate;		
	}
	public static String checkNullDateZone(Object objvalue){
		
		String currdatetime = dbformat.format(new Date());
		
		if (objvalue != null){
			if( objvalue instanceof Timestamp ) {
				long millisec = ((Timestamp) objvalue).getTime() + (((Timestamp) objvalue).getNanos() / 1000000);
				Timestamp timestamp = new Timestamp(millisec);
				currdatetime = dbformat.format(timestamp);
			}else if( objvalue instanceof Date ) {
				currdatetime = dbformat.format(objvalue);
			}else if( objvalue instanceof DateTime ) {
				int mnt = ((DateTime) objvalue).getMonthOfYear();
				int dd = ((DateTime) objvalue).getDayOfMonth();
				int yyyy = ((DateTime) objvalue).getYear();
				String dt =  String.valueOf(yyyy) + "-" + StringUtils.leftPad("" + String.valueOf(mnt), 2, '0') + "-" + StringUtils.leftPad("" + String.valueOf(dd), 2, '0');
				currdatetime = dt;
			}else{
				currdatetime = dbformat.format(objvalue.toString());
			}
		}
		return currdatetime;		
	}
	
	
	public static String checkNullTimeZone(Object objvalue){
		String currdatetime = dbformat.format(new Date());
		
		if (objvalue != null){
			if( objvalue instanceof Timestamp ) {
				long millisec = ((Timestamp) objvalue).getTime() + (((Timestamp) objvalue).getNanos() / 1000000);
				Timestamp timestamp = new Timestamp(millisec);
				currdatetime = dbformat.format(timestamp);
			}else if( objvalue instanceof Date ) {
				currdatetime = dbformat.format(objvalue);
			}else if( objvalue instanceof DateTime ) {
				int mnt = ((DateTime) objvalue).getMonthOfYear();
				int dd = ((DateTime) objvalue).getDayOfMonth();
				int yyyy = ((DateTime) objvalue).getYear();
				int hour = ((DateTime) objvalue).getHourOfDay();
			    int min = ((DateTime) objvalue).getMinuteOfHour();
			    int sec = ((DateTime) objvalue).getSecondOfMinute();
				String dt =  String.valueOf(yyyy) + "-" + StringUtils.leftPad("" + String.valueOf(mnt), 2, '0') + "-" + StringUtils.leftPad("" + String.valueOf(dd), 2, '0') + " " + StringUtils.leftPad("" + String.valueOf(hour), 2, '0') + ":" + StringUtils.leftPad("" + String.valueOf(min), 2, '0') + ":" + StringUtils.leftPad("" + String.valueOf(sec), 2, '0');
				currdatetime = dt;
			}else{
				currdatetime = dbformat.format(objvalue.toString());
			}
		}
		return currdatetime;		
	}

	public static String checkNullDate2(Object objvalue){
		
		String currdate = shortformat.format(new Date());
		
		if (objvalue != null){
			if( objvalue instanceof Timestamp ) {
				long millisec = ((Timestamp) objvalue).getTime() + (((Timestamp) objvalue).getNanos() / 1000000);
				Date date = new Date(millisec);
				currdate = shortformat.format(date);
			}else if( objvalue instanceof Date ) {
				currdate = shortformat.format(objvalue);
			}else if( objvalue instanceof DateTime ) {
				int mnt = ((DateTime) objvalue).getMonthOfYear();
				int dd = ((DateTime) objvalue).getDayOfMonth();
				int yy = ((DateTime) objvalue).getYear();
				String dt = String.valueOf(yy) + "-" + StringUtils.leftPad("" + String.valueOf(mnt), 2, '0') + "-" + StringUtils.leftPad("" + String.valueOf(dd), 2, '0');
				currdate = dt;
			}else{
				currdate = shortformat.format(objvalue.toString());
			}
		}
		return currdate;		
	}
	public static String checkNullString(Object objvalue){
		String strnewValue = "";
		if (objvalue != null){
			strnewValue = objvalue.toString();
		}
		return strnewValue;		
	}
	
	public static String checkNullMass(Object objvalue){
		String strnewValue = "Each";
		if (objvalue != null){
			strnewValue = objvalue.toString();
		}
		return strnewValue;		
	}
	
	public static String checkNullKeyString(Object objvalue){
		String strnewValue = "0";
		if (objvalue != null){
			strnewValue = objvalue.toString();
		}
		return strnewValue;		
	}
	
	/**
	 * 
	 * @param withtime - will be use to include the time or not (value 1 and 0)
	 * 				     1 - true , 0 = false 
	 * @param submitteddate
	 * @return
	 */
	public static String parsejsondate(String withtime, String submitteddate) {
		
		String sday = null;
		String smonth = null;
		String syear = null;
		String shour = null;
		String smin = null;
		String ssec = null;
		
		if (withtime.equals("0")){ //month and year
			smonth = submitteddate.substring(2,4);
			syear = submitteddate.substring(4,8);
			submitteddate = smonth + "-" +  syear;
		}else{
			sday = submitteddate.substring(0,2);
			smonth = submitteddate.substring(2,4);
			syear = submitteddate.substring(4,8);
			shour = submitteddate.substring(8,10);
			smin = submitteddate.substring(10,12);
			ssec = submitteddate.substring(12,14);
			
			if (withtime.equals("1")){
				submitteddate = syear + "-" + smonth + "-" +  sday + " " + shour + ":" + smin + ":" + ssec ;
			}else{
				submitteddate = syear + "-" + smonth + "-" +  sday;
			}
		}
		return submitteddate;
	}
	
	public static String generateRandomCode() throws Exception{
		StringBuffer code = new StringBuffer();
	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= 4; ++idx){
	      int randomInt = randomGenerator.nextInt(100);
	      code.append(randomInt);
	    }
		return code.toString();
	}
}
