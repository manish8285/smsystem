package com.smsystem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Course {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String session;
	private String duration;
	private String profile;
	@Column(length = 2000)
	private String about;
	private String fees;
	private String time;
	
	@OneToOne(mappedBy="course")
	private ClassRoom classRoom;
	
	@OneToOne
	private Teacher teacher;
	
	@OneToMany(mappedBy = "course",fetch=FetchType.LAZY)
	private List<Attendence> attendence;
	
	@ManyToMany(mappedBy="courses",cascade=CascadeType.ALL)
	private List<Student> students;

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(int id, String name, String session, String duration, String profile, String about, String fees,
			Teacher teacher, List<Attendence> attendence, List<Student> students) {
		super();
		this.id = id;
		this.name = name;
		this.session = session;
		this.duration = duration;
		this.profile = profile;
		this.about = about;
		this.fees = fees;
		this.teacher = teacher;
		this.attendence = attendence;
		this.students = students;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Attendence> getAttendence() {
		return attendence;
	}

	public void setAttendence(List<Attendence> attendence) {
		this.attendence = attendence;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	} 
	
	
	
}
