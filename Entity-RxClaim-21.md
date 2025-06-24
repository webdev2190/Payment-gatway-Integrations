package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Modernized RxClaim class using Java 21 features:
 * - Record-like immutability and thread safety using final fields.
 * - Builder pattern for flexible object construction.
 * - @JsonAlias for flexible mapping between field names.
 * - @JsonInclude for cleaner JSON output (no nulls).
 * - Validation applied using Utils.stringFieldValidator for field validation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@Builder(toBuilder = true) // Builder pattern for flexibility
public class RxClaim {

    private final String cagm;
    private final String memberIdentifier;
    private final String status;
    private final String recordId;
    private final String claimStatus;
    private final String claimId;
    private final String sequenceNumber;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String dateOfBirth;
    private final String gender;
    private final String address1;
    private final String address2;
    private final String city;
    private final String stateCode;
    private final String zipCode;
    private final String snapshotTime;
    private final String galaxyIdentifier;
    private final String addressType;
    private final String pharmacyId;
    private final String drugCode;
    private final String healthPlanIdentifier;
    private final String daysSupply;
    private final String prescriptionNumber;
    @JsonAlias("prescriberSpecialityDescription")
    private final String prescriberSpecialtyDescription;
    private final String quantityFilled;
    private final String fillDate;
    private final String drugCodeType;
    private final String drugStrength;
    private final String drugCarrier;
    @JsonAlias("prescriberSpecialityCode")
    private final String prescriberSpecialtyCode;
    private final String refillNumber;
    private final String drugName;
    private final String prescribingProviderId;
    private final String totalRefills;
    private final String providerName;

    /**
     * Constructor with field validation for the RxClaim object.
     * This ensures no invalid data is assigned to fields.
     */
    public RxClaim(String cagm, String memberIdentifier, String status, String recordId, String claimStatus,
                   String claimId, String sequenceNumber, String firstName, String middleName, String lastName,
                   String dateOfBirth, String gender, String address1, String address2, String city, String stateCode,
                   String zipCode, String snapshotTime, String galaxyIdentifier, String addressType, String pharmacyId,
                   String drugCode, String healthPlanIdentifier, String daysSupply, String prescriptionNumber,
                   String prescriberSpecialtyDescription, String quantityFilled, String fillDate, String drugCodeType,
                   String drugStrength, String drugCarrier, String prescriberSpecialtyCode, String refillNumber,
                   String drugName, String prescribingProviderId, String totalRefills, String providerName) {

        this.cagm = Utils.stringFieldValidator(cagm);
        this.memberIdentifier = Utils.stringFieldValidator(memberIdentifier);
        this.status = Utils.stringFieldValidator(status);
        this.recordId = Utils.stringFieldValidator(recordId);
        this.claimStatus = Utils.stringFieldValidator(claimStatus);
        this.claimId = Utils.stringFieldValidator(claimId);
        this.sequenceNumber = Utils.stringFieldValidator(sequenceNumber);
        this.firstName = Utils.stringFieldValidator(firstName);
        this.middleName = Utils.stringFieldValidator(middleName);
        this.lastName = Utils.stringFieldValidator(lastName);
        this.dateOfBirth = Utils.stringFieldValidator(dateOfBirth);
        this.gender = Utils.stringFieldValidator(gender);
        this.address1 = Utils.stringFieldValidator(address1);
        this.address2 = Utils.stringFieldValidator(address2);
        this.city = Utils.stringFieldValidator(city);
        this.stateCode = Utils.stringFieldValidator(stateCode);
        this.zipCode = Utils.stringFieldValidator(zipCode);
        this.snapshotTime = Utils.stringFieldValidator(snapshotTime);
        this.galaxyIdentifier = Utils.stringFieldValidator(galaxyIdentifier);
        this.addressType = Utils.stringFieldValidator(addressType);
        this.pharmacyId = Utils.stringFieldValidator(pharmacyId);
        this.drugCode = Utils.stringFieldValidator(drugCode);
        this.healthPlanIdentifier = Utils.stringFieldValidator(healthPlanIdentifier);
        this.daysSupply = Utils.stringFieldValidator(daysSupply);
        this.prescriptionNumber = Utils.stringFieldValidator(prescriptionNumber);
        this.prescriberSpecialtyDescription = Utils.stringFieldValidator(prescriberSpecialtyDescription);
        this.quantityFilled = Utils.stringFieldValidator(quantityFilled);
        this.fillDate = Utils.stringFieldValidator(fillDate);
        this.drugCodeType = Utils.stringFieldValidator(drugCodeType);
        this.drugStrength = Utils.stringFieldValidator(drugStrength);
        this.drugCarrier = Utils.stringFieldValidator(drugCarrier);
        this.prescriberSpecialtyCode = Utils.stringFieldValidator(prescriberSpecialtyCode);
        this.refillNumber = Utils.stringFieldValidator(refillNumber);
        this.drugName = Utils.stringFieldValidator(drugName);
        this.prescribingProviderId = Utils.stringFieldValidator(prescribingProviderId);
        this.totalRefills = Utils.stringFieldValidator(totalRefills);
        this.providerName = Utils.stringFieldValidator(providerName);
    }

    /**
     * Example usage of builder pattern for creating and modifying objects:
     * 
     * // Construction
     * RxClaim rxClaim = RxClaim.builder()
     *     .claimId("123")
     *     .providerName("Dr. Smith")
     *     .build();
     * 
     * // Modification (creates new instance)
     * RxClaim modifiedRxClaim = rxClaim.toBuilder()
     *     .claimStatus("APPROVED")
     *     .build();
     */
}

=============================================Java 21 Record=================================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Modern Java 21 RxClaim using a record:
 * - All fields are immutable.
 * - Lombok is not needed (records generate all boilerplate).
 * - Jackson 2.12+ fully supports records and field annotations like @JsonAlias.
 * - @JsonInclude ensures only non-null fields are serialized.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RxClaim(
        String cagm,
        String memberIdentifier,
        String status,
        String recordId,
        String claimStatus,
        String claimId,
        String sequenceNumber,
        String firstName,
        String middleName,
        String lastName,
        String dateOfBirth,
        String gender,
        String address1,
        String address2,
        String city,
        String stateCode,
        String zipCode,
        String snapshotTime,
        String galaxyIdentifier,
        String addressType,
        String pharmacyId,
        String drugCode,
        String healthPlanIdentifier,
        String daysSupply,
        String prescriptionNumber,
        @JsonAlias("prescriberSpecialityDescription") String prescriberSpecialtyDescription,
        String quantityFilled,
        String fillDate,
        String drugCodeType,
        String drugStrength,
        String drugCarrier,
        @JsonAlias("prescriberSpecialityCode") String prescriberSpecialtyCode,
        String refillNumber,
        String drugName,
        String prescribingProviderId,
        String totalRefills,
        String providerName
) {
    // No setters, no custom logic: just a pure, immutable data carrier.
    // Add validation or computed methods here if needed.
}
