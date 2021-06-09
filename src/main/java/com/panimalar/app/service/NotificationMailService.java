package com.panimalar.app.service;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.panimalar.app.model.Holder;


@Service
public class NotificationMailService {
   
	@Autowired
	private JavaMailSender javaMailSender;		
	
	private Logger logger = Logger.getLogger(NotificationMailService.class.getName());
	
	private static final String FROM_MAIL = "DIGITAL BANKING";
	
	private static final String SUBJECT_OTP = "BANKING OTP";
	
	public void sendOTPNotificationMail(Holder holder, Integer otp) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(holder.getEmail());
		mail.setFrom(FROM_MAIL);
		mail.setSubject(SUBJECT_OTP);
		logger.info(holder.getEmail()+" " + otp);
		mail.setText(holder.getName()+" Banking OTP : "+otp
				+"\n\nNote : This OTP will expire with 5 mins.");
		javaMailSender.send(mail);
	}
	
}
