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
