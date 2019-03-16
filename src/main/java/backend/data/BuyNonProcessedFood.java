package backend.data;

/**
 * Activity: Buy non-processed food.
 * @author Kostas Lyrakis
 */
public class BuyNonProcessedFood extends Activity {

    /**
     * Constructor.
     */
    public BuyNonProcessedFood () {
        super();
        this.setCategory("Food");
        this.setName("Buy non-processed food");
    }

    @Override
    public double calculateCarbonSaved(User user) {
        // TODO
        return 0;
    }
}
