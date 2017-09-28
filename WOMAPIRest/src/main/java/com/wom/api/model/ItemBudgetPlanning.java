package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WOMDBPR.tblitembudgetplanning")
public class ItemBudgetPlanning implements Serializable{

	public ItemBudgetPlanning(){}
	public ItemBudgetPlanning(BigInteger id, String itembudgetcode, String stockcontrolcode, String jobid, String productcode, 
			String amount, String staffcode, String itembudgetdate){
		this.id = id;
		this.itemBudgetCode = itembudgetcode;
		this.stockControlCode= stockcontrolcode;
		this.jobId = jobid;
		this.productCode = productcode;
		this.amount = amount;
		this.staffCode = staffcode;
		this.itemBudgetDate = itembudgetdate;
	}
	
	private static final long serialVersionUID = -388526296605834392L;
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "ItemBudgetCode")
	private String itemBudgetCode;
	
	@Column(name = "StockControlCode")
	private String stockControlCode;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name="ProductCode")
	private String productCode;
	
	@Column(name = "Amount")
	private String amount;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "ItemBudgetDate")
	private String itemBudgetDate;
		
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getItemBudgetCode() {
		return itemBudgetCode;
	}

	public void setItemBudgetCode(String itemBudgetCode) {
		this.itemBudgetCode = itemBudgetCode;
	}

	public String getStockControlCode() {
		return stockControlCode;
	}

	public void setStockControlCode(String stockControlCode) {
		this.stockControlCode = stockControlCode;
	}

	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getItemBudgetDate() {
		return itemBudgetDate;
	}

	public void setItemBudgetDate(String itemBudgetDate) {
		this.itemBudgetDate = itemBudgetDate;
	}
	
	
}
