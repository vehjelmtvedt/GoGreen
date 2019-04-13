package data;

import tools.CarbonCalculator;

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
        double totalCarbonSavedToday =
                this.calculateCarbonSavedTodayByTransportationActivities(user);

        // calculate daily carbon emissions
        double dailyCarEmissions = calculateDailyCarEmissions(user);

        // calculate total kilometres travelled today
        double totalKilometresTraveledToday = this.calculateTotalKilometresTravelledToday(user);


        // calculate maximum CO2 this activity can save
        double maxC02SavedByThisActivity;
        switch (user.getCarType()) {
            case "small":
                maxC02SavedByThisActivity = CarbonCalculator
                        .smallCarEmissions(this.getKilometres());
                break;
            case "medium":
                maxC02SavedByThisActivity = CarbonCalculator
                        .mediumCarEmissions(this.getKilometres());
                break;
            case "large":
                maxC02SavedByThisActivity = CarbonCalculator
                        .largeCarEmissions(this.getKilometres());
                break;
            default:
                maxC02SavedByThisActivity = 0;
                break;
        }

        // The user can save carbon only for the daily kilometres he/she travels by car
        if (totalKilometresTraveledToday >= user.getDailyCarKilometres()) {
            return 0;
        }

        // calculate CO2 saved
        if (dailyCarEmissions > maxC02SavedByThisActivity + totalCarbonSavedToday) {
            return  maxC02SavedByThisActivity;
        } else {
            return dailyCarEmissions - totalCarbonSavedToday;
        }
    }
}
