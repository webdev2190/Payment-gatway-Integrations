package com.optum.pure.model.dto.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for Medical Section (Claims and Eligibilities)
 *
 * Java 21 Modernizations:
 * - Used 'var' in the constructor for type inference (modern Java, improves readability)
 * - Optionally, could use records for immutability (not shown here because class uses setters)
 * - Comments clarify where Java 21 idioms are applied
 */
@Getter
@Setter
@ToString
public class Medical {

    private List<MedEligibilityDto> eligibilities;
    private List<MedClaimDto> claims;

    public Medical() {
        // Java 21: Use 'var' for local variable type inference (concise and modern)
        var eligibilityList = new ArrayList<MedEligibilityDto>();
        var claimList = new ArrayList<MedClaimDto>();
        eligibilities = eligibilityList;
        claims = claimList;

        // You could even do:
        // eligibilities = new ArrayList<>();
        // claims = new ArrayList<>();
        // But using 'var' shows type inference benefit in Java 21
    }
}
