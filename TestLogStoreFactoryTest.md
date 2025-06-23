package com.optum.pure.logstore.factory;

import com.optum.pure.logstore.LogStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for LogStoreFactory (Java 21 + JUnit 5 Modernized)
 */
class LogStoreFactoryTest {

    @Test
    void shouldReturnNonNullLogStore() {
        LogStore logStore = LogStoreFactory.getLogStore();
        assertNotNull(logStore, "LogStore instance should not be null");
    }

    @Test
    void shouldReturnSameInstanceEveryTime() {
        LogStore logStore1 = LogStoreFactory.getLogStore();
        LogStore logStore2 = LogStoreFactory.getLogStore();
        assertSame(logStore1, logStore2, "Expected same singleton LogStore instance");
    }
}
