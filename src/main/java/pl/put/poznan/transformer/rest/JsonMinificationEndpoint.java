package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.TextTransformer;

import java.util.Arrays;

@RestController
@RequestMapping("/minification")
public class JsonMinify {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    @RequestMapping(value = "/minify/{text}", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ResponseEntity<String> handleMinifyRequest(@PathVariable String text) {
        // log the parameters
        logger.debug(text);

        return callMinifyCreateResponse(text);
    }


    @RequestMapping(value = "/deminify/{text}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> handleDeMinifyRequest(@PathVariable String text) {
        // log the parameters
        logger.debug(text);

        return callDeMinifyCreateResponse(text);
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
            // TODO Call the minify function from the logic directory
            // For example:
            // MinifyLogic minifyLogic = new MinifyLogic();
            // String minifiedText = minifyLogic.minify(text);
            String minifiedText = "PLACEHOLDER FOR MINIFIED JSON TEXT";

            responseJson.put("text", minifiedText);

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
