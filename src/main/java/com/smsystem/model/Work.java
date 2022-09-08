package com.smsystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="WORK")
public class Work {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private ClassWork classWork;
	
	@OneToOne
	private Student student;
	private float marks;
	private String work;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public float getMarks() {
		return marks;
	}
	public void setMarks(float marks) {
		this.marks = marks;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public ClassWork getClassWork() {
		return classWork;
	}
	public void setClassWork(ClassWork classWork) {
		this.classWork = classWork;
	}
	
	
}
