package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.JsonComparator;
import pl.put.poznan.transformer.logic.JsonDeletingKeys;

import java.util.Map;

/**
* The class is a REST controller which contains endpoints for finding differences between JSON files.
*/
@RestController
@RequestMapping("/diff")
public class JsonDiffEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(JsonDiffEndpoint.class);

    /**
     * Handles the JSON diff request by extracting JSON strings from "json1" and "json2",
     * logging the parameters, and calling performJsonDiff to compute the JSON diff.
     *
     * @param jsonMap The JSON object containing the "json1" and "json2" keys.
     * @return The JSON response containing the result of the JSON diff.
     */
    @RequestMapping(value = "/compare", method = {RequestMethod.POST, RequestMethod.GET}, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> handleJsonDiffRequest(@RequestBody Map<String, Object> jsonMap) {
        // Example request:
        // POST /compare
        // Content-Type: application/json
        // Request Body:
        // {
        //   "json1": "{\"name\": \"John\", \"age\": 30}",
        //   "json2": "{\"name\": \"Jane\", \"age\": 25}"
        // }

        // Check if the JSON structure is correct
        if (!jsonMap.containsKey("json1") || !jsonMap.containsKey("json2")) {
            return ResponseEntity.badRequest().body("Invalid JSON structure");
        }

        // Extract the JSON strings from "json1" and "json2"
        String text1 = jsonMap.get("json1").toString();
        String text2 = jsonMap.get("json2").toString();

        // Log the parameters
        logger.debug("Text 1: {}", text1);
        logger.debug("Text 2: {}", text2);

        return performJsonDiff(text1, text2);
    }




    /**
     * Finds the difference between two texts and returns it as a JSON response.
     *
     * @param text1 the first text
     * @param text2 the second text
     * @return the difference between the texts as a JSON response or BAD REQUEST error
     */
    public ResponseEntity<String> performJsonDiff(String text1, String text2) {
        // Create JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();


        try {
            JsonComparator jsonComparator = new JsonComparator();
            String difference = jsonComparator.compareJson(text1, text2);

            responseJson.put("difference", difference);

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            responseJson.put("error", "BAD REQUEST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }
}
