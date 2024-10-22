package com.example.sb1010_2.main;

import com.example.sb1010_2.chap07.Calculator;
import com.example.sb1010_2.chap07.ImpeCalculator;
import com.example.sb1010_2.chap07.RecCalculator;
import com.example.sb1010_2.config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspect {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppCtx.class);

        Calculator cal = context.getBean("calculator", ImpeCalculator.class);
        long fiveFact = cal.factorial(5);
        System.out.println("cal.factorial(5) = " + fiveFact);
        System.out.println(cal.getClass().getName());
        context.close();
    }
}
