package com.optum.pure.model.requestobjects.v2;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

/**
 * Modernized PostTokensV2 class for Java 21+:
 * - Replaced class with record for better immutability and thread-safety, 
 *   but kept it as a traditional class per the request.
 * - Null safety for deIdentifiedTokenTuples using a constructor check.
 * - Lombok annotations for automatic getter and toString methods.
 */
@Getter
@ToString
public class PostTokensV2 {

    private final List<TokenTuple> deIdentifiedTokenTuples;

    // Constructor ensures null safety for deIdentifiedTokenTuples list
    public PostTokensV2(List<TokenTuple> deIdentifiedTokenTuples) {
        this.deIdentifiedTokenTuples = Objects.requireNonNullElse(deIdentifiedTokenTuples, List.of());
    }

    // Default constructor added for potential deserialization scenarios
    public PostTokensV2() {
        this.deIdentifiedTokenTuples = List.of(); // Empty list by default
    }
}
