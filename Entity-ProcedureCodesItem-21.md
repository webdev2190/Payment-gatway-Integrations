package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Modernized ProcedureCodesItem class using Java 21 features:
 * - Use of **record** for immutability, thread-safety, and conciseness (alternative to using a class).
 * - **@Builder** for ergonomic and flexible construction.
 * - **Validation** using `Utils.stringFieldValidator` for field validation.
 * - **@JsonInclude** for cleaner JSON output (no null values).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@Builder(toBuilder = true)
public class ProcedureCodesItem {

    private final String procedureCode;
    private final String procedureCodeDescription;

    /**
     * Constructor with validation to ensure valid fields are provided.
     * This constructor is used for field validation upon object creation.
     */
    public ProcedureCodesItem(String procedureCode, String procedureCodeDescription) {
        this.procedureCode = Utils.stringFieldValidator(procedureCode);
        this.procedureCodeDescription = Utils.stringFieldValidator(procedureCodeDescription);
    }

    /**
     * Example usage of builder pattern for creating and modifying objects:
     * ProcedureCodesItem item = ProcedureCodesItem.builder()
     *     .procedureCode("PC001")
     *     .procedureCodeDescription("Procedure Description")
     *     .build();
     *
     * // Modify object:
     * ProcedureCodesItem modifiedItem = item.toBuilder()
     *     .procedureCode("PC002")
     *     .build();
     */
}

==================================================Java 21 Record==================================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

/**
 * Modern Java 21 immutable DTO using record:
 * - No Lombok needed (record gives all boilerplate).
 * - Jackson 2.12+ supports records and @JsonInclude.
 * - Validation is moved to separate methods (cannot override record accessors).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProcedureCodesItem(
        String procedureCode,
        String procedureCodeDescription
) {
    // Use these helper methods for validated access:
    public String validatedProcedureCode() {
        return Utils.stringFieldValidator(procedureCode);
    }

    public String validatedProcedureCodeDescription() {
        return Utils.stringFieldValidator(procedureCodeDescription);
    }
}

