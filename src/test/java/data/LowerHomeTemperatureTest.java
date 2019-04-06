package data;

import org.junit.Test;
import tools.CarbonCalculator;

import static org.junit.Assert.assertEquals;

public class LowerHomeTemperatureTest {
    LowerHomeTemperature temp = new LowerHomeTemperature();
    User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");

    @Test
    public void testConstructor() {
        assertEquals("Household", temp.getCategory());
        assertEquals("Lower Home Temperature", temp.getName());
        assertEquals(0, temp.getDegrees());
    }

    @Test
    public void testSetDegrees() {
        temp.setDegrees(10);
        assertEquals(10, temp.getDegrees());
    }

    @Test
    public void testCalculateCarbonSaved() {
        user.setHeatingOilDailyConsumption(2.5);
        temp.setDegrees(10000);
        temp.setCarbonSaved(temp.calculateCarbonSaved(user));
        assertEquals((int) CarbonCalculator.heatingOilEmissions(user.getHeatingOilDailyConsumption() *temp.getDegrees()*1.8/100), (int) temp.getCarbonSaved());
    }
}
