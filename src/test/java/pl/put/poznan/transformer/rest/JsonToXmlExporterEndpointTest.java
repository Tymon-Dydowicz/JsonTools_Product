package pl.put.poznan.transformer.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonToXmlExporterEndpointTest {

    private JsonToXmlExporterEndpoint endpointTest;

    @BeforeEach
    void setUp() {
        endpointTest = new JsonToXmlExporterEndpoint();
    }

    @Test
    void testHandleJsonToXmlExportRequest_Success() {
        // Prepare test data
        String jsonBody = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        // Invoke the method
        ResponseEntity<String> response = endpointTest.handleJsonToXmlExportRequest(jsonBody);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("<?xml version='1.0' encoding='UTF-8'?>\n" + "<root>\n" + "  <name>John</name>\n" + "  <age>30</age>\n" + "  <city>New York</city>\n" + "</root>\n", response.getBody());
    }

    @Test
    void testHandleJsonToXmlExportRequest_InvalidJson() {
        // Prepare test data
        String jsonBody = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\";";

        // Invoke the method and verify the exception
        ResponseEntity<String> response = endpointTest.handleJsonToXmlExportRequest(jsonBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
