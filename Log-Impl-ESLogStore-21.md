package com.optum.pure.logstore.impl;

import com.google.gson.Gson;
import com.optum.pure.common.ConfigurationManager;
import com.optum.pure.logstore.LogStore;
import com.optum.pure.model.requestobjects.common.LogRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;
import java.util.Objects;

/**
 * Java 21 modernized implementation class for Elasticsearch Log store.
 * - Uses constructor injection for required dependencies (final if possible).
 * - Logger and config usage unchanged (they are already modern).
 * - Best practice: mark fields final when possible for immutability.
 * - Uses Objects.requireNonNull for constructor parameters.
 * - Clean error logging.
 * - JavaDoc is maintained.
 */
public class ESLogStore implements LogStore {

    private static final Logger LOG = LogManager.getLogger(ESLogStore.class);

    // Use final for immutable fields (best practice)
    private static final String ES_LOG_STORE_INDEX = ConfigurationManager.get("ES_LOG_STORE_INDEX");
    private final RestHighLevelClient client;
    private final Gson gson = new Gson(); // Prefer creating Gson once

    /**
     * Constructor injects the Elasticsearch client.
     * Using 'final' enforces immutability and thread safety.
     */
    public ESLogStore(RestHighLevelClient client) {
        this.client = Objects.requireNonNull(client, "RestHighLevelClient must not be null");
    }

    /**
     * Inserts a LogRecord into the Elasticsearch index.
     * - Handles IOExceptions by logging and rethrowing.
     * - Serializes the log record to JSON using Gson.
     */
    @Override
    public void insertLogRecord(LogRecord logRecord) throws IOException {
        Objects.requireNonNull(logRecord, "LogRecord must not be null");
        try {
            var indexRequest = new IndexRequest(ES_LOG_STORE_INDEX)
                    .source(gson.toJson(logRecord), XContentType.JSON);
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOG.error("Failed to insert log record for trackingId {} in LogStore",
                    logRecord.trackingId());
            throw e;
        }
    }
}
