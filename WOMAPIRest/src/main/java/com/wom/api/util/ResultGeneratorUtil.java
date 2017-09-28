package com.wom.api.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.hibernate.Query;
import org.hibernate.Session;

import com.wom.api.constant.MainEnum;
import com.wom.api.constant.Messages;
import com.wom.api.constant.StatusCode;

public class ResultGeneratorUtil {

	public static String codeGenerator(String code, String sequencename, String prefixname, Session session){
		  String codeformatted = null;
		  Query lastid = session.createSQLQuery("SELECT nextval('" + sequencename + "') as next_sequence");
		  BigInteger lastidresult = (BigInteger) lastid.uniqueResult();
		  
		  if(code.equals("")){
			  codeformatted = prefixname +  StringUtils.leftPad("" + lastidresult, 6, '0');
		  }else{
			  codeformatted = lastidresult.toString();
		  }
		  return codeformatted;
	}
	
	public static BigInteger idGenerator(String code, String sequencename, Session session){
		  Query lastid = session.createSQLQuery("SELECT nextval('" + sequencename + "') as next_sequence");
		  BigInteger lastidresult = (BigInteger) lastid.uniqueResult();
		  return lastidresult;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static Collection changeToObjectList(String listString, Class contentClass) {

        Collection returnList = new ArrayList();

        // Code to remove [ and ] coming from the toString method
        if (listString.charAt(0) == '[') {
            listString = listString.substring(1);
        }
        if (listString.charAt(listString.length() - 1) == ']') {
            listString = listString.substring(0, listString.length() - 1);
        }

        String[] stringArray = listString.trim().split(",");
        for (int i = 0; i < stringArray.length; i++) {
            String[] contentArray = stringArray[i].trim().split("&");
            Object ob = null;
            try {
                ob = contentClass.newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            for (int j = 0; j < contentArray.length; j++) {

                String[] keyValueArray = contentArray[j].trim().split("=");

                String fieldName = keyValueArray[0].trim();
                String s = String.valueOf(fieldName.toCharArray()[0]);
                s = s.toUpperCase();
                fieldName = s + fieldName.substring(1);

                String fieldValue = keyValueArray[1].trim();

                Class[] paramTypes = new Class[1];
                paramTypes[0] = String.class;
                String methodName = "set" + fieldName; 
                Method m = null;
                try {
                    m = contentClass.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException mcontent) {
                	mcontent.printStackTrace();
                }
                try {
                    String result = (String) m.invoke(ob, fieldValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
            returnList.add(ob);
        }
        return returnList;
    }
	
	@SuppressWarnings("rawtypes")
	public static JSONArray populateresults(List<String> resultList, MainEnum mainEnum) throws Exception{
		
		Map<String, String> resultmap = new HashMap<String, String>();
		
		JSONArray resultArray = new JSONArray();
		
		StringBuffer deliveryaddress = new StringBuffer();
		BigDecimal gstamount = new BigDecimal(0.00);
		
		if(resultList == null || resultList.size()==0){
			
			resultmap.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
			resultmap.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
			resultArray.put(resultmap);
			
		}else{
			for (Iterator it = resultList.iterator(); it.hasNext();){
				StringBuffer packcontentssb = new StringBuffer();
				Object[] resultListRecord = (Object[]) it.next();
				
				if(MainEnum.VERSION.equals(mainEnum)){
					
					resultmap.put("Id", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("Version", HelperUtil.checkNullString(resultListRecord[1]));
					resultArray.put(resultmap);
					
				}else if(MainEnum.CATEGORY.equals(mainEnum)){
						
						resultmap.put("CategoryCode", HelperUtil.checkNullString(resultListRecord[0]));
						resultmap.put("CategoryName", HelperUtil.checkNullString(resultListRecord[1]));
						resultmap.put("SubCategory", HelperUtil.checkNullString(resultListRecord[2]));
						resultmap.put("Active", HelperUtil.checkNullString(resultListRecord[3]));
						resultArray.put(resultmap);	
					
				}else if(MainEnum.STOCKCONTROL.equals(mainEnum)){
					//0B.STORECODE, 1D.STORENAME, "
					//2A.PRODUCTCODE, 3PRODUCTNAME, "
					//4B.STOCKLEVELDAYS, "
					//5A.DATECREATED, 6LASTREQUESTUNIT, "
					//7A.STAFFCODE, 8E.FILENAME, 9E.FILETYPE, 10B.PACKQUANTITY, 11B.PACKWEIGHT, 12B.PACKMASS, "
					//13B.RRPRICE, 14F.FILENAME AS SFILENAME, 15F.FILETYPE AS SFILETYPE, 16B.PHOTOCODE, 
					//17B.UNITQUANTITY, 18E.FILESIZE, 19E.SIDE"
					
					resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("StoreName", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[3]));
					
					resultmap.put("LastStockLevelDays", HelperUtil.checkNullNumbers(resultListRecord[4]));
					resultmap.put("DateCreated", HelperUtil.checkNullDate(resultListRecord[5]));
					resultmap.put("LastRequestUnit", HelperUtil.checkNullNumbers(resultListRecord[6]));
					resultmap.put("StaffCode", HelperUtil.checkNullString(resultListRecord[7]));
					
					if(HelperUtil.checkNullString(resultListRecord[12]).equalsIgnoreCase("")){
						resultmap.put("ImageURL", "-");
					}else{
						resultmap.put("ImageURL", HelperUtil.PRODUCT_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[8]) 
								+ "_" + HelperUtil.checkNullString(resultListRecord[18]) + "_" + HelperUtil.checkNullString(resultListRecord[19])
								+ "." + HelperUtil.checkNullString(resultListRecord[9])); 
					}				
					resultmap.put("PackQuantity", HelperUtil.checkNullNumbers(resultListRecord[10]));
					resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[11]));
					resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[12]));
					resultmap.put("PackPrice", HelperUtil.checkNullAmount(resultListRecord[13]));
					resultmap.put("StaffURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[14]) + "." + HelperUtil.checkNullString(resultListRecord[15]));
					resultmap.put("SupplierProductCode", HelperUtil.checkNullString(resultListRecord[16]));
					resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[17]));
					
					if(!(HelperUtil.checkNullString(resultListRecord[11]).equalsIgnoreCase("0"))){
						packcontentssb.append(HelperUtil.checkNullNumbers(resultListRecord[17]));
						packcontentssb.append(" x " + HelperUtil.checkNullString(resultListRecord[11]) + " " + HelperUtil.checkNullMass(resultListRecord[12]));
						if(!(HelperUtil.checkNullNumbers(resultListRecord[10]).equalsIgnoreCase("0"))){
							packcontentssb.append(" (" + HelperUtil.checkNullNumbers(resultListRecord[10]) + " Pack)");
						}
					}else{
						if((HelperUtil.checkNullNumbers(resultListRecord[17]).equalsIgnoreCase("0")) && !(HelperUtil.checkNullNumbers(resultListRecord[10]).equalsIgnoreCase("0"))){
							packcontentssb.append("(" + HelperUtil.checkNullNumbers(resultListRecord[10]) + " Pack)");
						}else{
							packcontentssb.append(HelperUtil.checkNullNumbers(resultListRecord[17]) + " Each");
						}
					}
					resultmap.put("PackContents", packcontentssb.toString());
					resultArray.put(resultmap);
					
				}else if(MainEnum.STOCKCONTROLHISTORY.equals(mainEnum)){
					//0E.SUPPLIERNAME, 1D.SUPPLIERCODE, 2A.PRODUCTCODE,
					//3C.ISSUEDATE, 4B.SUBMITTEDTOINVENTORY
					
					resultmap.put("SupplierName", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("SupplierCode", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("PurchaseDate", HelperUtil.checkNullDate(resultListRecord[3]));
					resultmap.put("PurchaseUnit", HelperUtil.checkNullNumbers(resultListRecord[4]));
					
					resultArray.put(resultmap);
					
				}else if(MainEnum.ITEMBUDGETPLANNING.equals(mainEnum)){
					//0D.ITEMBUDGETCODE, 1C.STORECODE, 2F.STORENAME,
					//3C.PRODUCTCODE, 4B.PRODUCTNAME, 5C.REQUESTDATE, 6C.STOCKUNIT, 
					//7B.PACKQUANTITY, 8B.PACKPRICE, 9B.PACKWEIGHT, "
					//10B.PACKMASS, 11C.STAFFCODE, 12E.FILENAME, 13E.FILETYPE, 14G.FILENAME AS SFILENAME, 
					//15G.FILETYPE AS SFILETYPE, 16B.PHOTOCODE, 17B.UNITQUANTITY, 18B.GST
					//19E.FILESIZE, 20E.SIDE
					
					resultmap.put("ItemBudgetCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("StoreName", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("RequestDate", HelperUtil.checkNullDate(resultListRecord[5]));
					resultmap.put("StockUnit", HelperUtil.checkNullNumbers(resultListRecord[6]));
					resultmap.put("PackQuantity", HelperUtil.checkNullNumbers(resultListRecord[7]));
					resultmap.put("PackPrice", HelperUtil.checkNullAmount(resultListRecord[8]));
					resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[9]));
					resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[10]));
					resultmap.put("StaffCode", HelperUtil.checkNullString(resultListRecord[11]));
					
					if(HelperUtil.checkNullString(resultListRecord[12]).equalsIgnoreCase("")){
						resultmap.put("ImageURL", "-");
					}else{
						resultmap.put("ImageURL", HelperUtil.PRODUCT_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[12]) 
								+ "_" + HelperUtil.checkNullString(resultListRecord[19]) + "_" + HelperUtil.checkNullString(resultListRecord[20])
								+ "." + HelperUtil.checkNullString(resultListRecord[13])); 

					}
					
					resultmap.put("StaffURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[14]) + "." + HelperUtil.checkNullString(resultListRecord[15]));
					resultmap.put("SupplierProductCode", HelperUtil.checkNullString(resultListRecord[16]));
					resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[17]));
					
					if(!(HelperUtil.checkNullString(resultListRecord[9]).equalsIgnoreCase("0"))){
						packcontentssb.append(HelperUtil.checkNullNumbers(resultListRecord[17]));
						packcontentssb.append(" x " + HelperUtil.checkNullString(resultListRecord[9]) + " " + HelperUtil.checkNullMass(resultListRecord[10]));
						if(!(HelperUtil.checkNullNumbers(resultListRecord[7]).equalsIgnoreCase("0"))){
							packcontentssb.append(" (" + HelperUtil.checkNullNumbers(resultListRecord[7]) + " Pack)");
						}
					}else{
						if((HelperUtil.checkNullNumbers(resultListRecord[17]).equalsIgnoreCase("0")) && !(HelperUtil.checkNullNumbers(resultListRecord[7]).equalsIgnoreCase("0"))){
							packcontentssb.append("(" + HelperUtil.checkNullNumbers(resultListRecord[7]) + " Pack)");
						}else{
							packcontentssb.append(HelperUtil.checkNullNumbers(resultListRecord[17]) + " Each");
						}
					}
					resultmap.put("PackContents", packcontentssb.toString());
					resultmap.put("GST", HelperUtil.checkNullString(resultListRecord[18]));
					resultArray.put(resultmap);
				
				}else if(MainEnum.ITEMBUDGETPLANNINGHISTORY.equals(mainEnum)){
					
					resultmap.put("SupplierCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("SupplierName", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("PackingUnit", HelperUtil.checkNullNumbers(resultListRecord[3]));
					resultmap.put("PackingPrice", HelperUtil.checkNullAmount(resultListRecord[4]));
					resultArray.put(resultmap);	
				
				}else if(MainEnum.PURCHASEFUNDING.equals(mainEnum)){
					
					//0F.PURCHASEFUNDINGCODE, 1F.JOBID, 2C.STORECODE,  "
					//3H.STORENAME, 4C.PRODUCTCODE, 5PRODUCTNAME, 6E.REQUESTQUANTITY, 7E.REQUESTTOTALUNIT, 8E.REQUESTTOTALAMOUNT, " 
					//9B.PACKQUANTITY, 10B.PACKWEIGHT, 11B.PACKMASS, 12ORIGINALBUDGET, " 
					//13E.PAYMENTTERMS, 14C.STAFFCODE, 15G.FILENAME, 16G.FILETYPE, 17C.REQUESTDATE, 
					//18I.FILENAME AS SFILENAME, 19I.FILETYPE AS SFILETYPE, "
					//20B.PHOTOCODE, 21B.UNITQUANTITY, 22E.REQUESTTOTALAMOUNTGST, 23E.SHIPPINGDATE, 24G.FILESIZE, 25G.SIDE
					
					resultmap.put("PurchaseFundingCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("JobId", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("StoreName", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("RequestQuantity", HelperUtil.checkNullNumbers(resultListRecord[6]));
					resultmap.put("RequestTotalUnit", HelperUtil.checkNullNumbers(resultListRecord[7]));
					resultmap.put("RequestTotalAmount", HelperUtil.checkNullAmount(resultListRecord[8]));
					resultmap.put("PackQuantity", HelperUtil.checkNullNumbers(resultListRecord[9]));
					resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[10]));
					resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[11]));
					resultmap.put("OriginalBudget", HelperUtil.checkNullAmount(resultListRecord[12]));
					resultmap.put("PaymentTerms", HelperUtil.checkNullString(resultListRecord[13]));
					resultmap.put("StaffCode", HelperUtil.checkNullString(resultListRecord[14]));
					
					if(HelperUtil.checkNullString(resultListRecord[18]).equalsIgnoreCase("")){
						resultmap.put("ImageURL", "-");
					}else{
						resultmap.put("ImageURL", HelperUtil.PRODUCT_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[18]) 
								+ "_" + HelperUtil.checkNullString(resultListRecord[24]) + "_" + HelperUtil.checkNullString(resultListRecord[25])
								+ "." + HelperUtil.checkNullString(resultListRecord[19])); 
					}
					resultmap.put("RequestDate", HelperUtil.checkNullDate(resultListRecord[17]));
					resultmap.put("StaffURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[18]) + "." + HelperUtil.checkNullString(resultListRecord[19]));
					resultmap.put("SupplierProductCode", HelperUtil.checkNullString(resultListRecord[20]));
					resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[21]));
					resultmap.put("RequestTotalAmountWithGST", HelperUtil.checkNullAmount(resultListRecord[22]));
					resultmap.put("ShippingDate", HelperUtil.checkNullDate(resultListRecord[23]));
					
					if(HelperUtil.checkNullNumbers(resultListRecord[9]).equalsIgnoreCase("0")){
						resultmap.put("PackContents", HelperUtil.checkNullNumbers(resultListRecord[21]) + " x " + HelperUtil.checkNullString(resultListRecord[10]) + " " + HelperUtil.checkNullMass(resultListRecord[11]));
					}else{
						resultmap.put("PackContents", HelperUtil.checkNullNumbers(resultListRecord[21]) + " x " + HelperUtil.checkNullString(resultListRecord[10]) + " " + HelperUtil.checkNullMass(resultListRecord[11]) + " (" + HelperUtil.checkNullNumbers(resultListRecord[9]) + " Pack)");
					}
					
					resultArray.put(resultmap);
					
				}else if(MainEnum.PURCHASEFUNDINGHISTORY.equals(mainEnum)){
					
					resultmap.put("OriginalBudget", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("CurrentBudget", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("BudgetDate", HelperUtil.checkNullDate(resultListRecord[2]));
					resultArray.put(resultmap);	
					
				}else if(MainEnum.PURCHASEAPPROVAL.equals(mainEnum)){
					//0G.PURCHASEAPPROVALCODE, 1G.JOBID,
					//2C.STORECODE, 3I.STORENAME,
					//4C.PRODUCTCODE, 5B.PRODUCTNAME, 6B.PACKQUANTITY, 7B.PACKWEIGHT, 8B.PACKMASS,
					//9E.SUPPLIERCODE, 10J.SUPPLIERNAME, 11J.ADDRESS, 12J.PHONE, 13J.FAX, 14J.WEBSITE,
					//15E.REQUESTQUANTITY, 16E.REQUESTTOTALUNIT, 17E.REQUESTTOTALAMOUNT,
					//18L.PACKINGQUANTITY, 19L.PACKINGUNIT, 20L.PACKINGPRICE, 
					//21ORIGINALBUDGET 22CURRENTBUDGET,
					//23F.STAFFCODE, 24H.FILENAME AS PFILENAME, 25H.FILETYPE AS PFILETYPE,
					//26K.FILENAME AS SFILENAME, 27K.FILETYPE AS SFILETYPE, 28C.REQUESTDATE, 29B.PACKPRICE, 30B.PHOTOCODE, 
					//31B.GST, 32B.UNITQUANTITY, 33E.REQUESTPACKINGPRICE, 34E.REQUESTEDPACKINGWEIGHT, 35E.REQUESTEDPACKINGMASS
					//36E.REQUESTTOTALAMOUNTGST, 37E.SHIPPINGDATE, 38H.FILESIZE, 39H.SIDE
					String purchaseapprovalcode = HelperUtil.checkNullString(resultListRecord[0]);
					
					resultmap.put("PurchaseApprovalCode", purchaseapprovalcode);
					HelperUtil.setPurchaseapprovalcode(purchaseapprovalcode);
					
					resultmap.put("JobId", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("StoreName", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("PackQuantity", HelperUtil.checkNullNumbers(resultListRecord[6]));
					resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[7]));
					resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[8]));
					resultmap.put("SupplierCode", HelperUtil.checkNullString(resultListRecord[9]));
					resultmap.put("SupplierName", HelperUtil.checkNullString(resultListRecord[10]));
					resultmap.put("Address", HelperUtil.checkNullString(resultListRecord[11]));
					resultmap.put("Phone", HelperUtil.checkNullString(resultListRecord[12]));
					resultmap.put("Fax", HelperUtil.checkNullString(resultListRecord[13]));
					resultmap.put("Website", HelperUtil.checkNullString(resultListRecord[14]));
					
					resultmap.put("RequestQuantity", HelperUtil.checkNullNumbers(resultListRecord[15]));
					resultmap.put("RequestTotalUnit", HelperUtil.checkNullNumbers(resultListRecord[16]));
					resultmap.put("RequestTotalAmount", HelperUtil.checkNullAmount(resultListRecord[17]));
					
					resultmap.put("PackingQuantity", HelperUtil.checkNullNumbers(resultListRecord[18]));
					resultmap.put("PackingUnit", HelperUtil.checkNullNumbers(resultListRecord[19]));
					resultmap.put("PackingPrice", HelperUtil.checkNullAmount(resultListRecord[20]));
					
					resultmap.put("OriginalBudget", HelperUtil.checkNullAmount(resultListRecord[21]));
					resultmap.put("CurrentBudget", HelperUtil.checkNullAmount(resultListRecord[22]));
					resultmap.put("StaffCode", HelperUtil.checkNullString(resultListRecord[23]));
					
					if(HelperUtil.checkNullString(resultListRecord[24]).equalsIgnoreCase("")){
						resultmap.put("ImageURL-PRODUCT", "-");
					}else{
						resultmap.put("ImageURL-PRODUCT", HelperUtil.PRODUCT_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[24]) 
								+ "_" + HelperUtil.checkNullString(resultListRecord[38]) + "_" + HelperUtil.checkNullString(resultListRecord[39])
								+ "." + HelperUtil.checkNullString(resultListRecord[25])); 
					}
					resultmap.put("ImageURL-SUPPLIER", HelperUtil.SUPPLIER_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[26]) + "." + HelperUtil.checkNullString(resultListRecord[27]));
					resultmap.put("RequestDate", HelperUtil.checkNullDate(resultListRecord[28]));
					resultmap.put("SellingPrice", HelperUtil.checkNullAmount(resultListRecord[29]));
					resultmap.put("SupplierProductCode", HelperUtil.checkNullString(resultListRecord[30]));
					resultmap.put("GST", HelperUtil.checkNullString(resultListRecord[31]));
					resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[32]));
					resultmap.put("UnitPrice", HelperUtil.checkNullAmount(resultListRecord[33]));
					resultmap.put("RequestTotalAmountWithGST", HelperUtil.checkNullAmount(resultListRecord[36]));
					resultmap.put("ShippingDate", HelperUtil.checkNullDate(resultListRecord[37]));
					
					if(HelperUtil.checkNullNumbers(resultListRecord[6]).equalsIgnoreCase("0")){
						resultmap.put("PackContents", HelperUtil.checkNullNumbers(resultListRecord[32]) + " x " + HelperUtil.checkNullString(resultListRecord[7]) + " " + HelperUtil.checkNullMass(resultListRecord[8]));
					}else{
						resultmap.put("PackContents", HelperUtil.checkNullNumbers(resultListRecord[32]) + " x " + HelperUtil.checkNullString(resultListRecord[7]) + " " + HelperUtil.checkNullMass(resultListRecord[8]) + " (" + HelperUtil.checkNullNumbers(resultListRecord[6]) + " Pack)");
					}
					
					resultArray.put(resultmap);
					
				}else if(MainEnum.PURCHASEORDER.equals(mainEnum) || MainEnum.RECEIVINGINVOICE.equals(mainEnum) || MainEnum.RECEIVINGDELIVERYORDER.equals(mainEnum)){
					
					//IF RECEIVINGINVOICE
					//0B.PURCHASEORDERCODE, 1B.ISSUEDATE, 2A.JOBID, "
					//3E.STORECODE, 4J.STORENAME, "
					//5C.PRODUCTCODE, 6D.PRODUCTNAME, 7D.PACKQUANTITY, 8G.REQUESTEDPACKINGWEIGHT, 9G.REQUESTEDPACKINGMASS, "
					//10G.SUPPLIERCODE, 11K.SUPPLIERNAME, 12K.ADDRESS, 13K.PHONE, 14K.FAX, 15K.WEBSITE, "
					//16G.REQUESTQUANTITY, 17G.REQUESTUNIT, 18G.REQUESTTOTALAMOUNT, " 
					//19L.PACKINGQUANTITY, 20L.PACKINGUNIT, 21L.PACKINGPRICE,  "
					//22C.STAFFCODE, 23I.FILENAME, 24I.FILETYPE, 25A.INVOICECODE, 26A.DUEDATE, "
					//27E.REQUESTDATE, 28C.ACTUALORDERQTY, 29K.CONTACTPERSON, 30D.PHOTOCODE, 31D.UNITQUANTITY
					//32.G.REQUESTTOTALAMOUNTGST, 33D.GST, 34G.REQUESTTOTALUNIT, 35F.SHIPPINGDATE, 36G.REQUESTCODE, 37C.FULLITEM, 38D.BRAND
					
					//IF PURCHASEORDER
					//0A.PURCHASEORDERCODE, 1A.ISSUEDATE, 2A.JOBID, "
					//3D.STORECODE, 4I.STORENAME, "
					//5B.PRODUCTCODE, 6C.PRODUCTNAME, 7C.PACKQUANTITY, 8C.PACKWEIGHT, 9C.PACKMASS, "
					//10F.SUPPLIERCODE, 11J.SUPPLIERNAME, 12J.ADDRESS, 13J.PHONE, 14J.FAX, 15J.WEBSITE, "
					//16F.REQUESTQUANTITY, 17F.REQUESTTOTALUNIT, 18F.REQUESTTOTALAMOUNT, " 
					//19K.PACKINGQUANTITY, 20K.PACKINGUNIT, 21K.PACKINGPRICE,  "
					//22B.STAFFCODE, 23H.FILENAME, 24H.FILETYPE, 25D.REQUESTDATE, 26B.ACTUALORDERQTY, 27J.CONTACTPERSON, 
					//28C.PHOTOCODE, 29C.UNITQUANTITY, 30C.GST, 31F.REQUESTTOTALUNIT, 32F.SHIPPINGDATE, 33F.REQUESTCODE, 34C.BRAND
					
					resultmap.put("PurchaseOrderNumber", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("IssueDate", HelperUtil.checkNullDate(resultListRecord[1]));
					resultmap.put("JobId", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("StoreName", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[6]));
					
					resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[8]));
					resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[9]));
					resultmap.put("SupplierCode", HelperUtil.checkNullString(resultListRecord[10]));
					resultmap.put("SupplierName", HelperUtil.checkNullString(resultListRecord[11]));
					resultmap.put("Address", HelperUtil.checkNullString(resultListRecord[12]));
					resultmap.put("Phone", HelperUtil.checkNullString(resultListRecord[13]));
					resultmap.put("Fax", HelperUtil.checkNullString(resultListRecord[14]));
					resultmap.put("Website", HelperUtil.checkNullString(resultListRecord[15]));
					
					BigDecimal actualorderqty = new BigDecimal(0.00);
					
					if (MainEnum.RECEIVINGDELIVERYORDER.equals(mainEnum)){
						resultmap.put("RInvoiceCode", HelperUtil.checkNullString(resultListRecord[25]));
						resultmap.put("InvoiceNumber", HelperUtil.checkNullString(resultListRecord[25]));
						resultmap.put("DueDate", HelperUtil.checkNullDate(resultListRecord[26]));
						resultmap.put("RequestDate", HelperUtil.checkNullDate(resultListRecord[27]));
						
						BigDecimal balanceqty = new BigDecimal(0.00);
						BigDecimal requestedtotalunit = new BigDecimal(0.00);
						BigDecimal unitprice = new BigDecimal(0.00);
						BigDecimal balanceamount3 = new BigDecimal(0.00);
						
						actualorderqty = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[28]));
						BigDecimal totalamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[18])); //.setScale(2, BigDecimal.ROUND_HALF_EVEN);
						BigDecimal requestedqty = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[16])).setScale(0, BigDecimal.ROUND_HALF_UP);
						BigDecimal requestedunit = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[17])).setScale(0, BigDecimal.ROUND_HALF_UP);
						
						balanceqty = requestedqty.subtract(actualorderqty);
						unitprice = totalamount.divide(balanceqty, BigDecimal.ROUND_HALF_UP);
						balanceamount3 = unitprice.multiply(balanceqty).setScale(2, BigDecimal.ROUND_HALF_UP);
						requestedtotalunit = balanceqty.multiply(requestedunit);
								
						resultmap.put("RequestQuantity", balanceqty.toString());
						resultmap.put("RequestUnit", requestedunit.toString());
						resultmap.put("RequestTotalUnit", requestedtotalunit.toString());
						resultmap.put("RequestTotalAmount", balanceamount3.toString());
						resultmap.put("AttentionContactPerson", HelperUtil.checkNullString(resultListRecord[29]));
						resultmap.put("SupplierProductCode", HelperUtil.checkNullString(resultListRecord[30]));
						resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[31]));
						resultmap.put("ShippingDate", HelperUtil.checkNullDate(resultListRecord[35]));
						resultmap.put("PackingQuantity", HelperUtil.checkNullNumbers(resultListRecord[19]));
						
						resultmap.put("PackContents", HelperUtil.checkNullDigits(resultListRecord[17]) + " x " + HelperUtil.checkNullString(resultListRecord[8]) + " " + HelperUtil.checkNullMass(resultListRecord[9]));
						resultmap.put("RequestTotalAmountGST", HelperUtil.checkNullAmount(resultListRecord[32]));
						resultmap.put("GST", HelperUtil.checkNullString(resultListRecord[33]));
						
						if("S".equalsIgnoreCase(HelperUtil.checkNullString(resultListRecord[33]))){
							BigDecimal gstrate = new BigDecimal((String) HelperUtil.GST_RATE);
							gstamount = balanceamount3.multiply(gstrate).setScale(2, BigDecimal.ROUND_HALF_UP);;
							resultmap.put("GSTAmount", gstamount.toString());
						}else{
							resultmap.put("GSTAmount", "0.00");
						}
					}else if (MainEnum.RECEIVINGINVOICE.equals(mainEnum)){
						
						resultmap.put("RInvoiceCode", HelperUtil.checkNullString(resultListRecord[25]));
						resultmap.put("InvoiceNumber", "0");
						resultmap.put("DueDate", HelperUtil.checkNullDate(resultListRecord[26]));
						resultmap.put("RequestDate", HelperUtil.checkNullDate(resultListRecord[27]));
						
						BigDecimal balanceamount = new BigDecimal(0.00);
						BigDecimal balanceamountwithgst = new BigDecimal(0.00);
						BigDecimal requestedtotalunit = new BigDecimal(0.00);
						
						BigDecimal requestedunit = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[17])); //17G.REQUESTUNIT
						//BigDecimal requestedqty = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[16])); //16G.REQUESTQUANTITY
						actualorderqty = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[28])); //28C.ACTUALORDERQTY
						//BigDecimal totalamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[18])); //18G.REQUESTTOTALAMOUNT
						BigDecimal packingprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[21])); //18G.REQUESTTOTALAMOUNT
						
						//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
						//if(requestedqty.compareTo(actualorderqty) == 1){
						
						//BigDecimal unitprice = new BigDecimal(0.00);
						
						//unitprice = totalamount.divide(requestedqty, BigDecimal.ROUND_HALF_UP);
						balanceamount = packingprice.multiply(actualorderqty).setScale(2, BigDecimal.ROUND_HALF_UP);
						requestedtotalunit = actualorderqty.multiply(requestedunit);
						resultmap.put("RequestTotalUnit", requestedtotalunit.toString());
						
						//}else{
						//	balanceamount = totalamount;
						//	resultmap.put("RequestTotalUnit", HelperUtil.checkNullNumbers(resultListRecord[34]));
						//}
						
						resultmap.put("RequestQuantity", actualorderqty.toString());
						resultmap.put("RequestUnit", actualorderqty.toString());
						
						resultmap.put("RequestTotalAmount", balanceamount.toString());
						resultmap.put("AttentionContactPerson", HelperUtil.checkNullString(resultListRecord[29]));
						resultmap.put("SupplierProductCode", HelperUtil.checkNullString(resultListRecord[30]));
						resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[31]));
						resultmap.put("PackQuantity", actualorderqty.toString());
						
						resultmap.put("PackContents", HelperUtil.checkNullNumbers(resultListRecord[17]) + " X " + HelperUtil.checkNullString(resultListRecord[8]) + " " + HelperUtil.checkNullMass(resultListRecord[9]));
						
						if("S".equalsIgnoreCase(HelperUtil.checkNullString(resultListRecord[33]))){
							BigDecimal gstrate = new BigDecimal((String) HelperUtil.GST_RATE);
							gstamount = balanceamount.multiply(gstrate);
							balanceamountwithgst = balanceamount.add(gstamount).setScale(2, BigDecimal.ROUND_HALF_UP);
							resultmap.put("GSTAmount", gstamount.toString());
						}else{
							resultmap.put("GSTAmount", "0.00");
						}
						resultmap.put("RequestTotalAmountGST", balanceamountwithgst.toString());
						resultmap.put("GST", HelperUtil.checkNullString(resultListRecord[33]));
						resultmap.put("ShippingDate", HelperUtil.checkNullDate(resultListRecord[35]));
					}else{
						
						resultmap.put("RequestQuantity", HelperUtil.checkNullNumbers(resultListRecord[16]));
						resultmap.put("RequestDate", HelperUtil.checkNullDate(resultListRecord[25]));
						actualorderqty = new BigDecimal((String) HelperUtil.checkNullDigits(resultListRecord[26]));
						resultmap.put("RequestUnit", HelperUtil.checkNullNumbers(resultListRecord[17]));
						resultmap.put("RequestTotalUnit", HelperUtil.checkNullNumbers(resultListRecord[31]));
						resultmap.put("RequestTotalAmount", HelperUtil.checkNullAmount(resultListRecord[18]));
						resultmap.put("AttentionContactPerson", HelperUtil.checkNullString(resultListRecord[27]));
						resultmap.put("SupplierProductCode", HelperUtil.checkNullString(resultListRecord[28]));
						resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[29]));
						resultmap.put("GST", HelperUtil.checkNullString(resultListRecord[30]));
						resultmap.put("ShippingDate", HelperUtil.checkNullDate(resultListRecord[32]));
						resultmap.put("PackingQuantity", HelperUtil.checkNullNumbers(resultListRecord[19]));
						
						BigDecimal rquesttotalamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[18]));
						if("S".equalsIgnoreCase(HelperUtil.checkNullString(resultListRecord[30]))){
							BigDecimal gstrate = new BigDecimal((String) HelperUtil.GST_RATE);
							gstamount = rquesttotalamount.multiply(gstrate).setScale(2, BigDecimal.ROUND_HALF_UP);
							resultmap.put("GSTAmount", gstamount.toString());
						}else{
							resultmap.put("GSTAmount", "0.00");
						}
						
						if(HelperUtil.checkNullNumbers(resultListRecord[7]).equalsIgnoreCase("0")){
							resultmap.put("PackContents", HelperUtil.checkNullNumbers(resultListRecord[29]) + " x " + HelperUtil.checkNullString(resultListRecord[8]) + " " + HelperUtil.checkNullMass(resultListRecord[9]));
						}else{
							resultmap.put("PackContents", HelperUtil.checkNullNumbers(resultListRecord[29]) + " x " + HelperUtil.checkNullString(resultListRecord[8]) + " " + HelperUtil.checkNullMass(resultListRecord[9]) + " (" + HelperUtil.checkNullNumbers(resultListRecord[7]) + " Pack)");
						}
					}
					
					resultmap.put("PackingUnit", HelperUtil.checkNullNumbers(resultListRecord[20]));
					resultmap.put("RequestPackingPrice", HelperUtil.checkNullAmount(resultListRecord[21]));
					resultmap.put("PackingPrice", HelperUtil.checkNullAmount(resultListRecord[21]));
					resultmap.put("StaffCode", HelperUtil.checkNullString(resultListRecord[22]));
					resultmap.put("ImageURL-SUPPLIER", HelperUtil.SUPPLIER_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[23]) + "." + HelperUtil.checkNullString(resultListRecord[24]));
					
					resultArray.put(resultmap);		
					
				}else if(MainEnum.APPROVERS.equals(mainEnum)){
					
					resultmap.put("PurchaseApprovalCode",  HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("ApprovedBy", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("StaffName",HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("ImageURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[3]) + "." + HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("Status",HelperUtil.checkNullString(resultListRecord[5]));
					resultArray.put(resultmap);
					
				}else if(MainEnum.SYSTEMJOBLIST.equals(mainEnum)){
					
					//A.JOBID, B.SUPPLIERCODE, B.SUPPLIERNAME,
					//A.DATECREATED, A.DEPARTMENTCODE, A.JOBTYPE, D.FILENAME, D.FILETYPE, PURCHASEORDERCODE
					
					resultmap.put("JobId",  HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("Code", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("Name", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("DateCreated",HelperUtil.checkNullDate(resultListRecord[3]));
					resultmap.put("DepartmentCode",HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("JobType",HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("ImageURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[6]) + "." 
							+ HelperUtil.checkNullString(resultListRecord[7]));
					resultmap.put("PurchaseOrderCode", HelperUtil.checkNullString(resultListRecord[8]));
					resultArray.put(resultmap);
					
				}else if(MainEnum.JOBLIST.equals(mainEnum)){
					
					//A.JOBID, B.PRODUCTCODE, B.PRODUCTNAME,
					//A.DATECREATED, A.DEPARTMENTCODE, A.JOBTYPE, D.FILENAME, D.FILETYPE, 
					//G.STAFFNAME, G.POSITION, B.PHOTOCODE, I.SUPPLIERNAME
					
					resultmap.put("JobId",  HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("Code", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("Name", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("DateCreated",HelperUtil.checkNullDate(resultListRecord[3]));
					resultmap.put("DepartmentCode",HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("JobType",HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("ImageURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[6]) + "." 
							+ HelperUtil.checkNullString(resultListRecord[7]));
					resultmap.put("StaffName",HelperUtil.checkNullString(resultListRecord[8]));
					resultmap.put("Position",HelperUtil.checkNullString(resultListRecord[9]));
					resultmap.put("SupplierProductCode",HelperUtil.checkNullString(resultListRecord[10]));
					resultmap.put("SupplierName",HelperUtil.checkNullString(resultListRecord[11]));
					resultArray.put(resultmap);	
						
				}else if(MainEnum.SALESORDERJOBLIST.equals(mainEnum)){
					//A.JOBID, A.DATECREATED, D.FILENAME, D.FILETYPE, 
					//B.DELIVERYDATE, B.DELIVERYTIME, B.DELIVERYGROUP, B.SALESORDERCODE
					resultmap.put("JobId",  HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("DateCreated",HelperUtil.checkNullDate(resultListRecord[1]));
					resultmap.put("ImageURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[2]) + "." 
							+ HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("DeliveryDate",HelperUtil.checkNullDate(resultListRecord[4]));
					resultmap.put("DeliveryTime",HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("DeliveryGroup",HelperUtil.checkNullString(resultListRecord[6]));
					resultmap.put("SALESORDERCODE",HelperUtil.checkNullString(resultListRecord[7]));
					resultArray.put(resultmap);
					
				}else if(MainEnum.PURCHASEORDERJOBLIST.equals(mainEnum) || MainEnum.RECEIVINGINVOICEJOBLIST.equals(mainEnum)){
					
					resultmap.put("JobId",  HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("SupplierCode",HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("DateCreated",HelperUtil.checkNullDate(resultListRecord[2]));
					resultArray.put(resultmap);
						
				}else if(MainEnum.DELIVERYDETAILS.equals(mainEnum)){
					
					resultmap.put("Area",HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("BoxCode",HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("SalesOrderCode",HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("SalesDate",HelperUtil.checkNullDate(resultListRecord[3]));
					resultmap.put("DeliveryGroup",HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("DeliveryTime",HelperUtil.checkNullString(resultListRecord[5]));
					resultArray.put(resultmap);
					
				}else if(MainEnum.SUPPLIERLIST.equals(mainEnum)){
					// 0A.SUPPLIERCODE, 1A.SUPPLIERNAME, 2A.ADDRESS, 3A.PHONE,
					// 4A.FAX, 5A.WEBSITE, 6A.CONTACTPERSON, 7A.SUPPLIERGSTID, 8A.EMAIL, 
					// 9A.CONTACTPERSONPHONE, 10B.FILENAME, 11B.FILETYPE,
					// 12C.PACKUNIT, 13C.PACKPRICE, 14C.PAYMENTTERMS,
					// 15D.PACKWEIGHT, 16D.PACKMASS, 17D.GST
					
					resultmap.put("SupplierCode",  HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("SupplierName", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("Address", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("Phone", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("Fax", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("Website", HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("ContactPerson", HelperUtil.checkNullString(resultListRecord[6]));
					resultmap.put("GSTId", HelperUtil.checkNullString(resultListRecord[7]));
					resultmap.put("Email", HelperUtil.checkNullString(resultListRecord[8]));
					resultmap.put("ContactPersonPhone", HelperUtil.checkNullString(resultListRecord[9]));
					
					resultmap.put("ImageURL", HelperUtil.SUPPLIER_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[10]) + "." 
							+ HelperUtil.checkNullString(resultListRecord[11]));
					
					resultmap.put("PackUnit", HelperUtil.checkNullNumbers(resultListRecord[12]));
					resultmap.put("PackPrice", HelperUtil.checkNullAmount(resultListRecord[13]));
					resultmap.put("PaymentTerms", HelperUtil.checkNullNumbers(resultListRecord[14]));
					
					resultmap.put("PackWeight", HelperUtil.checkNullAmount(resultListRecord[15]));
					resultmap.put("PackMass", HelperUtil.checkNullString(resultListRecord[16]));
					resultmap.put("GST", HelperUtil.checkNullString(resultListRecord[17]));
					
					resultArray.put(resultmap);
					
				}else if(MainEnum.STORE.equals(mainEnum)){
					resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("StoreName", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("Address", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("ContactNumber", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("Website", HelperUtil.checkNullString(resultListRecord[4]));
					resultArray.put(resultmap);
					
				}else if(MainEnum.MAILBOX.equals(mainEnum)){
					
					resultmap.put("MailCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("MailType", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("MailContent", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("CustomerCode", HelperUtil.checkNullString(resultListRecord[3]));
					
					resultArray.put(resultmap);	
				}else if(MainEnum.DELIVERYJOBLIST.equals(mainEnum)){
					//0A.DELIVERYCODE, 1D.SALESORDERCODE, 2G.ADDRESSINFO, 3F.ADDRESS, 
					//4G.GOOGLEADDRESS, 5 A.TRUCKCODE, 6D.DELIVERYTIME, 7E.PHONENUMBER
					resultmap.put("DeliveryCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("SalesOrderCode", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("DeliveryAddress", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("GoogleAddress", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("TruckCode", HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("DeliveryTime", HelperUtil.checkNullString(resultListRecord[6]));
					resultmap.put("ContactNumber", HelperUtil.checkNullString(resultListRecord[7]));
										
					deliveryaddress.append("|" + HelperUtil.checkNullString(resultListRecord[2]));
					
					HelperUtil.setDeliveryaddress(deliveryaddress.toString());
					HelperUtil.setOfficeaddress(HelperUtil.checkNullString(resultListRecord[3]));
					
					resultArray.put(resultmap);
					
				}else if(MainEnum.DELIVERYCUSTOMERLIST.equals(mainEnum)){
					//0A.DELIVERYCODE, 1B.BOXCODE, 2C.SALESORDERCODE, 3D.CUSTOMERCODE, "
					//4H.ADDRESSINFO, 5D.PAYMENTMETHOD, 6F.PRODUCTCODE, 7F.PRODUCTNAME, 8C.QUANTITY, 
					//9C.PRICE, 10F.PACKQUANTITY, 11F.PACKWEIGHT, 12F.PACKMASS, "
					//13G.FILENAME, 14G.FILETYPE, 15B.AREA, 16F.GST, 17B.TRUCKRACKINGCODE
					//19D.WCPAIDAMOUNT, 20D.CCPAIDAMOUNT, 21D.SALESNOTE, 22D.CONTACTNUMBER, 23G.FILESIZE, 24G.SIDE
					//25F.DISCOUNT, 26F.DISCOUNTAMOUNT
					BigDecimal discountamount = new BigDecimal(0.00);
					
					resultmap.put("DeliveryCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("BoxCode", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("SalesOrderCode", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("CustomerCode", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("Address", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("PaymentMethod", HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[6]));
					resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[7]));
					resultmap.put("Quantity", HelperUtil.checkNullNumbers(resultListRecord[8]));
					
					resultmap.put("PackQuantity", HelperUtil.checkNullNumbers(resultListRecord[10]));
					resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[11]));
					resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[12]));
					
					if(HelperUtil.checkNullString(resultListRecord[13]).equalsIgnoreCase("")){
						resultmap.put("ImageURL", "-");
					}else{
						resultmap.put("ImageURL", HelperUtil.PRODUCT_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[13]) 
								+ "_" + HelperUtil.checkNullString(resultListRecord[23]) + "_" + HelperUtil.checkNullString(resultListRecord[24])
								+ "." + HelperUtil.checkNullString(resultListRecord[14])); 
					}
					resultmap.put("DeliveryTruckArea", HelperUtil.checkNullString(resultListRecord[15]));
					
					BigDecimal paymentamount = new BigDecimal(0.00);
					BigDecimal balanceamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[20]));
					BigDecimal womcoinamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[19]));
					discountamount = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[26]));
					
					paymentamount = balanceamount.add(womcoinamount).setScale(2, RoundingMode.HALF_UP);
								
					resultmap.put("GSTAmount", HelperUtil.checkNullAmount(resultListRecord[16]));
					resultmap.put("Price", HelperUtil.checkNullAmount(resultListRecord[9]));
					resultmap.put("TruckRackingCode", HelperUtil.checkNullString(resultListRecord[17]));
					resultmap.put("PackContents", HelperUtil.checkNullString(resultListRecord[18]));
					
					//including the GST here
					resultmap.put("WOMCoinTotalAmount", womcoinamount.toString());
					resultmap.put("TotalPaymentAmount", paymentamount.toString());
					resultmap.put("BalanceAmount", balanceamount.toString());
					resultmap.put("SalesNote", HelperUtil.checkNullString(resultListRecord[21]));
					resultmap.put("ContactNumber", HelperUtil.checkNullString(resultListRecord[22]));
					resultmap.put("Discount", HelperUtil.checkNullAmount(resultListRecord[25]));
					resultmap.put("DiscountAmount", discountamount.toString());
					
					resultArray.put(resultmap);
				}else if (MainEnum.STAFF.equals(mainEnum)){
					resultmap.put("StaffCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("StaffName", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("DateOfBirth", HelperUtil.checkNullDate(resultListRecord[2]));
					resultmap.put("Position", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("Ratings", HelperUtil.checkNullNumbers(resultListRecord[4]));
					resultmap.put("Attendance", HelperUtil.checkNullNumbers(resultListRecord[5]));
					resultmap.put("Salary", HelperUtil.checkNullAmount(resultListRecord[6]));
					resultmap.put("BalanceLeave", HelperUtil.checkNullNumbers(resultListRecord[7]));
					resultmap.put("CompanyItemAsset", HelperUtil.checkNullNumbers(resultListRecord[8]));
					resultmap.put("SubmitClaim", HelperUtil.checkNullNumbers(resultListRecord[9]));
					resultmap.put("ReportingTo", HelperUtil.checkNullString(resultListRecord[10]));
					resultmap.put("StaffURL", HelperUtil.STAFF_IMAGE_LOCATION + HelperUtil.checkNullString(resultListRecord[11]) + "." 
							+ HelperUtil.checkNullString(resultListRecord[12]));
					resultArray.put(resultmap);
					
				}else if (MainEnum.CUSTOMER.equals(mainEnum)){
					
					resultmap.put("CustomerCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("EmailAddress", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("PhoneNumber", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("Points", HelperUtil.checkNullNumbers(resultListRecord[3]));
					resultArray.put(resultmap);
					
				}else if(MainEnum.RECHARGEHISTORY.equals(mainEnum)){
					resultmap.put("CustomerCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("RechargeAmount", HelperUtil.checkNullAmount(resultListRecord[1]));
					resultmap.put("WOMCoin", HelperUtil.checkNullAmount(resultListRecord[2]));
					resultmap.put("PaymentType", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("VoucherNumber", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("RechargeDate", HelperUtil.checkNullDate(resultListRecord[5]));
					resultArray.put(resultmap);		
					
				}else if(MainEnum.VOUCHERREDEEMED.equals(mainEnum)){
					resultmap.put("CustomerCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("VoucherNumber", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("Amount", HelperUtil.checkNullAmount(resultListRecord[2]));
					resultmap.put("RedeemedDate", HelperUtil.checkNullDate(resultListRecord[3]));
					resultArray.put(resultmap);	
					
				}else if(MainEnum.TRUCKINFO.equals(mainEnum)){
					resultmap.put("TruckCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("Capacity", HelperUtil.checkNullNumbers(resultListRecord[1]));
					resultArray.put(resultmap);	
					
				}else if(MainEnum.INVOICEPAYABLE.equals(mainEnum)){
					
					//0A.SUPPLIERCODE, 1B.SUPPLIERNAME, 2A.PURCHASEORDERCODE, 
				    //3A.INVOICECODE, 4A.PURCHASEDATE, 5A.DUEDATE, 6A.PURCHASETOTALAMOUNTWGST,
				    //7A.PURCHASEDATE, 8SOURCE, 
				    //9BUDGET

					resultmap.put("SupplierCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("SupplierName", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("PurchaseOrderCode", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("RInvoiceCode", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("PurchaseDate", HelperUtil.checkNullDate(resultListRecord[4]));
					resultmap.put("DueDate", HelperUtil.checkNullDate(resultListRecord[5]));
					resultmap.put("TotalAmountWGST", HelperUtil.checkNullAmount(resultListRecord[6]));
					resultmap.put("PurchaseDate", HelperUtil.checkNullDate(resultListRecord[7]));
					resultmap.put("Source", HelperUtil.checkNullString(resultListRecord[8]));
					resultmap.put("Budget", HelperUtil.checkNullAmount(resultListRecord[9]));
					resultmap.put("InvoiceNumber", HelperUtil.checkNullAmount(resultListRecord[10]));
					
					resultArray.put(resultmap);	
					
				}else if(MainEnum.ADDRESSINFO.equals(mainEnum)){
					
					resultmap.put("AddressCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("AddressInfo", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("AddressType", HelperUtil.checkNullString(resultListRecord[2]));
					
					resultArray.put(resultmap);	
				}else if(MainEnum.SHOPPINGRANK.equals(mainEnum)){
					//RANK, A.CUSTOMERCODE, C.EMAILADDRESS, TOTALPOINT
					
					BigDecimal myranking = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[0])).setScale(0, BigDecimal.ROUND_HALF_UP);
					BigDecimal mypoints = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[3])).setScale(0, BigDecimal.ROUND_HALF_UP);
					
					resultmap.put("MyRank", myranking.toString());
					resultmap.put("MyCustomerCode", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("MyEmailAddress", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("MyTotalPoints", mypoints.toString());
					resultArray.put(resultmap);	
					
				}else if(MainEnum.SHOPPINGRANKLIST.equals(mainEnum)){
					//RANK, A.CUSTOMERCODE, C.EMAILADDRESS, TOTALPOINT
					
					BigDecimal rankingall = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[0])).setScale(0, BigDecimal.ROUND_HALF_UP);
					BigDecimal rankingpoints = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[3])).setScale(0, BigDecimal.ROUND_HALF_UP);
					
					resultmap.put("Rank", rankingall.toString());
					resultmap.put("CustomerCode", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("EmailAddress", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("TotalPoints", rankingpoints.toString());
					resultArray.put(resultmap);	
				}
			}
		}
		return resultArray;
	}
	
	
}
