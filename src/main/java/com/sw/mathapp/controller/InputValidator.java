package com.sw.mathapp.controller;

import org.springframework.stereotype.Component;

@Component
public class InputValidator {

	private static String COMMA_INT_CHECK = "[0-9,]+";
	private static String DOUBLE_CHECK = "^[^.]+$";


	public boolean validate(String toValidate) {

		boolean resultOfInputStringValidation = toValidate.matches(COMMA_INT_CHECK);
		boolean resultOfCheckInputForDoubleValues = toValidate.matches(DOUBLE_CHECK);

		return resultOfInputStringValidation || resultOfCheckInputForDoubleValues;

	}

}
