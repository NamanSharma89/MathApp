package com.sw.mathapp.controller;

import com.sw.mathapp.service.ClassA;
import com.sw.mathapp.service.ClassB;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ClassATest {

    @InjectMocks
    private ClassA classA;
    @Mock
    private ClassB classB;


    @Test
    public void test_doSomething(){
        Mockito.when(classB.isPossible("naman")).thenReturn(true);
        classA.doSomething();
    }



}
