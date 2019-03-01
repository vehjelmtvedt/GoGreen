package tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarbonCalculatorTest {

    @Test
    public void electricityEmissions() {
        assertEquals((int)(500 * 0.309)*1000, (int) CarbonCalculator.electricityEmissions(500)*1000);
    }

    @Test
    public void naturalGasEmissions() {
        assertEquals((int)(200 * 11.36 * 0.203)*1000, (int)CarbonCalculator.naturalGasEmissions(200)*1000);
    }

    @Test
    public void heatingOilEmissions() {
        assertEquals((int)(450 * 2.96)*1000, (int) CarbonCalculator.heatingOilEmissions(450)*1000);
    }

    @Test
    public void smallCarEmissions() {
        assertEquals((int)(0.621371 * 15 * 0.390)*1000, (int) CarbonCalculator.smallCarEmissions(15)*1000);
    }

    @Test
    public void mediumCarEmissions() {
        assertEquals((int) (0.621371 * 25 * 0.430)*1000, (int)CarbonCalculator.mediumCarEmissions(25)*1000);
    }

    @Test
    public void largeCarEmissions() {
        assertEquals((int) (0.621371 * 211 * 0.600)*1000, (int) CarbonCalculator.largeCarEmissions(211)*1000);
    }

    @Test
    public void busEmissions() {
        assertEquals((int) (45 * 0.089)*1000, (int) CarbonCalculator.busEmissions(45)*1000);
    }

    @Test
    public void trainEmissions() {
        assertEquals((int) (29 * 0.049)*1000, (int) CarbonCalculator.trainEmissions(29)*1000);
    }

    @Test
    public void nonOrganicFoodEmissions() {
        assertEquals(700, CarbonCalculator.nonOrganicFoodEmissions("none"));
        assertEquals(500, CarbonCalculator.nonOrganicFoodEmissions("some"));
        assertEquals(200, CarbonCalculator.nonOrganicFoodEmissions("most"));
        assertEquals(0, CarbonCalculator.nonOrganicFoodEmissions("all"));
    }

    @Test
    public void meatAndDairyConsumptionEmissions() {
        assertEquals(600, CarbonCalculator.meatAndDairyConsumptionEmissions("above average"));
        assertEquals(400, CarbonCalculator.meatAndDairyConsumptionEmissions("average"));
        assertEquals(250, CarbonCalculator.meatAndDairyConsumptionEmissions("below average"));
        assertEquals(100, CarbonCalculator.meatAndDairyConsumptionEmissions("lacto-vegetarian"));
        assertEquals(0, CarbonCalculator.meatAndDairyConsumptionEmissions("vegan"));
    }

    @Test
    public void foodMilesEmissions() {
        assertEquals(500, CarbonCalculator.foodMilesEmissions("very little"));
        assertEquals(300, CarbonCalculator.foodMilesEmissions("average"));
        assertEquals(200, CarbonCalculator.foodMilesEmissions("above average"));
        assertEquals(100, CarbonCalculator.foodMilesEmissions("almost all"));
    }

    @Test
    public void processedFoodEmissions() {
        assertEquals(600, CarbonCalculator.processedFoodEmissions("above average"));
        assertEquals(400, CarbonCalculator.processedFoodEmissions("average"));
        assertEquals(200, CarbonCalculator.processedFoodEmissions("below average"));
        assertEquals(50, CarbonCalculator. processedFoodEmissions("very little"));
    }


    // exception tests
    @Test(expected = IllegalArgumentException.class)
    public void nonOrganicFoodEmissionsExceptionTest(){
        int i = CarbonCalculator.nonOrganicFoodEmissions("irrelevant string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void meatAndDairyConsumptionEmissionsExceptionTest(){
        int i = CarbonCalculator.meatAndDairyConsumptionEmissions("random string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void foodMilesEmissionsExceptionTest(){
        int i = CarbonCalculator.foodMilesEmissions("completely random string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void processedFoodEmissionsExceptionTest(){
        int i = CarbonCalculator.processedFoodEmissions("Roses are red, violets are blue");
    }
}