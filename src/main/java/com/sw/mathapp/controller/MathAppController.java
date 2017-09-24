package com.sw.mathapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sw.mathapp.service.SumOfAllDivisors;

@Controller
public class MathAppController {
	// TODO: Look into auto-wiring
	@Autowired
	SumOfAllDivisors sumOfAllDivisors;

	@RequestMapping(value = "/sumofdivisors")
	public void getSumOfDivisors() {

	}

}
