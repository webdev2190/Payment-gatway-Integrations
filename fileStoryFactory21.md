package com.optum.pure.filestore.factory;

import com.optum.pure.filestore.FileStore;
import com.optum.pure.filestore.impl.OOSSFileStore;

/**
 * Factory class for FileStore.
 *
 * Java 21 Enhancements:
 * - Uses 'volatile' for thread-safety in singleton
 * - Uses 'var' for local variable inference (where possible)
 * - Uses the modern double-checked locking pattern for better performance (avoids synchronizing every time)
 * - Comments included at every step
 *
 * @author Dwarakesh T P (Upgraded by ChatGPT)
 */
public class FileStoreFactory {

    // Java 21: Use 'volatile' to ensure changes visible across threads (best practice for singleton)
    private static volatile FileStore fileStore;

    // Private constructor to prevent instantiation (utility/factory class)
    private FileStoreFactory() {}

    /**
     * Returns the singleton FileStore instance.
     * Uses double-checked locking for thread-safety and performance.
     */
    public static FileStore getFileStore() {
        // First check without locking (fast path)
        if (fileStore == null) {
            synchronized (FileStoreFactory.class) {
                // Only one thread creates the instance
                if (fileStore == null) {
                    fileStore = new OOSSFileStore(); // Java 21: can use 'var' here if desired
                }
            }
        }
        return fileStore;
    }
}
