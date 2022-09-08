package com.smsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MyConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public UserDetailsService userDetailsService(){
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		UserDetails userd=User.withUsername("manish").password("123").roles("USER").build();
//		UserDetails userDetails1 = User.withUsername("user").password("123").roles("ADMIN").build();
//		manager.createUser(userd);
//		manager.createUser(userDetails1);
//		return manager;
		
		UserDetailsServiceImple userDetailsServiceImple = new UserDetailsServiceImple();
		return userDetailsServiceImple;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/teacher/**").hasRole("TEACHER")
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/account/**").hasAnyRole("ACCOUNTANT","ADMIN")
		.antMatchers("/student/**").hasRole("STUDENT")
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/**").permitAll()
		.and().formLogin()
		.and().csrf().disable();
	}
	
	

}
