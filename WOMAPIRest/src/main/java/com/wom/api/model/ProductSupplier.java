package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblproductsupplier")
public class ProductSupplier implements Serializable{

	private static final long serialVersionUID = -7860222614035058667L;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name = "SupplierCode")
	private String supplierCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "PackQuantity")
	private String packQuantity;
	
	@Column(name = "PackUnit")
	private String packUnit;
	
	@Column(name = "PackPrice")
	private String packPrice;
	
	@Column(name = "PaymentTerms")
	private String paymentTerms;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getPackQuantity() {
		return packQuantity;
	}

	public void setPackQuantity(String packQuantity) {
		this.packQuantity = packQuantity;
	}

	public String getPackUnit() {
		return packUnit;
	}

	public void setPackUnit(String packUnit) {
		this.packUnit = packUnit;
	}

	public String getPackPrice() {
		return packPrice;
	}

	public void setPackPrice(String packPrice) {
		this.packPrice = packPrice;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	@Override
	public String toString(){
		
		String mystring = "supplierCode=" + supplierCode + "&" + "productCode=" + productCode
				+ "&" + "packQuantity=" + packQuantity + "&" + "packUnit=" + packUnit
				+ "&" + "packPrice=" + packPrice + "&" + "paymentTerms=" + paymentTerms;
		return mystring;
	}

}
