package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public class JsonSelectingKeys {
    private ObjectMapper objectMapper;

    public JsonSelectingKeys() {
        this.objectMapper = new ObjectMapper();
    }

    public String selectKeys(String json, String[] keysToSelect) throws JsonProcessingException {
        if (json == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }

        if (keysToSelect == null) {
            throw new IllegalArgumentException("Keys to select cannot be null");
        }

        JsonNode rootNode = objectMapper.readTree(json);
        selectKeys(rootNode, keysToSelect);
        return rootNode.toString();
    }

    private void selectKeys(JsonNode node, String[] keysToSelect) {
        if (node.isObject()) {
            // Remove keys not in the selected keys array from JSON objects
            ObjectNode objectNode = (ObjectNode) node;
            List<String> keysToRemove = new ArrayList<>();

            objectNode.fields().forEachRemaining(entry -> {
                if (!containsKey(entry.getKey(), keysToSelect)) {
                    keysToRemove.add(entry.getKey());
                }
            });

            // Remove the keys outside of the iteration loop
            keysToRemove.forEach(objectNode::remove);

            // Recursively process child nodes
            objectNode.fields().forEachRemaining(entry -> selectKeys(entry.getValue(), keysToSelect));
        } else if (node.isArray()) {
            // Process each element in JSON arrays
            for (JsonNode childNode : node) {
                selectKeys(childNode, keysToSelect);
            }
        }
        // Ignore other node types (e.g., strings, numbers, booleans)
    }

    private boolean containsKey(String key, String[] keysToSelect) {
        for (String selectedKey : keysToSelect) {
            if (selectedKey.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
