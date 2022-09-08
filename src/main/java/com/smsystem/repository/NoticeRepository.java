package com.smsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smsystem.model.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer>{
	
	@Query("from Notice as n order by n.date DESC")
	public List<Notice> getAllNotice();

}
