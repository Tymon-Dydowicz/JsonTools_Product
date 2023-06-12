package pl.put.poznan.transformer.inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MinificatorTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ObjectWriter writer;

    private Minificator minificator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        minificator = new Minificator(objectMapper, writer);
    }

    @Test
    void minifyJson_ShouldMinifyJsonUsingObjectMapper() throws JsonProcessingException {
        // Arrange
        String jsonText = "{\"name\": \"John\", \"age\": 30}";
        Object minifiedJson = new Object(); // Mocked object

        when(objectMapper.readValue(jsonText, Object.class)).thenReturn(minifiedJson);
        when(objectMapper.writeValueAsString(minifiedJson)).thenReturn("{\"name\":\"John\",\"age\":30}");

        // Act
        String minified = minificator.minifyJson(jsonText);

        // Assert
        assertEquals("{\"name\":\"John\",\"age\":30}", minified);
        verify(objectMapper).readValue(jsonText, Object.class);
        verify(objectMapper).writeValueAsString(minifiedJson);
        verifyNoMoreInteractions(objectMapper, writer);
    }

    @Test
    void minifyJson_ShouldThrowException_WhenJsonProcessingFails() throws JsonProcessingException {
        // Arrange
        String jsonText = "{\"name\": \"John\", \"age\": 30}";

        when(objectMapper.readValue(jsonText, Object.class)).thenThrow(new JsonProcessingException("Mock Exception") {});

        // Act & Assert
        assertThrows(JsonProcessingException.class, () -> minificator.minifyJson(jsonText));
        verify(objectMapper).readValue(jsonText, Object.class);
        verifyNoMoreInteractions(objectMapper, writer);
    }

    @Test
    void unMinifyJson_ShouldUnminifyJsonUsingObjectMapperAndWriter() throws JsonProcessingException {
        // Arrange
        String jsonText = "{\"name\":\"John\",\"age\":30}";
        Object unMinifiedJson = new Object(); // Mocked object

        when(objectMapper.readValue(jsonText, Object.class)).thenReturn(unMinifiedJson);
        when(writer.writeValueAsString(unMinifiedJson)).thenReturn("{\n  \"name\" : \"John\",\n  \"age\" : 30\n}");

        // Act
        String unMinified = minificator.unMinifyJson(jsonText);

        // Assert
        assertEquals("{\n  \"name\" : \"John\",\n  \"age\" : 30\n}", unMinified);
        verify(objectMapper).readValue(jsonText, Object.class);
        verify(objectMapper).enable(SerializationFeature.INDENT_OUTPUT);
        verify(writer).writeValueAsString(unMinifiedJson);
        verifyNoMoreInteractions(objectMapper, writer);
    }

    @Test
    void unMinifyJson_ShouldThrowException_WhenJsonProcessingFails() throws JsonProcessingException {
        // Arrange
        String jsonText = "{\"name\":\"John\",\"age\":30}";

        when(objectMapper.readValue(jsonText, Object.class)).thenThrow(new JsonProcessingException("Mock Exception") {});

        // Act & Assert
        assertThrows(JsonProcessingException.class, () -> minificator.unMinifyJson(jsonText));
        verify(objectMapper).readValue(jsonText, Object.class);
    }
}
