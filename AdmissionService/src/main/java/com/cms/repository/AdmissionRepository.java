package com.cms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.Repository;

import com.cms.model.Admission;

@Repository
public interface AdmissionRepository extends MongoRepository<Admission, String> {
	Admission findByRegistrationId(String registrationId);

	int getFeesByCourseId(String courseId);

	List<Admission> getAdmissionByAssociateId(String associateId);

	Optional<Admission> findByRegistrationId(Long registrationId);

	Optional<Admission> findByCourseId(String courseId);

	void deleteByRegistrationId(Long deleteAdmin);

	List<Admission> findByAssociateId(String associateId);


	Optional<Admission> findByRegistrationId(int registartionId);

//	Optional<Admission> existsByRegistrationId(long registrationId);
//
//	Optional<Integer> findByAssociateId(String associateId);
//
//	List<Admission> getAdmissionbyAssociateId(String associateId);
//
//	int getFeesByCourseId(String courseId);
//
//	List<String> getFeedbackByCourseId(String courseId);
//
//	List<Admission> getAdmissionByCourseId(String courseId);
}