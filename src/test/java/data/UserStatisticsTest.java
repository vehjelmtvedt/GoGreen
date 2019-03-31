package data;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStatisticsTest {
    @Test
    public void getTimePeriod() {
        UserStatistics userStatistics = new UserStatistics("all", 5, 5);
        Assert.assertEquals("all", userStatistics.getTimePeriod());
    }

    @Test
    public void setTimePeriod() {
        UserStatistics userStatistics = new UserStatistics("", 5, 5);
        userStatistics.setTimePeriod("none");
        Assert.assertEquals("none", userStatistics.getTimePeriod());
    }

    @Test
    public void getTotalUsers() {
        UserStatistics userStatistics = new UserStatistics("all", 5, 5);
        Assert.assertEquals(5, userStatistics.getTotalUsers());
    }

    @Test
    public void setTotalUsers() {
        UserStatistics userStatistics = new UserStatistics("all", 5, 5);
        userStatistics.setTotalUsers(15);
        Assert.assertEquals(15, userStatistics.getTotalUsers());
    }

    @Test
    public void getTotalCO2Saved() {
        UserStatistics userStatistics = new UserStatistics("all", 5, 7);
        Assert.assertEquals(7, userStatistics.getTotalCO2Saved(), 0);
    }

    @Test
    public void setTotalCO2Saved() {
        UserStatistics userStatistics = new UserStatistics("all", 5, 7);
        userStatistics.setTotalCO2Saved(15);
        Assert.assertEquals(15, userStatistics.getTotalCO2Saved(), 0);
    }

    @Test
    public void addTotalCo2Saved() {
        UserStatistics userStatistics = new UserStatistics("all", 5, 7);
        userStatistics.addTotalCo2Saved(205);
        double expected = 7 + 205;
        Assert.assertEquals(expected, userStatistics.getTotalCO2Saved(), 0.01);
    }

    @Test
    public void incrementTotalUsers() {
        UserStatistics userStatistics = new UserStatistics("all", 5, 7);
        userStatistics.incrementTotalUsers();
        Assert.assertEquals(6, userStatistics.getTotalUsers());
    }

    @Test
    public void deleteUser() {
        UserStatistics userStatistics = new UserStatistics("all", 5, 7);
        User testUser = new User("test", "user", 25, "testuser@email.com", "testuser", "pwd");
        userStatistics.deleteUser(testUser);
        Assert.assertEquals(4, userStatistics.getTotalUsers());
    }

    @Test
    public void deleteUserWithActivities() {
        UserStatistics userStatistics = new UserStatistics("all", 10, 250);
        User testUser = new User("test", "user", 25, "testuser@email.com", "testuser", "pwd");

        for (int i = 0; i < 10; ++i) {
            Activity activity = new EatVegetarianMeal();
            activity.setCarbonSaved(1);
            testUser.addActivity(activity);
        }

        userStatistics.deleteUser(testUser);
        Assert.assertEquals(240, userStatistics.getTotalCO2Saved(), 0.1);
    }

    @Test
    public void getAverageCO2Saved() {
        UserStatistics userStatistics = new UserStatistics("all", 10, 25);
        double expected = 25.0 / 10.0;
        Assert.assertEquals(expected, userStatistics.getAverageCO2Saved(), 0.1);
    }

    @Test
    public void getAverageCO2SavedZero() {
        UserStatistics userStatistics = new UserStatistics("all", 0, 0);
        double expected = 0;

        Assert.assertEquals(expected, userStatistics.getAverageCO2Saved(), 0);
    }

    @Test
    public void testEqualSame() {
        UserStatistics userStatistics = new UserStatistics("", 0,0);

        Assert.assertEquals(userStatistics, userStatistics);
    }

    @Test
    public void testNotEqualDifferentClass() {
        UserStatistics userStatistics = new UserStatistics("", 0, 0);
        EatVegetarianMeal meal = new EatVegetarianMeal();

        Assert.assertNotEquals(userStatistics, meal);
    }

    @Test
    public void testNotEqualsNull() {
        UserStatistics userStatistics = new UserStatistics("", 0, 0);

        Assert.assertNotEquals(userStatistics, null);
    }

    @Test
    public void testNotEqualUserStatsCO2Saved() {
        UserStatistics userStatistics1 = new UserStatistics("", 0, 5);
        UserStatistics userStatistics2 = new UserStatistics("", 0, 4);

        Assert.assertNotEquals(userStatistics1, userStatistics2);
    }

    @Test
    public void testNotEqualsUserStatsUsers() {
        UserStatistics userStatistics1 = new UserStatistics("", 5, 4);
        UserStatistics userStatistics2 = new UserStatistics("", 4, 4);

        Assert.assertNotEquals(userStatistics1, userStatistics2);
    }

    @Test
    public void testNotEqualsUserStatsPeriod() {
        UserStatistics userStatistics1 = new UserStatistics("a", 4, 4);
        UserStatistics userStatistics2 = new UserStatistics("b", 4, 4);

        Assert.assertNotEquals(userStatistics1, userStatistics2);
    }

    @Test
    public void testEqualUserStats() {
        UserStatistics userStatistics1 = new UserStatistics("all", 5, 5);
        UserStatistics userStatistics2 = new UserStatistics("all",5, 5);

        Assert.assertEquals(userStatistics1, userStatistics2);
    }
}