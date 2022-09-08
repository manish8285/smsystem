package com.smsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smsystem.model.Student;
import com.smsystem.service.StudentService;


@RestController
public class SearchController {

	@Autowired
	StudentService studentService;

	
	@PostMapping("/admin/search/student")
	public List<Student> searchStudentByName(@RequestParam("query") String query ){
		
		List<Student> students = studentService.searchByName(query);
		return students;
	}
	
	@PostMapping("/admin/search/students")
	public List<Student> searchStudentByCourse(@RequestParam("query") Integer course ){
		
		List<Student> students = studentService.getStudentsByCourse(course);
		System.out.println(students.size());
		return students;
	}
	
}
