package com.smsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smsystem.model.Course;
import com.smsystem.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	@Query("select max(registrationNo) from Student")
	public Integer getLastRegNo();
	
	@Query("from Student as s where s.name like :query%")
	public List<Student> searchByName(String query);
	

}
