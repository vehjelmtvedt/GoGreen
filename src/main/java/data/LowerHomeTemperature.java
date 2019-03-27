package data;

/**
 * Activity: Lower home temperature.
 * @author Kostas Lyrakis
 */
public class LowerHomeTemperature extends Activity {
    /**
     * Constructor.
     */
    public LowerHomeTemperature(){
        this.setCategory("Household");
        this.setName("Lower Home Temperature");
    }

    @Override
    public double calculateCarbonSaved(User user) {
        return 0;
    }
}
