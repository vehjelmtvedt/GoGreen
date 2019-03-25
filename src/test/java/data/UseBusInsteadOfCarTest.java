package data;

import org.junit.Test;
import tools.CarbonCalculator;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class UseBusInsteadOfCarTest {
    UseBusInsteadOfCar travel1 = new UseBusInsteadOfCar();

    @Test
    public void testConstructor() {
        assertEquals("Transportation", travel1.getCategory());
        assertEquals("Use Bus Instead of Car", travel1.getName());

    }

    @Test
    public void testCarbonSavedByUserWithSmallCar() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("small");
        user.setDailyCarKilometres(50);
        UseBusInsteadOfCar travel = new UseBusInsteadOfCar();
        travel.setKilometres(50);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) (CarbonCalculator.smallCarEmissions(50) - CarbonCalculator.busEmissions(50)), (int) travel.getCarbonSaved());
        user.addActivity(travel);
        UseBusInsteadOfCar travel2 = new UseBusInsteadOfCar();
        travel2.setKilometres(5);
        travel2.setCarbonSaved(travel2.calculateCarbonSaved(user));
        assertEquals((int) (CarbonCalculator.smallCarEmissions(5) - CarbonCalculator.busEmissions(5)), (int) travel2.calculateCarbonSaved(user));
        user.addActivity(travel2);
        user.addActivity(null);
        user.addActivity(new EatVegetarianMeal());
        UseBusInsteadOfCar travel3 = new UseBusInsteadOfCar();
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
        user.setDailyCarKilometres(100);
        UseBusInsteadOfCar travel = new UseBusInsteadOfCar();
        travel.setKilometres(100);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) (CarbonCalculator.mediumCarEmissions(100) - CarbonCalculator.busEmissions(100)), (int) travel.getCarbonSaved());
        user.addActivity(travel);
        assertEquals(0, (int) travel.calculateCarbonSaved(user));
    }

    @Test
    public void testCarbonSavedByUserWithLargeCar() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("large");
        user.setDailyCarKilometres(20);
        UseBusInsteadOfCar travel = new UseBusInsteadOfCar();
        travel.setKilometres(20);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) (CarbonCalculator.largeCarEmissions(20) - CarbonCalculator.busEmissions(20)), (int) travel.calculateCarbonSaved(user));
        user.addActivity(travel);
        UseBusInsteadOfCar travel2 = new UseBusInsteadOfCar();
        travel2.setKilometres(50);
        travel2.setCarbonSaved(travel2.calculateCarbonSaved(user));
        assertEquals(0, (int) travel2.calculateCarbonSaved(user));
        user.addActivity(travel2);
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
        UseBusInsteadOfCar travel2 = new UseBusInsteadOfCar();
        try {
            travel2.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/1999"));
        }catch (Exception e){ }
        user.addActivity(travel2);
        assertEquals(1, travel1.timesPerformedInTheSameDay(user));
    }

    @Test
    public void testActivityWithMoreKilometresThanUsersDailyKilometres() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("large");
        user.setDailyCarKilometres(20);
        UseBusInsteadOfCar travel = new UseBusInsteadOfCar();
        travel.setKilometres(30);
        assertEquals(0, (int) travel.calculateCarbonSaved(user));
    }
}
