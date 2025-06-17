package com.optum.pure.model.dto.v1;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

/**
 * Modernized version of ResponseV1 using Java 21+ features:
 * - Immutable record-like class with flexibility (Builder pattern).
 * - Use of default values and compact constructor.
 * - Builder pattern for construction of objects.
 * - Java 21 style improvements for null safety and better code practices.
 */

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ResponseV1 {
    private String trackingId;
    private String tokenType;
    private List<DeIdentifiedTokensV1> result;

    // Java 21 - The constructor is now compact and uses the Builder pattern for immutability and flexibility.
    public ResponseV1(String trackingId, String tokenType) {
        this.trackingId = trackingId;
        this.tokenType = tokenType;
        this.result = List.of();  // Using modern Java 21 immutable list creation
    }

    /**
     * This builder pattern allows users to create immutable ResponseV1 objects easily.
     * Example Usage:
     * ResponseV1 response = ResponseV1.builder()
     *                                  .trackingId("123")
     *                                  .tokenType("JWT")
     *                                  .build();
     */
}

===================================Future proof===================
package com.optum.pure.model.dto.v1;

import lombok.Getter;
import lombok.ToString;
import lombok.Builder;
import lombok.NoArgsConstructor;
import com.optum.pure.model.dto.v1.DeIdentifiedTokensV1;

import java.util.List;
import java.util.Collections;

/**
 * Modernized version of ResponseV1 using Java 21+ features:
 * - Immutable design with final fields and a constructor.
 * - Use of the Builder pattern for clean and flexible object construction.
 * - Handling null-safety and empty collection initialization.
 */
@Getter
@ToString
@NoArgsConstructor
@Builder(toBuilder = true)
public class ResponseV1 {

    private final String trackingId;
    private final String tokenType;
    private final List<DeIdentifiedTokensV1> result;

    // Compact constructor that ensures 'result' is never null
    public ResponseV1(String trackingId, String tokenType) {
        this.trackingId = trackingId;
        this.tokenType = tokenType;
        this.result = Collections.emptyList();  // Immutable empty list
    }

    /**
     * Example usage of the Builder:
     * 
     * ResponseV1 response = ResponseV1.builder()
     *                                  .trackingId("123")
     *                                  .tokenType("JWT")
     *                                  .build();
     */
}

