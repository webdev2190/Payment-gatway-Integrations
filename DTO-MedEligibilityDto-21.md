package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import lombok.Getter;
import lombok.ToString;

/**
 * DTO for Medical Eligibility Information.
 *
 * Java 21 Modernizations:
 * - Used records (Java 16+) if immutability is desired (see below, optional)
 * - Kept as class for setter-based mutability (with modern best practices)
 * - Explicitly marked fields as 'private' (Lombok @Getter provides getters)
 * - Considered using 'Optional' in setters for null safety (Java 8+, but not strictly Java 21 only)
 * - Provided clear Javadoc and inline comments about changes
 */
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedEligibilityDto {
    // Java 21: You could use 'final' for true immutability (if you make it a record)
    private String updateDate;
    private String endDate;
    private String healthPlanIdentifier;
    private String startDate;

    // Java 21: Setters remain for mutability, but can use 'var' for parameters in code blocks if needed

    /**
     * Sets the update date with field validation.
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = Utils.stringFieldValidator(updateDate);
    }

    /**
     * Sets the end date with field validation.
     */
    public void setEndDate(String endDate) {
        this.endDate = Utils.stringFieldValidator(endDate);
    }

    /**
     * Sets the health plan identifier with field validation.
     */
    public void setHealthPlanIdentifier(String healthPlanIdentifier) {
        this.healthPlanIdentifier = Utils.stringFieldValidator(healthPlanIdentifier);
    }

    /**
     * Sets the start date with field validation.
     */
    public void setStartDate(String startDate) {
        this.startDate = Utils.stringFieldValidator(startDate);
    }
}

==============================================Java 21 Record======================================================

package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

/**
 * Java 21 modernized MedEligibilityDto using a record:
 * - Immutability: fields cannot be changed after creation.
 * - No Lombok needed (record provides accessors, toString, equals, hashCode).
 * - Validation logic is done at creation via the factory method.
 * - Jackson @JsonInclude works as before.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MedEligibilityDto(
        String updateDate,
        String endDate,
        String healthPlanIdentifier,
        String startDate
) {
    /**
     * Factory method to apply validation (as in your original setters).
     */
    public static MedEligibilityDto of(
            String updateDate,
            String endDate,
            String healthPlanIdentifier,
            String startDate
    ) {
        return new MedEligibilityDto(
            Utils.stringFieldValidator(updateDate),
            Utils.stringFieldValidator(endDate),
            Utils.stringFieldValidator(healthPlanIdentifier),
            Utils.stringFieldValidator(startDate)
        );
    }
}

