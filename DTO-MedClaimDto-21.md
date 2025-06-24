package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import com.optum.pure.model.entity.IcdDiagnosisCodesItem;
import com.optum.pure.model.entity.ServiceProcedureItem;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for Medical Claims.
 *
 * Java 21 Modernizations:
 * - Used List.of() for immutable exclusionList (Java 9+, recommended in Java 21)
 * - Used enhanced for-loop with 'var' for type inference (Java 10+, recommended)
 * - Added final to static field for immutability
 * - Comments clarify Java 21 idioms and possible further modernization
 * - Javadoc improved
 */
@Getter
@ToString
@Log4j2
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedClaimDto {

    // Java 21: List.of() is immutable and more efficient than Arrays.asList()
    @JsonIgnore
    private static final List<String> EXCLUSION_LIST = List.of("null", "NONE", "NO NDC");

    private String providerSpecialtyDescription;
    private String providerNpi;
    private String providerSpecialtyCode;
    private String adjudicationFlag;
    private String updateDate;
    private String serviceDate;
    private List<IcdDiagnosisCodesItem> icdDiagnosisCodes;
    private String ndcCodeDescription;
    private String claimType;
    private String ndcCode;
    private List<ServiceProcedureItem> serviceProcedures;
    private String claimId;
    private String icdCodeType;
    private String claimStatus;

    /**
     * Returns a list of ICD diagnosis codes as Strings.
     * Java 21: uses 'var' for local variable type inference (readable, modern).
     */
    public List<String> getIcdDiagnosisCodes() {
        if (icdDiagnosisCodes == null)
            return null;
        var items = new ArrayList<String>();
        for (var icdDiagnosisCodesItem : icdDiagnosisCodes) {
            items.add(icdDiagnosisCodesItem.getIcdDiagnosisCode());
        }
        return Utils.listFieldValidator(items);
    }

    // Java 21: Mutators remain unchanged, but could use 'Optional' for future modernization

    public void setIcdDiagnosisCodes(List<IcdDiagnosisCodesItem> icdDiagnosisCodes) {
        this.icdDiagnosisCodes = Utils.listFieldValidator(icdDiagnosisCodes);
    }

    public void setIcdCodeType(String icdCodeType) {
        this.icdCodeType = Utils.stringFieldValidator(icdCodeType);
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = Utils.stringFieldValidator(claimStatus);
    }

    public void setAdjudicationFlag(String adjudicationFlag) {
        this.adjudicationFlag = Utils.stringFieldValidator(adjudicationFlag);
    }

    public void setClaimId(String claimId) {
        this.claimId = Utils.stringFieldValidator(claimId);
    }

    public void setProviderSpecialtyDescription(String providerSpecialtyDescription) {
        this.providerSpecialtyDescription = Utils.stringFieldValidator(providerSpecialtyDescription);
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = Utils.stringFieldValidator(updateDate);
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = Utils.stringFieldValidator(serviceDate);
    }

    public void setNdcCode(String ndcCode) {
        this.ndcCode = Utils.stringFieldValidator(ndcCode);
    }

    /**
     * Java 21: EXCLUSION_LIST is now immutable. Stream API is modern and readable.
     */
    public void setNdcCodeDescription(String ndcCodeDescription) {
        if (ndcCodeDescription == null || EXCLUSION_LIST.stream().anyMatch(ndcCodeDescription::equalsIgnoreCase)
                || ndcCodeDescription.isEmpty()) {
            this.ndcCodeDescription = null;
        } else {
            this.ndcCodeDescription = ndcCodeDescription;
        }
    }

    public void setClaimType(String claimType) {
        this.claimType = Utils.stringFieldValidator(claimType);
    }

    public void setServiceProcedures(List<ServiceProcedureItem> serviceProcedures) {
        this.serviceProcedures = Utils.listFieldValidator(serviceProcedures);
    }

    public void setProviderNpi(String providerNpi) {
        this.providerNpi = Utils.stringFieldValidator(providerNpi);
    }

    public void setProviderSpecialtyCode(String providerSpecialtyCode) {
        this.providerSpecialtyCode = Utils.stringFieldValidator(providerSpecialtyCode);
    }
}
=============================================Java 21 Record================================================>

package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import com.optum.pure.model.entity.IcdDiagnosisCodesItem;
import com.optum.pure.model.entity.ServiceProcedureItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Java 21 modernized MedClaimDto using a record:
 * - Immutable by default (fields can't be changed after creation).
 * - No Lombok needed (record provides accessors, toString, equals, hashCode).
 * - Validation, filtering, and field normalization is done up-front in the static factory method.
 * - @JsonInclude works as before for Jackson.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MedClaimDto(
        String providerSpecialtyDescription,
        String providerNpi,
        String providerSpecialtyCode,
        String adjudicationFlag,
        String updateDate,
        String serviceDate,
        List<IcdDiagnosisCodesItem> icdDiagnosisCodes,
        String ndcCodeDescription,
        String claimType,
        String ndcCode,
        List<ServiceProcedureItem> serviceProcedures,
        String claimId,
        String icdCodeType,
        String claimStatus
) {
    @JsonIgnore
    private static final List<String> exclusionList = Arrays.asList("null", "NONE", "NO NDC");

    /**
     * Factory method for validation and normalization.
     */
    public static MedClaimDto of(
            String providerSpecialtyDescription,
            String providerNpi,
            String providerSpecialtyCode,
            String adjudicationFlag,
            String updateDate,
            String serviceDate,
            List<IcdDiagnosisCodesItem> icdDiagnosisCodes,
            String ndcCodeDescription,
            String claimType,
            String ndcCode,
            List<ServiceProcedureItem> serviceProcedures,
            String claimId,
            String icdCodeType,
            String claimStatus
    ) {
        // Apply validation for all string/list fields as in original setters
        providerSpecialtyDescription = Utils.stringFieldValidator(providerSpecialtyDescription);
        providerNpi = Utils.stringFieldValidator(providerNpi);
        providerSpecialtyCode = Utils.stringFieldValidator(providerSpecialtyCode);
        adjudicationFlag = Utils.stringFieldValidator(adjudicationFlag);
        updateDate = Utils.stringFieldValidator(updateDate);
        serviceDate = Utils.stringFieldValidator(serviceDate);
        icdDiagnosisCodes = Utils.listFieldValidator(icdDiagnosisCodes);
        ndcCode = Utils.stringFieldValidator(ndcCode);
        claimType = Utils.stringFieldValidator(claimType);
        serviceProcedures = Utils.listFieldValidator(serviceProcedures);
        claimId = Utils.stringFieldValidator(claimId);
        icdCodeType = Utils.stringFieldValidator(icdCodeType);
        claimStatus = Utils.stringFieldValidator(claimStatus);

        // Exclusion logic for NDC code description
        if (ndcCodeDescription == null ||
                exclusionList.stream().anyMatch(e -> e.equalsIgnoreCase(ndcCodeDescription)) ||
                ndcCodeDescription.isEmpty()) {
            ndcCodeDescription = null;
        }

        return new MedClaimDto(
                providerSpecialtyDescription,
                providerNpi,
                providerSpecialtyCode,
                adjudicationFlag,
                updateDate,
                serviceDate,
                icdDiagnosisCodes,
                ndcCodeDescription,
                claimType,
                ndcCode,
                serviceProcedures,
                claimId,
                icdCodeType,
                claimStatus
        );
    }

    /**
     * Returns validated ICD diagnosis codes as a List of Strings.
     */
    public List<String> icdDiagnosisCodesAsStrings() {
        if (icdDiagnosisCodes == null)
            return null;
        List<String> items = new ArrayList<>();
        for (IcdDiagnosisCodesItem item : icdDiagnosisCodes) {
            items.add(item.icdDiagnosisCode());
        }
        return Utils.listFieldValidator(items);
    }
}
