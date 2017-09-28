package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WOMDBPR.tblproductquotation")
public class ProductQuotation implements Serializable{

	private static final long serialVersionUID = -7335129011343325154L;
	
	public ProductQuotation(){}
	public ProductQuotation(BigInteger id, String itemBudgetCode, String supplierCode, String productCode, 
			String packingUnit, String packingPrice, String packingQuantity, String moq, String gst,
			String shippingDays, String packingpricewithgst){
		this.id = id;
		this.itemBudgetCode = itemBudgetCode;
		this.supplierCode=supplierCode;
		this.productCode=productCode;
		this.packingUnit=packingUnit;
		this.packingPrice=packingPrice;
		this.packingQuantity=packingQuantity;
		this.moq=moq;
		this.gst=gst;
		this.shippingDays=shippingDays;
		this.packingPriceWithGST = packingpricewithgst;
	}
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "ItemBudgetCode")
	private String itemBudgetCode;
	
	@Column(name = "SupplierCode")
	private String supplierCode;

	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "PackingUnit")
	private String packingUnit;
	
	@Column(name = "PackingPrice")
	private String packingPrice;
	
	@Column(name = "PackingQuantity")
	private String packingQuantity;
	
	@Column(name = "MOQ")
	private String moq;
	
	@Column(name = "GST")
	private String gst;
	
	@Column(name = "ShippingDate")
	private String shippingDays;
	
	@Column(name = "PackingPriceWithGST")
	private String packingPriceWithGST;

	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getItemBudgetCode() {
		return itemBudgetCode;
	}

	public void setItemBudgetCode(String itemBudgetCode) {
		this.itemBudgetCode = itemBudgetCode;
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

	public String getPackingUnit() {
		return packingUnit;
	}

	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}

	public String getPackingPrice() {
		return packingPrice;
	}

	public void setPackingPrice(String packingPrice) {
		this.packingPrice = packingPrice;
	}

	public String getPackingQuantity() {
		return packingQuantity;
	}

	public void setPackingQuantity(String packingQuantity) {
		this.packingQuantity = packingQuantity;
	}

	public String getMoq() {
		return moq;
	}

	public void setMoq(String moq) {
		this.moq = moq;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getShippingDays() {
		return shippingDays;
	}

	public void setShippingDays(String shippingDays) {
		this.shippingDays = shippingDays;
	}
	
	public String getPackingPriceWithGST() {
		return packingPriceWithGST;
	}
	
	public void setPackingPriceWithGST(String packingPriceWithGST) {
		this.packingPriceWithGST = packingPriceWithGST;
	}

}
