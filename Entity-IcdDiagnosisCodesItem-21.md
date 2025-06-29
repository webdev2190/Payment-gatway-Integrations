package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Modernized version of IcdDiagnosisCodesItem using Java 21+ features:
 * - Immutable fields for thread-safety.
 * - Constructor for field validation and default values.
 * - Use of @Getter for automatically generating getter methods.
 * - Use of the @ToString annotation to generate a concise string representation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@NoArgsConstructor
public class IcdDiagnosisCodesItem {

    private String icdDiagnosisCode;
    private String icdDiagnosisDecimalCode;
    private String icdDiagnosisCodeDescription;

    /**
     * Canonical constructor with validation for null safety.
     * Ensures that fields are not set to null values or empty strings.
     */
    public IcdDiagnosisCodesItem(String icdDiagnosisCode, String icdDiagnosisDecimalCode, String icdDiagnosisCodeDescription) {
        this.icdDiagnosisCode = Utils.stringFieldValidator(icdDiagnosisCode);
        this.icdDiagnosisDecimalCode = Utils.stringFieldValidator(icdDiagnosisDecimalCode);
        this.icdDiagnosisCodeDescription = Utils.stringFieldValidator(icdDiagnosisCodeDescription);
    }

    /**
     * If needed, additional methods can be added to transform or validate other aspects.
     */
}

=======================================================Java 21 Record=============================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

/**
 * Java 21 modernized IcdDiagnosisCodesItem using a record:
 * - Immutability: fields are final and set only at construction.
 * - No Lombok needed: records generate all getters, toString, equals, hashCode.
 * - Jackson 2.12+ supports records and @JsonInclude.
 * - Validation is provided by separate helper methods.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record IcdDiagnosisCodesItem(
        String icdDiagnosisCode,
        String icdDiagnosisDecimalCode,
        String icdDiagnosisCodeDescription
) {
    // Validation logic as helper methods (cannot override accessors in records)
    public String validatedIcdDiagnosisCode() {
        return Utils.stringFieldValidator(icdDiagnosisCode);
    }

    public String validatedIcdDiagnosisDecimalCode() {
        return Utils.stringFieldValidator(icdDiagnosisDecimalCode);
    }

    public String validatedIcdDiagnosisCodeDescription() {
        return Utils.stringFieldValidator(icdDiagnosisCodeDescription);
    }
}
