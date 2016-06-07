package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.api.util.HelperUtil;

@Entity
@Table(name = "WOMDBPR.tblpurchaseorderproducts")
public class PurchaseOrderProducts implements Serializable{
	
	public PurchaseOrderProducts(){}
	public PurchaseOrderProducts(PurchaseOrder purchaseorder, String purchaseordercode, String productcode, String remainingunit, String staffcode){
		this.purchaseOrder = purchaseorder;
		this.purchaseOrderCode=purchaseordercode;
		this.productCode = productcode;
		this.actualOrderQty = remainingunit;
		this.staffCode = "";
		this.scanned = "YES";
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.scannedDate = currdatenow;
		this.submittedToInventory = "0";
		this.returnedUnits = "0";
	}
	
	public PurchaseOrderProducts(BigInteger id, PurchaseOrder purchaseorder, String purchaseordercode, String productcode,  
			String staffcode, String requestquantity, String requestunit, String totalAmount,
			String actualorderqty, String purchaseapprovalcode){
		this.seq = id;
		this.purchaseOrder = purchaseorder;
		this.purchaseOrderCode=purchaseordercode;
		this.productCode = productcode;
		this.staffCode = "";
		this.requestQuantity = requestquantity;
		this.requestUnit = requestunit;
		this.totalAmount = totalAmount;
		this.actualOrderQty = actualorderqty;
		this.submittedToInventory = "0";
		this.purchaseApprovalCode = purchaseapprovalcode;
		this.returnedUnits = "0";
	}
	
	private static final long serialVersionUID = -2270667660864193310L;
	
	@Id
	//@GenericGenerator(name="idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Seq")
	private BigInteger seq;
	
	@Column(name = "PurchaseOrderCode")
	private String purchaseOrderCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "RequestQuantity")
	private String requestQuantity;
	
	@Column(name = "RequestUnit")
	private String requestUnit;
	
	@Column(name = "TotalAmount")
	private String totalAmount;
	
	@Column(name = "ActualOrderQty")
	private String actualOrderQty;
	
	@Column(name = "SubmittedToInventory")
	private String submittedToInventory;
	
	@Column(name = "FullItem")
	private String fullItem;
	
	@Column(name = "Scanned")
	private String scanned;
	
	@Column(name = "ScannedDate")
	private String scannedDate;
	
	@Column(name = "PurchaseApprovalCode")
	private String purchaseApprovalCode;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name = "ReturnedUnits")
	private String returnedUnits;
	
	public BigInteger getSeq() {
		return seq;
	}
	
	public void setSeq(BigInteger seq) {
		this.seq = seq;
	}
	
	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}
	
	public String getRequestQuantity() {
		return requestQuantity;
	}

	public void setRequestQuantity(String requestQuantity) {
		this.requestQuantity = requestQuantity;
	}

	public String getRequestUnit() {
		return requestUnit;
	}

	public void setRequestUnit(String requestUnit) {
		this.requestUnit = requestUnit;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getStaffCode() {
		return staffCode;
	}	

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	public String getActualOrderQty() {
		return actualOrderQty;
	}

	public void setActualOrderQty(String actualOrderQty) {
		this.actualOrderQty = actualOrderQty;
	}
	
	public String getSubmittedToInventory() {
		return submittedToInventory;
	}
	
	public void setSubmittedToInventory(String submittedToInventory) {
		this.submittedToInventory = submittedToInventory;
	}
	
	public String getFullItem() {
		return fullItem;
	}
	
	public void setFullItem(String fullItem) {
		this.fullItem = fullItem;
	}

	public String getScanned() {
		return scanned;
	}

	public void setScanned(String scanned) {
		this.scanned = scanned;
	}

	public String getScannedDate() {
		return scannedDate;
	}

	public void setScannedDate(String scannedDate) {
		this.scannedDate = scannedDate;
	}

	public String getPurchaseApprovalCode() {
		return purchaseApprovalCode;
	}
	
	public void setPurchaseApprovalCode(String purchaseApprovalCode) {
		this.purchaseApprovalCode = purchaseApprovalCode;
	}

	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getReturnedUnits() {
		return returnedUnits;
	}
	
	public void setReturnedUnits(String returnedUnits) {
		this.returnedUnits = returnedUnits;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	private PurchaseOrder purchaseOrder;
	public PurchaseOrder getPurchaseOrder() {
		return this.purchaseOrder;
	}
	
	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	
}
