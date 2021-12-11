package com.rahul.lld.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

public abstract class Log {
    private static final LogManager logManager = LogManager.getLogManager();
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static{
        try {
            logManager.readConfiguration(new FileInputStream("log.properties"));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error in loading configuration",exception);
        }
    }
}
