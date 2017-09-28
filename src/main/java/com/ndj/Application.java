package com.ndj;

import org.springframework.context.annotation.ComponentScan;

/**
 * Created by don on 26/09/2017.
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.ndj" })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
