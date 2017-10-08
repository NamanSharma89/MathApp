package com.sw.mathapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sw.mathapp.service.SumOfAllDivisors;

@RestController
public class MathAppController {
	// TODO: Look into auto-wiring
	@Autowired
	SumOfAllDivisors sumOfAllDivisors;

	@GetMapping("/sumofdivisors/{numberForDivisorSum}")
	public int getSumOfDivisorsController(@PathVariable(value = "numberForDivisorSum") final int numberForDivisorSum) {
		return sumOfAllDivisors.getSumOfAllDivisors(numberForDivisorSum);

	}

}
