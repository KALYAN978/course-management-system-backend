package com.cms.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cms.model.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, Integer> {


	Optional<Course> findByCourseId(String courseId);

	Optional<Course> deleteByCourseId(String courseId);

	


	
	
	
}
