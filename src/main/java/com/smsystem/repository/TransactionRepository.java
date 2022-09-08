package com.smsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsystem.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

}
