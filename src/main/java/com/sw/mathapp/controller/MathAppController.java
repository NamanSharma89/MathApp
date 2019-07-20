package com.sw.mathapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sw.mathapp.service.SortNumbers;
import com.sw.mathapp.service.SumOfAllDivisors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathAppController {

    // TODO: Look into auto-wiring and how it works with DI?
    @Autowired
    SumOfAllDivisors sumOfAllDivisors;
    @Autowired
    SortNumbers sortNumbers;
    @Autowired
    InputValidator inputValidator;

    @GetMapping("/sumofdivisors/{numberForDivisorSum}")
    public int getSumOfDivisorsController(@PathVariable(value = "numberForDivisorSum") final int numberForDivisorSum) {

        return sumOfAllDivisors.getSumOfAllDivisors(numberForDivisorSum);
    }

    @PostMapping("/sortnumbers/{sortNumberInput}")
    public ResponseEntity<String> getSortedNumberList(@PathVariable(value = "sortNumberInput") final String sortNumberInput) {

        if (!inputValidator.validate(sortNumberInput)) {
            return new ResponseEntity<>("Input is not a valid csv string", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(sortNumbers.getSortedNumberList(sortNumberInput), HttpStatus.OK);
    }
}
