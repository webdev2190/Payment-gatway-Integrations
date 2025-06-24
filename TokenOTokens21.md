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

================================Furure proof java 21========================================================>
package com.optum.pure.model.tokenobjects;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Java 21 version of the Tokens class
 * Uses modern features like switch expressions, pattern matching (in future), and defensive coding.
 */
public final class Tokens {

    private List<String> tokenType1 = Collections.emptyList();
    private List<String> tokenType2 = Collections.emptyList();
    private List<String> tokenType3 = Collections.emptyList();
    private List<String> tokenType4 = Collections.emptyList();
    private List<String> tokenType6 = Collections.emptyList();

    public Tokens() {
    }

    // Getters and Setters — optional with frameworks like Lombok, but shown here for clarity

    public List<String> getTokenType1() {
        return tokenType1;
    }

    public void setTokenType1(List<String> tokenType1) {
        this.tokenType1 = Objects.requireNonNullElse(tokenType1, Collections.emptyList());
    }

    public List<String> getTokenType2() {
        return tokenType2;
    }

    public void setTokenType2(List<String> tokenType2) {
        this.tokenType2 = Objects.requireNonNullElse(tokenType2, Collections.emptyList());
    }

    public List<String> getTokenType3() {
        return tokenType3;
    }

    public void setTokenType3(List<String> tokenType3) {
        this.tokenType3 = Objects.requireNonNullElse(tokenType3, Collections.emptyList());
    }

    public List<String> getTokenType4() {
        return tokenType4;
    }

    public void setTokenType4(List<String> tokenType4) {
        this.tokenType4 = Objects.requireNonNullElse(tokenType4, Collections.emptyList());
    }

    public List<String> getTokenType6() {
        return tokenType6;
    }

    public void setTokenType6(List<String> tokenType6) {
        this.tokenType6 = Objects.requireNonNullElse(tokenType6, Collections.emptyList());
    }

    /**
     * Java 21+ switch expression to return the correct token list by key.
     * Returns an immutable empty list for unmatched types.
     */
    public List<String> get(String tokenType) {
        return switch (tokenType) {
            case "tokenType1" -> tokenType1;
            case "tokenType2" -> tokenType2;
            case "tokenType3" -> tokenType3;
            case "tokenType4" -> tokenType4;
            case "tokenType6" -> tokenType6;
            default -> List.of(); // modern replacement for new ArrayList<>()
        };
    }

    @Override
    public String toString() {
        return "Tokens{" +
                "tokenType1=" + tokenType1 +
                ", tokenType2=" + tokenType2 +
                ", tokenType3=" + tokenType3 +
                ", tokenType4=" + tokenType4 +
                ", tokenType6=" + tokenType6 +
                '}';
    }
}

| Upgrade                           | Purpose                                                               |
| --------------------------------- | --------------------------------------------------------------------- |
| `switch` expression               | Modern, concise, safer alternative to legacy switch blocks            |
| `List.of()`                       | Immutable empty list for unmatched cases                              |
| `Objects.requireNonNullElse(...)` | Prevents NPE by ensuring empty lists are assigned if null             |
| No Lombok                         | Replaced Lombok with native Java 21 for clear visibility (optional)   |
| `final class`                     | Keeps immutability and prevents subclassing — aligned with your style |


-----------------------------------------------You can use This one Also-----------------------------------------------

package com.optum.pure.model.tokenobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Final class to hold token types and provide accessor method.
 * Modernized for Java 21 while maintaining backward-compatible patterns.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public final class Tokens {

    private List<String> tokenType1;
    private List<String> tokenType2;
    private List<String> tokenType3;
    private List<String> tokenType4;
    private List<String> tokenType6;

    /**
     * Returns the list corresponding to the given token type string.
     * Uses switch expression (Java 14+), enhanced readability in Java 21.
     *
     * @param tokenType the token type identifier
     * @return list of tokens for that type, or empty list if invalid
     */
    public List<String> get(String tokenType) {
        return switch (tokenType) {
            case "tokenType1" -> tokenType1 != null ? tokenType1 : List.of();
            case "tokenType2" -> tokenType2 != null ? tokenType2 : List.of();
            case "tokenType3" -> tokenType3 != null ? tokenType3 : List.of();
            case "tokenType4" -> tokenType4 != null ? tokenType4 : List.of();
            case "tokenType6" -> tokenType6 != null ? tokenType6 : List.of();
            default -> List.of(); // Immutable empty list
        };
    }
}



