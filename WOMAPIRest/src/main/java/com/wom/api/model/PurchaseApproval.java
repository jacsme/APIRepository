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
@Table(name = "WOMDBPR.tblpurchaseapproval")
public class PurchaseApproval implements Serializable{

	public PurchaseApproval(){}
	public PurchaseApproval(BigInteger id, String purchaseApprovalCode, String purchaseFundingCode, 
			String jobId, String originalbudget, String currentBudget, String storecode){
		this.id = id;
		this.purchaseApprovalCode = purchaseApprovalCode;
		this.purchaseFundingCode = purchaseFundingCode;
		this.storeCode = storecode;
		this.jobId = jobId;
		this.originalBudget = originalbudget;
		this.currentBudget = currentBudget;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		this.confirmDate = currdatenow;
	}
	
	private static final long serialVersionUID = -5632795549567806915L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "PurchaseApprovalCode")
	private String purchaseApprovalCode;
	
	@Column(name = "PurchaseFundingCode")
	private String purchaseFundingCode;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name = "OriginalBudget")
	private String originalBudget;
	
	@Column(name = "CurrentBudget")
	private String currentBudget;

	@Column(name = "ConfirmDate")
	private String confirmDate;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getPurchaseApprovalCode() {
		return purchaseApprovalCode;
	}

	public void setPurchaseApprovalCode(String purchaseApprovalCode) {
		this.purchaseApprovalCode = purchaseApprovalCode;
	}

	public String getPurchaseFundingCode() {
		return purchaseFundingCode;
	}

	public void setPurchaseFundingCode(String purchaseFundingCode) {
		this.purchaseFundingCode = purchaseFundingCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getOriginalBudget() {
		return originalBudget;
	}

	public void setOriginalBudget(String originalBudget) {
		this.originalBudget = originalBudget;
	}

	public String getCurrentBudget() {
		return currentBudget;
	}

	public void setCurrentBudget(String currentBudget) {
		this.currentBudget = currentBudget;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = PurchaseApproved.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "purchaseApprovalCode", referencedColumnName = "purchaseApprovalCode")
	
	private List<PurchaseApproved> purchaseApproved;

	public List<PurchaseApproved> getPurchaseApproved() {
		return purchaseApproved;
	}

	public void setPurchaseApproved(List<PurchaseApproved> purchaseApproved) {
		this.purchaseApproved = purchaseApproved;
	}
}
