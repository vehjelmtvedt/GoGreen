package data;

import java.util.ArrayList;

public class UserPendingData {
    private final ArrayList<UserAchievement> achievements;
    private final ArrayList<String> friendRequests;
    private final ArrayList<String> friends;

    /**.
     * Constructs new UserPendingData object with initially empty pending request lists
     */
    public UserPendingData() {
        achievements = new ArrayList<>();
        friendRequests = new ArrayList<>();
        friends = new ArrayList<>();
    }

    public ArrayList<UserAchievement> getAchievements() {
        return achievements;
    }

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void addNewAchievement(UserAchievement achievement) {
        achievements.add(achievement);
    }

    public void addNewRequest(String request) {
        friendRequests.add(request);
    }

    public void addNewFriend(String friend) {
        friends.add(friend);
    }
}
