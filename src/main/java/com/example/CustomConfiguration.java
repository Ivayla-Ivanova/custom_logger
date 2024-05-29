package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.Logger;


/**
 *  This class instantiate and configure Logger objects with specialized settings.
 *  <p>Usage:</p>
 *  <pre>{@code
 * // Example: Logger for logs in Example.java
 *
 * import org.apache.logging.log4j.Logger;
 * import com.example.CustomConfiguration;
 *
 *  class Example{
 *
 *      private static final Logger logger = CustomConfiguration.getLogger(Example.class);
 *
 *      public static void main(String [] args){
 *
 *      logger.debug("Debug message.");
 *      logger.info("Hallo!");
 *      logger.warn("This is a warning message.");
 *      logger.error("Error!");
 *      logger.fatal("Fatal event occurred.");
 *      }
 *  }
 *
 *
 *
 *
 *
 *  }</pre>
 *  @see CustomConfiguration
 */
public class CustomConfiguration {

    LoggerContext context;
    ConfigurationFactory factory;
    Configuration config;

    private CustomConfiguration(Class<?> clazz){

        this.context = (LoggerContext) LogManager.getContext(false);
        this.factory = new CustomConfigurationFactory();
        this.config = this.factory.getConfiguration(context, "config", null);
        context.setConfiguration(config);
    }



    public static Logger getLogger(Class<?> clazz){

        CustomConfiguration config = new CustomConfiguration(clazz);
        Logger logger = LogManager.getLogger(clazz);
        return logger;

    }


}

