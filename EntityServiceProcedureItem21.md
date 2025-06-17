package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.optum.pure.common.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Modernized ServiceProcedureItem class using Java 21 features:
 * - Uses immutable fields with final for thread safety and consistency.
 * - @Builder pattern for clean object construction.
 * - @JsonInclude for cleaner JSON output (no null fields).
 * - Constructor validates fields using Utils.stringFieldValidator().
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
@Builder(toBuilder = true) // Builder pattern for flexible object creation
@Log4j2
public class ServiceProcedureItem {

    private final List<ServiceDiagnosesItem> serviceDiagnoses;
    private final String typeOfServiceCode;
    private final String revenueCodeDescription;
    private final String procedureCode;
    private final String procedureCodeType;
    private final String quantityOfServices;
    private final List<ProcedureCodeModifiersItem> procedureCodeModifiers;
    private final String toDate;
    private final String placeOfService;
    @JsonIgnore
    private final String typeOfServiceDescription;
    private final String fromDate;
    @JsonIgnore
    private final String procedureCodeDescription;
    private final String lineNumber;
    @JsonIgnore
    private final String placeOfServiceDescription;
    private final String revenueCode;

    /**
     * Constructor that validates and initializes the fields.
     */
    public ServiceProcedureItem(List<ServiceDiagnosesItem> serviceDiagnoses, String typeOfServiceCode, String revenueCodeDescription,
                                String procedureCode, String procedureCodeType, String quantityOfServices, List<ProcedureCodeModifiersItem> procedureCodeModifiers,
                                String toDate, String placeOfService, String typeOfServiceDescription, String fromDate, String procedureCodeDescription,
                                String lineNumber, String placeOfServiceDescription, String revenueCode) {
        this.serviceDiagnoses = Utils.listFieldValidator(serviceDiagnoses);
        this.typeOfServiceCode = Utils.stringFieldValidator(typeOfServiceCode);
        this.revenueCodeDescription = Utils.stringFieldValidator(revenueCodeDescription);
        this.procedureCode = Utils.stringFieldValidator(procedureCode);
        this.procedureCodeType = Utils.stringFieldValidator(procedureCodeType);
        this.quantityOfServices = Utils.stringFieldValidator(quantityOfServices);
        this.procedureCodeModifiers = Utils.listFieldValidator(procedureCodeModifiers);
        this.toDate = Utils.stringFieldValidator(toDate);
        this.placeOfService = Utils.stringFieldValidator(placeOfService);
        this.typeOfServiceDescription = Utils.stringFieldValidator(typeOfServiceDescription);
        this.fromDate = Utils.stringFieldValidator(fromDate);
        this.procedureCodeDescription = Utils.stringFieldValidator(procedureCodeDescription);
        this.lineNumber = Utils.stringFieldValidator(lineNumber);
        this.placeOfServiceDescription = Utils.stringFieldValidator(placeOfServiceDescription);
        this.revenueCode = Utils.stringFieldValidator(revenueCode);
    }

    // **Updated Getters** (Since fields are immutable, getters are automatically available via @Getter)

    /**
     * Get list of service diagnosis codes.
     */
    public List<String> getServiceDiagnoses() {
        if (serviceDiagnoses == null) return null;
        List<String> items = new ArrayList<>();
        for (ServiceDiagnosesItem serviceDiagnosesItem : serviceDiagnoses) {
            items.add(serviceDiagnosesItem.getDiagnosisCode());
        }
        return Utils.listFieldValidator(items);
    }

    /**
     * Get list of procedure code modifiers.
     */
    public List<String> getProcedureCodeModifiers() {
        if (procedureCodeModifiers == null) return null;
        List<String> items = new ArrayList<>();
        for (ProcedureCodeModifiersItem procedureCodeModifiersItem : procedureCodeModifiers) {
            items.add(procedureCodeModifiersItem.getProcedureCodeModifier());
        }
        return Utils.listFieldValidator(items);
    }

    /**
     * Logging example for debugging (using Log4j2)
     */
    public void logServiceProcedureDetails() {
        log.info("Service Procedure Item Details: {}", this);
    }
}

