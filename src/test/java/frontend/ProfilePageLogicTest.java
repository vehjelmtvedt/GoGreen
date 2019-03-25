package frontend;

import data.User;
import frontend.gui.ProfilePageLogic;
import org.junit.Assert;
import org.junit.Test;

public class ProfilePageLogicTest {

    private User user = new User();

    @Test
    public void getLevel() {


        Assert.assertEquals(1 , ProfilePageLogic.getLevel(user));

    }

    @Test
    public void getScore() {

        double test = 0.0;
        Assert.assertEquals(test , ProfilePageLogic.getScore(user) , 0);
    }

    @Test
    public void getAchievements() {

        //tbd
    }

    @Test
    public void getBadge() {

        String test = "badges/1.png";

        Assert.assertTrue(test.equals(ProfilePageLogic.getBadge(user)));

    }
}