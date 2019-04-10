package data;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
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

    @Test
    public void testPerformActivityRequest() {
        // Mock Requests class
        Requests mockRequests = Mockito.mock(Requests.class);


        panels.setKwhSavedPerYear(10000);
        user.setElectricityDailyConsumption(100000);

        // Create expected new User that is returned by request
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getAge(),
                user.getEmail(), user.getUsername(), user.getPassword());
        newUser.addActivity(panels);

        // Mock mockRequests object to return updated user upon adding activity
        Mockito.when(mockRequests.addActivityRequest(panels, user.getUsername()))
                .thenReturn(newUser);

        panels.performActivity(user, mockRequests);

        Assert.assertEquals(panels, user.getActivities().get(0));
    }

}
