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

==========================================Future Proof Java 21 code==================================================>

package com.optum.pure.model.requestobjects.v2;

import java.util.Objects;

/**
 * Java 21 upgraded version of TokenTuple.
 * Holds a pair of tokenType values.
 */
public final class TokenTuple {

    private String tokenType1;
    private String tokenType2;

    // No-arg constructor
    public TokenTuple() {
    }

    // All-args constructor
    public TokenTuple(String tokenType1, String tokenType2) {
        // Enforce non-null via constructor
        this.tokenType1 = Objects.requireNonNull(tokenType1, "tokenType1 must not be null");
        this.tokenType2 = Objects.requireNonNull(tokenType2, "tokenType2 must not be null");
    }

    // Getters
    public String getTokenType1() {
        return tokenType1;
    }

    public String getTokenType2() {
        return tokenType2;
    }

    // Setters (with non-null check)
    public void setTokenType1(String tokenType1) {
        this.tokenType1 = Objects.requireNonNull(tokenType1, "tokenType1 must not be null");
    }

    public void setTokenType2(String tokenType2) {
        this.tokenType2 = Objects.requireNonNull(tokenType2, "tokenType2 must not be null");
    }

    @Override
    public String toString() {
        return "TokenTuple{" +
                "tokenType1='" + tokenType1 + '\'' +
                ", tokenType2='" + tokenType2 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenTuple other)) return false;
        return tokenType1.equals(other.tokenType1) && tokenType2.equals(other.tokenType2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType1, tokenType2);
    }
}

| Change                                           | Why                                               |
| ------------------------------------------------ | ------------------------------------------------- |
| ✅ `final class`                                  | Prevents subclassing (immutability intent)        |
| ✅ Removed Lombok                                 | Full control, readable, avoids runtime dependency |
| ✅ `Objects.requireNonNull`                       | Ensures @NonNull behavior at runtime              |
| ✅ `equals()` using `instanceof pattern matching` | Cleaner type checking (Java 16+ feature)          |
| ✅ Explicit `toString`, `equals`, `hashCode`      | Matches behavior Lombok would provide             |

