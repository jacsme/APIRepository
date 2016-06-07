package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblpurchaseapproved")
public class PurchaseApproved implements Serializable{

	private static final long serialVersionUID = 8731105675206543345L;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name="PurchaseApprovalCode")
	private String purchaseApprovalCode;
	
	@Column(name = "ApprovedBy")
	private String approvedBy;
	
	@Column(name = "Status")
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPurchaseApprovalCode() {
		return purchaseApprovalCode;
	}

	public void setPurchaseApprovalCode(String purchaseApprovalCode) {
		this.purchaseApprovalCode = purchaseApprovalCode;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
