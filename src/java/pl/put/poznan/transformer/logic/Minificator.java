package pl.put.poznan.transformer.logic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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

    /**
     * This function is responsible for reversing minification of JSON text
     *
     * @param JsonText text that is to be unminified
     * @return Json in its unminified format
     */
    public String unMinifyJson(String JsonText) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        Object unMinifiedJson = objectMapper.readValue(JsonText, Object.class);
        return objectMapper.writer(prettyPrinter).writeValueAsString(unMinifiedJson);
    }
}
