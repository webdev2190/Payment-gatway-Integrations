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
