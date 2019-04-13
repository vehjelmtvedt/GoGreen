package data;

import tools.CarbonCalculator;

/**
 * Activity: Travel by bus instead of travelling by car.
 * @author Kostas Lyrakis
 */
public class UseBusInsteadOfCar extends TransportationActivity {
    /**
     * Constructor.
     */
    public UseBusInsteadOfCar() {
        super();
        this.setName("Use Bus Instead of Car");
    }

    @Override
    public double calculateCarbonSaved(User user) {
        // calculate total kilometres travelled today
        double totalKilometresTraveledToday = this.calculateTotalKilometresTravelledToday(user);

        // calculate maximum CO2 this activity can save
        double maxC02SavedByThisActivity;
        switch (user.getCarType()) {
            case "small":
                maxC02SavedByThisActivity = CarbonCalculator.smallCarEmissions(
                        this.getKilometres()) - CarbonCalculator.busEmissions(this.getKilometres());
                break;
            case "medium":
                maxC02SavedByThisActivity = CarbonCalculator.mediumCarEmissions(
                        this.getKilometres()) - CarbonCalculator.busEmissions(this.getKilometres());
                break;
            case "large":
                maxC02SavedByThisActivity = CarbonCalculator.largeCarEmissions(
                        this.getKilometres()) - CarbonCalculator.busEmissions(this.getKilometres());
                break;
            default:
                maxC02SavedByThisActivity = 0;
                break;
        }

        // The user can save carbon only for the daily kilometres he/she travels by car
        if (totalKilometresTraveledToday >= user.getDailyCarKilometres()) {
            return 0;
        }

        if (this.getKilometres() > user.getDailyCarKilometres()) {
            return 0;
        }

        return maxC02SavedByThisActivity;
    }
}
