package data;

import java.util.ArrayList;

/**
 * Activity: Travel by bike instead of travelling by car.
 * @author Kostas Lyrakis
 */
public class UseBikeInsteadOfCar extends TransportationActivity{
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
        ArrayList<Activity> activitiesPerformedOnTheSameDay = getActivitiesOfTheSameTypePerformedInTheSameDay(user);
        double totalCarbonSavedToday = 0;
        for(Activity activity: activitiesPerformedOnTheSameDay) {
            totalCarbonSavedToday += activity.getCarbonSaved();
        }

        // calculate daily carbon emissions
        double dailyCarbonEmissions = calculateDailyCarbonEmissions(user);

        if (user.getCarType().equals("small")) {
            // TODO
            return 0;
        } else if (user.getCarType().equals("medium")) {
            // TODO
            return 0;
        } else if (user.getCarType().equals("large")) {
            // TODO
            return 0;
        } else {
            return 0;
        }
    }
}
