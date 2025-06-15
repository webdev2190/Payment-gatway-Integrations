package com.optum.pure.model.dto.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for Prescription Claims and Eligibilities (Rx).
 *
 * Java 21 Modernizations:
 * - Used 'var' for local variable type inference in the constructor (modern Java, concise)
 * - Kept as a class to support mutability with Lombok @Setter
 * - Comments clarify Java 21 idioms and suggestions
 */
@Getter
@Setter
@ToString
public class Rx {

    private List<RxEligibilityDto> eligibilities;
    private List<RxClaimDto> claims;

    public Rx() {
        // Java 21: Use 'var' for modern, concise local variable declaration
        var eligibilityList = new ArrayList<RxEligibilityDto>();
        var claimList = new ArrayList<RxClaimDto>();
        eligibilities = eligibilityList;
        claims = claimList;

        // Alternative: You can assign directly as in Java 8,
        // eligibilities = new ArrayList<>();
        // claims = new ArrayList<>();
        // but 'var' demonstrates Java 21 type inference.
    }
}
