package com.optum.pure.logstore.factory;

import com.optum.pure.common.RestElasticsearchClient;
import com.optum.pure.logstore.LogStore;
import com.optum.pure.logstore.impl.ESLogStore;

/**
 * Factory class for LogStore.
 *
 * Java 21 Enhancements:
 * - Uses 'volatile' for thread-safe singleton (recommended for Java 21)
 * - Double-checked locking for performance (only synchronizes when needed)
 * - Comments explain every change and why
 * - Cleaner and safer singleton implementation
 *
 * @author Dwarakesh T P 
 */
public class LogStoreFactory {

    // Java 21: Use 'volatile' to ensure proper singleton handling across threads
    private static volatile LogStore logStore;

    // Private constructor prevents instantiation
    private LogStoreFactory() {}

    /**
     * Returns the singleton LogStore instance.
     * Uses double-checked locking for efficient, thread-safe lazy initialization.
     */
    public static LogStore getLogStore() {
        // Fast, unsynchronized check
        if (logStore == null) {
            synchronized (LogStoreFactory.class) {
                // Only create if still null (safe for multi-threading)
                if (logStore == null) {
                    logStore = new ESLogStore(RestElasticsearchClient.getClient());
                }
            }
        }
        return logStore;
    }
}

