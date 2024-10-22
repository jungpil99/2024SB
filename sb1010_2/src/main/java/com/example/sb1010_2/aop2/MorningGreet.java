package com.example.sb1010_2.aop2;

import org.springframework.stereotype.Component;

@Component
public class MorningGreet implements Greet {
    @Override
    public void greeting() {
        System.out.println("---------------------");
        System.out.println("good morning");
        System.out.println("---------------------");
    }
}
