package com.moelholm.tools.actuatorui.dump;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.DumpEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(EndpointHandlerMapping.class)
public class DumpAutoConfiguration {

    public static final String TITLE_QUALIFIER = "actuator-ui.dump-ui.qualifier.title";
    public static final String PATH_TO_DUMP_QUALIFIER = "actuator-ui.dump-ui.qualifier.pathToDump";

    private static final String PATH_TO_DUMP_PROPERTY_NAME = "actuator-ui.dump-ui.pathToDump";
    private static final String TITLE_PROPERTY_NAME = "actuator-ui.dump-ui.title";
    private static final String TITLE_PROPERTY_DEFAULT_VALUE = "Thread dump";

    @Autowired
    private Environment config;

    @Autowired
    private EndpointHandlerMapping handlerMapping;

    @Bean
    public DumpController dumpUiController() {
        return new DumpController();
    }

    @Bean
    @Qualifier(TITLE_QUALIFIER)
    public String dumpUiTitle() {
        return config.getProperty(TITLE_PROPERTY_NAME, TITLE_PROPERTY_DEFAULT_VALUE);
    }

    @Bean
    @Qualifier(PATH_TO_DUMP_QUALIFIER)
    public String pathToDump() {

        String manuallyOverriddenPathToDump = getPathToDump();

        if (manuallyOverriddenPathToDump != null) {
            return manuallyOverriddenPathToDump;
        }

        for (MvcEndpoint endpoint : handlerMapping.getEndpoints()) {
            if (endpoint.getEndpointType() == DumpEndpoint.class) {
                return handlerMapping.getPrefix() + endpoint.getPath();
            }
        }

        throw new IllegalStateException(String.format("Failed to resolve path to %s", DumpEndpoint.class));
    }

    private String getPathToDump() {
        if (config.containsProperty(PATH_TO_DUMP_PROPERTY_NAME)) {
            return config.getProperty(PATH_TO_DUMP_PROPERTY_NAME);
        }
        return null;
    }
}