package data;

import java.util.ArrayList;

/**
 * Activity: Travel by bike instead of travelling by car.
 * @author Kostas Lyrakis
 */
public class UseBikeInsteadOfCar extends TransportationActivity{
    /**
     * Constructor.
     */
    public UseBikeInsteadOfCar() {
        super();
        this.setName("Use Bike Instead of Car");
    }



    @Override
    public double calculateCarbonSaved(User user) {
        // TODO
        // calculate total carbon saved today
        // calculate daily carbon emissions
        // calculate carbon saved by this activity
        return this.getKilometres();
    }
}
