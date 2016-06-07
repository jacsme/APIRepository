package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblcustomerrecharge")
public class CustomerRecharge implements Serializable{
	
	public CustomerRecharge(String customercode, String rechargeamount, String womcoin, 
			String paymenttype, String rechargedate){
		this.customerCode = customercode;
		this.rechargeAmount = rechargeamount;
		this.wOMCoin = womcoin;
		this.paymentType = paymenttype;
		this.rechargeDate = rechargedate;
	}
	
	public CustomerRecharge() {}

	private static final long serialVersionUID = -6041540442012008325L;

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "id")
	private long id;
	
	@Column(name = "CustomerCode")
	private String customerCode;
	
	@Column(name = "RechargeAmount")
	private String rechargeAmount;
	
	@Column(name = "WOMCoin")
	private String wOMCoin;
	
	/** Credit Card; Voucher **/
	@Column(name = "PaymentType")
	private String paymentType;
	
	@Column(name = "CardNumber")
	private String cardNumber;
	
	@Column(name = "RechargeDate")
	private String rechargeDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getwOMCoin() {
		return wOMCoin;
	}

	public void setwOMCoin(String wOMCoin) {
		this.wOMCoin = wOMCoin;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getRechargeDate() {
		return rechargeDate;
	}

	public void setRechargeDate(String rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	
	@Override
	public String toString(){
		String mystring = "customerCode=" + customerCode + "&" + "rechargeAmount=" + rechargeAmount + "&" + "wOMCoin =" + wOMCoin +
				 "&" + "paymentType=" + paymentType + "&" + "cardNumber=" + cardNumber + "&" + "rechargeDate=" + rechargeDate;
		return mystring;
	}
}
