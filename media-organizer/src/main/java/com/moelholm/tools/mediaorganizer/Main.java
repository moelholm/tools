package com.moelholm.tools.mediaorganizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private Scanner scanner;

    @Override
    public void run(String... args)  {
        scanner.scan(args);
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

    }

}