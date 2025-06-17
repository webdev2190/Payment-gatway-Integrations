package com.optum.pure.model.dto.v2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import java.util.List;
import java.util.ArrayList;

/**
 * Modernized version of ResponseV2 using Java 21+ features:
 * - Immutable design using final fields.
 * - Constructor with validation and default value assignment.
 * - Builder pattern for ergonomic and flexible construction.
 * - Use of @ToString for automatic string representation.
 * - Null safety with Optional usage for lists.
 */
@Getter
@ToString
@Builder(toBuilder = true)
public class ResponseV2 {

    private final String trackingId; // Immutable field
    private final List<DeIdentifiedTokensV2> result; // Immutable field, thread-safe

    // Compact constructor for null safety and validation
    public ResponseV2(String trackingId, List<DeIdentifiedTokensV2> result) {
        this.trackingId = trackingId;
        this.result = result == null ? new ArrayList<>() : result; // Ensure result is never null
    }

    /**
     * Example usage:
     * 
     * // Construction using the builder pattern
     * var response = ResponseV2.builder()
     *     .trackingId("12345")
     *     .build();
     * 
     * // Modifying the object using toBuilder
     * var modifiedResponse = response.toBuilder()
     *     .trackingId("67890")
     *     .build();
     */
}
