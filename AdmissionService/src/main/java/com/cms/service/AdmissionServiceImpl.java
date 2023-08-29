package com.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.model.Associate;
import com.cms.model.Course;
import com.cms.payment.PaypalService;
import com.cms.repository.AdmissionRepository;
import com.paypal.api.payments.Payment;

@Service
public class AdmissionServiceImpl implements IAdmissionService {

	@Autowired
	private AdmissionRepository admissionRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PaypalService paypalService;

	@Override
	public Admission registerAssociateForCourse(String associateId, String courseId) throws AdmissionInvalidException {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String associateUrl = "http://GATEWAY/associate/viewAssociate/" + associateId;
		ResponseEntity<Associate> associate = restTemplate.exchange(associateUrl, HttpMethod.GET, entity,
				Associate.class);
		Associate associateResponse = associate.getBody();
		String courseUrl = "http://GATEWAY/courses/course/viewByCourseId/" + courseId;
		ResponseEntity<Course> course = restTemplate.exchange(courseUrl, HttpMethod.GET, entity, Course.class);
		Course courseResponse = course.getBody();

		if (associateResponse != null && courseResponse != null) {
			Admission admission = new Admission();
			admission.setAssociateId(associateId);
			admission.setCourseId(courseId);
			admission.setFees(courseResponse.getFees());
			long adminId = sequenceGeneratorService.getNextAdminId();
			admission.setRegistrationId(adminId);
			admissionRepository.save(admission);
		}
		return null;

	}

	@Override
	public int calculateFees(String associateId) throws AdmissionInvalidException {
		List<Admission> admissionObjects = admissionRepository.findByAssociateId(associateId);
		int totalFees = 0;
		for (int i = 0; i < admissionObjects.size(); i++) {
			Admission admission=admissionObjects.get(i);
			int fees=admission.getFees();
			totalFees = totalFees + fees;
		}

		return totalFees;
	}

	@Override
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
//		Admission admission = new Admission();
//		admission.getRegistrationId();
//		admission.getCourseId();
		Admission admission = null;
		String cId = null;
		Optional<Admission> admission1 = admissionRepository.findByRegistrationId(regNo);
		if (admission1.isPresent()) {
			admission = admission1.get();
			admission.setFeedback(feedback);
			admissionRepository.save(admission);
			cId = admission.getCourseId();
		}
		String courseUrl = "http://GATEWAY/courses/calculateAverageFeedback/" + cId + "/" + feedbackRating;
		restTemplate.exchange(courseUrl, HttpMethod.PUT, entity, Course.class);

		return admission;
	}

	@Override
	public List<String> highestFeeForTheRegisteredCourse(String associateId) throws AdmissionInvalidException {
		List<Admission> admissions = admissionRepository.findByAssociateId(associateId) ;
		List<String>str=new ArrayList<>();
		int maxFees=0;
		for(int i=0;i<admissions.size();i++)
		{
			if(admissions.get(i).getFees()>maxFees)
			{
				maxFees=admissions.get(i).getFees();
				
			}
				
		}
		
		for(int i=0;i<admissions.size();i++)
		{
			if(admissions.get(i).getFees()==maxFees)
			{
				String courseid=admissions.get(i).getCourseId();
				str.add(courseid);
				
			}
				
		}
		return str;
	}

	@Override
	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		List<String> feedbackList = new ArrayList<>();
		Optional<Admission> isCoursePresent = admissionRepository.findByCourseId(courseId);
		if (isCoursePresent.isPresent()) {
			String feedback = isCoursePresent.get().getFeedback();
			feedbackList.add(feedback);

		}
		return feedbackList;
	}

	@Override
	public boolean deactivateAdmission(String courseId) throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		Optional<Admission> isCoursePresent = admissionRepository.findByCourseId(courseId);
		if (isCoursePresent.isPresent()) {
			Admission admission = isCoursePresent.get();
			Long deleteAdmin = admission.getRegistrationId();
			admissionRepository.deleteByRegistrationId(deleteAdmin);
			return true;
		}
		return false;
	}

	@Override
	public boolean makePayment(int registartionId, int fees) throws AdmissionInvalidException {
		// TODO Auto-generated method stub
		Optional<Admission>isAdmission= admissionRepository.findByRegistrationId(registartionId);
		if(isAdmission.isPresent())
		{
			Double total = (double) fees;
			String currency = "INR";
			String method = "direct";
			String intent = "esx";
			String description = "description"; 
			String cancelUrl = "cancel";
			String successUrl = "success";
			try {
				//creating payment using paypal service
			Payment payment = paypalService.createPayment(total, currency, currency, intent, description, cancelUrl, successUrl);
			if(payment.getState().equals("approved")) {
				// Payment successful, u might want to update your admission record here
				// Assuming you have a method to update payment status in Admission
				
				//admission.setPayemntStatus("paid");
				//admissionRepository.save(admission);
				
			}
			Payment payment1 = paypalService.executePayment(cancelUrl, successUrl);
			}
			catch(Exception e) {
				 
			}
		
			
			return true;
		}
		return false;
	}

	@Override
	public List<Admission> viewAll() {
		return admissionRepository.findAll();
	}

}