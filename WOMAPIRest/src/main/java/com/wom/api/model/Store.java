package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblstore")
public class Store implements Serializable {
	
	public Store(){}
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "StoreName")
	private String storeName;
	
	@Column(name = "Address")
	private String address;
	
	@Column(name = "ContactNumber")
	private String contactNumber;
	
	@Column(name = "Website")
	private String website;
	
	@Column(name = "Fax")
	private String Fax;
	
	@Column(name = "GSTid")
	private String gst;
	
	@Column(name = "Active")
	private String active;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}
