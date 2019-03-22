package tools;

import data.Activity;

import java.util.ArrayList;
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

    /**.
     * Filters activities by category
     * @param category - category to filter by
     * @return filtered list of activities by category
     */
    public List<Activity> filterActivities(String category) {
        ArrayList<String> categories = new ArrayList<>();
        categories.add(category);
        return filterActivitiesByCategories(categories);
    }

    /**.
     * Filters activities by multiple categories
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
     * Filters activities that have been done in the specified time range
     * @param from - Start date
     * @param to - End date
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

        int fromIndex = toIndex;

        // Now that we have the index of the last valid activity, we now loop
        // until we find an activity that is not in the range of the dates
        for (; fromIndex > 0; --fromIndex) {
            Activity activity = activities.get(fromIndex - 1);

            // Activity is not in our range, we may break, since all the other
            // preceding activities are also before the "from" date
            if (activity.getDate().before(from)) {
                if (fromIndex == toIndex) { // edge case where no activities fit the range
                    return new ArrayList<>();
                }

                break;
            }
        }

        // Edge case where no matching activities found
        if (fromIndex == -1) {
            return new ArrayList<>();
        }

        // Return the appropriate sublist
        return activities.subList(fromIndex, toIndex + 1);
    }

    // ---------- CO2 METHODS ----------

    /**.
     * Returns the total CO2 saved by the user
     * @return - total CO2 saved
     */
    public double getTotalCO2Saved() {
        return getTotalCO2Saved(activities);
    }

    /**.
     * Returns the CO2 saved until today and from today - dateUnit
     * @param dateUnit - Time period (date unit)
     * @return - total CO2 saved
     */
    public double getTotalCO2Saved(DateUnit dateUnit) {
        Date today = DateUtils.dateToday();
        Date startDate = DateUtils.getDateBefore(today, dateUnit);

        return getTotalCO2Saved(startDate, today);
    }

    /**.
     * Returns the total CO2 saved up until today from a specified date
     * @param fromDate - start date
     * @return - total CO2 saved from specified date until today
     */
    public double getTotalCO2Saved(Date fromDate) {
        Date today = DateUtils.dateToday();

        return getTotalCO2Saved(fromDate, today);
    }

    /**.
     * Returns the CO2 saved over the specified time period
     * @param fromDate - start date
     * @param toDate - end date
     * @return - total CO2 saved
     */
    public double getTotalCO2Saved(Date fromDate, Date toDate) {
        List<Activity> filteredActivities = filterActivitiesByDate(fromDate, toDate);

        return getTotalCO2Saved(filteredActivities);
    }

    /**.
     * Helper method to calculate the total CO2 saved by a list of activities
     * @param activityList - List of activities
     * @return - total CO2 saved by all the activities
     */
    private double getTotalCO2Saved(List<Activity> activityList) {
        return Activity.getSum(activityList);
    }
}
