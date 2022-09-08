package com.smsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smsystem.model.User;
import com.smsystem.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public List<User> getUserByRole(String role){
		return userRepository.findByRole(role);
	}
	
	public void addNewUser(User user) {		
		userRepository.save(user);
	}
	public User getUserById(int id) {
		return userRepository.getById(id);
	}
	
	public User getUserByUsername(String username) {
		return userRepository.getUserByUserName(username);
	}
	
	public int sendOtp(User user) {
		String otp = Double.toString(Math.random()).substring(12,17);
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(user.getUsername());
		simpleMailMessage.setSubject("OTP for Email Varificatiton");
		simpleMailMessage.setFrom("ermaanish@gmaqil.com");
		simpleMailMessage.setText("OTP for email varification is : "+otp);
		javaMailSender.send(simpleMailMessage);
				
		user.getUsername();
		return Integer.parseInt(otp) ;
	}
	
}
