package data;

import data.BuyOrganicFood;
import data.EatVegetarianMeal;
import data.User;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import tools.CarbonCalculator;
import tools.Requests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class EatVegetarianMealTest {
    EatVegetarianMeal meal = new EatVegetarianMeal();
    User userOne = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
    @Test
    public void testConstructor() {
        assertNotNull(meal);
        Date dateNow = Calendar.getInstance().getTime();
        assertEquals(dateNow.toString(), meal.getDate().toString());
        String currentMonth = dateNow.toString().split(" ")[1];
        String currentDay = dateNow.toString().split(" ")[2];
        assertEquals(currentMonth, meal.getDate().toString().split(" ")[1]);
        assertEquals(currentDay, meal.getDate().toString().split(" ")[2]);
        assertEquals(0, (int) meal.getCarbonSaved());
        assertEquals("Food", meal.getCategory());
        assertEquals("Eat Vegetarian Meal", meal.getName());
    }

    @Test
    public void testSetDate() {
        Date dateNow = Calendar.getInstance().getTime();
        meal.setDate(dateNow);
        assertEquals(dateNow.toString(), meal.getDate().toString());
    }

    @Test
    public void testSetCarbonSaved() {
        meal.setCarbonSaved(15.2);
        assertNotEquals(0, (int) meal.getCarbonSaved());
    }

    @Test
    public void testGetCarbonSaved() {
        EatVegetarianMeal meal2 = new EatVegetarianMeal();
        assertEquals(0, (int) meal2.getCarbonSaved());
    }

    @Test
    public void testGetCategory() {
        assertEquals("Food", meal.getCategory());
    }

    @Test
    public void testSetCategory() {
        meal.setCategory("test");
        assertEquals("test", meal.getCategory());
    }

    @Test
    public void testGetName() {
        assertEquals("Eat Vegetarian Meal", meal.getName());
    }

    @Test
    public void testSetName() {
        meal.setName("testName");
        assertEquals("testName", meal.getName());
    }

    @Test
    public void testAboveAverageToAverage() {
        double result = CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.ABOVE_AVERAGE) / 365.0
                - CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.AVERAGE) / 365.0;
        assertEquals((int) result, (int) meal.aboveAverageToAverage());
    }

    @Test
    public void testAverageToBelowAverage() {
        double result = CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.AVERAGE) / 365.0
                - CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.BELOW_AVERAGE) / 365.0;
        assertEquals((int) result, (int) meal.averageToBelowAverage());
    }

    @Test
    public void testBelowAverageToVegan() {
        double result = CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.BELOW_AVERAGE) / 365.0
                - CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.VEGAN) / 365.0;
        assertEquals((int) result, (int) meal.belowAverageToVegan());
    }

    @Test
    public void testCalculateCarbonSavedFromAboveAverageUser() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setMeatAndDairyConsumption("above average");
        assertEquals((int) meal.aboveAverageToAverage(), (int) meal.calculateCarbonSaved(user));
        user.addActivity(meal);
        assertEquals((int) meal.averageToBelowAverage(), (int) meal.calculateCarbonSaved(user));
        user.addActivity(meal);
        assertEquals((int) meal.belowAverageToVegan(), (int) meal.calculateCarbonSaved(user));
        user.addActivity(meal);
        assertEquals(0, (int)meal.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedFromAverageUser() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setMeatAndDairyConsumption("average");
        assertEquals((int) meal.averageToBelowAverage(), (int) meal.calculateCarbonSaved(user));
        user.addActivity(meal);
        assertEquals((int) meal.belowAverageToVegan(), (int) meal.calculateCarbonSaved(user));
        user.addActivity(meal);
        assertEquals(0, (int)meal.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedFromBelowAverageUser() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setMeatAndDairyConsumption("below average");
        assertEquals((int) meal.belowAverageToVegan(), (int) meal.calculateCarbonSaved(user));
        user.addActivity(meal);
        assertEquals(0, (int)meal.calculateCarbonSaved(user));
    }

    @Test
    public void testCalculateCarbonSavedFromVeganUser() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setMeatAndDairyConsumption("vegan");
        user.addActivity(meal);
        assertEquals(0, (int)meal.calculateCarbonSaved(user));
    }

    @Test
    public  void testTimesPerformedInTheSameDay () {
        assertEquals(0, meal.timesPerformedInTheSameDay(userOne));
        userOne.addActivity(meal);
        userOne.addActivity(null);
        userOne.addActivity(new BuyOrganicFood());
        EatVegetarianMeal meal2 = new EatVegetarianMeal();
        try {
            meal2.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/1999"));
        }catch (Exception e){ }
        userOne.addActivity(meal2);
        assertEquals(1, meal.timesPerformedInTheSameDay(userOne));
    }
}
