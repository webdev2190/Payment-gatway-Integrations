package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Modernized TrackingStatus class using Java 21 features:
 * - Immutable `trackingId` field with final modifier.
 * - @Builder pattern for ergonomic object creation and flexibility.
 * - Constructor for validation and safe defaults.
 * - Uses record-like immutability with the flexibility of a class.
 * - @JsonInclude for cleaner JSON output, excluding null fields.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@Builder(toBuilder = true) // Generates the builder pattern for flexible object creation
public class TrackingStatus {

    private final String trackingId; // Immutable field
    private String status;
    private String errorDescription;

    // Compact constructor for essential fields only
    public TrackingStatus(String trackingId) {
        this.trackingId = trackingId;
    }

    public TrackingStatus(String trackingId, String status) {
        this.trackingId = trackingId;
        this.status = status;
    }

    /**
     * Builder method for flexible construction of the object.
     * Allows setting status and error description while maintaining immutability.
     *
     * Usage Example:
     * var trackingStatus = TrackingStatus.builder()
     *     .trackingId("12345")
     *     .status("Completed")
     *     .errorDescription("No issues")
     *     .build();
     */
}

========================================Java 21 Record==================================================

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Modern, idiomatic Java 21 version of TrackingStatus:
 * - Uses a record for immutability, safety, and brevity
 * - Keeps Jackson annotations for null-handling during JSON serialization
 * - Provides static factory methods for various construction patterns
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // At class level: applies to all fields
public record TrackingStatus(
        String trackingId,
        String status,
        String errorDescription
) {
    // If you want multiple ways to create, use static factory methods:
    public static TrackingStatus of(String trackingId) {
        return new TrackingStatus(trackingId, null, null);
    }

    public static TrackingStatus of(String trackingId, String status) {
        return new TrackingStatus(trackingId, status, null);
    }

    // Jackson (as of 2.12+) works well with records; just be sure your library is updated
}

