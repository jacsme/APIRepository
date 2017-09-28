package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblsalesorderdetails")
public class SalesOrderDetails implements Serializable {
	
	private static final long serialVersionUID = 5279115345189984594L;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name = "SalesOrderCode")
	private String salesOrderCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "Price")
	private String price;
	
	@Column(name = "Quantity")
	private String quantity;
	
	@Column(name = "BoxCode")
	private String boxCode;
	
	@Column(name = "GST")
	private String gst;
	
	@Column(name = "Discount")
	private String discount;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "ReturnQuantity")
	private String returnQuantity;
	
	@Column(name = "ReturnPrice")
	private String returnPrice;
	
	@Column(name = "ReturnGST")
	private String returnGST;
	
	@Column(name = "ReturnDiscount")
	private String returnDiscount;
	
	//@Transient
	//private String type;
			
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSalesOrderCode() {
		return salesOrderCode;
	}
	public void setSalesOrderCode(String salesOrderCode) {
		this.salesOrderCode = salesOrderCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBoxCode() {
		return boxCode;
	}
	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getGst() {
		return gst;
	}
	
	public void setGst(String gst) {
		this.gst = gst;
	}
	
	public String getDiscount() {
		return discount;
	}
	
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getReturnGST() {
		return returnGST;
	}
	
	public void setReturnGST(String returnGST) {
		this.returnGST = returnGST;
	}
	
	public String getReturnDiscount() {
		return returnDiscount;
	}
	
	public void setReturnDiscount(String returnDiscount) {
		this.returnDiscount = returnDiscount;
	}
	
	@Override
	public String toString(){
		String mystring = "salesOrderCode=" + salesOrderCode + "&" + "productCode=" + productCode  
				 + "&" + "boxCode =" + boxCode + "&" + "price=" + price + "&" + "quantity=" + quantity 
				 + "&" + "gst=" + gst + "&" + "discount=" + discount + "&" + "returnQuantity=" + returnQuantity
				 + "&" + "returnPrice=" + returnPrice + "&" + "returnGST=" + returnGST + "&" + "returnDiscount=" + returnDiscount;
		
		return mystring;
	}
}
