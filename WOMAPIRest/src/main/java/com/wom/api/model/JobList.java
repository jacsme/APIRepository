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
@Table(name = "WOMDBPR.tbljoblist")
public class JobList implements Serializable {

	public JobList(){}
	public JobList(BigInteger id, String jobid, String productcode, String suppliercode, String departmentcode, String jobtype, String staffcode){
		this.id = id;
		this.jobId = jobid;
		this.productCode = productcode;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.dateCreated = currdatenow;
		this.supplierCode = suppliercode;
		this.departmentCode =departmentcode;
		this.jobType = jobtype;
		this.staffCode = staffcode;
	}
	
	private static final long serialVersionUID = 3638282269810150000L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator = "idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "DateCreated")
	private String dateCreated;
	
	@Column(name = "SupplierCode")
	private String supplierCode;

	@Column(name = "DepartmentCode")
	private String departmentCode;

	@Column(name = "JobType")
	private String jobType;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
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

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public String getJobtype() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	
	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	/**
	@OneToMany(fetch = FetchType.LAZY, targetEntity = StockPlanning.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "jobid", referencedColumnName = "JobId")
	
	private List<StockPlanning> stockplanning;
	public List<StockPlanning> getStockplanning() {
		return stockplanning;
	}

	public void setStockplanning(List<StockPlanning> stockplanning) {
		this.stockplanning = stockplanning;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = ItemBudgetPlanning.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "jobId", referencedColumnName = "jobid")
	
	private ItemBudgetPlanning itembudgetplanning;
	public ItemBudgetPlanning getItembudgetplanning() {
		return itembudgetplanning;
	}

	public void setItembudgetplanning(ItemBudgetPlanning itembudgetplanning) {
		this.itembudgetplanning = itembudgetplanning;
	}**/
}
