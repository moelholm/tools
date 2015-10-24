package com.moelholm.tools.mediaorganizer;

import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.moelholm.tools.mediaorganizer.filesystem.FileSystemType;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Main {

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // Constants
    // --------------------------------------------------------------------------------------------------------------------------------------------

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // Member fields
    // --------------------------------------------------------------------------------------------------------------------------------------------

    @Autowired
    private Environment environment;

    @Autowired
    private MediaOrganizer organizer;

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // Public API
    // --------------------------------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void started() {

        boolean startedWithMandatoryArguments = (environment.containsProperty(MainArgument.FROM_DIR.getArgumentName())
                && environment.containsProperty(MainArgument.TO_DIR.getArgumentName()));

        if (!startedWithMandatoryArguments) {
            printUsage();
            return;
        }

        boolean daemonRunMode = environment.containsProperty(MainArgument.DAEMON_RUNMODE.getArgumentName());
        FileSystemType fileSystemType = FileSystemType.fromString(environment.getProperty(MainArgument.FILESYSTEM_TYPE.getArgumentName()));
        String fromDir = environment.getProperty(MainArgument.FROM_DIR.getArgumentName());
        String toDir = environment.getProperty(MainArgument.TO_DIR.getArgumentName());

        printApplicationStartedMessage(fromDir, toDir, daemonRunMode, fileSystemType);

        if (daemonRunMode) {
            organizer.scheduleUndoFlatMess(Paths.get(fromDir), Paths.get(toDir));
        } else {
            organizer.asyncUndoFlatMess(Paths.get(fromDir), Paths.get(toDir));
        }
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // Private functionality
    // --------------------------------------------------------------------------------------------------------------------------------------------

    private void printApplicationStartedMessage(String fromDir, String toDir, boolean daemonRunMode, FileSystemType fileSystemType) {
        LOG.info("");
        LOG.info("Application started with the following arguments:");
        LOG.info("    --{} ? {}", MainArgument.DAEMON_RUNMODE.getArgumentName(), daemonRunMode ? "yes" : "no");
        LOG.info("    --{} = {}", MainArgument.FILESYSTEM_TYPE.getArgumentName(), fileSystemType.toString().toLowerCase());
        LOG.info("    --{} = {}", MainArgument.FROM_DIR.getArgumentName(), fromDir);
        LOG.info("    --{}   = {}", MainArgument.TO_DIR.getArgumentName(), toDir);
        LOG.info("");
    }

    private void printUsage() {
        LOG.info("");
        LOG.info("Usage: Main --{}=[dir to copy from] --{}=[dir to copy to] [--daemon] [--{}=[type]]", MainArgument.FROM_DIR.getArgumentName(),
                MainArgument.TO_DIR.getArgumentName(), MainArgument.FILESYSTEM_TYPE.getArgumentName());
        LOG.info("");
        LOG.info("  Where:");
        LOG.info("");
        LOG.info("    --{} specifies the folder that contains your media files", MainArgument.FROM_DIR.getArgumentName());
        LOG.info("    --{}   specifies the folder that should contain the organized media files", MainArgument.TO_DIR.getArgumentName());
        LOG.info("    --{}   specifies if the application should run as a daemon", MainArgument.DAEMON_RUNMODE.getArgumentName());
        LOG.info("    --{}   specifies the filesystem: 'local' or 'dropbox' (without quotes)", MainArgument.FILESYSTEM_TYPE.getArgumentName());
        LOG.info("");
    }
}