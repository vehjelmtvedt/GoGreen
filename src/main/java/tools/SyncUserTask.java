package tools;

import data.LoginDetails;
import data.User;
import data.UserAchievement;
import data.UserPendingData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Callable;

public class SyncUserTask implements Callable<UserPendingData> {
    private Requests requests;
    private LoginDetails loginDetails;
    private User user;

    /**.
     * Create new SyncUserTask with user and login details
     * @param loginDetails - Login Details of the User
     * @param user - Client User object for the User
     */
    public SyncUserTask(Requests requests, LoginDetails loginDetails, User user) {
        this.requests = requests;
        this.loginDetails = loginDetails;
        this.user = user;
    }

    /**.
     * Executes the task and returns pending User objects (achievements & friend requests)
     * @return - Pending User Data
     */
    public UserPendingData call() {
        // Initialize pending data object
        UserPendingData userPendingData = new UserPendingData();

        // Get most recent User object
        User newUser = requests.loginRequest(loginDetails);

        // Update achievements & friend requests
        updateUserAchievements(newUser, userPendingData);
        updateUserFriendRequests(newUser, userPendingData);
        updateUserFriends(newUser, userPendingData);

        return userPendingData;
    }

    public User getUser() {
        return this.user;
    }

    /**.
     * Updates the User object
     * @param user - new User object
     */
    public void updateUser(User user) {
        this.user = user;
    }

    /**.
     * Updates the current User's achievements
     * @param newUser - New User to check for differences with
     * @param userPendingData - User Pending Data object
     */
    private void updateUserAchievements(User newUser, UserPendingData userPendingData) {
        ArrayList<UserAchievement> achievements = newUser.getProgress().getAchievements();

        // Get current User & new User achievement list sizes
        int userAchievementSize = user.getProgress().getAchievements().size();
        int newUserAchievementSize = achievements.size();

        // The new achievements are the new achievements at the end of the list (since the
        // list is essentially automatically sorted by adding date)
        for (int i = userAchievementSize; i < newUserAchievementSize; ++i) {
            userPendingData.addNewAchievement(newUser.getProgress().getAchievements().get(i));
        }

        // Update current User progress
        user.getProgress().setAchievements(newUser.getProgress().getAchievements());
    }

    /**.
     * Updates the current User's friend requests
     * @param newUser - New User to check for differences with
     * @param userPendingData - User Pending Data object
     */
    private void updateUserFriendRequests(User newUser, UserPendingData userPendingData) {
        // Create HashSets for current User's friend requests and new User's friend requests
        HashSet<String> friendSet = new HashSet<>(user.getFriendRequests());
        HashSet<String> newFriendSet = new HashSet<>(newUser.getFriendRequests());

        // Calculate difference between new User's friend requests and current
        // (and update it to the hashset), (Essentially B - A)
        newFriendSet.removeAll(friendSet);

        // Add new requests as pending
        for (String s : newFriendSet) {
            userPendingData.addNewRequest(s);
        }

        // Update current User requests
        user.setFriendRequests(newUser.getFriendRequests());
    }

    private void updateUserFriends(User newUser, UserPendingData userPendingData) {
        // Create HashSets for current User's friends and new User's friends
        HashSet<String> friendSet = new HashSet<>(user.getFriends());
        HashSet<String> newFriendSet = new HashSet<>(newUser.getFriends());

        // Calculate difference between new User's friends and current
        // (and update it to the haset), (Essentially B - A)
        newFriendSet.removeAll(friendSet);

        // Add new friends as pending
        for (String s : newFriendSet) {
            userPendingData.addNewFriend(s);
        }

        // Update current User friends
        user.setFriends(newUser.getFriends());
    }
}
