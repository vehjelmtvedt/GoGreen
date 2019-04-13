package data;

import tools.CarbonCalculator;
import tools.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Super class for Transportation activities.
 * @author Kostas Lyrakis
 */
public abstract class TransportationActivity extends Activity {
    private int kilometres;

    /**
     * Constructor.
     */
    TransportationActivity() {
        super();
        this.setCategory("Transportation");
        this.kilometres = 0;

    }

    public void setKilometres(int kilometres) {
        this.kilometres = kilometres;
    }

    public int getKilometres() {
        return kilometres;
    }

    /**
     * Calculates a user's daily car emissions.
     * @param user currently logged in user
     * @return user's daily car emissions in kg.
     */
    public double calculateDailyCarEmissions(User user) {

        switch (user.getCarType()) {
            case "small":
                return CarbonCalculator.smallCarEmissions(user.getDailyCarKilometres());
            case "medium":
                return CarbonCalculator.mediumCarEmissions(user.getDailyCarKilometres());
            case "large":
                return CarbonCalculator.largeCarEmissions(user.getDailyCarKilometres());
            default:
                return 0;
        }
    }

    /**
     * Calculates the amount of CO2 a user has saved today
     * by doing transportation activities.
     * @param user currently logged in user
     * @return user's savings in kg
     */
    double calculateCarbonSavedTodayByTransportationActivities(User user) {
        Date currentDate = DateUtils.instance.dateToday();
        String currentMonth = currentDate.toString().split(" ")[1];
        String currentDay = currentDate.toString().split(" ")[2];
        String currentYear = currentDate.toString().split(" ")[5];
        ArrayList<TransportationActivity> transportationActivities
                = new ArrayList<>();

        double result = 0;

        for (Activity activity : user.getActivities()) {
            if (activity instanceof TransportationActivity) {
                String dateNow = currentMonth + currentDay + currentYear;
                if (dateNow.equals(activity.getDate().toString().split(" ")[1]
                        + activity.getDate().toString().split(" ")[2]
                        + activity.getDate().toString().split(" ")[5])) {
                    transportationActivities.add((TransportationActivity) activity);
                }
            }
        }

        for (TransportationActivity activity: transportationActivities) {
            result += activity.getCarbonSaved();
        }

        return result;
    }

    /**
     * Calculates the total amount of kilometres a user has travelled today.
     * @param user currently logged in user
     * @return kilometres (int)
     */
    int calculateTotalKilometresTravelledToday(User user) {
        Date currentDate = DateUtils.instance.dateToday();
        String currentMonth = currentDate.toString().split(" ")[1];
        String currentDay = currentDate.toString().split(" ")[2];
        String currentYear =
                currentDate.toString().split(" ")[5];
        ArrayList<TransportationActivity> transportationActivities
                = new ArrayList<>();

        int result = 0;

        for (Activity activity : user.getActivities()) {
            if (activity instanceof TransportationActivity) {
                String dateNow = currentMonth + currentDay + currentYear;
                if (dateNow.equals(activity.getDate().toString().split(" ")[1]
                        + activity.getDate().toString().split(" ")[2]
                        + activity.getDate().toString().split(" ")[5])) {
                    transportationActivities.add((TransportationActivity) activity);
                }
            }
        }

        for (TransportationActivity activity: transportationActivities) {
            result += activity.getKilometres();
        }

        return result;
    }
}
