package data;

import tools.CarbonCalculator;

/**
 * Activity: Buy non-processed food.
 * @author Kostas Lyrakis
 */
public class BuyNonProcessedFood extends Activity {

    /**
     * Constructor.
     */
    public BuyNonProcessedFood() {
        super();
        this.setCategory("Food");
        this.setName("Buy Non-Processed Food");
    }

    /**
     * A user that consumes more processed food than the average one
     * behaves like an average user.
     */
    public double aboveAverageToAverage() {
        return CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.ABOVE_AVERAGE) / 365.0
                - CarbonCalculator.processedFoodEmissions(
                        CarbonCalculator.ProcessedFoodConsumption.AVERAGE) / 365.0;
    }

    /**
     * A user that consumes an average amount of processed food
     * buys less process food than an average user.
     */
    public double averageToBelowAverage() {
        return CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.AVERAGE) / 365.0
                - CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.BELOW_AVERAGE) / 365.0;
    }

    /**
     * A user that buys less processed food than the average user
     * now buys even less processed food.
     */
    public double belowAverageToVeryLittle() {
        return CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.BELOW_AVERAGE) / 365.0
                - CarbonCalculator.processedFoodEmissions(
                CarbonCalculator.ProcessedFoodConsumption.VERY_LITTLE) / 365.0;
    }

    /**
     * Calculates the carbon saved by performing this activity.
     * @param user user currently logged in
     */
    @Override
    public double calculateCarbonSaved(User user) {
        String processedFoodConsumption = user.getProcessedFoodConsumption();
        int timesPerformedInTheSameDay = timesPerformedInTheSameDay(user);

        if (timesPerformedInTheSameDay == 0) {
            if (processedFoodConsumption.equals("above average")) {
                return aboveAverageToAverage();
            } else if (processedFoodConsumption.equals("average")) {
                return averageToBelowAverage();
            } else if (processedFoodConsumption.equals("below average")) {
                return belowAverageToVeryLittle();
            }
        } else if (timesPerformedInTheSameDay == 1) {
            if (processedFoodConsumption.equals("above average")) {
                return averageToBelowAverage();
            } else if (processedFoodConsumption.equals("average")) {
                return belowAverageToVeryLittle();
            }
        } else if (timesPerformedInTheSameDay == 2) {
            if (processedFoodConsumption.equals("above average")) {
                return belowAverageToVeryLittle();
            }
        }
        return 0;
    }

}
