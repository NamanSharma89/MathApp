package com.sw.mathapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sw.mathapp.service.SortNumbers;
import com.sw.mathapp.service.SumOfAllDivisors;

@RestController
public class MathAppController {
	// TODO: Look into auto-wiring and how it works with DI?
	@Autowired
	SumOfAllDivisors sumOfAllDivisors;
	SortNumbers sortNumbers;
	/*
	 * public MathAppController(final SumOfAllDivisors sumOfAllDivisors) { // TODO:
	 * Look into super again. super(); this.sumOfAllDivisors = sumOfAllDivisors; }
	 */

	@GetMapping("/sumofdivisors/{numberForDivisorSum}")
	public int getSumOfDivisorsController(@PathVariable(value = "numberForDivisorSum") final int numberForDivisorSum) {
		return sumOfAllDivisors.getSumOfAllDivisors(numberForDivisorSum);

	}

	@PostMapping("/sortnumbers/{sortNumberInput}")
	public ResponseEntity<String> getSortedNumberList(@PathVariable(value = "sortNumberInput") final String sortNumberInput) {
		try {
			String checkInputForCsvValue= "[0-9, /,]+";
			boolean resultOfInputStringValidation=sortNumberInput.matches(checkInputForCsvValue);
			System.out.println(resultOfInputStringValidation);
			if (!resultOfInputStringValidation) {
				return new ResponseEntity<>("Input is not a csv string", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>("String is a csv", HttpStatus.OK);
	}

}
