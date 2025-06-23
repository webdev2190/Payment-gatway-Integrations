package com.optum.pure.filestore.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optum.pure.common.Utils;
import com.optum.pure.model.requestobjects.v1.PostTokensV1;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ✅ JUnit 5 way of enabling Mockito
class OOSSFileStoreTest {

    @Mock private AmazonS3 mockClient;
    @Mock private S3Object s3Object;
    @Mock private ObjectMapper objectMapper;
    @Mock private TransferManager mockTransferManager;
    @Mock private HttpRequestBase httpRequestBase;

    @InjectMocks
    private OOSSFileStore oossFileStore;

    private PutObjectResult putResult;
    private PostTokensV1 postTokensV1;

    @BeforeEach
    void setup() {
        postTokensV1 = new PostTokensV1("token1", List.of("pure", "ohhl", "test"));
        putResult = new PutObjectResult();
        putResult.setVersionId("1");

        // Static mocking for Utils.getS3client
        Utils.setS3client(mockClient); // 🔄 You should create a static setter or use PowerMockito if required
    }

    @Test
    void checkIfObjectPresentSuccessTest() {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(true);
        assertTrue(oossFileStore.checkIfObjectPresent(mockClient, "key"));
    }

    @Test
    void checkIfObjectPresentFailTest() {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(false);
        assertFalse(oossFileStore.checkIfObjectPresent(mockClient, "key"));
    }

    @Test
    void deleteObjectSuccessTest() {
        doNothing().when(mockClient).deleteObject(anyString(), anyString());
        assertTrue(oossFileStore.deleteObject("test", "key"));
    }

    @Test
    void deleteObjectFailTest() {
        doThrow(new RuntimeException("test")).when(mockClient).deleteObject(anyString(), anyString());
        assertThrows(RuntimeException.class, () -> oossFileStore.deleteObject(null, null));
    }

    @Test
    void writeObjectSuccessTest() throws Exception {
        // Should not throw any exception
        oossFileStore.writeObject("test/test", postTokensV1, false);
        // You may add more verifications here if write logic has effects to validate
    }

    @Test
    void writeObjectFailTest() throws Exception {
        when(mockTransferManager.upload(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class)))
            .thenThrow(new RuntimeException("Upload failed"));

        Utils.setTransferManager(mockTransferManager); // 🔄 If transferManager is static or internal

        assertThrows(Exception.class, () -> oossFileStore.writeObject("test/test", postTokensV1, false));
    }

    @Test
    void readObjectSuccessTest() throws Exception {
        String mockJson = "{\"tokenType\": \"tokenType1\", \"deIdentifiedTokens\": [\"test-token\"]}";

        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(true);
        when(mockClient.getObject(anyString(), anyString())).thenReturn(s3Object);
        when(s3Object.getObjectContent())
            .thenReturn(new S3ObjectInputStream(IOUtils.toInputStream(mockJson, "UTF-8"), httpRequestBase));
        when(objectMapper.readValue(any(byte[].class), eq(PostTokensV1.class)))
            .thenReturn(new PostTokensV1());

        byte[] obj = oossFileStore.readObject("input/test.json");
        PostTokensV1 read = objectMapper.readValue(obj, PostTokensV1.class);

        assertEquals(PostTokensV1.class, read.getClass());
    }

    @Test
    void readObjectFailTest1() {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(false);
        assertThrows(FileNotFoundException.class, () -> oossFileStore.readObject("test"));
    }

    @Test
    void readObjectFailTest2() throws IOException {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(true);
        when(mockClient.getObject(anyString(), anyString())).thenThrow(new IOException("fail"));

        assertThrows(IOException.class, () -> oossFileStore.readObject("test"));
    }
}
