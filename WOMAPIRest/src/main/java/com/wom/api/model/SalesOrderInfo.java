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

@Entity
@Table(name = "WOMDBPR.tblsalesorderinfo")
public class SalesOrderInfo implements Serializable {
	
	private static final long serialVersionUID = -1676651071396517536L;
	public SalesOrderInfo(){}
	public SalesOrderInfo(BigInteger id, String salesordercode, String storecode, String address, String salesorderdate, String paymentmethod,
			String contactnumber, String note, String joblistid, String customercode, String wcpaidamount, String ccpaidamount, 
			String postcode, String deliverydate, String deliverytime, String prioritynumber, String townname, String deliverygroup) {
		this.id = id;
		this.salesOrderCode = salesordercode;
		this.storeCode = storecode;
		this.address = address;
		this.salesDate = salesorderdate;
		this.paymentMethod = paymentmethod;
		this.contactno = contactnumber;
		this.salesNote = note;
		this.jobId = joblistid;
		this.customerCode = customercode;
		this.status = "New Order";
		this.staffCode = "";
		this.wcPaidAmount = wcpaidamount;
		this.ccPaidAmount =ccpaidamount;
		this.postCode = postcode;
		this.deliveryDate = deliverydate;
		this.deliveryTime = deliverytime;
		this.priorityNumber = prioritynumber;
		this.townName = townname;
		this.deliveryGroup = deliverygroup;
	}

	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "SalesOrderCode")
	private String salesOrderCode;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "Address")
	private String address;
	
	@Column(name = "SalesDate")
	private String salesDate;
	
	@Column(name = "PaymentMethod")
	private String paymentMethod;
	
	@Column(name = "ContactNumber")
	private String contactno;
	
	@Column(name = "SalesNote")
	private String salesNote;
	
	@Column(name="Status")
	private String status;
	
	@Column(name="JobId")
	private String jobId;
	
	@Column(name = "CustomerCode")
	private String customerCode;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "WCPaidAmount")
	private String wcPaidAmount;
	
	@Column(name = "CCPaidAmount")
	private String ccPaidAmount;
	
	@Column(name = "RefundAmount")
	private String refundAmount;
	
	@Column(name = "PostCode")
	private String postCode;
	
	@Column(name = "DeliveryDate")
	private String deliveryDate;
	
	@Column(name = "DeliveryTime")
	private String deliveryTime;
	
	@Column(name = "PriorityNumber")
	private String priorityNumber;
	
	@Column(name = "TownName")
	private String townName;
	
	@Column(name = "DeliveryGroup")
	private String deliveryGroup;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getSalesOrderCode() {
		return salesOrderCode;
	}
	public void setSalesOrderCode(String salesOrderCode) {
		this.salesOrderCode = salesOrderCode;
	}
	
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getSalesNote() {
		return salesNote;
	}
	public void setSalesNote(String salesNote) {
		this.salesNote = salesNote;
	}
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getWcPaidAmount() {
		return wcPaidAmount;
	}
	
	public void setWcPaidAmount(String wcPaidAmount) {
		this.wcPaidAmount = wcPaidAmount;
	}
	
	public String getCcPaidAmount() {
		return ccPaidAmount;
	}
	
	public void setCcPaidAmount(String ccPaidAmount) {
		this.ccPaidAmount = ccPaidAmount;
	}
	
	public String getRefundAmount() {
		return refundAmount;
	}
	
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getPriorityNumber() {
		return priorityNumber;
	}
	public void setPriorityNumber(String priorityNumber) {
		this.priorityNumber = priorityNumber;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public String getDeliveryGroup() {
		return deliveryGroup;
	}
	public void setDeliveryGroup(String deliveryGroup) {
		this.deliveryGroup = deliveryGroup;
	}

	@OneToMany(fetch = FetchType.LAZY, targetEntity = SalesOrderDetails.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "salesOrderCode", referencedColumnName = "salesOrderCode")
	
	private List<SalesOrderDetails> salesorderdetails;

	public List<SalesOrderDetails> getSalesorderdetails() {
		return this.salesorderdetails;
	}
	
	public void setSalesorderdetails(List<SalesOrderDetails> salesorderdetails) {
		this.salesorderdetails = salesorderdetails;
	}
	
}
