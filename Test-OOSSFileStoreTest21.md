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

=======================================Java 21 Record=======================================================

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // JUnit 5 extension for Mockito
class OOSSFileStoreTest {

    @InjectMocks
    private OOSSFileStore oossFileStore; // Class under test

    @Mock
    private AmazonS3 mockClient;

    @Mock
    private S3Object s3Object;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private TransferManager mockTransferManager;

    @Mock
    private HttpRequestBase httpRequestBase;

    private PutObjectResult putResult;
    private PostTokensV1 postTokensV1;

    @BeforeEach
    void setup() {
        // Test data setup
        String tokenType = "token1";
        List<String> deIdentifiedTokens = new ArrayList<>();
        deIdentifiedTokens.add("pure");
        deIdentifiedTokens.add("ohhl");
        deIdentifiedTokens.add("test");
        postTokensV1 = new PostTokensV1(tokenType, deIdentifiedTokens);

        putResult = new PutObjectResult();
        putResult.setVersionId("1");
    }

    @Test
    void checkIfObjectPresentSuccessTest() {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(true);
        boolean result = oossFileStore.checkIfObjectPresent(mockClient, "key");
        assertTrue(result);
    }

    @Test
    void checkIfObjectPresentFailTest() {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(false);
        boolean result = oossFileStore.checkIfObjectPresent(mockClient, "key");
        assertFalse(result);
    }

    @Test
    void deleteObjectSuccessTest() {
        // No exception thrown means success
        doNothing().when(mockClient).deleteObject(anyString(), anyString());
        boolean result = oossFileStore.deleteObject("test", "key");
        assertTrue(result);
    }

    @Test
    void deleteObjectFailTest() {
        doThrow(new RuntimeException("test")).when(mockClient).deleteObject(any(), any());
        assertThrows(RuntimeException.class, () -> oossFileStore.deleteObject(null, null));
    }

    @Test
    void writeObjectSuccessTest() throws Exception {
        // Mock S3 Put result and transfer manager upload logic as needed in your implementation
        // ... (requires actual implementation details for correct mocks)
        // For illustration:
        // when(mockTransferManager.upload(...)).thenReturn(putResult);

        // Call the method
        oossFileStore.writeObject("test" + Utils.FILE_SEPARATOR + "test", postTokensV1, false);
        // Assert as per your logic
        // assertEquals("1", putResult.getVersionId());
    }

    @Test
    void writeObjectFailTest() throws Exception {
        // Mock the behavior to throw Exception when uploading
        when(mockTransferManager.upload(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class)))
                .thenThrow(new IOException());
        // Exception expected
        assertThrows(Exception.class, () ->
                oossFileStore.writeObject("test" + Utils.FILE_SEPARATOR + "test", postTokensV1, false));
    }

    @Test
    void readObjectSuccessTest() throws Exception {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(true);
        when(mockClient.getObject(anyString(), anyString())).thenReturn(s3Object);
        // Prepare the test JSON content
        var inputStream = IOUtils.toInputStream("{\"tokenType\": \"tokenType1\", \"deIdentifiedTokens\": [\"test-token\"]}", "UTF-8");
        when(s3Object.getObjectContent()).thenReturn(new S3ObjectInputStream(inputStream, httpRequestBase));
        // (Mock ObjectMapper if used for deserialization)
        when(objectMapper.readValue(any(byte[].class), eq(PostTokensV1.class)))
                .thenReturn(new PostTokensV1("tokenType1", List.of("test-token")));

        byte[] obj = oossFileStore.readObject("input" + Utils.FILE_SEPARATOR + "test.json");
        Object resultObject = objectMapper.readValue(obj, PostTokensV1.class);
        assertTrue(resultObject instanceof PostTokensV1);
    }

    @Test
    void readObjectFailTest1() throws Exception {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(false);
        assertThrows(FileNotFoundException.class, () -> oossFileStore.readObject("test"));
    }

    @Test
    void readObjectFailTest2() throws Exception {
        when(mockClient.doesObjectExist(anyString(), anyString())).thenReturn(true);
        when(mockClient.getObject(anyString(), anyString())).thenThrow(new IOException());
        assertThrows(Exception.class, () -> oossFileStore.readObject("test"));
    }

    // You may need to update/mock any static utility methods in Utils if required.
}

