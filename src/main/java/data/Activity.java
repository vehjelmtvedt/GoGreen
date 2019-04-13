package data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.web.client.ResourceAccessException;
import tools.DateUtils;
import tools.Requests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This class is used as a superclass for the specific activities a user performs.
 *
 * @author Kostas Lyrakis
 */
@SuppressWarnings("UnusedAssignment")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EatVegetarianMeal.class, name = "EatVegetarianMeal"),
        @JsonSubTypes.Type(value = BuyLocallyProducedFood.class, name = "BuyLocallyProducedFood"),
        @JsonSubTypes.Type(value = BuyNonProcessedFood.class, name = "BuyNonProcessedFood"),
        @JsonSubTypes.Type(value = BuyOrganicFood.class, name = "BuyOrganicFood"),
        @JsonSubTypes.Type(value = UseBikeInsteadOfCar.class, name = "UseBikeInsteadOfCar"),
        @JsonSubTypes.Type(value = UseBusInsteadOfCar.class, name = "UseBusInsteadOfCar"),
        @JsonSubTypes.Type(value = UseTrainInsteadOfCar.class, name = "UseTrainInsteadOfCar"),
        @JsonSubTypes.Type(value = InstallSolarPanels.class, name = "InstallSolarPanels"),
        @JsonSubTypes.Type(value = LowerHomeTemperature.class, name = "LowerHomeTemperature"),
        @JsonSubTypes.Type(value = RecyclePaper.class, name = "RecyclePaper"),
        @JsonSubTypes.Type(value = RecyclePlastic.class, name = "RecyclePlastic"),
    })
public abstract class Activity {
    private Date date;
    private double carbonSaved;
    private String name;
    private String category;

    Activity() {
        this.date = DateUtils.instance.dateToday();
        this.carbonSaved = 0;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCarbonSaved() {
        return carbonSaved;
    }

    public void setCarbonSaved(double carbonSaved) {
        // keep only 3 decimal places
        this.carbonSaved = ((int)(carbonSaved * 1000)) / 1000.0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    protected abstract double calculateCarbonSaved(User user);

    /**
     * calculates how many times on the same day the user has performed this activity.
     *
     * @param user user currently logged in
     */
    public int timesPerformedInTheSameDay(User user) {
        return getActivitiesOfTheSameTypePerformedInTheSameDay(user).size();
    }

    /**
     * Creates an array list that contains the activities of the same type performed on the same day.
     *
     * @param user currently logged in user
     * @return ArrayList
     */
    private ArrayList<Activity> getActivitiesOfTheSameTypePerformedInTheSameDay(User user) {
        ArrayList<Activity> result = new ArrayList<>();
        Date currentDate = DateUtils.instance.dateToday();
        String currentMonth = currentDate.toString().split(" ")[1];
        String currentDay = currentDate.toString().split(" ")[2];
        String currentYear = currentDate.toString().split(" ")[5];

        for (Activity activity : user.getActivities()) {
            if (activity != null && activity.getClass().getSimpleName()
                    .equals(this.getClass().getSimpleName())) {
                String dateNow = currentMonth + currentDay + currentYear;
                if (dateNow.equals(activity.getDate().toString().split(" ")[1]
                        + activity.getDate().toString().split(" ")[2]
                        + activity.getDate().toString().split(" ")[5])) {
                    result.add(activity);
                }
            }
        }

        return result;
    }

    private static Comparator<Activity> getDateComparator() {
        return Comparator.comparing(Activity::getDate);
    }

    private static Comparator<Activity> getCarbonSavedComparator() {
        return Comparator.comparingDouble(Activity::getCarbonSaved);
    }

    static Comparator<Activity> getClassComparator() {
        return Comparator.comparing(o -> o.getClass().getName());
    }

    private static List<Activity> sortHelper(List<Activity> activityList,
                                             Comparator<Activity> comparator) {
        ArrayList<Activity> sortedList = new ArrayList<>(activityList);
        sortedList.sort(comparator);
        return sortedList;
    }

    /**
     * .
     * Sorts Activity List by Date
     *
     * @param activityList - List to sort
     * @return Activity List sorted by Date
     */
    public static List<Activity> sortByDate(List<Activity> activityList) {
        return sortHelper(activityList, getDateComparator());
    }

    /**
     * .
     * Sorts Activity List by Carbon Saved
     *
     * @param activityList - List to sort
     * @return Activity List sorted by Carbon Saved
     */
    public static List<Activity> sortByCarbonSaved(List<Activity> activityList) {
        return sortHelper(activityList, getCarbonSavedComparator());
    }

    /**
     * .
     * Sorts Activity List by Class
     *
     * @param activityList - List to sort
     * @return Activity List sorted by Class
     */
    public static List<Activity> sortByClass(List<Activity> activityList) {
        return sortHelper(activityList, getClassComparator());
    }

    /**
     * .
     * Returns the sum of activity carbon saved
     *
     * @param activityList - activities list
     * @return sum of CO2 by all activities in the list
     */
    public static double getSum(List<Activity> activityList) {
        if (activityList == null) {
            return 0;
        }

        double sum = 0.0;

        for (Activity a : activityList) {
            sum += a.getCarbonSaved();
        }

        return sum;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Activity activity = (Activity) object;

        return Double.compare(activity.carbonSaved, carbonSaved) == 0
                && Objects.equals(date, activity.date);
    }

    /**
     * performs the activity and updates the user object.
     *
     * @param user user currently logged in
     */
    public void performActivity(User user, Requests requests) {
        this.setCarbonSaved(this.calculateCarbonSaved(user));
        user.setTotalCarbonSaved(user.getTotalCarbonSaved() + this.calculateCarbonSaved(user));
        // update logged in user for the gui
        user.addActivity(this);
        // update user in the database
        try {
            user = requests.addActivityRequest(this, user.getUsername());


        } catch (ResourceAccessException e) {
            System.out.println("Activity was not added to the database");
            e.printStackTrace();
        }
    }
}
