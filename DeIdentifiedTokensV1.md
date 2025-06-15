package com.optum.pure.model.dto.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.optum.pure.model.dto.common.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for De-Identified Tokens V1.
 *
 * Java 21 Modernizations:
 * - Modern Javadoc added.
 * - 'var' used for local variable type inference in the constructor (modern, concise).
 * - Considered use of records for immutability, but left as class for mutability and Lombok compatibility.
 * - Inline comments provided for clarity and future enhancements.
 */
@Getter
@Setter
@ToString
public class DeIdentifiedTokensV1 {

    private String token;
    @JsonIgnore
    private String tokenType;
    private Data data;

    public DeIdentifiedTokensV1() {
        // Java 21: Use 'var' for modern type inference (cleaner and more expressive)
        var dataObj = new Data();
        this.data = dataObj;

        // Alternatively, you can directly assign:
        // this.data = new Data();
        // But 'var' shows the modern Java 21 approach.
    }
}
