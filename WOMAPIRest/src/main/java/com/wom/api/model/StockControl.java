package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.api.util.HelperUtil;

@Entity
@Table(name = "WOMDBPR.tblstockcontrol")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StockControl implements Serializable{
    
	private static final long serialVersionUID = 1L;

	public StockControl(){}
	public StockControl(BigInteger id, String stockcontrolcode, String jobid, String productcode, String storecode, String unit, 
			String staffcode) {
		this.id = id;
		this.stockControlCode = stockcontrolcode;
		this.jobId = jobid;
		this.productCode = productcode;
		this.storeCode = storecode;
		this.stockUnit = unit;
		this.staffCode = staffcode;
		this.status = "Processing";
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.requestDate = currdatenow;
	}
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "StockControlCode")
	private String stockControlCode;
	
	@Column(name = "JobId")
	private String jobId;

	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "StockLevelDays")
	private String stockLeveDays;
	
	@Column(name = "RequestDate")
	private String requestDate;
	
	@Column(name = "StockUnit")
	private String stockUnit;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "Status")
	private String status;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStockLeveDays() {
		return stockLeveDays;
	}

	public void setStockLeveDays(String stockLeveDays) {
		this.stockLeveDays = stockLeveDays;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getStockUnit() {
		return stockUnit;
	}

	public void setStockUnit(String stockUnit) {
		this.stockUnit = stockUnit;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	public String getStockControlCode() {
		return stockControlCode;
	}
	
	public void setStockControlCode(String stockControlCode) {
		this.stockControlCode = stockControlCode;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
