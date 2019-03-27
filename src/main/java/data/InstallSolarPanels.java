package data;

/**
 * Activity: Install solar panels.
 * @author Kostas Lyrakis
 */
public class InstallSolarPanels extends Activity{
    /**
     * Constructor.
     */
    public InstallSolarPanels() {
        this.setCategory("Household");
        this.setName("Install Solar Panels");
    }



    @Override
    public double calculateCarbonSaved(User user) {
        return 0;
    }
}
