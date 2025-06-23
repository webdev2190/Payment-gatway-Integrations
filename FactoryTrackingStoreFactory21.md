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
//===================================Future Proof Java 21 Code================================================

package com.optum.pure.trackingstore.factory;

import com.optum.pure.common.RestElasticsearchClient;
import com.optum.pure.trackingstore.TrackingStore;
import com.optum.pure.trackingstore.impl.ESTrackingStore;

/**
 * Java 21 upgraded factory class for {@link TrackingStore}.
 * Applies singleton pattern using lazy initialization with double-checked locking.
 */
public final class TrackingStoreFactory { // Marked final to prevent subclassing

    private static volatile TrackingStore trackingStore; // volatile ensures thread-safe access in modern JVMs

    //  Private constructor prevents instantiation
    private TrackingStoreFactory() {
        throw new UnsupportedOperationException("TrackingStoreFactory should not be instantiated");
    }

    /**
     * Lazily initializes and returns the singleton instance of TrackingStore.
     *
     * @return TrackingStore instance (singleton)
     */
    public static TrackingStore getTrackingStore() {
        if (trackingStore == null) {
            synchronized (TrackingStoreFactory.class) { // Double-checked locking for lazy-thread-safe singleton
                if (trackingStore == null) {
                    trackingStore = new ESTrackingStore(RestElasticsearchClient.getClient());
                }
            }
        }
        return trackingStore;
    }
}

| Change                               | Description                                       | Reason                                                 |
| ------------------------------------ | ------------------------------------------------- | ------------------------------------------------------ |
| `final class`                        | Prevents subclassing of utility factory           | Enforces design intention                              |
| `private constructor with exception` | Prevents instantiation                            | Clean API design for utility class                     |
| `volatile` field                     | Ensures visibility of changes across threads      | Essential for double-checked locking to work correctly |
| `double-checked locking`             | Efficient thread-safe singleton instantiation     | Avoids unnecessary synchronization once initialized    |
| Javadoc improvements                 | Clear documentation for method purpose and design | Boosts readability and API usability                   |
