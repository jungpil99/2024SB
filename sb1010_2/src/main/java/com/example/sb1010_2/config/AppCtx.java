package com.example.sb1010_2.config;

import com.example.sb1010_2.aspect.ExeTimeAspect;
import com.example.sb1010_2.chap07.Calculator;
import com.example.sb1010_2.chap07.ImpeCalculator;
import com.example.sb1010_2.chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppCtx {

    @Bean
    public ExeTimeAspect exeTimeAspect() {
        return new ExeTimeAspect();
    }

    @Bean
    public Calculator calculator() {
        return new ImpeCalculator();
    }
}
