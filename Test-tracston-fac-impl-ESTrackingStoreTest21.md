package com.optum.pure.trackingstore.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optum.pure.model.requestobjects.common.TrackingRecord;
import com.optum.pure.trackingstore.TrackingStore;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ESTrackingStoreTest {

    // Mocks Elasticsearch client
    @Mock
    private RestHighLevelClient mockClient;

    // Mocks the response of a search query in Elasticsearch
    @Mock
    private SearchResponse mockSearchResponse;

    // Mocks the hits (documents) returned by the search
    @Mock
    private SearchHits mockHits;

    // Mocks a single document returned in hits
    @Mock
    private SearchHit mockHit;

    // Mocks the JSON mapper used to convert string to TrackingRecord
    @Mock
    private ObjectMapper objectMapper;

    // Automatically injects mocks into ESTrackingStore instance
    @InjectMocks
    private ESTrackingStore trackingStore;

    private TrackingRecord trackingRecord;

    // Sample JSON string representing the tracking record as stored in Elasticsearch
    private final String responseString = """
            {
              "trackingId": "test",
              "status": "IN-PROGRESS",
              "callerId": "test"
            }
            """;

    @BeforeEach
    void setup() {
        // Initialize Mockito annotations (mocks, injects)
        MockitoAnnotations.openMocks(this);

        // Create a sample TrackingRecord object for comparison
        trackingRecord = new TrackingRecord();
        trackingRecord.setTrackingId("test");
        trackingRecord.setStatus("IN-PROGRESS");
        trackingRecord.setCallerId("test");
    }

    @Test
    void getTrackingRecord_success() throws IOException {
        // Simulate a valid SearchHit array with 1 mock hit
        SearchHit[] hitsArray = {mockHit};

        // Mocking Elasticsearch behavior for successful search
        when(mockClient.search(any(SearchRequest.class), eq(RequestOptions.DEFAULT))).thenReturn(mockSearchResponse);
        when(mockSearchResponse.getHits()).thenReturn(mockHits);
        when(mockHits.getHits()).thenReturn(hitsArray);
        when(mockHits.getAt(0)).thenReturn(mockHit);
        when(mockHit.getSourceAsString()).thenReturn(responseString);
        when(objectMapper.readValue(responseString, TrackingRecord.class)).thenReturn(trackingRecord);

        // Call method and assert result is correct
        TrackingRecord result = trackingStore.getTrackingRecord("test");
        assertEquals(trackingRecord.getTrackingId(), result.getTrackingId());
        assertEquals(trackingRecord.getStatus(), result.getStatus());
        assertEquals(trackingRecord.getCallerId(), result.getCallerId());
    }

    @Test
    void getTrackingRecord_failsToSearch() throws IOException {
        // Simulate an IOException when Elasticsearch fails during search
        when(mockClient.search(any(SearchRequest.class), eq(RequestOptions.DEFAULT)))
                .thenThrow(new IOException("search failed"));

        // Assert that IOException is thrown with the correct message
        IOException exception = assertThrows(IOException.class, () ->
                trackingStore.getTrackingRecord("test"));
        assertEquals("search failed", exception.getMessage());
    }

    @Test
    void getTrackingRecord_failsToParse() throws IOException {
        SearchHit[] hitsArray = {mockHit};

        // Simulate valid search but failure while parsing the document JSON to TrackingRecord
        when(mockClient.search(any(SearchRequest.class), eq(RequestOptions.DEFAULT))).thenReturn(mockSearchResponse);
        when(mockSearchResponse.getHits()).thenReturn(mockHits);
        when(mockHits.getHits()).thenReturn(hitsArray);
        when(mockHits.getAt(0)).thenReturn(mockHit);
        when(mockHit.getSourceAsString()).thenReturn("invalid-json");
        when(objectMapper.readValue("invalid-json", TrackingRecord.class))
                .thenThrow(new IOException("parsing failed"));

        // Assert parse failure throws IOException
        IOException exception = assertThrows(IOException.class, () ->
                trackingStore.getTrackingRecord("test"));
        assertEquals("parsing failed", exception.getMessage());
    }

    @Test
    void insertTrackingRecord_success() throws IOException {
        // Mock Elasticsearch indexing success
        IndexResponse mockIndexResponse = mock(IndexResponse.class);
        when(mockClient.index(any(IndexRequest.class), eq(RequestOptions.DEFAULT)))
                .thenReturn(mockIndexResponse);

        // No exception should be thrown
        trackingStore.insertTrackingRecord(trackingRecord);

        // Verify that index method was called
        verify(mockClient).index(any(IndexRequest.class), eq(RequestOptions.DEFAULT));
    }

    @Test
    void insertTrackingRecord_fails() throws IOException {
        // Simulate failure while indexing a document
        when(mockClient.index(any(IndexRequest.class), eq(RequestOptions.DEFAULT)))
                .thenThrow(new IOException("index failed"));

        // Assert indexing failure throws IOException
        IOException exception = assertThrows(IOException.class, () ->
                trackingStore.insertTrackingRecord(trackingRecord));
        assertEquals("index failed", exception.getMessage());
    }
}

==============================================Java 21 New Code=================================================

package com.optum.pure.trackingstore.impl;

import com.optum.pure.model.requestobjects.common.TrackingRecord;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ESTrackingStoreTest {

    @Mock
    private RestHighLevelClient mockClient;

    @InjectMocks
    private ESTrackingStore trackingStore;

    @Captor
    private ArgumentCaptor<SearchRequest> searchRequestCaptor;

    private TrackingRecord trackingRecord;
    private String responseString;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        trackingRecord = new TrackingRecord();
        trackingRecord.setStatus("IN-PROGRESS");
        trackingRecord.setTrackingId("test");
        trackingRecord.setCallerId("test");
        responseString = "{ \"trackingId\": \"test\", \"status\": \"IN-PROGRESS\", \"callerId\": \"test\" }";
    }

    @Test
    void getTrackingStatus_returnsExpectedStatus() throws IOException {
        ESTrackingStore spyStore = Mockito.spy(trackingStore);
        doReturn(trackingRecord).when(spyStore).getTrackingRecord("test");
        var status = spyStore.getTrackingStatus("test");
        assertThat(status.getStatus()).isEqualTo("IN-PROGRESS");
        assertThat(status.getTrackingId()).isEqualTo("test");
    }

    @Test
    void getTrackingRecord_returnsTrackingRecordOnSuccess() throws IOException {
        SearchResponse mockSearchResponse = mock(SearchResponse.class);
        SearchHits mockHits = mock(SearchHits.class);
        SearchHit mockHit = mock(SearchHit.class);

        when(mockClient.search(any(SearchRequest.class), any(RequestOptions.class))).thenReturn(mockSearchResponse);
        when(mockSearchResponse.getHits()).thenReturn(mockHits);
        when(mockHits.getHits()).thenReturn(new SearchHit[] {mockHit});
        when(mockHits.getAt(anyInt())).thenReturn(mockHit);
        when(mockHit.getSourceAsString()).thenReturn(responseString);

        TrackingRecord result = trackingStore.getTrackingRecord("test");
        assertThat(result.getTrackingId()).isEqualTo(trackingRecord.getTrackingId());
        assertThat(result.getStatus()).isEqualTo(trackingRecord.getStatus());
        assertThat(result.getCallerId()).isEqualTo(trackingRecord.getCallerId());
    }

    @Test
    void getTrackingRecord_throwsIOExceptionWhenSearchFails() throws IOException {
        when(mockClient.search(any(SearchRequest.class), any(RequestOptions.class)))
                .thenThrow(new IOException("test"));
        assertThatThrownBy(() -> trackingStore.getTrackingRecord("test"))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("test");
    }

    @Test
    void getTrackingRecord_throwsIOExceptionOnParseFail() throws IOException {
        SearchResponse mockSearchResponse = mock(SearchResponse.class);
        SearchHits mockHits = mock(SearchHits.class);
        SearchHit mockHit = mock(SearchHit.class);

        when(mockClient.search(any(SearchRequest.class), any(RequestOptions.class))).thenReturn(mockSearchResponse);
        when(mockSearchResponse.getHits()).thenReturn(mockHits);
        when(mockHits.getHits()).thenReturn(new SearchHit[] {mockHit});
        when(mockHits.getAt(anyInt())).thenReturn(mockHit);
        // Provide an invalid JSON string to simulate parsing failure
        when(mockHit.getSourceAsString()).thenReturn("{ \"Invalid-Field\": \"test\" }");

        assertThatThrownBy(() -> trackingStore.getTrackingRecord("test"))
                .isInstanceOf(IOException.class);
    }

    @Test
    void insertTrackingRecord_successfulInsertDoesNotThrow() throws IOException {
        IndexResponse mockResponse = mock(IndexResponse.class);
        when(mockClient.index(any(IndexRequest.class), any(RequestOptions.class))).thenReturn(mockResponse);
        assertThatCode(() -> trackingStore.insertTrackingRecord(trackingRecord)).doesNotThrowAnyException();
    }

    @Test
    void insertTrackingRecord_throwsIOExceptionOnFailure() throws IOException {
        when(mockClient.index(any(IndexRequest.class), any(RequestOptions.class))).thenThrow(new IOException("test"));
        assertThatThrownBy(() -> trackingStore.insertTrackingRecord(trackingRecord))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("test");
    }
}

