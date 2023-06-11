package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of decorator wrapping classes that operate on JSONs
 */
public class JsonLogicDecoratorImpl implements JsonLogicDecorator{
    private final JsonLogicDecorator decoratedObject;

    public JsonLogicDecoratorImpl(JsonLogicDecorator decoratedObject){
        this.decoratedObject = decoratedObject;
    }

    /**
     * Check validity of json string by trying to parse it using Jacskon
     * @param json json string to be tested
     * @return output of test, either true if json is correct or false otherwise
     */
    public Boolean isJsonValid(String json) {
        try {
            new ObjectMapper().readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    /**
     * Run processing of Json file by object that is being decorated
     * @param json json string to be processed
     * @param keysToSelect keys that will be used in operation
     * @return output of decorated object
     * @throws IllegalArgumentException thrown if json is invalid
     */
    @Override
    public String processJson(String json, String[] keysToSelect) throws IllegalArgumentException{

        if(!isJsonValid(json)){
            throw new IllegalArgumentException("Invalid JSON argument!");
        }

        if(keysToSelect == null){
            throw new IllegalArgumentException("Keys are nulll!");
        }

        return this.decoratedObject.processJson(json, keysToSelect);
    }
}
