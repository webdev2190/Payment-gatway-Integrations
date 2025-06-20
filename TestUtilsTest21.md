package com.optum.pure.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit Tests for common Utils.java
 *
 * @author Dwarakesh T P
 */
public class UtilsTest {

    @Test
    public void generateTrackingIdTest() {
        // Generate a tracking ID and assert that it is not null
        String trackingId = Utils.generateTrackingId();
        Assertions.assertNotNull(trackingId, "Tracking ID should not be null");
    }

    @Test
    public void getCurrentDateTest() {
        // Get the current date and assert that it is not null
        String date = Utils.getCurrentDate();
        Assertions.assertNotNull(date, "Date should not be null");
    }

    @Test
    public void getCurrentTimestampTest() {
        // Get the current timestamp and assert that it is not null
        String timestamp = Utils.getCurrentTimestamp();
        Assertions.assertNotNull(timestamp, "Timestamp should not be null");
    }

    @Test
    public void getKafkaResourcePathTest() {
        // Get the Kafka resource path and assert that it is not null
        String path = Utils.getKafkaResourcePath();
        Assertions.assertNotNull(path, "Kafka resource path should not be null");
    }

    @Test
    public void getNewInputArtifactUriTest() {
        // Generate URI for the input artifact and assert that it is not null
        String uri = Utils.getNewInputArtifactUri("12345");
        Assertions.assertNotNull(uri, "Input artifact URI should not be null");
    }

    @Test
    public void getNewOutputArtifactUriTest() {
        // Generate URI for the output artifact and assert that it is not null
        String uri = Utils.getNewOutputArtifactUri("12345");
        Assertions.assertNotNull(uri, "Output artifact URI should not be null");
    }
}
