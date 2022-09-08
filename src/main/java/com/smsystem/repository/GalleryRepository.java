package com.smsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smsystem.model.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Integer>{
	
	@Query("FROM Gallery as g ORDER BY g.date")
	public List<Gallery> getAllByDate();
}
