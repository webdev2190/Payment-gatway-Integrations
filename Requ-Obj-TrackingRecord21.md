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
====================================================Java 21 Record=====================================================>

package com.optum.pure.model.requestobjects.common;

import java.util.Map;
import java.util.Objects;

/**
 * Modern, immutable TrackingRecord using Java 21 record and builder pattern.
 */
public record TrackingRecord(
        String trackingId,
        String status,
        String errorDescription,
        String inputArtifactUri,
        String outputArtifactUri,
        String receivedTimestamp,
        String processingStartedTimestamp,
        String processingCompletedTimestamp,
        String callerId,
        String version,
        String requestUri,
        String producerUri,
        String consumerUri,
        // Time metrics
        long timeToWriteInputToFileStore,
        long timeToInsertTrackingRecord,
        long timeToEmitNotification,
        String notificationEmitTimestamp,
        String notificationReceivedTimestamp,
        long timeToFetchAltIds,
        long timeToReadInputFromFileStore,
        long timeToFetchClaimsAndEligibilities,
        long timeToWriteOutputToFileStore,
        long totalTimeToProcessNotification,
        long timeToUpdateStatusFromNotYetStarted,
        long timeToUpdateStatusFromInProgress,
        long timeToReadOutputFromFileStore,
        Map<String, Integer> timeMetrics,
        // Record counts
        int tokenCountReceived,
        int tokenCountMatched,
        int tokenCountMatchedWithData,
        int rxClaimsCount,
        int rxEligCount,
        int medClaimsCount,
        int medEligCount
) {
    // Compact constructor: you can add null checks if required for critical fields
    public TrackingRecord {
        // Example: trackingId should never be null
        Objects.requireNonNull(trackingId, "trackingId cannot be null");
        // Defensive copy for Map (immutability)
        timeMetrics = timeMetrics == null ? Map.of() : Map.copyOf(timeMetrics);
    }

    /**
     * Static builder method for easy fluent creation.
     * Example usage:
     *   TrackingRecord rec = TrackingRecord.builder("track-123")
     *        .status("STARTED")
     *        .callerId("system")
     *        .build();
     */
    public static Builder builder(String trackingId) {
        return new Builder(trackingId);
    }

    // --- Modern builder for the record ---
    public static class Builder {
        private String trackingId;
        private String status;
        private String errorDescription;
        private String inputArtifactUri;
        private String outputArtifactUri;
        private String receivedTimestamp;
        private String processingStartedTimestamp;
        private String processingCompletedTimestamp;
        private String callerId;
        private String version;
        private String requestUri;
        private String producerUri;
        private String consumerUri;
        private long timeToWriteInputToFileStore;
        private long timeToInsertTrackingRecord;
        private long timeToEmitNotification;
        private String notificationEmitTimestamp;
        private String notificationReceivedTimestamp;
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

        public Builder(String trackingId) { this.trackingId = trackingId; }

        public Builder status(String value) { this.status = value; return this; }
        public Builder errorDescription(String value) { this.errorDescription = value; return this; }
        public Builder inputArtifactUri(String value) { this.inputArtifactUri = value; return this; }
        public Builder outputArtifactUri(String value) { this.outputArtifactUri = value; return this; }
        public Builder receivedTimestamp(String value) { this.receivedTimestamp = value; return this; }
        public Builder processingStartedTimestamp(String value) { this.processingStartedTimestamp = value; return this; }
        public Builder processingCompletedTimestamp(String value) { this.processingCompletedTimestamp = value; return this; }
        public Builder callerId(String value) { this.callerId = value; return this; }
        public Builder version(String value) { this.version = value; return this; }
        public Builder requestUri(String value) { this.requestUri = value; return this; }
        public Builder producerUri(String value) { this.producerUri = value; return this; }
        public Builder consumerUri(String value) { this.consumerUri = value; return this; }
        public Builder timeToWriteInputToFileStore(long value) { this.timeToWriteInputToFileStore = value; return this; }
        public Builder timeToInsertTrackingRecord(long value) { this.timeToInsertTrackingRecord = value; return this; }
        public Builder timeToEmitNotification(long value) { this.timeToEmitNotification = value; return this; }
        public Builder notificationEmitTimestamp(String value) { this.notificationEmitTimestamp = value; return this; }
        public Builder notificationReceivedTimestamp(String value) { this.notificationReceivedTimestamp = value; return this; }
        public Builder timeToFetchAltIds(long value) { this.timeToFetchAltIds = value; return this; }
        public Builder timeToReadInputFromFileStore(long value) { this.timeToReadInputFromFileStore = value; return this; }
        public Builder timeToFetchClaimsAndEligibilities(long value) { this.timeToFetchClaimsAndEligibilities = value; return this; }
        public Builder timeToWriteOutputToFileStore(long value) { this.timeToWriteOutputToFileStore = value; return this; }
        public Builder totalTimeToProcessNotification(long value) { this.totalTimeToProcessNotification = value; return this; }
        public Builder timeToUpdateStatusFromNotYetStarted(long value) { this.timeToUpdateStatusFromNotYetStarted = value; return this; }
        public Builder timeToUpdateStatusFromInProgress(long value) { this.timeToUpdateStatusFromInProgress = value; return this; }
        public Builder timeToReadOutputFromFileStore(long value) { this.timeToReadOutputFromFileStore = value; return this; }
        public Builder timeMetrics(Map<String, Integer> value) { this.timeMetrics = value; return this; }
        public Builder tokenCountReceived(int value) { this.tokenCountReceived = value; return this; }
        public Builder tokenCountMatched(int value) { this.tokenCountMatched = value; return this; }
        public Builder tokenCountMatchedWithData(int value) { this.tokenCountMatchedWithData = value; return this; }
        public Builder rxClaimsCount(int value) { this.rxClaimsCount = value; return this; }
        public Builder rxEligCount(int value) { this.rxEligCount = value; return this; }
        public Builder medClaimsCount(int value) { this.medClaimsCount = value; return this; }
        public Builder medEligCount(int value) { this.medEligCount = value; return this; }

        public TrackingRecord build() {
            return new TrackingRecord(
                    trackingId,
                    status,
                    errorDescription,
                    inputArtifactUri,
                    outputArtifactUri,
                    receivedTimestamp,
                    processingStartedTimestamp,
                    processingCompletedTimestamp,
                    callerId,
                    version,
                    requestUri,
                    producerUri,
                    consumerUri,
                    timeToWriteInputToFileStore,
                    timeToInsertTrackingRecord,
                    timeToEmitNotification,
                    notificationEmitTimestamp,
                    notificationReceivedTimestamp,
                    timeToFetchAltIds,
                    timeToReadInputFromFileStore,
                    timeToFetchClaimsAndEligibilities,
                    timeToWriteOutputToFileStore,
                    totalTimeToProcessNotification,
                    timeToUpdateStatusFromNotYetStarted,
                    timeToUpdateStatusFromInProgress,
                    timeToReadOutputFromFileStore,
                    timeMetrics,
                    tokenCountReceived,
                    tokenCountMatched,
                    tokenCountMatchedWithData,
                    rxClaimsCount,
                    rxEligCount,
                    medClaimsCount,
                    medEligCount
            );
        }
    }
}
