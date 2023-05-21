package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diff")
public class JsonDiffEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(JsonDiffEndpoint.class);

    @RequestMapping(value = "/compare", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> handleJsonDiffRequest(@RequestParam String text1, @RequestParam String text2) {
        // log the parameters
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
            // TODO: Implement the text difference finding logic
            String difference = "PLACEHOLDER FOR TEXT DIFFERENCE";

            responseJson.put("difference", difference);

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            responseJson.put("error", "BAD REQUEST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }
}
