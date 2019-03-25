package data;

import org.junit.Test;
import tools.CarbonCalculator;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class UseBikeInsteadOfCarTest {
    UseBikeInsteadOfCar travel1 = new UseBikeInsteadOfCar();
    @Test
    public void testConstructor() {
        assertEquals("Transportation", travel1.getCategory());
        assertEquals("Use Bike Instead of Car", travel1.getName());
        travel1.setKilometres(2);
        assertEquals(2, travel1.getKilometres());
    }

    @Test
    public void testCalculateDailyCarbonEmissions() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("small");
        user.setDailyCarKilometres(20);
        assertEquals((int) CarbonCalculator.smallCarEmissions(20), (int) travel1.calculateDailyCarEmissions(user));
        user.setCarType("medium");
        assertEquals((int) CarbonCalculator.mediumCarEmissions(20), (int) travel1.calculateDailyCarEmissions(user));
        user.setCarType("large");
        assertEquals((int) CarbonCalculator.largeCarEmissions(20), (int) travel1.calculateDailyCarEmissions(user));
        user.setCarType("I don't own a car");
        assertEquals(0, (int) travel1.calculateDailyCarEmissions(user));
    }

    @Test
    public void testCarbonSavedByUserWithSmallCar() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("small");
        user.setDailyCarKilometres(20);
        UseBikeInsteadOfCar travel = new UseBikeInsteadOfCar();
        travel.setKilometres(15);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) CarbonCalculator.smallCarEmissions(15), (int) travel.calculateCarbonSaved(user));
        user.addActivity(travel);
        UseBikeInsteadOfCar travel2 = new UseBikeInsteadOfCar();
        travel2.setKilometres(5);
        travel2.setCarbonSaved(travel2.calculateCarbonSaved(user));
        assertEquals((int) CarbonCalculator.smallCarEmissions(5), (int) travel2.calculateCarbonSaved(user));
        user.addActivity(travel2);
        user.addActivity(null);
        user.addActivity(new EatVegetarianMeal());
        UseBikeInsteadOfCar travel3 = new UseBikeInsteadOfCar();
        try {
            travel3.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/1999"));
        }catch (Exception e){ }
        user.addActivity(travel3);
        assertEquals(0, (int) travel.calculateCarbonSaved(user));
    }

    @Test
    public void testCarbonSavedByUserWithMediumCar() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("medium");
        user.setDailyCarKilometres(20);
        UseBikeInsteadOfCar travel = new UseBikeInsteadOfCar();
        travel.setKilometres(20);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) CarbonCalculator.mediumCarEmissions(20), (int) travel.calculateCarbonSaved(user));
        user.addActivity(travel);
        assertEquals(0, (int) travel.calculateCarbonSaved(user));
    }

    @Test
    public void testCarbonSavedByUserWithLargeCar() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("large");
        user.setDailyCarKilometres(20);
        UseBikeInsteadOfCar travel = new UseBikeInsteadOfCar();
        travel.setKilometres(20);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) CarbonCalculator.largeCarEmissions(20), (int) travel.calculateCarbonSaved(user));
        user.addActivity(travel);
        assertEquals(0, (int) travel.calculateCarbonSaved(user));

    }

    @Test
    public void testCarbonSavedByUserWithoutCar() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("no car");
        user.setDailyCarKilometres(0);
        travel1.setKilometres(15);
        assertEquals(0, (int) travel1.calculateCarbonSaved(user));
    }

    @Test
    public  void testTimesPerformedInTheSameDay () {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("large");
        assertEquals(0, travel1.timesPerformedInTheSameDay(user));
        user.addActivity(travel1);
        user.addActivity(null);
        user.addActivity(new EatVegetarianMeal());
        UseBikeInsteadOfCar travel2 = new UseBikeInsteadOfCar();
        try {
            travel2.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/1999"));
        }catch (Exception e){ }
        user.addActivity(travel2);
        assertEquals(1, travel1.timesPerformedInTheSameDay(user));
    }
}
