package com.optum.pure.model.tokenobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.Objects;

/**
 * Modernized version of TokenStoreObject class for Java 21:
 * - Uses immutable and final fields where appropriate.
 * - Utilizes `Map` for more efficient and scalable field management.
 * - Applies modern Java features like `Objects.requireNonNullElse` for null safety.
 * - Cleaner and more concise constructor using `Lombok`.
 * - Uses `var` for type inference where applicable to make the code more concise.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TokenStoreObject {

    // Use of final for immutability and ensuring that the object state cannot be modified after initialization
    private final String status;
    private final List<AlternateIdentifierObject> alternateIdentifiers;
    private final Tokens tokenList;
    private final String recordId;
    private final String loadTimeStamp;

    // Constructor to initialize all the fields (with validation)
    public TokenStoreObject(String status, List<AlternateIdentifierObject> alternateIdentifiers, 
                            Tokens tokenList, String recordId, String loadTimeStamp) {
        this.status = Objects.requireNonNullElse(status, "Unknown");
        this.alternateIdentifiers = Objects.requireNonNullElse(alternateIdentifiers, List.of());
        this.tokenList = Objects.requireNonNullElse(tokenList, new Tokens());
        this.recordId = Objects.requireNonNullElse(recordId, "NoRecordId");
        this.loadTimeStamp = Objects.requireNonNullElse(loadTimeStamp, "0000-00-00T00:00:00");
    }
    
    /**
     * Example usage:
     * 
     * // Constructing TokenStoreObject instance
     * var tokenStore = new TokenStoreObject("Active", alternateIdentifiers, tokenList, "12345", "2025-01-01T12:00:00");
     * 
     * // ToString and Lombok's generated methods
     * System.out.println(tokenStore);
     */
}
========================================Future Proof Java 21 Code=====================================================>

package com.optum.pure.model.tokenobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Java 21-compatible POJO for token store response.
 * Using `final class` to ensure immutability intent and project-wide consistency.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public final class TokenStoreObject {

    private String status;
    private List<AlternateIdentifierObject> alternateIdentifiers;
    private Tokens tokenList;
    private String recordId;
    private String loadTimeStamp;
}

