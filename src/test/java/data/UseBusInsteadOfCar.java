package data;

import tools.CarbonCalculator;

import java.util.ArrayList;

public class UseBusInsteadOfCar extends TransportationActivity{
    /**
     * Constructor.
     */
    public UseBusInsteadOfCar() {
        super();
        this.setName("Use Bus Instead of Car");
    }

    @Override
    public double calculateCarbonSaved(User user) {
        // calculate total carbon saved today
        ArrayList<Activity> activitiesPerformedOnTheSameDay =
                getActivitiesOfTheSameTypePerformedInTheSameDay(user);
        double totalCarbonSavedToday = 0;
        for (Activity activity : activitiesPerformedOnTheSameDay) {
            totalCarbonSavedToday += activity.getCarbonSaved();
        }

        // calculate daily carbon emissions
        double dailyCarbonEmissions = calculateDailyCarbonEmissions(user);

        double maxC02SavedByThisActivity;

        // calculate maximum CO2 this activity can save
        if (user.getCarType().equals("small")) {
            maxC02SavedByThisActivity = CarbonCalculator.smallCarEmissions(this.getKilometres()) - CarbonCalculator.busEmissions(this.getKilometres());
        } else if (user.getCarType().equals("medium")) {
            maxC02SavedByThisActivity = CarbonCalculator.mediumCarEmissions(this.getKilometres() - CarbonCalculator.busEmissions(this.getKilometres()));
        } else if (user.getCarType().equals("large")) {
            maxC02SavedByThisActivity = CarbonCalculator.largeCarEmissions(this.getKilometres() - CarbonCalculator.busEmissions(this.getKilometres()));
        } else {
            maxC02SavedByThisActivity = 0;
        }

        if (maxC02SavedByThisActivity < 0) {
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
