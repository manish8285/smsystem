package com.smsystem.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.util.PropertySource.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smsystem.model.Account;
import com.smsystem.model.Student;
import com.smsystem.model.Transaction;
import com.smsystem.model.User;
import com.smsystem.service.AccountService;
import com.smsystem.service.TeacherService;
import com.smsystem.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/accounts")
	public String showAllAccounts(Model model,Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("admin", user);
		model.addAttribute("accounts", accountService.getAllAccounts());
		return "account/accounts";
	}
	
	@GetMapping("/account/{acid}")		
	public String showAccount(@PathVariable("acid") Integer acid,Model model,Principal principal) {
		model.addAttribute("account", accountService.getAccountById(acid));
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("admin", user);
		model.addAttribute("transaction", new Transaction());
		return "account/single_account";
	}
	
	
	@PostMapping("/transaction")
	public String makeTransaction(@ModelAttribute Transaction transaction,@RequestParam("acid") Integer acid,Principal principal) {
		//accountService.makePayment(payment,acid);
		String name = userService.getUserByUsername(principal.getName()).getName();
		transaction.setRemarks(transaction.getRemarks()+"/"+name);
		accountService.makeTransaction(transaction, acid);
		return "redirect:/account/account/"+acid;
	}
	
	@ResponseBody
	@PostMapping("/search")
	public List<Account> accountSearch(@RequestParam("query") String query){
		List<Account> accounts = accountService.searchAccount(query);
		return accounts;
	}
	
	@GetMapping("/payroll/{tid}")
	public String payroll(@PathVariable("tid") Integer tid ,Model model,Principal principal) {
		User user = userService.getUserByUsername(principal.getName());
		model.addAttribute("admin", user);
		model.addAttribute("teacher", teacherService.getTeacherById(tid));
		return "account/payroll";
	}
	/*
	@ResponseBody
	@PostMapping("/getAccount")
	public List<Account> accountSearch(@RequestParam("query") Integer query){
		//int num =Integer.parseInt(query);
		Account account = accountService.getAccountById(query);
		return 
				}
				*/
	
}
