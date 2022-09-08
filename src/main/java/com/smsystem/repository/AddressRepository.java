package com.smsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsystem.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
