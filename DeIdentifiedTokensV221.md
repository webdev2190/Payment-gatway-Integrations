package com.optum.pure.model.dto.v2;

import com.optum.pure.model.dto.common.Data;
import com.optum.pure.model.requestobjects.v2.TokenTuple;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;
import java.util.Objects;

/**
 * Modernized version of DeIdentifiedTokensV2 using Java 21+ features:
 * - Immutable design with final fields and a constructor.
 * - Record-based implementation for simplicity and thread-safety.
 * - Default value for `data` via constructor with a null-safety check.
 * - Use of @Builder for flexible and safe object construction.
 */
@Getter
@ToString
@Builder(toBuilder = true)
public class DeIdentifiedTokensV2 {

    private final TokenTuple tokenTuple;
    private final Data data;

    // Compact constructor for null safety and validation
    public DeIdentifiedTokensV2(TokenTuple tokenTuple, Data data) {
        this.tokenTuple = tokenTuple;
        this.data = Objects.requireNonNullElse(data, new Data()); // Ensure `data` is never null
    }

    /**
     * Example usage:
     *
     * // Construction
     * var token = DeIdentifiedTokensV2.builder()
     *     .tokenTuple(new TokenTuple("type1", "type2"))
     *     .build();
     *
     * // Modification (creates a new instance with a modified field)
     * var modified = token.withTokenTuple(new TokenTuple("newType1", "newType2"));
     */
}
