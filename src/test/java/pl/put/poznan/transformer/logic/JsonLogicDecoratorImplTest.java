package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonLogicDecoratorImplTest {
    private static JsonLogicDecorator jsonSelectingKeys;
    private static JsonLogicDecorator jsonDeletingKeys;

    @BeforeAll
    public static void setup() {
        jsonSelectingKeys = new JsonLogicDecoratorImpl(new JsonSelectingKeys());
        jsonDeletingKeys = new JsonLogicDecoratorImpl(new JsonDeletingKeys());
    }
    @Test
    public void testSelectKeys_NullJson_ThrowsException() {
        // Arrange
        String[] keysToSelect = {"age", "city"};

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonSelectingKeys.processJson(null, keysToSelect);
        });
    }

    @Test
    public void testSelectKeys_NullKeys_ThrowsException() {
        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonSelectingKeys.processJson("{}", null);
        });
    }

    @Test
    public void testSelectKeys_InvalidJsonFormat() {
        // Arrange
        String json = "{\"name\": \"John\", \"age\": 30, \"address\": {\"city\": \"New York\"}";
        String[] keysToSelect = {"age"};

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonSelectingKeys.processJson(json, keysToSelect);
        });
    }

    @Test
    public void testDeleteKeys_NullJson_ThrowsException() {
        // Arrange
        String[] keysToDelete = {"age", "city"};

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonDeletingKeys.processJson(null, keysToDelete);
        });
    }

    @Test
    public void testDeleteKeys_InvalidJsonFormat() {
        // Arrange
        String json = "{\"name\": \"John\", \"age\": 30, \"address\": {\"city\": \"New York\"}";
        String[] keysToDelete = {"age"};

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonDeletingKeys.processJson(json, keysToDelete);
        });
    }

    @Test
    public void testDeleteKeys_NullKeys_ThrowsException() {
        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonDeletingKeys.processJson("{}", null);
        });
    }
}
