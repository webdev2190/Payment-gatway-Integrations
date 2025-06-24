package com.optum.pure.model.requestobjects.common;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.Objects;

/**
 * Modernized LogRecord class using Java 21 features:
 * - Builder pattern for ergonomic and flexible object creation.
 * - Immutability of fields for better thread safety and consistency.
 * - More compact constructor.
 * - Automatic generation of `equals()` and `hashCode()` for better object comparison and hashing.
 */
@Getter
@ToString
@EqualsAndHashCode // Automatically generates equals() and hashCode based on all fields
@Builder(toBuilder = true) // Allows for flexible object construction and modification
public class LogRecord {

    private final String trackingId;
    private final String correlationId;
    private final String callerId;
    private final String serviceName;
    private final String status;
    private final String timeStamp;
    private final long timeTakenMs;

    /**
     * Compact constructor for field validation and null safety
     */
    public LogRecord {
        // Java 21: Objects.requireNonNullElse for null safety
        this.trackingId = Objects.requireNonNullElse(trackingId, "Unknown");
        this.correlationId = Objects.requireNonNullElse(correlationId, "Unknown");
        this.callerId = Objects.requireNonNullElse(callerId, "Unknown");
        this.serviceName = Objects.requireNonNullElse(serviceName, "Unknown");
        this.status = Objects.requireNonNullElse(status, "Unknown");
        this.timeStamp = Objects.requireNonNullElse(timeStamp, "Unknown");
        this.timeTakenMs = Objects.requireNonNullElse(timeTakenMs, 0L);
    }

    /**
     * Example usage:
     * 
     * var logRecord = LogRecord.builder()
     *     .trackingId("1234")
     *     .correlationId("5678")
     *     .serviceName("AuthService")
     *     .status("SUCCESS")
     *     .timeTakenMs(250)
     *     .build();
     * 
     * var modifiedLogRecord = logRecord.toBuilder()
     *     .status("FAILED")
     *     .build();
     */
}
=========================================Java 21 Record======================================================>

package com.optum.pure.model.requestobjects.common;

/**
 * Java 21 modernized, immutable LogRecord using a record:
 * - No need for Lombok annotations
 * - All fields are final and set in the constructor (immutability)
 * - Getters, toString, equals, and hashCode are auto-generated
 */
public record LogRecord(
        String trackingId,
        String correlationId,
        String callerId,
        String serviceName,
        String status,
        String timeStamp,
        long timeTakenMs
) {
    // No-args or all-args constructor isn't needed: records generate an all-args constructor by default.
    // If you need to enforce validation, you can use a compact constructor:
    // public LogRecord {
    //     if (trackingId == null || trackingId.isBlank()) {
    //         throw new IllegalArgumentException("trackingId must not be blank");
    //     }
    // }
}
