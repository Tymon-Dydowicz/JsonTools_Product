package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides functionality to compare two JSON texts line by line and highlight the differences in a cohesive string.
 */
public class JsonComparator {
    private ObjectMapper objectMapper;

    /**
     * Constructs a new instance of the JsonComparator class.
     */
    public JsonComparator() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Compares two JSON texts line by line and returns the differences as a cohesive string with line numbers.
     *
     * @param json1 The first JSON text.
     * @param json2 The second JSON text.
     * @return String highlighting the differences with line numbers.
     * @throws IllegalArgumentException If either of the JSON strings is null.
     */
    public String compareJson(String json1, String json2) throws IllegalArgumentException {
        if (json1 == null || json2 == null) {
            throw new IllegalArgumentException("JSON strings cannot be null");
        }

        if (json1.isBlank() && json2.isBlank()) {
            return "";
        } else if (json1.isBlank()) {
            return getHighlightedLines(json2);
        } else if (json2.isBlank()) {
            return getHighlightedLines(json1);
        }

        String[] lines1 = json1.split("\n");
        String[] lines2 = json2.split("\n");

        List<String> diffLines = compareLines(lines1, lines2);

        return formatDiffLines(diffLines);
    }

    private List<String> compareLines(String[] lines1, String[] lines2) {
        List<String> diffLines = new ArrayList<>();
        int minLength = Math.min(lines1.length, lines2.length);

        for (int i = 0; i < minLength; i++) {
            if (!lines1[i].equals(lines2[i])) {
                diffLines.add("Line " + (i + 1) + ":");
                diffLines.add("  - " + lines1[i]);
                diffLines.add("  + " + lines2[i]);
            }
        }

        if (lines1.length > minLength) {
            for (int i = minLength; i < lines1.length; i++) {
                diffLines.add("Line " + (i + 1) + ":");
                diffLines.add("  - " + lines1[i]);
            }
        }

        if (lines2.length > minLength) {
            for (int i = minLength; i < lines2.length; i++) {
                diffLines.add("Line " + (i + 1) + ":");
                diffLines.add("  + " + lines2[i]);
            }
        }

        return diffLines;
    }

    private String formatDiffLines(List<String> diffLines) {
        StringBuilder diffBuilder = new StringBuilder();
        for (String line : diffLines) {
            diffBuilder.append(line).append("\n");
        }
        return diffBuilder.toString();
    }

    private String getHighlightedLines(String json) {
        String[] lines = json.split("\n");
        List<String> highlightedLines = new ArrayList<>();
        for (String line : lines) {
            highlightedLines.add("Line " + (highlightedLines.size() + 1) + ": " + line);
        }
        return formatDiffLines(highlightedLines);
    }
}