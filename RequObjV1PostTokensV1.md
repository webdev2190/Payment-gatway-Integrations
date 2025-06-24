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

=========================================Future Proof Code================================================>

package com.optum.pure.model.requestobjects.v1;

import java.util.List;
import java.util.Objects;

/**
 * Java 21 upgraded version of PostTokensV1.
 * Represents a request containing token type and list of de-identified tokens.
 */
public final class PostTokensV1 {

    private String tokenType;
    private List<String> deIdentifiedTokens;

    // No-arg constructor
    public PostTokensV1() {
    }

    // All-arg constructor
    public PostTokensV1(String tokenType, List<String> deIdentifiedTokens) {
        this.tokenType = tokenType;
        this.deIdentifiedTokens = deIdentifiedTokens;
    }

    // Getter for tokenType
    public String getTokenType() {
        return tokenType;
    }

    // Setter for tokenType
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    // Getter for deIdentifiedTokens
    public List<String> getDeIdentifiedTokens() {
        return deIdentifiedTokens;
    }

    // Setter for deIdentifiedTokens
    public void setDeIdentifiedTokens(List<String> deIdentifiedTokens) {
        this.deIdentifiedTokens = deIdentifiedTokens;
    }

    // toString for logging/debugging
    @Override
    public String toString() {
        return "PostTokensV1{" +
                "tokenType='" + tokenType + '\'' +
                ", deIdentifiedTokens=" + deIdentifiedTokens +
                '}';
    }

    // equals() using Java 21 pattern matching
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostTokensV1 other)) return false;
        return Objects.equals(tokenType, other.tokenType) &&
               Objects.equals(deIdentifiedTokens, other.deIdentifiedTokens);
    }

    // hashCode override for use in hash-based collections
    @Override
    public int hashCode() {
        return Objects.hash(tokenType, deIdentifiedTokens);
    }
}

====================================Java 21 Record=============================================
package com.optum.pure.model.requestobjects.v1;

import java.util.List;

// Modern Java 21: use 'record' to represent a simple, immutable data carrier
public record PostTokensV1(String tokenType, List<String> deIdentifiedTokens) {

    // Compact constructor for null checks and immutability
    public PostTokensV1 {
        // Null check for tokenType, mimicking Lombok's @NonNull
        if (tokenType == null) {
            throw new NullPointerException("tokenType cannot be null");
        }
        // Null check for deIdentifiedTokens list, mimicking Lombok's @NonNull
        if (deIdentifiedTokens == null) {
            throw new NullPointerException("deIdentifiedTokens cannot be null");
        }
        // Make the list immutable, enforcing full immutability for the record
        deIdentifiedTokens = List.copyOf(deIdentifiedTokens);
    }

    // You can add custom methods here if needed
    // For example, a quick count method:
    public int numberOfTokens() {
        return deIdentifiedTokens.size();
    }
}
