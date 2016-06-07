package com.wom.api.constant;

public class StatusCode {

	/**	The user is successfully login  **/
	public static final String SUCCESSFUL_CODE = "200";
	
	/**	The user is successfully login  **/
	public static final String UNSUCCESSFUL_CODE = "900";
	
	/** The user id is not correct **/
	public static final String LOGIN_USER_ERROR_CODE = "100";
	
	/** The user id is correct but the password is incorrect **/
	public static final String LOGIN_PASSWORD_ERROR_CODE = "300";
	
	/** The login user is not exist in the Database **/
	public static final String LOGIN_NOTEXIST_ERROR_CODE = "400";
	
	/** The session is no longer active **/
	public static final String LOGIN_TIMEOUT_ERROR_CODE = "500";
	
	/** The result not found **/
	public static final String NO_RECORD_FOUND_CODE = "600";
	
	/** Exception error **/
	public static final String EXCEPTION_ERROR_CODE = "700";
	
	/** Status code for user exist **/
	public static final String ALREADY_EXIST = "800";
	
	/** Status code for box is in use **/
	public static final String BOX_IS_ON_DELIVERY = "110";
	
	/** Status code for insufficient stocks **/
	public static final String INSUFFICIENT_STOCKS = "110";
	
	/** Status code for user exist **/
	public static final String FOR_DELIVERY_CODE = "120";
	
	/** Status code for insufficient stocks **/
	public static final String VOUCHER_ALREADY_REDEEMED = "130";
	
	/** Status code for email error **/
	public static final String EMAIL_ERROR_CODE = "400";
	
	/** Status code for user exist **/
	public static final String WRONG_STOCKLOCATION_CODE = "900";
	
	/** Status code for user exist **/
	public static final String SMS_ERROR_CODE = "-200";
	
	/** Status code for Voucher already expired **/
	public static final String VOUCHER_ALREADY_EXPIRED = "140";
	
	/** Status code for invalid payment type **/
	public static final String INVALID_PAYMENT_TYPE = "150";
	
	/** Status code for invalid email address **/
	public static final String INVALID_EMAIL_CODE = "-400";
	
	/** Status code for Stockcontrol exist **/
	public static final String STOCKCONTROL_EXIST_CODE = "140";
	
	/** Status code for Address not exist **/
	public static final String ADDRESS_NOT_EXIST_CODE = "-410";
	
	/** Status code for Uploading Error **/
	public static final String UPLOADING_ERROR_CODE = "-420";
	
	/** The login user is not yet verified in the Database **/
	public static final String LOGIN_NOTYETVERIFIED_CODE = "430";
}
