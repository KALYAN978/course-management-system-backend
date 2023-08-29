package com.cms.service;

import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

	private int currentSequenceNumber;

	public SequenceGeneratorService() {
		currentSequenceNumber = 300;
	}

	public long getNextAdminId() {
		currentSequenceNumber++;
		Long convertedCurrentSequenceNumber = Long.valueOf(currentSequenceNumber);
		return convertedCurrentSequenceNumber;
	}

}