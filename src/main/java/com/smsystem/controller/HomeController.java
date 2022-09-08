package com.smsystem.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smsystem.helper.Email;
import com.smsystem.helper.Message;
import com.smsystem.model.Course;
import com.smsystem.model.Enquery;
import com.smsystem.model.User;
import com.smsystem.service.CourseService;
import com.smsystem.service.EnqueryService;
import com.smsystem.service.GalleryService;
import com.smsystem.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private EnqueryService enqueryService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	Email emailService;
	
	@Autowired
	GalleryService galleryService;
	
	@GetMapping({"/home","/"})
	public String homePage(Model model) {
		model.addAttribute("enquery", new Enquery());
		List<Course> courses = courseService.getAllCourses();
		model.addAttribute("courses", courses);
		model.addAttribute("metadata",null);
		
		return "home";
	}
	
	@GetMapping("/about")
	public String aboutPage(Model model) {
		model.addAttribute("metadata",null);
		return "about";
	}
	
	@GetMapping("/class")
	public String classPage(Model model) {
		model.addAttribute("metadata",null);
		return "class";
	}
	
	//about teachers page
	@GetMapping("/team")
	public String teamPage(Model model) {
		model.addAttribute("metadata",null);
		return "team";
	}
	
	@GetMapping("/gallery")
	public String galleryPage(Model model) {
		model.addAttribute("metadata",null);
		model.addAttribute("galleryList", galleryService.getAllByDate());
		return "gallery";
	}
	@GetMapping("/contact")
	public String contactPage(Model model) {
		model.addAttribute("metadata",null);
		model.addAttribute("enquery", new Enquery());
		return "contact";
	}
	
	@GetMapping("/events")
	public String eventsPage(Model model) {
		model.addAttribute("metadata",null);
		return "events";
	}
	@GetMapping("/events/notice")
	public String singleNoticePage(Model model) {
		model.addAttribute("metadata",null);
		return "single";
	}
	
	//--------------------->
	
	@GetMapping("/signup")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "register_user";
	}
	@RequestMapping(path="/otp/",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> sendOtp(@RequestBody Map<String,Object> data,HttpSession session) {
		Message message= new Message("success","OTP has sent","OTP has been sent on the email !!");
		String email =  data.get("email").toString();
		
		int sentOtp = emailService.sendOtp(email);
		session.setAttribute("message", message);
		session.setAttribute("otp",sentOtp);
		//return ResponseEntity.ok();
		return ResponseEntity.ok("OTP has been sent successfully !!!");
	}
	
	@PostMapping("/process_user_signup")
	public String addNewUser(@ModelAttribute User user,@RequestParam("DOB") String DOB,@RequestParam("OTP") Integer OTP, HttpSession session) {
		int pOtp =(Integer) session.getAttribute("otp");
		if(pOtp != OTP) {
			session.setAttribute("message", new Message("danger","Wrong OTP","Please Enter a valid OTP"));
			 return "redirect:/signup/";
		}
		user.setEnabled(true);
		user.setRole("ROLE_USER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			user.setDob(sdf.parse(DOB));
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("dob ="+user.getDob()+" otp = "+OTP+" session otp ="+pOtp);
		userService.addNewUser(user);	
		return "redirect:/user/home";
	}
	
	@PostMapping("/process_enquery")
	public String processEnquery(@ModelAttribute Enquery enquery,HttpSession session) {
		Message message = new Message();
		message.setMessage("Message has been sent successfully !!!");
		message.setType("success");
		session.setAttribute("message", message);
		enqueryService.saveEnquery(enquery);
		return "redirect:/contact/";
	}
}
