package data;

import java.util.ArrayList;

/**
 * Activity: Travel by bike instead of travelling by car.
 * @author Kostas Lyrakis
 */
public class UseBikeInsteadOfCar extends TransportationActivity{
    private int kilometres;
    /**
     * Constructor.
     */
    public UseBikeInsteadOfCar() {
        this.setName("Use Bike Instead of Car");
    }

    public void setKilometres(int kilometres) {
        this.kilometres = kilometres;
    }

    public int getKilometres() {
        return kilometres;
    }

    public ArrayList<UseBikeInsteadOfCar> getSimilarActivitiesOnTheSameDay(User user) {
        // TODO
        ArrayList<UseBikeInsteadOfCar> result = new ArrayList<UseBikeInsteadOfCar>();

        return result;
    }

    @Override
    public double calculateCarbonSaved(User user) {
        // TODO
        // calculate total carbon saved today
        // calculate daily carbon emmisions
        // calculate cargon saved by this activity
        return this.kilometres;
    }
}
