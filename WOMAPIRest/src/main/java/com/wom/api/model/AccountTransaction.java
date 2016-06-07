package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.api.util.HelperUtil;

@Entity
@Table(name = "WOMDBPR.tblaccounttransaction")
public class AccountTransaction implements Serializable {

	public AccountTransaction(){}
	public AccountTransaction(BigInteger id, String storecode, String rinvoicecode, String purchaseordercode,
			String suppliercode, String purchasedate,
			String subtotalamount, String totalgst, String maintotal, String staffcode, String duedate, String invoicenumber){
		this.id = id;
		this.storeCode = storecode;
		this.rinvoiceCode = rinvoicecode;
		this.purchaseOrderCode = purchaseordercode;
		this.supplierCode = suppliercode;
		this.purchaseDate = purchasedate;
		this.purchaseTotalAmount = subtotalamount;
		this.totalGST = totalgst;
		this.purchaseTotalAmountWGST = maintotal;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.transactionDate = currdatenow;
		this.staffCode = staffcode;
		this.dueDate = duedate;
		this.invoiceNumber = invoicenumber;
		this.status = "Pending";
	}
	
	private static final long serialVersionUID = -3327678122720247070L;

	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator = "idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "RInvoiceCode")
	private String rinvoiceCode;
	
	@Column(name = "PurchaseOrderCode")
	private String purchaseOrderCode;
	
	@Column(name = "SupplierCode")
	private String supplierCode;
	
	@Column(name = "PurchaseDate")
	private String purchaseDate;
	
	@Column(name = "TotalGST")
	private String totalGST;
	
	@Column(name = "PurchaseTotalAmount")
	private String purchaseTotalAmount;
	
	@Column(name = "PurchaseTotalAmountWGST")
	private String purchaseTotalAmountWGST;
	
	@Column(name = "AmountPaid")
	private String amountPaid;
	
	@Column(name = "TransactionDate")
	private String transactionDate;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "DueDate")
	private String dueDate;
	
	@Column(name = "PaymentDate")
	private String paymentDate;
	
	@Column(name = "Status")
	private String status;

	@Column(name = "InvoiceNumber")
	private String invoiceNumber;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
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

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getPurchaseTotalAmount() {
		return purchaseTotalAmount;
	}

	public void setPurchaseTotalAmount(String purchaseTotalAmount) {
		this.purchaseTotalAmount = purchaseTotalAmount;
	}

	public String getPurchaseTotalAmountWGST() {
		return purchaseTotalAmountWGST;
	}
	
	public void setPurchaseTotalAmountWGST(String purchaseTotalAmountWGST) {
		this.purchaseTotalAmountWGST = purchaseTotalAmountWGST;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTotalGST() {
		return totalGST;
	}
	
	public void setTotalGST(String totalGST) {
		this.totalGST = totalGST;
	}
	
	public String getRinvoiceCode() {
		return rinvoiceCode;
	}
	
	public void setRinvoiceCode(String rinvoiceCode) {
		this.rinvoiceCode = rinvoiceCode;
	}
	
	public String getAmountPaid() {
		return amountPaid;
	}
	
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	public String getPaymentDate() {
		return paymentDate;
	}
	
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	
	
	
}
