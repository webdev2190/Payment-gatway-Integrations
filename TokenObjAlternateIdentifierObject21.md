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
============================================Future Proof Java 21 code===================================================>

package com.optum.pure.model.tokenobjects;

import java.util.Objects;

/**
 * Java 21 upgraded version of AlternateIdentifierObject.
 * This class holds alternate identifier info for tokens.
 */
public final class AlternateIdentifierObject {

    private String alternateIdentifier;
    private String alternateIdentifierType;
    private String alternateIdentifierSource;

    // No-arg constructor
    public AlternateIdentifierObject() {
    }

    // All-args constructor (optional but useful)
    public AlternateIdentifierObject(String alternateIdentifier, String alternateIdentifierType, String alternateIdentifierSource) {
        this.alternateIdentifier = alternateIdentifier;
        this.alternateIdentifierType = alternateIdentifierType;
        this.alternateIdentifierSource = alternateIdentifierSource;
    }

    // Getters
    public String getAlternateIdentifier() {
        return alternateIdentifier;
    }

    public String getAlternateIdentifierType() {
        return alternateIdentifierType;
    }

    public String getAlternateIdentifierSource() {
        return alternateIdentifierSource;
    }

    // Setters with null safety (optional)
    public void setAlternateIdentifier(String alternateIdentifier) {
        this.alternateIdentifier = Objects.requireNonNullElse(alternateIdentifier, "");
    }

    public void setAlternateIdentifierType(String alternateIdentifierType) {
        this.alternateIdentifierType = Objects.requireNonNullElse(alternateIdentifierType, "");
    }

    public void setAlternateIdentifierSource(String alternateIdentifierSource) {
        this.alternateIdentifierSource = Objects.requireNonNullElse(alternateIdentifierSource, "");
    }

    @Override
    public String toString() {
        return "AlternateIdentifierObject{" +
                "alternateIdentifier='" + alternateIdentifier + '\'' +
                ", alternateIdentifierType='" + alternateIdentifierType + '\'' +
                ", alternateIdentifierSource='" + alternateIdentifierSource + '\'' +
                '}';
    }
}

| Feature/Change                                       | Explanation                                                                  |
| ---------------------------------------------------- | ---------------------------------------------------------------------------- |
| `final class`                                        | Prevents subclassing — ensures immutability style without `record`           |
| Null-safe setters using `Objects.requireNonNullElse` | Avoids `null` values internally, aligns with modern best practices           |
| Manual `toString()`                                  | Provides clarity without relying on Lombok (optional)                        |
| Optional all-args constructor                        | Useful for flexible object creation                                          |
| Removed Lombok                                       | Explicit code is more portable, readable, and tool-friendly in the long term |
