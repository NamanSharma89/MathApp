package com.sw.mathapp.service;

import org.springframework.stereotype.Service;

@Service
public class SumOfAllDivisors {

	public int getSumOfAllDivisors(final int n) {
		int sum = 0;
		final int sqrt = (int) Math.sqrt(n);
		for (int i = 1; i <= sqrt; i++) {
			if (n % i == 0) { // if "i" is a divisor
				sum += i + n / i; // add both divisors
			}
		}
		/* If sqrt is a divisor, we should only count it once */
		if (sqrt * sqrt == n) {
			sum -= sqrt;
		}
		return sum;
	}
}
