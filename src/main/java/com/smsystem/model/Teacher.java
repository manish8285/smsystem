package com.smsystem.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 private int id;
	 private String date;
	 private String name;
	 private String fatherName;
	 private String email;
	 private String profile;
	 private Long numbers;
	 
	 @OneToOne
	 private User user;
	 
	 @OneToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	 private Address address;
	 
	 private int salary;
	
	 @OneToMany(mappedBy="teacher")
	 private List<Course> courses;
	 
	@OneToOne(cascade= CascadeType.ALL)
	 private Account account;

	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teacher(int id, String date, String name, String email, Long numbers, Address address, int salary,
			List<Course> courses) {
		super();
		this.id = id;
		this.date = date;
		this.name = name;
		this.email = email;
		this.numbers = numbers;
		this.address = address;
		this.salary = salary;
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getNumbers() {
		return numbers;
	}

	public void setNumbers(Long numbers) {
		this.numbers = numbers;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}


	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	 
	 
	 
}
