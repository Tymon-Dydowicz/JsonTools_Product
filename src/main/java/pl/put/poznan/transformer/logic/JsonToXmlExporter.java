package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.io.IOException;

/**
 * This class is responsible for exporting JSON data to XML format using the Jackson library.
 *
 * @version 1.0
 * @since 4.0
 */
public class JsonToXmlExporter {
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public JsonToXmlExporter() {
        this.objectMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        this.xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    /**
     * Exports the JSON data to an XML string.
     *
     * @param json the JSON data as a string
     * @return the XML representation of the JSON data
     * @throws IOException if an I/O error occurs during the export process
     */
    public String exportToXml(String json) throws IOException {
        if (json == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }

        JsonNode rootNode = objectMapper.readTree(json);
        ObjectWriter writer = xmlMapper.writer().withRootName("root");
        return writer.writeValueAsString(rootNode);
    }
}
