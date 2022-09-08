package com.smsystem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int acid;
	
	@OneToOne
	private Student student; //no need-------
	
	@JsonIgnore
	@OneToOne
	private User user;
	private float accountBalance;
	

	
	@OneToMany(mappedBy="account",cascade=CascadeType.ALL)
	private List<Transaction> transactions;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getAcid() {
		return acid;
	}

	public void setAcid(int acid) {
		this.acid = acid;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
