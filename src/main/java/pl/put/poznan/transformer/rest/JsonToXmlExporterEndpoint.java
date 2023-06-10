package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.logic.JsonToXmlExporter;

import java.io.IOException;

/**
 * The class is a REST controller which contains an endpoint for exporting JSON to XML.
 */
@RestController
@RequestMapping("/export")
public class JsonToXmlExporterEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(JsonToXmlExporterEndpoint.class);

    /**
     * Handles the JSON to XML export request and returns the XML as a response.
     *
     * @param jsonBody the JSON body
     * @return the XML representation of the JSON as a response or BAD REQUEST error
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/xml")
    public ResponseEntity<String> handleJsonToXmlExportRequest(@RequestBody String jsonBody) {
        // log the JSON body
        logger.debug("JSON Body: {}", jsonBody);

        try {
            JsonToXmlExporter exporter = new JsonToXmlExporter();
            String xmlOutput = exporter.exportToXml(jsonBody);

            return ResponseEntity.ok(xmlOutput);
        } catch (IOException e) {
            ObjectNode responseJson = createErrorResponse("Failed to export JSON to XML: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }

    /**
     * Creates a JSON error response with the specified error message.
     *
     * @param errorMessage the error message
     * @return the JSON error response as an ObjectNode
     */
    private ObjectNode createErrorResponse(String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();
        responseJson.put("error", errorMessage);
        return responseJson;
    }
}
