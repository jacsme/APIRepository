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
@Table(name = "WOMDBPR.tblreceivinginvoice")
public class ReceivingInvoice implements Serializable{

	public ReceivingInvoice(){}
	public ReceivingInvoice(BigInteger id, String invoicecode, String purchaseordercode, String jobid, 
			String staffcode, String duedate){
		this.id = id;
		this.invoiceCode = invoicecode;
		this.purchaseOrderCode = purchaseordercode;
		this.jobId = jobid;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.dueDate = currdatenow;
		this.invoiceDate = currdatenow;
		this.status = "Pending";
		this.staffCode = staffcode;
		this.imported = "NO";
	}
	
	private static final long serialVersionUID = 3448399621921887377L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator = "idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "InvoiceCode")
	private String invoiceCode;
	
	@Column(name = "PurchaseOrderCode")
	private String purchaseOrderCode;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name = "InvoiceNumber")
	private String invoiceNumber;
	
	@Column(name = "InvoiceDate")
	private String invoiceDate;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "DueDate")
	private String dueDate;
	
	@Column(name = "Imported")
	private String imported;

	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}

	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
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
	
	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getImported() {
		return imported;
	}

	public void setImported(String imported) {
		this.imported = imported;
	}
}
