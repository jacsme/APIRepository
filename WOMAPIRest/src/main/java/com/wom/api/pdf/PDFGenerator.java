package com.wom.api.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wom.api.constant.MainEnum;
import com.wom.api.constant.PDFEnum;
import com.wom.api.constant.StatusCode;
import com.wom.api.factory.FactoryEntityService;
import com.wom.api.factory.FactoryEntityServiceImpl;
import com.wom.api.model.Product;
import com.wom.api.model.SalesOrderDetails;
import com.wom.api.util.HelperUtil;

public class PDFGenerator {
	
	static final Logger logger = Logger.getLogger(PDFGenerator.class);
	
	static Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
	static Font NORMAL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL);
	static Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
	static Font INNER_FONT = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL);
	
	public static boolean generatePurchaseOrderPDF(HttpServletRequest request, String purchaseordercode, String issuedate, String deptcode, String compgstid,
			String duedate, String suppliercode, String suppliergstid, String suppliername, String supplierphone, String supplierfax, String suppliermail,
			String supplieraddress, String suppliercontactperson, String staffname, String storecode, String officeaddress, List<String> productitems, 
			BigDecimal totalamount, BigDecimal totalgst, BigDecimal totalinclgst, String pdffilename, String fullitem){
		
		Document document = new Document();
		try
		{
			String contextPath = request.getSession().getServletContext().getRealPath("/");
			//check if folder exist
			//tmp/tempfolders/purchaseorders/
			File tmpmainfolder = new File("/tmp/womfolders");
			File tmpsubfolder = null;
			String potype = "PURCHASE ORDER";
			if ("DEL".equalsIgnoreCase(fullitem)){ 
				tmpsubfolder = new File("/tmp/womfolders/creditmemos");
				potype = "CREDIT MEMOS";
			} else { 
				tmpsubfolder = new File("/tmp/womfolders/purchaseorders"); 
			}
			
			if (!tmpmainfolder.exists()){ tmpmainfolder.mkdir(); }
			if (!tmpmainfolder.exists()) { tmpsubfolder.mkdir(); }

			File filepath = new File(pdffilename);
			filepath.createNewFile();
			String bgname = "ML_Background.png";
			
			String bgPath  = contextPath + "/documents/" + bgname;
			 
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.open();
			
			PdfContentByte canvas = writer.getDirectContentUnder();
	        Image bgimage = Image.getInstance(bgPath);
	        bgimage.scaleAbsolute(600, 841);
	        bgimage.setAbsolutePosition(0, 0);
	        canvas.addImage(bgimage);
	       
	        float[] titlecolumnWidths = {1f, 1f};
			PdfPTable titletable = createTable(2, titlecolumnWidths, 100, 10f, 0f);
			titletable.addCell(createCell("", NORMAL_FONT, 10, 110, PDFEnum.NONE, PDFEnum.MIDDLE, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			titletable.addCell(createCell("", NORMAL_FONT, 10, 110, PDFEnum.NONE, PDFEnum.MIDDLE, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			float[] header2columnWidths = {1f, 1f};
			PdfPTable titletable2 = createTable(2, header2columnWidths, 100, 0f, 0f); 
			
			titletable2.addCell(createCell("", NORMAL_FONT, 0, 20, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			titletable2.addCell(createCell(potype, TITLE_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
	
			float[] headercolumnWidths = {1f, 1f, 1f, 1.5f};
			PdfPTable headertable = createTable(4, headercolumnWidths, 100, 0f, 0f); 
			
			headertable.addCell(createCell("Purchasing Code : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(deptcode, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("Number : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(purchaseordercode, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			headertable.addCell(createCell("GST ID : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(compgstid, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("Date : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(issuedate, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			headertable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("Expected Delivery :", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(duedate, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			headertable.addCell(createCell("", HEADER_FONT, 0, 15, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("", HEADER_FONT, 0, 15, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("Page :", HEADER_FONT, 0, 15, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("1", HEADER_FONT, 0, 15, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
				
			/** Middle **/
			float[] middlecolumnWidths = {1f, 1f, 1f, 1.5f};
			PdfPTable middletable = createTable(4, middlecolumnWidths, 100, 10f, 0f); 
			
			middletable.addCell(createCell("Vendor Code : " , HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell(suppliercode, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell("Address : ", HEADER_FONT, 0, 26, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 2, 0));
			middletable.addCell(createCell(supplieraddress, HEADER_FONT, 0, 26, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 2, 0));

			middletable.addCell(createCell("Vendor GST ID : " , HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell(suppliergstid, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			middletable.addCell(createCell("Vendor Name : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell(suppliername, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			middletable.addCell(createCell("Telephone : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell(supplierphone, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));

			middletable.addCell(createCell("Fax : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell(supplierfax, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));

			middletable.addCell(createCell("Email : ", HEADER_FONT, 0, 15, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell(suppliermail, HEADER_FONT, 0, 15, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell("Attention : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			middletable.addCell(createCell(suppliercontactperson, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			
			/** Note **/
			float[] notecolumnWidths = {1.3f, 1f, 1.2f};
			PdfPTable notetable = createTable(3, notecolumnWidths, 100, 10f, 0f); 
			
			notetable.addCell(createCell("General Terms & Agreement", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			notetable.addCell(createCell("Store Code : ", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			notetable.addCell(createCell(storecode, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			notetable.addCell(createCell("Note:", HEADER_FONT, 0, 40, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			notetable.addCell(createCell("Delivery Address : ", HEADER_FONT, 0, 40, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			notetable.addCell(createCell(officeaddress, HEADER_FONT, 0, 40, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
		
			/** Header of the Detail table **/
			float[] tbldetailheaderWidths = {0.5f, 2.5f, 1f, 1f, 1f,1f, 1f, 1f,1f};
			PdfPTable tbldetailheader = createTable(9, tbldetailheaderWidths, 100, 10f, 0f);
			
			tbldetailheader.addCell(createCell("No", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0)); 
			tbldetailheader.addCell(createCell("Description", HEADER_FONT, 10, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0)); 
			tbldetailheader.addCell(createCell("Order\nQty", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0)); 
			//tbldetailheader.addCell(createCell("UOM", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			tbldetailheader.addCell(createCell("Case Qty", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			tbldetailheader.addCell(createCell("Unit Price", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			tbldetailheader.addCell(createCell("Net Value\nEx GST", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			tbldetailheader.addCell(createCell("GST\nRate", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			tbldetailheader.addCell(createCell("GST\nAmt", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			tbldetailheader.addCell(createCell("New Value\nInc GST", HEADER_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			
			/** Detail Table **/
			float[] tbldetailproductWidths = {0.5f, 2.5f, 1f, 1f, 1f,1f, 1f, 1f,1f};
			PdfPTable tbldetailproduct = createTable(9, tbldetailproductWidths, 100, 0f, 0f);
			PdfPCell productscell;
			
			int i = 1;
			for (String products:productitems){
				if (i==2){
					productscell = createCell(products.toString(), INNER_FONT, 0, 25, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0); 
					tbldetailproduct.addCell(productscell);
				}else{
					if(i >= 5){
						productscell = createCell(HelperUtil.checkNullPDFAmount(products.toString()), INNER_FONT, 0, 25, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0);
						tbldetailproduct.addCell(productscell);	
					}else{
						productscell = createCell(products.toString(), INNER_FONT, 0, 25, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0);
						tbldetailproduct.addCell(productscell);
					}
					
					if(i==9){ i=0; }
				}
				i=i+1;
			}
			
			/** Blank Space Table **/
			float[] blankspacecolumnWidths = {1f};
			PdfPTable blanktable = createTable(1, blankspacecolumnWidths, 100, 50f, 0f);
			blanktable.addCell(createCell("", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.NONE, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			
			/** Total Table **/
			float[] totalcolumnWidths = {0.5f, 2.5f, 1f, 1f, 1f,1f, 1f, 1f,1f}; //3f, 1f, 1f, 1f};
			
			PdfPTable totaltable = createTable(9, totalcolumnWidths, 100, 0f, 0f);
			totaltable.addCell(createCell("Total Net Value", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 5));
			totaltable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalamount.toString()), HEADER_FONT, 0, 0, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			totaltable.addCell(createCell("", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			totaltable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalgst.toString()), HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			totaltable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalinclgst.toString()), HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));

			/** Note Table **/
			float[] footnotecolumnWidths = {2f, 3.5f};
			
			PdfPTable footnotetable = createTable(2, footnotecolumnWidths, 100, 30f, 0f);
			footnotetable.addCell(createCell("", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			if (!"DEL".equalsIgnoreCase(fullitem)){ 
				footnotetable.addCell(createCell("   * This is official P.O. and print by computer-generated form.\n   * If you are unable to fulfill the order please contact us within 24 hour\n"
						+ "   * Purchase order & innvoice must be quoated with P.O. number.\n   * Delivery after due date will be rejected.", HEADER_FONT, 0, 0, PDFEnum.NONE, PDFEnum.NONE, PDFEnum.GREYBORDER, PDFEnum.NONE, 0, 0));
			}
			
			document.add(titletable);
			document.add(titletable2);
			document.add(headertable);
			document.add(middletable);
			document.add(notetable);
			document.add(tbldetailheader);
			document.add(tbldetailproduct);
			document.add(blanktable);
			document.add(totaltable);
			document.add(footnotetable);

			document.close();
			writer.close();
			return true;
		} catch (Exception e){
			logger.info("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return false;
	}
	
	public static boolean generateTaxInvoicePDF(HttpServletRequest request, String salesordercode, String gstid, String customercode, 
			String address, String salesorderdate, List<SalesOrderDetails> salesorderdetailslist, BigDecimal totalmyr,
			BigDecimal totalgst, BigDecimal totalcurrentpurchasenogst, BigDecimal totalcurrentpurchaseexgst, 
			BigDecimal totalcurrentpurchasegst, String invoicetype, String maincontactnumber, Session session){
		
		logger.info("generateTaxInvoicePDF " +salesorderdetailslist);
		
		FactoryEntityService<Product> factoryproductService = new FactoryEntityServiceImpl<Product>();
		
		Document document = new Document();
		try
		{
			String contextPath = request.getSession().getServletContext().getRealPath("/");
			String filename = HelperUtil.SO_IMAGE_LOCATION + salesordercode + ".pdf";

			//check if folder exist
			//tmp/tempfolders/salesorders/
			
			File tmpmainfolder = new File("/tmp/womfolders");
			File tmpsubfolder = new File("/tmp/womfolders/salesorders");
			
			if (!tmpmainfolder.exists()){ tmpmainfolder.mkdir(); }
			if (!tmpmainfolder.exists()) { tmpsubfolder.mkdir(); }
			
			File filepath = new File(filename);
			filepath.createNewFile();
			String bgname = "ML_Background.png";
			
			String bgPath  = contextPath + "/documents/" + bgname;
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
			document.open();
			
			PdfContentByte canvas = writer.getDirectContentUnder();
	        Image image = Image.getInstance(bgPath);
	        image.scaleAbsolute(600, 841);
	        image.setAbsolutePosition(0, 0);
	        canvas.addImage(image);
	        
	        /** Blank Columns **/
	        
	        float[] titlecolumnWidths = {1f, 1f};
			PdfPTable titletable = createTable(2, titlecolumnWidths, 100, 10f, 0f);
			titletable.addCell(createCell("", NORMAL_FONT, 0, 110, PDFEnum.NONE, PDFEnum.MIDDLE, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			titletable.addCell(createCell("", NORMAL_FONT, 0, 110, PDFEnum.NONE, PDFEnum.MIDDLE, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			 /** Tax Invoice Title **/
			float[] taxinvoicetitlecolumnWidths = {1f, 1f};
			PdfPTable taxinvoicetitletable = createTable(2, taxinvoicetitlecolumnWidths, 100, 10f, 0f);
			taxinvoicetitletable.addCell(createCell("", NORMAL_FONT, 0, 30, PDFEnum.NONE, PDFEnum.MIDDLE, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			taxinvoicetitletable.addCell(createCell(invoicetype, TITLE_FONT, 0, 30, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			/** Customer Info **/
			float[] headercolumnWidths = {1f, 1f, 1f, 1.5f};
			PdfPTable headertable = createTable(4, headercolumnWidths, 100, 0f, 0f);

			headertable.addCell(createCell("BILL TO(USER :", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(customercode + ")\n", HEADER_FONT,0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("GST ID No :", HEADER_FONT,0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(gstid, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			headertable.addCell(createCell("ADDRESS :", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 3, 0));
			headertable.addCell(createCell(address, HEADER_FONT, 0, 26, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 3, 0));
			
			headertable.addCell(createCell("INVOICE NO :", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(salesordercode, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("INVOICE DATE :", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(salesorderdate, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));	
				
			headertable.addCell(createCell("CONTACT NO :", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell(maincontactnumber, HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("PAGE :", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			headertable.addCell(createCell("1", HEADER_FONT, 0, 13, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));

			
			/** Header of the Detail table **/
			float[] columnWidths = {0.3f, 1f, 3.5f, 0.5f, 1f, 1f, 0.5f, 1f};
			PdfPTable headerdetailedtable = createTable(8, columnWidths, 100, 15f, 0f);

			headerdetailedtable.addCell(createCell("No", HEADER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			headerdetailedtable.addCell(createCell("Code", HEADER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			headerdetailedtable.addCell(createCell("Description", HEADER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			headerdetailedtable.addCell(createCell("Qty", HEADER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			headerdetailedtable.addCell(createCell("Unit Price", HEADER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			headerdetailedtable.addCell(createCell("Disc Amount", HEADER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			headerdetailedtable.addCell(createCell("GST", HEADER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			headerdetailedtable.addCell(createCell("Amount", HEADER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.BGCOLOR, 0, 0));
			
			/** Detail Table **/
			int i = 1;
			BigDecimal displayitems = new BigDecimal(0.00);
			BigDecimal displayprice = new BigDecimal(0.00);
			BigDecimal displaygst = new BigDecimal(0.00);
			BigDecimal unitprice = new BigDecimal(0.00);
			BigDecimal displaydiscountamount = new BigDecimal(0.00);
			
			BigDecimal returnitems = new BigDecimal(0.00);
			BigDecimal returnamount = new BigDecimal(0.00);
			BigDecimal returndiscount = new BigDecimal(0.00);
			BigDecimal returndiscountedprice = new BigDecimal(0.00);
			BigDecimal returngst = new BigDecimal(0.00);
			
			BigDecimal purchaseitems = new BigDecimal(0.00);
			BigDecimal purchaseprice = new BigDecimal(0.00);
			BigDecimal purchasegst = new BigDecimal(0.00);
			BigDecimal purchasediscount = new BigDecimal(0.00);
			
			for (SalesOrderDetails products:salesorderdetailslist){
				
					Product productname= factoryproductService.getEntity(MainEnum.PRODUCT, products.getProductCode(), session);
					returnitems = new BigDecimal((String) HelperUtil.checkNullNumbers(products.getReturnQuantity()));
					returnamount = new BigDecimal((String) HelperUtil.checkNullAmount(products.getReturnPrice()));
					returngst = new BigDecimal((String) HelperUtil.checkNullAmount(products.getReturnGST()));
					returndiscount = new BigDecimal((String) HelperUtil.checkNullAmount(products.getReturnDiscount()));
					
					purchaseitems = new BigDecimal((String) HelperUtil.checkNullNumbers(products.getQuantity()));
					purchaseprice = new BigDecimal((String) HelperUtil.checkNullAmount(products.getPrice()));
					purchasegst = new BigDecimal((String) HelperUtil.checkNullAmount(products.getGst()));
					purchasediscount = new BigDecimal((String) HelperUtil.checkNullAmount(products.getDiscount()));
					unitprice = purchaseprice.divide(purchaseitems, 2, RoundingMode.HALF_UP); //RRPrice
					
					returndiscountedprice = returnamount.subtract(returndiscount);

					//Compare equal value == 0, Compare Left > right == 1, Left < right == -1
					if(purchaseitems.compareTo(returnitems) == 1){
						displayitems = purchaseitems.subtract(returnitems);
						displayprice = purchaseprice.subtract(returndiscountedprice);
						displaygst = purchasegst.subtract(returngst);
						displaydiscountamount = purchasediscount.subtract(returndiscount);
					}else{
						displayitems = returnitems.subtract(purchaseitems);
						displayprice = returndiscountedprice.subtract(purchaseprice);
						displaygst = returngst.subtract(purchasegst);
						displaydiscountamount = returndiscount.subtract(purchasediscount);
					}
							 
					if(displayitems.compareTo(new BigDecimal(0.00)) == 1){
						headerdetailedtable.addCell(createCell(Integer.toString(i), INNER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0)); 
						headerdetailedtable.addCell(createCell(products.getProductCode(), INNER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
						headerdetailedtable.addCell(createCell(productname.getBrand()+ " " + productname.getProductName() + " " + productname.getPackWeight() + " " + productname.getPackMass(), INNER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
						headerdetailedtable.addCell(createCell(displayitems.toString(), INNER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
						headerdetailedtable.addCell(createCell(HelperUtil.checkNullPDFAmount(unitprice.toString()), INNER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
						headerdetailedtable.addCell(createCell(HelperUtil.checkNullPDFAmount(displaydiscountamount.toString()), INNER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
						headerdetailedtable.addCell(createCell(HelperUtil.checkNullPDFAmount(displaygst.toString()), INNER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
						headerdetailedtable.addCell(createCell(HelperUtil.checkNullPDFAmount(displayprice), INNER_FONT, 0, 20, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
					}
				i=i+1;
			}
			
			/** Total Table **/
			float[] totalcolumnWidths = {4f, 4f, 1f};
			PdfPTable totaltable = createTable(3, totalcolumnWidths, 100, 50f, 0f);
			
			totaltable.addCell(createCell("", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			totaltable.addCell(createCell("Total(Excluding Tax):", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			totaltable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalcurrentpurchaseexgst.toString()), NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			/** GST Table **/
			float[] gstcolumnWidths = {4f, 4f, 1f};
			PdfPTable gsttable = createTable(3, gstcolumnWidths, 100, 0f, 0f);
			gsttable.addCell(createCell("", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			gsttable.addCell(createCell("Add GST 6%:", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			gsttable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalgst.toString()), NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			
			/** MYR Table **/
			float[] myrcolumnWidths = {4f, 4f, 1f};
			PdfPTable myrtable = createTable(3, myrcolumnWidths, 100, 0f, 0f);
			
			myrtable.addCell(createCell("", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			myrtable.addCell(createCell("Total : MYR", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			myrtable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalmyr.toString()), NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			
			/** GST Summary **/
			float[] gstsummcolumnWidths = {1f, 1f, 1f, 5F};
			PdfPTable gstsummtable = createTable(4, gstsummcolumnWidths, 100, 10f, 0f);
			
			gstsummtable.addCell(createCell("GST Summary", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell("Amount(MYR)", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell("Tax(MYR)", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.BOTTOMBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell("", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			gstsummtable.addCell(createCell("S = 6%", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalcurrentpurchasegst.toString()), NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalgst.toString()), NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell("", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			gstsummtable.addCell(createCell("Z = 0%", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.LEFT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell(HelperUtil.checkNullPDFAmount(totalcurrentpurchasenogst.toString()), NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell("0.00", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.CENTER, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			gstsummtable.addCell(createCell("", NORMAL_FONT, 0, 0, PDFEnum.NONE, PDFEnum.RIGHT, PDFEnum.NOBORDER, PDFEnum.NONE, 0, 0));
			
			document.add(titletable);
			document.add(taxinvoicetitletable);
			document.add(headertable);
			document.add(headerdetailedtable);
			document.add(totaltable);
			document.add(gsttable);
			document.add(myrtable);
			document.add(gstsummtable);
			
			document.close();
			writer.close();
			return true;
		} catch (Exception e){
			logger.info("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return false;
	}
	
	private static PdfPTable createTable(int column, float[] columnwidths, int widthpercentage, float spacebefore, float spaceafter) throws DocumentException {
		PdfPTable pdftable = new PdfPTable(column); // 1 column.
		pdftable.setWidthPercentage(widthpercentage); //Width 100%
		pdftable.setSpacingBefore(spacebefore); //40f
		pdftable.setSpacingAfter(spaceafter); //10f
		
		pdftable.setWidths(columnwidths);
		pdftable.completeRow();
		return pdftable;
	}

	private static PdfPCell createCell(String strparagraph, Font font, int padding, int height, PDFEnum valignment, 
			PDFEnum halignment, PDFEnum border, PDFEnum bgcolor, int rowspan, int colspan) {
		
		PdfPCell cell;
		if (!"".equals(strparagraph)){
			Paragraph paragraph = new Paragraph(strparagraph, font);
			cell = new PdfPCell(paragraph);
		}else{
			cell = new PdfPCell();
		}
		
		cell.setPaddingLeft(padding);
		cell.setFixedHeight(height);
		
		if(rowspan != 0){ cell.setRowspan(rowspan);}
		if(colspan !=0){cell.setColspan(colspan);}
		
		if(PDFEnum.BGCOLOR.equals(bgcolor)){ cell.setBackgroundColor(BaseColor.GRAY); }
		if(PDFEnum.GREYBORDER.equals(border)){
			cell.setBorderColor(BaseColor.BLACK);
		}else if(PDFEnum.BOTTOMBORDER.equals(border)){
			cell.setBorder(Rectangle.BOTTOM);
		}else if(PDFEnum.NOBORDER.equals(border)){
			cell.setBorder(Rectangle.NO_BORDER);
		}
	
		if(!PDFEnum.NONE.equals(valignment)){
			if(PDFEnum.MIDDLE.equals(valignment)){
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			}else if(PDFEnum.LEFT.equals(valignment)){
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
			}else if(PDFEnum.RIGHT.equals(valignment)){
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			}
		}
		
		if(!PDFEnum.NONE.equals(halignment)){
			if(PDFEnum.CENTER.equals(halignment)){
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			}else if(PDFEnum.LEFT.equals(halignment)){
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			}else if(PDFEnum.RIGHT.equals(halignment)){
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			}
		}
		return cell;
	}
}
