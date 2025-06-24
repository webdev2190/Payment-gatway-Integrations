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

============================================Java 21 Record==========================================>

package com.optum.pure.model.dto.common;

import java.util.List;

/**
 * Java 21 modernized Medical using a record:
 * - Immutability: fields are final and cannot change after construction.
 * - No Lombok needed (record provides accessors, toString, equals, hashCode).
 * - Lists are always non-null and unmodifiable, matching your original constructor's behavior.
 */
public record Medical(
        List<MedEligibilityDto> eligibilities,
        List<MedClaimDto> claims
) {
    // Compact constructor: guarantees lists are non-null and unmodifiable
    public Medical {
        eligibilities = (eligibilities == null) ? List.of() : List.copyOf(eligibilities);
        claims = (claims == null) ? List.of() : List.copyOf(claims);
    }

    // Static factory for classic "empty" construction
    public static Medical empty() {
        return new Medical(List.of(), List.of());
    }
}


