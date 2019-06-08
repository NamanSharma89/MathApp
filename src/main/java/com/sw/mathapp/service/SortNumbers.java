package com.sw.mathapp.service;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

@Service
public class SortNumbers {

	public String getSortedNumberList(ArrayList<Integer> arrayListofCsvString) {		
		Collections.sort(arrayListofCsvString);	
		String resultString = "";
		for (Integer i : arrayListofCsvString) {
			resultString += i + ",";
		}
		return resultString;
	}
}
