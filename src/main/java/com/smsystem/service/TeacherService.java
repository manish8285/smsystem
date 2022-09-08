package com.smsystem.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smsystem.helper.SaveFile;
import com.smsystem.model.Address;
import com.smsystem.model.Attendence;
import com.smsystem.model.Course;
import com.smsystem.model.Student;
import com.smsystem.model.Teacher;
import com.smsystem.model.User;
import com.smsystem.repository.AddressRepository;
import com.smsystem.repository.AttendenceRepository;
import com.smsystem.repository.CourseRepository;
import com.smsystem.repository.TeacherRepository;
import com.smsystem.repository.UserRepository;

@Service
public class TeacherService {
	
	@Autowired
	private AttendenceRepository attendenceRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	public Teacher saveTeacher(Teacher teacher) {
		Teacher teacher2 = teacherRepository.save(teacher);
		return teacher2;
	}
	
	public Teacher getTeacherById(int id) {
		return teacherRepository.getById(id);
	}
	
	public List<Teacher> getAllTeachers(){
		return teacherRepository.findAll();
		
	}
	
	public Course getCourseById(Teacher teacher,int cid){
		Course course = courseRepository.getById(cid);
		boolean contains = teacher.getCourses().contains(course);
		if(contains) {
			return course;
		}
		return null;
	}
	
	public void updateTeacher(Teacher teacher,MultipartFile file) {
		Teacher teacher2 = teacherRepository.getById(teacher.getId());
		if(!file.isEmpty()) {
			if(teacher2.getProfile() != null) {
				SaveFile.deleteFile(teacher2.getProfile());
			}
			String fileName = SaveFile.saveFile(file);		
			teacher2.setProfile(fileName);
		}
			teacher2.setName(teacher.getName());
			teacher2.setEmail(teacher2.getEmail());
			teacher2.setFatherName(teacher.getFatherName());
			teacher2.setSalary(teacher.getSalary());
			teacher2.setNumbers(teacher.getNumbers());
			
			Address address = teacher2.getAddress();
			address.setCity(teacher.getAddress().getCity());
			address.setStreet(teacher.getAddress().getStreet());
			teacher2.setAddress(address);
			teacherRepository.save(teacher2);
			
	}
	
	public void deleteTeacher(int tid) {
		Teacher teacher = teacherRepository.getById(tid);

		//updating user
		if(teacher.getUser()!= null) {
			User user = teacher.getUser();
			user.setTeacher(null);
			userRepository.save(user);
		}
		
		//deleting address
		if(teacher.getAddress() != null) {
			addressRepository.delete(teacher.getAddress());
			teacher.setAddress(null);
		}
		
		//updating courses and deleting attendance
		if(teacher.getCourses() != null) {
			List<Course> courses = teacher.getCourses();
			courses.forEach(c->{
				if(c.getAttendence() != null) {
					List<Attendence> attendences = c.getAttendence();
					attendenceRepository.deleteAll(attendences);
					c.setAttendence(null);
				}
				
				c.setTeacher(null);
				courseRepository.save(c);		
			});
		}
		//deleting profile pic
		if(teacher.getProfile() != null) {
			SaveFile.deleteFile(teacher.getProfile());
		}
		teacher.setUser(null);
		teacherRepository.delete(teacher);
	
	}
	
	public void markAttendence(List<Integer> sids, int cid, Teacher teacher ) {
		 List<Attendence> attendence;
		 Course course = courseRepository.getById(cid);
		 
		 //determining present student list
		 List<Student> students = new ArrayList<Student>();
		 course.getStudents().forEach(student->{
			 for(int i=0;i<sids.size();i++) {
				 if(student.getId()==sids.get(i)) {
					 students.add(student);
				 }
			 }
		 });
		 
		 // fetching list of attendence
		
		 if(course.getAttendence()==null) {
			 attendence = new ArrayList<Attendence>();
		 }else {
			attendence= course.getAttendence();
		 }
		 
		 Attendence attendence_tody = new Attendence();
		 attendence_tody.setCourse(course);
		 attendence_tody.setDate(new Date());
		 attendence_tody.setTeacher(teacher);
		 attendence_tody.setStudents(students);
		 attendenceRepository.save(attendence_tody);
		 attendence.add(attendence_tody);
		 course.setAttendence(attendence);
		 
		 courseRepository.save(course);	 
	}
}
