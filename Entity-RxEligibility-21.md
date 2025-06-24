package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Modernized RxEligibility class using Java 21 features:
 * - Uses record-like immutability and thread-safety with final fields.
 * - Builder pattern for flexible object construction.
 * - Field validation applied using Utils.stringFieldValidator() in constructor.
 * - @JsonInclude for cleaner JSON output (no nulls).
 * - Builder for object construction.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@Builder(toBuilder = true) // Builder pattern for flexibility
public class RxEligibility {

    private final String recordId;
    private final String cagm;
    private final String status;
    private final String memberIdentifier;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String gender;
    private final String dateOfBirth;
    private final String address1;
    private final String address2;
    private final String city;
    private final String stateCode;
    private final String zipCode;
    private final String snapshotTime;
    private final String healthPlanAccountIdentifier;
    private final String endDate;
    private final String healthPlanIdentifier;
    private final String startDate;

    /**
     * Compact constructor for field validation and null safety.
     * Ensures that invalid or null fields are sanitized or rejected.
     */
    public RxEligibility(String recordId, String cagm, String status, String memberIdentifier, String firstName,
                         String middleName, String lastName, String gender, String dateOfBirth, String address1,
                         String address2, String city, String stateCode, String zipCode, String snapshotTime,
                         String healthPlanAccountIdentifier, String endDate, String healthPlanIdentifier, String startDate) {

        this.recordId = Utils.stringFieldValidator(recordId);
        this.cagm = Utils.stringFieldValidator(cagm);
        this.status = Utils.stringFieldValidator(status);
        this.memberIdentifier = Utils.stringFieldValidator(memberIdentifier);
        this.firstName = Utils.stringFieldValidator(firstName);
        this.middleName = Utils.stringFieldValidator(middleName);
        this.lastName = Utils.stringFieldValidator(lastName);
        this.gender = Utils.stringFieldValidator(gender);
        this.dateOfBirth = Utils.stringFieldValidator(dateOfBirth);
        this.address1 = Utils.stringFieldValidator(address1);
        this.address2 = Utils.stringFieldValidator(address2);
        this.city = Utils.stringFieldValidator(city);
        this.stateCode = Utils.stringFieldValidator(stateCode);
        this.zipCode = Utils.stringFieldValidator(zipCode);
        this.snapshotTime = Utils.stringFieldValidator(snapshotTime);
        this.healthPlanAccountIdentifier = Utils.stringFieldValidator(healthPlanAccountIdentifier);
        this.endDate = Utils.stringFieldValidator(endDate);
        this.healthPlanIdentifier = Utils.stringFieldValidator(healthPlanIdentifier);
        this.startDate = Utils.stringFieldValidator(startDate);
    }

    /**
     * Example usage:
     * 
     * // Construction using Builder pattern:
     * RxEligibility eligibility = RxEligibility.builder()
     *     .recordId("123")
     *     .memberIdentifier("ABC")
     *     .build();
     * 
     * // Modification (creates a new object):
     * RxEligibility modifiedEligibility = eligibility.toBuilder()
     *     .status("Active")
     *     .build();
     */
}
===========================================Java 21 Record==============================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Java 21 modernized RxEligibility using a record:
 * - No Lombok needed.
 * - Immutable, concise, and serialization-friendly.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RxEligibility(
        String recordId,
        String cagm,
        String status,
        String memberIdentifier,
        String firstName,
        String middleName,
        String lastName,
        String gender,
        String dateOfBirth,
        String address1,
        String address2,
        String city,
        String stateCode,
        String zipCode,
        String snapshotTime,
        String healthPlanAccountIdentifier,
        String endDate,
        String healthPlanIdentifier,
        String startDate
) {
    // No setters, no custom logic: just a pure data carrier.
}
