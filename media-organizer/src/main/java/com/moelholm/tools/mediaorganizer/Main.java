package com.moelholm.tools.mediaorganizer;

import java.nio.file.Paths;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Main implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Autowired
    private MediaOrganizer organizer;

    @Override
    public void run(String... args) {
        LOG.info("Application started. Arguments: {}", Arrays.toString(args));

        if (args.length != 2) {
            LOG.info("Usage: Main [from] [to]");
            return;
        }

        organizer.undoFlatMessAsync(Paths.get(args[0]), Paths.get(args[1]));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}