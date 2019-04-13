package tools;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class InputValidationToolTest {
    @Test
    public void testAgeValidation() {
        assertTrue(InputValidationTool.validateAge("5"));
    }

    @Test
    public void testAgeValidationFalse() {
        assertFalse(InputValidationTool.validateAge("-1"));
    }

    @Test
    public void testAgeValidationInvalidInput() {
        assertFalse(InputValidationTool.validateAge("abc"));
    }

    @Test
    public void testPassValidation() {
        assertTrue(InputValidationTool.validatePassword("qwerty"));
    }

    @Test
    public void testEmailValidation() {
        assertTrue(InputValidationTool.validateEmail("a@a.com"));
    }
}
