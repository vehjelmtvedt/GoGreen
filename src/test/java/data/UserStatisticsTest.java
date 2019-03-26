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
    public void getAverageCO2Saved() {
        UserStatistics userStatistics = new UserStatistics("all", 10, 25);
        double expected = 25.0 / 10.0;
        Assert.assertEquals(expected, userStatistics.getAverageCO2Saved(), 0.1);
    }
}