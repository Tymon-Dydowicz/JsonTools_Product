package pl.put.poznan.transformer.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonKeyOpsEndpointTest {

    private JsonKeyOpsEndpoint endpointTest;
    @BeforeEach
    void prep(){
        endpointTest = new JsonKeyOpsEndpoint();
    }

    @Test
    void testHandleJsonKeySelectRequest_Success() {
        // Prepare test data
        String[] keys = {"name", "age"};
        String jsonBody = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        // Invoke the method
        ResponseEntity<String> response = endpointTest.handleJsonKeySelectRequest(keys, jsonBody);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"selectedNodes\":\"{\\\"name\\\":\\\"John\\\",\\\"age\\\":30}\"}", response.getBody());
    }

    @Test
    void testHandleJsonKeySelectRequest_InvalidKeys() {
        // Prepare test data
        String[] keys = {};
        String jsonBody = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        // Invoke the method and verify the exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> endpointTest.handleJsonKeySelectRequest(keys, jsonBody));
        assertEquals("At least one key must be provided.", exception.getMessage());
    }

    @Test
    void testHandleJsonKeyOmitRequest_Success() {
        // Prepare test data
        String[] keys = {"city"};
        String jsonBody = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        // Invoke the method
        ResponseEntity<String> response = endpointTest.handleJsonKeyOmitRequest(keys, jsonBody);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"result\":\"{\\\"name\\\":\\\"John\\\",\\\"age\\\":30}\"}", response.getBody());
    }

    @Test
    void testHandleJsonKeyOmitRequest_InvalidKeys() {
        // Prepare test data
        String[] keys = {};
        String jsonBody = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        // Invoke the method and verify the exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> endpointTest.handleJsonKeyOmitRequest(keys, jsonBody));
        assertEquals("At least one key must be provided.", exception.getMessage());
    }
}
