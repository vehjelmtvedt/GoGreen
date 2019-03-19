package data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.web.client.HttpClientErrorException;
import tools.Requests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * This class is used as a superclass for the specific activities a user performs.
 * @author Kostas Lyrakis
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EatVegetarianMeal.class, name = "EatVegetarianMeal"),
        @JsonSubTypes.Type(value = BuyLocallyProducedFood.class, name = "BuyLocallyProducedFood"),
        @JsonSubTypes.Type(value = BuyNonProcessedFood.class, name = "BuyNonProcessedFood"),
        @JsonSubTypes.Type(value = BuyOrganicFood.class, name = "BuyOrganicFood"),
    })
public abstract class Activity {
    private Date date;
    private double carbonSaved;
    private String name;
    private String category;

    public Activity() {
        this.date = Calendar.getInstance().getTime();
        this.carbonSaved = 0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCarbonSaved() {
        return carbonSaved;
    }

    public void setCarbonSaved(double carbonSaved) {
        this.carbonSaved = carbonSaved;
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

    public abstract double calculateCarbonSaved(User user);

    /**
     * calculates how many times on the same day the user has performed this activity.
     * @param user user currently logged in
     */
    public int timesPerformedInTheSameDay(User user) {
        Date currentDate = Calendar.getInstance().getTime();
        String currentMonth = currentDate.toString().split(" ")[1];
        String currentDay = currentDate.toString().split(" ")[2];
        String currentYear = currentDate.toString().split(" ")[5];

        int result = 0;
        for (Activity activity : user.getActivities()) {
            if (activity != null && activity.getClass().getSimpleName()
                    .equals(this.getClass().getSimpleName())) {
                String dateNow = currentMonth + currentDay + currentYear;
                if (dateNow.equals(activity.getDate().toString().split(" ")[1]
                        + activity.getDate().toString().split(" ")[2]
                        + activity.getDate().toString().split(" ")[5])) {
                    result++;
                }
            }
        }
        return result;
    }
    
    protected static Comparator<Activity> getDateComparator() {
        return new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }

    private static Comparator<Activity> getCarbonSavedComparator() {
        return new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return Double.compare(o1.getCarbonSaved(), o2.getCarbonSaved());
            }
        };
    }

    static Comparator<Activity> getClassComparator() {
        return new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getClass().getName().compareTo(o2.getClass().getName());
            }
        };
    }

    private static List<Activity> sortHelper(List<Activity> activityList,
                                             Comparator<Activity> comparator) {
        ArrayList<Activity> sortedList = new ArrayList<>(activityList);
        sortedList.sort(comparator);
        return sortedList;
    }

    /**.
     * Sorts Activity List by Date
     * @param activityList - List to sort
     * @return Activity List sorted by Date
     */
    public static List<Activity> sortByDate(List<Activity> activityList) {
        return sortHelper(activityList, getDateComparator());
    }

    /**.
     * Sorts Activity List by Carbon Saved
     * @param activityList - List to sort
     * @return Activity List sorted by Carbon Saved
     */
    public static List<Activity> sortByCarbonSaved(List<Activity> activityList) {
        return sortHelper(activityList, getCarbonSavedComparator());
    }

    /**.
     * Sorts Activity List by Class
     * @param activityList - List to sort
     * @return Activity List sorted by Class
     */
    public static List<Activity> sortByClass(List<Activity> activityList) {
        return sortHelper(activityList, getClassComparator());
    }

    /**
     * performs the activity and updates the user object.
     * @param user user currently logged in
     */
    public void performActivity(User user) {
        this.setCarbonSaved(this.calculateCarbonSaved(user));
        user.setTotalCarbonSaved(user.getTotalCarbonSaved() + this.calculateCarbonSaved(user));
        // update logged in user for the gui
        user.addActivity(this);
        // update user in the database
        try {
            user = Requests.addActivityRequest(this, user.getUsername());
        } catch (HttpClientErrorException e) {
            System.out.println("Activity was not added to the database");
            System.out.println(e.fillInStackTrace());
        }
    }
}