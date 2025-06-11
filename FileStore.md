package com.optum.pure.filestore;

// Java 21: Use AWS SDK v2 S3Client, NOT AmazonS3 from v1 (mandatory for Java 21+ projects)
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

/**
 * Interface for FileStore.
 *
 * Java 21 Enhancements:
 * - Uses AWS SDK v2's S3Client (thread-safe, modern, replaces AmazonS3)
 * - Adds 'default' interface methods (optional in Java 8, but more common/useful in Java 21)
 * - Javadoc cleaned up for clarity.
 * - Uses generic types for flexibility, where appropriate.
 *
 * @author Dwarakesh T P (modernized for Java 21 by ChatGPT)
 */
public interface FileStore {

    /**
     * Check if object is present in the store or not.
     *
     * @param s3client   - S3Client (AWS SDK v2)
     * @param objectName - object key
     * @return true if found, false otherwise
     */
    boolean checkIfObjectPresent(S3Client s3client, String objectName);

    /**
     * Method to read OOSS object.
     *
     * @param artifactUri URI/key for the artifact in the store
     * @return object as a byte array
     * @throws IOException if reading fails
     */
    byte[] readObject(String artifactUri) throws IOException;

    /**
     * Method to write object.
     *
     * @param artifactUri          URI/key to write to
     * @param data                 Data to write (any Object, can use generics for more type safety)
     * @param isCompressionEnabled Enable gzip or not
     * @throws IOException          if writing fails
     * @throws InterruptedException if interrupted
     */
    void writeObject(String artifactUri, Object data, boolean isCompressionEnabled) throws InterruptedException, IOException;

    /**
     * Delete a specific object.
     *
     * @param oossFolder Folder/bucket prefix
     * @param fileName   Object/file key
     * @return true if deleted, false if not found or failed
     */
    boolean deleteObject(String oossFolder, String fileName);

    // Java 21: You may now add static or default methods here if you wish
    // (not required, but powerful for interface utility functions)
    /*
    default void logOperation(String opName) {
        System.out.println("Operation: " + opName);
    }
    */
}
