package com.cms.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;

import lombok.extern.slf4j.Slf4j;

@Service // inorder to make this class as a service we use @Service
@Slf4j
public class CourseServiceImpl implements ICourseService {

	private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException {
		Optional<Course> course = courseRepository.findByCourseId(cObj.getCourseId());
		if (course.isPresent()) {
			throw new CourseInvalidException("CourseId already exists");
		}
		String courseId = sequenceGeneratorService.getNextCourseId();
		cObj.setCourseId(courseId);
		Course addCourse = courseRepository.save(cObj);
		return addCourse;

	}

	@Override
	public Course updateCourse(String courseId, Integer fees) throws CourseInvalidException {
		Optional<Course> course = courseRepository.findByCourseId(courseId);
		if (course.isPresent()) {
			Course updateCourseFees = course.get();
			updateCourseFees.setFees(fees);
			Course updateCourse = courseRepository.save(updateCourseFees);
			log.info("updateCourse Method: course Fees has been updated successfully");
			return updateCourse;
		}
		log.error("No course found with the given courseId");
		throw new CourseInvalidException("CourseId does not exists");
	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		log.info("Inside get course by id");
		Optional<Course> course = courseRepository.findByCourseId(courseId);
		if (course.isPresent()) {
			return course.get();
		}
		log.error("No course found with the given courseId");
		throw new CourseInvalidException("Invalid Course Id");
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		Optional<Course> course = courseRepository.findByCourseId(courseId);
		if (course.isEmpty()) {
			log.error("Error in calculateAverageFeedback");
			throw new CourseInvalidException("CourseId doesnot exist");
		}
		Course cObj = new Course();
		float currentRating = cObj.getRating();
		int feedbackCount = 1;

		float averageRating = (currentRating + rating) / (feedbackCount + 1);
		cObj.setRating(averageRating);
 		return courseRepository.save(cObj);

	}

	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {
		Optional<Course> course = courseRepository.findByCourseId(courseId);
		if (course.isEmpty()) {
			log.error("Error in findFeedbackRatingForCourseId");
			throw new CourseInvalidException("CourseId doesnot exist");
		}
		Course cObj = new Course();
		float rating = cObj.getRating();
		log.info("The Method findFeedbackRating has completed successfully");
		return rating;
	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {
		Optional<Course> course = courseRepository.findByCourseId(courseId);
		if (course.isPresent()) {
			courseRepository.deleteByCourseId(courseId);
			log.info("The Given courseId has been deleted successfully");
		}
		log.error("No course found with the given courseId");
		throw new CourseInvalidException("CourseId does not exists");

	}

	@Override
	public List<Course> viewAll() {
		log.info("viewAll: This Method has listed all the courses");
		return courseRepository.findAll();

	}

}
