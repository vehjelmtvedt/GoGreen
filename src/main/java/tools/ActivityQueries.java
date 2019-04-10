package tools;

import data.Activity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ActivityQueries {
    private List<Activity> activities;

    public ActivityQueries(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    // ---------- FILTER METHODS ----------

    /**
     * .
     * Filters activities by category
     *
     * @param category - category to filter by
     * @return filtered list of activities by category
     */
    public List<Activity> filterActivities(String category) {
        ArrayList<String> categories = new ArrayList<>();
        categories.add(category);
        return filterActivitiesByCategories(categories);
    }

    /**
     * .
     * Filters activities by multiple categories
     *
     * @param categories - list of categories (String) to filter  by
     * @return list of activities filtered by specified categories
     */
    public List<Activity> filterActivitiesByCategories(List<String> categories) {
        List<Activity> filteredActivities = new ArrayList<Activity>();

        for (Activity a : activities) {
            if (categories.contains(a.getCategory())) {
                filteredActivities.add(a);
            }
        }

        return filteredActivities;

        /*return activities.stream() // convert activities list to stream
                .filter(activity -> categories.contains(activity.getCategory()))
                // check if categories list contains the category of the activity
                .collect(Collectors.toList());*/ // return result as list
    }

    /**.
     * Filters activities that have been done the specified time unit ago
     * @param dateUnit - Specified time unit (today, 1 day, 7 days, 30 days, 365 days)
     * @return - filtered List of activities done within the specified time period
     */
    public List<Activity> filterActivitiesByDate(DateUnit dateUnit) {
        Date today = DateUtils.instance.dateToday();
        Date startDate = DateUtils.instance.getDateBefore(today, dateUnit);

        return filterActivitiesByDate(startDate, today);
    }

    /**
     * .
     * Filters activities that have been done in the specified time range
     *
     * @param from - Start date
     * @param to   - End date
     * @return - filtered list of activities that fall in the date range
     */
    public List<Activity> filterActivitiesByDate(Date from, Date to) {
        // Start from the end of the list (since most recent activity is at the end of the list)
        int toIndex = activities.size() - 1;

        // We start by looking at the dates from the end of the list until we find the
        // first activity that is performed in our time range (and we know that the list is sorted
        // by date)
        while (toIndex >= 0 && activities.get(toIndex).getDate().after(to)) {
            toIndex--;
        }

        // No activities exist
        if (toIndex == -1) {
            return new ArrayList<>();
        }

        int fromIndex = toIndex;

        // Now that we have the index of the last valid activity, we now loop
        // until we find an activity that is not in the range of the dates
        while (fromIndex > 0
                && !activities.get(fromIndex - 1).getDate().before(from)) {
            fromIndex--;
        }


        // Edge case where fromIndex matches toIndex
        if (fromIndex == toIndex) {
            Date date = activities.get(fromIndex).getDate();

            // In the case where fromIndex matches toIndex, the Date is out of range
            // if the date is not equal to from or to AND if the date is both not after
            // from and not before to.
            if (!DateUtils.instance.checkDateInRange(date, from, to)) {
                return new ArrayList<>();
            }
        }

        // Return the appropriate sublist
        return activities.subList(fromIndex, toIndex + 1);
    }

    /**
     * .
     * Returns the activities that match the given CO2 criteria
     *
     * @param co2     - CO2 value to filter by
     * @param greater - should value be greater than CO2 or lower?
     * @return - filtered list of activities that match the CO2 criteria
     */
    public List<Activity> filterActivitiesByCO2Saved(double co2, boolean greater) {
        List<Activity> filteredActivities = new ArrayList<>();

        for (Activity a : activities) {
            if ((greater && a.getCarbonSaved() >= co2)
                    || (!greater && a.getCarbonSaved() <= co2)) {
                filteredActivities.add(a);
            }
        }

        return filteredActivities;
    }

    /**.
     * Returns the activities that match the given CO2 range
     * @param fromCo2 - from CO2 (inclusive)
     * @param toCo2 - to CO2 (inclusive)
     * @return - filtered list of activities matching the CO2 range
     */
    public List<Activity> filterActivitiesByCO2Saved(double fromCo2, double toCo2) {
        List<Activity> filteredActivities = new ArrayList<>();

        for (Activity a : activities) {
            if (a.getCarbonSaved() >= fromCo2 && a.getCarbonSaved() <= toCo2) {
                filteredActivities.add(a);
            }
        }

        return filteredActivities;
    }

    /**.
     * Returns the activities that match a given type
     * @param type - Type of activities to look for
     * @return - filtered list of activities of the given type
     */
    public List<Activity> filterActivitiesByType(Class type) {
        ArrayList<Class> categories = new ArrayList<>();
        categories.add(type);
        return filterActivitiesByType(categories);
    }

    /**.
     * Returns the activities that match the given types
     * @param types - Lsit of types of activities to look for
     * @return - filtered list of activities of the given types
     */
    public List<Activity> filterActivitiesByType(ArrayList<Class> types) {
        ArrayList<Activity> filteredActivities = new ArrayList<>();

        for (Activity activity : activities) {
            if (types.contains(activity.getClass())) {
                filteredActivities.add(activity);
            }
        }

        return filteredActivities;
    }

    // ---------- CO2 METHODS ----------

    /**
     * .
     * Returns the total CO2 saved by the user
     *
     * @return - total CO2 saved
     */
    public double getTotalCO2Saved() {
        return getTotalCO2Saved(activities);
    }

    /**
     * .
     * Returns the CO2 saved until today and from today - dateUnit
     *
     * @param dateUnit - Time period (date unit)
     * @return - total CO2 saved
     */
    public double getTotalCO2Saved(DateUnit dateUnit) {
        Date today = DateUtils.instance.dateToday();
        Date startDate = DateUtils.instance.getDateBefore(today, dateUnit);

        return getTotalCO2Saved(startDate, today);
    }

    /**
     * .
     * Returns the total CO2 saved up until today from a specified date
     *
     * @param fromDate - start date
     * @return - total CO2 saved from specified date until today
     */
    public double getTotalCO2Saved(Date fromDate) {
        Date today = DateUtils.instance.dateToday();

        return getTotalCO2Saved(fromDate, today);
    }

    /**
     * .
     * Returns the CO2 saved over the specified time period
     *
     * @param fromDate - start date
     * @param toDate   - end date
     * @return - total CO2 saved
     */
    public double getTotalCO2Saved(Date fromDate, Date toDate) {
        List<Activity> filteredActivities = filterActivitiesByDate(fromDate, toDate);

        return getTotalCO2Saved(filteredActivities);
    }

    /**
     * .
     * Helper method to calculate the total CO2 saved by a list of activities
     *
     * @param activityList - List of activities
     * @return - total CO2 saved by all the activities
     */
    private double getTotalCO2Saved(List<Activity> activityList) {
        return Activity.getSum(activityList);
    }

    // ---------- CHART METHODS ----------

    /**.
     * Returns a List to be used directly for a Chart which contains entries
     * for CO2 savings of a week, starting from the today's date and moving
     * 7 days back
     * @return - ObservableList of BarChart Data objects with fully constructed entries
     */
    public ObservableList<XYChart.Data> getWeeklyCO2Savings() {
        // Filters the activities for one week for efficiency
        activities = filterActivitiesByDate(DateUnit.WEEK);

        // Create a new ObservableList with BarChart Data
        ObservableList<XYChart.Data> list = FXCollections.observableArrayList();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.instance.dateToday());

        // Iterate for 7 days to get the data for the entire week
        for (int i = 0; i < DateUnit.WEEK.getNumDays(); ++i) {
            DateUtils.instance.setDateToEnd(calendar);
            Date toDate = calendar.getTime();
            DateUtils.instance.setDateToMidnight(calendar);
            Date fromDate = calendar.getTime();

            // Get the Day of Week String name for the entry label
            String dayName = DateUtils.instance.getDayName(toDate);

            // Get the CO2 saved for the date
            double co2Saved = getTotalCO2Saved(fromDate, toDate);

            // Add entry to ObservableList
            list.add(new XYChart.Data<>(dayName, co2Saved));

            calendar.add(Calendar.DATE, -1);
        }

        // Reverse the list (since the first date we added was today)
        Collections.reverse(list);

        // Return the ObservableList to be plugged directly to a BarChart
        return list;
    }
}
