package com.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.service.ICourseService;

@CrossOrigin
@RestController  //It indicates that the class will handle the Restful web services
public class CourseController {
		
	@Autowired  
	private final ICourseService courseService;
	
	public CourseController(ICourseService courseService) { 
		this.courseService = courseService; 
	}
	
	
	@PostMapping("/courses/course/addCourse")          //ResponseEntity - (Http response. we can set HTTP status code,header & body of the response
	public ResponseEntity<Course> addCourse(@RequestBody Course course){    //Requestbody - which is used to bind the request body to a method parameter		
		try {                             
			Course addedCourse = courseService.addCourse(course);
			return ResponseEntity.ok(addedCourse);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}		
	}
	
	
	@PutMapping("/courses/course/update/{courseId}/{courseFees}")
	public ResponseEntity<Course> updateCourse(@PathVariable String  courseId,@PathVariable(name = "courseFees") Integer fees) {

 		try {
			Course updateCourse = courseService.updateCourse(courseId, fees);
			return ResponseEntity.ok(updateCourse);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	@GetMapping("/courses/course/viewAll")
	public ResponseEntity<List<Course>> viewAll(){
		try {
			List<Course> viewAllCourses =  courseService.viewAll();
			return ResponseEntity.ok(viewAllCourses);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	@GetMapping("/courses/course/viewByCourseId/{courseId}")
	public ResponseEntity<Course> viewByCourseId(@PathVariable String courseId) {
		try {
		Course viewByCourseId =  courseService.viewByCourseId(courseId);
		return ResponseEntity.ok(viewByCourseId);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/courses/viewFeedback/{courseId}")
	public ResponseEntity<Float> viewFeedback(@PathVariable String courseId){
		try {
			float feedbacks = courseService.findFeedbackRatingForCourseId(courseId);
			return ResponseEntity.ok(feedbacks);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	 
	@PutMapping("/courses/calculateAverageFeedback/{courseId}/{rating}")
	public ResponseEntity<Course> calculateAverageFeedbackAndUpdate(@PathVariable String courseId, @PathVariable float rating){
		try {
			Course averageFeedback = courseService.calculateAverageFeedbackAndUpdate(courseId,rating);
			return ResponseEntity.ok(averageFeedback);
			}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
		
		
	@DeleteMapping("/courses/course/deactivate/{courseId}")
	public ResponseEntity<Course> deactivateCourse(@PathVariable String courseId) {
		try {
		Course deactivateCourse = courseService.deactivateCourse(courseId);
		return ResponseEntity.ok(deactivateCourse);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	
	
}
