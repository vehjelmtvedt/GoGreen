package data;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

public class UserPendingDataTest {
    @Test
    public void testGetAchievements() {
        UserPendingData userPendingData = new UserPendingData();

        Assert.assertNotNull(userPendingData.getAchievements());
    }

    @Test
    public void testGetFriendRequests() {
        UserPendingData userPendingData = new UserPendingData();

        Assert.assertNotNull(userPendingData.getFriendRequests());
    }

    @Test
    public void testGetFriends() {
        UserPendingData userPendingData = new UserPendingData();

        Assert.assertNotNull(userPendingData.getFriends());
    }

    @Test
    public void testAddAchievement() {
        UserPendingData userPendingData = new UserPendingData();
        userPendingData.addNewAchievement(new UserAchievement(0, true,
                Calendar.getInstance().getTime()));
        userPendingData.addNewAchievement(new UserAchievement(1, true,
                Calendar.getInstance().getTime()));

        Assert.assertEquals(2, userPendingData.getAchievements().size());
    }

    @Test
    public void testAddRequests() {
        UserPendingData userPendingData = new UserPendingData();
        userPendingData.addNewRequest("user_a");
        userPendingData.addNewRequest("user_b");
        userPendingData.addNewRequest("user_c");

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("user_a");
        expected.add("user_b");
        expected.add("user_c");

        Assert.assertEquals(expected, userPendingData.getFriendRequests());
    }

    @Test
    public void testAddFriends() {
        UserPendingData userPendingData = new UserPendingData();
        userPendingData.addNewFriend("user_a");
        userPendingData.addNewFriend("user_c");

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("user_a");
        expected.add("user_c");

        Assert.assertEquals(expected, userPendingData.getFriends());
    }
}