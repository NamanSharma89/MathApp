package com.sw.mathapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
				String checkInputForCommaIntValue= "[0-9,]+";
				String checkInputForDoubleValues= "^[^.]+$";
				boolean resultOfInputStringValidation=sortNumberInput.matches(checkInputForCommaIntValue);
				boolean resultOfcheckInputForDoubleValues=sortNumberInput.matches(checkInputForDoubleValues);
				System.out.println("valid int and comma? " + resultOfInputStringValidation);
				System.out.println("any double values?" + resultOfcheckInputForDoubleValues);
				if (!resultOfInputStringValidation || !resultOfcheckInputForDoubleValues) {
					return new ResponseEntity<>("Input is not a valid csv string", HttpStatus.BAD_REQUEST);
				} else {
					// int[] numbersFromInput = Arrays.stream(sortNumberInput.split(",")).mapToInt(Integer::parseInt).toArray();
					List<String> numbersFromInput = Arrays.asList(sortNumberInput.split("\\s*,\\s*"));
					ArrayList<Integer> arrayListofCsvString = new ArrayList<Integer>(numbersFromInput.stream().map(Integer::parseInt).collect(Collectors.toList()));
					
					for (Integer int1 : arrayListofCsvString) {
						System.out.println("at " + int1);
					}
					return new ResponseEntity<>("String is a csv and here is the sorted list:" + sortNumbers.getSortedNumberList(arrayListofCsvString), HttpStatus.OK);				
				}
			} catch (Exception e) {
				return new ResponseEntity<>("Exception :" + e, HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
	}

}
