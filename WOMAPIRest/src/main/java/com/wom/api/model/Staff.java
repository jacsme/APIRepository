package com.wom.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "WOMDBPR.tblstaff")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Staff implements Serializable {

	private static final long serialVersionUID = 1L;
		
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long Id;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "StaffName")
	private String staffName;
	
	@Column(name = "DateOfBirth")
	private Date dateOfBirth;
	
	@Column(name = "DateHired")
	private Date dateHired;
	
	@Column(name = "Position")
	private String position;
	
	@Column(name = "Ratings")
	private String ratings;
	
	@Column(name = "Attendance")
	private String attendance;
	
	@Column(name = "Salary")
	private String salary;
	
	@Column(name = "BalanceLeave")
	private String balanceLeave;
	
	@Column(name = "CompanyItemAsset")
	private String companyItemAsset;
	
	@Column(name = "SubmitClaim")
	private String submitClaim;
	
	@Column(name = "ReportingTo")
	private String reportingTo;

	public long getId() {
		return Id;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getBalanceLeave() {
		return balanceLeave;
	}

	public void setBalanceLeave(String balanceLeave) {
		this.balanceLeave = balanceLeave;
	}

	public String getCompanyItemAsset() {
		return companyItemAsset;
	}

	public void setCompanyItemAsset(String companyItemAsset) {
		this.companyItemAsset = companyItemAsset;
	}

	public String getSubmitClaim() {
		return submitClaim;
	}

	public void setSubmitClaim(String submitClaim) {
		this.submitClaim = submitClaim;
	}
}
