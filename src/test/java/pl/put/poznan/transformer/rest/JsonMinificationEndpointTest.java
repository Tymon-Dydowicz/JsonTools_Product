package pl.put.poznan.transformer.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonMinificationEndpointTest {

    @Test
    void handleMinifyRequest_ValidJson_ReturnsOk() {
        // Arrange
        String json = "{\"name\":\"John\",\"age\":30}";
        JsonMinificationEndpoint controller = new JsonMinificationEndpoint();

        // Act
        ResponseEntity<String> response = controller.handleMinifyRequest(json);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Additional assertions if needed
    }

    @Test
    void handleMinifyRequest_InvalidJson_ReturnsBadRequest() {
        // Arrange
        String json = "invalid json";
        JsonMinificationEndpoint controller = new JsonMinificationEndpoint();

        // Act
        ResponseEntity<String> response = controller.handleMinifyRequest(json);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Additional assertions if needed
    }

}
