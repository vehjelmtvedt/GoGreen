package data;

public class RecyclePaper extends Activity {

    /**
     * Constructor.
     */
    public RecyclePaper() {
        super();
        this.setName("Recycle Paper");
        this.setCategory("Household");
    }

    @Override
    public double calculateCarbonSaved(User user) {
        return 0;
    }
}
