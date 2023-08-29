package com.cms.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "admission")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Admission implements Serializable {

	@Id
	private long registrationId;
	private String courseId;
	private String associateId;
	private int fees;
	private String feedback;

}
