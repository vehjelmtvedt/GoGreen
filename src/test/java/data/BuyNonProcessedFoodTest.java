package data;

import data.BuyNonProcessedFood;
import data.EatVegetarianMeal;
import data.User;
import org.junit.Test;
import tools.CarbonCalculator;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class BuyNonProcessedFoodTest {
    BuyNonProcessedFood food = new BuyNonProcessedFood();
    User userOne = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");

    @Test
    public void testConstructor() {
        assertEquals("Food", food.getCategory());
        assertEquals("Buy Non-Processed Food", food.getName());
    }

    @Test
    public void testAboveAverageToAverage() {
        int result = (int) (CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.ABOVE_AVERAGE) / 365.0
                - CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.AVERAGE) / 365.0);
        assertEquals(result, (int) food.aboveAverageToAverage());
    }

    @Test
    public  void testAverageToBelowAverage() {
        int result = (int) (CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.AVERAGE) / 365.0
                - CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.BELOW_AVERAGE) / 365.0);
        assertEquals(result, (int) food.averageToBelowAverage());
    }

    @Test
    public void belowAverageToVeryLittle() {
        int result = (int) (CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.AVERAGE) / 365.0
                - CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.BELOW_AVERAGE) / 365.0);
        assertEquals(result, (int) food.belowAverageToVeryLittle());
    }

    @Test
    public  void testTimesPerformedInTheSameDay () {
        assertEquals(0, food.timesPerformedInTheSameDay(userOne));
        userOne.addActivity(food);
        userOne.addActivity(null);
        userOne.addActivity(new EatVegetarianMeal());
        BuyNonProcessedFood food2 = new BuyNonProcessedFood();
        try {
            food2.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/1999"));
        }catch (Exception e){ }
        userOne.addActivity(food2);
        assertEquals(1, food.timesPerformedInTheSameDay(userOne));
    }

    @Test
    public void testCalculateCarbonSavedByAboveAverageUser() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setProcessedFoodConsumption("above average");
        assertEquals((int) food.aboveAverageToAverage(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.averageToBelowAverage(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.belowAverageToVeryLittle(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));

    }

    @Test
    public void testCalculateCarbonSavedByAverageUser() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setProcessedFoodConsumption("average");
        assertEquals((int) food.averageToBelowAverage(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.belowAverageToVeryLittle(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedByBelowAverageUser() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setProcessedFoodConsumption("below average");
        assertEquals((int) food.belowAverageToVeryLittle(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedByVerryLittleUser() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setProcessedFoodConsumption("very little");
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }
}
