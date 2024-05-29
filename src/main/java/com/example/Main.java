package com.example;

import org.apache.logging.log4j.Logger;
import com.example.CustomConfiguration;

public class Main {

    private static final Logger logger = CustomConfiguration.getLogger(Main.class);

    public static void main(String[] args) {

        logger.debug("Debug message.");
        logger.info("Hallo!");
        logger.warn("This is a warning message.");
        logger.error("Error!");
        logger.fatal("Fatal event occurred.");


    }
}