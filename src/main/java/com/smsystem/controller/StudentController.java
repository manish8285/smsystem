package com.smsystem.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.razorpay.*;

import com.smsystem.model.Address;
import com.smsystem.model.Attendence;
import com.smsystem.model.ClassWork;
import com.smsystem.model.Course;
import com.smsystem.model.Notice;
import com.smsystem.model.Student;
import com.smsystem.model.Transaction;
import com.smsystem.model.User;
import com.smsystem.model.Work;
import com.smsystem.service.AccountService;
import com.smsystem.service.ClassRoomService;
import com.smsystem.service.CourseService;
import com.smsystem.service.NoticeService;
import com.smsystem.service.StudentService;
import com.smsystem.service.UserService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	private float amount;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ClassRoomService classRoomService;
	
	@ModelAttribute
	public void commondata(Model model,Principal principal) {
		String name = principal.getName();
		User user = userService.getUserByUsername(name);
		Student student = user.getStudent();
		model.addAttribute("student", student);
	}

	@GetMapping("/noticeboard")
	public String notiboard(Model model) {
		List<Notice> list = noticeService.getAllSortedNotice();
		model.addAttribute("notices", list);
		//model.addAttribute("notice",new Notice());
		return "student/noticeboard";
	}
	
	@GetMapping("/")
	public String homePage() {
		
		return "student/home";
	}
	@GetMapping("/courses")
	public String courses() {
		
		return "student/courses";
	}
	@GetMapping("/attendence/course/{cid}")
	public String courses(@PathVariable("cid") int cid,Model model) {
		model.addAttribute("course", courseService.getCourseById(cid));
		return "student/attendence";
	}
	
	@ResponseBody
	@GetMapping("/course_attendence/{cid}")
	public List<String> courseAttndence(@PathVariable("cid") int cid,Principal principal) {
		String username = principal.getName();
		int sid = userService.getUserByUsername(username).getStudent().getId();
		List<Attendence> attendence = studentService.getAttendence(cid, sid); //student id = 1
		List<String> sa= new ArrayList<>();
		Date parse=null;
		SimpleDateFormat df = new SimpleDateFormat("MMMM/dd/yyyy");
		String format=null;
		attendence.forEach(a->{sa.add(df.format(a.getDate()));});	
		return sa;
	}
	
	@GetMapping("/account")
	public String showAccount() {
		return "student/account";
	}
	
	
	//payments ---------------------------->
	@ResponseBody
	@PostMapping("/payment/request")
	public String generatePayment(@RequestBody Map<String,Object> data) throws Exception{
		this.amount= Float.parseFloat(data.get("amount").toString());
		float amnt = Float.parseFloat(data.get("amount").toString());
		String key="rzp_test_2ra6BDdCQY0C8x";
		String sec = "mJDgHSv7tT2WkcpgXwpDrcsh";
		
		RazorpayClient client = new RazorpayClient(key, sec);
		JSONObject options = new JSONObject();
		options.put("amount", amnt*100);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		Order order = client.orders.create(options);
		return order.toString();
	}
	
	@PostMapping("/payment/add")
	@ResponseBody
	public String sucessPayment(@RequestBody Map<String,Object> data) {
		int acid = Integer.parseInt(data.get("account_id").toString());	
		Transaction transaction = new Transaction();
		transaction.setAmount(this.amount);
		transaction.setMode("ONLINE");
		transaction.setType("CREDIT");
		transaction.setRemarks("SELF/"+"PAYMENT ID-"+data.get("payment_id").toString());
		accountService.makeTransaction(transaction, acid);	
		return data.toString();
	}
	
	//class room------------->
	
	@GetMapping("/classroom/{cid}")
	public String classRoom(@PathVariable("cid") Integer cid,Model model) {
		Course course = courseService.getCourseById(cid);
		model.addAttribute("course", course);
		model.addAttribute("work", new Work());
		model.addAttribute("classworks", course.getClassRoom().getClasswork());
		return "student/class_room";
	}
	
	@PostMapping("/classroom/work/submit")
	public String submitClassWork(@ModelAttribute Work work,
			@RequestParam("workFile") MultipartFile file,
			@RequestParam("cwid") Integer cwid,
			Principal principal) {
		User user =userService.getUserByUsername(principal.getName());
		Student st = user.getStudent();
		work.setStudent(st);
		work.setClassWork(classRoomService.getClassWorkById(cwid));
		classRoomService.saveWork(file, work);
		return "redirect:/student/courses/";
	}
	
}
