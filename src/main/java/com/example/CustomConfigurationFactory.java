package com.example;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.net.URI;


public class CustomConfigurationFactory extends ConfigurationFactory{

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.ERROR);


        // Create appenders for different log levels
        // Create console appender for all log levels

        AppenderComponentBuilder consoleAppender = builder.newAppender("Console", "CONSOLE")
                .addAttribute("target", "SYSTEM_OUT") // Output to standard output
                .add(builder.newLayout("PatternLayout")
                        .addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
        consoleAppender.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT, Filter.Result.NEUTRAL).
                addAttribute("level", Level.DEBUG));


        AppenderComponentBuilder debugAppender = builder.newAppender("DebugAppender", "File")
                .addAttribute("fileName", "target/logs/debug.log");
        debugAppender.add(builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d [%t] %-5level %logger{36}: %msg%n%throwable"));
        debugAppender.add(builder.newFilter("LevelMatchFilter", Filter.Result.ACCEPT, Filter.Result.DENY).
                addAttribute("level", Level.DEBUG));

        AppenderComponentBuilder infoAppender = builder.newAppender("InfoAppender", "File")
                .addAttribute("fileName", "target/logs/info.log");
        infoAppender.add(builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d [%t] %-5level %logger{36}: %msg%n%throwable"));
        infoAppender.add(builder.newFilter("LevelMatchFilter", Filter.Result.ACCEPT, Filter.Result.DENY).
                addAttribute("level", Level.INFO));


        AppenderComponentBuilder warnAppender = builder.newAppender("WarnAppender", "File")
                .addAttribute("fileName", "target/logs/warn.log");
        warnAppender.add(builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d [%t] %-5level %logger{36}: %msg%n%throwable"));
        warnAppender.add(builder.newFilter("LevelMatchFilter", Filter.Result.ACCEPT, Filter.Result.DENY).
                addAttribute("level", Level.WARN));


        AppenderComponentBuilder errorAppender = builder.newAppender("ErrorAppender", "File")
                .addAttribute("fileName", "target/logs/error.log");
        errorAppender.add(builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d [%t] %-5level %logger{36}: %msg%n%throwable"));
        errorAppender.add(builder.newFilter("LevelMatchFilter", Filter.Result.ACCEPT, Filter.Result.DENY).
                addAttribute("level", Level.ERROR));

        // Add appenders to the configuration
        builder.add(consoleAppender);
        builder.add(debugAppender);
        builder.add(infoAppender);
        builder.add(warnAppender);
        builder.add(errorAppender);

        // Add a logger with multiple appenders
        // Exchange "com.example" with your actual root package name
        builder.add(builder.newLogger("com.example", Level.DEBUG)
                .add(builder.newAppenderRef("Console"))
                .add(builder.newAppenderRef("DebugAppender"))
                .add(builder.newAppenderRef("InfoAppender"))
                .add(builder.newAppenderRef("WarnAppender"))
                .add(builder.newAppenderRef("ErrorAppender"))
                .addAttribute("additivity", false));

        //  Create customized root logger
        builder.add(builder.newRootLogger(Level.ERROR)
                .add(builder.newAppenderRef("ErrorAppender"))
                .add(builder.newAppenderRef("Console")));

        return builder.build();


    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }



}
