package data;

import tools.CarbonCalculator;

import java.util.ArrayList;

/**
 * Activity: Travel by bike instead of travelling by car.
 * @author Kostas Lyrakis
 */
public class UseBikeInsteadOfCar extends TransportationActivity {
    /**
     * Constructor.
     */
    public UseBikeInsteadOfCar() {
        super();
        this.setName("Use Bike Instead of Car");
    }

    @Override
    public double calculateCarbonSaved(User user) {
        // calculate total carbon saved today
//        ArrayList<Activity> activitiesPerformedOnTheSameDay =
//                getActivitiesOfTheSameTypePerformedInTheSameDay(user);
        double totalCarbonSavedToday = this.calculateCarbonSavedTodayByTransportationActivities(user);
//        for (Activity activity : activitiesPerformedOnTheSameDay) {
//            totalCarbonSavedToday += activity.getCarbonSaved();
//        }

        // calculate daily carbon emissions
        double dailyCarbonEmissions = calculateDailyCarbonEmissions(user);

        double maxC02SavedByThisActivity;

        // calculate maximum CO2 this activity can save
        if (user.getCarType().equals("small")) {
            maxC02SavedByThisActivity = CarbonCalculator.smallCarEmissions(this.getKilometres());
        } else if (user.getCarType().equals("medium")) {
            maxC02SavedByThisActivity = CarbonCalculator.mediumCarEmissions(this.getKilometres());
        } else if (user.getCarType().equals("large")) {
            maxC02SavedByThisActivity = CarbonCalculator.largeCarEmissions(this.getKilometres());
        } else {
            maxC02SavedByThisActivity = 0;
        }

        // calculate CO2 saved
        if (dailyCarbonEmissions > maxC02SavedByThisActivity + totalCarbonSavedToday) {
            return  maxC02SavedByThisActivity;
        } else {
            return dailyCarbonEmissions - totalCarbonSavedToday;
        }
    }
}
