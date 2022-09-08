package com.smsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Enquery {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String email;
	private Long mobile;
	private String subject;
	private String status;
	private String name;
	@Column(length = 2000)
	private String message;
	public Enquery() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Enquery(int id, String email, Long mobile, String subject, String name, String message) {
		super();
		this.id = id;
		this.email = email;
		this.mobile = mobile;
		this.subject = subject;
		this.name = name;
		this.message = message;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
