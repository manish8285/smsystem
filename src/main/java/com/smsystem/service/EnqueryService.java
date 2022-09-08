package com.smsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsystem.model.Enquery;
import com.smsystem.repository.EnqueryRepository;

@Service
public class EnqueryService {
	
	@Autowired
	private EnqueryRepository enqueryRepository;
	
	public void saveEnquery(Enquery enquery) {
		
		enqueryRepository.save(enquery);
	}
	
	public List<Enquery> getAllEnquery(){
		List<Enquery> enqueries = enqueryRepository.findAll();
		return enqueries;
	}
	
	public void deleteEnquery(int id) {
		enqueryRepository.deleteById(id);
	}
	public void setEnqueryStatus(int eid,String status) {
		Enquery enquery = enqueryRepository.getById(eid);
		enquery.setStatus(status);
		enqueryRepository.save(enquery);
	}
}
