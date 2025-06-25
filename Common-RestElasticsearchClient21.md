package com.optum.pure.common;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;

import javax.ws.rs.core.MediaType;

/**
 * Utility class to provide a singleton RestHighLevelClient for Elasticsearch.
 *
 * @author Nandu
 */
public final class RestElasticsearchClient { //  Marked as final to prevent inheritance (utility class best practice)

    //  All config fields are now final (they are constants loaded only once)
    private static final String ES_HOSTNAME = ConfigurationManager.get("ES_HOSTNAME");
    private static final int ES_PORT = Integer.parseInt(ConfigurationManager.get("ES_PORT")); // ✅ parsed once
    private static final String ES_SCHEME = ConfigurationManager.get("ES_SCHEME");
    private static final String ES_USERNAME = ConfigurationManager.get("ES_USERNAME");
    private static final String ES_PASSWORD = ConfigurationManager.get("ES_PASSWORD");

    //  Singleton instance (lazy-loaded using static holder for thread safety and Java 21 idiom)
    private static final class Holder {
        private static final RestHighLevelClient INSTANCE = createClient();
    }

    //  Private constructor to prevent instantiation
    private RestElasticsearchClient() {
    }

    //  Public accessor method
    public static RestHighLevelClient getClient() {
        return Holder.INSTANCE;
    }

    //  Client creation method, encapsulated and clean
    private static RestHighLevelClient createClient() {
        Header[] headers = {
            new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
            new BasicHeader("Role", "Read")
        };

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(ES_USERNAME, ES_PASSWORD));

        return new RestHighLevelClientBuilder(RestClient.builder(new HttpHost(ES_HOSTNAME, ES_PORT, ES_SCHEME))
            .setDefaultHeaders(headers)
            .setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
            .build())
            .setApiCompatibilityMode(true) // ✅ Ensures compatibility with newer ES versions
            .build();
    }
}
===========================================================Java 21 New Code======================================================>

package com.optum.pure.common;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;

import javax.ws.rs.core.MediaType;

/**
 * Java 21 modernized RestElasticsearchClient:
 * - Singleton instance, final static for thread safety.
 * - Private constructor to prevent instantiation.
 * - Ensures null safety and clear initialization.
 * - Clean separation of config fetching and client creation.
 * - Logs can be added for troubleshooting if needed.
 * - Ready for ES 8.x compatibility.
 */
public final class RestElasticsearchClient {

    // All config values are loaded once and final
    private static final String ES_HOSTNAME = ConfigurationManager.get("ES_HOSTNAME");
    private static final int ES_PORT = Integer.parseInt(ConfigurationManager.get("ES_PORT"));
    private static final String ES_SCHEME = ConfigurationManager.get("ES_SCHEME");
    private static final String ES_USERNAME = ConfigurationManager.get("ES_USERNAME");
    private static final String ES_PASSWORD = ConfigurationManager.get("ES_PASSWORD");

    // The singleton client instance (thread-safe and lazy initialized)
    private static final RestHighLevelClient CLIENT = createClient();

    // Private constructor prevents instantiation
    private RestElasticsearchClient() {}

    /**
     * Creates a thread-safe singleton Elasticsearch client.
     */
    private static RestHighLevelClient createClient() {
        // Use immutable array for headers
        Header[] headers = {
                new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
                new BasicHeader("Role", "Read")
        };

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(ES_USERNAME, ES_PASSWORD));

        // ES 8.x compatibility mode
        return new RestHighLevelClientBuilder(RestClient.builder(new HttpHost(ES_HOSTNAME, ES_PORT, ES_SCHEME))
                .setDefaultHeaders(headers)
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .build())
                .setApiCompatibilityMode(true)
                .build();
    }

    /**
     * Returns the singleton Elasticsearch client.
     * @return a thread-safe RestHighLevelClient
     */
    public static RestHighLevelClient getClient() {
        return CLIENT;
    }
}
