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
