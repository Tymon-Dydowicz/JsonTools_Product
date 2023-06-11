package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.logic.JsonDeletingKeys;

public class JsonDeletingKeysTests {

    private JsonDeletingKeys jsonDeletingKeys;

    @BeforeEach
    public void setup() {
        jsonDeletingKeys = new JsonDeletingKeys();
    }

    @Test
    public void testDeleteKeys_Success() throws JsonProcessingException {
        // Arrange
        JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
        String json = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
        String[] keysToRemove = {"age", "city"};
        String expectedJson = "{\"name\":\"John\"}";

        // Act
        String result = jsonDeletingKeys.deleteKeys(json, keysToRemove);

        // Assert
        Assertions.assertEquals(expectedJson, result);
    }

    @Test
    public void testDeleteKeys_Success_EmptyJson() throws JsonProcessingException {
        // Arrange
        JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
        String json = "{}";
        String[] keysToRemove = {};
        String expectedJson = "{}";

        // Act
        String result = jsonDeletingKeys.deleteKeys(json, keysToRemove);

        // Assert
        Assertions.assertEquals(expectedJson, result);
    }

    @Test
    public void testDeleteKeys_NullJson_ThrowsException() {
        // Arrange
        JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
        String[] keysToRemove = {"age", "city"};

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonDeletingKeys.deleteKeys(null, keysToRemove);
        });
    }

    @Test
    public void testDeleteKeys_InvalidJsonFormat() {
        // Arrange
        JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
        String json = "{\"name\": \"John\", \"age\": 30, \"address\": {\"city\": \"New York\"}";
        String[] keysToRemove = {"age"};

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonDeletingKeys.deleteKeys(json, keysToRemove);
        });
    }


    @Test
    public void testDeleteKeys_KeyNotPresent() throws JsonProcessingException {
        // Arrange
        JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
        String json = "{\"name\":\"John\",\"age\":30,\"address\":{\"city\":\"New York\"}}";
        String[] keysToRemove = {"email"};

        // Act
        String result = jsonDeletingKeys.deleteKeys(json, keysToRemove);

        // Assert
        Assertions.assertEquals(json, result);
    }


}
