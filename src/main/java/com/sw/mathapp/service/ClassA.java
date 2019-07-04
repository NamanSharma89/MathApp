package com.sw.mathapp.service;

import org.springframework.beans.factory.annotation.Autowired;

public class ClassA {

    @Autowired
    private ClassB classB;

    public void doSomething(){
        if (classB.isPossible("Naman")){
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
