package com.optum.pure.model.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Modernized Notification class using Java 21 features:
 * - Builder pattern for ergonomic and flexible object creation.
 * - Immutability of fields (trackingId, version, inputArtifactUri) for better thread safety and consistency.
 * - More compact constructor.
 * - Automatic generation of `equals()` and `hashCode()` for better object comparison and hashing.
 */
@Getter
@ToString
@EqualsAndHashCode // Generates equals and hashCode methods based on all fields
@Builder(toBuilder = true) // Allows for flexible object construction and modification
public class Notification {

    private final String trackingId;
    private final String version;
    private final String inputArtifactUri;

    /**
     * Constructor for essential fields only
     * Ensures trackingId is set.
     * 
     * @param trackingId the unique identifier for the notification
     */
    public Notification(String trackingId) {
        this.trackingId = trackingId;
        this.version = null;
        this.inputArtifactUri = null;
    }

    /**
     * Constructor for trackingId and version.
     * 
     * @param trackingId the unique identifier for the notification
     * @param version the version associated with the notification
     */
    public Notification(String trackingId, String version) {
        this.trackingId = trackingId;
        this.version = version;
        this.inputArtifactUri = null;
    }

    /**
     * Example usage:
     * 
     * var notification = Notification.builder()
     *     .trackingId("1234")
     *     .version("1.0")
     *     .inputArtifactUri("artifactUri")
     *     .build();
     * 
     * var modifiedNotification = notification.toBuilder()
     *     .version("2.0")
     *     .build();
     */
}
=========================================Java 21 Record=======================================================>

package com.optum.pure.model.notification;

/**
 * Modern Java 21 Notification class as a record.
 * - No need for Lombok: all boilerplate is handled by the record.
 * - Immutability by default: safer and easier to reason about.
 * - Getters, toString, equals, and hashCode are auto-generated.
 */
public record Notification(
        String trackingId,
        String version,
        String inputArtifactUri
) {
    // No extra constructors or setters needed.
    // If you want to add validation, you can use a compact constructor:
    // public Notification {
    //     if (trackingId == null || trackingId.isBlank()) {
    //         throw new IllegalArgumentException("trackingId cannot be blank");
    //     }
    // }
}
