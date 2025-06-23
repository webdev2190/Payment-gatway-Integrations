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
