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
===================================Future Proof Code=====================================================
package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Modernized Java 21 version of ServiceProcedureItem.
 * - No Lombok.
 * - Uses standard constructors.
 * - Streams and Java 21 style for mapping.
 * - Maintains Jackson annotations.
 * - Setter and getter methods for framework compatibility.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceProcedureItem {

    private List<ServiceDiagnosesItem> serviceDiagnoses;
    private String typeOfServiceCode;
    private String revenueCodeDescription;
    private String procedureCode;
    private String procedureCodeType;
    private String quantityOfServices;
    private List<ProcedureCodeModifiersItem> procedureCodeModifiers;
    private String toDate;
    private String placeOfService;
    @JsonIgnore
    private String typeOfServiceDescription;
    private String fromDate;
    @JsonIgnore
    private String procedureCodeDescription;
    private String lineNumber;
    @JsonIgnore
    private String placeOfServiceDescription;
    private String revenueCode;

    // No-args constructor for Jackson and frameworks
    public ServiceProcedureItem() {}

    // All-args constructor for easier instantiation
    public ServiceProcedureItem(
            List<ServiceDiagnosesItem> serviceDiagnoses,
            String typeOfServiceCode,
            String revenueCodeDescription,
            String procedureCode,
            String procedureCodeType,
            String quantityOfServices,
            List<ProcedureCodeModifiersItem> procedureCodeModifiers,
            String toDate,
            String placeOfService,
            String typeOfServiceDescription,
            String fromDate,
            String procedureCodeDescription,
            String lineNumber,
            String placeOfServiceDescription,
            String revenueCode
    ) {
        this.serviceDiagnoses = serviceDiagnoses;
        this.typeOfServiceCode = typeOfServiceCode;
        this.revenueCodeDescription = revenueCodeDescription;
        this.procedureCode = procedureCode;
        this.procedureCodeType = procedureCodeType;
        this.quantityOfServices = quantityOfServices;
        this.procedureCodeModifiers = procedureCodeModifiers;
        this.toDate = toDate;
        this.placeOfService = placeOfService;
        this.typeOfServiceDescription = typeOfServiceDescription;
        this.fromDate = fromDate;
        this.procedureCodeDescription = procedureCodeDescription;
        this.lineNumber = lineNumber;
        this.placeOfServiceDescription = placeOfServiceDescription;
        this.revenueCode = revenueCode;
    }

    // --- Modernized Getters with Validation and Mapping ---

    public List<String> getServiceDiagnoses() {
        if (serviceDiagnoses == null) return null;
        var codes = serviceDiagnoses.stream()
                .map(ServiceDiagnosesItem::getDiagnosisCode)
                .toList(); // Java 16+ style
        return Utils.listFieldValidator(codes);
    }

    public String getTypeOfServiceCode() {
        return Utils.stringFieldValidator(typeOfServiceCode);
    }

    public String getRevenueCodeDescription() {
        return Utils.stringFieldValidator(revenueCodeDescription);
    }

    public String getProcedureCode() {
        return Utils.stringFieldValidator(procedureCode);
    }

    public String getProcedureCodeType() {
        return Utils.stringFieldValidator(procedureCodeType);
    }

    public String getQuantityOfServices() {
        return Utils.stringFieldValidator(quantityOfServices);
    }

    public List<String> getProcedureCodeModifiers() {
        if (procedureCodeModifiers == null) return null;
        var modifiers = procedureCodeModifiers.stream()
                .map(ProcedureCodeModifiersItem::getProcedureCodeModifier)
                .toList(); // Java 16+ style
        return Utils.listFieldValidator(modifiers);
    }

    public String getToDate() {
        return Utils.stringFieldValidator(toDate);
    }

    public String getPlaceOfService() {
        return Utils.stringFieldValidator(placeOfService);
    }

    public String getTypeOfServiceDescription() {
        return Utils.stringFieldValidator(typeOfServiceDescription);
    }

    public String getFromDate() {
        return Utils.stringFieldValidator(fromDate);
    }

    public String getProcedureCodeDescription() {
        return Utils.stringFieldValidator(procedureCodeDescription);
    }

    public String getLineNumber() {
        return Utils.stringFieldValidator(lineNumber);
    }

    public String getPlaceOfServiceDescription() {
        return Utils.stringFieldValidator(placeOfServiceDescription);
    }

    public String getRevenueCode() {
        return Utils.stringFieldValidator(revenueCode);
    }

    // --- Setters for Jackson and framework compatibility ---

    public void setServiceDiagnoses(List<ServiceDiagnosesItem> serviceDiagnoses) {
        this.serviceDiagnoses = serviceDiagnoses;
    }

    public void setTypeOfServiceCode(String typeOfServiceCode) {
        this.typeOfServiceCode = typeOfServiceCode;
    }

    public void setRevenueCodeDescription(String revenueCodeDescription) {
        this.revenueCodeDescription = revenueCodeDescription;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public void setProcedureCodeType(String procedureCodeType) {
        this.procedureCodeType = procedureCodeType;
    }

    public void setQuantityOfServices(String quantityOfServices) {
        this.quantityOfServices = quantityOfServices;
    }

    public void setProcedureCodeModifiers(List<ProcedureCodeModifiersItem> procedureCodeModifiers) {
        this.procedureCodeModifiers = procedureCodeModifiers;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setPlaceOfService(String placeOfService) {
        this.placeOfService = placeOfService;
    }

    public void setTypeOfServiceDescription(String typeOfServiceDescription) {
        this.typeOfServiceDescription = typeOfServiceDescription;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setProcedureCodeDescription(String procedureCodeDescription) {
        this.procedureCodeDescription = procedureCodeDescription;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setPlaceOfServiceDescription(String placeOfServiceDescription) {
        this.placeOfServiceDescription = placeOfServiceDescription;
    }

    public void setRevenueCode(String revenueCode) {
        this.revenueCode = revenueCode;
    }
}

=================================Java 21 Record======================================================>

package com.optum.pure.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

import java.util.List;
import java.util.Objects;

/**
 * Java 21 record version of ServiceProcedureItem.
 * - Fields are immutable and exposed directly.
 * - Use instance methods for computed logic (cannot override accessor names!).
 * - For mapping/validation, provide helper methods.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceProcedureItem(
        List<ServiceDiagnosesItem> serviceDiagnoses,
        String typeOfServiceCode,
        String revenueCodeDescription,
        String procedureCode,
        String procedureCodeType,
        String quantityOfServices,
        List<ProcedureCodeModifiersItem> procedureCodeModifiers,
        String toDate,
        String placeOfService,
        @JsonIgnore String typeOfServiceDescription,
        String fromDate,
        @JsonIgnore String procedureCodeDescription,
        String lineNumber,
        @JsonIgnore String placeOfServiceDescription,
        String revenueCode
) {
    // Example: "computed" getter for diagnosis codes as strings (not standard accessor)
    public List<String> serviceDiagnosisCodes() {
        if (serviceDiagnoses == null) return null;
        var codes = serviceDiagnoses.stream()
                .map(ServiceDiagnosesItem::getDiagnosisCode)
                .toList();
        return Utils.listFieldValidator(codes);
    }

    // Computed getter for validated procedure code modifiers
    public List<String> procedureCodeModifierStrings() {
        if (procedureCodeModifiers == null) return null;
        var modifiers = procedureCodeModifiers.stream()
                .map(ProcedureCodeModifiersItem::getProcedureCodeModifier)
                .toList();
        return Utils.listFieldValidator(modifiers);
    }

    // Example: validated versions of string fields (use these in your service layers)
    public String validatedTypeOfServiceCode() { return Utils.stringFieldValidator(typeOfServiceCode); }
    public String validatedRevenueCodeDescription() { return Utils.stringFieldValidator(revenueCodeDescription); }
    public String validatedProcedureCode() { return Utils.stringFieldValidator(procedureCode); }
    public String validatedProcedureCodeType() { return Utils.stringFieldValidator(procedureCodeType); }
    public String validatedQuantityOfServices() { return Utils.stringFieldValidator(quantityOfServices); }
    public String validatedToDate() { return Utils.stringFieldValidator(toDate); }
    public String validatedPlaceOfService() { return Utils.stringFieldValidator(placeOfService); }
    public String validatedTypeOfServiceDescription() { return Utils.stringFieldValidator(typeOfServiceDescription); }
    public String validatedFromDate() { return Utils.stringFieldValidator(fromDate); }
    public String validatedProcedureCodeDescription() { return Utils.stringFieldValidator(procedureCodeDescription); }
    public String validatedLineNumber() { return Utils.stringFieldValidator(lineNumber); }
    public String validatedPlaceOfServiceDescription() { return Utils.stringFieldValidator(placeOfServiceDescription); }
    public String validatedRevenueCode() { return Utils.stringFieldValidator(revenueCode); }
}

