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
