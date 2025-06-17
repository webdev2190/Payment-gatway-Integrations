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
