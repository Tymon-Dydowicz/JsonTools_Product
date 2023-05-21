package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.logic.JsonComparator;

public class JsonComparatorTests {

    private JsonComparator jsonComparator;

    @BeforeEach
    public void setup() {
        jsonComparator = new JsonComparator();
    }

    @Test
    public void testDiff_Success() throws JsonProcessingException {
        // Arrange
        JsonComparator jsonComparator = new JsonComparator();


        String json1 = "{\"name\": \"John\", \n\"age\": 30}";
        String json2 = "{\"name\": \"Jane\", \n\"age\": 25, \n\"city\": \"Poznan\"}";
        String expected = "Line 1:\n" +
                "  - {\"name\": \"John\", \n" +
                "  + {\"name\": \"Jane\", \n" +
                "Line 2:\n" +
                "  - \"age\": 30}\n" +
                "  + \"age\": 25, \n" +
                "Line 3:\n" +
                "  + \"city\": \"Poznan\"}\n";

        // Act
        String result = jsonComparator.compareJson(json1, json2);
        // Assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testDiff_AllNone() throws JsonProcessingException {
        // Arrange
        JsonComparator jsonComparator = new JsonComparator();


        String json1 = "";
        String json2 = "";
        String expected = "";

        // Act
        String result = jsonComparator.compareJson(json1, json2);
        // Assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testDiff_1None() throws JsonProcessingException {
        // Arrange
        JsonComparator jsonComparator = new JsonComparator();


        String json1 = "";
        String json2 = "{\"name\": \"Jane\", \n\"age\": 25}";
        String expected = "Line 1: {\"name\": \"Jane\", \n" +
                "Line 2: \"age\": 25}\n";

        // Act
        String result = jsonComparator.compareJson(json1, json2);
        // Assert
        Assertions.assertEquals(expected, result);
    }


    @Test
    public void testDiff_2None() throws JsonProcessingException {
        // Arrange
        JsonComparator jsonComparator = new JsonComparator();


        String json1 = "{\"name\": \"Jane\", \n\"age\": 25}";
        String json2 = "";
        String expected = "Line 1: {\"name\": \"Jane\", \n" +
                "Line 2: \"age\": 25}\n";

        // Act
        String result = jsonComparator.compareJson(json1, json2);
        // Assert
        Assertions.assertEquals(expected, result);
    }


    @Test
    public void testDiff_NullJson1_ThrowsException() {
        // Arrange
        JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
        String json2 = "";

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonComparator.compareJson(null, json2);
        });
    }

    @Test
    public void testDiff_NullJson2_ThrowsException() {
        // Arrange
        JsonDeletingKeys jsonDeletingKeys = new JsonDeletingKeys();
        String json1 = "";

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonComparator.compareJson(json1, null);
        });
    }


}
