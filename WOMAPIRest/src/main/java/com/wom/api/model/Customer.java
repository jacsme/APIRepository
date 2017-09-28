package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.api.util.HelperUtil;

@Entity
@Table(name = "WOMDBPR.tblcustomer")
public class Customer implements Serializable {

	public Customer(){}
	public Customer(BigInteger id, String customercode, String phonenumber, String generatedcode){
		this.id = id;
		this.customerCode = customercode;
		this.phoneNumber = phonenumber;
		this.verifiedCode = generatedcode;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.entryDate = currdatenow;
		
	}
	private static final long serialVersionUID = -2160436081720354477L;

	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "CustomerCode")
	private String customerCode;
	
	@Column(name = "PhoneNumber")
	private String phoneNumber;
	
	@Column(name = "VerifiedCode")
	private String verifiedCode;
	
	@Column(name = "Points")
	private String points;
	
	@Column(name = "EntryDate")
	private String entryDate;

	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getVerifiedCode() {
		return verifiedCode;
	}
	
	public void setVerifiedCode(String verifiedCode) {
		this.verifiedCode = verifiedCode;
	}
	
	public String getPoints() {
		return points;
	}
	
	public void setPoints(String points) {
		this.points = points;
	}
	
	public String getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = CustomerRecharge.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "customerCode", referencedColumnName = "customerCode")
	
	private List<CustomerRecharge> customerRechargeDetails;

	public List<CustomerRecharge> getCustomerRechargeDetails() {
		return customerRechargeDetails;
	}
	public void setCustomerRechargeDetails(List<CustomerRecharge> customerRechargeDetails) {
		this.customerRechargeDetails = customerRechargeDetails;
	}

}
