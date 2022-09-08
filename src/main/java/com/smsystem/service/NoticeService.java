package com.smsystem.service;

import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smsystem.helper.SaveFile;
import com.smsystem.model.Notice;
import com.smsystem.repository.NoticeRepository;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	public List<Notice> getAllNotice(){
		return noticeRepository.findAll();
	}
	
	public List<Notice> getAllSortedNotice(){
		return noticeRepository.getAllNotice();
	}
	
	public Notice getNoticeById(int id) {
		return noticeRepository.getById(id);
	}
	
	public void saveNotice(Notice notice) {				
		notice.setDate(Timestamp.from(Instant.now()));
		//System.out.println(Timestamp.from(Instant.now()));
		noticeRepository.save(notice);
	}
	public void saveNoticeWithFile(Notice notice,MultipartFile file) {				
		notice.setDate(Timestamp.from(Instant.now()));
		if(!file.isEmpty()) {
			String saveFile = SaveFile.saveFile(file);
			notice.setImage(saveFile);
		}
		noticeRepository.save(notice);
	}
	
	public void deleteNotice(int nid) {
		Notice notice = noticeRepository.getById(nid);
		noticeRepository.delete(notice);
	}
	
}
