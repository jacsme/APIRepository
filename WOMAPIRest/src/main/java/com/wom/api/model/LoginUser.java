package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "WOMDBPR.tbllogin")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoginUser implements Serializable {

	public LoginUser(){}
	public LoginUser(BigInteger id, String usercode, String password, String emailaddress, String app){
		this.id = id;
		this.userCode = usercode;
		this.password = password;
		this.emailAddress = emailaddress;
		this.app = app;
		this.active = "NO";
	}
	private static final long serialVersionUID = 1L;

	@Id
	//@GenericGenerator(name="idgen", strategy="increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "UserCode")
	private String userCode;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "EmailAddress")
	private String emailAddress;

	@Column(name = "Active")
	private String active;
	
	@Column(name = "App")
	private String app;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String getApp() {
		return app;
	}
	
	public void setApp(String app) {
		this.app = app;
	}
	
	
	
	/**
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "loginUser")
	@Cascade(CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	
	public Customer membership;

	public Customer getMembership() {
		return membership;
	}

	public void setMembership(Customer membership) {
		this.membership = membership;
	}
	**/
	
}
