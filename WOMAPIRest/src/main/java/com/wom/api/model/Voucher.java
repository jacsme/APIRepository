package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblvoucher")
public class Voucher implements Serializable{
	
	public Voucher(){}
	private static final long serialVersionUID = 8298951614906152964L;

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name = "VoucherCode")
	private String voucherCode;
	
	@Column(name = "BarCode")
	private String barCode;
	
	@Column(name = "Amount")
	private String amount;
	
	@Column(name = "ExpiryDate")
	private String expiryDate;
	
	@Column(name = "Redeemed")
	private String redeemed;
	
	@Column(name = "VoucherNumber")
	private String voucherNumber;

	@Column(name = "TotalRedeemed")
	private String totalRedeemed;
	
	@Column(name = "TotalReleased")
	private String totalReleased;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getRedeemed() {
		return redeemed;
	}

	public void setRedeemed(String redeemed) {
		this.redeemed = redeemed;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getTotalRedeemed() {
		return totalRedeemed;
	}

	public void setTotalRedeemed(String totalRedeemed) {
		this.totalRedeemed = totalRedeemed;
	}

	public String getTotalReleased() {
		return totalReleased;
	}

	public void setTotalReleased(String totalReleased) {
		this.totalReleased = totalReleased;
	}
	
	
}
