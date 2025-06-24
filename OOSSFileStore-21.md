package com.optum.pure.filestore.impl;

// Java 21: AWS SDK v2 imports (modern S3 client)
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optum.pure.common.ConfigurationManager;
import com.optum.pure.common.Utils;
import com.optum.pure.filestore.FileStore;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * Implementation class for OOSS Filestore.
 *
 * Java 21 Enhancements:
 * - Uses AWS SDK v2's S3Client (thread-safe, modern)
 * - Uses try-with-resources for better resource management
 * - Cleaner, safer, and more efficient
 * - Uses final where possible
 * - Uses var for local variable inference
 *
 * @author Dwarakesh T P (upgraded for Java 21 by ChatGPT)
 */
public class OOSSFileStore implements FileStore {

    private static final Logger LOG = LogManager.getLogger(OOSSFileStore.class);
    // Java 21: Always use S3Client (AWS SDK v2, NOT AmazonS3 v1)
    private final S3Client s3Client = Utils.getS3client();
    private final String bucketName = ConfigurationManager.get("OOSS_BUCKET");

    /**
     * Checks for the existence of object in OOSS, return true if present, else returns false.
     * @param s3Client
     * @param objectName
     * @return true if present, false otherwise
     */
    @Override
    public boolean checkIfObjectPresent(S3Client s3Client, String objectName) {
        // Java 21: HeadObjectRequest is used for checking object existence
        try {
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build());
            return true;
        } catch (NoSuchKeyException | S3Exception e) {
            LOG.warn("Object {} not found in bucket {}", objectName, bucketName);
            return false;
        }
    }

    /**
     * Reads the specific file from OOSS and returns the retrieved data as bytes.
     * @param objectKey - ArtifactUri
     * @return byte[]
     */
    @Override
    public byte[] readObject(String objectKey) throws IOException {
        LOG.debug("Reading Object with ObjectKey {} from OOSS", objectKey);

        boolean isObjectPresent = checkIfObjectPresent(s3Client, objectKey);
        if (!isObjectPresent) {
            LOG.error("ObjectKey {} not found in bucket {}", objectKey, bucketName);
            throw new FileNotFoundException(objectKey);
        }
        try {
            var getObjRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            // Java 21: try-with-resources for auto-closing stream
            try (var s3Object = s3Client.getObject(getObjRequest)) {
                var resultObject = IOUtils.toByteArray(s3Object);
                LOG.info("Object - {} successfully retrieved from OOSS bucket - {}", objectKey, bucketName);
                return resultObject;
            }
        } catch (Exception e) {
            LOG.error("Failed to read ObjectKey {} from OOSS bucket - {}", objectKey, bucketName, e);
            throw e;
        }
    }

    /**
     * Helper to write an object as JSON (uncompressed) to S3.
     * @param objectKey
     * @param data
     */
    private void writeObject(String objectKey, Object data) throws JsonProcessingException {
        try {
            byte[] bytesToWrite = new ObjectMapper().writeValueAsBytes(data);

            // Java 21: Use PutObjectRequest and S3Client.putObject with RequestBody
            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytesToWrite));
            LOG.info("ObjectKey - {} successfully uploaded to OOSS bucket - {}", objectKey, bucketName);
        } catch (Exception e) {
            LOG.error("Upload of ObjectKey - {} to OOSS bucket - {} Failed", objectKey, bucketName, e);
            throw e;
        }
    }

    /**
     * Writes object (optionally compressed) as JSON to S3.
     * @param objectKey
     * @param data
     * @param isCompressionEnabled
     */
    @Override
    public void writeObject(String objectKey, Object data, boolean isCompressionEnabled) throws IOException {
        if (isCompressionEnabled) {
            try {
                byte[] bytesToWrite = new ObjectMapper().writeValueAsBytes(data);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try (GZIPOutputStream outputStream = new GZIPOutputStream(byteArrayOutputStream)) {
                    outputStream.write(bytesToWrite);
                }
                bytesToWrite = byteArrayOutputStream.toByteArray();

                var putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .contentEncoding("gzip")
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytesToWrite));
                LOG.info("ObjectKey - {} successfully uploaded (gzipped) to OOSS bucket - {}", objectKey, bucketName);
            } catch (Exception e) {
                LOG.error("Upload of ObjectKey - {} to OOSS bucket - {} Failed", objectKey, bucketName, e);
                throw e;
            }
        } else {
            writeObject(objectKey, data);
        }
    }

    /**
     * Deletes the specific file from OOSS, if exists.
     * Returns true if the file is successfully deleted from OOSS else returns false.
     * @param oossFolder
     * @param objectName
     * @return
     */
    @Override
    public boolean deleteObject(String oossFolder, String objectName) {
        String objectKey = oossFolder + Utils.FILE_SEPARATOR + objectName;
        boolean isObjectDeleted = false;
        try {
            var deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            isObjectDeleted = true;
            LOG.debug("{} successfully deleted from OOSS - {}", objectName, oossFolder);
        } catch (Exception e) {
            LOG.error("OOSS Delete operation failed for object - {}, bucket - {}", objectName, bucketName, e);
            throw e;
        }
        return isObjectDeleted;
    }
}
=========================================Java 21 New Code================================================>

package com.optum.pure.filestore.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optum.pure.common.ConfigurationManager;
import com.optum.pure.common.Utils;
import com.optum.pure.filestore.FileStore;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

/**
 * Java 21-modernized implementation for OOSS Filestore.
 * - Final fields for thread safety and clarity.
 * - Try-with-resources for safe stream management.
 * - Central ObjectMapper instance.
 * - Constructor supports dependency injection.
 * - Uses best practice S3 client construction.
 */
public class OOSSFileStore implements FileStore {

    private static final Logger LOG = LogManager.getLogger(OOSSFileStore.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final TransferManager transferManager;
    private final String bucketName;

    /**
     * Default constructor using configured S3 client and bucket name.
     */
    public OOSSFileStore() {
        this(Utils.getS3client(), ConfigurationManager.get("OOSS_BUCKET"));
    }

    /**
     * Constructor for dependency injection (testability/flexibility).
     */
    public OOSSFileStore(AmazonS3 s3Client, String bucketName) {
        this.transferManager = TransferManagerBuilder.standard()
                .withS3Client(Objects.requireNonNull(s3Client, "S3 client must not be null"))
                .build();
        this.bucketName = Objects.requireNonNull(bucketName, "Bucket name must not be null");
    }

    @Override
    public boolean checkIfObjectPresent(AmazonS3 s3Client, String objectName) {
        Objects.requireNonNull(s3Client, "S3 client must not be null");
        Objects.requireNonNull(objectName, "Object name must not be null");
        return s3Client.doesObjectExist(bucketName, objectName);
    }

    @Override
    public byte[] readObject(String objectKey) throws IOException {
        Objects.requireNonNull(objectKey, "Object key must not be null");
        AmazonS3 s3Client = Utils.getS3client();
        LOG.debug("Reading Object with ObjectKey {} from OOSS", objectKey);

        if (!checkIfObjectPresent(s3Client, objectKey)) {
            LOG.error("ObjectKey {} not found in bucket {}", objectKey, bucketName);
            throw new FileNotFoundException(objectKey);
        }
        try {
            S3Object object = s3Client.getObject(bucketName, objectKey);
            LOG.info("Object - {} successfully retrieved from OOSS bucket - {}", objectKey, bucketName);
            try (S3ObjectInputStream ipStream = object.getObjectContent()) {
                byte[] resultObject = IOUtils.toByteArray(ipStream);
                LOG.debug("resultObject successfully extracted from S3InputStream, object key -> {}", objectKey);
                return resultObject;
            }
        } catch (Exception e) {
            LOG.error("Failed to read ObjectKey {} from OOSS bucket - {}", objectKey, bucketName, e);
            throw e;
        }
    }

    @Override
    public void writeObject(String objectKey, Object data, boolean isCompressionEnabled)
            throws IOException, InterruptedException {
        Objects.requireNonNull(objectKey, "Object key must not be null");
        Objects.requireNonNull(data, "Data must not be null");

        byte[] bytesToWrite = OBJECT_MAPPER.writeValueAsBytes(data);

        if (isCompressionEnabled) {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 GZIPOutputStream gzipStream = new GZIPOutputStream(byteArrayOutputStream)) {
                gzipStream.write(bytesToWrite);
                gzipStream.finish();
                bytesToWrite = byteArrayOutputStream.toByteArray();
            }
        }

        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(bytesToWrite.length);

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesToWrite)) {
            transferManager.upload(bucketName, objectKey, inputStream, metaData).waitForCompletion();
            LOG.info("ObjectKey - {} successfully uploaded to OOSS bucket - {}", objectKey, bucketName);
        } catch (Exception e) {
            LOG.error("Upload of ObjectKey - {} to OOSS bucket - {} Failed", objectKey, bucketName, e);
            throw e;
        }
    }

    @Override
    public boolean deleteObject(String oossFolder, String objectName) {
        AmazonS3 s3Client = Utils.getS3client();
        String objectKey = oossFolder + Utils.FILE_SEPARATOR + objectName;
        try {
            s3Client.deleteObject(bucketName, objectKey);
            LOG.debug("{} successfully deleted from OOSS - {}", objectName, oossFolder);
            return true;
        } catch (Exception e) {
            LOG.error("OOSS Delete operation failed for object - {}, bucket - {}", objectName, bucketName, e);
            throw e;
        }
    }
}
