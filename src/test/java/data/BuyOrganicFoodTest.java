package data;

import data.BuyOrganicFood;
import data.EatVegetarianMeal;
import data.User;
import org.junit.Test;
import tools.CarbonCalculator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BuyOrganicFoodTest {
    BuyOrganicFood food = new BuyOrganicFood();
    User userOne = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");

    @Test
    public void testConstructor() {
        BuyOrganicFood food = new BuyOrganicFood();
        assertEquals("Food", food.getCategory());
        assertEquals("Buy Organic Food", food.getName());
    }

    @Test
    public void testSetDate() {
        Date dateNow = Calendar.getInstance().getTime();
        food.setDate(dateNow);
        assertEquals(dateNow.toString(), food.getDate().toString());
    }

    @Test
    public void testNoneToSome() {
        int result = (int) (CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.NONE) / 365.0
                - CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.SOME) / 365.0);
        assertEquals(result, (int) food.nonetoSome());
    }

    @Test
    public void testSomeToMost() {
        int result = (int) (CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.SOME) / 365.0
                - CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.MOST) / 365.0);
        assertEquals(result, (int) food.someToMost());
    }

    @Test
    public void testMostToAll() {
        int result = (int) (CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.MOST) / 365.0
                - CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.ALL) / 365.0);
        assertEquals(result, (int) food.mostToAll());
    }

    @Test
    public  void testTimesPerformedInTheSameDay () {
        assertEquals(0, food.timesPerformedInTheSameDay(userOne));
        userOne.addActivity(food);
        userOne.addActivity(null);
        userOne.addActivity(new EatVegetarianMeal());
        BuyOrganicFood food2 = new BuyOrganicFood();
        try {
            food2.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/1999"));
        }catch (Exception e){ }
        userOne.addActivity(food2);
        assertEquals(1, food.timesPerformedInTheSameDay(userOne));
    }

    @Test
    public void testCarbonSavedByUserThatDoesNotConsumeAnyOrganicFood() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setOrganicFoodConsumption("none");
        assertEquals((int) food.nonetoSome(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.someToMost(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.mostToAll(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

    @Test
    public void testCarbonSavedByUserThatConsumesSomeOrganicFood() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setOrganicFoodConsumption("some");
        assertEquals((int) food.someToMost(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals((int) food.mostToAll(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

    @Test
    public void testCarbonSavedByUserThatMainlyConsumesOrganicFood() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setOrganicFoodConsumption("most");
        assertEquals((int) food.mostToAll(), (int) food.calculateCarbonSaved(user));
        user.addActivity(food);
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }

    @Test
    public void testCarbonSavedByUserThatConsumesOnlyOrganicFood() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setOrganicFoodConsumption("all");
        assertEquals(0, (int) food.calculateCarbonSaved(user));
    }
}
