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

    /**
     * According to carbonindependent.org every user that recycles plastic can save on average 140kg
     * of carbon per year. That does not seem accurate enough because it assumes that regardless
     * of a user's spending habits, the amount of carbon saved will be the same. This method needs
     * to be updated in the future. This function calculates daily CO2 saved, so its use should
     * be limited to once per day.
     * @param user currently logged in user
     * @return daily CO2 saved.
     */
    @Override
    public double calculateCarbonSaved(User user) {
        return 140.0 / 365.0;
    }
}
