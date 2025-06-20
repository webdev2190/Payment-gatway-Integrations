package com.optum.pure.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for StatusEnum.java
 *
 * @author Dwarakesh T P
 */

public class StatusEnumTest {

    @Test
    public void enumTest() {
        // Test that the enum's toString method correctly returns the expected value
        Assertions.assertEquals("COMPLETED_SUCCESSFULLY", StatusEnum.COMPLETED_SUCCESSFULLY.toString(), "Enum value should match");
        Assertions.assertEquals("IN_PROGRESS", StatusEnum.IN_PROGRESS.toString(), "Enum value should match");
        Assertions.assertEquals("NOT_YET_STARTED", StatusEnum.NOT_YET_STARTED.toString(), "Enum value should match");
        Assertions.assertEquals("ERRORED", StatusEnum.ERRORED.toString(), "Enum value should match");
        Assertions.assertEquals("INVALID", StatusEnum.INVALID.toString(), "Enum value should match");
    }
}
