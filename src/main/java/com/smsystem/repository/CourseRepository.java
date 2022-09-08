package com.smsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smsystem.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{

}
