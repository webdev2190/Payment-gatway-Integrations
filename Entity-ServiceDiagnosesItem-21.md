package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Modernized ServiceDiagnosesItem class using Java 21 features:
 * - Immutable fields and final for thread safety and data integrity.
 * - Constructor for validation to ensure correct initialization.
 * - Builder pattern for flexible and readable construction.
 * - @JsonInclude to exclude null fields during JSON serialization.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@Builder(toBuilder = true) // Builder pattern for flexible object creation
public class ServiceDiagnosesItem {

    private final String diagnosisCode;
    private final String diagnosisCodeDescription;

    /**
     * Constructor with validation to ensure that invalid or null fields are sanitized or rejected.
     * Uses Utils.stringFieldValidator for validation.
     */
    public ServiceDiagnosesItem(String diagnosisCode, String diagnosisCodeDescription) {
        this.diagnosisCode = Utils.stringFieldValidator(diagnosisCode);
        this.diagnosisCodeDescription = Utils.stringFieldValidator(diagnosisCodeDescription);
    }

    /**
     * Example usage:
     * 
     * // Construction using Builder pattern:
     * ServiceDiagnosesItem diagnosis = ServiceDiagnosesItem.builder()
     *     .diagnosisCode("12345")
     *     .diagnosisCodeDescription("Sample Description")
     *     .build();
     * 
     * // Modification (creates a new object):
     * ServiceDiagnosesItem modifiedDiagnosis = diagnosis.toBuilder()
     *     .diagnosisCodeDescription("Updated Description")
     *     .build();
     */
}

====================================Java 21 Record=========================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

/**
 * Modern Java 21 immutable DTO using record:
 * - No setters needed (fields are final, set via constructor)
 * - Jackson 2.12+ supports records out-of-the-box
 * - Validation logic provided as helper methods
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceDiagnosesItem(
        String diagnosisCode,
        String diagnosisCodeDescription
) {
    // Return validated diagnosis code (use this in your services or for JSON if needed)
    public String validatedDiagnosisCode() {
        return Utils.stringFieldValidator(diagnosisCode);
    }

    public String validatedDiagnosisCodeDescription() {
        return Utils.stringFieldValidator(diagnosisCodeDescription);
    }
}

