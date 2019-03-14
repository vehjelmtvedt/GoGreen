package backend.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import frontend.Requests;

import java.util.Calendar;
import java.util.Date;

/**
 * This class is used as a superclass for the specific activities a user performs.
 * @author Kostas Lyrakis
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EatVegetarianMeal.class, name = "EatVegetarianMeal"),
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

    public abstract int timesPerformedInTheSameDay(User user);

    /**
     * performs the activity and updates the user object.
     * @param user user currently logged in
     */
    public void performActivity(User user) {
        this.setCarbonSaved(this.calculateCarbonSaved(user));
        user.setTotalCarbonSaved(user.getTotalCarbonSaved() + this.calculateCarbonSaved(user));
        user = Requests.addActivityRequest(this, user.getUsername());
        System.out.println("it works!");
        // TODO
        // connect with database
    }

}
