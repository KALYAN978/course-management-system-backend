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
import org.springframework.web.bind.annotation.RestController;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.proxy.CourseProxy;
import com.cms.service.IAdmissionService;

@CrossOrigin
@RestController
public class AdmissionController {


	@Autowired
	private IAdmissionService admissionService;

	@PostMapping("/admission/register/{associateId}/{courseId}")
	public ResponseEntity<Admission> registerAssociateForCourse(@RequestBody Admission admission, @PathVariable String associateId,
			@PathVariable String courseId) {

		try {
			Admission savedAdmission = admissionService.registerAssociateForCourse(associateId, courseId);
			return ResponseEntity.status(HttpStatus.OK).body(savedAdmission);
		} catch (AdmissionInvalidException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@PutMapping("/admission/calculateFees/{associateId}")
	public ResponseEntity<Integer> calculateFees(@PathVariable String associateId) {
		try {
			int calculateFees = admissionService.calculateFees(associateId);
			return ResponseEntity.ok(calculateFees);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/admission/feedback/{regNo}/{feedback}/{feedbackRating}")
	public ResponseEntity<Admission> addFeedback(@PathVariable Long regNo, @PathVariable String feedback,
			@PathVariable float feedbackRating) {
		try {
			Admission addFeedback = admissionService.addFeedback(regNo, feedback, feedbackRating);
			return ResponseEntity.ok(addFeedback);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@GetMapping("/admission/highestFee/{associateId}")
	public ResponseEntity<List<String>> highestFeeForTheRegisteredCourse(@PathVariable String associateId) {
		try {
			List<String> highestFeeForTheRegisteredCourse = admissionService
					.highestFeeForTheRegisteredCourse(associateId);
			return ResponseEntity.ok(highestFeeForTheRegisteredCourse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/admission/viewFeedbackByCourseId/{courseId}")
	public ResponseEntity<List<String>> viewFeedbackByCourseId(@PathVariable String courseId) {
		try {
			List<String> viewFeedbackByCourseId = admissionService.viewFeedbackByCourseId(courseId);
			return ResponseEntity.ok(viewFeedbackByCourseId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/admission/deactivate/{courseId}")
	public ResponseEntity<Boolean> deactivateAdmission(@PathVariable String courseId) {
		try {
			boolean deactivateAdmission = admissionService.deactivateAdmission(courseId);
			return ResponseEntity.ok(deactivateAdmission);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/admission/makePayment/{registartionId}/{fees}")
	public ResponseEntity<Boolean> makePayment(@PathVariable Integer registrationId, @PathVariable Integer fees) {
		try {
			boolean makePayment = admissionService.makePayment(registrationId,fees);
			return ResponseEntity.ok(makePayment);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/admission/viewAll")
	public ResponseEntity<List<Admission>> viewAll() {
		try {
			List<Admission> viewAll = admissionService.viewAll();
			return ResponseEntity.ok(viewAll);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}