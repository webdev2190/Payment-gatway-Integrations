package com.optum.pure.filestore;

import com.amazonaws.services.s3.AmazonS3;

import java.io.IOException;

/**
 * Interface for File store
 *
 * @author Dwarakesh T P
 */
public interface FileStore {
    /**
     * Check if object is present in the store or not.
     *
     * @param objectName - object key
     * @return - boolean value indicating whether object is found or not
     */
    boolean checkIfObjectPresent(AmazonS3 s3client, String objectName);

    /**
     * Method to read OOSS object
     *
     * @param artifactUri
     * @return
     * @throws IOException
     */
    byte[] readObject(String artifactUri) throws IOException;

    /**
     * Method to write object
     *
     * @param artifactUri
     * @param data
     * @param isCompressionEnabled
     * @throws IOException
     * @throws InterruptedException
     */
    void writeObject(String artifactUri, Object data, boolean isCompressionEnabled) throws InterruptedException, IOException;

    /**
     * Delete a specific object
     *
     * @param oossFolder
     * @param fileName
     * @return
     */
    boolean deleteObject(String oossFolder, String fileName);
}
