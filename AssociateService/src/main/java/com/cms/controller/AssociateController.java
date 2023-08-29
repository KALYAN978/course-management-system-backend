package com.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.service.IAssociateService;


@CrossOrigin
@RestController    //It indicates that the class is RestFul webservice controller.
public class AssociateController {
	
	@Autowired
	private IAssociateService associateService;
	
	
	@PostMapping("/associate/registerAssociate")
	public ResponseEntity<Associate> addAssociate(@RequestBody Associate associate) {
		try{
		 Associate addAssociate = associateService.addAssociate(associate); 
		return ResponseEntity.ok(addAssociate);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	
	
	@PutMapping("/associate/updateAssociateEmailId/{associateId}/{associateEmailId}")
	public ResponseEntity<Associate> updateAssociate(@PathVariable String associateId, @PathVariable String associateEmailId) {
		try {
			Associate updateAssociate = associateService.updateAssociate(associateId,associateEmailId);
			return ResponseEntity.ok(updateAssociate);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	
	
	@GetMapping("/associate/viewAssociate/{associateId}")
	public ResponseEntity<Associate> viewByAssociateId(@PathVariable String associateId) {
		try {
			Associate viewByAssociateId =  associateService.viewByAssociateId(associateId);
			return ResponseEntity.ok(viewByAssociateId);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	
	
	@GetMapping("/associate/viewAll")
	public ResponseEntity<List<Associate>> viewAll(){
		try {
		List<Associate> viewAll = associateService.viewAll();
		return ResponseEntity.ok(viewAll);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}

	
	
}





