package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "WOMDBPR.tblsupplier")
public class Supplier implements Serializable{
	public Supplier(){}
	public Supplier(BigInteger id, String suppliercode, String suppliername, String address, String phone, String fax, String website, 
			String email, String contactperson, String gstid, String contactpersonphone){
		this.id= id;
		this.supplierCode = suppliercode;
		this.supplierName = suppliername;
		this.address = address;
		this.phone = phone;
		this.fax = fax;
		this.website = website;
		this.active = "YES";
		this.email = email;
		this.contactPerson = contactperson;
		this.gSTId = gstid;
		this.contactPersonPhone = contactpersonphone;
		
	}
	private static final long serialVersionUID = -7860222614035058667L;
	
	@Id
	//@GenericGenerator(name="idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "SupplierCode")
	private String supplierCode;
	
	@Column(name = "SupplierName")
	private String supplierName;
	
	@Column(name="Address")
	private String address;
	
	@Column(name = "Phone")
	private String phone;
	
	@Column(name = "Fax")
	private String fax;
	
	@Column(name = "Website")
	private String website;
	
	@Column(name = "Active")
	private String active;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "ContactPerson")
	private String contactPerson;
	
	@Column(name = "Country")
	private String country;
	
	@Column(name = "ContactPersonPhone")
	private String contactPersonPhone;	

	@Column(name = "SupplierGSTId")
	private String gSTId;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContactPersonPhone() {
		return contactPersonPhone;
	}
	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}
	public String getgSTId() {
		return gSTId;
	}
	public void setgSTId(String gSTId) {
		this.gSTId = gSTId;
	}
	@OneToMany(fetch = FetchType.LAZY, targetEntity = ProductSupplier.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "supplierCode", referencedColumnName = "supplierCode")
	
	private List<ProductSupplier> productSupplier;

	public List<ProductSupplier> getProductSupplier() {
		return productSupplier;
	}

	public void setProductSupplier(List<ProductSupplier> productSupplier) {
		this.productSupplier = productSupplier;
	}
}
