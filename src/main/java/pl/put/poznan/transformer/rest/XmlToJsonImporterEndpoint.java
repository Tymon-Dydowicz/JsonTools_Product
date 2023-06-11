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
import pl.put.poznan.transformer.logic.XmlToJsonImporter;

/**
 * The class is a REST controller which contains an endpoint for importing XML to JSON.
 */
@RestController
@RequestMapping("/import")
public class XmlToJsonImporterEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(XmlToJsonImporterEndpoint.class);

    /**
     * Handles the XML to JSON import request and returns the JSON as a response.
     *
     * @param xmlBody the XML body
     * @return the JSON representation of the XML as a response or BAD REQUEST error
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> handleXmlToJsonImportRequest(@RequestBody String xmlBody) {
        // log the XML body
        logger.debug("XML Body: {}", xmlBody);

        try {
            XmlToJsonImporter importer = new XmlToJsonImporter();
            String jsonOutput = importer.importToJson(xmlBody);

            return ResponseEntity.ok(jsonOutput);
        } catch (Exception e) {
            ObjectNode responseJson = createErrorResponse("Failed to import XML to JSON: " + e.getMessage());
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
