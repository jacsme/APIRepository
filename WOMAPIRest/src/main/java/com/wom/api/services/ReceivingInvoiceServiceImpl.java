package com.wom.api.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.api.dao.ReceivingInvoiceDao;

public class ReceivingInvoiceServiceImpl implements ReceivingInvoiceService{

	@Autowired
	ReceivingInvoiceDao receivinginvoiceDao;
	
	@Override
	public JSONArray getReceivingInvoice(String suppliercode, String jobid, String staffcode) throws Exception {
		return receivinginvoiceDao.getReceivingInvoice(suppliercode, jobid, staffcode);
	}
	
	@Override
	public JSONArray getReceivingDeliveryOrder(String suppliercode, String jobid, String staffcode) throws Exception{
		return receivinginvoiceDao.getReceivingDeliveryOrder(suppliercode, jobid, staffcode);
	}
	
	@Override
	public JSONArray receiveDeliveryOrder(HttpServletRequest request, HttpServletResponse response, String invoicecode, String purchaseordercode, String jobid, String staffcode, String suppliercode, List<String> deliveryorderlist) throws Exception{
		return receivinginvoiceDao.receiveDeliveryOrder(request, response, invoicecode, purchaseordercode, jobid, staffcode, suppliercode, deliveryorderlist);
	}
	
	@Override
	public JSONArray importToAccount(String jobid, String storecode, String rinvoicecode, String purchaseordercode,
			String suppliercode, String purchasedate, String staffcode, String invoicenumber, String duedate, String subtotalamount, String totalgst, String maintotal
			) throws Exception{
		return receivinginvoiceDao.importToAccount(jobid, storecode, rinvoicecode, purchaseordercode, suppliercode, purchasedate, staffcode, invoicenumber, duedate, 
				subtotalamount, totalgst, maintotal);
		
	}
	
	@Override
	public JSONArray getStockingProduct() throws Exception{
		return receivinginvoiceDao.getStockingProduct();
	}
	
	@Override
	public JSONArray submitScannedProduct(String purchaseordercode, String productcode, String stocklocation, String productsource, String submittedunit, String staffcode, String storecode, String storelocation, String jobid) throws Exception{
		return receivinginvoiceDao.submitScannedProduct(purchaseordercode, productcode, stocklocation, productsource, submittedunit, staffcode, storecode, storelocation, jobid);
	}

	@Override
	public JSONArray updatestockingproductjob(String productsource, String jobid, String productcode, String staffcode)
			throws Exception {
		return receivinginvoiceDao.updatestockingproductjob(productsource, jobid, productcode, staffcode);
	}
	
	@Override
	public JSONArray getGRSummary(String jobid, String productcode, String staffcode) throws Exception{
		return receivinginvoiceDao.getGRSummary(jobid, productcode, staffcode);
	}
}
