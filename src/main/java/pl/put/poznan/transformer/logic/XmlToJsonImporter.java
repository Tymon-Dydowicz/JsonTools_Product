package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

/**
 * This class is responsible for importing XML data to JSON format using the Jackson library.
 *
 * @version 1.0
 * @since 4.0
 */
public class XmlToJsonImporter {
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public XmlToJsonImporter() {
        this.objectMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    /**
     * Imports the XML data to a JSON string.
     *
     * @param xml the XML data as a string
     * @return the JSON representation of the XML data
     * @throws IOException if an I/O error occurs during the import process
     */
    public String importToJson(String xml) throws IOException {
        if (xml == null) {
            throw new IllegalArgumentException("XML string cannot be null");
        }

        JsonNode rootNode = xmlMapper.readTree(xml);
        ObjectWriter writer = objectMapper.writer();
        return writer.writeValueAsString(rootNode);
    }
}
