package com.optum.pure.logstore.impl;

import com.optum.pure.logstore.LogStore;
import com.optum.pure.model.requestobjects.common.LogRecord;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.io.stream.BytesStreamInput;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ESLogStoreTest {

    private RestHighLevelClient mockClient;
    private LogStore logStore;
    private LogRecord logRecord;

    @BeforeEach
    void setUp() {
        mockClient = mock(RestHighLevelClient.class); // ✅ Java 21 & Mockito-friendly
        logStore = new ESLogStore(mockClient);
        logRecord = new LogRecord();
        logRecord.setCallerId("test");
    }

    @Test
    void insertLogRecordTest() throws IOException {
        //  Stub IndexResponse using minimal valid StreamInput (mocking real parsing is unnecessary)
        IndexResponse dummyResponse = new IndexResponse(new BytesStreamInput(new byte[0]));
        when(mockClient.index(any(IndexRequest.class), eq(RequestOptions.DEFAULT))).thenReturn(dummyResponse);

        // Act
        assertDoesNotThrow(() -> logStore.insertLogRecord(logRecord));

        //  Verify the interaction happened as expected
        verify(mockClient, times(1)).index(any(IndexRequest.class), eq(RequestOptions.DEFAULT));
    }
}
