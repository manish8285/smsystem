package com.smsystem.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smsystem.helper.Message;
import com.smsystem.model.User;
import com.smsystem.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("/home")
	public String home(Model model,Principal principal) {
		String username = principal.getName();
		User user1 =userService.getUserByUsername(username);
		model.addAttribute("loginuser", user1);
		return "user/home";
	}


}
