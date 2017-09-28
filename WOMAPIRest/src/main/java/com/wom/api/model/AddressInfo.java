package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WOMDBPR.tbladdress")
public class AddressInfo implements Serializable{

	public AddressInfo(){}
	public AddressInfo(BigInteger id, String addresscode, String customercode, String addressinfo, String googleaddress, String addresstype){
		this.id = id;
		this.addressCode = addresscode;
		this.customerCode = customercode;
		this.addressInfo = addressinfo;
		this.googleaddress = googleaddress;
		this.active = "YES";
		this.addressType = addresstype;
	}
	
	public AddressInfo(BigInteger id, String addresscode, String customercode, String addressinfo, String postcode, String state, String city, String	area,
			String street, String number, String building, String unit, String addresstype, String googleaddress){
		this.id = id;
		this.addressCode = addresscode;
		this.customerCode = customercode;
		this.addressInfo = addressinfo;
		this.postCode = postcode;
		this.state = state;
		this.city = city;
		this.area = area;
		this.street = street;
		this.number = number;
		this.building = building;
		this.unit = unit;
		this.addressType = addresstype;
		this.googleaddress = googleaddress;
		this.active = "YES";
	}
	
	private static final long serialVersionUID = -339044771051453335L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "CustomerCode")
	private String customerCode;
	
	@Column(name = "AddressCode")
	private String addressCode;
	
	@Column(name = "AddressInfo")
	private String addressInfo;

	@Column(name = "GoogleAddress")
	private String googleaddress;
	
	@Column(name = "Active")
	private String active;
	
	@Column(name = "AddressType")
	private String addressType;
	
	@Column(name = "PostCode")
	private String postCode;

	@Column(name = "State")
	private String state;
	
	@Column(name = "City")
	private String city;
	
	@Column(name = "Area")
	private String area;
	
	@Column(name = "Street")
	private String street;
	
	@Column(name = "Number")
	private String number;
	
	@Column(name = "Building")
	private String building;
	
	@Column(name = "UnitNo")
	private String unit;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String getAddressType() {
		return addressType;
	}
	
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	
	public String getGoogleaddress() {
		return googleaddress;
	}
	
	public void setGoogleaddress(String googleaddress) {
		this.googleaddress = googleaddress;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getBuilding() {
		return building;
	}
	
	public void setBuilding(String building) {
		this.building = building;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
