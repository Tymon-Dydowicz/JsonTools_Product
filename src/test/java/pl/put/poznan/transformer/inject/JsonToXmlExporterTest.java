package pl.put.poznan.transformer.inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsonToXmlExporterTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private XmlMapper xmlMapper;
    @Mock
    private JsonNode jsonNode;
    @Mock
    private ObjectWriter objectWriter;

    private JsonToXmlExporter jsonToXmlExporter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jsonToXmlExporter = new JsonToXmlExporter(objectMapper, xmlMapper);
    }

    @Test
    void exportToXml_ShouldConvertJsonToXml() throws IOException {
        // Arrange
        String json = "{\"name\":\"John\",\"age\":30}";
        String expectedXml = "<root><name>John</name><age>30</age></root>";

        when(objectMapper.readTree(json)).thenReturn(jsonNode);
        when(xmlMapper.writer()).thenReturn(objectWriter);
        when(objectWriter.withRootName("root")).thenReturn(objectWriter);
        when(objectWriter.writeValueAsString(jsonNode)).thenReturn(expectedXml);

        // Act
        String xml = jsonToXmlExporter.exportToXml(json);

        // Assert
        assertEquals(expectedXml, xml);
        verify(objectMapper).readTree(json);
        verify(xmlMapper).writer();
        verify(objectWriter).withRootName("root");
        verify(objectWriter).writeValueAsString(jsonNode);
    }

    @Test
    void exportToXml_ShouldThrowException_WhenJsonIsNull() {
        // Arrange
        String json = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> jsonToXmlExporter.exportToXml(json));
    }


    @Test
    void exportToXml_ShouldIndentXmlOutput_WhenConfigured() throws IOException {
        // Arrange
        String json = "{\"name\":\"John\",\"age\":30}";
        String expectedXml = "<root>\n  <name>John</name>\n  <age>30</age>\n</root>";

        when(objectMapper.readTree(json)).thenReturn(jsonNode);
        when(xmlMapper.writer()).thenReturn(objectWriter);
        when(objectWriter.withRootName("root")).thenReturn(objectWriter);
        when(objectWriter.writeValueAsString(jsonNode)).thenReturn(expectedXml);

        // Enable indentation
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // Act
        String xml = jsonToXmlExporter.exportToXml(json);

        // Assert
        assertEquals(expectedXml, xml);
        verify(objectMapper).readTree(json);
        verify(xmlMapper).writer();
        verify(objectWriter).withRootName("root");
        verify(objectWriter).writeValueAsString(jsonNode);
    }

    @Test
    void exportToXml_ShouldNotIndentXmlOutput_WhenNotConfigured() throws IOException {
        // Arrange
        String json = "{\"name\":\"John\",\"age\":30}";
        String expectedXml = "<root><name>John</name><age>30</age></root>";

        when(objectMapper.readTree(json)).thenReturn(jsonNode);
        when(xmlMapper.writer()).thenReturn(objectWriter);
        when(objectWriter.withRootName("root")).thenReturn(objectWriter);
        when(objectWriter.writeValueAsString(jsonNode)).thenReturn(expectedXml);

        // Disable indentation
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, false);

        // Act
        String xml = jsonToXmlExporter.exportToXml(json);

        // Assert
        assertEquals(expectedXml, xml);
        verify(objectMapper).readTree(json);
        verify(xmlMapper).writer();
        verify(objectWriter).withRootName("root");
        verify(objectWriter).writeValueAsString(jsonNode);
    }
}
