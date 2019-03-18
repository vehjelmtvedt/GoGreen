package data;

import data.BuyLocallyProducedFood;
import data.EatVegetarianMeal;
import data.User;
import org.junit.Test;
import tools.CarbonCalculator;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class BuyLocallyProducedFoodTest {
    BuyLocallyProducedFood food = new BuyLocallyProducedFood();
    User userOne = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");

    @Test
    public void testConstructor() {
        assertEquals("Food", food.getCategory());
        assertEquals("Buy Locally Produced Food", food.getName());
    }

    @Test
    public void testVeryLittleToAverage() {
        int result = (int) (CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.VERY_LITTLE) / 365.0
                - CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.AVERAGE) / 365.0);
        assertEquals(result, (int) food.veryLittleToAverage());
    }

    @Test
    public void testAverageToAboveAverage() {
        int result = (int) (CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.AVERAGE) / 365.0
                - CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.ABOVE_AVERAGE) / 365.0);
        assertEquals(result, (int) food.averageToAboveAverage());
    }

    @Test
    public void testAboveAverageToAlmostALl () {
        int result = (int) (CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.ABOVE_AVERAGE) / 365.0
                - CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.ALMOST_ALL) / 365.0);
        assertEquals(result, (int) food.aboveAverageToAlmostAll());
    }

    @Test
    public  void testTimesPerformedInTheSameDay () {
        assertEquals(0, food.timesPerformedInTheSameDay(userOne));
        userOne.addActivity(food);
        userOne.addActivity(null);
        userOne.addActivity(new EatVegetarianMeal());
        BuyLocallyProducedFood food2 = new BuyLocallyProducedFood();
        try {
            food2.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/1999"));
        }catch (Exception e){ }
        userOne.addActivity(food2);
        assertEquals(1, food.timesPerformedInTheSameDay(userOne));
    }

    @Test
    public void testCalculateCarbonSavedByUserWithVeryLittleConsumption() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setLocallyProducedFoodConsumption("very little");
        assertEquals((int) food.veryLittleToAverage(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.averageToAboveAverage(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.aboveAverageToAlmostAll(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedByUserWithAverageConsumption() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setLocallyProducedFoodConsumption("average");
        assertEquals((int) food.averageToAboveAverage(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.aboveAverageToAlmostAll(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedByUserWithAboveAverageConsumption() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setLocallyProducedFoodConsumption("above average");
        assertEquals((int) food.aboveAverageToAlmostAll(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedByUserWithAlmostAllConsumption() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setLocallyProducedFoodConsumption("almost all");
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

}
