package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class XmlToJsonImporterTests {

    private XmlToJsonImporter xmlToJsonImporter;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        xmlToJsonImporter = new XmlToJsonImporter();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testImportToJson_Success() throws Exception {
        // Arrange
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n" + "<root>\n" + "  <name>John</name>\n" + "  <age>30</age>\n" + "  <city>New York</city>\n" + "</root>\n";
        String expectedJson = "{\"name\":\"John\",\"age\":\"30\",\"city\":\"New York\"}";

        // Act
        String result = xmlToJsonImporter.importToJson(xml);

        // Convert the JSON strings to JsonNode for comparison
        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        JsonNode resultNode = objectMapper.readTree(result);

        // Assert
        Assertions.assertEquals(expectedNode, resultNode);
    }

    @Test
    public void testImportToJson_NullXml_ThrowsException() {
        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            xmlToJsonImporter.importToJson(null);
        });
    }

    @Test
    public void testImportToJson_InvalidXmlFormat() {
        // Arrange
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n" + "<root>\n" + "  <name>John</name>\n" + "  <age>30\n" + // Missing closing tag
                "</root>\n";

        // Act and Assert
        Assertions.assertThrows(Exception.class, () -> {
            xmlToJsonImporter.importToJson(xml);
        });
    }

    @Test
    public void testImportToJson_EmptyXml() throws Exception {
        // Arrange
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n" + "<root/>\n";
        String expectedJson = "{}";

        // Act
        String result = xmlToJsonImporter.importToJson(xml);

        // Convert the JSON strings to JsonNode for comparison
        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        JsonNode resultNode = objectMapper.readTree(result);

        // Assert
        Assertions.assertEquals(expectedNode, resultNode);
    }
}
