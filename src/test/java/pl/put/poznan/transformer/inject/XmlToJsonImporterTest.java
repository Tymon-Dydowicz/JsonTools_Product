package pl.put.poznan.transformer.inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class XmlToJsonImporterTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private XmlMapper xmlMapper;
    @Mock
    private JsonNode jsonNode;
    @Mock
    private ObjectWriter objectWriter;

    private XmlToJsonImporter xmlToJsonImporter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        xmlToJsonImporter = new XmlToJsonImporter(objectMapper, xmlMapper);
    }

    @Test
    void importToJson_ShouldConvertXmlToJson() throws IOException {
        // Arrange
        String xml = "<root><name>John</name><age>30</age></root>";
        String expectedJson = "{\"name\":\"John\",\"age\":30}";

        when(xmlMapper.readTree(xml)).thenReturn(jsonNode);
        when(objectMapper.writer()).thenReturn(objectWriter);
        when(objectWriter.writeValueAsString(jsonNode)).thenReturn(expectedJson);

        // Act
        String json = xmlToJsonImporter.importToJson(xml);

        // Assert
        assertEquals(expectedJson, json);
    }

    @Test
    void importToJson_ShouldCallReadTreeMethodWithXml() throws IOException {
        // Arrange
        String xml = "<root><name>John</name><age>30</age></root>";
        ObjectWriter objectWriter = mock(ObjectWriter.class);
        when(objectMapper.writer()).thenReturn(objectWriter);

        JsonNode jsonNode = mock(JsonNode.class); // Mock the JsonNode object
        when(xmlMapper.readTree(xml)).thenReturn(jsonNode); // Return the mocked JsonNode object

        when(objectWriter.writeValueAsString(jsonNode)).thenReturn("expectedJson");

        // Act
        xmlToJsonImporter.importToJson(xml);

        // Assert
        verify(xmlMapper).readTree(xml);
        verify(objectMapper).writer();
        verify(objectWriter).writeValueAsString(jsonNode);
    }


    @Test
    void importToJson_ShouldGetWriterFromObjectMapper() throws IOException {
        // Arrange
        String xml = "<root><name>John</name><age>30</age></root>";

        when(xmlMapper.readTree(xml)).thenReturn(jsonNode);
        when(objectMapper.writer()).thenReturn(objectWriter);
        when(objectWriter.writeValueAsString(jsonNode)).thenReturn("expectedJson");

        // Act
        xmlToJsonImporter.importToJson(xml);

        // Assert
        verify(objectMapper).writer();
        verify(objectWriter).writeValueAsString(jsonNode);
    }


    @Test
    void importToJson_ShouldCallWriteValueAsStringMethodWithJsonNode() throws IOException {
        // Arrange
        String xml = "<root><name>John</name><age>30</age></root>";

        when(xmlMapper.readTree(xml)).thenReturn(jsonNode);
        when(objectMapper.writer()).thenReturn(objectWriter);

        // Act
        xmlToJsonImporter.importToJson(xml);

        // Assert
        verify(objectWriter).writeValueAsString(jsonNode);
    }

    @Test
    void importToJson_ShouldThrowException_WhenXmlIsNull() {
        // Arrange
        String xml = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> xmlToJsonImporter.importToJson(xml));
    }

}
