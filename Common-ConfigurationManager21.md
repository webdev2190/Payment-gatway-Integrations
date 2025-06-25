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

========================================================================Java 21 New Code===============================================================>

package com.optum.pure.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Java 21 modernized ConfigurationManager:
 * - Uses final fields and avoids static mutability.
 * - Ensures null safety on config loading.
 * - Gracefully handles missing config file (won't throw NPE).
 * - Merges environment variables with config properties (environment wins).
 * - Static factory pattern, not instantiable.
 */
public final class ConfigurationManager {

    private static final Logger LOG = LogManager.getLogger(ConfigurationManager.class);
    // Use a final immutable properties instance
    private static final Properties PROPS = loadProperties();

    // Private constructor prevents instantiation
    private ConfigurationManager() {}

    /**
     * Loads properties from config.properties and merges with environment variables.
     */
    private static Properties loadProperties() {
        Properties props = new Properties();

        // Attempt to load from config.properties
        try (InputStream stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("config.properties")) {
            if (stream != null) {
                props.load(stream);
                LOG.debug("Configuration properties loaded successfully.");
            } else {
                LOG.warn("config.properties not found in classpath.");
            }
        } catch (IOException e) {
            LOG.error("Failed to load configuration properties", e);
        }

        // Merge environment variables (environment wins on collision)
        for (Map.Entry<String, String> env : System.getenv().entrySet()) {
            props.setProperty(env.getKey(), env.getValue());
        }

        return props;
    }

    /**
     * Gets a configuration property or null if not found.
     * @param key The property key.
     * @return The value or null.
     */
    public static String get(String key) {
        return PROPS.getProperty(key);
    }

    /**
     * Gets a configuration property or returns a default value if not found.
     * @param key The property key.
     * @param defaultValue The default value to return.
     * @return The property value or defaultValue.
     */
    public static String getOrDefault(String key, String defaultValue) {
        return PROPS.getProperty(key, defaultValue);
    }
}

