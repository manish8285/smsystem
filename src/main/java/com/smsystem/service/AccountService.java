package com.smsystem.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsystem.model.Account;
import com.smsystem.model.Student;
import com.smsystem.model.Teacher;
import com.smsystem.model.Transaction;
import com.smsystem.repository.AccountRepository;
import com.smsystem.repository.StudentRepository;
import com.smsystem.repository.TeacherRepository;

@Service
public class AccountService {
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private AccountRepository accountRepository;
	
	
	public Account saveAccount(Account account) {
		return accountRepository.save(account);
		
	}
	public void deleteAccount(Account account) {
		accountRepository.delete(account);
	}
	
	public void openStudentAccount(Student student) {
		Account account = new Account();
		System.out.println(student.getUser().getName());
		account.setUser(student.getUser());	
		account.setStudent(student);
		Account account2 = accountRepository.save(account);
		student.setAccount(account2);
		studentRepository.save(student);	
	}
	public void openTeacherAccount(Teacher teacher) {
		Account account = new Account();
		//System.out.println(student.getUser().getName());
		account.setUser(teacher.getUser());	
		Account account2 = accountRepository.save(account);
		teacher.setAccount(account2);
		teacherRepository.save(teacher);	
	}
	
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	public Account getAccountById(int acid) {
		return accountRepository.getById(acid);
	}
	
	public void makeTransaction(Transaction transaction,int acid) {
		Account account = accountRepository.getById(acid);
		List<Transaction> transactions;
		if(account.getTransactions() ==null) {
			transactions = new ArrayList<Transaction>();
			
		}else {
			transactions = account.getTransactions();
		}
		transaction.setAccount(account);
		if(transaction.getType().equals("DEBIT")) {
			float amount = 0 - transaction.getAmount();
			transaction.setAmount(amount);
		}
		transaction.setTimestamp(Timestamp.from(Instant.now()));
		account.setAccountBalance(account.getAccountBalance()+transaction.getAmount());
		transaction.setBalance(account.getAccountBalance());		
		transactions.add(transaction);
		accountRepository.save(account);
		
	}
	
	public List<Account> searchAccount(String query){
		List<Account> accounts = accountRepository.searchAccount(query);
		System.out.println("accounts = "+accounts.size());
		return accounts;
	}
	
}
