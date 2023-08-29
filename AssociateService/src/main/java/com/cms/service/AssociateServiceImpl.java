package com.cms.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AssociateServiceImpl implements IAssociateService{
	
	@Autowired
	private AssociateRepository associateRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	private final Logger log = LoggerFactory.getLogger(AssociateServiceImpl.class);
	
	public Associate addAssociate(Associate cObj) throws AssociateInvalidException {
		Optional<Associate> associate = associateRepository.findByAssociateId(cObj.getAssociateId());
		if(associate.isPresent()) {
			log.error("AssociateId already exists");
			throw new AssociateInvalidException("AssociateId already exists");
		}
		String nextAssociateId = sequenceGeneratorService.getNextAssociateId(); 
		cObj.setAssociateId(nextAssociateId);
		Associate addedAssociate = associateRepository.save(cObj);
		log.info("This method added associate successfully");
		return   addedAssociate;
	}

	
	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
		Optional<Associate> associate = associateRepository.findByAssociateId(associateId);
		if(associate.isPresent()) {
			log.info("This method findout the required associateId successfully");
			return  associate.get();
		}
		log.error("U have given Invalid AssociateId");
		throw new AssociateInvalidException("Associate Id does not exist");
	}

	
	public Associate updateAssociate(String associateId, String associateEmailId)throws AssociateInvalidException {
		Optional<Associate> associate = associateRepository.findByAssociateId(associateId);
		if(associate.isPresent()) {
			Associate updateAssociate = associate.get();
			updateAssociate.setAssociateEmailId(associateEmailId);
			Associate updatedAssociate = associateRepository.save(updateAssociate);
			log.info("The method updateAssociate has successfully updated the EmailId successfully");
			return updatedAssociate;
		}
		log.error("U have given the Invalid AssociateId");
		throw new AssociateInvalidException("AssociateId does not exists");
	}

	
	public List<Associate> viewAll() {
		log.info("This method has given the list of all Associates");
		return  associateRepository.findAll();
	}
}

