package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The class is a main REST controller which contains endpoints for the home page and all routes that are not defined.
 */
@RestController
@RequestMapping("/")
public class MainEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(MainEndpoint.class);

    /**
     * Handles the home page request and returns the home page as a HTML response.
     *
     * @return the home page as a HTML response
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html")
    public ResponseEntity<String> get() {
        // log the request
        logger.debug("Called home");

        String responseBody = "<h1>Home</h1>"; // HTML content for the home page

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

        return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.OK);
    }

    /**
     * Handles all routes that are not defined and returns a 404 error page as a HTML response.
     *
     * @return the 404 error page as a HTML response
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html", path = "{anyOtherPath}")
    public ResponseEntity<String> handleDefaultRoute() {
        logger.debug("Invalid route called");

        String responseBody = "<h1>Not found. Routing does not exist</h1>"; // HTML content for the default route

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

        return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.NOT_FOUND);
    }
}
