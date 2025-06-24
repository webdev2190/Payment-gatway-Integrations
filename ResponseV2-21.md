package com.optum.pure.model.dto.v2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import java.util.List;
import java.util.ArrayList;

/**
 * Modernized version of ResponseV2 using Java 21+ features:
 * - Immutable design using final fields.
 * - Constructor with validation and default value assignment.
 * - Builder pattern for ergonomic and flexible construction.
 * - Use of @ToString for automatic string representation.
 * - Null safety with Optional usage for lists.
 */
@Getter
@ToString
@Builder(toBuilder = true)
public class ResponseV2 {

    private final String trackingId; // Immutable field
    private final List<DeIdentifiedTokensV2> result; // Immutable field, thread-safe

    // Compact constructor for null safety and validation
    public ResponseV2(String trackingId, List<DeIdentifiedTokensV2> result) {
        this.trackingId = trackingId;
        this.result = result == null ? new ArrayList<>() : result; // Ensure result is never null
    }

    /**
     * Example usage:
     * 
     * // Construction using the builder pattern
     * var response = ResponseV2.builder()
     *     .trackingId("12345")
     *     .build();
     * 
     * // Modifying the object using toBuilder
     * var modifiedResponse = response.toBuilder()
     *     .trackingId("67890")
     *     .build();
     */
}

=================================================Java 21 Record============================================>

package com.optum.pure.model.dto.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Java 21 modernized ResponseV2 using a record:
 * - Immutability: all fields are final and set at construction.
 * - No Lombok needed (records generate all core methods).
 * - You can provide an overloaded static factory for single-argument construction, matching your previous constructor.
 * - If no result list is given, an empty list is used.
 */
public record ResponseV2(
        String trackingId,
        List<DeIdentifiedTokensV2> result
) {
    // Compact constructor to ensure result is never null
    public ResponseV2 {
        result = (result == null) ? List.of() : List.copyOf(result);
        Objects.requireNonNull(trackingId, "trackingId must not be null");
    }

    // Static factory to match your convenience constructor
    public static ResponseV2 withTrackingId(String trackingId) {
        return new ResponseV2(trackingId, List.of());
    }
}

