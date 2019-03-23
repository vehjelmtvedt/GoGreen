package data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static data.AchievementsLogic.checkActivity;
import static org.junit.Assert.*;

public class AchievementsLogicTest {

    Activity activity = new EatVegetarianMeal();
    Activity activity1 = new BuyLocallyProducedFood();
    Activity activity2 = new BuyNonProcessedFood();
    Activity activity3 = new BuyLocallyProducedFood();
    Activity activity4 = new BuyOrganicFood();


    User user = new User("Active", "User", 20, "active_user@email.com", "active_user", "pwd123");



    @Before
    public void setup(){
        activity.setDate(new Date(11 , 11 , 10));
        activity1.setDate(new Date(11 , 11 , 11));
    }

    @Test
    public void checkActivity() {

        AchievementsLogic.checkActivity(user , activity);
        Assert.assertNotNull(user.getProgress().getAchievements().get(0));
        Assert.assertEquals(6 , user.getProgress().getAchievements().get(0).getId());

    }


    @Test
    public void checkActivity1() {

        AchievementsLogic.checkActivity(user , activity1);

        Assert.assertNotNull(user.getProgress().getAchievements().get(0));
        Assert.assertEquals(15, user.getProgress().getAchievements().get(0).getId());

    }

    @Test
    public void checkActivity2() {

        AchievementsLogic.checkActivity(user , activity2);

        Assert.assertNotNull(user.getProgress().getAchievements().get(0));
        Assert.assertEquals(16 , user.getProgress().getAchievements().get(0).getId());
    }

    @Test
    public void checkActivity3() {
        AchievementsLogic.checkActivity(user , activity3);

        Assert.assertNotNull(user.getProgress().getAchievements().get(0));
        Assert.assertEquals(15 , user.getProgress().getAchievements().get(0).getId());
    }

    @Test
    public void checkActivity4() {
        AchievementsLogic.checkActivity(user , activity4);
        Assert.assertNotNull(user.getProgress().getAchievements().get(0));
        Assert.assertEquals(17 , user.getProgress().getAchievements().get(0).getId());

    }

    @Test // id 0
    public void checkOther() {

        user.getActivities().add(activity);
        user.setTotalCarbonSaved(100.0);

        AchievementsLogic.checkOther(user);


        Assert.assertNotNull(user.getProgress().getAchievements().get(0));

        Assert.assertEquals(0 , user.getProgress().getAchievements().get(0).getId());

    }

    @Test
    public void checkOther1(){
        user.getActivities().add(activity);
        user.getActivities().add(activity1);
        user.getActivities().add(activity2);
        user.getActivities().add(activity3);
        user.getActivities().add(activity4);
        user.getActivities().add(activity4);


        AchievementsLogic.checkOther(user);

        Assert.assertNotNull(user.getProgress().getAchievements().get(0));

        Assert.assertEquals(1 , user.getProgress().getAchievements().get(0).getId());
    }

    @Test
    public void checkOther2(){
        user.addFriend("test");



        AchievementsLogic.checkOther(user);

        Assert.assertNotNull(user.getProgress().getAchievements().get(0));

        Assert.assertEquals(8 , user.getProgress().getAchievements().get(0).getId());
    }


}