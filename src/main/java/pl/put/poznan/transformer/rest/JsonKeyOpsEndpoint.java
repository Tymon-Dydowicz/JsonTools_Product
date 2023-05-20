package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keyops")
public class JsonKeyOpsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(JsonKeyOpsEndpoint.class);

    @RequestMapping(value = "/select/{key}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> handleJsonKeySelectRequest(@PathVariable String key, @RequestBody String jsonBody) {
        // log the parameters
        logger.debug("Key: {}", key);
        logger.debug("JSON Body: {}", jsonBody);

        return performJsonKeySelect(key, jsonBody);
    }

    @RequestMapping(value = "/omit/{key}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> handleJsonKeyOmitRequest(@PathVariable String key, @RequestBody String jsonBody) {
        // log the parameters
        logger.debug("Key: {}", key);
        logger.debug("JSON Body: {}", jsonBody);

        return performJsonKeyOmit(key, jsonBody);
    }

    /**
     * Selects the specified key from the JSON and returns it as a JSON response.
     *
     * @param key      the key to select
     * @param jsonBody the JSON body
     * @return the selected key as a JSON response or BAD REQUEST error
     */
    public ResponseEntity<String> performJsonKeySelect(String key, String jsonBody) {
        // Create JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();

        try {

            String selectedNodes = "PLACEHOLDER FOR SELECTED NODES";

            responseJson.put(key, selectedNodes);

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            responseJson.put("error", "BAD REQUEST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }

    /**
     * Omits the specified key from the JSON and returns the modified JSON as a response.
     *
     * @param key      the key to omit
     * @param jsonBody the JSON body
     * @return the modified JSON as a JSON response or BAD REQUEST error
     */
    public ResponseEntity<String> performJsonKeyOmit(String key, String jsonBody) {
        // Create JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();

        try {
            String modifiedJson = "PLACEHOLDER MODIFIED JSON";

            responseJson.put("result", modifiedJson);

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            responseJson.put("error", "BAD REQUEST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }
}
