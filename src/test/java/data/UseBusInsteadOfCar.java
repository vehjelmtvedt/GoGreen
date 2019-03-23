package data;

public class UseBusInsteadOfCar extends TransportationActivity{
    /**
     * Constructor.
     */
    public UseBusInsteadOfCar() {
        super();
        this.setName("Use Bike Instead of Car");
    }

    @Override
    public double calculateCarbonSaved(User user) {
        // TODO
        return 0;
    }
}
