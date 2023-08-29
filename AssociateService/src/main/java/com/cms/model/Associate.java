package com.cms.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
//lombok annotation helps reduce boilerplate code by automatically generating common methods and constructors.
@Document(collection = "associate")  
@Getter
@Setter
@NoArgsConstructor  //Initializes the object with default values.
@AllArgsConstructor  //Initializes the constructor by taking all the fields of that class
public class Associate {
	
	@Id         // generatedValue - is used to specify how the primary key values should be generated for an entity.
	//@GeneratedValue(strategy = GenerationType.AUTO)  
	private String associateId;	   // strategy - it specifies that the strategy to be used for generating the primary key values.
	private String associateName;	
	private String associateAddress;	
	private String associateEmailId;


}
