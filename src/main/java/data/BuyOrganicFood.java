package data;

import tools.CarbonCalculator;

/**
 * Activity: Buy organic food.
 * @author Kostas Lyrakis
 */
public class BuyOrganicFood extends Activity {

    public BuyOrganicFood() {
        this.setCategory("Food");
        this.setName("Buy Organic Food");
    }


    /**
     * A user that never used to buy organic food,
     * now bought some.
     */
    public double nonetoSome() {
        return CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.NONE) / 365.0
                - CarbonCalculator.nonOrganicFoodEmissions(
                        CarbonCalculator.OrganicFoodConsumption.SOME) / 365.0;
    }

    /**
     * A user that used to buy some organic food,
     * now buys mainly organic products.
     */
    public double someToMost() {
        return CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.SOME) / 365.0
                - CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.MOST) / 365.0;
    }

    /**
     * A user that most of his food used to be organic,
     * now buys only organic products.
     */
    public double mostToAll() {
        return CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.MOST) / 365.0
                - CarbonCalculator.nonOrganicFoodEmissions(
                CarbonCalculator.OrganicFoodConsumption.ALL) / 365.0;
    }

    /**
     * Calculates the carbon saved by performing this activity.
     * @param user user currently logged in
     */
    @Override
    public double calculateCarbonSaved(User user) {
        String organicFoodConsumption = user.getOrganicFoodConsumption();
        int timesPerformedInTheSameDay = timesPerformedInTheSameDay(user);

        if (timesPerformedInTheSameDay == 0) {
            if (organicFoodConsumption.equals("none")) {
                return nonetoSome();
            } else if (organicFoodConsumption.equals("some")) {
                return someToMost();
            } else if (organicFoodConsumption.equals("most")) {
                return mostToAll();
            }
        } else if (timesPerformedInTheSameDay == 1) {
            if (organicFoodConsumption.equals("none")) {
                return someToMost();
            } else if (organicFoodConsumption.equals("some")) {
                return mostToAll();
            }
        } else if (timesPerformedInTheSameDay == 2) {
            if (organicFoodConsumption.equals("none")) {
                return mostToAll();
            }
        }
        return 0;
    }
}
