package com.optum.pure.model.requestobjects.v2;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Modernized TokenTuple class for Java 21+:
 * - Immutability and thread-safety using records (kept class as per the requirement).
 * - Explicit null safety through constructor.
 * - Enhanced equals and hashCode with @EqualsAndHashCode annotation.
 * - Better use of Lombok for cleaner code.
 */
@Getter
@ToString
@EqualsAndHashCode
public class TokenTuple {

    private final String tokenType1;
    private final String tokenType2;

    // Constructor ensures null safety for the fields
    public TokenTuple(String tokenType1, String tokenType2) {
        this.tokenType1 = Objects.requireNonNull(tokenType1, "tokenType1 cannot be null");
        this.tokenType2 = Objects.requireNonNull(tokenType2, "tokenType2 cannot be null");
    }

    // Default constructor added for deserialization purposes
    public TokenTuple() {
        this.tokenType1 = null;
        this.tokenType2 = null;
    }
}
