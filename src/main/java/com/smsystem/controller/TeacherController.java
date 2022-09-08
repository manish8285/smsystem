package com.smsystem.controller;


import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smsystem.model.Attendence;
import com.smsystem.model.ClassWork;
import com.smsystem.model.Course;
import com.smsystem.model.Notice;
import com.smsystem.model.Student;
import com.smsystem.model.Teacher;
import com.smsystem.model.User;
import com.smsystem.model.Work;
import com.smsystem.repository.CourseRepository;
import com.smsystem.service.ClassRoomService;
import com.smsystem.service.CourseService;
import com.smsystem.service.NoticeService;
import com.smsystem.service.TeacherService;
import com.smsystem.service.UserService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	TeacherService teacherService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private CourseService courseService;
	@Autowired
	private ClassRoomService classRoomService;
	
	Teacher teacher;
	
	@ModelAttribute
	public void addCommonObject(Principal principal,Model model) {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		Teacher teacher2 = user.getTeacher();
		this.teacher=teacher2;
		model.addAttribute("teacher",teacher );
	}
	
	@GetMapping("/")
	public String homeTeacher() {
		return "teacher/home";
	}
	@GetMapping("/add_student")
	public String addStudent() {
		return "student/add_student";
	}
	
	@GetMapping("/courses")
	public String showCourses(Model model) {
		model.addAttribute("courses", this.teacher.getCourses());
		
		return "teacher/courses";
	}
	
	@GetMapping("/add_attendence/{cid}")
	public String showAddAttendence(@PathVariable("cid") Integer cid,Model model) {
		Course course = teacherService.getCourseById(this.teacher,cid);
		model.addAttribute("course", course );
		return "teacher/add_attendence";
	}
	

	@PostMapping("/process_attendence/{cid}")
	public String addAttendence(@RequestParam List<Integer> sids,@PathVariable("cid") Integer cid) {
		teacherService.markAttendence(sids, cid, this.teacher);
		return "redirect:/teacher/courses";
	}
	
	@GetMapping("/attendence/{cid}")
	public String showAttendence(@PathVariable("cid") Integer cid,Model model) {
		Course course = teacherService.getCourseById(this.teacher, cid);
		List<Attendence> attendence = course.getAttendence();
		model.addAttribute("attendence", attendence);
		return "teacher/attendence";
	}
	
	@GetMapping("/noticeboard")
	public String notiboard(Model model) {
		List<Notice> list = noticeService.getAllSortedNotice();
		model.addAttribute("notices", list);
		//model.addAttribute("notice",new Notice());
		return "teacher/noticeboard";
	}
	
	@GetMapping("/classroom/{cid}")
	public String classRoom(@PathVariable("cid") Integer cid,Model model) {
		Course course = courseService.getCourseById(cid);
		model.addAttribute("course", course);
		model.addAttribute("classwork", new ClassWork());
		model.addAttribute("newWork", new Work());
		model.addAttribute("classworks", course.getClassRoom().getClasswork());
		return "teacher/class_room";
	}
	
	@PostMapping("/classroom/classwork")
	public String addClassWork(@ModelAttribute ClassWork cw,
			@RequestParam("cid") Integer cid,
			@RequestParam("ref") MultipartFile file) {
		classRoomService.postClassWork(cw,cid,file);
		return "redirect:/teacher/classroom/"+cid;
	}
	
	@GetMapping("/classroom/classwork/delete/{cid}/{cwid}")
	public String deleteClassWork(@PathVariable("cwid") Integer cwid,
			@PathVariable("cid") Integer cid) {
		classRoomService.deleteClassWork(cwid);
		return "redirect:/teacher/classroom/"+cid;
	}
	@GetMapping("/classroom/work/update/{cid}")
	public String updateSubmittedWork(@ModelAttribute Work work,
			@PathVariable("cid") Integer cid) {
		classRoomService.updateMarks(work);
		return "redirect:/teacher/classroom/"+cid;
	}
	
	
	
	
}
