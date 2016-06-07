package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WOMDBPR.tblboxdelivery")
public class BoxDelivery implements Serializable{

	public BoxDelivery(){}
	public BoxDelivery(BigInteger id, String salesordercode, String productcode, String boxcode, String quantity, String status){
		this.id = id;
		this.salesOrderCode = salesordercode;
		this.productCode = productcode;
		this.quantity = quantity;
		this.boxCode = boxcode;
		this.status = status;
	}
	
	private static final long serialVersionUID = -4881491582670255334L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator = "idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "BoxCode")
	private String boxCode;
	
	@Column(name = "DeliveryCode")
	private String deliveryCode;
	
	@Column(name = "TruckRackingCode")
	private String truckRackingCode;
	
	@Column(name = "Area")
	private String area;
	
	@Column(name = "BoxWeight")
	private String boxWeight;
	
	@Column(name = "ScannedBy")
	private String scannedBy;
	
	@Column(name = "Status")
	private String status;

	@Column(name = "SalesOrderCode")
	private String salesOrderCode;
	
	@Column(name = "Quantity")
	private String quantity;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(String boxWeight) {
		this.boxWeight = boxWeight;
	}

	public String getScannedBy() {
		return scannedBy;
	}

	public void setScannedBy(String scannedBy) {
		this.scannedBy = scannedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTruckRackingCode() {
		return truckRackingCode;
	}
	
	public void setTruckRackingCode(String truckRackingCode) {
		this.truckRackingCode = truckRackingCode;
	}
	
	public String getSalesOrderCode() {
		return salesOrderCode;
	}
	
	public void setSalesOrderCode(String salesOrderCode) {
		this.salesOrderCode = salesOrderCode;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
	
}
