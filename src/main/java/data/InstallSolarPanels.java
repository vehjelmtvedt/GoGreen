package data;

import tools.CarbonCalculator;

/**
 * Activity: Install solar panels.
 * @author Kostas Lyrakis
 */
public class InstallSolarPanels extends Activity {
    private int kwhSavedPerYear;

    /**
     * Constructor.
     */
    public InstallSolarPanels() {
        this.setCategory("Household");
        this.setName("Install Solar Panels");
        this.kwhSavedPerYear = 0;
    }

    public int getKwhSavedPerYear() {
        return kwhSavedPerYear;
    }

    public void setKwhSavedPerYear(int kwhSavedPerYear) {
        this.kwhSavedPerYear = kwhSavedPerYear;
    }

    /**
     * Installing solar panels can't be executed more than once per user.
     * Therefore this method calculates a user's daily savings assuming that
     * after the activity is performed, the user will rely on solar panels to cover
     * a percentage of his/her energy needs forever.
     * @param user currently logged in user
     * @return user's daily CO2 savings.
     */
    @Override
    public double calculateCarbonSaved(User user) {
        // TODO
        // update the function so that the amount saved by the solar panels does not exceed the amount
        // of the user's daily consumption in electricity
        return CarbonCalculator.electricityEmissions(this.getKwhSavedPerYear()) / 365.0;
    }
}
