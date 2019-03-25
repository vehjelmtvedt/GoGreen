package data;

import org.junit.Test;
import tools.CarbonCalculator;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class UseTrainInsteadOfCarTest {
    UseTrainInsteadOfCar travel1 = new UseTrainInsteadOfCar();
    @Test
    public void testConstructor()  {
        assertEquals("Transportation", travel1.getCategory());
        assertEquals("Use Train Instead of Car", travel1.getName());
    }

    @Test
    public void testCarbonSavedByUserWithSmallCar() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("small");
        user.setDailyCarKilometres(100);
        UseTrainInsteadOfCar travel = new UseTrainInsteadOfCar();
        travel.setKilometres(100);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) (CarbonCalculator.smallCarEmissions(100) - CarbonCalculator.trainEmissions(100)), (int) travel.getCarbonSaved());
        user.addActivity(travel);
        UseTrainInsteadOfCar travel2 = new UseTrainInsteadOfCar();
        travel2.setKilometres(5);
        travel2.setCarbonSaved(travel2.calculateCarbonSaved(user));
        assertEquals((int) (CarbonCalculator.smallCarEmissions(5) - CarbonCalculator.busEmissions(5)), (int) travel2.calculateCarbonSaved(user));
        user.addActivity(travel2);
        user.addActivity(null);
        user.addActivity(new EatVegetarianMeal());
        UseTrainInsteadOfCar travel3 = new UseTrainInsteadOfCar();
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
        user.setDailyCarKilometres(200);
        UseTrainInsteadOfCar travel = new UseTrainInsteadOfCar();
        travel.setKilometres(200);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) (CarbonCalculator.mediumCarEmissions(200) - CarbonCalculator.trainEmissions(200)), (int) travel.getCarbonSaved());
        user.addActivity(travel);
        assertEquals(0, (int) travel.calculateCarbonSaved(user));
    }

    @Test
    public void testCarbonSavedByUserWithLargeCar() {
        User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
        user.setCarType("large");
        user.setDailyCarKilometres(500);
        UseTrainInsteadOfCar travel = new UseTrainInsteadOfCar();
        travel.setKilometres(500);
        travel.setCarbonSaved(travel.calculateCarbonSaved(user));
        assertEquals((int) (CarbonCalculator.largeCarEmissions(500) - CarbonCalculator.trainEmissions(500)), (int) travel.calculateCarbonSaved(user));
        user.addActivity(travel);
        UseTrainInsteadOfCar travel2 = new UseTrainInsteadOfCar();
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
        UseTrainInsteadOfCar travel2 = new UseTrainInsteadOfCar();
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
        UseTrainInsteadOfCar travel = new UseTrainInsteadOfCar();
        travel.setKilometres(30);
        assertEquals(0, (int) travel.calculateCarbonSaved(user));
    }

}
