package com.optum.pure.filestore.impl;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
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
 * Java 21 upgraded implementation of OOSS Filestore
 */
public final class OOSSFileStore implements FileStore {

    private static final Logger LOG = LogManager.getLogger(OOSSFileStore.class);

    private final TransferManager transferManager = TransferManagerBuilder
            .standard()
            .withS3Client(Utils.getS3client())
            .build();

    private final String bucketName = ConfigurationManager.get("OOSS_BUCKET");

    public OOSSFileStore() {
    }

    // Check if object exists in S3 bucket
    public boolean checkIfObjectPresent(AmazonS3 s3Client, String objectName) {
        return s3Client.doesObjectExist(bucketName, objectName);
    }

    // Read and return object content as byte array
    public byte[] readObject(String objectKey) throws IOException {
        AmazonS3 s3Client = Utils.getS3client();
        LOG.debug("Reading Object with ObjectKey {} from OOSS", objectKey);

        if (!checkIfObjectPresent(s3Client, objectKey)) {
            LOG.error("ObjectKey {} not found in bucket {}", objectKey, bucketName);
            throw new FileNotFoundException(objectKey);
        }

        try (S3ObjectInputStream ipStream = s3Client.getObject(bucketName, objectKey).getObjectContent()) {
            LOG.info("Object - {} successfully retrieved from OOSS bucket - {}", objectKey, bucketName);
            return IOUtils.toByteArray(ipStream);
        } catch (Exception e) {
            LOG.error("Failed to read ObjectKey {} from OOSS bucket - {}", objectKey, bucketName);
            throw e;
        }
    }

    // Write JSON serialized data to S3
    private void writeObject(String objectKey, Object data) throws IOException, InterruptedException {
        byte[] bytesToWrite = new ObjectMapper().writeValueAsBytes(data);
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(bytesToWrite.length);

        transferManager.upload(bucketName, objectKey, new ByteArrayInputStream(bytesToWrite), metaData)
                .waitForCompletion();
        LOG.info("ObjectKey - {} successfully uploaded to OOSS bucket - {}", objectKey, bucketName);
    }

    // Write object to S3 with optional GZIP compression
    public void writeObject(String objectKey, Object data, boolean isCompressionEnabled) throws IOException, InterruptedException {
        if (isCompressionEnabled) {
            byte[] uncompressedBytes = new ObjectMapper().writeValueAsBytes(data);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 GZIPOutputStream gzipOut = new GZIPOutputStream(baos)) {

                gzipOut.write(uncompressedBytes);
                gzipOut.finish();

                byte[] compressedBytes = baos.toByteArray();
                ObjectMetadata metaData = new ObjectMetadata();
                metaData.setContentLength(compressedBytes.length);

                transferManager.upload(bucketName, objectKey, new ByteArrayInputStream(compressedBytes), metaData)
                        .waitForCompletion();

                LOG.info("Compressed ObjectKey - {} uploaded to bucket - {}", objectKey, bucketName);
            } catch (Exception e) {
                LOG.error("Compressed upload of ObjectKey - {} failed", objectKey);
                throw e;
            }
        } else {
            writeObject(objectKey, data);
        }
    }

    // Delete object from S3 bucket
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
