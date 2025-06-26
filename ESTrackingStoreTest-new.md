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

==============================New Test==========================================>

package com.optum.pure.trackingstore.impl;

import com.optum.pure.model.requestobjects.common.TrackingRecord;
import com.optum.pure.model.entity.TrackingStatus;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ESTrackingStoreTest {

    @Mock
    private RestHighLevelClient mockClient;
    @Mock
    private Logger mockLogger;
    @InjectMocks
    private ESTrackingStore trackingStore;
    @Captor
    private ArgumentCaptor<SearchRequest> searchRequestCaptor;
    @Captor
    private ArgumentCaptor<IndexRequest> indexRequestCaptor;
    @Captor
    private ArgumentCaptor<UpdateRequest> updateRequestCaptor;

    private static final String TEST_TRACKING_ID = "test123";
    private static final String TEST_CALLER_ID = "caller123";
    private static final String TEST_STATUS = "IN-PROGRESS";
    private static final String VALID_RESPONSE = """
        {
          "trackingId": "%s",
          "status": "%s",
          "callerId": "%s"
        }
        """.formatted(TEST_TRACKING_ID, TEST_STATUS, TEST_CALLER_ID);

    @BeforeEach
    void setup() throws Exception {
        // Initialize test data
        var trackingRecord = new TrackingRecord();
        trackingRecord.setStatus(TEST_STATUS);
        trackingRecord.setTrackingId(TEST_TRACKING_ID);
        trackingRecord.setCallerId(TEST_CALLER_ID);

        // Inject mock logger via reflection
        var logField = ESTrackingStore.class.getDeclaredField("LOG");
        logField.setAccessible(true);
        logField.set(null, mockLogger);
    }

    @Test
    void constructor_initializesCorrectly() {
        assertThat(trackingStore)
            .isNotNull()
            .extracting("client")
            .isEqualTo(mockClient);
    }

    // getTrackingStatus tests
    @Test
    void getTrackingStatus_returnsExpectedStatus() throws IOException {
        setupSuccessfulSearchResponse(VALID_RESPONSE);

        var result = trackingStore.getTrackingStatus(TEST_TRACKING_ID);

        assertThat(result)
            .extracting(TrackingStatus::getTrackingId, TrackingStatus::getStatus)
            .containsExactly(TEST_TRACKING_ID, TEST_STATUS);
    }

    @Test
    void getTrackingStatus_propagatesIOException() throws IOException {
        when(mockClient.search(any(SearchRequest.class), any(RequestOptions.class)))
            .thenThrow(new IOException("search failed"));

        assertThatThrownBy(() -> trackingStore.getTrackingStatus(TEST_TRACKING_ID))
            .isInstanceOf(IOException.class)
            .hasMessageContaining("search failed");

        verify(mockLogger).error("Failed to fetch tracking Record for trackingId {} from TrackingStore", TEST_TRACKING_ID);
    }

    // getTrackingRecord tests
    @Test
    void getTrackingRecord_returnsValidRecord() throws IOException {
        setupSuccessfulSearchResponse(VALID_RESPONSE);

        var result = trackingStore.getTrackingRecord(TEST_TRACKING_ID);

        assertThat(result)
            .extracting(TrackingRecord::getTrackingId, TrackingRecord::getStatus, TrackingRecord::getCallerId)
            .containsExactly(TEST_TRACKING_ID, TEST_STATUS, TEST_CALLER_ID);
    }

    @Test
    void getTrackingRecord_returnsEmptyRecordWhenNotFound() throws IOException {
        var mockSearchResponse = mock(SearchResponse.class);
        var mockHits = mock(SearchHits.class);

        when(mockClient.search(any(SearchRequest.class), any(RequestOptions.class)))
            .thenReturn(mockSearchResponse);
        when(mockSearchResponse.getHits()).thenReturn(mockHits);
        when(mockHits.getHits()).thenReturn(new SearchHit[0]);

        var result = trackingStore.getTrackingRecord("notfound");

        assertThat(result.getTrackingId()).isNull();
    }

    @Test
    void getTrackingRecord_throwsOnInvalidJson() throws IOException {
        setupSuccessfulSearchResponse("{ invalid json }");

        assertThatThrownBy(() -> trackingStore.getTrackingRecord(TEST_TRACKING_ID))
            .isInstanceOf(IOException.class);

        verify(mockLogger).error("Failed to parse tracking Record for trackingId {}", TEST_TRACKING_ID);
    }

    // insertTrackingRecord tests
    @Test
    void insertTrackingRecord_success() throws IOException {
        when(mockClient.index(any(IndexRequest.class), any(RequestOptions.class)))
            .thenReturn(mock(IndexResponse.class));

        var record = new TrackingRecord();
        record.setTrackingId(TEST_TRACKING_ID);

        assertThatCode(() -> trackingStore.insertTrackingRecord(record))
            .doesNotThrowAnyException();

        verify(mockClient).index(indexRequestCaptor.capture(), any(RequestOptions.class));
        assertThat(indexRequestCaptor.getValue().id()).isEqualTo(TEST_TRACKING_ID);
    }

    // updateRecord tests
    @Test
    void updateRecord_success() throws IOException {
        when(mockClient.update(any(UpdateRequest.class), any(RequestOptions.class)))
            .thenReturn(mock(UpdateResponse.class));

        var fields = List.of("status", "callerId");
        var values = List.<Object>of("COMPLETED", "newCaller");

        assertThatCode(() -> trackingStore.updateRecord(TEST_TRACKING_ID, fields, values))
            .doesNotThrowAnyException();

        verify(mockClient).update(updateRequestCaptor.capture(), any(RequestOptions.class));
        var request = updateRequestCaptor.getValue();
        assertThat(request)
            .extracting(UpdateRequest::id, UpdateRequest::retryOnConflict)
            .containsExactly(TEST_TRACKING_ID, 3);
    }

    @Test
    void updateRecord_handlesFieldValueMismatch() {
        var fields = List.of("status");
        var values = List.<Object>of(); // Empty list

        assertThatThrownBy(() -> trackingStore.updateRecord(TEST_TRACKING_ID, fields, values))
            .isInstanceOf(IndexOutOfBoundsException.class);
    }

    // Helper methods
    private void setupSuccessfulSearchResponse(String responseJson) throws IOException {
        var mockSearchResponse = mock(SearchResponse.class);
        var mockHits = mock(SearchHits.class);
        var mockHit = mock(SearchHit.class);

        when(mockClient.search(any(SearchRequest.class), any(RequestOptions.class)))
            .thenReturn(mockSearchResponse);
        when(mockSearchResponse.getHits()).thenReturn(mockHits);
        when(mockHits.getHits()).thenReturn(new SearchHit[]{mockHit});
        when(mockHit.getSourceAsString()).thenReturn(responseJson);
    }
}
// Note: This test class uses JUnit 5 and Mockito for mocking dependencies.

