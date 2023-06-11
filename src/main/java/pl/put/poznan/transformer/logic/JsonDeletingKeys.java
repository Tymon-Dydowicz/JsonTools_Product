package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class is responsible for deleting keys from JSON text
 * It uses Jackson library
 *
 * @version 1.0
 * @since 4.0
 */
public class JsonDeletingKeys  implements JsonLogicDecorator{
    private ObjectMapper objectMapper;

    public JsonDeletingKeys() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * This function is responsible for deleting keys from JSON text
     *
     * @param json          text that is to be processed
     * @param keysToRemove  keys that are to be deleted
     * @return Json in its unminified format
     */
    public String deleteKeys(String json, String[] keysToRemove) throws IllegalArgumentException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("invalid JSON argument!");
        }
        removeKeys(rootNode, keysToRemove);
        return rootNode.toString();
    }

    private void removeKeys(JsonNode node, String[] keysToRemove) {
        if (node.isObject()) {
            // Remove keys from JSON objects
            for (String key : keysToRemove) {
                ((ObjectNode) node).remove(key);
            }
            // Recursively process child nodes
            node.fields().forEachRemaining(entry -> removeKeys(entry.getValue(), keysToRemove));
        } else if (node.isArray()) {
            // Process each element in JSON arrays
            for (JsonNode childNode : node) {
                removeKeys(childNode, keysToRemove);
            }
        }
        // Ignore other node types (e.g., strings, numbers, booleans)
    }

    /**
     * This function calls deleteKeys method to delete keys from given json string
     * @param json json string to convert
     * @param keysToSelect list of keys that are to be selected for deletion
     * @return string
     * @throws IllegalArgumentException
     */
    @Override
    public String processJson(String json, String[] keysToSelect) throws IllegalArgumentException {
        return deleteKeys(json, keysToSelect);
    }
}
