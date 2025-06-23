package com.optum.pure.trackingstore.factory;

import com.optum.pure.trackingstore.TrackingStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for TrackingStoreFactory class using JUnit 5
 */
class TrackingStoreFactoryTest {

    @Test
    void getTrackingStoreNotNullTest() {
        // Get instance from factory and assert it's not null
        TrackingStore trackingStore = TrackingStoreFactory.getTrackingStore();
        assertNotNull(trackingStore); // JUnit 5 assertion
    }

    @Test
    void trackingStoreEqualsTest() {
        // Check that the same instance is returned (singleton behavior)
        TrackingStore trackingStore1 = TrackingStoreFactory.getTrackingStore();
        TrackingStore trackingStore2 = TrackingStoreFactory.getTrackingStore();
        assertEquals(trackingStore1, trackingStore2); // JUnit 5 assertion
    }
}
