package com.wom.api.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.joda.time.DateTime;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class CreateFileInS3 {

	public static String amazonkeyid = ResourceUtil.getMessage("amazon.keyid");
	public static String amazonsecretkey = ResourceUtil.getMessage("amazon.secretkey");
	private static String bucketName     = "purchaseordersml";
	private static String keyName        = "PO22000042_22.pdf";
	private static String uploadFileName = "D:/path/PO22000042_22.pdf";
	
	public static void main(String[] args) throws IOException {
		DateTime duedate = new DateTime();
		//StringUtils.leftPad("" + lastidresult, 6, '0')
		String strdudate = HelperUtil.checkNullDate(duedate);
		
		System.out.println("  " + strdudate);
		//BasicAWSCredentials credentials = new BasicAWSCredentials(amazonkeyid, amazonsecretkey);
        //AmazonS3 s3client = new AmazonS3Client(credentials);
        //try {
            //System.out.println("Uploading a new object to S3 from a file\n");
            //File file = new File(uploadFileName);
            //s3client.putObject(new PutObjectRequest(
            //		                 bucketName, keyName, file));
            
            //System.out.println("Successfully uploaded" + uploadFileName);
            /** Download
            S3Object object = s3client.getObject(new GetObjectRequest(
            		bucketName, keyName));
            
            System.out.println("the file " + object.getObjectContent());
            
            InputStream reader = new BufferedInputStream(object.getObjectContent());
            
            System.out.println("the reader " + reader.toString());
            
    		File file2 = new File("D:/app/PurchaseOrder2.pdf"); 
    		
    		OutputStream writer = new BufferedOutputStream(new FileOutputStream(file2));

    		int read = -1;

    		while ( ( read = reader.read() ) != -1 ) {
    		    writer.write(read);
    		}

    		writer.flush();
    		writer.close();
    		reader.close();
    		

         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }**/
    }
}
