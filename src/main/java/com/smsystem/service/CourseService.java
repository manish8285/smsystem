package com.smsystem.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsystem.model.Account;
import com.smsystem.model.Attendence;
import com.smsystem.model.ClassRoom;
import com.smsystem.model.ClassWork;
import com.smsystem.model.Course;
import com.smsystem.model.Student;
import com.smsystem.model.Teacher;
import com.smsystem.model.Transaction;
import com.smsystem.repository.AttendenceRepository;
import com.smsystem.repository.ClassRoomRepository;
import com.smsystem.repository.ClassWorkRepository;
import com.smsystem.repository.CourseRepository;
import com.smsystem.repository.StudentRepository;
import com.smsystem.repository.TeacherRepository;
import com.smsystem.repository.WorkRepository;

@Service
public class CourseService {
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	private ClassRoomRepository classRoomRepository;
	
	@Autowired
	private ClassRoomService classRoomService;
	
	@Autowired
	private ClassWorkRepository classWorkRepository;
	
	@Autowired
	private WorkRepository workRepository;
	
	@Autowired
	private AttendenceRepository attendenceRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AccountService accountService;
	
	public Course saveCourse(Course course) {
		Course course1 =courseRepository.save(course);
		if(course1.getClassRoom()==null) {
			classRoomService.createClassRoom(course);
		}
		return course1;
	}
	
	
	public Course updateCourse(Course course) {
		Course course2 = courseRepository.getById(course.getId());
		course2.setName(course.getName());
		course2.setFees(course.getFees());
		course2.setSession(course.getSession());
		course2.setDuration(course.getDuration());
		course2.setAbout(course.getAbout());
		course2.setTime(course.getTime());
		Course course1 =courseRepository.save(course2);
		return course1;
	}
	
	
	public void deleteCourse(int cid) {
		Course course = courseRepository.getById(cid);
		
		//updating teacher
		if(course.getTeacher() != null) {
			Teacher teacher = course.getTeacher();
			List<Course> courses = teacher.getCourses();
			courses.remove(course);
			teacher.setCourses(courses);
			teacherRepository.save(teacher);
		}
		
		//deleting classroom
		if(course.getClassRoom()!= null) {
			ClassRoom classRoom = course.getClassRoom();
			List<ClassWork> classwork = classRoom.getClasswork();
			classwork.forEach(w->{workRepository.deleteAll(w.getWorks());});
			classWorkRepository.deleteAll(classwork);
			classRoomRepository.delete(classRoom);
		}
		
		//delete attendence
		if(course.getAttendence() != null) {
			List<Attendence> attendences = course.getAttendence();
			attendences.forEach(attendence->{
					attendence.setStudents(null);				
			});
			attendenceRepository.deleteAll(attendences);
		}
		
		//updating students
		if(course.getStudents() != null) {
			List<Student> students = course.getStudents();
			students.forEach(s->{
				List<Course> courses2 = s.getCourses();
				courses2.remove(course);
				s.setCourses(courses2);
				studentRepository.save(s);
			});
		}
		
		courseRepository.delete(course);
		
		
	}
	
	public List<Course> getAllCourses(){
		List<Course> courses = courseRepository.findAll();
		return courses;
	}
	
	public Course getCourseById(int cId) {
		return courseRepository.getById(cId);
	}
	
	
	public void assignCourse(int teacher_id, int course_id) {
		Teacher teacher = teacherRepository.getById(teacher_id);
		Course course = courseRepository.getById(course_id);
		course.setTeacher(teacher);
		courseRepository.save(course);
		
		//adding curse entry to account
		Account account;
		if(teacher.getAccount()==null) {
			accountService.openTeacherAccount(teacher);
		}
		account = teacher.getAccount();
		List<Transaction> transactions;
		Transaction transaction= new Transaction();
		transaction.setAmount(000);
		transaction.setMode(Integer.toString(course_id));
		transaction.setRemarks(course.getName());
		transaction.setType("COURSESTART");
		transaction.setTimestamp(Timestamp.from(Instant.now()));
		
		if(account.getTransactions()==null) {
			transactions = new ArrayList<Transaction>();
		}else {
			transactions= account.getTransactions();
		}
		transaction.setAccount(account);
		transactions.add(transaction);
		accountService.saveAccount(account);
	
	}
}
