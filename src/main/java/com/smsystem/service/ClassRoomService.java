package com.smsystem.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smsystem.helper.SaveFile;
import com.smsystem.model.ClassRoom;
import com.smsystem.model.ClassWork;
import com.smsystem.model.Course;
import com.smsystem.model.Work;
import com.smsystem.repository.ClassRoomRepository;
import com.smsystem.repository.ClassWorkRepository;
import com.smsystem.repository.CourseRepository;
import com.smsystem.repository.WorkRepository;

@Service
public class ClassRoomService {

	@Autowired
	private ClassRoomRepository classRoomRepository;
	
	@Autowired
	private ClassWorkRepository classWorkRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	private WorkRepository workRepository;
	

	
	public ClassRoom createClassRoom(Course course) {
		ClassRoom classRoom = new ClassRoom();
		classRoom.setCourse(course);
		ClassRoom classRoom2 = classRoomRepository.save(classRoom);
		return classRoom2;
	}
	
	public void postClassWork(ClassWork classWork,int cid,MultipartFile file) {
		if(file.getSize() > 0) {
			System.out.println("file is not null");
			String filename = SaveFile.saveFile(file);
			classWork.setReference(filename);
		}
		classWork.setDate(Timestamp.from(Instant.now()));
		 
		ClassRoom classRoom = courseRepository.getById(cid).getClassRoom();
				
		List<ClassWork> classWorks;
		if(classRoom.getClasswork() == null) {
			classWorks = new ArrayList<ClassWork>();
		}else {
			classWorks = classRoom.getClasswork();
		}
		classWork.setClassRoom(classRoom);
		classWorks.add(classWork);
		classRoom.setClasswork(classWorks);
		classWorkRepository.save(classWork);
		ClassRoom save = classRoomRepository.save(classRoom);
				
	}
	

	public void deleteClassWork(int cwid) {
		ClassWork classWork = classWorkRepository.getById(cwid);
		if(classWork != null) {
			SaveFile.deleteFile(classWork.getReference());
		}
		classWorkRepository.delete(classWork);
		System.out.println("deleted cw ...");
	}
	
	public ClassWork getClassWorkById(int cwid) {
		return classWorkRepository.getById(cwid);
	}
	
	public void saveWork(MultipartFile file, Work work) {
		if(file != null) {
			String savedName = SaveFile.saveFile(file);
			work.setWork(savedName);
		}
		List<Work> works=null;
		ClassWork classWork = work.getClassWork();
		if(work.getClassWork().getWorks()==null) {
			works= new ArrayList<Work>();
		}else {
			works = classWork.getWorks();
		}
		Work work2 = workRepository.save(work);
		works.add(work2);
		classWork.setWorks(works);
		classWorkRepository.save(classWork);
		
	}
	public void updateMarks(Work work) {
		Work work2 = workRepository.getById(work.getId());
		work2.setMarks(work.getMarks());
		workRepository.save(work2);
	}
}
