package com.optum.pure.model.tokenobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Modernized version of Tokens class for Java 21:
 * - Uses immutability with final fields.
 * - Uses a Map for better performance and cleaner design.
 * - Enhanced with null-safety.
 * - Lombok for concise getter and toString generation.
 */
@Getter
@NoArgsConstructor
@ToString
public class Tokens {

    // Using a Map to handle token types more effectively
    private final Map<String, List<String>> tokenTypes;

    // Constructor initializes the token types map and assigns empty lists
    public Tokens() {
        tokenTypes = new HashMap<>();
        tokenTypes.put("tokenType1", new ArrayList<>());
        tokenTypes.put("tokenType2", new ArrayList<>());
        tokenTypes.put("tokenType3", new ArrayList<>());
        tokenTypes.put("tokenType4", new ArrayList<>());
        tokenTypes.put("tokenType6", new ArrayList<>());
    }

    /**
     * Get the list of tokens for the specified token type.
     *
     * @param tokenType The type of token to retrieve.
     * @return List of tokens for the specified token type.
     */
    public List<String> get(String tokenType) {
        // Validate input token type and return the corresponding token list or an empty list
        Objects.requireNonNull(tokenType, "tokenType cannot be null");
        return tokenTypes.getOrDefault(tokenType, new ArrayList<>());
    }
}
