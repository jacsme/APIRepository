package com.wom.api.email;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.wom.api.util.HelperUtil;

public class EmailRawUtil { 

	static final Logger logger = Logger.getLogger(EmailRawUtil.class);
	public static boolean sendEmail(HttpServletRequest request, String emailfrom, String emailto, 
			String purchaseordercode, String pdffilename, String fullitem) throws AddressException, MessagingException, IOException{
		 
		try{
			 logger.info("Welcome to Email method " + emailfrom + " " + emailto); // Setup AmazonSimpleEmailServiceClient 
			 String FROM = emailfrom; 
			 String SUBJECT = "";
			 String BODY = null;
			
			 BasicAWSCredentials credentials = new BasicAWSCredentials(HelperUtil.AMAZON_KEY_ID, HelperUtil.AMAZON_SECRET_KEY);
			 AmazonSimpleEmailServiceClient amazonSimpleEmailService = new AmazonSimpleEmailServiceClient(credentials);

			 if("DEL".equalsIgnoreCase(fullitem)){
				 SUBJECT = "Credit Memo (Rejected Items) - " + purchaseordercode;
				 BODY = HelperUtil.ORDER_EMAIL_MESSAGE1 + HelperUtil.ORDER_EMAIL_MESSAGE2;
			 }else{
				 SUBJECT = "Purchase Order - " + purchaseordercode;
			 	 BODY = HelperUtil.PO_EMAIL_MESSAGE;
			 }
			 Properties props = new Properties();
			 
			 // sets SMTP server properties
			 props.setProperty("mail.transport.protocol", "aws");
			 props.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
			 props.setProperty("mail.aws.password", credentials.getAWSSecretKey());
			 
		     //attachment  
		     String[] attachFiles = new String[1];
		     attachFiles[0] = pdffilename;
		     
			 // creates a new session with an authenticator
			 Session mailSession = Session.getInstance(props);
		
			 // Create an email 
			 MimeMessage msg = new MimeMessage(mailSession);
			 
			 // Sender and recipient
			 msg.setFrom(new InternetAddress(FROM));
	
			 // for multipal Recipient
			 //InternetAddress[] toAddresses = new InternetAddress[emailto.size()];
			 //for (int i = 0; i < toAddresses.length; i++){
			 //    toAddresses[i] = new InternetAddress(emailto.get(i));
			 //}
			 
			 msg.setRecipients(Message.RecipientType.TO, emailto);		 
			 // Subject
			 msg.setSubject(SUBJECT);
			 
			 // creates message part
			 BodyPart part = new MimeBodyPart();
			 String myText = null;
		 	 myText = BODY;
			 part.setContent(myText, "text/html");
			 
			 // Add a MIME part to the message
			 MimeMultipart mp = new MimeMultipart();
			 mp.addBodyPart(part);
			 BodyPart attachment = null;
			 for (String filename : attachFiles) {
			      attachment = new MimeBodyPart();
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
			 return true;
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

}
