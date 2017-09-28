package com.wom.api.email;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.wom.api.constant.QueriesString;
import com.wom.api.pdf.PDFGenerator;
import com.wom.api.pdf.UploadPurchaseOrder;
import com.wom.api.util.HelperUtil;
import com.wom.api.util.HibernateUtil;

public class EmailMainUtil {

	static final Logger logger = Logger.getLogger(EmailMainUtil.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean generateandemail(HttpServletRequest request, HttpServletResponse response, String purchaseordercode, 
			String suppliercode, String staffcode, String fullitem, SessionFactory sessionFactory){
		
		String deptcode = null;
		String compgstid = null;
		String issuedate = null;
		String suppliername = null;
		String suppliergstid = null;
		String supplieraddress = null;
		String supplieremail = null;
		String supplierphone = null;
		String supplierfax = null;
		String suppliercontactperson = null;
		String staffname = null;
		String storecode = null;
		String officeaddress = null;
		String count = null;
		String prodgst = null;
		BigDecimal totalamount = new BigDecimal(0.00);
		BigDecimal totalgst = new BigDecimal(0.00);
		BigDecimal totalinclgst = new BigDecimal(0.00);
		boolean successful = false;
		int i = 0;
		List<String> productitems = new ArrayList<String>();
		String staffemail = null;
		BigDecimal requestedunit = new BigDecimal(0.00);
		BigDecimal balanceamount = new BigDecimal(0.00);
		BigDecimal requestedqty = new BigDecimal(0.00);
		BigDecimal unitprice = new BigDecimal(0.00);
		
		BigDecimal gstrate = new BigDecimal(0.00);
		String gstrateformat = "0.00";
		BigDecimal gstamount = new BigDecimal(0.00);
		BigDecimal inclgst = new BigDecimal(0.00);
		String strdudate = null;
		String bucketname = null;
		
		String pdffilename = null;
		Session session = HibernateUtil.callSession(sessionFactory);
		try {
			List<String> purchaseorderList;
			
			if(fullitem.equalsIgnoreCase("DEL")){
				purchaseorderList = session.createSQLQuery(QueriesString.purchaseorderemailinfoQuery 
						+ QueriesString.purchaseorderemailinfodelWhere + QueriesString.purchaseorderemailinfoGroupby)
						.setCacheable(true)
						.setString("staffcode", staffcode)
						.setString("purchaseordercode", purchaseordercode)
						.setString("suppliercode", suppliercode)
						.list();
			}else{
				purchaseorderList = session.createSQLQuery(QueriesString.purchaseorderemailinfoQuery 
						+ QueriesString.purchaseorderemailinfoWhere + QueriesString.purchaseorderemailinfoGroupby)
						.setCacheable(true)
						.setString("staffcode", staffcode)
						.setString("purchaseordercode", purchaseordercode)
						.setString("suppliercode", suppliercode)
						.list();
			}
			
			for (Iterator it = purchaseorderList.iterator(); it.hasNext();){
				Object[] resultListRecord = (Object[]) it.next();
									
				issuedate = HelperUtil.checkNullDate(resultListRecord[1]);
				deptcode = staffcode;
				compgstid = HelperUtil.checkNullString(resultListRecord[11]); //company gst id
				strdudate = HelperUtil.checkNullDate(resultListRecord[24]); //expectdelivery
				suppliercode = HelperUtil.checkNullString(resultListRecord[2]);
				suppliergstid = HelperUtil.checkNullString(resultListRecord[3]);
				suppliername = HelperUtil.checkNullString(resultListRecord[4]);
				supplierphone = HelperUtil.checkNullString(resultListRecord[5]);
				supplierfax = HelperUtil.checkNullString(resultListRecord[6]);
				
				
				supplieraddress = HelperUtil.checkNullString(resultListRecord[8]);
				suppliercontactperson = HelperUtil.checkNullString(resultListRecord[9]);
				
				storecode = HelperUtil.checkNullString(resultListRecord[10]);
				staffname = HelperUtil.checkNullString(resultListRecord[12]);
				officeaddress = HelperUtil.checkNullString(resultListRecord[13]);
				
				prodgst = HelperUtil.checkNullString(resultListRecord[22]);
				requestedunit = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[16])).setScale(0, BigDecimal.ROUND_HALF_UP);
				balanceamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[17]));
				requestedqty = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[15])).setScale(0, BigDecimal.ROUND_HALF_UP);
				
				unitprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[23])); 
				
				i=i+1;
				count = Integer.toString(i);
				
				//0A.PURCHASEORDERCODE, 1A.ISSUEDATE, 2D.SUPPLIERCODE, " 
				//3D.SUPPLIERGSTID, 4D.SUPPLIERNAME, 5D.PHONE, 6D.FAX, 7D.EMAIL, 8D.ADDRESS AS SUPPLIERADDRESS, 9D.CONTACTPERSON, "
				//10E.STORECODE, 11E.GSTID, 12F.STAFFNAME, 13E.ADDRESS AS STOREADDRESS, 14C.PRODUCTNAME, 15B.REQUESTQUANTITY, "
				//16B.REQUESTUNIT, 17B.TOTALAMOUNT, 18G.EMAILADDRESS, 19B.ACTUALORDERQTY, 20C.UOM, 21C.PACKPRICE, 22C.GST, 
				//23H.REQUESTPACKINGPRICE, 24H.SHIPPINGDATE, 25J.REQUESTCODE, 26B.FULLITEM 
				
				productitems.add(count); //count
				productitems.add(HelperUtil.checkNullString(resultListRecord[14])); //product
				
				//if (actualorderqty.compareTo(new BigDecimal(0.00)) == 0){
					
				productitems.add(requestedqty.toString()); //requestqty
				//productitems.add(HelperUtil.checkNullString(resultListRecord[20])); //UOM
				productitems.add(requestedunit.toString()); //orderunit
				productitems.add(unitprice.toString()); //UNITPRICE
				productitems.add(balanceamount.toString()); //excl gst total
				
				if (prodgst.equalsIgnoreCase("S")){ 
					gstrate = new BigDecimal((String) HelperUtil.GST_RATE);
					gstrateformat = "6.00";
					gstamount = balanceamount.multiply(gstrate).setScale(2, BigDecimal.ROUND_HALF_UP);
				}else{
					gstrate = new BigDecimal(0.00);
					gstamount = new BigDecimal(0.00);
				}
				
				productitems.add(gstrateformat); //gstrate
				productitems.add(gstamount.toString()); //gstamount
				inclgst = balanceamount.add(gstamount).setScale(2, BigDecimal.ROUND_HALF_UP);
				productitems.add(inclgst.toString()); //netvalue incl gst
				
				totalamount = totalamount.add(balanceamount).setScale(2, BigDecimal.ROUND_HALF_UP);
				totalgst = totalgst.add(gstamount).setScale(2, BigDecimal.ROUND_HALF_UP);
				totalinclgst = totalinclgst.add(inclgst).setScale(2, BigDecimal.ROUND_HALF_UP);
					
//}else{
//do this next time
//					balanceqty = requestedqty.subtract(actualorderqty); //ordqty
//					balanceamount = unitprice.multiply(balanceqty).setScale(2, BigDecimal.ROUND_HALF_UP);
//					
//					productitems.add(balanceqty.toString()); //requestqty
//					//productitems.add(HelperUtil.checkNullString(resultListRecord[20])); //UOM
//					productitems.add(requestedunit.setScale(2, BigDecimal.ROUND_HALF_UP).toString());//orderunit
//					productitems.add(unitprice.toString()); //unitprice
//					productitems.add(balanceamount.toString()); //excl gst total
//					
//					if (prodgst.equalsIgnoreCase("S")){ 
//						gstrate = new BigDecimal((String) HelperUtil.GST_RATE);
//						gstrateformat = "6.00";
//						gstamount = balanceamount.multiply(gstrate).setScale(2, BigDecimal.ROUND_HALF_UP);
//					}else{
//						gstrate = new BigDecimal(0.00);
//						gstamount = new BigDecimal(0.00);
//					}
//					
//					productitems.add(gstrateformat); //gstrate
//					productitems.add(gstamount.toString()); //gstamount
//					inclgst = balanceamount.add(gstamount).setScale(2, BigDecimal.ROUND_HALF_UP);
//					productitems.add(inclgst.toString()); //netvalue incl gst
//					
//					totalamount = totalamount.add(balanceamount).setScale(2, BigDecimal.ROUND_HALF_UP);
//					totalgst = totalgst.add(gstamount).setScale(2, BigDecimal.ROUND_HALF_UP);
//					totalinclgst = totalinclgst.add(inclgst).setScale(2, BigDecimal.ROUND_HALF_UP);
//					
//					balance = true;
//				}
				staffemail = HelperUtil.PO_EMAIL_ADDRESS; // .checkNullString(resultListRecord[18]);
				if("DEL".equalsIgnoreCase(fullitem)){	
					supplieremail = HelperUtil.CM_EMAIL_ADDRESS_TO;
				}else{
					supplieremail = HelperUtil.checkNullString(resultListRecord[7]);
				}
			}
			
			if("DEL".equalsIgnoreCase(fullitem)){	
				bucketname = HelperUtil.CM_BUCKET_NAME;
				pdffilename = HelperUtil.CM_IMAGE_LOCATION + purchaseordercode + ".pdf";
			}else{
				bucketname = HelperUtil.PO_BUCKET_NAME;
				pdffilename = HelperUtil.PO_IMAGE_LOCATION + purchaseordercode + ".pdf";
			}
			
			if (PDFGenerator.generatePurchaseOrderPDF(request, purchaseordercode, issuedate, deptcode, compgstid, strdudate, suppliercode, 
				suppliergstid, suppliername, supplierphone, supplierfax, supplieremail, supplieraddress, suppliercontactperson,
				staffname, storecode, officeaddress, productitems, totalamount, totalgst, totalinclgst, pdffilename, fullitem)==true){
				
				if(EmailRawUtil.sendEmail(request, staffemail, supplieremail, purchaseordercode, pdffilename, fullitem)== true){
					if(UploadPurchaseOrder.uploadtoS3(purchaseordercode, pdffilename, bucketname) == true){
						successful = true;
					}
				}
			}
			HibernateUtil.callCommitClose(session);
			return successful;
		}catch(Exception e){
			logger.info("Error Message: " + e.getMessage());
		}
		return false;
	}
}
