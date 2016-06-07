package com.wom.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "WOMDBPR.tblstockproducts")
public class StockProducts implements Serializable{

	public StockProducts(){}
	public StockProducts(String stockcode, String productcode, String availableunit, 
			String stockLocation, String soldunit){
		this.stockCode = stockcode;
		this.productCode = productcode;
		this.availableUnit = availableunit;
		this.stockLocation = stockLocation;
		this.soldUnit = soldunit;
	}
	public StockProducts(String stockcode, String productcode, String accumulatedunit, String availableunit, 
			String purchaseunit, String stockLocation, String submittedunit, String remainingunit, String storecode){
		this.stockCode = stockcode;
		this.productCode = productcode;
		this.accumulatedUnit = accumulatedunit;
		this.availableUnit = availableunit;
		this.lastPurchaseUnit = purchaseunit;
		this.stockLocation = stockLocation;
		this.submittedUnit = submittedunit;
		this.remainingUnit = remainingunit;
		this.storeCode = storecode;
	}
	private static final long serialVersionUID = -4254178289210999297L;

	@Id
	@GenericGenerator(name="idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long id;
	
	@Column(name = "StockCode")
	private String stockCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "AccumulatedUnit")
	private String accumulatedUnit;
	
	@Column(name = "AvailableUnit")
	private String availableUnit;
	
	@Column(name = "SoldUnit")
	private String soldUnit;
	
	@Column(name = "LastPurchaseUnit")
	private String lastPurchaseUnit;
	
	@Column(name = "StockLocation")
	private String stockLocation;
	
	@Column(name = "SubmittedUnit")
	private String submittedUnit;
	
	@Column(name = "RemainingUnit")
	private String remainingUnit;
	
	@Column(name = "StoreCode")
	private String storeCode;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getAccumulatedUnit() {
		return accumulatedUnit;
	}
	public void setAccumulatedUnit(String accumulatedUnit) {
		this.accumulatedUnit = accumulatedUnit;
	}
	public String getAvailableUnit() {
		return availableUnit;
	}
	public void setAvailableUnit(String availableUnit) {
		this.availableUnit = availableUnit;
	}
	public String getSoldUnit() {
		return soldUnit;
	}
	public void setSoldUnit(String soldUnit) {
		this.soldUnit = soldUnit;
	}
	public String getLastPurchaseUnit() {
		return lastPurchaseUnit;
	}
	public void setLastPurchaseUnit(String lastPurchaseUnit) {
		this.lastPurchaseUnit = lastPurchaseUnit;
	}
	public String getStockLocation() {
		return stockLocation;
	}
	public void setStockLocation(String stockLocation) {
		this.stockLocation = stockLocation;
	}
	public String getSubmittedUnit() {
		return submittedUnit;
	}
	public void setSubmittedUnit(String submittedUnit) {
		this.submittedUnit = submittedUnit;
	}
	public String getRemainingUnit() {
		return remainingUnit;
	}
	public void setRemainingUnit(String remainingUnit) {
		this.remainingUnit = remainingUnit;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	
}
