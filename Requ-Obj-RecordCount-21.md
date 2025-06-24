package com.optum.pure.model.requestobjects.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Modernized RecordCount class for Java 21+:
 * - Immutable fields using final.
 * - Constructor with validation to ensure non-negative counts.
 * - `@ToString` for automatic string representation.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecordCount {

    // Immutable fields using final for better thread-safety and consistency
    private final int eligibilityCount;
    private final int claimsCount;

    /**
     * Constructor with validation: Ensure that the counts are non-negative
     */
    public RecordCount(int eligibilityCount, int claimsCount) {
        this.eligibilityCount = Math.max(eligibilityCount, 0);  // Ensure non-negative count
        this.claimsCount = Math.max(claimsCount, 0);  // Ensure non-negative count
    }

    // Optional: Builder pattern can be added here for flexible construction (if needed)
}

=========================================Java 21================================================================

package com.optum.pure.model.requestobjects.common;

/**
 * Modern, immutable Java 21 version of RecordCount using record.
 * - No need for Lombok or boilerplate
 * - Immutable by default (fields cannot change)
 * - Getters are automatically created: eligibilityCount(), claimsCount()
 */
public record RecordCount(int eligibilityCount, int claimsCount) {
    // No need for constructors, setters, or getters—record does it all.
    // If you want validation, add a compact constructor here.

    // Example: Add validation if needed (uncomment if required)
    // public RecordCount {
    //     if (eligibilityCount < 0 || claimsCount < 0) {
    //         throw new IllegalArgumentException("Counts cannot be negative");
    //     }
    // }
}

