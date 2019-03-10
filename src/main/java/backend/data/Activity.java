package backend.data;

import java.util.Calendar;
import java.util.Date;

/**
 * This class is used as a superclass for the specific activities a user performs.
 * @author Kostas Lyrakis
 */
public abstract class Activity {
    private Date date;
    private double carbonSaved;

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

    public abstract double calculateCarbonSaved(User user);

    public abstract int timesPerformedInTheSameDay(User user);

    public abstract void performActivity(User user);

}
