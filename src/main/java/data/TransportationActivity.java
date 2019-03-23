package data;

import tools.CarbonCalculator;

import java.util.ArrayList;
import java.util.Calendar;
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
    public TransportationActivity() {
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

    public double calculateDailyCarbonEmissions(User user) {

        if (user.getCarType().equals("small")) {
            return CarbonCalculator.smallCarEmissions(user.getDailyCarKilometres());
        } else if (user.getCarType().equals("medium")){
            return CarbonCalculator.mediumCarEmissions(user.getDailyCarKilometres());
        } else if (user.getCarType().equals("large")) {
            return CarbonCalculator.largeCarEmissions(user.getDailyCarKilometres());
        } else {
            return 0;
        }
    }

    public double calculateCarbonSavedTodayByTransportationActivities(User user) {
        Date currentDate = Calendar.getInstance().getTime();
        String currentMonth = currentDate.toString().split(" ")[1];
        String currentDay = currentDate.toString().split(" ")[2];
        String currentYear = currentDate.toString().split(" ")[5];
        ArrayList<TransportationActivity> transportationActivities = new ArrayList<TransportationActivity>();

        double result = 0;

        for (Activity activity : user.getActivities()) {
            if (activity != null && activity instanceof TransportationActivity) {
                String dateNow = currentMonth + currentDay + currentYear;
                if (dateNow.equals(activity.getDate().toString().split(" ")[1]
                        + activity.getDate().toString().split(" ")[2]
                        + activity.getDate().toString().split(" ")[5])) {
                    transportationActivities.add((TransportationActivity) activity);
                }
            }
        }

        for(TransportationActivity activity: transportationActivities) {
            result += activity.getCarbonSaved();
        }

        return result;
    }
}
