package com.optum.pure.common;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Generic Util class
 */
public final class Utils {

    public static final String FILE_SEPARATOR = "/";
    public static final String TRACKING_ID = "trackingId";
    public static final String VERSION = "version";
    public static final String INPUT_ARTIFACT_URI = "inputArtifactUri";
    public static final String VER_V1 = "v1";
    public static final String VER_V2 = "v2";
    public static final int MAX_RECORDS = 10000;

    private static final Logger LOG = LogManager.getLogger(Utils.class);
    private static AmazonS3 s3Client = null;

    // Private constructor to prevent instantiation
    private Utils() {
    }

    // ✅ Use UUID for unique tracking ID generation
    public static String generateTrackingId() {
        return UUID.randomUUID().toString();
    }

    // ✅ Java 21: Simplified current date formatting using java.time
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

    // ✅ Use java.time for timestamp formatting
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getKafkaResourcePath() {
        return "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;
    }

    // ✅ Clean string validation
    public static String stringFieldValidator(String value) {
        return (value == null || value.isEmpty() || value.equalsIgnoreCase("null") || value.equalsIgnoreCase("none"))
                ? null : value;
    }

    // ✅ Generic list validator
    public static <T> List<T> listFieldValidator(List<T> value) {
        return (value == null || value.isEmpty()) ? null : value;
    }

    public static String getNewInputArtifactUri(String trackingId) {
        return frameUri(trackingId, "input");
    }

    public static String getNewOutputArtifactUri(String trackingId) {
        return frameUriGzip(trackingId, "output");
    }

    private static String frameUri(String trackingId, String context) {
        return context + FILE_SEPARATOR + getCurrentDate() + FILE_SEPARATOR + trackingId + ".json";
    }

    private static String frameUriGzip(String trackingId, String context) {
        return context + FILE_SEPARATOR + getCurrentDate() + FILE_SEPARATOR + trackingId + ".gz";
    }

    // ✅ Modernized S3 client creation using AmazonS3ClientBuilder (AmazonS3Client is deprecated)
    private static AmazonS3 createS3client() {
        String serviceEndPoint = ConfigurationManager.get("OOSS_URL");
        String accessKey = ConfigurationManager.get("OOSS_ACCESS_KEY");
        String secretKey = ConfigurationManager.get("OOSS_SECRET_KEY");

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(serviceEndPoint, "us-east-1"))
                .withPathStyleAccessEnabled(true)
                .build();
    }

    // ✅ Thread-safe lazy initialization of the AmazonS3 client
    public static synchronized AmazonS3 getS3client() {
        if (Objects.isNull(s3Client)) {
            s3Client = createS3client();
            LOG.info("OOSS - Connection successful");
        }
        return s3Client;
    }
}

================================================================Java 21 New Code======================================================>

package com.optum.pure.common;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Java 21 modernized generic utility class.
 * - Uses up-to-date AWS S3 client construction (no deprecated APIs).
 * - Final static fields for constants.
 * - Thread-safe, eager initialization of S3 client.
 * - Modern date/time APIs everywhere.
 * - Defensive programming and clearer code.
 */
public final class Utils {

    public static final String FILE_SEPARATOR = "/";
    public static final String TRACKING_ID = "trackingId";
    public static final String VERSION = "version";
    public static final String INPUT_ARTIFACT_URI = "inputArtifactUri";
    public static final String VER_V1 = "v1";
    public static final String VER_V2 = "v2";
    public static final int MAX_RECORDS = 10000;

    private static final Logger LOG = LogManager.getLogger(Utils.class);

    // Eager, thread-safe initialization of the S3 client (recommended for singletons in modern Java)
    private static final AmazonS3 S3_CLIENT = createS3client();

    private Utils() {} // Prevent instantiation

    /**
     * Generate tracking id using UUID.
     */
    public static String generateTrackingId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Returns the current date in "yyyy-MM-dd" format using Java 21's LocalDate.
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

    /**
     * Returns the current timestamp in "yyyy-MM-dd HH:mm:ss" format using LocalDateTime.
     */
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Returns the Kafka resource path using the file separator constant.
     */
    public static String getKafkaResourcePath() {
        return "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;
    }

    /**
     * Validates String fields in POJO; returns null for null/none/empty/"null" values.
     */
    public static String stringFieldValidator(String value) {
        if (value == null || value.isEmpty() ||
                value.equalsIgnoreCase("null") || value.equalsIgnoreCase("none")) {
            return null;
        }
        return value;
    }

    /**
     * Validates List fields in POJO; returns null if list is null or empty.
     */
    public static <T> List<T> listFieldValidator(List<T> value) {
        return (value == null || value.isEmpty()) ? null : value;
    }

    /**
     * Returns the OOSS inputArtifact URI.
     */
    public static String getNewInputArtifactUri(String trackingId) {
        return frameUri(trackingId, "input");
    }

    /**
     * Returns the OOSS outputArtifact URI (GZIP extension).
     */
    public static String getNewOutputArtifactUri(String trackingId) {
        return frameUriGzip(trackingId, "output");
    }

    /**
     * Constructs the URI for JSON file.
     */
    private static String frameUri(String trackingId, String context) {
        return context + FILE_SEPARATOR + getCurrentDate() + FILE_SEPARATOR + trackingId + ".json";
    }

    /**
     * Constructs the URI for GZIP file.
     */
    private static String frameUriGzip(String trackingId, String context) {
        return context + FILE_SEPARATOR + getCurrentDate() + FILE_SEPARATOR + trackingId + ".gz";
    }

    /**
     * Creates a new AmazonS3 client based on configuration values using modern AWS builder APIs.
     */
    private static AmazonS3 createS3client() {
        String serviceEndPoint = ConfigurationManager.get("OOSS_URL");
        String accessKey = ConfigurationManager.get("OOSS_ACCESS_KEY");
        String secretKey = ConfigurationManager.get("OOSS_SECRET_KEY");

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        // Modern Java AWS client builder pattern (no deprecated AmazonS3Client usage)
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(serviceEndPoint, "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .enablePathStyleAccess() // Recommended for custom endpoints/OOSS
                .build();

        LOG.info("OOSS - Connection successful");
        return amazonS3;
    }

    /**
     * Returns the singleton AmazonS3 client for IO operations.
     * Eager initialization ensures thread safety and avoids lazy-loading race conditions.
     */
    public static AmazonS3 getS3client() {
        return S3_CLIENT;
    }
}
