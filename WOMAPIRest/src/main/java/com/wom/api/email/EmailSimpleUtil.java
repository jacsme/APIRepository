package com.wom.api.email;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.wom.api.constant.Messages;
import com.wom.api.constant.StatusCode;
import com.wom.api.util.HelperUtil;

public class EmailSimpleUtil {

	static final Logger logger = Logger.getLogger(EmailSimpleUtil.class);
	
	public static boolean sendEmailRegister(HttpServletRequest request, String emailto, 
			String loginid, String password, String verificationcode) throws IOException{
		 
		try{
			// Setup AmazonSimpleEmailServiceClient 
			String FROM = HelperUtil.PO_EMAIL_ADDRESS;
			String TO = emailto;
			String SUBJECT = "Welcome to WOM Grocery ";
			String BODY = null;
			if(verificationcode.equals("")){
				BODY = HelperUtil.REGISTRATION_EMAIL_MESSAGE1 + "<p>User Login : " + loginid + "</p><p>Password : " + password + "</p>" + HelperUtil.REGISTRATION_EMAIL_MESSAGE2;
			}else{
				BODY = "<p>Verification Code : " + verificationcode + "</p><br>" + HelperUtil.VERIFICATION_EMAIL_MESSAGE;
			}

		 	// Construct an object to contain the recipient address.
	        Destination destination = new Destination().withToAddresses(new String[]{TO});
	        
	        // Create the subject and body of the message.
	        Content subject = new Content().withData(SUBJECT);
	        Content textBody = new Content().withData(BODY); 
	        Body body = new Body().withHtml(textBody);
		        
		    // Create a message with the specified subject and body.
	        Message message = new Message().withSubject(subject).withBody(body);
		        
		    // Assemble the email.
		    SendEmailRequest emailrequest = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
		    
		    BasicAWSCredentials credentials = new BasicAWSCredentials(HelperUtil.AMAZON_KEY_ID, HelperUtil.AMAZON_SECRET_KEY);
		    AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);
		    
		    Region REGION = Region.getRegion(Regions.US_EAST_1);
		    client.setRegion(REGION);
		    client.sendEmail(emailrequest); 
		    logger.info("StatusCode: " + StatusCode.SUCCESSFUL_CODE + " Message : " + Messages.SUCCESSFUL_EMAIL_SENT_MESSAGE);
		    
			return true;
			
		}catch(Exception e){
			logger.error("StatusCode:" + StatusCode.INVALID_EMAIL_CODE + " Error Message :" + e.getMessage());
			return false;
		}
	}

}
