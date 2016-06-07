package com.wom.api.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.api.util.HelperUtil;

@Entity
@Table(name = "WOMDBPR.tblpurchaserequest")
public class PurchaseRequest implements Serializable{

	public PurchaseRequest(){}
	public PurchaseRequest(BigInteger id, String requestcode, String itembudgetcode, String suppliercode, String storecode, String productcode,
			String requestquantity, String requestunit, String requesttotalunit, String requestpackingprice, 
			String gst, String requesttotalamount, String paymentTerms, String requesttotalamountgst,
			String requestedpackingweight, String requestedpackingmass, String shippingdate){
		this.id = id;
		this.requestCode = requestcode;
		this.itemBudgetCode = itembudgetcode;
		this.supplierCode = suppliercode;
		this.storeCode = storecode;
		this.productCode = productcode;
		this.requestQuantity = requestquantity;
		this.requestUnit = requestunit;
		this.requestTotalUnit = requesttotalunit;
		this.requestPackingPrice = requestpackingprice;
		this.gst = gst;
		this.requestTotalAmount = requesttotalamount;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.requestDate = currdatenow;
		this.paymentTerms=paymentTerms;
		this.requestTotalAmountGST = requesttotalamountgst;
		this.requestedPackingWeight = requestedpackingweight;
		this.requestedPackingMass = requestedpackingmass;
		this.shippingDate = shippingdate;
	}
	private static final long serialVersionUID = 3071963721790232308L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "RequestCode")
	private String requestCode;
	
	@Column(name = "ItemBudgetCode")
	private String itemBudgetCode;
	
	@Column(name = "SupplierCode")
	private String supplierCode;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "RequestQuantity")
	private String requestQuantity;

	@Column(name = "RequestUnit")
	private String requestUnit;
	
	@Column(name = "RequestTotalUnit")
	private String requestTotalUnit;
	
	@Column(name = "RequestPackingPrice")
	private String requestPackingPrice;
	
	@Column(name = "GST")
	private String gst;
	
	@Column(name = "RequestTotalAmount")
	private String requestTotalAmount;
	
	@Column(name = "RequestDate")
	private String requestDate;
	
	@Column(name = "PaymentTerms")
	private String paymentTerms;
	
	@Column(name = "RequestTotalAmountGST")
	private String requestTotalAmountGST;
	
	@Column(name = "PurchaseOrderCode")
	private String purchaseOrderCode;
	
	@Column(name = "RequestedPackingWeight")
	private String requestedPackingWeight;
	
	@Column(name = "RequestedPackingMass")
	private String requestedPackingMass;
	
	@Column(name = "ShippingDate")
	private String shippingDate;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getRequestQuantity() {
		return requestQuantity;
	}

	public void setRequestQuantity(String requestQuantity) {
		this.requestQuantity = requestQuantity;
	}

	public String getRequestTotalUnit() {
		return requestTotalUnit;
	}

	public void setRequestTotalUnit(String requestTotalUnit) {
		this.requestTotalUnit = requestTotalUnit;
	}

	public String getRequestPackingPrice() {
		return requestPackingPrice;
	}

	public void setRequestPackingPrice(String requestPackingPrice) {
		this.requestPackingPrice = requestPackingPrice;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getRequestTotalAmount() {
		return requestTotalAmount;
	}

	public void setRequestTotalAmount(String requestTotalAmount) {
		this.requestTotalAmount = requestTotalAmount;
	}
	
	public String getRequestUnit() {
		return requestUnit;
	}
	
	public void setRequestUnit(String requestUnit) {
		this.requestUnit = requestUnit;
	}
	
	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
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
	
	public String getStoreCode() {
		return storeCode;
	}
	
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	
	public String getRequestTotalAmountGST() {
		return requestTotalAmountGST;
	}
	
	public void setRequestTotalAmountGST(String requestTotalAmountGST) {
		this.requestTotalAmountGST = requestTotalAmountGST;
	}
	
	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}
	
	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}
	
	public String getRequestedPackingWeight() {
		return requestedPackingWeight;
	}
	
	public void setRequestedPackingWeight(String requestedPackingWeight) {
		this.requestedPackingWeight = requestedPackingWeight;
	}
	
	public String getRequestedPackingMass() {
		return requestedPackingMass;
	}
	
	public void setRequestedPackingMass(String requestedPackingMass) {
		this.requestedPackingMass = requestedPackingMass;
	}
	
	public String getShippingDate() {
		return shippingDate;
	}
	
	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
	
	
	
}
