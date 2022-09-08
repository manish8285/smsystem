package com.smsystem.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int rollNo;
	private int registrationNo;
	private String name;
	private Date dob;
	@Column(length = 10)
	private String sex;
	private String fatherName;
	private String email;
	private String profile;
	private String addBy;
	
	@JsonIgnore
	@OneToOne
	private User user;
	
	private Long numbers;
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Course> courses;
	
	@JsonIgnore
	@OneToOne(cascade= CascadeType.ALL)
	private Account account;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(int id, int rollNo, int registrationNo, String name, String email, String profile, Long numbers,
			Address address, List<Course> courses) {
		super();
		this.id = id;
		this.rollNo = rollNo;
		this.registrationNo = registrationNo;
		this.name = name;
		this.email = email;
		this.profile = profile;
		this.numbers = numbers;
		this.address = address;
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRollNo() {
		return rollNo;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public int getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(int registrationNo) {
		this.registrationNo = registrationNo;
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
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

	public String getAddBy() {
		return addBy;
	}

	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	
}
