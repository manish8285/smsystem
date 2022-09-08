package com.smsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smsystem.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Query("from Account as a where a.acid =:query")
	public List<Account> searchAccount(int query);
	
	@Query("from Account as a where a.student.name like %:query%")
	public List<Account> searchAccount(String query);
}
