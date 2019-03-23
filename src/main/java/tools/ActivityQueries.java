package tools;

import data.Activity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

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
     * Returns the activities that match a given type
     * @param type - Type of activities to look for
     * @return - filtered list of activities of the given type
     */
    public List<Activity> filterActivitiesByType(Class<Activity> type) {
        ArrayList<Activity> filteredActivities = new ArrayList<>();

        for (Activity activity : activities) {
            if (activity.getClass() == type) {
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
        Date today = DateUtils.dateToday();
        Date startDate = DateUtils.getDateBefore(today, dateUnit);

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
        Date today = DateUtils.dateToday();

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

    //    /**
    //     * .
    //     * get the number of activities performed by the user in each
    //     * category of activities i.e. Food, Transportation, Household
    //     *
    //     * @return - Array of int corresponding to each category
    //     */
    //    public ArrayList<int[]> getNrOfActivitiesByCat() {
    //        int[] countCat = new int[3];
    //        int[] countFood = new int[4];
    //        int[] countTransportation = new int[3];
    //        int[] countHousehold = new int[2];
    //        String activityName;
    //        for (Activity activity : this.activities) {
    //            if (activity.getCategory().equals("Food")) {
    //                countCat[0]++;
    //                activityName = activity.getName();
    //                switch (activityName) {
    //                    case "Eat Vegetarian Meal" :
    //                        countFood[0]++;
    //                        break;
    //                    case "Buy Organic Food" :
    //                        countFood[1]++;
    //                        break;
    //                    case "Buy Locally Produced Food" :
    //                        countFood[2]++;
    //                        break;
    //                    case "Buy Non-Processed Food" :
    //                        countFood[3]++;
    //                        break;
    //                    default :
    //                        break;
    //                }
    //            } else if (activity.getCategory().equals("Transportation")) {
    //                countCat[1]++;
    //
    //            } else {
    //                countCat[2]++;
    //            }
    //        }
    //        ArrayList<int[]> counted = new ArrayList<>();
    //        counted.add(countCat);
    //        counted.add(countFood);
    //        System.out.println(countFood[0] + countFood[1] + countFood[2] + countFood[3]);
    //        counted.add(countTransportation);
    //        counted.add(countHousehold);
    //
    //        return counted;
    //    }
}
