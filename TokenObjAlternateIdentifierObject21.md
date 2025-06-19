package com.optum.pure.model.tokenobjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Modernized version of AlternateIdentifierObject for Java 21:
 * - Immutability via final fields for thread-safety and predictability.
 * - Explicit null safety with constructor validation.
 * - Lombok used for getter, toString, and equals/hashCode generation.
 */
@Getter
@ToString
@EqualsAndHashCode
public class AlternateIdentifierObject {

    // Fields are now final to make the class immutable
    private final String alternateIdentifier;
    private final String alternateIdentifierType;
    private final String alternateIdentifierSource;

    // Constructor with null checks to ensure field values are not null
    public AlternateIdentifierObject(String alternateIdentifier, 
                                     String alternateIdentifierType, 
                                     String alternateIdentifierSource) {
        this.alternateIdentifier = Objects.requireNonNull(alternateIdentifier, "alternateIdentifier cannot be null");
        this.alternateIdentifierType = Objects.requireNonNull(alternateIdentifierType, "alternateIdentifierType cannot be null");
        this.alternateIdentifierSource = Objects.requireNonNull(alternateIdentifierSource, "alternateIdentifierSource cannot be null");
    }

    // Default constructor for deserialization if needed
    public AlternateIdentifierObject() {
        this.alternateIdentifier = null;
        this.alternateIdentifierType = null;
        this.alternateIdentifierSource = null;
    }
}
