package com.example.sb1010_2.aop2;


public class EveningGreet implements Greet {
    @Override
    public void greeting() {
        System.out.println("---------------------");
        System.out.println("good evening");
        System.out.println("---------------------");
    }
}
