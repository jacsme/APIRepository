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
@Table(name = "WOMDBPR.tblinventory")
public class Inventory implements Serializable{

	public Inventory(){}
	public Inventory(BigInteger id, String sourcecode, String productcode, String storecode, String unitquantity, String stocklocation, 
			String unit, String unitfrom, String source, String staffcode, String stockcode, String status, String comments){
		this.id = id;
		this.sourceCode = sourcecode;
		this.productCode = productcode;
		this.storeCode = storecode;
		this.stockLocation = stocklocation;
		this.inventorySource = source;
		this.unitQuantity = unitquantity;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.transactionDate = currdatenow;
		this.staffCode = staffcode;
		this.stockCode = stockcode;
		this.status = status;
		this.comments = comments;
		
		if (unitfrom.equalsIgnoreCase("GR")){ this.pOUnit = unit; this.sOUnit = "0"; this.pOReturnUnit = "0"; this.sOReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("STI")){ this.pOUnit = unit; this.sOUnit = "0"; this.pOReturnUnit = "0"; this.sOReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("SO")){ this.pOUnit = "0"; this.sOUnit = unit; this.pOReturnUnit = "0"; this.sOReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("SPW")){ this.pOUnit = "0"; this.sOUnit = "0"; this.pOReturnUnit = unit; this.sOReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("RFC")){ this.pOUnit = "0"; this.sOUnit = "0"; this.pOReturnUnit = "0"; this.sOReturnUnit = unit;}
	}
	
	public Inventory(String sourcecode, String productcode, String storecode, String unitquantity, String stocklocation, 
			String unit, String unitfrom, String source, String staffcode, String stockcode, String status, String comments){
		this.sourceCode = sourcecode;
		this.productCode = productcode;
		this.storeCode = storecode;
		this.stockLocation = stocklocation;
		this.inventorySource = source;
		this.unitQuantity = unitquantity;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.transactionDate = currdatenow;
		this.staffCode = staffcode;
		this.stockCode = stockcode;
		this.status = status;
		this.comments = comments;
		
		if (unitfrom.equalsIgnoreCase("GR")){ this.pOUnit = unit; this.sOUnit = "0"; this.pOReturnUnit = "0"; this.sOReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("STI")){ this.pOUnit = unit; this.sOUnit = "0"; this.pOReturnUnit = "0"; this.sOReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("SO")){ this.pOUnit = "0"; this.sOUnit = unit; this.pOReturnUnit = "0"; this.sOReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("SPW")){ this.pOUnit = "0"; this.sOUnit = "0"; this.pOReturnUnit = unit; this.sOReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("RFC")){ this.pOUnit = "0"; this.sOUnit = "0"; this.pOReturnUnit = "0"; this.sOReturnUnit = unit;}
	}
	
	private static final long serialVersionUID = -4254178289210999297L;

	@Id
	//@GenericGenerator(name="idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "SourceCode")
	private String sourceCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "UnitQuantity")
	private String unitQuantity;
	
	@Column(name = "POUnit")
	private String pOUnit;
	
	@Column(name = "POReturnUnit")
	private String pOReturnUnit;
	
	@Column(name = "SOUnit")
	private String sOUnit;
	
	@Column(name = "SOReturnUnit")
	private String sOReturnUnit;
	
	@Column(name = "InventorySource")
	private String inventorySource;
	
	@Column(name = "TransactionDate")
	private String transactionDate;
	
	@Column(name = "StockLocation")
	private String stockLocation;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "StockCode")
	private String stockCode;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name = "Requested")
	private String requested;
	
	@Column(name = "Comments")
	private String comments;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	
	public String getUnitQuantity() {
		return unitQuantity;
	}
	
	public void setUnitQuantity(String unitQuantity) {
		this.unitQuantity = unitQuantity;
	}
	
	public String getpOUnit() {
		return pOUnit;
	}

	public void setpOUnit(String pOUnit) {
		this.pOUnit = pOUnit;
	}

	public String getpOReturnUnit() {
		return pOReturnUnit;
	}

	public void setpOReturnUnit(String pOReturnUnit) {
		this.pOReturnUnit = pOReturnUnit;
	}

	public String getsOUnit() {
		return sOUnit;
	}

	public void setsOUnit(String sOUnit) {
		this.sOUnit = sOUnit;
	}

	public String getsOReturnUnit() {
		return sOReturnUnit;
	}

	public void setsOReturnUnit(String sOReturnUnit) {
		this.sOReturnUnit = sOReturnUnit;
	}

	
	public String getInventorySource() {
		return inventorySource;
	}
	
	public void setInventorySource(String inventorySource) {
		this.inventorySource = inventorySource;
	}
	
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStockLocation() {
		return stockLocation;
	}

	public void setStockLocation(String stockLocation) {
		this.stockLocation = stockLocation;
	}
	
	public String getStaffCode() {
		return staffCode;
	}
	
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	public String getStockCode() {
		return stockCode;
	}
	
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getRequested() {
		return requested;
	}
	
	public void setRequested(String requested) {
		this.requested = requested;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
