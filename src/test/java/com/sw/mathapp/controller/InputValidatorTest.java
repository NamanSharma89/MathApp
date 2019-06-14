package com.sw.mathapp.controller;

import com.sw.mathapp.service.SumOfAllDivisors;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InputValidatorTest {

    @InjectMocks
    private InputValidator sut;
    @InjectMocks
    private SumOfAllDivisors sumOfAllDivisors_sut;

    @Test
    public void testValidate_InputWithSplChars_returnsFalse() {

        String invalidStr = "1,2,3,as,1";

        final boolean actual = sut.validate(invalidStr);

        Assert.assertThat(actual, Matchers.is(Boolean.FALSE));

    }


    @Test
    public void testValidate_InputWithDecimalValues_returnsFalse() {

        String invalidStr = "1,2,3,1.12";

        final boolean actual = sut.validate(invalidStr);

        Assert.assertThat(actual, Matchers.is(Boolean.FALSE));

    }

    @Test
    public void testValidate_ValidInput_returnsTrue() {

        String validStr = "1,2,3,1,12,19";

        final boolean actual = sut.validate(validStr);

        Assert.assertThat(actual, Matchers.is(Boolean.TRUE));

    }

    @Test
    public void test_SumOfAllDivisors_returnsTrue() {

        int validInput = 123;

        final int actual = sumOfAllDivisors_sut.getSumOfAllDivisors(validInput);

        Assert.assertEquals(168,actual);

    }


}