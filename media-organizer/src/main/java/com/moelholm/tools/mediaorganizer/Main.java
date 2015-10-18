package com.moelholm.tools.mediaorganizer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String PROGRAM_ARG_FROM_DIR = "fromDir";
    private static final String PROGRAM_ARG_TO_DIR = "toDir";

    @Autowired
    private MediaOrganizer organizer;

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void started() {

        boolean startedWithMandatoryArguments = (environment.containsProperty(PROGRAM_ARG_FROM_DIR) && environment.containsProperty(PROGRAM_ARG_TO_DIR));

        if (!startedWithMandatoryArguments) {
            printUsage();
            return;
        }

        String fromDir = environment.getProperty(PROGRAM_ARG_FROM_DIR);
        String toDir = environment.getProperty(PROGRAM_ARG_TO_DIR);

        printApplicationStartedMessage(fromDir, toDir);
        // organizer.undoFlatMessAsync(Paths.get(fromDir), Paths.get(toDir));
    }

    private void printApplicationStartedMessage(String fromDir, String toDir) {
        LOG.info("");
        LOG.info("Application started with the following arguments:");
        LOG.info("    --{} = {}", PROGRAM_ARG_FROM_DIR, fromDir);
        LOG.info("    --{}   = {}", PROGRAM_ARG_TO_DIR, toDir);
        LOG.info("");
    }

    private void printUsage() {
        LOG.info("");
        LOG.info("Usage: Main --{}=[dir to copy from] --{}=[dir to copy to]", PROGRAM_ARG_FROM_DIR, PROGRAM_ARG_TO_DIR);
        LOG.info("");
        LOG.info("  Where:");
        LOG.info("");
        LOG.info("    --{} specifies the folder that contains your media files", PROGRAM_ARG_FROM_DIR);
        LOG.info("    --{}   specifies the folder that should contain the organized media files", PROGRAM_ARG_TO_DIR);
        LOG.info("");
    }
}