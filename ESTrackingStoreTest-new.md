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
import java.util.Map;

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

//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//        trackingRecord = new TrackingRecord();
//        trackingRecord.setStatus("IN-PROGRESS");
//        trackingRecord.setTrackingId("test");
//        trackingRecord.setCallerId("test");
//        responseString = "{ \"trackingId\": \"test\", \"status\": \"IN-PROGRESS\", \"callerId\": \"test\" }";
//    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        trackingRecord = new TrackingRecord(
                "test",           // trackingId
                "IN-PROGRESS",    // status
                "test",           // callerId
                "field4", "field5", "field6", "field7", "field8", "field9", "field10",
                "field11", "field12", "field13",
                1L, 2L, 3L,
                "field17", "field18",
                4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L,
                Map.of("key", 1),
                12, 13, 14, 15, 16, 17, 18
        );
        responseString = "{ \"trackingId\": \"test\", \"status\": \"IN-PROGRESS\", \"callerId\": \"test\" }";
    }
    


    
//    void getTrackingStatus_returnsExpectedStatus() throws IOException {
//        ESTrackingStore spyStore = Mockito.spy(trackingStore);
//        doReturn(trackingRecord).when(spyStore).getTrackingRecord("test");
//        var status = spyStore.getTrackingStatus("test");
//        assertThat(status.getStatus()).isEqualTo("IN-PROGRESS");
//        assertThat(status.getTrackingId()).isEqualTo("test");
//    }
    @Test
    void getTrackingStatus_returnsExpectedStatus() throws IOException {
        ESTrackingStore spyStore = Mockito.spy(trackingStore);
        doReturn(trackingRecord).when(spyStore).getTrackingRecord("test");
        var status = spyStore.getTrackingStatus("test");
        assertThat(status.status()).isEqualTo("IN-PROGRESS");
        assertThat(status.trackingId()).isEqualTo("test");
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

//        TrackingRecord result = trackingStore.getTrackingRecord("test");
//        assertThat(result.getTrackingId()).isEqualTo(trackingRecord.getTrackingId());
//        assertThat(result.getStatus()).isEqualTo(trackingRecord.getStatus());
//        assertThat(result.getCallerId()).isEqualTo(trackingRecord.getCallerId());

//        TrackingRecord result = trackingStore.getTrackingRecord("test");
        TrackingRecord result = trackingStore.getTrackingRecord("test").orElseThrow();
        assertThat(result.trackingId()).isEqualTo(trackingRecord.trackingId());
        assertThat(result.status()).isEqualTo(trackingRecord.status());
        assertThat(result.callerId()).isEqualTo(trackingRecord.callerId());
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
// Note: This test class uses JUnit 5 and Mockito for mocking dependencies.

