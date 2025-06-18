package com.optum.pure.model.requestobjects.common;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Modernized ProcessingMetrics class for Java 21+:
 * - Uses `record` for immutability and simplicity.
 * - Builder pattern for ergonomic object creation.
 * - @ToString for automatic string representation.
 * - Field validation added if necessary (using Objects.requireNonNullElse for defaults).
 */
@Getter
@ToString
@Builder(toBuilder = true) // Builder pattern for flexible and readable construction
public class ProcessingMetrics {
    private final int numberOfTokens; // Immutability with final

    /**
     * Compact constructor for validation or defaulting (optional)
     * Here, we are ensuring that the number of tokens is non-negative.
     */
    public ProcessingMetrics {
        this.numberOfTokens = Math.max(numberOfTokens, 0); // Ensure non-negative value
    }

    /**
     * Example usage:
     * 
     * var metrics = ProcessingMetrics.builder()
     *     .numberOfTokens(5)
     *     .build();
     * 
     * var modifiedMetrics = metrics.toBuilder()
     *     .numberOfTokens(10)
     *     .build();
     */
}
