package data;

public class RecyclePlastic extends Activity {

    /**
     * Constructor.
     */
    public RecyclePlastic() {
        super();
        this.setName("Recycle Plastic");
        this.setCategory("Household");
    }

    @Override
    public double calculateCarbonSaved(User user) {
        return 0;
    }
}
