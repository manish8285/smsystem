package com.smsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smsystem.model.User;
import com.smsystem.repository.UserRepository;

public class UserDetailsServiceImple implements UserDetailsService{

	@Autowired
	public UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUserName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("User Not found");
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
	}

}
