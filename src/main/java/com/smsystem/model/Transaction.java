package com.smsystem.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Account account;
	
	@Column(length=30)
	private String type;
	private String remarks="";
	private Timestamp timestamp;
	@Column(length=40)
	private String mode;
	private float balance;
	private float amount;
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Transaction(int id, String type, String remarks, Timestamp timestamp, String mode, float balance,
			float amount) {
		super();
		this.id = id;
		this.type = type;
		this.remarks = remarks;
		this.timestamp = timestamp;
		this.mode = mode;
		this.balance = balance;
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	
	
	

}
