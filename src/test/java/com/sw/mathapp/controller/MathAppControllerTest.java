package com.sw.mathapp.controller;

import com.sw.mathapp.MathApp.MathAppApplication;
import com.sw.mathapp.service.SortNumbers;
import com.sw.mathapp.service.SumOfAllDivisors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@WebMvcTest(MathAppController.class)
@SpringBootTest(classes = MathAppApplication.class)
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class MathAppControllerTest {


    private MockMvc mvc;

    @InjectMocks
    MathAppController sut;

    @Mock
    InputValidator inputValidator;
    @Mock
    SortNumbers sortNumbers;

    @Before
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void testApi_getSortedNumberListController () throws Exception{

        Mockito.when(inputValidator.validate("1,2,4,3,5")).thenReturn(true);
        Mockito.when(sortNumbers.getSortedNumberList("1,2,3,4,5")).thenReturn("1,2,3,4,5");
    }
}
