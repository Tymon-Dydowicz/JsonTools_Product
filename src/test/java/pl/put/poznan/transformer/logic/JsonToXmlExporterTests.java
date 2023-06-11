package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonToXmlExporterTests {

    private JsonToXmlExporter jsonToXmlExporter;

    @BeforeEach
    public void setup() {
        jsonToXmlExporter = new JsonToXmlExporter();
    }

    @Test
    public void testExportToXml_Success() throws Exception {
        // Arrange
        String json = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";
        String expectedXml = "<?xml version='1.0' encoding='UTF-8'?>\n" + "<root>\n" + "  <name>John</name>\n" + "  <age>30</age>\n" + "  <city>New York</city>\n" + "</root>\n";

        // Act
        String result = jsonToXmlExporter.exportToXml(json);

        // Assert
        Assertions.assertEquals(expectedXml, result);
    }

    @Test
    public void testExportToXml_NullJson_ThrowsException() {
        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jsonToXmlExporter.exportToXml(null);
        });
    }

    @Test
    public void testExportToXml_InvalidJsonFormat() {
        // Arrange
        String json = "{\"name\": \"John\", \"age\": 30, \"address\": {\"city\": \"New York\"}";

        // Act and Assert
        Assertions.assertThrows(Exception.class, () -> {
            jsonToXmlExporter.exportToXml(json);
        });
    }

    @Test
    public void testExportToXml_EmptyJson() throws Exception {
        // Arrange
        String json = "{}";
        String expectedXml = "<?xml version='1.0' encoding='UTF-8'?>\n" + "<root/>\n";

        // Act
        String result = jsonToXmlExporter.exportToXml(json);

        // Assert
        Assertions.assertEquals(expectedXml, result);
    }
}
