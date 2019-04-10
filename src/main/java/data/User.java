package data;

import org.springframework.data.annotation.Id;
import tools.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class User {

    @Id
    private String email;

    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private String password;

    private int loginStreak;

    private HashSet<String> friends;
    private HashSet<String> friendRequests;
    private Date lastLoginDate;

    private ArrayList<Activity> activities;

    private double electricityDailyConsumption;
    private double heatingOilDailyConsumption;
    private int dailyCarKilometres;
    private String carType;
    private String meatAndDairyConsumption;
    private String locallyProducedFoodConsumption;
    private String organicFoodConsumption;
    private String processedFoodConsumption;
    private double totalCarbonSaved;
    private Progress progress = new Progress();

    private String avatar;

    private int securityQuestionId;
    private String securityQuestionAnswer;


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
        this.loginStreak = 0;
        this.friends = new HashSet<>();
        this.friendRequests = new HashSet<>();
        this.electricityDailyConsumption = 0;
        this.heatingOilDailyConsumption = 0;
        this.carType = "default";
        this.dailyCarKilometres = 0;
        this.meatAndDairyConsumption = "default";
        this.locallyProducedFoodConsumption = "default";
        this.organicFoodConsumption = "default";
        this.processedFoodConsumption = "default";
        this.totalCarbonSaved = 0;
        this.lastLoginDate = DateUtils.instance.dateToday();
        this.activities = new ArrayList<>();
        this.avatar = "0";
    }

    public User() {
    }

    public Progress getProgress() {
        return progress;
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

    public int getLoginStreak() {
        return this.loginStreak;
    }

    public void incLoginStreak() {
        this.loginStreak++;
    }

    public void resetLoginStreak() {
        this.loginStreak = 0;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityQuestionId(int id) {
        this.securityQuestionId = id;
    }

    public int getSecurityQuestionId() {
        return this.securityQuestionId;
    }

    public void setSecurityQuestionAnswer(String answer) {
        this.securityQuestionAnswer = answer;
    }

    public String getSecurityQuestionAnswer() {
        return this.securityQuestionAnswer;
    }

    public HashSet<String> getFriends() {
        return this.friends;
    }

    public ArrayList<Activity> getActivities() {
        return this.activities;
    }

    public HashSet<String> getFriendRequests() {
        return this.friendRequests;
    }

    public void setElectricityDailyConsumption(double electricityDailyConsumption) {
        this.electricityDailyConsumption = electricityDailyConsumption;
    }

    public double getElectricityDailyConsumption() {
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
        // keep only 3 decimal places
        this.totalCarbonSaved = ((int) (totalCarbonSaved * 1000)) / 1000.0;
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

    public void setFriends(HashSet<String> friends) {
        this.friends = friends;
    }

    public void setFriendRequests(HashSet<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    /**
     * .
     * Adds a new activity to user's activities list
     *
     * @param activity - Activity to add
     */
    public void addActivity(Activity activity) {
        this.activities.add(activity);

    }

    /**
     * .
     * Removes an activity from user's activities list
     *
     * @param activity - Activity to remove
     */
    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }

    /**
     * .
     * Returns a list of activities that are of the same type of the specified activity.
     *
     * @param activity - Activity to compare to
     * @return List of activities of same type
     */
    public ArrayList<Activity> getSimilarActivities(Activity activity) {
        ArrayList<Activity> result = new ArrayList<>();

        for (Activity userActivity : activities) {
            if (userActivity.getClass() == activity.getClass() && !userActivity.equals(activity)) {
                result.add(userActivity);
            }
        }

        return result;
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
