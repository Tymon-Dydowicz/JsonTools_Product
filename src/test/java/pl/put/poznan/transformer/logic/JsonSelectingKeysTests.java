package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonSelectingKeysTests {

    private JsonSelectingKeys jsonSelectingKeys;

    @BeforeEach
    public void setup() {
        jsonSelectingKeys = new JsonSelectingKeys();
    }

    @Test
    public void testSelectKeys_Success() throws JsonProcessingException {
        // Arrange
        String json = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
        String[] keysToSelect = {"age", "city"};
        String expectedJson = "{\"age\":30,\"city\":\"New York\"}";

        // Act
        String result = jsonSelectingKeys.selectKeys(json, keysToSelect);

        // Assert
        Assertions.assertEquals(expectedJson, result);
    }

    @Test
    public void testSelectKeys_NullJson_ThrowsException() {
        // Arrange
        String[] keysToSelect = {"age", "city"};

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonSelectingKeys.selectKeys(null, keysToSelect);
        });
    }

    @Test
    public void testSelectKeys_NullKeysToSelect_ThrowsException() {
        // Arrange
        String json = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonSelectingKeys.selectKeys(json, null);
        });
    }

    @Test
    public void testSelectKeys_InvalidJsonFormat() {
        // Arrange
        String json = "{\"name\": \"John\", \"age\": 30, \"address\": {\"city\": \"New York\"}";
        String[] keysToSelect = {"age"};

        // Act and Assert
        Assertions.assertThrows(JsonProcessingException.class, () -> {
            jsonSelectingKeys.selectKeys(json, keysToSelect);
        });
    }

    @Test
    public void testSelectKeys_KeyNotPresent() throws JsonProcessingException {
        // Arrange
        String json = "{\"name\":\"John\",\"age\":30,\"address\":{\"city\":\"New York\"}}";
        String[] keysToSelect = {"email"};

        // Act
        String result = jsonSelectingKeys.selectKeys(json, keysToSelect);

        // Assert
        Assertions.assertEquals("{}", result);
    }
}
