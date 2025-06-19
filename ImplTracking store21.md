package com.optum.pure.trackingstore.factory;

import com.optum.pure.common.RestElasticsearchClient;
import com.optum.pure.trackingstore.TrackingStore;
import com.optum.pure.trackingstore.impl.ESTrackingStore;

/**
 * Factory class for Tracking store
 *
 * Modernized for Java 21:
 * - Use `volatile` keyword for the singleton instance to ensure visibility across threads
 * - Double-checked locking for efficient and thread-safe initialization of singleton
 * - Eliminated unnecessary synchronization by using `volatile` and double-checked locking
 * - Improved performance and thread safety
 */
public class TrackingStoreFactory {
    
    // Use of volatile ensures visibility across threads without synchronization overhead
    private static volatile TrackingStore trackingStore;

    /**
     * Get the singleton instance of TrackingStore
     * 
     * This method uses double-checked locking to ensure thread-safety while avoiding the overhead
     * of synchronization on every call.
     * 
     * @return the TrackingStore instance
     */
    public static TrackingStore getTrackingStore() {
        // First check without synchronization for performance
        if (trackingStore == null) {
            // Synchronize only when the instance is null
            synchronized (TrackingStoreFactory.class) {
                // Double-check to avoid redundant instantiation
                if (trackingStore == null) {
                    trackingStore = new ESTrackingStore(RestElasticsearchClient.getClient());
                }
            }
        }
        return trackingStore;
    }
}
