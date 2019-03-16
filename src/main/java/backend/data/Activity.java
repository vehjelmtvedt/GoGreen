package backend.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import frontend.Requests;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used as a superclass for the specific activities a user performs.
 * @author Kostas Lyrakis
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EatVegetarianMeal.class, name = "EatVegetarianMeal"),
        @JsonSubTypes.Type(value = BuyLocallyProducedFood.class, name = "BuyLocallyProducedFood"),
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
            if (activity != null
                    && activity.getClass().getSimpleName()
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
        } catch (Exception e) {
            System.out.println("Activity not logged to user");
        }
    }

}
