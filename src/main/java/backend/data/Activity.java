package backend.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.*;

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

    protected static Comparator<Activity> getDateComparator() {
        return new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        };
    }

    protected static Comparator<Activity> getCarbonSavedComparator() {
        return new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return Double.compare(o1.getCarbonSaved(), o2.getCarbonSaved());
            }
        };
    }

    protected static Comparator<Activity> getClassComparator() {
        return new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return o1.getClass().getName().compareTo(o2.getClass().getName());
            }
        };
    }

    private static List<Activity> sortHelper(List<Activity> activityList, Comparator<Activity> comparator) {
        ArrayList<Activity> sortedList = new ArrayList<>(activityList);
        sortedList.sort(comparator);
        return sortedList;
    }

    public static List<Activity> sortByDate(List<Activity> activityList) {
        return sortHelper(activityList, getDateComparator());
    }

    public static List<Activity> sortByCarbonSaved(List<Activity> activityList) {
        return sortHelper(activityList, getCarbonSavedComparator());
    }

    public static List<Activity> sortByClass(List<Activity> activityList) {
        return sortHelper(activityList, getClassComparator());
    }
}
