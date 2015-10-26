package com.moelholm.tools.actuatorui.dump;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
public class DumpUiAutoConfiguration {

    @Bean 
    public DumpUiController dumpUiController() {
        return new DumpUiController();
    }

}