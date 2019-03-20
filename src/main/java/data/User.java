package data;

import org.springframework.data.annotation.Id;
import tools.DateUnit;
import tools.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User {

    @Id
    private String email;

    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private String password;

    private ArrayList<String> friends;
    private ArrayList<String> friendRequests;
    private Date lastLoginDate;

    private ArrayList<Activity> activities;

    private int electricityDailyConsumption;
    private double heatingOilDailyConsumption;
    private int dailyCarKilometres;
    private String carType;
    private String meatAndDairyConsumption;
    private String locallyProducedFoodConsumption;
    private String organicFoodConsumption;
    private String processedFoodConsumption;
    private double totalCarbonSaved;

    //new stuff
    private Progress progress = new Progress();


    /**
     * Constructor of User object.
     *
     * @param firstName - first name of user.
     * @param lastName  - last name of user.
     * @param age       - age of user.
     * @param email     - email of user.
     * @param password  - user's password.
     */
    public User(String firstName, String lastName, int age, String email,
                String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.electricityDailyConsumption = 0;
        this.heatingOilDailyConsumption = 0;
        this.carType = "default";
        this.dailyCarKilometres = 0;
        this.meatAndDairyConsumption = "default";
        this.locallyProducedFoodConsumption = "default";
        this.organicFoodConsumption = "default";
        this.processedFoodConsumption = "default";
        this.totalCarbonSaved = 0;
        this.lastLoginDate = Calendar.getInstance().getTime();
        this.activities = new ArrayList<>();
    }

    public User() {
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFriends() {
        return this.friends;
    }

    public ArrayList<Activity> getActivities() {
        return this.activities;
    }

    public ArrayList<String> getFriendRequests() {
        return this.friendRequests;
    }

    public void setElectricityDailyConsumption(int electricityDailyConsumption) {
        this.electricityDailyConsumption = electricityDailyConsumption;
    }

    public int getElectricityDailyConsumption() {
        return this.electricityDailyConsumption;
    }

    public void setHeatingOilDailyConsumption(double heatingOilDailyConsumption) {
        this.heatingOilDailyConsumption = heatingOilDailyConsumption;
    }

    public double getHeatingOilDailyConsumption() {
        return this.heatingOilDailyConsumption;
    }

    public void setDailyCarKilometres(int dailyCarKilometres) {
        this.dailyCarKilometres = dailyCarKilometres;
    }

    public int getDailyCarKilometres() {
        return this.dailyCarKilometres;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setMeatAndDairyConsumption(String meatAndDairyConsumption) {
        this.meatAndDairyConsumption = meatAndDairyConsumption;
    }

    public String getMeatAndDairyConsumption() {
        return meatAndDairyConsumption;
    }

    public void setLocallyProducedFoodConsumption(String locallyProducedFoodConsumption) {
        this.locallyProducedFoodConsumption = locallyProducedFoodConsumption;
    }

    public String getLocallyProducedFoodConsumption() {
        return this.locallyProducedFoodConsumption;
    }

    public void setOrganicFoodConsumption(String organicFoodConsumption) {
        this.organicFoodConsumption = organicFoodConsumption;
    }

    public String getOrganicFoodConsumption() {
        return this.organicFoodConsumption;
    }

    public void setProcessedFoodConsumption(String processedFoodConsumption) {
        this.processedFoodConsumption = processedFoodConsumption;
    }

    public String getProcessedFoodConsumption() {
        return this.processedFoodConsumption;
    }

    public void setTotalCarbonSaved(double totalCarbonSaved) {
        this.totalCarbonSaved = totalCarbonSaved;
    }

    public double getTotalCarbonSaved() {
        return this.totalCarbonSaved;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    public void setLastLoginDate(Date date) {
        this.lastLoginDate = date;
    }

    /**
     * Returns string representation of the User object.
     *
     * @return String
     */
    public String toString() {
        StringBuilder userString = new StringBuilder();
        userString.append("First name: ").append(this.firstName).append('\n');
        userString.append("Last name: ").append(this.lastName).append('\n');
        userString.append("Age: ").append(this.age).append('\n');
        userString.append("Email: ").append(this.email).append('\n');
        userString.append("Username: ").append(this.username).append('\n');
        userString.append("Password: ").append(this.password).append('\n');

        userString.append("Friend emails: \n");
        for (String friendEmail : friends) {
            userString.append("-").append(friendEmail).append("\n");
        }


        return userString.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return email.equals(user.email);
    }

    // ---------- FRIEND METHODS ----------

    /**
     * Adds a friend to friends list.
     *
     * @param friend - user to become friends with
     */
    public void addFriend(String friend) {
        friends.add(friend);
    }

    /**
     * Adds a friend request to friend request list.
     *
     * @param username - username of the person who sent the request.
     */

    public void newFriendRequest(String username) {
        friendRequests.add(username);
    }

    /**
     * Delete a request from the friend request list.
     *
     * @param username - username of the request to delete.
     */
    public void deleteFriendRequest(String username) {
        friendRequests.remove(username);
    }

    // ---------- ACTIVITY METHODS ----------

    /**.
     * Adds a new activity to user's activities list
     * @param activity - Activity to add
     */
    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    /**.
     * Removes an activity from user's activities list
     * @param activity - Activity to remove
     */
    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }

    /**.
     * Returns a list of activities that are of the same type of the specified activity.
     * @param activity - Activity to compare to
     * @return List of activities of same type
     */
    public ArrayList<Activity> getSimilarActivities(Activity activity) {
        ArrayList<Activity> result = new ArrayList<>();

        for (Activity userActivity : activities) {
            if (userActivity.getClass() == activity.getClass() && !userActivity.equals(activity)) {
                result.add(activity);
            }
        }

        return result;
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
     * Returns the CO2 saved over th specified time period
     * @param dateUnit - Time period (date unit)
     * @return - total CO2 saved
     */
    public double getTotalCO2Saved(DateUnit dateUnit) {
        Date today = DateUtils.getInstance().dateToday();
        Date startDate = DateUtils.getInstance().getDateBefore(today, dateUnit);
        List<Activity> filteredActivities = filterActivitiesByDate(startDate, today);

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


    /*
     * Removes a friend from the friends list
     *
     * @param email - email of the user to not be friends with anymore
     * @return true if the user was successfully removed from
     * friends list (found & removed from the list)
     */
    //    public boolean removeFriend(String email) {
    //        return friends.remove(email);
    //    }
}
