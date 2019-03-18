package data;

import tools.CarbonCalculator;

/**
 * Activity: Buy locally produced food.
 * @author Kostas Lyrakis
 */
public class BuyLocallyProducedFood extends Activity {
    /**
     * Constructor.
     */
    public BuyLocallyProducedFood() {
        super();
        this.setCategory("Food");
        this.setName("Buy Locally Produced Food");
    }

    /**
     * A user with very little local food consumption
     * now buys as much local food as an average user.
     */
    public double veryLittleToAverage() {
        return CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.VERY_LITTLE) / 365.0
                - CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.AVERAGE) / 365.0;
    }

    /**
     * A user with average local food consumption
     * now buys more locally produced food than the average user.
     */
    public double averageToAboveAverage() {
        return CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.AVERAGE) / 365.0
                - CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.ABOVE_AVERAGE) / 365.0;
    }

    /**
     * A user with above average local food consumption
     * now buys almost all of his/her food from local producers.
     */
    public double aboveAverageToAlmostAll() {
        return CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.ABOVE_AVERAGE) / 365.0
                - CarbonCalculator.foodMilesEmissions(
                CarbonCalculator.LocallyProducedFoodConsumption.ALMOST_ALL) / 365.0;
    }

    /**
     * Calculates the carbon saved by performing this activity.
     * @param user user currently logged in
     */
    @Override
    public double calculateCarbonSaved(User user) {
        String locallyProducedFoodConsumption = user.getLocallyProducedFoodConsumption();
        int timesPerformedInTheSameDay = timesPerformedInTheSameDay(user);

        if (timesPerformedInTheSameDay == 0) {
            if (locallyProducedFoodConsumption.equals("very little")) {
                return veryLittleToAverage();
            } else if (locallyProducedFoodConsumption.equals("average")) {
                return averageToAboveAverage();
            } else if (locallyProducedFoodConsumption.equals("above average")) {
                return aboveAverageToAlmostAll();
            }
        } else if (timesPerformedInTheSameDay == 1) {
            if (locallyProducedFoodConsumption.equals("very little")) {
                return averageToAboveAverage();
            } else if (locallyProducedFoodConsumption.equals("average")) {
                return aboveAverageToAlmostAll();
            }
        } else if (timesPerformedInTheSameDay == 2) {
            if (locallyProducedFoodConsumption.equals("very little")) {
                return aboveAverageToAlmostAll();
            }
        }
        return 0;
    }
}
