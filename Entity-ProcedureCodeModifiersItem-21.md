package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Modernized ProcedureCodeModifiersItem class using Java 21 features:
 * - Use of **record** for immutability, thread-safety, and conciseness.
 * - **@JsonInclude** for cleaner JSON output (no null values).
 * - Builder pattern for object construction.
 * - **Utils.stringFieldValidator** to handle string validation.
 * - Optional improvements for null safety and cleaner constructors.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@Builder(toBuilder = true)
public class ProcedureCodeModifiersItem {

    private final String procedureCodeModifier;
    private final String procedureCodeModifierDescription;

    /**
     * Compact constructor for validating fields at the time of object creation.
     * Utilizes Utils.stringFieldValidator to ensure the string values are valid.
     */
    public ProcedureCodeModifiersItem(String procedureCodeModifier, String procedureCodeModifierDescription) {
        this.procedureCodeModifier = Utils.stringFieldValidator(procedureCodeModifier);
        this.procedureCodeModifierDescription = Utils.stringFieldValidator(procedureCodeModifierDescription);
    }

    /**
     * Example usage of builder pattern:
     * ProcedureCodeModifiersItem item = ProcedureCodeModifiersItem.builder()
     *     .procedureCodeModifier("MOD1")
     *     .procedureCodeModifierDescription("Description")
     *     .build();
     */
}

===================================================Java 21 Record===================================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

/**
 * Java 21 modernized ProcedureCodeModifiersItem using a record:
 * - Immutability by default, fields are final.
 * - No Lombok needed; record provides all standard methods (getters, toString, equals, hashCode).
 * - Jackson 2.12+ supports records and @JsonInclude.
 * - Validation logic provided through helper methods (since accessor methods cannot be overridden in records).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProcedureCodeModifiersItem(
        String procedureCodeModifier,
        String procedureCodeModifierDescription
) {
    // Use these methods for validated access:
    public String validatedProcedureCodeModifier() {
        return Utils.stringFieldValidator(procedureCodeModifier);
    }

    public String validatedProcedureCodeModifierDescription() {
        return Utils.stringFieldValidator(procedureCodeModifierDescription);
    }
}
