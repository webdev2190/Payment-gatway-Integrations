package com.optum.pure.common;

/**
 * Enum for response statuses.
 * Java 21-compatible and enhanced with features like custom messages and sealed interfaces (optional).
 */
public enum StatusEnum {
    NOT_YET_STARTED("Not Yet Started"),
    INVALID("Invalid"),
    IN_PROGRESS("In Progress"),
    COMPLETED_SUCCESSFULLY("Completed Successfully"),
    ERRORED("Errored");

    //  Java 21-style enhancement: Add a readable label for each enum value
    private final String label;

    //  Private constructor for enum
    StatusEnum(String label) {
        this.label = label;
    }

    //  Public getter to fetch readable name
    public String getLabel() {
        return label;
    }

    //  Override toString for cleaner output if used in logs or UI
    @Override
    public String toString() {
        return label;
    }
}
======================================================Java 21 New Code with Display Name==================================================>

package com.optum.pure.common;

/**
 * Enum representing the response status for a process.
 */
public enum Status {
    NOT_YET_STARTED("Not Yet Started"),
    INVALID("Invalid"),
    IN_PROGRESS("In Progress"),
    COMPLETED_SUCCESSFULLY("Completed Successfully"),
    ERRORED("Errored");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
