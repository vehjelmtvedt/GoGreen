package tools;

import data.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Calendar;

public class SyncUserTaskTest {
    Requests requests;

    private User user = new User("Test", "User", 25,
            "test-user@email.com","testuser","pwd123");

    private User modifiedUser = new User("Test", "User", 25,
            "test-user@email.com", "testuser", "pwd123");

    private LoginDetails loginDetails = new LoginDetails("testuser", "pwd123");

    @Before
    public void setup() {
        requests = Mockito.mock(Requests.class);

        ArrayList<UserAchievement> achievements1 = new ArrayList<>();
        ArrayList<UserAchievement> achievements2 = new ArrayList<>();

        UserAchievement ach1 = new UserAchievement(0, true, Calendar.getInstance().getTime());
        UserAchievement ach2 = new UserAchievement(1, true, Calendar.getInstance().getTime());
        UserAchievement ach3 = new UserAchievement(2, true, Calendar.getInstance().getTime());

        achievements1.add(ach1);
        achievements1.add(ach2);
        achievements2.add(ach1);
        achievements2.add(ach2);
        achievements2.add(ach3);

        ArrayList<String> requests1 = new ArrayList<>();
        ArrayList<String> requests2 = new ArrayList<>();

        String req1 = "abc";
        String req2 = "differentPerson";
        String req3 = "def";

        requests1.add(req1);
        requests1.add(req3);
        requests2.add(req1);
        requests2.add(req2);

        user.getProgress().setAchievements(achievements1);
        user.setFriendRequests(requests1);

        modifiedUser.getProgress().setAchievements(achievements2);
        modifiedUser.setFriendRequests(requests2);
    }

    @Test
    public void callEqual() {
        SyncUserTask userTask = new SyncUserTask(requests, loginDetails, user);

        Mockito.when(requests.loginRequest(loginDetails)).thenReturn(user);

        UserPendingData result = null;

        try {
            result = userTask.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // No equals method or UserAchievement, therefore two asserts here
        Assert.assertEquals(0, result.getAchievements().size());
        Assert.assertEquals(0, result.getFriendRequests().size());
    }

    @Test
    public void callDifferent() {
        Mockito.when(requests.loginRequest(loginDetails)).thenReturn(modifiedUser);

        SyncUserTask userTask = new SyncUserTask(requests, loginDetails, user);

        UserPendingData result = null;

        try {
            result = userTask.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1, result.getAchievements().size());
        Assert.assertEquals(1, result.getFriendRequests().size());
        Assert.assertEquals(3, user.getProgress().getAchievements().size());
        Assert.assertEquals(2, user.getFriendRequests().size());
    }
}