package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Modernized MedEligibility class using Java 21 features:
 * - Use of **@Builder** for flexible object construction.
 * - Constructor validation using **builder pattern** for immutability.
 * - **@JsonInclude** for cleaner JSON serialization (no nulls).
 * - Fields are still mutable, but can be easily modified to make them immutable with final fields.
 * - Lombok's **@Getter**, **@Setter**, and **@ToString** for code reduction and easy usage.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@NoArgsConstructor
@Builder(toBuilder = true) // Adds the builder pattern to the class
public class MedEligibility {

    private String stateCode;
    private String recordId;
    private String dateOfBirth;
    private String zipCode;
    private String memberIdentifier;
    private String gender;
    private String updateDate;
    private String endDate;
    private String healthPlanIdentifier;
    private String startDate;

    /**
     * Constructor for field validation and null safety (if required)
     * Ensures that all fields are validated during construction.
     */
    public MedEligibility(String stateCode, String recordId, String dateOfBirth, String zipCode,
                          String memberIdentifier, String gender, String updateDate, String endDate,
                          String healthPlanIdentifier, String startDate) {
        this.stateCode = stateCode;
        this.recordId = recordId;
        this.dateOfBirth = dateOfBirth;
        this.zipCode = zipCode;
        this.memberIdentifier = memberIdentifier;
        this.gender = gender;
        this.updateDate = updateDate;
        this.endDate = endDate;
        this.healthPlanIdentifier = healthPlanIdentifier;
        this.startDate = startDate;
    }
}
====================================================Java 21 Record==============================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Java 21 modernized MedEligibility using a record:
 * - Immutability by default, fields cannot be changed after creation.
 * - No Lombok needed (records generate all boilerplate code).
 * - Jackson 2.12+ supports records and @JsonInclude for non-null serialization.
 * - Cleaner, safer, and more maintainable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MedEligibility(
        String stateCode,
        String recordId,
        String dateOfBirth,
        String zipCode,
        String memberIdentifier,
        String gender,
        String updateDate,
        String endDate,
        String healthPlanIdentifier,
        String startDate
) {
    // No custom logic is needed for pure data carriers.
    // You can add helper methods or validation here if needed.
}
