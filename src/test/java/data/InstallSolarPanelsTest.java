package data;

import org.junit.Test;
import tools.CarbonCalculator;
import tools.Requests;

import static org.junit.Assert.assertEquals;

public class InstallSolarPanelsTest {
    InstallSolarPanels panels = new InstallSolarPanels();
    User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");

    @Test
    public void testConstructor() {
        assertEquals("Install Solar Panels", panels.getName());
        assertEquals("Household", panels.getCategory());
        panels.setKwhSavedPerYear(1000);
        assertEquals(1000, panels.getKwhSavedPerYear());
    }

    @Test
    public void testSetAndGetDailyCarbonSaved() {
        panels.setDailyCarbonSaved(15);
        assertEquals(15, (int) panels.getDailyCarbonSaved());
    }

    @Test
    public void testCalculateCarbonSavedWhenSolarPanelsProduceLessElectricityThanTheUserConsumes() {
        assertEquals((int) (CarbonCalculator.electricityEmissions(1000) / 365.0), (int) panels.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedWhenSolarPanelsProduceMoreElectricityThanTheUserConsumes() {
        panels.setKwhSavedPerYear(100000);
        user.setElectricityDailyConsumption(10000/365.0);
        assertEquals((int) (CarbonCalculator.electricityEmissions((int) user.getElectricityDailyConsumption())),(int) panels.calculateCarbonSaved(user));
    }

    @Test
    public void testPerformActivity() {
        panels.setKwhSavedPerYear(10000);
        user.setElectricityDailyConsumption(100000);
        panels.performActivity(user, Requests.instance);
        assertEquals((int) (CarbonCalculator.electricityEmissions(10000) / 365.0), (int) panels.getDailyCarbonSaved());
    }

}
