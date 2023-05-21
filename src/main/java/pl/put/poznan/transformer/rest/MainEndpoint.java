package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class MainEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(MainEndpoint.class);


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html")
    public ResponseEntity<String> get() {
        // log the request
        logger.debug("Called home");

        String responseBody = "<h1>Home</h1>"; // HTML content for the home page

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);

        return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.OK);
    }




}


