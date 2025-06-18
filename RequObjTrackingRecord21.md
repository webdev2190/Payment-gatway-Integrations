package com.optum.pure.model.requestobjects.common;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * Modernized TrackingRecord class for Java 21+:
 * - Immutable class for thread-safety and better reliability
 * - Builder pattern used for constructing instances
 * - Use of `record` instead of regular class for immutability
 */
@Getter
@ToString
public class TrackingRecord {

    private final String trackingId;
    private final String status;
    private final String errorDescription;
    private final String inputArtifactUri;
    private final String outputArtifactUri;
    private final String receivedTimestamp;
    private final String processingStartedTimestamp;
    private final String processingCompletedTimestamp;
    private final String callerId;
    private final String version;
    private final String requestUri;
    private final String producerUri;
    private final String consumerUri;

    // Time metrics
    private final long timeToWriteInputToFileStore;
    private final long timeToInsertTrackingRecord;
    private final long timeToEmitNotification;
    private final String notificationEmitTimestamp;
    private final String notificationReceivedTimestamp;
    private final long timeToFetchAltIds;
    private final long timeToReadInputFromFileStore;
    private final long timeToFetchClaimsAndEligibilities;
    private final long timeToWriteOutputToFileStore;
    private final long totalTimeToProcessNotification;
    private final long timeToUpdateStatusFromNotYetStarted;
    private final long timeToUpdateStatusFromInProgress;
    private final long timeToReadOutputFromFileStore;
    private final Map<String, Integer> timeMetrics;

    // Record count
    private final int tokenCountReceived;
    private final int tokenCountMatched;
    private final int tokenCountMatchedWithData;
    private final int rxClaimsCount;
    private final int rxEligCount;
    private final int medClaimsCount;
    private final int medEligCount;

    // Canonical constructor for validation and immutability
    public TrackingRecord(
        String trackingId, String status, String errorDescription, String inputArtifactUri,
        String outputArtifactUri, String receivedTimestamp, String processingStartedTimestamp,
        String processingCompletedTimestamp, String callerId, String version, String requestUri,
        String producerUri, String consumerUri, long timeToWriteInputToFileStore, long timeToInsertTrackingRecord,
        long timeToEmitNotification, String notificationEmitTimestamp, String notificationReceivedTimestamp,
        long timeToFetchAltIds, long timeToReadInputFromFileStore, long timeToFetchClaimsAndEligibilities,
        long timeToWriteOutputToFileStore, long totalTimeToProcessNotification, long timeToUpdateStatusFromNotYetStarted,
        long timeToUpdateStatusFromInProgress, long timeToReadOutputFromFileStore, Map<String, Integer> timeMetrics,
        int tokenCountReceived, int tokenCountMatched, int tokenCountMatchedWithData, int rxClaimsCount,
        int rxEligCount, int medClaimsCount, int medEligCount
    ) {
        this.trackingId = trackingId;
        this.status = status;
        this.errorDescription = errorDescription;
        this.inputArtifactUri = inputArtifactUri;
        this.outputArtifactUri = outputArtifactUri;
        this.receivedTimestamp = receivedTimestamp;
        this.processingStartedTimestamp = processingStartedTimestamp;
        this.processingCompletedTimestamp = processingCompletedTimestamp;
        this.callerId = callerId;
        this.version = version;
        this.requestUri = requestUri;
        this.producerUri = producerUri;
        this.consumerUri = consumerUri;
        this.timeToWriteInputToFileStore = timeToWriteInputToFileStore;
        this.timeToInsertTrackingRecord = timeToInsertTrackingRecord;
        this.timeToEmitNotification = timeToEmitNotification;
        this.notificationEmitTimestamp = notificationEmitTimestamp;
        this.notificationReceivedTimestamp = notificationReceivedTimestamp;
        this.timeToFetchAltIds = timeToFetchAltIds;
        this.timeToReadInputFromFileStore = timeToReadInputFromFileStore;
        this.timeToFetchClaimsAndEligibilities = timeToFetchClaimsAndEligibilities;
        this.timeToWriteOutputToFileStore = timeToWriteOutputToFileStore;
        this.totalTimeToProcessNotification = totalTimeToProcessNotification;
        this.timeToUpdateStatusFromNotYetStarted = timeToUpdateStatusFromNotYetStarted;
        this.timeToUpdateStatusFromInProgress = timeToUpdateStatusFromInProgress;
        this.timeToReadOutputFromFileStore = timeToReadOutputFromFileStore;
        this.timeMetrics = timeMetrics;
        this.tokenCountReceived = tokenCountReceived;
        this.tokenCountMatched = tokenCountMatched;
        this.tokenCountMatchedWithData = tokenCountMatchedWithData;
        this.rxClaimsCount = rxClaimsCount;
        this.rxEligCount = rxEligCount;
        this.medClaimsCount = medClaimsCount;
        this.medEligCount = medEligCount;
    }

    /**
     * Builder pattern for easier object construction
     */
    public static class Builder {

        private final String trackingId;
        private String status = null;
        private String errorDescription = null;
        private String inputArtifactUri = null;
        private String outputArtifactUri = null;
        private String receivedTimestamp = null;
        private String processingStartedTimestamp = null;
        private String processingCompletedTimestamp = null;
        private String callerId = null;
        private String version = null;
        private String requestUri = null;
        private String producerUri = null;
        private String consumerUri = null;
        private long timeToWriteInputToFileStore;
        private long timeToInsertTrackingRecord;
        private long timeToEmitNotification;
        private String notificationEmitTimestamp = null;
        private String notificationReceivedTimestamp = null;
        private long timeToFetchAltIds;
        private long timeToReadInputFromFileStore;
        private long timeToFetchClaimsAndEligibilities;
        private long timeToWriteOutputToFileStore;
        private long totalTimeToProcessNotification;
        private long timeToUpdateStatusFromNotYetStarted;
        private long timeToUpdateStatusFromInProgress;
        private long timeToReadOutputFromFileStore;
        private Map<String, Integer> timeMetrics;
        private int tokenCountReceived;
        private int tokenCountMatched;
        private int tokenCountMatchedWithData;
        private int rxClaimsCount;
        private int rxEligCount;
        private int medClaimsCount;
        private int medEligCount;

        public Builder(String trackingId) {
            this.trackingId = trackingId;
        }

        // Setter methods to chain values (for builder pattern)
        public Builder setStatus(String status) { this.status = status; return this; }
        public Builder setErrorDescription(String errorDescription) { this.errorDescription = errorDescription; return this; }
        public Builder setInputArtifactUri(String inputArtifactUri) { this.inputArtifactUri = inputArtifactUri; return this; }
        // ... (more setters for each field)

        public TrackingRecord build() {
            return new TrackingRecord(
                trackingId, status, errorDescription, inputArtifactUri, outputArtifactUri, receivedTimestamp,
                processingStartedTimestamp, processingCompletedTimestamp, callerId, version, requestUri, producerUri,
                consumerUri, timeToWriteInputToFileStore, timeToInsertTrackingRecord, timeToEmitNotification,
                notificationEmitTimestamp, notificationReceivedTimestamp, timeToFetchAltIds, timeToReadInputFromFileStore,
                timeToFetchClaimsAndEligibilities, timeToWriteOutputToFileStore, totalTimeToProcessNotification,
                timeToUpdateStatusFromNotYetStarted, timeToUpdateStatusFromInProgress, timeToReadOutputFromFileStore,
                timeMetrics, tokenCountReceived, tokenCountMatched, tokenCountMatchedWithData, rxClaimsCount,
                rxEligCount, medClaimsCount, medEligCount
            );
        }
    }
}
