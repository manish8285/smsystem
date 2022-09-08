package com.smsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smsystem.helper.SaveFile;
import com.smsystem.model.Account;
import com.smsystem.model.Attendence;
import com.smsystem.model.Course;
import com.smsystem.model.Student;
import com.smsystem.model.Transaction;
import com.smsystem.model.User;
import com.smsystem.repository.AttendenceRepository;
import com.smsystem.repository.CourseRepository;
import com.smsystem.repository.StudentRepository;
import com.smsystem.repository.TransactionRepository;
import com.smsystem.repository.UserRepository;

@Service
public class StudentService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AttendenceRepository attendenceRepositroy;

	public Student saveStudent(Student student) {
		if (student.getRegistrationNo() == 0) {
			student.setRegistrationNo(getNewRegNo());
		}
		Student student2 = studentRepository.save(student);
		return student2;
	}

	public Student getStudentById(int id) {
		return studentRepository.getById(id);
	}

	public List<Student> geteAllStudents() {
		return studentRepository.findAll();
	}
	
	public List<Student> searchByName(String query){
		return studentRepository.searchByName(query);
	}
	public List<Student> getStudentsByCourse(int course){

		return courseRepository.getById(course).getStudents();
	}

	public void updateStudent(Student student, MultipartFile file) {
		
		Student student2 = studentRepository.getById(student.getId());
		if(!file.isEmpty()) {
			if(student2.getProfile() != null) {
				SaveFile.deleteFile(student2.getProfile());
			}
			String saveFile = SaveFile.saveFile(file);
			student2.setProfile(saveFile);
		}
		student2.setName(student.getName());
		student2.setFatherName(student.getFatherName());
		student2.setEmail(student.getEmail());
		student2.setNumbers(student.getNumbers());
		student2.setAddress(student.getAddress());
		student2.setRollNo(student.getRollNo());
		student2.setRegistrationNo(student.getRegistrationNo());
		studentRepository.save(student2);

	}

	public int getNewRegNo() {
		Integer num = studentRepository.getLastRegNo();
		if(num==null) {
			return 0;
		}
		return  num+ 1;
	}
	 public int getNewRollNo(Course course) {
		 int[] rollNo = new int[1];
	 rollNo[0]=0; 
	 if(course.getStudents()==null) { 
		 return 0; }
	 List<Student> students = course.getStudents(); students.forEach(s->{
	 if(s.getRollNo()>rollNo[0]) { rollNo[0]=s.getRollNo(); } }); 
	 return rollNo[0]+1; 
	 } 
	 
	public void deleteStudent(int sid) {
		Student student = studentRepository.getById(sid);
		User user = student.getUser();
		user.setStudent(null);
		userRepository.save(user);
		if (student.getProfile() != null) {
			SaveFile.deleteFile(student.getProfile());
		}
		if (student.getCourses() != null) {
			List<Course> courses = student.getCourses();
			courses.forEach(c -> {
				if(c.getAttendence() != null) {
				List<Attendence> attendences = c.getAttendence();
				attendences.forEach(a -> {
					a.getStudents().remove(student);
					attendenceRepositroy.save(a);
				});
				c.setAttendence(attendences);
				}
				List<Student> students = c.getStudents();
				students.remove(student);
				c.setStudents(students);

				courseRepository.save(c);
			});
			student.setCourses(null);
		}
		if (student.getAccount() != null) {
			Account account = student.getAccount();
			if(account.getTransactions()!= null) {
				List<Transaction> transactions = account.getTransactions();
				transactionRepository.deleteAll(transactions);
				account.setTransactions(null);
				account.setUser(null);
				account.setStudent(null);
			accountService.deleteAccount(account);
			}
		}

		studentRepository.delete(student);
	}

	public void addCourseToStudent(int course_id, int student_id) {
		Student student = studentRepository.getById(student_id);
		List<Course> courses = student.getCourses();
		Course course = courseRepository.getById(course_id);
		courses.add(course);
		student.setCourses(courses);
		if (student.getRollNo() == 0) {
			student.setRollNo(getNewRollNo(course));
		}
		course.getStudents().add(student);
		courseRepository.save(course);

		Transaction transaction = new Transaction();
		transaction.setRemarks(course.getName());
		float amount = Float.parseFloat(course.getFees());
		transaction.setAmount(amount);
		transaction.setType("DEBIT");
		transaction.setMode("ONLINE");
		if (student.getAccount() == null) {
			accountService.openStudentAccount(student);
		}
		int acid = student.getAccount().getAcid();
		accountService.makeTransaction(transaction, acid);

	}

	public List<Attendence> getAttendence(int cid, int sid) {

		Course course = courseRepository.getById(cid);
		List<Attendence> myAttendence = new ArrayList<Attendence>();
		List<Attendence> attendence = course.getAttendence();
		attendence.forEach(att -> {
			att.getStudents().forEach(s -> {

				if (s.getId() == sid) {
					myAttendence.add(att);
				}
			});
		});
		return myAttendence;
	}

}
