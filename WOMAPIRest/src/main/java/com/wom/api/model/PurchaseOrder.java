package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.api.util.HelperUtil;

@Entity
@Table(name = "WOMDBPR.tblpurchaseorder")
public class PurchaseOrder implements Serializable{

	public PurchaseOrder(){}
	public PurchaseOrder(BigInteger id, String purchaseordercode, String suppliercode, String jobid,
			String storecode, String staffcode){
		this.id = id;
		this.purchaseOrderCode = purchaseordercode;
		this.supplierCode = suppliercode;
		this.jobId = jobid;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.issueDate = currdatenow;
		this.status = "Pending";
		this.storeCode = storecode;
		this.staffCode = staffcode;
	}
	
	private static final long serialVersionUID = -6272573777162800951L;

	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "PurchaseOrderCode")
	private String purchaseOrderCode;
	
	@Column(name = "SupplierCode")
	private String supplierCode;
	
	@Column(name = "JobId")
	private String jobId;

	@Column(name = "IssueDate")
	private String issueDate;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "StaffCode")
	private String staffCode;

	@Column(name = "DateReceived")
	private String dateReceived;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}
	
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
	@Cascade(CascadeType.ALL)
	@Fetch(FetchMode.SELECT)	
		
	private List<PurchaseOrderProducts> purchaseorderproducts = new ArrayList<PurchaseOrderProducts>(0);

	public List<PurchaseOrderProducts> getPurchaseOrderProducts() {
		return purchaseorderproducts;
	}

	public void setPurchaseOrderProducts(List<PurchaseOrderProducts> purchaseorderproducts) {
		this.purchaseorderproducts = purchaseorderproducts;
	}
}
