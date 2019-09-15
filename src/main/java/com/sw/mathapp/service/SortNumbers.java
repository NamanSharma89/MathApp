package com.sw.mathapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class SortNumbers {


	public String getSortedNumberList(String incomingString) {

		List<String> numbersFromInput = Arrays.asList(incomingString.split("\\s*,\\s*"));
		ArrayList<Integer> returnRequestedString = new ArrayList<Integer>(
				numbersFromInput.stream().map(Integer::parseInt).collect(Collectors.toList()));

		Collections.sort(returnRequestedString);
		String resultString = "";
		for (Integer i : returnRequestedString) {
			resultString += i + ",";
		}

		return resultString;
	}
}
