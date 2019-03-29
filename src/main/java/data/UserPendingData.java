package data;

import java.util.ArrayList;

public class UserPendingData {
    private ArrayList<UserAchievement> achievements;
    private ArrayList<String> friendRequests;

    public UserPendingData() {
        achievements = new ArrayList<>();
        friendRequests = new ArrayList<>();
    }

    public ArrayList<UserAchievement> getAchievements() {
        return achievements;
    }

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public void addNewAchievement(UserAchievement achievement) {
        achievements.add(achievement);
    }

    public void addNewRequest(String request) {
        friendRequests.add(request);
    }
}
