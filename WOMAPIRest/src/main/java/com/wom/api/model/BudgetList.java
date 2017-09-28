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
@Table(name = "WOMDBPR.tblbudgetlist")
public class BudgetList implements Serializable{
	
	public BudgetList(){}
	public BudgetList(BigInteger id, String storecode, String creditamount, String debitamount, String sourcecode, 
			String fundsource, String staffcode, String type){
		this.id = id;
		this.storeCode = storecode;
		this.creditAmount = creditamount;
		this.debitAmount = debitamount;
		this.sourceCode = sourcecode;
		this.staffCode = staffcode;
		this.fundSource = fundsource;
		this.type = type;
	
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.budgetDate = currdatenow;
		
	}
	private static final long serialVersionUID = -5118721729767379385L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator = "idgen") 
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "BudgetAmount")
	private String budgetAmount;
	
	@Column(name = "BudgetDate")
	private String budgetDate;

	@Column(name = "CreditAmount")
	private String creditAmount;
	
	@Column(name = "DebitAmount")
	private String debitAmount;
	
	@Column(name = "SourceCode")
	private String sourceCode;
	
	@Column(name = "FundSource")
	private String fundSource;
	
	@Column(name = "StaffCode")
	private String staffCode;

	@Column(name = "Type")
	private String type;
	
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

	public String getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public String getBudgetDate() {
		return budgetDate;
	}

	public void setBudgetDate(String budgetDate) {
		this.budgetDate = budgetDate;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getFundSource() {
		return fundSource;
	}
	
	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}
	
	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	

}
