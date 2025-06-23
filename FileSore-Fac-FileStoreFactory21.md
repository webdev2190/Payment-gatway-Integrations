package com.optum.pure.filestore.factory;

import com.optum.pure.filestore.FileStore;
import com.optum.pure.filestore.impl.OOSSFileStore;

/**
 * Factory class for File store.
 * <p>
 * - Uses modern Java coding conventions.
 * - Applies best practices for singleton initialization and thread safety.
 */
public final class FileStoreFactory { // ✅ Marked final to prevent subclassing

    // Declared volatile to ensure visibility of changes across threads in double-checked locking
    private static volatile FileStore fileStore;

    // Private constructor to prevent instantiation
    private FileStoreFactory() {
        throw new UnsupportedOperationException("FileStoreFactory is a utility class and should not be instantiated.");
    }

    /**
     * Returns a singleton instance of FileStore.
     * Uses double-checked locking for efficient thread-safe lazy initialization.
     */
    public static FileStore getFileStore() {
        // Modern double-checked locking pattern
        if (fileStore == null) {
            synchronized (FileStoreFactory.class) {
                if (fileStore == null) {
                    fileStore = new OOSSFileStore();
                }
            }
        }
        return fileStore;
    }
}
