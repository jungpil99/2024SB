package com.example.sb1118_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Sb11182Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb11182Application.class, args);
    }

}
