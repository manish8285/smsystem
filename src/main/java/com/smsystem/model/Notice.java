package com.smsystem.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Notice {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String title;
	private Timestamp date;
	private String issueby;
	private String image;
	@Lob
	private String description;
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Notice(int id, String title, Timestamp date, String issueby, String image, String description) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.issueby = issueby;
		this.image = image;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getIssueby() {
		return issueby;
	}
	public void setIssueby(String issueby) {
		this.issueby = issueby;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
