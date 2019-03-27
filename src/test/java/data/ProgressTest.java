package data;

import data.Progress;
import data.User;
import data.UserAchievement;
import javafx.fxml.FXML;
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
    public void getLevel11() {

        user.getProgress().setPoints(0);
        Assert.assertEquals(1, user.getProgress().getLevel());
    }

    @Test
    public void getLevel12() {

        user.getProgress().setPoints(100);
        Assert.assertEquals(1, user.getProgress().getLevel());
    }

    @Test
    public void getLevel5() {

        user.getProgress().addPoints(40300);

        Assert.assertEquals(5, user.getProgress().getLevel());

    }

    @Test
    public void getLevel6() {

        user.getProgress().addPoints(69320.0);

        Assert.assertEquals(6, user.getProgress().getLevel());

    }

    @Test
    public void getLevel8() {

        user.getProgress().setPoints(298100);

        Assert.assertEquals(8, user.getProgress().getLevel());

    }

    @Test
    public void getLevel81() {

        user.getProgress().setPoints(500000);

        Assert.assertEquals(8, user.getProgress().getLevel());

    }

    @Test
    public void pointsNeededtest() {

        user.getProgress().setPoints(0);

        Assert.assertEquals(739, user.getProgress().pointsNeeded(), 1);


    }

    @Test
    public void pointsNeededtest1() {

        user.getProgress().setPoints(700);

        Assert.assertEquals(1, user.getProgress().getLevel());

        Assert.assertEquals(39, user.getProgress().pointsNeeded(), 1);


    }

    @Test
    public void pointsNeededtest2() {

        user.getProgress().setPoints(561000000);

        Assert.assertEquals(8, user.getProgress().getLevel());

        Assert.assertEquals(0, user.getProgress().pointsNeeded(), 1);
    }
}