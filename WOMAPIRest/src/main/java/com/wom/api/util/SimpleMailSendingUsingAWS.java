package com.wom.api.util;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;

public class SimpleMailSendingUsingAWS {

	public static String amazonkeyid = ResourceUtil.getMessage("amazon.keyid");
	public static String amazonsecretkey = ResourceUtil.getMessage("amazon.secretkey");
	private static String bucketName     = "purchaseordersml";
	private static String keyName        = "PO1234569.pdf";
	public static String poemailmessage = ResourceUtil.getMessage("po.email.message");
	
	public static void main(String[] args) throws AddressException, MessagingException, IOException {
		
		// Setup AmazonSimpleEmailServiceClient 
		 BasicAWSCredentials credentials = new BasicAWSCredentials(amazonkeyid, amazonsecretkey);
		 AmazonSimpleEmailServiceClient amazonSimpleEmailService = new AmazonSimpleEmailServiceClient(credentials);
		 AmazonS3 s3client = new AmazonS3Client(credentials);
		 Properties props = new Properties();
		 
		 // sets SMTP server properties
		 props.setProperty("mail.transport.protocol", "aws");
		 props.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
		 props.setProperty("mail.aws.password", credentials.getAWSSecretKey());
		 
		 //String sfilename = "PurchaseOrder.pdf";
		 
		 S3Object object = s3client.getObject(new GetObjectRequest(
         		bucketName, keyName));
		 
		 //String contextPath = request.getSession().getServletContext().getRealPath("/");
		 //String filePath  = contextPath + "/documents/" + sfilename;
		 
	     //attachment  
	     String[] attachFiles = new String[1];
	     attachFiles[0] = object.getObjectContent().toString();
	     
	     /**attachFiles[1] = "d:/collection.txt"; **/
		   
	     //Recipient
		 //String[] to = {"jack.lord.hermoso@gmail.com","jack.lord.hermoso@gmail.com"};
		 
		 // creates a new session with an authenticator
		 Session mailSession = Session.getInstance(props);
	
		 // Create an email 
		 MimeMessage msg = new MimeMessage(mailSession);
		 
		 // Sender and recipient
		 msg.setFrom(new InternetAddress("worldon9mall2014@gmail.com"));

		 // msg.setRecipient( Message.RecipientType.TO, new InternetAddress("abc  <abc@gmail.com>"));

		 // for multipal Recipient
		 //InternetAddress[] toAddresses = new InternetAddress[to.length];
		 //for (int i = 0; i < toAddresses.length; i++){
		 //    toAddresses[i] = new InternetAddress(to[i]);
		 //}
		 
		 msg.setRecipients(Message.RecipientType.TO, "jack.lord.hermoso@gmail.com");		 
		 // Subject
		 msg.setSubject("Purchase Order");
		 
		 // creates message part
		 BodyPart part = new MimeBodyPart();
		 String myText = poemailmessage;
		 part.setContent(myText, "text/html");
		 
		 // Add a MIME part to the message
		 MimeMultipart mp = new MimeMultipart();
		 mp.addBodyPart(part);
		 BodyPart attachment = null;
		 for (String filename : attachFiles) {
		      attachment = new MimeBodyPart();
		      InputStream reader = new BufferedInputStream(object.getObjectContent());
		      
		      DataSource source = new FileDataSource(filename);
		      attachment.setDataHandler(new DataHandler(source));
		      attachment.setFileName(source.getName());
		      
		      mp.addBodyPart(attachment);
		 }
	
		 // sets the multi-part as e-mail's content                
		 msg.setContent(mp);
		     
		 // Print the raw email content on the console
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 msg.writeTo(out);
	
		 // Send Mail
		 RawMessage rm = new RawMessage();
		 rm.setData(ByteBuffer.wrap(out.toString().getBytes()));
		 amazonSimpleEmailService.sendRawEmail(new SendRawEmailRequest().withRawMessage(rm));
	}
}
