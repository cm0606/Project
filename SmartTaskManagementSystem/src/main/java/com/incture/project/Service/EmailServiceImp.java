package com.incture.project.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.incture.project.dto.Email;
@Service
public class EmailServiceImp implements EmailService{
	private static  final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImp.class);
	@Autowired 
	private JavaMailSender javaMailSender;

	
	@Override
    public String sendSimpleMail(Email details, String sender){
		LOGGER.info("Attempting to send email to: " + details.getRecipient());
    	try {
    		SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            LOGGER.info("Email sent successfully");
            return null;
        }
        catch (Exception e) {
        	LOGGER.error("Error occurred while sending email to: {}", details.getRecipient(), e.getMessage());
            return details.getRecipient();
		}
    }

}
