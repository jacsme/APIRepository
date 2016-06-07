package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "WOMDBPR.tblcustomercart")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomerCart implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public CustomerCart(){}
	public CustomerCart(BigInteger id, String productcode, String quantity, String price, String customercode){
		this.id = id;
		this.productCode = productcode;
		this.quantity = quantity;
		this.price = price;
		this.customerCode = customercode;
		this.status = "Active";
		this.returnQuantity = "0";
		this.returnPrice = "0";
	}
	public CustomerCart(BigInteger id, String productcode, String quantity, String price, String returnquantity, String returnprice, String customercode, String status) {
		this.id = id;
		this.productCode = productcode;
		this.quantity = quantity;
		this.price = price;
		this.returnQuantity = returnquantity;
		this.returnPrice = returnprice;
		this.customerCode = customercode;
		this.status = status;
	}
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "Quantity")
	private String quantity;
	
	@Column(name = "Price")
	private String price;
	
	@Column(name = "ReturnQuantity")
	private String returnQuantity;
	
	@Column(name = "ReturnPrice")
	private String returnPrice;
	
	@Column(name = "CustomerCode")
	private String customerCode;
	
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReturnQuantity() {
		return returnQuantity;
	}
	
	public void setReturnQuantity(String returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	public String getReturnPrice() {
		return returnPrice;
	}
	public void setReturnPrice(String returnPrice) {
		this.returnPrice = returnPrice;
	}
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
