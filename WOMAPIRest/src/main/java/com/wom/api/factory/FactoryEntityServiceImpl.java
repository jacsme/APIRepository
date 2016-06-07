package com.wom.api.factory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wom.api.constant.MainEnum;
import com.wom.api.util.HelperUtil;

public class FactoryEntityServiceImpl<H> implements FactoryEntityService<H>{
	
	@SuppressWarnings("unchecked")
	@Override
	public H getEntity(MainEnum mainenum, String parameter, Session session) throws Exception{
		
		H resultentity = null;
		String param = HelperUtil.checkNullString(parameter);
		
		if(MainEnum.STOCKPRODUCT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.StockProducts");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("stockCode", new String(param))).uniqueResult();
		
		//using productcode	
		}else if(MainEnum.STOCKPRODUCTP.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.StockProducts");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param))).uniqueResult();

		}else if(MainEnum.STORE.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Store");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("storeCode", new String(param))).uniqueResult();

		}else if(MainEnum.LOGINMAN.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.LoginUser");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("userCode", new String(param)))
					.add(Restrictions.eq("active", new String("YES")))
					.add(Restrictions.eq("app", new String("Management")))
					.uniqueResult();
		
		}else if(MainEnum.LOGINEDIT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.LoginUser");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("userCode", new String(param)))
					.add(Restrictions.eq("active", new String("YES")))
					.add(Restrictions.eq("app", new String("Grocery")))
					.uniqueResult();
			
		}else if(MainEnum.LOGINGROC.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.LoginUser");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("emailAddress", new String(param)))
					//.add(Restrictions.eq("active", new String("YES")))
					.add(Restrictions.eq("app", new String("Grocery")))
					.uniqueResult();

		}else if(MainEnum.LOGINREG.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.LoginUser");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("emailAddress", new String(param)))
					.uniqueResult();

		}else if(MainEnum.SALESORDER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderInfo");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("salesOrderCode", new String(param)))
					.add(Restrictions.ne("status", new String("Delivered")))
					.add(Restrictions.ne("status", new String("Delivered With Returned")))
					.uniqueResult();
		
		}else if(MainEnum.SALESORDERDETAILS.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderDetails");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("salesOrderCode", new String(param))).uniqueResult();
		
		}else if(MainEnum.ITEMBUDGETPLANNING.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.ItemBudgetPlanning");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("jobId", new String(param))).uniqueResult();
			
		}else if(MainEnum.PURCHASEORDER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseOrder");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("supplierCode", new String(param)))
					.add(Restrictions.eq("status", new String("Pending"))).uniqueResult();
		
		}else if(MainEnum.RECEIVINGINVOICE.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseOrder");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param))).uniqueResult();
		
		//using suppliercode
		}else if(MainEnum.SUPPLIERCODE.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Supplier");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("supplierCode", new String(param))).uniqueResult();
			
		}else if(MainEnum.SUPPLIER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Supplier");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("supplierName", new String(param))).uniqueResult();
		
		}else if(MainEnum.PRODUCT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Product");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param)))
					.add(Restrictions.eq("active", "YES"))
					.uniqueResult();
			
		}else if(MainEnum.PRODUCTNAME.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Product");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("productName", new String(param))).uniqueResult();
			
		}else if(MainEnum.PHOTOCODE.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Product");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("photoCode", new String(param))).uniqueResult();
			
		}else if(MainEnum.CUSTOMER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Customer");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("customerCode", new String(param))).uniqueResult();
		
		}else if(MainEnum.CREDITCARD.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.CustomerCard");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("cardNumber", new String(param))).uniqueResult();
			
		}else if(MainEnum.VOUCHER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Voucher");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("voucherNumber", new String(param)))
					.add(Restrictions.eq("redeemed", new String("NO"))).uniqueResult();
		
		}else if(MainEnum.BOXDELIVERY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.BoxDelivery");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("boxCode", new String(param))).uniqueResult();
		
		}else if(MainEnum.BOXDELIVERYSO.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.BoxDelivery");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("salesOrderCode", new String(param))).uniqueResult();
			
		}else if(MainEnum.DELIVERYCOVERAGE.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.DeliveryCoverage");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("postCode", new String(param))).uniqueResult();
			
		}else if(MainEnum.PURCHASEREQUEST.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseRequest");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("itemBudgetCode", new String(param))).uniqueResult();
		
		}else if(MainEnum.PURCHASEFUNDING.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseFundingPlanning");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseFundingCode", new String(param))).uniqueResult();
		
		}else if(MainEnum.PURCHASEAPPROVAL.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseApproval");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseApprovalCode", new String(param))).uniqueResult();
			
		}else if(MainEnum.PURCHASEREQUESTPO.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseRequest");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param))).uniqueResult();
		
		}else if(MainEnum.INVENTORY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("stockCode", new String(param)))
					.uniqueResult();
		
		}else if(MainEnum.INVENTORYLOC.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param)))
					.add(Restrictions.eq("inventorySource", new String("GR")))
					.uniqueResult();
			
		}else if(MainEnum.PRODUCTQUOTATION.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.ProductQuotation");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("itemBudgetCode", new String(param))).uniqueResult();
		
		}else if(MainEnum.ADDRESSINFO.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.AddressInfo");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("addressCode", new String(param)))
					.add(Restrictions.eq("active", new String("YES")))
					.uniqueResult();
			
		}else if (MainEnum.STOCKCONTROL.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.StockControl");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param)))
					.add(Restrictions.eq("status", new String("Processing")))
					.uniqueResult();
			
		
		}
		
		return resultentity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public H getEntity(MainEnum mainenum, String parameter1, String parameter2, Session session) throws Exception{
		
		H resultentity = null;
		String param1 = HelperUtil.checkNullString(parameter1);
		String param2 = HelperUtil.checkNullString(parameter2);
		
		if(MainEnum.DELIVERY.equals(mainenum)){
			
			Class<?> cls = Class.forName("com.wom.api.model.Delivery");
			if(!param1.equals("")){
				resultentity = (H) session.createCriteria(cls)
						.add(Restrictions.eq("deliveryCode", new String(param1)))
						.add(Restrictions.eq("status", new String("On Delivery"))).uniqueResult();
			}else{
				resultentity = (H) session.createCriteria(cls)
						.add(Restrictions.eq("staffCode", new String(param2)))
						.add(Restrictions.eq("status", new String("On Delivery"))).uniqueResult();
			}
			
		}else if(MainEnum.PURCHASEORDER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseOrder");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param1)))
					.add(Restrictions.eq("supplierCode", new String(param2)))
					.add(Restrictions.eq("status", new String("Pending"))).uniqueResult();
			
		}else if(MainEnum.INVENTORY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("sourceCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2))).uniqueResult();
		
		}else if(MainEnum.INVENTORYSO.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("sourceCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.add(Restrictions.eq("inventorySource", "SO"))
					.uniqueResult();
		
		}else if(MainEnum.INVENTORYSTOCK.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("stockCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.add(Restrictions.eq("inventorySource", new String("GR"))).uniqueResult();
		
		}else if(MainEnum.INVENTORYTAG.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentity = (H) session.createCriteria(cls)
				.add(Restrictions.eq("productCode", new String(param1)))
				.add(Restrictions.eq("jobId", new String(param2)))
				.add(Restrictions.eq("inventorySource", new String("GR"))).uniqueResult();
			
		}else if(MainEnum.PURCHASEFUNDING.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseFundingPlanning");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("itemBudgetCode", new String(param1)))
					.add(Restrictions.eq("jobId", new String(param2))).uniqueResult();
			
		}else if(MainEnum.ADDRESSINFO.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.AddressInfo");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("customerCode", new String(param1)))
					.add(Restrictions.eq("addressCode", new String(param2)))
					.add(Restrictions.eq("active", new String("YES")))
					.uniqueResult();
			
		}else if(MainEnum.LOGINGROC.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.LoginUser");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("emailAddress", new String(param1)))
					.add(Restrictions.eq("active", new String(param2))).uniqueResult();
		
		}else if(MainEnum.CUSTOMERRECHARGE.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.CustomerRecharge");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("customerCode", new String(param1)))
					.add(Restrictions.eq("cardNumber", new String(param2))).uniqueResult();
		
		}else if(MainEnum.CUSTOMEREMOBILE.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Customer");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("phoneNumber", new String(param1)))
					.add(Restrictions.eq("customerCode", new String(param2)))
					.uniqueResult();
			
		}else if(MainEnum.LOGINFORGOT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.LoginUser");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("emailAddress", new String(param1)))
					.add(Restrictions.eq("app", new String(param2)))
					.add(Restrictions.eq("active", new String("YES")))
					.uniqueResult();
		
		}else if(MainEnum.PURCHASEREQUEST.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseRequest");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.uniqueResult();
		}
		
		return resultentity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public H getEntity(MainEnum mainenum, String parameter1, String parameter2, String parameter3, String parameter4, Session session) throws Exception{
		H resultentity = null;
		String param1 = HelperUtil.checkNullString(parameter1);
		String param2 = HelperUtil.checkNullString(parameter2);
		String param3 = HelperUtil.checkNullString(parameter3);
		String param4 = HelperUtil.checkNullString(parameter4);
		
		if(MainEnum.SUPPLIER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Supplier");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("supplierName", new String(param1)))
					.add(Restrictions.eq("address", new String(param2)))
					.add(Restrictions.eq("gSTId", new String(param3)))
					.add(Restrictions.eq("contactPersonPhone", new String(param4)))
					.uniqueResult();
			
		}else if(MainEnum.INVENTORYSTOCK.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("sourceCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.add(Restrictions.eq("storeLocation", new String(param3)))
					.add(Restrictions.eq("inventorySource", new String(param4)))
					.add(Restrictions.eq("status", new String("Items on Hand"))).uniqueResult();
		}
		return resultentity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public H getEntity(MainEnum mainenum, String parameter1, String parameter2, String parameter3, Session session) throws Exception{
		H resultentity = null;
		String param1 = HelperUtil.checkNullString(parameter1);
		String param2 = HelperUtil.checkNullString(parameter2);
		String param3 = HelperUtil.checkNullString(parameter3);
		
		if(MainEnum.BOXDELIVERY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.BoxDelivery");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("deliveryCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.add(Restrictions.eq("salesOrderCode", new String(param3)))
					.uniqueResult();
		}
		return resultentity;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<H> getEntityList(MainEnum mainenum, String parameter, Session session) throws Exception{
		
		List<H> resultentitylist = null;
		String param = HelperUtil.checkNullString(parameter);
		
		if(MainEnum.BOXDELIVERY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.BoxDelivery");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("deliveryCode", new String(param))).list();
			
		}else if(MainEnum.BOXDELIVERYSCAN.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.BoxDelivery");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("boxCode", new String(param))).list();
		
		}else if(MainEnum.ORDERRECEIPT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderInfo");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("customerCode", new String(param)))
					.addOrder(Order.desc("salesOrderCode"))
					.list();
			
		}else if(MainEnum.SALESORDERDETAILS.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderDetails");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("salesOrderCode", new String(param))).list();
		
		}else if(MainEnum.PURCHASEAPPROVED.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseApproved");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseApprovalCode", new String(param)))
					.add(Restrictions.eq("status", new String("Pending"))).list();
			
		}else if(MainEnum.ROLEASSIGN.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.RoleAssign");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("roleCode", new String("RO000001"))).list();
			
		}else if(MainEnum.CUSTOMERCART.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.CustomerCart");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("customerCode", new String(param)))
				    .add(Restrictions.eq("status", new String("Active"))).list();
			
		}else if(MainEnum.ADDRESSINFO.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.AddressInfo");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("customerCode", new String(param)))
					.add(Restrictions.eq("active", new String("YES")))
					.list();
			
		}else if(MainEnum.ACCOUNTTRANSACTION.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.AccountTransaction");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("rinvoiceCode", new String(param)))
					.add(Restrictions.eq("status", new String("Pending"))).list();
		
		}else if(MainEnum.INVENTORY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentitylist =  (List<H>)  session.createCriteria(cls)
					.add(Restrictions.eq("sourceCode", new String(param))).list();
			
		}else if(MainEnum.SALESORDERDETAILSBOX.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderDetails");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("boxCode", new String(param)))
					.add(Restrictions.eq("Status", new String("Processing"))).list();
		
		}else if(MainEnum.INVENTORYSTOCK.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentitylist =  (List<H>)  session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param))).list();
		
		}else if(MainEnum.INVENTORYLOC.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentitylist =  (List<H>)  session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param))).list();
		
		}else if(MainEnum.SALESORDER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderInfo");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("customerCode", new String(param)))
					.list();
			
		}else if(MainEnum.SALESORDERDETAILSSCANNED.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderDetails");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("salesOrderCode", new String(param)))
					.add(Restrictions.eq("status", new String("Scanned"))).list();
		
		}else if(MainEnum.SALESORDERDETAILSPROCESS.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderDetails");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("salesOrderCode", new String(param)))
					.add(Restrictions.eq("status", new String("Processing"))).list();
			
		}else if (MainEnum.PURCHASEORDERPRODUCTS.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseOrderProducts");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param)))
					.add(Restrictions.isNull("fullItem"))
					.list();
		
		}else if (MainEnum.PRODUCTSUPPLIER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.ProductSupplier");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param)))
					.list();
			
		}else if (MainEnum.PURCHASEORDERPRODUCTSREJECT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.PurchaseOrderProducts");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param)))
					.add(Restrictions.eq("fullItem", "DEL"))
					.list();
		
		}else if (MainEnum.STOCKCONTROL.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.StockControl");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param)))
					.add(Restrictions.eq("status", new String("Processing")))
					.list();
				
		}
		
		return resultentitylist;
	}
	
	/**
	 * With 2 Parameters
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<H> getEntityList(MainEnum mainenum, String parameter1, String parameter2, Session session) throws Exception{
		
		List<H> resultentitylist = null;
		String param1 = HelperUtil.checkNullString(parameter1);
		String param2 = HelperUtil.checkNullString(parameter2);
		
		if(MainEnum.SALESORDER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.SalesOrderInfo");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("customerCode", new String(param1)))
					.add(Restrictions.eq("paymentMethod", new String(param2))).list();
			
		}else if(MainEnum.INVENTORYSTOCK.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("stockCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.list();
		
		}else if(MainEnum.INVENTORY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("sourceCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2))).list();
			
		}else if(MainEnum.INVENTORYJOB.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param1)))
					.add(Restrictions.eq("stockLocation", new String(param2))).list();
		
		}else if(MainEnum.BOXDELIVERYSCAN.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.BoxDelivery");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("boxCode", new String(param1)))
					.add(Restrictions.eq("salesOrderCode", new String(param2))).list();
			
		}else if(MainEnum.BOXDELIVERY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.BoxDelivery");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("area", new String(param1)))
					.add(Restrictions.eq("boxCode", new String(param2))).list();
		
		//FOR STOCK CONTROL GENERATION OF COMPLETE SALES ORDER
		}else if(MainEnum.INVENTORYMAX.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.Inventory");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("stockLocation", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.setProjection(Projections.max("transactionDate"))
					.list();
		}
		
		return resultentitylist;
	}
	
	/**
	 * With 3 Parameters
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<H> getEntityList(MainEnum mainenum, String parameter1, String parameter2, String parameter3, Session session) throws Exception{
		List<H> resultentitylist = null;
		String param1 = HelperUtil.checkNullString(parameter1);
		String param2 = HelperUtil.checkNullString(parameter2);
		String param3 = HelperUtil.checkNullString(parameter3);
		
		if(MainEnum.BOXDELIVERY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.api.model.BoxDelivery");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("area", new String(param1)))
					.add(Restrictions.eq("boxCode", new String(param2)))
					.add(Restrictions.eq("salesOrderCode", new String(param3)))
					.list();
		}
		return resultentitylist;
	}
}
