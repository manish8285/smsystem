package com.smsystem.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smsystem.model.Course;
import com.smsystem.model.Enquery;
import com.smsystem.model.Gallery;
import com.smsystem.model.Notice;
import com.smsystem.model.Student;
import com.smsystem.model.Teacher;
import com.smsystem.model.User;
import com.smsystem.service.AccountService;
import com.smsystem.service.CourseService;
import com.smsystem.service.EnqueryService;
import com.smsystem.service.GalleryService;
import com.smsystem.service.NoticeService;
import com.smsystem.service.StudentService;
import com.smsystem.service.TeacherService;
import com.smsystem.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	TeacherService teacherService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EnqueryService enqueryService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	GalleryService galleryService;
	
	@GetMapping("/")
	public String homeAdmin() {
		return "admin/home";
	}
	
	@ModelAttribute
	public void adminLogin(Principal principal,Model model) {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		model.addAttribute("admin", user);
	}
	
	// teachers ------------------------------>
	
	@GetMapping("/teachers")
	public String showTeachers(Model model) {
		List<Teacher> teachers = teacherService.getAllTeachers();
		model.addAttribute("teachers", teachers);
		model.addAttribute("teacher", new Teacher());
		return "admin/teachers";
	}
	
	@GetMapping("/add_teacher/{uid}")
	public String addAdmin(@PathVariable("uid") Integer uid,Model model) {
		model.addAttribute("teacher", new Teacher());
		model.addAttribute("user", userService.getUserById(uid));
		return "admin/add_teacher";
	}
	
	@PostMapping("/process_add_teacher")
	public String processAddTeacher(@ModelAttribute Teacher teacher) {	
		teacher.setDate(new Date().toString());
		teacherService.saveTeacher(teacher);
		
		return "redirect:/admin/teachers";
	}
	@PostMapping("/teacher/update")
	public String updateTeacher(@ModelAttribute Teacher teacher,@RequestParam("myfile") MultipartFile file) {
		teacher.setDate(new Date().toString());
		teacherService.updateTeacher(teacher, file);
		return "redirect:/admin/teachers";
	}
	@GetMapping("/teacher/delete/{tid}")
	public String deleteTeacher(@PathVariable("tid") Integer tid) {
		teacherService.deleteTeacher(tid);
		return "redirect:/admin/teachers";
	}
	
	// courses -------------------------------->
	@GetMapping("/courses")
	public String showCourses(Model model) {
		List<Course> courses = courseService.getAllCourses();
		//courses.forEach(c->System.out.println(c.getStudents().size()));
		model.addAttribute("courses", courses);
		return "admin/courses";
	}
	
	
	@GetMapping("/add_course")
	public String addCourse(Model model) {
		model.addAttribute("course", new Course());
		return "admin/add_course";
	}
	@PostMapping("/process_add_course")
	public String processAddCourse(@ModelAttribute Course course) {	
		System.out.println(course.getName());
		courseService.saveCourse(course);
		
		return "redirect:/admin/courses";
	}
	@PostMapping("/process_update_course")
	public String processUpdateCourse(@ModelAttribute Course course) {	
		System.out.println(course.getName());
		courseService.updateCourse(course);
		
		return "redirect:/admin/courses";
	}
	
	@PostMapping("/course/teacher")
	public String processAssignTeacher(@RequestParam("cid") Integer cid,@RequestParam("tid") Integer tid) {
		courseService.assignCourse(tid, cid);
		return "redirect:/admin/course/"+cid;
	}
	
	@GetMapping("/course/{cId}")
	public String showCourse(@PathVariable("cId") Integer cId,Model model) {
		Course course = courseService.getCourseById(cId);
		model.addAttribute("course",course);
		List<Teacher> teachers = teacherService.getAllTeachers();
		model.addAttribute("teachers", teachers);
		return "admin/single_course";
	}
	@GetMapping("/course/delete/{cId}")
	public String deleteCourse(@PathVariable("cId") Integer cId) {
		courseService.deleteCourse(cId);
		return "redirect:/admin/courses";
	}
	
	//students ------------------------>
	
	@GetMapping("/students")
	public String showStudents(Model model) {
		List<Student> students = studentService.geteAllStudents();
		model.addAttribute("students", students);
		model.addAttribute("courses", courseService.getAllCourses());
		return "admin/students";
	}
	
	@GetMapping("/student/add_student/{userId}")
	public String showAddStudent(@PathVariable("userId") Integer userId,Model model) {
		model.addAttribute("student", new Student());
		model.addAttribute("user",userService.getUserById(userId));
		return "admin/add_student";
	}
	
	@PostMapping("/student/process_add_student")
	public String processAddStudent(@ModelAttribute Student student) {
		//Student student =studentService.getStudentById(sid);
		student.setAddBy("BY_ADMIN");
		studentService.saveStudent(student);
		return "redirect:/admin/students";
	}
	
	@GetMapping("/student/add_course/{cid}")
	public String addCourseToStudent(@PathVariable("cid") Integer cid,Model model) {
		model.addAttribute("student", studentService.getStudentById(cid));
		model.addAttribute("courses", courseService.getAllCourses());
		model.addAttribute("newstudent", new Student());
		return "admin/student_add_course";
	}
	
	@PostMapping("/student/process_add_course")
	public String processAddStudentCourse(@RequestParam("cid") Integer cid , @RequestParam("sid") Integer sid) {
		studentService.addCourseToStudent(cid, sid);
		return "redirect:/admin/students";
	}
	@PostMapping("/student/process_update_student")
	public String processUpdateStudent(@ModelAttribute Student student, @RequestParam("myfile") MultipartFile file) {
		studentService.updateStudent(student,file);
		return "redirect:/admin/students";
	}
	@GetMapping("/student/delete/{sid}")
	public String processdeleteStudent(@PathVariable("sid") Integer sid) {
		studentService.deleteStudent(sid);
		return "redirect:/admin/students";
	}
	
	
	//accounts----------------------------->
	@GetMapping("/accounts")
	public String showAllAccounts(Model model) {
		model.addAttribute("accounts", accountService.getAllAccounts());
		return "admin/accounts";
	}
	
	
	//users ------------------------------------>
	
	@GetMapping("/users")
	public String showAllUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("accountants", userService.getUserByRole("ROLE_ACCOUNTANT"));
		model.addAttribute("admins", userService.getUserByRole("ROLE_ADMIN"));
		model.addAttribute("other_users", userService.getUserByRole("ROLE_USER"));
		model.addAttribute("students", userService.getUserByRole("ROLE_STUDENT"));
		model.addAttribute("teachers", userService.getUserByRole("ROLE_TEACHER"));
		model.addAttribute("user", new User());
		return "admin/users";
	}
	
	
	
	@PostMapping("/add_new_user")
	public String addNewUser(@ModelAttribute User user,@RequestParam("isEnabled") Integer f) {
		//System.out.println("admin c flag"+f);
		if(f==1) {
			user.setEnabled(true);
		}
		userService.addNewUser(user);
		return "redirect:/admin/users";
	}
	
	// enquery ------------------->
	
	@GetMapping("/enqueries")
	public String enquery(Model model) {
		List<Enquery> enqueries = enqueryService.getAllEnquery();
		model.addAttribute("enqueries", enqueries);
		return "admin/enquery";
	}
	
	@PostMapping("/enquery/delete/{eid}")
	public String deleteEnquery(@PathVariable("eid") int eid) {
		enqueryService.deleteEnquery(eid);
		return "redirect:/admin/enqueries";
	}
	@PostMapping("/enquery/status/{eid}")
	public String setEnqueryStatus(@PathVariable("eid") int eid) {
		enqueryService.setEnqueryStatus(eid, "RESPONDED");
		return "redirect:/admin/enqueries";
	}
	
	// notice board ------------------->
	
	@GetMapping("/noticeboard")
	public String notiboard(Model model) {
		List<Notice> list = noticeService.getAllSortedNotice();
		model.addAttribute("notices", list);
		model.addAttribute("notice",new Notice());
		return "admin/noticeboard";
	}
	
	@PostMapping("/notice/save")
	public String saveNotice(@ModelAttribute Notice notice,@RequestParam("myfile") MultipartFile file, Principal principal ) {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		notice.setIssueby(user.getName());
		noticeService.saveNoticeWithFile(notice, file);
		return "redirect:/admin/noticeboard/";
	}
	
	@GetMapping("/notice/delete/{nid}")
	public String deleteNotice(@PathVariable("nid") Integer nid) {
		noticeService.deleteNotice(nid);
		return "redirect:/admin/noticeboard";
	}
	
	// Gallery---------------------->
	@GetMapping("/gallery")
	public String gallery(Model model) {
		model.addAttribute("gallery", new Gallery());
		model.addAttribute("galleryList", galleryService.getAllByDate());
		return "admin/gallery";
	}
	
	@PostMapping("/upload_to_gallery")
	public String uploadGallery(@ModelAttribute Gallery gallery,@RequestParam("picture") MultipartFile file) {
		galleryService.saveToGallery(gallery,file);
		return "redirect:/admin/gallery/";
	}
	
	@GetMapping("/gallery/delete/{gid}")
	public String deleteGallery(@PathVariable("gid") Integer gid){
		galleryService.deleteGallery(gid);
		return "redirect:/admin/gallery";
	}
	
	@ResponseBody
	@GetMapping("/test")
	public String test() {
		System.out.println("called test controller");
//		studentService.getNewRollNo();
		return "called";
	}

}
