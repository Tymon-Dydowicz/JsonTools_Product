package pl.put.poznan.transformer.logic;

/**
 * Decorator for classes that perform logic operations on JSON objects
 */
public interface JsonLogicDecorator {
    /**
     * Process JSON string
     * @param json
     * @param keysToSelect
     * @return
     */
    String processJson(String json, String[] keysToSelect);
}
