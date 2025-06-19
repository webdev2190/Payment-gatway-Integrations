package com.optum.pure.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Properties;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit tests for ConfigurationManager.java (Modernized for Java 21)
 */
@ExtendWith(MockitoExtension.class)
public class ConfigurationManagerTest {

    @Mock
    Properties props; // Mocking the Properties class

    @BeforeEach
    public void setUp() {
        // Mock setup will automatically initialize
    }

    @Test
    public void getTest() throws IllegalAccessException {
        // Injecting the mock into the static field of ConfigurationManager
        Field field = ConfigurationManager.class.getDeclaredField("props");
        field.setAccessible(true); // Ensure the field is accessible (in case it's private)
        field.set(null, props); // Set the mocked properties to the static field

        // Define behavior for the mocked Properties object
        when(props.getProperty("key")).thenReturn("test");

        // Test the static method
        String value = ConfigurationManager.get("key");

        // Assert the result
        assertEquals("test", value);
    }
}
