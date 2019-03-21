package data;

import java.util.ArrayList;

/**
 * Activity: Travel by bike instead of travelling by car.
 * @author Kostas Lyrakis
 */
public class UseBikeInsteadOfCar extends Activity{
    private int kilometres;
    /**
     * Constructor.
     */
    public UseBikeInsteadOfCar() {
        super();
        this.setCategory("Transportation");
        this.setName("Use Bike Instead of Car");
        this.kilometres = 0;
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
        return this.kilometres;
    }
}
