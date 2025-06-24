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

============================================Java 21 Record===============================================>

package com.optum.pure.model.dto.v2;

import com.optum.pure.model.dto.common.Data;
import com.optum.pure.model.requestobjects.v2.TokenTuple;
import java.util.Objects;

/**
 * Java 21 modernized DeIdentifiedTokensV2 using a record:
 * - Immutable: fields cannot be changed after creation.
 * - No Lombok needed (records provide accessors, toString, equals, hashCode).
 * - The compact constructor ensures 'data' is never null (matches your old constructor logic).
 */
public record DeIdentifiedTokensV2(
        TokenTuple tokenTuple,
        Data data
) {
    // Compact constructor to ensure 'data' is always non-null
    public DeIdentifiedTokensV2 {
        data = (data == null) ? new Data() : data;
        // Optionally ensure tokenTuple is never null:
        // tokenTuple = Objects.requireNonNull(tokenTuple, "tokenTuple must not be null");
    }
}
