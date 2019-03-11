package backend.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;


public class ProgressTest {


    User user = new User();
    Progress progress = user.getProgress();


    @Test
    public void addPoints() {

        progress.setPoints(10.0);
        UserAchievement test = new UserAchievement(1, true, new Date(1999, 02, 22));

        progress.getAchievements().add(test);

        user.getProgress().addPoints(5.0);

        Assert.assertEquals(15, user.getProgress().getPoints(), 0);
    }


    @Test
    public void getLevel1() {

        Assert.assertEquals(1, user.getProgress().getLevel());
    }

    @Test
    public void getLevel6() {

        user.getProgress().addPoints(1000);

        Assert.assertEquals(6, user.getProgress().getLevel());

    }

    @Test
    public void getLevel8() {

        user.getProgress().setPoints(5000);

        Assert.assertEquals(8, user.getProgress().getLevel());

    }
}