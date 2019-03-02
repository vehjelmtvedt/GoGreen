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
        assertEquals(CarbonCalculator.nonOrganicFoodEmissions(CarbonCalculator.OrganicFoodConsumption.NONE), 700);
        assertEquals(CarbonCalculator.nonOrganicFoodEmissions(CarbonCalculator.OrganicFoodConsumption.SOME), 500);
        assertEquals(CarbonCalculator.nonOrganicFoodEmissions(CarbonCalculator.OrganicFoodConsumption.MOST), 200);
        assertEquals(CarbonCalculator.nonOrganicFoodEmissions(CarbonCalculator.OrganicFoodConsumption.ALL), 0);
    }

    @Test
    public void meatAndDairyConsumptionEmissions() {
        assertEquals(CarbonCalculator.meatAndDairyConsumptionEmissions(CarbonCalculator.MeatAndDairyConsumption.ABOVE_AVERAGE), 600);
        assertEquals(CarbonCalculator.meatAndDairyConsumptionEmissions(CarbonCalculator.MeatAndDairyConsumption.AVERAGE), 400);
        assertEquals(CarbonCalculator.meatAndDairyConsumptionEmissions(CarbonCalculator.MeatAndDairyConsumption.BELOW_AVERAGE), 250);
        assertEquals(CarbonCalculator.meatAndDairyConsumptionEmissions(CarbonCalculator.MeatAndDairyConsumption.LACTO_VEGETARIAN), 100);
        assertEquals(CarbonCalculator.meatAndDairyConsumptionEmissions(CarbonCalculator.MeatAndDairyConsumption.VEGAN), 0);
    }

    @Test
    public void foodMilesEmissions() {
        assertEquals(CarbonCalculator.foodMilesEmissions(CarbonCalculator.LocallyProducedFoodConsumption.VERY_LITTLE), 500);
        assertEquals(CarbonCalculator.foodMilesEmissions(CarbonCalculator.LocallyProducedFoodConsumption.AVERAGE), 300);
        assertEquals(CarbonCalculator.foodMilesEmissions(CarbonCalculator.LocallyProducedFoodConsumption.ABOVE_AVERAGE), 200);
        assertEquals(CarbonCalculator.foodMilesEmissions(CarbonCalculator.LocallyProducedFoodConsumption.ALMOST_ALL), 100);
    }

    @Test
    public void processedFoodEmissions() {
        assertEquals(CarbonCalculator.processedFoodEmissions(CarbonCalculator.ProcessedFoodConsumption.ABOVE_AVERAGE), 600);
        assertEquals(CarbonCalculator.processedFoodEmissions(CarbonCalculator.ProcessedFoodConsumption.AVERAGE), 400);
        assertEquals(CarbonCalculator.processedFoodEmissions(CarbonCalculator.ProcessedFoodConsumption.BELOW_AVERAGE), 200);
        assertEquals(CarbonCalculator.processedFoodEmissions(CarbonCalculator.ProcessedFoodConsumption.VERY_LITTLE), 50);
    }

}