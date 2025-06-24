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
================================= This code Future proofe for java 21
package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * ✅ Modern Java 21+ DTO as a class:
 * - Uses @Builder for clean, flexible object creation
 * - Fields are final (immutable, thread-safe)
 * - Null safety and value cleanup in canonical constructor
 * - No public setters; object state cannot be changed after creation
 * - @JsonInclude to ignore nulls in serialization
 */
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(toBuilder = true) // enables builder pattern and easy copying
public class RxEligibilityDto {

    private final String healthPlanAccountIdentifier;
    private final String endDate;
    private final String healthPlanIdentifier;
    private final String startDate;

    // Canonical constructor to perform validation and normalization
    public RxEligibilityDto(String healthPlanAccountIdentifier, String endDate,
                            String healthPlanIdentifier, String startDate) {
        // 🟢 Use Utils.stringFieldValidator for field normalization (as in Java 8)
        this.healthPlanAccountIdentifier = Utils.stringFieldValidator(healthPlanAccountIdentifier);
        this.endDate = Utils.stringFieldValidator(endDate);
        this.healthPlanIdentifier = Utils.stringFieldValidator(healthPlanIdentifier);
        this.startDate = Utils.stringFieldValidator(startDate);
    }

    // 🟢 Optionally, provide a no-args constructor if frameworks (like Jackson) need it
    public RxEligibilityDto() {
        this(null, null, null, null);
    }
}
===========================================Java 21 Record=====================================================>

package com.optum.pure.model.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.optum.pure.common.Utils;

/**
 * Java 21 modernized RxEligibilityDto using a record:
 * - Immutability by default (fields can't be changed after construction).
 * - No Lombok needed (records generate accessors, toString, equals, hashCode).
 * - Jackson @JsonInclude works as before.
 * - Validation logic now happens at object creation via a static factory method.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RxEligibilityDto(
        String healthPlanAccountIdentifier,
        String endDate,
        String healthPlanIdentifier,
        String startDate
) {
    /**
     * Factory method for input validation.
     * Ensures all fields are validated before record creation.
     */
    public static RxEligibilityDto of(
            String healthPlanAccountIdentifier,
            String endDate,
            String healthPlanIdentifier,
            String startDate
    ) {
        return new RxEligibilityDto(
            Utils.stringFieldValidator(healthPlanAccountIdentifier),
            Utils.stringFieldValidator(endDate),
            Utils.stringFieldValidator(healthPlanIdentifier),
            Utils.stringFieldValidator(startDate)
        );
    }
}

    /**
     * Sets the start date after validation.
     */
    public void setStartDate(String startDate) {
        this.startDate = Utils.stringFieldValidator(startDate);
    }
}
