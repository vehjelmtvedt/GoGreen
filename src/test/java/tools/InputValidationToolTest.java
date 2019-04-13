package tools;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class InputValidationToolTest {
    @Test
    public void testAgeValidation() {
        assertEquals(InputValidationTool.validateAge("5"),true);
    }

    @Test
    public void testAgeValidationFalse() {
        assertEquals(InputValidationTool.validateAge("-1"),false);
    }

    @Test
    public void testAgeValidationInvalidInput() {
        assertEquals(InputValidationTool.validateAge("abc"),false);
    }

    @Test
    public void testPassValidation() {
        assertEquals(InputValidationTool.validatePassword("qwerty"),true);
    }

    @Test
    public void testEmailValidation() {
        assertEquals(InputValidationTool.validateEmail("a@a.com"),true);
    }
}
