package com.smsystem.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;
import javax.validation.Constraint;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique = true)
	private String username;
	private String password;
	private boolean isEnabled;
	private String role;
	private String name;
	@Column(length=10)
	private String sex;
	private Long mobile;
	private Date dob;
	private String profile;
	
	@OneToOne(mappedBy="user")
	//@JoinColumn(name="user")
	private Student student;
	
	@OneToOne(mappedBy="user")
	//@JoinColumn(name="user")
	private Teacher teacher;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String username, String password, boolean isEnabled, String role, String name, String profile,
			int roleId) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.isEnabled = isEnabled;
		this.role = role;
		this.name = name;
		this.profile = profile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}

	

}
