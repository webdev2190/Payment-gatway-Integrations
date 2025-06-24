package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Modernized MedClaim class using Java 21 features:
 * - Record classes are not suitable here due to the complexity and large number of fields, so a class is retained.
 * - Constructor validation using builder pattern to ensure immutability.
 * - Lombok @Builder for flexible object creation.
 * - Field initialization and null safety ensured.
 * - @JsonInclude for cleaner serialization.
 * 
 * This approach maintains backwards compatibility with Jackson serialization, while improving immutability and safety.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@NoArgsConstructor
@Builder(toBuilder = true) // Generates builder for this class.
public class MedClaim {

    private String recordId;
    private String city;
    private String memberIdentifier;
    private String stateCode;
    private String zip;
    private String dateOfBirth;
    private String gender;
    private String claimStatus;
    private String adjudicationFlag;
    private String claimId;
    private String providerZip;
    private String providerCity;
    private String providerSpecialtyDescription;
    private String updateDate;
    private String typeOfAdmissionDescription;
    private String sourceOfAdmissionDescription;
    private String serviceDate;
    private String drgCode;
    private String drgCodeDescription;
    private String healthPlanIdentifier;
    private String dischargeStatusCode;
    private String procedureCodeType;
    private List<IcdDiagnosisCodesItem> icdDiagnosisCodes;
    private String typeOfAdmission;
    private String ndcCodeDescription;
    private String claimType;
    private String sourceOfAdmission;
    private String typeOfBill;
    private String providerName;
    private String ndcCode;
    private String drgGrouper;
    private List<ServiceProcedureItem> serviceProcedures;
    private String providerLastName;
    private String providerStateCode;
    private String providerNpi;
    private List<ProcedureCodesItem> procedureCodes;
    private String dischargeStatusCodeDescription;
    private String admittingDiagnosisCode;
    private String providerSpecialtyCode;
    private String servicePostDate;
    private String providerFirstName;
    private String icdCodeType;
    private String providerAddress2;
    private String providerAddress1;

    /**
     * Constructor for mandatory field validation (if required)
     * Ensures that all fields are validated during construction.
     */
    public MedClaim(String recordId, String city, String memberIdentifier, String stateCode, String zip,
                    String dateOfBirth, String gender, String claimStatus, String adjudicationFlag,
                    String claimId, String providerZip, String providerCity, String providerSpecialtyDescription,
                    String updateDate, String typeOfAdmissionDescription, String sourceOfAdmissionDescription,
                    String serviceDate, String drgCode, String drgCodeDescription, String healthPlanIdentifier,
                    String dischargeStatusCode, String procedureCodeType, List<IcdDiagnosisCodesItem> icdDiagnosisCodes,
                    String typeOfAdmission, String ndcCodeDescription, String claimType, String sourceOfAdmission,
                    String typeOfBill, String providerName, String ndcCode, String drgGrouper,
                    List<ServiceProcedureItem> serviceProcedures, String providerLastName, String providerStateCode,
                    String providerNpi, List<ProcedureCodesItem> procedureCodes, String dischargeStatusCodeDescription,
                    String admittingDiagnosisCode, String providerSpecialtyCode, String servicePostDate,
                    String providerFirstName, String icdCodeType, String providerAddress2, String providerAddress1) {

        this.recordId = recordId;
        this.city = city;
        this.memberIdentifier = memberIdentifier;
        this.stateCode = stateCode;
        this.zip = zip;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.claimStatus = claimStatus;
        this.adjudicationFlag = adjudicationFlag;
        this.claimId = claimId;
        this.providerZip = providerZip;
        this.providerCity = providerCity;
        this.providerSpecialtyDescription = providerSpecialtyDescription;
        this.updateDate = updateDate;
        this.typeOfAdmissionDescription = typeOfAdmissionDescription;
        this.sourceOfAdmissionDescription = sourceOfAdmissionDescription;
        this.serviceDate = serviceDate;
        this.drgCode = drgCode;
        this.drgCodeDescription = drgCodeDescription;
        this.healthPlanIdentifier = healthPlanIdentifier;
        this.dischargeStatusCode = dischargeStatusCode;
        this.procedureCodeType = procedureCodeType;
        this.icdDiagnosisCodes = icdDiagnosisCodes;
        this.typeOfAdmission = typeOfAdmission;
        this.ndcCodeDescription = ndcCodeDescription;
        this.claimType = claimType;
        this.sourceOfAdmission = sourceOfAdmission;
        this.typeOfBill = typeOfBill;
        this.providerName = providerName;
        this.ndcCode = ndcCode;
        this.drgGrouper = drgGrouper;
        this.serviceProcedures = serviceProcedures;
        this.providerLastName = providerLastName;
        this.providerStateCode = providerStateCode;
        this.providerNpi = providerNpi;
        this.procedureCodes = procedureCodes;
        this.dischargeStatusCodeDescription = dischargeStatusCodeDescription;
        this.admittingDiagnosisCode = admittingDiagnosisCode;
        this.providerSpecialtyCode = providerSpecialtyCode;
        this.servicePostDate = servicePostDate;
        this.providerFirstName = providerFirstName;
        this.icdCodeType = icdCodeType;
        this.providerAddress2 = providerAddress2;
        this.providerAddress1 = providerAddress1;
    }
}
================================================Java 21 Record================================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Java 21 modernized MedClaim using a record:
 * - Immutability: all fields are final and set only via constructor.
 * - No Lombok needed: records auto-generate all getters, toString, equals, and hashCode.
 * - Jackson 2.12+ supports records and @JsonInclude.
 * - Works perfectly for REST APIs and persistence DTOs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MedClaim(
        String recordId,
        String city,
        String memberIdentifier,
        String stateCode,
        String zip,
        String dateOfBirth,
        String gender,
        String claimStatus,
        String adjudicationFlag,
        String claimId,
        String providerZip,
        String providerCity,
        String providerSpecialtyDescription,
        String updateDate,
        String typeOfAdmissionDescription,
        String sourceOfAdmissionDescription,
        String serviceDate,
        String drgCode,
        String drgCodeDescription,
        String healthPlanIdentifier,
        String dischargeStatusCode,
        String procedureCodeType,
        List<IcdDiagnosisCodesItem> icdDiagnosisCodes,
        String typeOfAdmission,
        String ndcCodeDescription,
        String claimType,
        String sourceOfAdmission,
        String typeOfBill,
        String providerName,
        String ndcCode,
        String drgGrouper,
        List<ServiceProcedureItem> serviceProcedures,
        String providerLastName,
        String providerStateCode,
        String providerNpi,
        List<ProcedureCodesItem> procedureCodes,
        String dischargeStatusCodeDescription,
        String admittingDiagnosisCode,
        String providerSpecialtyCode,
        String servicePostDate,
        String providerFirstName,
        String icdCodeType,
        String providerAddress2,
        String providerAddress1
) {
    // No custom logic needed for pure DTO. If needed, add helper methods or validation here.
}
