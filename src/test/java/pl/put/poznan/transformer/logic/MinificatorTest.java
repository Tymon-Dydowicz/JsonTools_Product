package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinificatorTest {
    private Minificator minifi;

    @BeforeEach
    public void setup() {
        minifi = new Minificator();
    }

    @Test
    public void testMinifyJSON_stringNotPresent(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            minifi.minifyJson(null);
        });
    }

    @Test
    public void testMinifyJSON_wrongInputFormat(){
        ;
        String json = "{\"name\": \"John\", \"age\": 30, \"address\": {\"city\": \"New York\"}";
        Assertions.assertThrows(JsonProcessingException.class, () -> {
            minifi.minifyJson(json);
        });
    }

    @Test
    public void testMinifyJSON_wrongOutput() throws JsonProcessingException {
        String json = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
        String expectedJson = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        String resultJson = minifi.minifyJson(json);
        Assertions.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void testunMinifyJSON_stringNotPresent(){

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            minifi.unMinifyJson(null);
        });
    }

    @Test
    public void testunMinifyJSON_wrongInputFormat(){
        ;
        String json = "{\"name\": \"John\", \"age\": 30, \"address\": {\"city\": \"New York\"}";
        Assertions.assertThrows(JsonProcessingException.class, () -> {
            minifi.unMinifyJson(json);
        });
    }

    @Test
    public void testunMinifyJSON_wrongOutput() throws JsonProcessingException {
        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        String expectedJson = "{\r\n  \"name\" : \"John\",\r\n  \"age\" : 30,\r\n  \"city\" : \"New York\"\r\n}";

        String resultJson = minifi.unMinifyJson(json);
        Assertions.assertEquals(expectedJson, resultJson);
    }

}