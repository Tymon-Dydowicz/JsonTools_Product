package pl.put.poznan.transformer.logic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Minificator {

    public Minificator() {
    }

    /**
     * This function is responsible for minimizing the JSON text
     *
     * @param JsonText text that is to be minified
     * @return Json in it's minified format
     */
    public String minifyJson(String JsonText) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object minifiedJson = objectMapper.readValue(JsonText, Object.class);
        return objectMapper.writeValueAsString(minifiedJson);
    }
}
