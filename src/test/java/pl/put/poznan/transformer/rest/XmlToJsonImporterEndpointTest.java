package pl.put.poznan.transformer.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlToJsonImporterEndpointTest {

    private XmlToJsonImporterEndpoint endpointTest;

    @BeforeEach
    void setUp() {
        endpointTest = new XmlToJsonImporterEndpoint();
    }

    @Test
    void testHandleXmlToJsonImportRequest_Success() {
        // Prepare test data
        String xmlBody = "<?xml version='1.0' encoding='UTF-8'?>\n" + "<root>\n" + "  <name>John</name>\n" + "  <age>30</age>\n" + "  <city>New York</city>\n" + "</root>\n";

        // Invoke the method
        ResponseEntity<String> response = endpointTest.handleXmlToJsonImportRequest(xmlBody);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"name\":\"John\",\"age\":\"30\",\"city\":\"New York\"}", response.getBody());
    }

    @Test
    void testHandleXmlToJsonImportRequest_InvalidXml() {
        // Prepare test data
        String xmlBody = "<?xml version='1.0' encoding='UTF-8'?>\n" + "<root>\n" + "  <name>John</name>\n" + "  <age>30</age>\n" + "  <city>New York</city>\n";

        // Invoke the method and verify the exception
        ResponseEntity<String> response = endpointTest.handleXmlToJsonImportRequest(xmlBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
