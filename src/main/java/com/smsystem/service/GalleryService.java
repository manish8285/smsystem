package com.smsystem.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smsystem.helper.SaveFile;
import com.smsystem.model.Gallery;
import com.smsystem.repository.GalleryRepository;

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;
	
	public void saveToGallery(Gallery gallery,MultipartFile file) {
		String saveFileName = SaveFile.saveFile(file);
		gallery.setPic(saveFileName);
		gallery.setDate(Date.valueOf(LocalDate.now()));
		galleryRepository.save(gallery);
	}
	public List<Gallery> getAll(){
		return galleryRepository.findAll();
	}
	
	public List<Gallery> getAllByDate(){
		return galleryRepository.getAllByDate();
	}
	public void deleteGallery(int gid){
		Gallery gallery = galleryRepository.getById(gid);
		SaveFile.deleteFile(gallery.getPic());
		galleryRepository.delete(gallery);
	}

}
