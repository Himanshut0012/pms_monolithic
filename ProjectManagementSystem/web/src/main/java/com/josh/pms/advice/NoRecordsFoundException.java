package com.josh.pms.advice;

public class NoRecordsFoundException extends Exception {
	public NoRecordsFoundException(String message) {
		super(message);
	}
}
