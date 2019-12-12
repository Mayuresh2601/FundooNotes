/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Java Message Service
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.user.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Jms {
	
	@Autowired
	JavaMailSender javamailsender;
	
	
	/**Method: To send Token on EmailId
	 * @param emailId
	 * @param token
	 */
	public void sendMail(String emailId, String token)
	{
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setFrom("mayuresh.com");
		mail.setTo(emailId);
		mail.setSubject("Verification token:");
		mail.setText(token);
		
		javamailsender.send(mail);
	}

}