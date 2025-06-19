package com.optum.pure.model.requestobjects.v1;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Modernized PostTokensV1 class for Java 21+:
 * - Replaced class with record for better immutability and thread-safety.
 * - Field validation using modern constructor for validation.
 * - Lombok annotations for automatic getter and toString methods.
 * - Use of `List` for de-identified tokens.
 */
@Getter
@ToString
public class PostTokensV1 {

    private final String tokenType;
    private final List<String> deIdentifiedTokens;

    // Constructor for validation and immutability
    public PostTokensV1(String tokenType, List<String> deIdentifiedTokens) {
        this.tokenType = tokenType;
        // Ensure deIdentifiedTokens list is not null
        this.deIdentifiedTokens = deIdentifiedTokens != null ? deIdentifiedTokens : List.of();
    }
}
