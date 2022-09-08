package com.smsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsystem.model.Work;

public interface WorkRepository extends JpaRepository<Work, Integer> {

}
