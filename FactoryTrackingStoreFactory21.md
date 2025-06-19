package com.optum.pure.trackingstore.factory;

import com.optum.pure.common.RestElasticsearchClient;
import com.optum.pure.trackingstore.TrackingStore;
import com.optum.pure.trackingstore.impl.ESTrackingStore;

/**
 * Modernized version of TrackingStoreFactory class for Java 21:
 * - Uses `volatile` for thread-safety of the singleton pattern
 * - Simplified synchronization mechanism using `volatile` and double-checked locking
 * - The `TrackingStore` singleton initialization is now thread-safe and efficient
 * - Immutable object instantiation where applicable
 */
public class TrackingStoreFactory {

    // Using volatile to ensure that changes to the singleton instance are visible across threads
    private static volatile TrackingStore trackingStore;

    /**
     * Get the singleton instance of TrackingStore
     * Double-checked locking for thread-safe and efficient initialization
     *
     * @return the TrackingStore instance
     */
    public static TrackingStore getTrackingStore() {
        // First check (without synchronization) for performance reasons
        if (trackingStore == null) {
            // Synchronized block to ensure only one thread can initialize the trackingStore
            synchronized (TrackingStoreFactory.class) {
                // Second check (after acquiring lock) to avoid redundant initialization
                if (trackingStore == null) {
                    trackingStore = new ESTrackingStore(RestElasticsearchClient.getClient());
                }
            }
        }
        return trackingStore;
    }
}
