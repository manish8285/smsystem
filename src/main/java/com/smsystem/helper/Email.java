package com.smsystem.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Email {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public int sendOtp(String email) {
		String sotp = Double.toString(Math.random()).substring(12, 15);
		int otp = Integer.parseInt(sotp);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("ermaanish@gmail.com");
		mail.setSubject("OTP for Email Varification - SSMS");
		mail.setTo(email);
		mail.setText("Your OTP for Email Varification is : "+sotp);
		javaMailSender.send(mail);
		return otp;
	}

}
