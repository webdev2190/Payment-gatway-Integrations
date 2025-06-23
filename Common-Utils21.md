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
