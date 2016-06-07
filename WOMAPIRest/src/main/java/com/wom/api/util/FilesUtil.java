/*package com.wom.api.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FilesUtil {
	
	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unused")
	public String renameFiles(){
		  // change file names in 'Directory':
		
	        String absolutePath = "D:\\WOM\\Images\\WOMDatabase_Beverage_Picture";
	        
	        File dir = new File(absolutePath);
	        File[] filesInDir = dir.listFiles();
	        int i = 0;
	        
	        String code = null;
	        String newName = null;
	        String prodcode = null;
	        
	        for(File file:filesInDir) {
	            i++;
	            
	            code = StringUtils.substringBefore(file.getName(), ".");
	            
	            *//**use this for updating the existing files
	            int query = session2.createSQLQuery("UPDATE TBLPRODUCT SET EXISTFILE = 'Y'"
	            		+ " WHERE PHOTOCODE =:code")
	            		.setString("code", code).executeUpdate();
	            
	            **//*
	            Session productSession = HibernateUtil.callSession(sessionFactory);
	            
	            Query qryProduct = productSession.createSQLQuery("SELECT A.PRODUCTCODE FROM TBLPRODUCT A"
	            		+ " WHERE A.PHOTOCODE =:code")
	            		.setString("code", code);
	            
	            prodcode = (String) qryProduct.uniqueResult();
	            
	            if (prodcode != null){
	            	 Query qryImage = productSession.createSQLQuery("SELECT A.FILENAME FROM TBLPRODUCTIMAGE A INNER JOIN"
	 	            		+ " TBLPRODUCT B ON A.PRODUCTCODE = B.PRODUCTCODE WHERE B.PHOTOCODE =:code")
	 	            		.setString("code", code);
	            	 
	            	 newName = (String) qryImage.uniqueResult();
	            	 if(newName == null){
	            		 
	            		 //Session updatesession = HibernateUtil.callSession(sessionFactory);
	            		 
	            		  Query qrySPImage = productSession.createSQLQuery("CALL getLastProductImageId(:productcode)")
	            				  .setString("productcode", prodcode);
	            		  
	            		  Object[] spresult = (Object[]) qrySPImage.uniqueResult();
	            		  
	            		  String filename = spresult[0].toString();
	            		  String fileid = spresult[1].toString();
	            			
	            		  String newPath = "D:\\WOM\\Images\\Beverage2\\" + filename + ".png";
	      	            
	      	              file.renameTo(new File(newPath));
	      	            
	      	              int uquery = productSession.createSQLQuery("UPDATE TBLPRODUCT SET EXISTFILE = 'Y', PHOTO = 'YES'"
	      	            		+ " WHERE PHOTOCODE =:code")
	      	            		.setString("code", code).executeUpdate();
	            	 }
	            }
	            
	            //System.out.println(code + " changed to " + newName);
	            HibernateUtil.callCommit(sessionFactory);
	        }
	        
	        
			return code + " changed to " + newName;
	}
}
*/