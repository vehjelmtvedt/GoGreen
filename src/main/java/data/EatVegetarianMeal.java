package data;

import tools.CarbonCalculator;

/**
 * Activity: eat a vegetarian meal.
 * @author Kostas Lyrakis
 */
public class EatVegetarianMeal extends Activity {

    /**
     * Constructor.
     */
    public EatVegetarianMeal() {
        super();
        this.setCategory("Food");
        this.setName("Eat Vegetarian Meal");
    }


    /**
     * A user with above average meat and dairy consumption behaves like one with average.
     */
    public double aboveAverageToAverage() {
        return CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.ABOVE_AVERAGE) / 365.0
                - CarbonCalculator.meatAndDairyConsumptionEmissions(
                        CarbonCalculator.MeatAndDairyConsumption.AVERAGE) / 365.0;
    }

    /**
     * A user with average meat and dairy consumption behaves like one with below average.
     */
    public double averageToBelowAverage() {
        return CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.AVERAGE) / 365.0
                - CarbonCalculator.meatAndDairyConsumptionEmissions(
                        CarbonCalculator.MeatAndDairyConsumption.BELOW_AVERAGE) / 365.0;
    }

    /**
     * A user with below average meat and dairy consumption behaves like a vegan.
     */
    public double belowAverageToVegan() {
        return CarbonCalculator.meatAndDairyConsumptionEmissions(
                CarbonCalculator.MeatAndDairyConsumption.BELOW_AVERAGE) / 365.0
                - CarbonCalculator.meatAndDairyConsumptionEmissions(
                        CarbonCalculator.MeatAndDairyConsumption.VEGAN) / 365.0;
    }

    /**
     * Calculates the carbon saved by performing this activity.
     * @param user user currently logged in
     */
    @Override
    public double calculateCarbonSaved(User user) {
        String meatAndDairyConsumption = user.getMeatAndDairyConsumption();

        if (meatAndDairyConsumption.equals("above average")) {
            switch (timesPerformedInTheSameDay(user)) {
                case 0:
                    return aboveAverageToAverage();
                case 1:
                    return averageToBelowAverage();
                case 2:
                    return belowAverageToVegan();
                default:
                    return 0;
            }
        } else if (meatAndDairyConsumption.equals("average")) {
            switch (timesPerformedInTheSameDay(user)) {
                case 0:
                    return averageToBelowAverage();
                case 1:
                    return belowAverageToVegan();
                default:
                    return 0;
            }
        } else if (meatAndDairyConsumption.equals("below average")) {
            switch (timesPerformedInTheSameDay(user)) {
                case 0:
                    return belowAverageToVegan();
                default:
                    return 0;
            }
        } else {
            return 0;
        }
    }
}
