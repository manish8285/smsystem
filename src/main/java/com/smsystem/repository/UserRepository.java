package com.smsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smsystem.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("from User as u where u.username= :userName")
	public User getUserByUserName(@Param("userName") String username);

	
	public List<User> findByRole(String role);
	
}
