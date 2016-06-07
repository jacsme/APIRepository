package com.wom.api.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

public interface ReceivingInvoiceService {
	
	public JSONArray getReceivingInvoice(String suppliercode, String jobid, String staffcode) throws Exception;
	public JSONArray getReceivingDeliveryOrder(String suppliercode, String jobid, String staffcode) throws Exception;
	public JSONArray receiveDeliveryOrder(HttpServletRequest request, HttpServletResponse response, String invoicecode, String purchaseordercode, String jobid, String staffcode, String suppliercode, List<String> deliveryorderlist) throws Exception;
	public JSONArray importToAccount(String jobid, String storecode, String rinvoicecode, String purchaseordercode,
			String suppliercode, String purchasedate, String staffcode, String invoicenumber, String duedate, String subtotalamount, String totalgst, String maintotal
			) throws Exception;
	public JSONArray getStockingProduct() throws Exception;
	public JSONArray submitScannedProduct(String purchaseordercode, String productcode, String stocklocation, String productsource, 
			String submittedunit, String staffcode, String storecode, String storelocation, String jobid) throws Exception;
	public JSONArray updatestockingproductjob(String productsource, String jobid, String productcode, 
			String staffcode) throws Exception;
	public JSONArray getGRSummary(String jobid, String productcode, String staffcode) throws Exception;
}
