package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WOMDBPR.tblpurchasefundingplanning")
public class PurchaseFundingPlanning implements Serializable{

	public PurchaseFundingPlanning(){}
	public PurchaseFundingPlanning(BigInteger id, String purchaseFundingCode, String jobId, String itemBudgetCode, String staffCode){
		this.id = id;
		this.purchaseFundingCode = purchaseFundingCode;
		this.jobId = jobId;
		this.itemBudgetCode = itemBudgetCode;
		this.staffCode = staffCode;
	}
	private static final long serialVersionUID = -1689750479082450681L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "PurchaseFundingCode")
	private String purchaseFundingCode;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name = "ItemBudgetCode")
	private String itemBudgetCode;
	
	@Column(name = "StaffCode")
	private String staffCode;
	

	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getPurchaseFundingCode() {
		return purchaseFundingCode;
	}

	public void setPurchaseFundingCode(String purchaseFundingCode) {
		this.purchaseFundingCode = purchaseFundingCode;
	}

	public String getItemBudgetCode() {
		return itemBudgetCode;
	}

	public void setItemBudgetCode(String itemBudgetCode) {
		this.itemBudgetCode = itemBudgetCode;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
}
