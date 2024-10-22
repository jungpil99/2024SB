package com.example.sb1010_2.main;

import com.example.sb1010_2.chap07.Calculator;
import com.example.sb1010_2.config.AppCtxWithCache;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectWithCache {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppCtxWithCache.class);

        Calculator cal = context.getBean("calculator" ,Calculator.class);
        cal.factorial(7);
        cal.factorial(7);
        cal.factorial(5);
        cal.factorial(5);
        context.close();
    }
}
