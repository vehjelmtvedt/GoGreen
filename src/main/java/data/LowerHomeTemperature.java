package data;

import tools.CarbonCalculator;

/**
 * Activity: Lower home temperature.
 * @author Kostas Lyrakis
 */
public class LowerHomeTemperature extends Activity {
    private int degrees;

    /**
     * Constructor.
     */
    public LowerHomeTemperature() {
        this.setCategory("Household");
        this.setName("Lower Home Temperature");
        this.degrees = 0;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    @Override
    public double calculateCarbonSaved(User user) {
        return CarbonCalculator.heatingOilEmissions(
                user.getHeatingOilDailyConsumption()) * degrees * 1.8 / 100;
    }

}
