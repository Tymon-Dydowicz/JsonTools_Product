package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.JsonDeletingKeys;
import pl.put.poznan.transformer.logic.JsonSelectingKeys;

import java.util.Arrays;

@RestController
@RequestMapping("/keyops")
public class JsonKeyOpsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(JsonKeyOpsEndpoint.class);

    /**
     * Handles the JSON key selection request and returns the selected key as a JSON response.
     *
     * @param keys     the keys to select
     * @param jsonBody the JSON body
     * @return the selected key as a JSON response or BAD REQUEST error
     */
    @RequestMapping(value = "/select", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> handleJsonKeySelectRequest(@RequestParam("keys") String[] keys, @RequestBody String jsonBody) {
        // log the parameters
        logger.debug("Keys: {}", Arrays.toString(keys));
        logger.debug("JSON Body: {}", jsonBody);

        // Validate the keys parameter
        if (keys == null || keys.length == 0) {
            throw new IllegalArgumentException("At least one key must be provided.");
        }

        return performJsonKeySelect(keys, jsonBody);
    }

    /**
     * Handles the omit request of specified keys from the JSON and returns the modified JSON as a response.
     *
     * @param keys     the keys to omit
     * @param jsonBody the JSON body
     * @return the modified JSON as a JSON response or BAD REQUEST error
     */
    @RequestMapping(value = "/omit", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> handleJsonKeyOmitRequest(@RequestParam("keys") String[] keys, @RequestBody String jsonBody) {
        // log the parameters
        logger.debug("Keys: {}", Arrays.toString(keys));
        logger.debug("JSON Body: {}", jsonBody);

        // Validate the keys parameter
        if (keys == null || keys.length == 0) {
            throw new IllegalArgumentException("At least one key must be provided.");
        }

        return performJsonKeyOmit(keys, jsonBody);
    }

    /**
     * Selects the specified key from the JSON and returns it as a JSON response.
     *
     * @param keys     the keys to select
     * @param jsonBody the JSON body
     * @return the selected key as a JSON response or BAD REQUEST error
     */
    private ResponseEntity<String> performJsonKeySelect(String[] keys, String jsonBody) {
        // Create JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();

        try {

            JsonSelectingKeys selectingKeys = new JsonSelectingKeys();
            String modifiedJson = selectingKeys.selectKeys(jsonBody, keys);

            responseJson.put("selectedNodes", modifiedJson);

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            responseJson.put("error", "BAD REQUEST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }

    /**
     * Omits the specified key from the JSON and returns the modified JSON as a response.
     *
     * @param keys     the keys to omit
     * @param jsonBody the JSON body
     * @return the modified JSON as a JSON response or BAD REQUEST error
     */
    private ResponseEntity<String> performJsonKeyOmit(String[] keys, String jsonBody) {
        // Create JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();

        try {
            JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
            String modifiedJson = jsonDeletingKeys.deleteKeys(jsonBody, keys);

            responseJson.put("result", modifiedJson);

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            responseJson.put("error", "BAD REQUEST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }



}
