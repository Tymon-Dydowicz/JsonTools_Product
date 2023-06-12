package pl.put.poznan.transformer.logic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsonLogicDecoratorImplMockTest {
    @Mock
    private JsonLogicDecorator decoratedObject;

    private JsonLogicDecoratorImpl decorator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        decorator = new JsonLogicDecoratorImpl(decoratedObject);
    }

    @Test
    void processJson_ValidJsonAndKeys_CallsDecoratedObject() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        String[] keysToSelect = {"name", "age"};
        String expectedResult = "{\"name\": \"John\", \"age\": 30}";

        when(decoratedObject.processJson(json, keysToSelect)).thenReturn(expectedResult);

        String result = decorator.processJson(json, keysToSelect);

        assertEquals(expectedResult, result);
        verify(decoratedObject, times(1)).processJson(json, keysToSelect);
    }

    @Test
    void processJson_InvalidJson_ThrowsIllegalArgumentException() {
        String invalidJson = "{\"name\": \"John\", \"age\": 30";
        String[] keysToSelect = {"name", "age"};

        assertThrows(IllegalArgumentException.class, () -> decorator.processJson(invalidJson, keysToSelect));
        verify(decoratedObject, never()).processJson(anyString(), any(String[].class));
    }

    @Test
    void processJson_NullKeys_ThrowsIllegalArgumentException() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        String[] keysToSelect = null;

        assertThrows(IllegalArgumentException.class, () -> decorator.processJson(json, keysToSelect));
        verify(decoratedObject, never()).processJson(anyString(), any(String[].class));
    }

    @Test
    void processJson_ValidJsonAndKeys_CallsDecoratedObjectOnce() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        String[] keysToSelect = {"name", "age"};
        String expectedResult = "{\"name\": \"John\", \"age\": 30}";

        when(decoratedObject.processJson(json, keysToSelect)).thenReturn(expectedResult);

        String result = decorator.processJson(json, keysToSelect);

        assertEquals(expectedResult, result);
        verify(decoratedObject, times(1)).processJson(json, keysToSelect);
    }
    @Test
    void processJson_MultipleCalls_VerifyDecoratedObjectInvocation() {
        String correctJson1 = "{\"name\": \"John\", \"age\": 30}";
        String correctJson2 = "{\"name\": \"Alice\", \"age\": 25}";
        String incorrectJson = "{\"name\": \"Bob\", \"age\": 35";
        String[] keysToSelect = {"name", "age"};
        String expectedResult = "{\"name\": \"John\", \"age\": 30}";

        when(decoratedObject.processJson(correctJson1, keysToSelect)).thenReturn(expectedResult);
        when(decoratedObject.processJson(correctJson2, keysToSelect)).thenReturn(expectedResult);

        assertDoesNotThrow(() -> decorator.processJson(correctJson1, keysToSelect));
        assertDoesNotThrow(() -> decorator.processJson(correctJson2, keysToSelect));
        assertThrows(IllegalArgumentException.class, () -> decorator.processJson(incorrectJson, keysToSelect));
        assertDoesNotThrow(() -> decorator.processJson(correctJson1, keysToSelect));
        assertDoesNotThrow(() -> decorator.processJson(correctJson2, keysToSelect));

        verify(decoratedObject, times(4)).processJson(anyString(), any(String[].class));
    }

    @Test
    void processJson_DifferentReturnPaths_Coverage() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        String[] keysToSelect = {"name", "age"};

        // Stub the behavior of the decorated object
        when(decoratedObject.processJson(json, keysToSelect))
                .thenThrow(new IllegalArgumentException("Invalid JSON argument!")) // Simulate an exception
                .thenReturn("{\"name\": \"John\", \"age\": 30}") // Simulate a successful return
                .thenReturn(""); // Simulate an empty return

        // First call: Exception path
        assertThrows(IllegalArgumentException.class, () -> decorator.processJson(json, keysToSelect));

        // Second call: Successful return path
        String result = decorator.processJson(json, keysToSelect);
        assertEquals("{\"name\": \"John\", \"age\": 30}", result);

        // Third call: Empty return path
        result = decorator.processJson(json, keysToSelect);
        assertEquals("", result);

        // Verify that the decorated object was called three times
        verify(decoratedObject, times(3)).processJson(json, keysToSelect);
    }


}
