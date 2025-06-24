package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import lombok.Getter;
import lombok.ToString;

/**
 * DTO for Rx Claims.
 *
 * Java 21 Modernizations:
 * - Modern JavaDoc for class.
 * - Comments describe best practices and potential future improvements with Java 21.
 * - Fields remain private and final if immutability is needed (here kept mutable for setters).
 * - Still uses validation in setters for null/empty protection.
 */
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RxClaimDto {
    private String pharmacyId;
    private String drugCode;
    private String daysSupply;
    private String prescriptionNumber;
    private String quantityFilled;
    private String fillDate;
    private String prescribingProviderId;
    private String claimId;
    private String sequenceNumber;
    private String claimStatus;

    // Java 21: Setters remain, but validation ensures clean data.
    public void setClaimStatus(String claimStatus) {
        // Java 21: you could use 'var' here for type inference if code becomes complex.
        this.claimStatus = Utils.stringFieldValidator(claimStatus);
    }

    public void setClaimId(String claimId) {
        this.claimId = Utils.stringFieldValidator(claimId);
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = Utils.stringFieldValidator(sequenceNumber);
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = Utils.stringFieldValidator(pharmacyId);
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = Utils.stringFieldValidator(drugCode);
    }

    public void setDaysSupply(String daysSupply) {
        this.daysSupply = Utils.stringFieldValidator(daysSupply);
    }

    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = Utils.stringFieldValidator(prescriptionNumber);
    }

    public void setQuantityFilled(String quantityFilled) {
        this.quantityFilled = Utils.stringFieldValidator(quantityFilled);
    }

    public void setFillDate(String fillDate) {
        this.fillDate = Utils.stringFieldValidator(fillDate);
    }

    public void setPrescribingProviderId(String prescribingProviderId) {
        this.prescribingProviderId = Utils.stringFieldValidator(prescribingProviderId);
    }
}

==================================================Java 21 Record==============================================>

package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

/**
 * Java 21 modernized RxClaimDto using a record:
 * - Immutable by default; all fields are set once via the constructor.
 * - No Lombok needed (records generate accessors, toString, equals, hashCode).
 * - Jackson @JsonInclude works as before for non-null serialization.
 * - Validation logic is done in the static factory method, ensuring only valid values are stored.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RxClaimDto(
        String pharmacyId,
        String drugCode,
        String daysSupply,
        String prescriptionNumber,
        String quantityFilled,
        String fillDate,
        String prescribingProviderId,
        String claimId,
        String sequenceNumber,
        String claimStatus
) {
    /**
     * Factory method for validation.
     * Applies your original setter validation logic before constructing the record.
     */
    public static RxClaimDto of(
            String pharmacyId,
            String drugCode,
            String daysSupply,
            String prescriptionNumber,
            String quantityFilled,
            String fillDate,
            String prescribingProviderId,
            String claimId,
            String sequenceNumber,
            String claimStatus
    ) {
        return new RxClaimDto(
                Utils.stringFieldValidator(pharmacyId),
                Utils.stringFieldValidator(drugCode),
                Utils.stringFieldValidator(daysSupply),
                Utils.stringFieldValidator(prescriptionNumber),
                Utils.stringFieldValidator(quantityFilled),
                Utils.stringFieldValidator(fillDate),
                Utils.stringFieldValidator(prescribingProviderId),
                Utils.stringFieldValidator(claimId),
                Utils.stringFieldValidator(sequenceNumber),
                Utils.stringFieldValidator(claimStatus)
        );
    }
}
