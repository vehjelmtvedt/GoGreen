package data;

import tools.CarbonCalculator;

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

}
