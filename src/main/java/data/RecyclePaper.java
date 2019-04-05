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

    /**
     * According to carbonindependent.org every user that recycles paper can save on average 70kg
     * of carbon per year. That does not seem accurate enough because it assumes that regardless
     * of a user's spending habits, the amount of carbon saved will be the same. This method needs
     * to be updated in the future. This function calculates daily CO2 saved, so its use should
     * be limited to once per day.
     * @param user currently logged in user
     * @return CO2 saved
     */
    @Override
    public double calculateCarbonSaved(User user) {
        return 70.0 / 365.0;
    }
}
