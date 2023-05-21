package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonDeletingKeys {
    private ObjectMapper objectMapper;

    public JsonDeletingKeys() {
        this.objectMapper = new ObjectMapper();
    }

    public String deleteKeys(String json, String[] keysToRemove) throws JsonProcessingException {
        if (json == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }

        if (keysToRemove == null) {
            throw new IllegalArgumentException("Keys to remove cannot be null");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
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
}
