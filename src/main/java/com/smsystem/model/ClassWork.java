package com.smsystem.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ClassWork {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	private ClassRoom classRoom;
	
	private int totalMarks;
	private String title;
	@Column(columnDefinition = "text")
	private String description;
	private Timestamp date;
	private Date lastDate;
	private String reference;
	
	@OneToMany(mappedBy="classWork")
	private List<Work> works;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public List<Work> getWorks() {
		return works;
	}
	public void setWorks(List<Work> works) {
		this.works = works;
	}
	public ClassRoom getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
