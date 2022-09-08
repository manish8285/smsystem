package com.smsystem.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Gallery")
public class Gallery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String pic;
	@Column(length=70)
	private String about;
	private Date date;
	
	
	public Gallery() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Gallery(int id, String pic, String about, Date date) {
		super();
		this.id = id;
		this.pic = pic;
		this.about = about;
		this.date = date;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
}
