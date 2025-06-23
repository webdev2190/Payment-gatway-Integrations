package com.optum.pure.filestore;

import com.amazonaws.services.s3.AmazonS3;

import java.io.IOException;

/**
 * Java 21 upgraded interface for FileStore abstraction
 *
 * @author Dwarakesh T P
 */
public sealed interface FileStore permits com.optum.pure.filestore.impl.OOSSFileStore {
    // Java 21 enhancement: sealed interface restricts which classes can implement this interface
    // This ensures only authorized implementation(s) like OOSSFileStore can implement FileStore

    /**
     * Checks if an object is present in the S3 bucket.
     *
     * @param s3client    the Amazon S3 client
     * @param objectName  the key name of the object
     * @return true if the object exists, false otherwise
     */
    boolean checkIfObjectPresent(AmazonS3 s3client, String objectName);

    /**
     * Reads an object from the given artifact URI (i.e., S3 key).
     *
     * @param artifactUri the S3 object key
     * @return the file content in byte array
     * @throws IOException if file read fails
     */
    byte[] readObject(String artifactUri) throws IOException;

    /**
     * Writes an object to S3 with optional compression.
     *
     * @param artifactUri          the S3 object key
     * @param data                 the object to be serialized and stored
     * @param isCompressionEnabled whether GZIP compression is enabled
     * @throws IOException          if upload fails
     * @throws InterruptedException if thread interrupted during upload
     */
    void writeObject(String artifactUri, Object data, boolean isCompressionEnabled)
            throws IOException, InterruptedException;

    /**
     * Deletes a file from the specified OOSS folder.
     *
     * @param oossFolder the folder in S3
     * @param fileName   the file to delete
     * @return true if deleted, false otherwise
     */
    boolean deleteObject(String oossFolder, String fileName);
}
