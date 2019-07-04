package com.sw.mathapp.controller;

import com.sw.mathapp.service.SumOfAllDivisors;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SumOfDivisorsTest {

    @InjectMocks
    private SumOfAllDivisors sumOfAllDivisorsSut;


    @Test
    public void test_SumOfAllDivisors_returnsTrue() {

        int validInput = 123;

        final int actual = sumOfAllDivisorsSut.getSumOfAllDivisors(validInput);

        Assert.assertEquals(168,actual);

    }


}