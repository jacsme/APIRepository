package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WOMDBPR.tbldelivery")
public class Delivery implements Serializable{

	public Delivery(){}
	public Delivery(BigInteger id, String truckcode, String deliverycode, String staffcode){
		this.id = id;
		this.truckCode = truckcode;
		this.deliveryCode = deliverycode;
		this.deliveredBy = staffcode;
		this.status = "On Delivery";
	}
	
	private static final long serialVersionUID = -4881491582670255334L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator = "idgen") 
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "DeliveryCode")
	private String deliveryCode;
	
	@Column(name = "DeliveredDate")
	private String deliveredDate;
	
	@Column(name = "DeliveredBy")
	private String deliveredBy;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "TruckCode")
	private String truckCode;

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
	
	public String getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTruckCode() {
		return truckCode;
	}
	
	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}
	
	/**
	@OneToMany(fetch = FetchType.LAZY, targetEntity = BoxDelivery.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "deliveryCode", referencedColumnName = "deliveryCode")
	
	private List<BoxDelivery> boxdelivery;

	public List<BoxDelivery> getBoxdelivery() {
		return boxdelivery;
	}
	public void setBoxdelivery(List<BoxDelivery> boxdelivery) {
		this.boxdelivery = boxdelivery;
	}
	**/
}
