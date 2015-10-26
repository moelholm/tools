package com.moelholm.tools.actuatorui.dump;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class DumpMain {

    public static void main(String[] args) {
        SpringApplication.run(DumpMain.class, args);
    }
}