package com.wom.api.pdf;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.itextpdf.text.DocumentException;
import com.wom.api.util.HelperUtil;

public class UploadPurchaseOrder {

	static final Logger logger = Logger.getLogger(UploadPurchaseOrder.class);
	public static boolean uploadtoS3(String scode, String uploadFileName, String bucketname) throws IOException, GeneralSecurityException, DocumentException {
		logger.info("Started the UploadPurchaseOrder");
		BasicAWSCredentials credentials = new BasicAWSCredentials(HelperUtil.AMAZON_KEY_ID, HelperUtil.AMAZON_SECRET_KEY);
        AmazonS3 s3client = new AmazonS3Client(credentials);
        try {
        	String keyName        = scode + ".pdf";
            File uploadfile = new File(uploadFileName);
            s3client.putObject(new PutObjectRequest(
            		                 bucketname, keyName, uploadfile).withCannedAcl(CannedAccessControlList.PublicRead));
            uploadfile.delete();
            logger.info("Successful Upload of PurchaseOrder");
            return true;
         } catch (AmazonServiceException ase) {
        	logger.info("Error Message:    " + ase.getMessage() + "HTTP Status Code: " + ase.getStatusCode() +
        			"AWS Error Code:   " + ase.getErrorCode() + "Error Type:       " + ase.getErrorType() + 
        			"Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	logger.info("Error Message: " + ace.getMessage());
		}
        logger.info("Uploading Unsuccessful");
		return false;
    }
}
