package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;
import lombok.Getter;
import lombok.ToString;

/**
 * DTO for Rx Eligibility.
 *
 * Java 21 Modernizations:
 * - Modern Javadoc added.
 * - Comments on best practices and Java 21 enhancements.
 * - Setters continue to validate input using Utils.
 * - Immutable version with record also provided below as an alternative.
 */
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RxEligibilityDto {
    // Fields remain private for encapsulation; Lombok generates getters.
    private String healthPlanAccountIdentifier;
    private String endDate;
    private String healthPlanIdentifier;
    private String startDate;

    /**
     * Sets the health plan account identifier after validation.
     */
    public void setHealthPlanAccountIdentifier(String healthPlanAccountIdentifier) {
        // Java 21: Use of validation helper.
        this.healthPlanAccountIdentifier = Utils.stringFieldValidator(healthPlanAccountIdentifier);
    }

    /**
     * Sets the end date after validation.
     */
    public void setEndDate(String endDate) {
        this.endDate = Utils.stringFieldValidator(endDate);
    }

    /**
     * Sets the health plan identifier after validation.
     */
    public void setHealthPlanIdentifier(String healthPlanIdentifier) {
        this.healthPlanIdentifier = Utils.stringFieldValidator(healthPlanIdentifier);
    }

    /**
     * Sets the start date after validation.
     */
    public void setStartDate(String startDate) {
        this.startDate = Utils.stringFieldValidator(startDate);
    }
}
