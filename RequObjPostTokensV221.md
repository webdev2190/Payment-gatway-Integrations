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
===========================================Future Proof Code=============================================>

package com.optum.pure.model.requestobjects.v2;

import java.util.List;
import java.util.Objects;

/**
 * Java 21 upgraded version of PostTokensV2.
 * Represents a list of de-identified token tuples.
 */
public final class PostTokensV2 {

    private List<TokenTuple> deIdentifiedTokenTuples;

    // No-arg constructor
    public PostTokensV2() {
    }

    // All-args constructor
    public PostTokensV2(List<TokenTuple> deIdentifiedTokenTuples) {
        this.deIdentifiedTokenTuples = deIdentifiedTokenTuples;
    }

    // Getter
    public List<TokenTuple> getDeIdentifiedTokenTuples() {
        return deIdentifiedTokenTuples;
    }

    // Setter
    public void setDeIdentifiedTokenTuples(List<TokenTuple> deIdentifiedTokenTuples) {
        this.deIdentifiedTokenTuples = deIdentifiedTokenTuples;
    }

    // toString for logging/debugging
    @Override
    public String toString() {
        return "PostTokensV2{" +
                "deIdentifiedTokenTuples=" + deIdentifiedTokenTuples +
                '}';
    }

    // equals() for object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostTokensV2 other)) return false;
        return Objects.equals(deIdentifiedTokenTuples, other.deIdentifiedTokenTuples);
    }

    // hashCode() to support hash-based collections
    @Override
    public int hashCode() {
        return Objects.hash(deIdentifiedTokenTuples);
    }
}
=================================Java 21 Record==========================================================
package com.optum.pure.model.requestobjects.v2;

import java.util.List;
import java.util.Objects;

// Use record to automatically generate constructor, toString, equals, hashCode, etc.
public record PostTokensV2(List<TokenTuple> deIdentifiedTokenTuples) {

    // Compact constructor to enforce non-null and immutability
    public PostTokensV2 {
        // Check for null like Lombok's @NonNull (best practice for DTOs)
        if (deIdentifiedTokenTuples == null) {
            throw new NullPointerException("deIdentifiedTokenTuples cannot be null");
        }
        // Optionally: make the list unmodifiable to ensure true immutability
        deIdentifiedTokenTuples = List.copyOf(deIdentifiedTokenTuples);
    }

    // You can add methods here if you need business logic
    // For example, to get count of tokens:
    public int numberOfTokens() {
        return deIdentifiedTokenTuples.size();
    }
}
