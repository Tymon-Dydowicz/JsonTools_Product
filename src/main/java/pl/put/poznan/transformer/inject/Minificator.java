package pl.put.poznan.transformer.inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * This class is responsible for minifying and unminifying JSON text
 * It uses Jackson library
 *
 * @version 1.0
 * @since 4.0
 */
public class Minificator {
    private final ObjectMapper objectMapper;
    private final ObjectWriter writer;

    public Minificator(ObjectMapper objectMapper, ObjectWriter writer) {
        this.objectMapper = objectMapper;
        this.writer = writer;
    }

    /**
     * This function is responsible for minimizing the JSON text
     *
     * @param jsonText text that is to be minified
     * @return Json in its minified format
     */
    public String minifyJson(String jsonText) throws JsonProcessingException {
        Object minifiedJson = objectMapper.readValue(jsonText, Object.class);
        return objectMapper.writeValueAsString(minifiedJson);
    }

    /**
     * This function is responsible for reversing minification of JSON text
     *
     * @param jsonText text that is to be unminified
     * @return Json in its unminified format
     */
    public String unMinifyJson(String jsonText) throws JsonProcessingException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Object unMinifiedJson = objectMapper.readValue(jsonText, Object.class);
        return writer.writeValueAsString(unMinifiedJson);
    }
}

