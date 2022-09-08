package com.smsystem.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CLASSROOM")
public class ClassRoom {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Course course;
	
	@OneToMany(mappedBy="classRoom")
	private List<ClassWork> classwork;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<ClassWork> getClasswork() {
		return classwork;
	}
	public void setClasswork(List<ClassWork> classwork) {
		this.classwork = classwork;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	

}
