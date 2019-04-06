package data;

import org.junit.Assert;

import org.junit.Test;

public class AchievementsLogicTest {

    Activity activity = new EatVegetarianMeal();
    Activity activity1 = new BuyLocallyProducedFood();
    Activity activity2 = new BuyNonProcessedFood();
    Activity activity3 = new UseTrainInsteadOfCar();
    Activity activity4 = new BuyOrganicFood();
    Activity activity5 = new UseBusInsteadOfCar();
    Activity activity6 = new UseBikeInsteadOfCar();


    User user = new User("Active", "User", 20, "active_user@email.com", "active_user", "pwd123");


    @Test
    public void checkActivity() {


        Assert.assertEquals(6 , AchievementsLogic.checkFoodActivity(user , activity));

    }


    @Test
    public void checkActivity1() {

        Assert.assertEquals(15 , AchievementsLogic.checkFoodActivity(user , activity1));

    }

    @Test
    public void checkActivity2() {

        Assert.assertEquals(16 , AchievementsLogic.checkFoodActivity(user , activity2));
    }


    @Test
    public void checkActivity3() {
        Assert.assertEquals(24, AchievementsLogic.checkTranspostActivity1(user , activity3));
    }


    @Test
    public void checkActivity4() {
        Assert.assertEquals(17, AchievementsLogic.checkFoodActivity(user , activity4));
    }

    @Test
    public void checkActivity5() {
        Assert.assertEquals(4, AchievementsLogic.checkTranspostActivity(user , activity5));
    }
    @Test
    public void checkActivity6() {
        Assert.assertEquals(2, AchievementsLogic.checkTranspostActivity(user , activity6));
    }

    @Test // id 0
    public void checkOther() {

        user.getActivities().add(activity);
        user.setTotalCarbonSaved(100.0);



        Assert.assertEquals(0 , AchievementsLogic.checkOther(user));

    }

    @Test
    public void checkOther1(){
        user.getActivities().add(activity);
        user.getActivities().add(activity1);
        user.getActivities().add(activity2);
        user.getActivities().add(activity4);
        user.getActivities().add(activity4);
        user.getActivities().add(activity4);


        Assert.assertEquals(1 , AchievementsLogic.checkOther(user));
    }

    @Test
    public void checkOther2(){
        user.addFriend("test");

        Assert.assertEquals(8 , AchievementsLogic.checkOther(user));
    }


}