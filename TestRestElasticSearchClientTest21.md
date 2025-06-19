package com.optum.pure.common;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for RestElasticSearchClient.java (Modernized for Java 21 with JUnit 5)
 *
 * @author Dwarakesh T P
 */

public class RestElasticSearchClientTest {

    @Test
    public void getClientTestAssertNotNull() {
        // Fetch the client using RestElasticsearchClient
        RestHighLevelClient client = RestElasticsearchClient.getClient();
        
        // Assert the client is not null using JUnit 5's Assertions
        Assertions.assertNotNull(client, "Elasticsearch client should not be null");
    }

    @Test
    public void getClientTestAssertEquals() {
        // Fetch two instances of RestHighLevelClient
        RestHighLevelClient client1 = RestElasticsearchClient.getClient();
        RestHighLevelClient client2 = RestElasticsearchClient.getClient();
        
        // Assert that the two instances are the same using JUnit 5's assertEquals
        Assertions.assertEquals(client1, client2, "Both clients should be the same instance");
    }
}
