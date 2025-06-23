package com.optum.pure.filestore.factory;

import com.optum.pure.filestore.FileStore;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Modern JUnit 5 tests for FileStoreFactory.java
 * 
 * Improvements:
 * - Uses JUnit 5 (`org.junit.jupiter.api.Test`) instead of JUnit 4
 * - Uses static `Assertions` for cleaner and more expressive test assertions
 * - Provides custom failure messages
 */
public class FileStoreFactoryTest {

    @Test
    void getFileStoreNotNullTest() {
        // Act: Fetch the FileStore instance from the factory
        FileStore fileStore = FileStoreFactory.getFileStore();

        // Assert: Ensure it's not null
        assertNotNull(fileStore, "FileStore instance should not be null");
    }

    @Test
    void getFileStoreEqualsTest() {
        // Act: Fetch two FileStore instances
        FileStore fileStore1 = FileStoreFactory.getFileStore();
        FileStore fileStore2 = FileStoreFactory.getFileStore();

        // Assert: They should be the same (singleton behavior expected)
        assertEquals(fileStore1, fileStore2, "FileStore instances should be equal (singleton expected)");
    }
}
