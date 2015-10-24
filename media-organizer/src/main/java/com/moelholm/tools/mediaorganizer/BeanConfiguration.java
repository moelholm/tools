package com.moelholm.tools.mediaorganizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.moelholm.tools.mediaorganizer.filesystem.DropboxFileSystem;
import com.moelholm.tools.mediaorganizer.filesystem.FileSystem;
import com.moelholm.tools.mediaorganizer.filesystem.FileSystemType;
import com.moelholm.tools.mediaorganizer.filesystem.LocalFileSystem;

@Configuration
public class BeanConfiguration {

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // Member fields
    // --------------------------------------------------------------------------------------------------------------------------------------------

    @Autowired
    private Environment environment;

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // @Bean producers
    // --------------------------------------------------------------------------------------------------------------------------------------------

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public FileSystem fileSystem() {
        if (FileSystemType.LOCAL == FileSystemType.fromString(environment.getProperty(MainArgument.FILESYSTEM_TYPE.getArgumentName()))) {
            return new LocalFileSystem();
        } else {
            return new DropboxFileSystem();
        }
    }
}
