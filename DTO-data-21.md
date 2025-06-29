package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Java 21 Enhancements:
 * - Used 'final' for immutability where applicable
 * - Used 'var' in constructor for modern type inference (optional, for code blocks)
 * - Considered using records (Java 16+), but here class with Lombok is kept for mutability and compatibility
 * - Comments for every upgrade
 */
@Getter
@Setter
@ToString
public class Data {

    // Java 21: If timeMetrics is not meant to be reassigned, consider using 'final'
    @JsonIgnore
    private Map<String, Integer> timeMetrics;

    // Java 21: You could use 'final' here, but Lombok's @Setter disables that
    private Rx rx;
    private Medical medical;

    // Java 21: Use 'var' in the constructor for readability (optional, not required in fields)
    public Data() {
        // var rx = new Rx(); // example usage, but assigning directly
        rx = new Rx();
        medical = new Medical();
    }

    // Java 21: For full immutability, you could use records (if no setters/mutators required)
    // But with @Setter, class style is retained for mutability
}

===================================================Java 21 Record==========================================>

package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;

/**
 * Java 21 modernized Data using a record:
 * - Immutability: all fields are final and set at construction.
 * - No Lombok needed (records generate accessors, toString, equals, hashCode).
 * - Null safety: 'rx' and 'medical' always initialized (never null).
 * - @JsonIgnore works as before.
 */
public record Data(
        @JsonIgnore Map<String, Integer> timeMetrics,
        Rx rx,
        Medical medical
) {
    // Compact constructor ensures rx and medical are never null, matching your old constructor logic
    public Data {
        rx = (rx == null) ? new Rx(List.of(), List.of()) : rx;
        medical = (medical == null) ? new Medical(List.of(), List.of()) : medical;
        // timeMetrics can remain nullable unless you want to default to Map.of()
    }
}
