package com.panimalar.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.panimalar.app.model.Holder;

@Service
public class OtpService {
	
	@Autowired
    private OtpGenerator otpGenerator; 
	
	@Autowired 
	private NotificationMailService notificationMailService;
	
	public Integer generateOtp(Holder holder) {
		String key = holder.getEmail();
		if(Boolean.TRUE.equals(otpGenerator.isGenerated(key))) {
			return -1;
		}
		Integer otp = otpGenerator.generateOTP(key);
		try {
		    notificationMailService.sendOTPNotificationMail(holder, otp);
		}catch(MailException e){
			return -1;
		}
		return otp;
	}
	
	public Integer resendOtp(Holder holder) {
		String key = holder.getEmail();
		if(Boolean.FALSE.equals(otpGenerator.isGenerated(key))) {
			return -1;
		}
		Integer otp = otpGenerator.getOTPByKey(key);
		try {
		    notificationMailService.sendOTPNotificationMail(holder, otp);
		}catch(MailException e){
			return -1;
		}
		return otp;
	}
	
	public Boolean validateOTP(String key, Integer otp) {
		Integer cacheOtp = otpGenerator.getOTPByKey(key);
		if(cacheOtp.equals(otp)) {
			otpGenerator.clearOTPCache(key);
			return true;
		} else {
			return false;
		}
	}
}
