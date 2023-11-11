package com.sparta.lv5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Lv5Application {

    public static void main(String[] args) {
        SpringApplication.run(Lv5Application.class, args);
    }

}
