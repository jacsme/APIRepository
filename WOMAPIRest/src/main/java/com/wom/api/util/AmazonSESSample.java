package com.wom.api.util;

import java.io.IOException;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class AmazonSESSample {
 
    static final String FROM = "worldon9mall2014@gmail.com";  // Replace with your "From" address. This address must be verified.
    static final String TO = "jack.lord.hermoso@gmail.com"; // Replace with a "To" address. If your account is still in the
                                                      // sandbox, this address must be verified.
    static final String BODY = "This email was sent through Amazon SES by using the AWS SDK for Java.";
    static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";
  

    public static void main(String[] args) throws IOException {    	
                
        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{TO});
        
        // Create the subject and body of the message.
        Content subject = new Content().withData(SUBJECT);
        Content textBody = new Content().withData(BODY); 
        Body body = new Body().withText(textBody);
        
        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);
        
        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
        
        try
        {        
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
        
            BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAI3WIUI2FDW4JPPJQ", "KgCvz9L7J2JsJUuY3SDuJzZwlq2UDYc+5TXqRilb");
			AmazonSimpleEmailServiceClient amazonSimpleEmailService = new AmazonSimpleEmailServiceClient(credentials);
          
            Region REGION = Region.getRegion(Regions.US_EAST_1);
            amazonSimpleEmailService.setRegion(REGION);
       
            // Send the email.
            amazonSimpleEmailService.sendEmail(request);  
            System.out.println("Email sent!");
        }
        catch (Exception ex) 
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }
}