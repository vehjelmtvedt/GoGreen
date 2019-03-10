package backend.data;

import tools.CarbonCalculator;

import java.util.Calendar;
import java.util.Date;

/**
 * Activity: eat a vegetarian meal.
 * @author Kostas Lyrakis
 */
public class EatVegetarianMeal extends Activity {

    /**
     * calculates how many times on the same day the user has performed this activity.
     * @param user user currently logged in
     */
    public int timesPerformedInTheSameDay(User user) {
        Date currentDate = Calendar.getInstance().getTime();
        String currentMonth = currentDate.toString().split(" ")[1];
        String currentDay = currentDate.toString().split(" ")[2];
        int result = 0;
        for (Activity activity : user.getActivities()) {
            if (activity instanceof EatVegetarianMeal
                    && activity.getDate().toString().split(" ")[1].equals(currentMonth)
                    && activity.getDate().toString().split(" ")[2].equals(currentDay)) {
                result++;
            }
        }
        return result;
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
     * @return
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
    public double calculateCarbonSaved(User user) {
        String meatAndDairyConsumption = user.getMeatAndDairyConsumption();
        switch (meatAndDairyConsumption) {
            case "above average":
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
            case "average" :
                switch (timesPerformedInTheSameDay(user)) {
                    case 0:
                        return averageToBelowAverage();
                    case 1:
                        return belowAverageToVegan();
                    default:
                        return 0;
                }
            case "below average" :
                switch (timesPerformedInTheSameDay(user)) {
                    case 0:
                        return belowAverageToVegan();
                    default:
                        return 0;
                }
            default:
                return 0;
        }
    }

    /**
     * performs the activity and updates the user object.
     * @param user user currently logged in
     */
    public void performActivity(User user) {
        this.setCarbonSaved(this.calculateCarbonSaved(user));
        user.setTotalCarbonSaved(user.getTotalCarbonSaved() + this.calculateCarbonSaved(user));
        user.addActivity(this);
    }

}
