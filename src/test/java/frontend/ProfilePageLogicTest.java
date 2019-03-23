package frontend;

import data.User;
import data.UserAchievement;
import frontend.gui.ProfilePageLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class ProfilePageLogicTest {

    private User user = new User("Active", "User", 20, "active_user@email.com", "active_user", "pwd123");
    private UserAchievement userAchievement = new UserAchievement(0, true, new Date(11, 11, 11));

    @Before
    public void setup() {

        user.getProgress().getAchievements().add(userAchievement);

    }


    @Test
    public void getLevel() {


        Assert.assertEquals(1, ProfilePageLogic.getLevel(user));

    }

    @Test
    public void getScore() {

        double test = 0.0;
        Assert.assertEquals(test, ProfilePageLogic.getScore(user), 0);
    }

    @Test
    public void getAchievements() {

        Assert.assertNotNull(user.getProgress().getAchievements().get(0));
    }

    @Test
    public void getBadge() {

        String test = "badges/1.png";

        user.setTotalCarbonSaved(0);

        Assert.assertTrue(test.equals(ProfilePageLogic.getBadge(user)));

    }

    @Test
    public void getNameString() {
        String test = "Saved your first CO2";

        Assert.assertTrue(test.equals(ProfilePageLogic.getNameString(userAchievement)));

    }

    @Test
    public void getDateString() {


        String test = new Date(11, 11, 11).toString();

        Assert.assertTrue(test.equals(ProfilePageLogic.getDateString(userAchievement)));

    }

}