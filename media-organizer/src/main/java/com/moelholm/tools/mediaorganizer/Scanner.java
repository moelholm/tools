package com.moelholm.tools.mediaorganizer;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Scanner {
    
    private static final Logger LOG = LoggerFactory.getLogger(Scanner.class);

    public void scan(String... args) {
        LOG.info("Scanning started - arguments: {}", Arrays.toString(args));
    }
}
