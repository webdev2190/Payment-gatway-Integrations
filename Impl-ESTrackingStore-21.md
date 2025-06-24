package com.optum.pure.trackingstore.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.optum.pure.common.ConfigurationManager;
import com.optum.pure.model.requestobjects.common.TrackingRecord;
import com.optum.pure.model.entity.TrackingStatus;
import com.optum.pure.trackingstore.TrackingStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.xcontent.XContentFactory.jsonBuilder;

/**
 * This implementation of {@link TrackingStore} uses elasticsearch to store and fetch the tracking records.
 *
 * @author Nandu
 */
public class ESTrackingStore implements TrackingStore {

    private static final Logger LOG = LogManager.getLogger(ESTrackingStore.class);
    private static final String ES_TRACKING_STORE_INDEX = ConfigurationManager.get("ES_TRACKING_STORE_INDEX");
    private static final int RETRY_COUNT = 3;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestHighLevelClient client;

    // Constructor with Dependency Injection of RestHighLevelClient
    public ESTrackingStore(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * Fetches the tracking status for a given trackingId
     *
     * @param trackingId the tracking ID to search for
     * @return the tracking status
     * @throws IOException if an error occurs while fetching the status
     */
    @Override
    public TrackingStatus getTrackingStatus(String trackingId) throws IOException {
        // Use the getTrackingRecord method to get the status
        return new TrackingStatus(trackingId, getTrackingRecord(trackingId).getStatus());
    }

    /**
     * Retrieves the full tracking record for a given trackingId
     *
     * @param trackingId the tracking ID to search for
     * @return the tracking record
     * @throws IOException if an error occurs while fetching the record
     */
    @Override
    public TrackingRecord getTrackingRecord(String trackingId) throws IOException {
        var trackingRecord = new TrackingRecord(); // Use var for type inference
        SearchResponse response;

        // Execute the search query to fetch the tracking record
        try {
            response = client.search(getSearchRequest(ES_TRACKING_STORE_INDEX, getSearchByTrackingIdQuery(trackingId)),
                    RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("Failed to fetch tracking Record for trackingId {} from TrackingStore", trackingId);
            throw e;
        }

        // If the search results contain any hits, parse the first one into a TrackingRecord
        if (response.getHits().getHits().length > 0) {
            try {
                trackingRecord = objectMapper.readValue(response.getHits().getAt(0).getSourceAsString(),
                        TrackingRecord.class);
            } catch (IOException e) {
                LOG.error("Failed to parse tracking Record for trackingId {}", trackingId);
                throw e;
            }
        }
        return trackingRecord;
    }

    /**
     * Builds the query to search for a tracking record by trackingId
     *
     * @param trackingId the tracking ID to search for
     * @return a BoolQueryBuilder with the appropriate search criteria
     */
    private BoolQueryBuilder getSearchByTrackingIdQuery(String trackingId) {
        return QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("trackingId", trackingId));
    }

    /**
     * Creates a search request for Elasticsearch
     *
     * @param index the Elasticsearch index to search
     * @param query the query to use for the search
     * @return a SearchRequest for Elasticsearch
     */
    private SearchRequest getSearchRequest(String index, BoolQueryBuilder query) {
        return new SearchRequest(index).source(new SearchSourceBuilder().query(query).fetchSource(null));
    }

    /**
     * Inserts a tracking record into the Elasticsearch store
     *
     * @param trackingRecord the tracking record to insert
     * @throws IOException if an error occurs during the insertion
     */
    public void insertTrackingRecord(TrackingRecord trackingRecord) throws IOException {
        try {
            client.index(new IndexRequest(ES_TRACKING_STORE_INDEX)
                    .id(trackingRecord.getTrackingId())
                    .source(new Gson().toJson(trackingRecord), XContentType.JSON), RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("Failed to insert tracking Record for trackingId {} in TrackingStore", trackingRecord.getTrackingId());
            throw e;
        }
    }

    /**
     * Updates a tracking record with new field values
     *
     * @param trackingId the tracking ID to update
     * @param fields the list of fields to update
     * @param values the corresponding values for the fields
     * @throws IOException if an error occurs during the update
     */
    @Override
    public void updateRecord(String trackingId, List<String> fields, List<?> values) throws IOException {
        var updateRequest = new UpdateRequest();
        updateRequest.retryOnConflict(RETRY_COUNT);
        XContentBuilder doc;

        try {
            doc = jsonBuilder().startObject();
            for (int index = 0; index < fields.size(); index++) {
                doc.field(fields.get(index), values.get(index));
            }
            doc.endObject();
        } catch (IOException e) {
            LOG.error("Error while creating multi-update request for TrackingRecord for trackingId {}", trackingId);
            throw e;
        }

        updateRequest.index(ES_TRACKING_STORE_INDEX)
                .id(trackingId)
                .doc(doc);

        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
            LOG.debug("Update Record successful for trackingId-{}", trackingId);
        } catch (IOException e) {
            LOG.error("Error while updating trackingRecord for trackingId {}", trackingId);
            throw e;
        }
    }
}
//==========================================Future Proof Code==================================

package com.optum.pure.trackingstore.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.optum.pure.common.ConfigurationManager;
import com.optum.pure.model.entity.TrackingStatus;
import com.optum.pure.model.requestobjects.common.TrackingRecord;
import com.optum.pure.trackingstore.TrackingStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;
import java.util.List;

/**
 * Java 21 Upgraded implementation of TrackingStore using Elasticsearch
 */
public final class ESTrackingStore implements TrackingStore {  // Modern: use `final` to ensure immutability and safety

    private static final Logger LOG = LogManager.getLogger(ESTrackingStore.class);
    private static final String ES_TRACKING_STORE_INDEX = ConfigurationManager.get("ES_TRACKING_STORE_INDEX");
    private static final int RETRY_COUNT = 3;

    // Use final for immutable ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestHighLevelClient client;  // Made final to follow immutability best practice

    // Constructor with dependency injection
    public ESTrackingStore(RestHighLevelClient client) {
        this.client = client;
    }

    // Returns the tracking status using trackingId
    @Override
    public TrackingStatus getTrackingStatus(String trackingId) throws IOException {
        return new TrackingStatus(trackingId, getTrackingRecord(trackingId).getStatus());
    }

    // Returns the full tracking record by querying ES
    @Override
    public TrackingRecord getTrackingRecord(String trackingId) throws IOException {
        try {
            SearchRequest request = new SearchRequest(ES_TRACKING_STORE_INDEX)
                    .source(new SearchSourceBuilder().query(getSearchByTrackingIdQuery(trackingId)).fetchSource(null));
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            if (response.getHits().getHits().length > 0) {
                return objectMapper.readValue(response.getHits().getAt(0).getSourceAsString(), TrackingRecord.class);
            }
            return new TrackingRecord();  // Return empty if no result
        } catch (IOException e) {
            LOG.error("Error while fetching/parsing trackingId {} from store", trackingId, e);
            throw e;
        }
    }

    // Elasticsearch query for filtering by trackingId
    private BoolQueryBuilder getSearchByTrackingIdQuery(String trackingId) {
        return QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("trackingId", trackingId));
    }

    // Insert tracking record into Elasticsearch
    @Override
    public void insertTrackingRecord(TrackingRecord trackingRecord) throws IOException {
        try {
            IndexRequest request = new IndexRequest(ES_TRACKING_STORE_INDEX)
                    .id(trackingRecord.getTrackingId())
                    .source(new Gson().toJson(trackingRecord), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
            LOG.debug("Inserted tracking record for ID {}", trackingRecord.getTrackingId());
        } catch (IOException e) {
            LOG.error("Failed to insert trackingId {} in ES", trackingRecord.getTrackingId(), e);
            throw e;
        }
    }

    // Update multiple fields in tracking record
    @Override
    public void updateRecord(String trackingId, List<String> fields, List<?> values) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest()
                .index(ES_TRACKING_STORE_INDEX)
                .id(trackingId)
                .retryOnConflict(RETRY_COUNT);

        try {
            XContentBuilder builder = buildUpdateDocument(fields, values);
            updateRequest.doc(builder);

            client.update(updateRequest, RequestOptions.DEFAULT);
            LOG.debug("Updated tracking record for ID {}", trackingId);
        } catch (IOException e) {
            LOG.error("Update failed for trackingId {}", trackingId, e);
            throw e;
        }
    }

    // Extracted method to build JSON doc for update
    private XContentBuilder buildUpdateDocument(List<String> fields, List<?> values) throws IOException {
        try (XContentBuilder doc = org.elasticsearch.xcontent.XContentFactory.jsonBuilder()) {
            doc.startObject();
            for (int i = 0; i < fields.size(); i++) {
                doc.field(fields.get(i), values.get(i));
            }
            doc.endObject();
            return doc;
        }
    }
}

| Area                 | Change                                                         | Reason                                                     |
| -------------------- | -------------------------------------------------------------- | ---------------------------------------------------------- |
| `final` class        | `public final class ESTrackingStore`                           | Prevents subclassing; improves security and predictability |
| `final` fields       | `client`, `objectMapper`                                       | Better thread safety and design                            |
| `try-with-resources` | Used for `XContentBuilder` (update doc)                        | Ensures resource cleanup, avoids memory leaks              |
| Logging              | Unified `LOG.error` with exception `e`                         | More informative stack traces                              |
| Refactoring          | Extracted `buildUpdateDocument()` method                       | Improves readability and unit testing                      |
| Object creation      | Used inline initialization for `SearchRequest`, `IndexRequest` | Reduces boilerplate                                        |
==================================================================Java 21========================================>

package com.optum.pure.trackingstore.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optum.pure.common.ConfigurationManager;
import com.optum.pure.model.requestobjects.common.TrackingRecord;
import com.optum.pure.model.entity.TrackingStatus;
import com.optum.pure.trackingstore.TrackingStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.elasticsearch.xcontent.XContentFactory.jsonBuilder;

/**
 * Java 21 Modernized implementation of {@link TrackingStore} using Elasticsearch.
 */
public class ESTrackingStore implements TrackingStore {

    private static final Logger LOG = LogManager.getLogger(ESTrackingStore.class);

    // Make the index and retry count configurable and final
    private static final String ES_TRACKING_STORE_INDEX = Objects.requireNonNull(
            ConfigurationManager.get("ES_TRACKING_STORE_INDEX"), "ES_TRACKING_STORE_INDEX must not be null");
    private static final int RETRY_COUNT = 3;

    // Prefer final and explicit constructor-based dependency injection
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public ESTrackingStore(RestHighLevelClient client) {
        this(client, new ObjectMapper());
    }

    // Allow for custom ObjectMapper if needed (for testing/mocking)
    public ESTrackingStore(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = Objects.requireNonNull(client, "RestHighLevelClient must not be null");
        this.objectMapper = Objects.requireNonNull(objectMapper, "ObjectMapper must not be null");
    }

    @Override
    public TrackingStatus getTrackingStatus(String trackingId) throws IOException {
        var trackingRecord = getTrackingRecord(trackingId);
        return new TrackingStatus(trackingId, trackingRecord.status());
    }

    @Override
    public TrackingRecord getTrackingRecord(String trackingId) throws IOException {
        // Modernize variable declaration and error handling
        var searchRequest = getSearchRequest(ES_TRACKING_STORE_INDEX, getSearchByTrackingIdQuery(trackingId));
        SearchResponse response;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("Failed to fetch TrackingRecord for trackingId {} from TrackingStore", trackingId, e);
            throw e;
        }

        var hits = response.getHits().getHits();
        if (hits.length > 0) {
            try {
                // Use Jackson for deserialization (prefer over Gson if you already use Jackson)
                return objectMapper.readValue(hits[0].getSourceAsString(), TrackingRecord.class);
            } catch (IOException e) {
                LOG.error("Failed to parse TrackingRecord for trackingId {}", trackingId, e);
                throw e;
            }
        }
        // Return null or throw an exception if no record found (up to you)
        LOG.warn("No TrackingRecord found for trackingId {}", trackingId);
        return null;
    }

    private BoolQueryBuilder getSearchByTrackingIdQuery(String trackingId) {
        // Modern, fluent API
        return QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("trackingId", trackingId));
    }

    private SearchRequest getSearchRequest(String index, BoolQueryBuilder query) {
        var sourceBuilder = new SearchSourceBuilder().query(query).fetchSource(null); // null: all fields
        return new SearchRequest(index).source(sourceBuilder);
    }

    public void insertTrackingRecord(TrackingRecord trackingRecord) throws IOException {
        Objects.requireNonNull(trackingRecord, "trackingRecord must not be null");
        try {
            // Use Jackson for serialization to JSON
            var json = objectMapper.writeValueAsString(trackingRecord);
            var indexRequest = new IndexRequest(ES_TRACKING_STORE_INDEX)
                    .id(trackingRecord.trackingId())
                    .source(json, XContentType.JSON);

            client.index(indexRequest, RequestOptions.DEFAULT);
            LOG.info("Inserted TrackingRecord for trackingId {}", trackingRecord.trackingId());
        } catch (IOException e) {
            LOG.error("Failed to insert TrackingRecord for trackingId {} in TrackingStore",
                    trackingRecord.trackingId(), e);
            throw e;
        }
    }

    @Override
    public void updateRecord(String trackingId, List<String> fields, List<?> values) throws IOException {
        // Validate input lists
        if (fields == null || values == null || fields.size() != values.size()) {
            throw new IllegalArgumentException("Fields and values must be non-null and of equal size");
        }

        XContentBuilder doc;
        try {
            doc = jsonBuilder().startObject();
            for (int i = 0; i < fields.size(); i++) {
                doc.field(fields.get(i), values.get(i));
            }
            doc.endObject();
        } catch (IOException e) {
            LOG.error("Error while creating update Request for TrackingRecord with trackingId {}", trackingId, e);
            throw e;
        }

        var updateRequest = new UpdateRequest(ES_TRACKING_STORE_INDEX, trackingId)
                .doc(doc)
                .retryOnConflict(RETRY_COUNT);

        try {
            client.update(updateRequest, RequestOptions.DEFAULT);
            LOG.debug("Update Record successful for trackingId-{}", trackingId);
        } catch (IOException e) {
            LOG.error("Error while updating TrackingRecord for trackingId {}", trackingId, e);
            throw e;
        }
    }
}
