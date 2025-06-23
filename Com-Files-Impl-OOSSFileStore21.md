package com.optum.pure.filestore.impl;

// AWS S3 dependencies for file operations
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

// Jackson for JSON serialization
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// Project utilities and config
import com.optum.pure.common.ConfigurationManager;
import com.optum.pure.common.Utils;
import com.optum.pure.filestore.FileStore;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * Java 21 upgraded implementation of OOSS Filestore
 */
public final class OOSSFileStore implements FileStore {

    // Logger for debugging and tracking
    private static final Logger LOG = LogManager.getLogger(OOSSFileStore.class);

    // TransferManager handles large uploads/downloads via AWS
    private final TransferManager transferManager = TransferManagerBuilder
            .standard()
            .withS3Client(Utils.getS3client())
            .build();

    // Fetch bucket name from configuration
    private final String bucketName = ConfigurationManager.get("OOSS_BUCKET");

    public OOSSFileStore() {
        // Default constructor
    }

    /**
     * Checks if an object exists in the specified bucket
     */
    public boolean checkIfObjectPresent(AmazonS3 s3Client, String objectName) {
        return s3Client.doesObjectExist(bucketName, objectName);
    }

    /**
     * Reads a file from S3 and returns its byte content
     */
    public byte[] readObject(String objectKey) throws IOException {
        AmazonS3 s3Client = Utils.getS3client();
        LOG.debug("Reading Object with ObjectKey {} from OOSS", objectKey);

        // Check if the object exists
        if (!checkIfObjectPresent(s3Client, objectKey)) {
            LOG.error("ObjectKey {} not found in bucket {}", objectKey, bucketName);
            throw new FileNotFoundException(objectKey);
        }

        // Try-with-resources (Java 7+) — ✅ still best practice in Java 21
        try (S3ObjectInputStream ipStream = s3Client.getObject(bucketName, objectKey).getObjectContent()) {
            LOG.info("Object - {} successfully retrieved from OOSS bucket - {}", objectKey, bucketName);
            return IOUtils.toByteArray(ipStream); // Convert input stream to byte array
        } catch (Exception e) {
            LOG.error("Failed to read ObjectKey {} from OOSS bucket - {}", objectKey, bucketName);
            throw e;
        }
    }

    /**
     * Writes an object to S3 after converting it to JSON
     */
    private void writeObject(String objectKey, Object data) throws IOException, InterruptedException {
        // Convert POJO to JSON bytes
        byte[] bytesToWrite = new ObjectMapper().writeValueAsBytes(data);

        // Set metadata like file size
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(bytesToWrite.length);

        // Upload using TransferManager
        transferManager.upload(bucketName, objectKey, new ByteArrayInputStream(bytesToWrite), metaData)
                .waitForCompletion(); // Wait for async transfer to finish

        LOG.info("ObjectKey - {} successfully uploaded to OOSS bucket - {}", objectKey, bucketName);
    }

    /**
     * Upload object to S3 with optional GZIP compression
     */
    public void writeObject(String objectKey, Object data, boolean isCompressionEnabled) throws IOException, InterruptedException {
        if (isCompressionEnabled) {
            // Convert object to JSON byte array
            byte[] uncompressedBytes = new ObjectMapper().writeValueAsBytes(data);

            // Java 21 compatible try-with-resources for auto-closing
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 GZIPOutputStream gzipOut = new GZIPOutputStream(baos)) {

                gzipOut.write(uncompressedBytes); // Compress data
                gzipOut.finish(); // Ensure completion

                byte[] compressedBytes = baos.toByteArray(); // Final byte array

                // Create metadata with content length
                ObjectMetadata metaData = new ObjectMetadata();
                metaData.setContentLength(compressedBytes.length);

                // Upload compressed file to S3
                transferManager.upload(bucketName, objectKey, new ByteArrayInputStream(compressedBytes), metaData)
                        .waitForCompletion();

                LOG.info("Compressed ObjectKey - {} uploaded to bucket - {}", objectKey, bucketName);
            } catch (Exception e) {
                LOG.error("Compressed upload of ObjectKey - {} failed", objectKey);
                throw e;
            }
        } else {
            // Upload uncompressed JSON
            writeObject(objectKey, data);
        }
    }

    /**
     * Delete an object from the given folder in S3
     */
    public boolean deleteObject(String oossFolder, String objectName) {
        AmazonS3 s3Client = Utils.getS3client();
        String objectKey = oossFolder + Utils.FILE_SEPARATOR + objectName;

        try {
            s3Client.deleteObject(bucketName, objectKey);
            LOG.debug("{} successfully deleted from OOSS - {}", objectName, oossFolder);
            return true;
        } catch (Exception e) {
            LOG.error("OOSS Delete operation failed for object - {}, bucket - {}", objectName, bucketName);
            throw e;
        }
    }
}
