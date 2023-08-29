
package com.cms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//lombok annotation helps reduce boilerplate code by automatically generating common methods and constructors.
@Document(collection = "courses") // The instances of the class will be stored in a mongodb collection
@Getter
@Setter // generates getter and setter methods
@NoArgsConstructor // Initializes the object with default values.
@AllArgsConstructor // Initializes the constructor by taking all the fields of that class
public class Course {

	@Id
	private String courseId; 
	private String courseName;
	private Integer fees;
	private Integer duration;
	private String courseType;
	private float rating;

}
