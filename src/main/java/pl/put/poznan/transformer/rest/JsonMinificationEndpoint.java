package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.Minificator;

@RestController
@RequestMapping("/minification")
public class JsonMinificationEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(MainEndpoint.class);

    /**
     * Handles the minify request by logging the request body, and calling
     * callMinifyCreateResponse to perform JSON minification.
     *
     * @param json The JSON string to be minified.
     * @return The JSON response containing the minified JSON string.
     */
    @RequestMapping(value = "/minify", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> handleMinifyRequest(@RequestBody String json) {
        // Example usage:
        // POST /minify
        // Content-Type: application/json
        // Request Body:
        // {
        //   "name": "John",
        //   "age": 30
        // }
        // Log the request body
        logger.debug(json);

        return callMinifyCreateResponse(json);
    }


    /**
     * Handles the de-minify request by logging the request body, and calling
     * callDeMinifyCreateResponse to perform JSON de-minification.
     *
     * @param json The JSON string to be de-minified.
     * @return The JSON response containing the de-minified JSON string.
     */
    @RequestMapping(value = "/deminify", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> handleDeMinifyRequest(@RequestBody String json) {
        // Example usage:
        // POST /deminify
        // Content-Type: application/json
        // Request Body:
        // "{\"name\":\"John\",\"age\":30}"
        // Log the request body
        logger.debug(json);

        return callDeMinifyCreateResponse(json);
    }


    /**
     * Minify text and return it in a JSON-compatible form
     *
     * @param textToMinify text that is to be minified
     * @return minified text as a JSON response or BAD REQUEST error
     */
    public ResponseEntity<String> callMinifyCreateResponse(String textToMinify) {
        // Create JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();

        try {
            responseJson.put("textBefore", textToMinify);
            Minificator minificator = new Minificator();
            String minifiedText = minificator.minifyJson(textToMinify);

            responseJson.put("textAfter", minifiedText);

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            responseJson.put("error", "BAD REQUEST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }

    public ResponseEntity<String> callDeMinifyCreateResponse(String textToDeMinify) {
        // Create JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();

        try {
            // TODO Call the deminify function from the logic directory
            // For example:
            // DeminifyLogic deminifyLogic = new DeminifyLogic();
            // String deminifiedText = deminifyLogic.deminify(textToDeminify);
            String deminifiedText = "PLACEHOLDER FOR DEMINIFIED JSON TEXT";

            responseJson.put("text", deminifiedText);

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            responseJson.put("error", "BAD REQUEST: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson.toString());
        }
    }

}
