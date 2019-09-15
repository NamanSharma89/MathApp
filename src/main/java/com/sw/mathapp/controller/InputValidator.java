package com.sw.mathapp.controller;

import org.springframework.stereotype.Component;

@Component
public class InputValidator {

	private static String COMMA_INT_CHECK = "^[0-9,]+$";

	public boolean validate(String toValidate) {
		boolean resultOfInputStringValidation = toValidate.matches(COMMA_INT_CHECK);
		return resultOfInputStringValidation;
	}
}
