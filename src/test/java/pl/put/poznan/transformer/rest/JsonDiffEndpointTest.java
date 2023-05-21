package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.put.poznan.transformer.rest.JsonKeyOpsEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonDiffEndpointTest {

    private JsonDiffEndpoint endpointTest;

    @BeforeEach
    void prep() {
        endpointTest = new JsonDiffEndpoint();
    }

    @Test
    void testHandleJsonDiff_Success() throws JsonProcessingException {

        // Prepare test data
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("json1", "{\"name\": \"John\", \"age\": 30}");
        jsonMap.put("json2", "{\"name\": \"Jane\", \"age\": 25}");
        // Invoke the method
        ResponseEntity<String> response = endpointTest.handleJsonDiffRequest(jsonMap);
        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"difference\":\"Line 1:\\n  - {\\\"name\\\": \\\"John\\\", \\\"age\\\": 30}\\n  + {\\\"name\\\": \\\"Jane\\\", \\\"age\\\": 25}\\n\"}", response.getBody());
    }

    @Test
    void testHandleJsonDiff_InvalidKeys() {

        // Prepare test data
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("json1", "{\"name\": \"John\", \"age\": 30}");
        jsonMap.put("stuff", "{\"name\": \"Jane\", \"age\": 25}");

        // Invoke the method and verify the exception
        ResponseEntity<String> response = endpointTest.handleJsonDiffRequest(jsonMap);
        assertEquals("<400 BAD_REQUEST Bad Request,Invalid JSON structure,[]>", response.toString());
    }

}

