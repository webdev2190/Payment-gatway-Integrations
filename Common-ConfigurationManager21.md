package com.optum.pure.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Fetches the Configuration from config.properties file
 *
 * @author Dwarakesh T P
 */
public final class ConfigurationManager {

    private static final Logger LOG = LogManager.getLogger(ConfigurationManager.class);

    // Using 'final' to ensure immutability and thread safety
    private static final Properties props = new Properties();

    // Static initializer block loads config and environment variables once at class loading time
    static {
        // Get current thread context class loader to access resources
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream("config.properties")) {
            if (stream != null) {
                props.load(stream); // Load properties from file
                LOG.debug("Configuration Properties loaded from config.properties");
            } else {
                LOG.warn("config.properties not found in classpath");
            }
        } catch (IOException e) {
            LOG.error("Failed to load configuration properties", e);
        }

        // Add system environment variables (modernized with forEach)
        Map<String, String> env = System.getenv();
        env.forEach(props::put);
    }

    // Private constructor to prevent instantiation (utility class pattern)
    private ConfigurationManager() {
    }

    // Modernized getter method
    public static String get(String key) {
        return props.getProperty(key);
    }
}
