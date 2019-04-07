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


        Assert.assertTrue(6 == AchievementsLogic.checkFoodActivity(user , activity).get(0));

    }


    @Test
    public void checkActivity1() {

        Assert.assertTrue(15 == AchievementsLogic.checkFoodActivity(user , activity1).get(0));

    }

    @Test
    public void checkActivity2() {

        Assert.assertTrue(16 == AchievementsLogic.checkFoodActivity(user , activity2).get(0));
    }


    @Test
    public void checkActivity3() {
        Assert.assertTrue(24 == AchievementsLogic.checkTranspostActivity1(user , activity3).get(0));
    }


    @Test
    public void checkActivity4() {
        Assert.assertTrue(17 == AchievementsLogic.checkFoodActivity(user , activity4).get(0));
    }

    @Test
    public void checkActivity5() {
        Assert.assertTrue(4 == AchievementsLogic.checkTranspostActivity(user , activity5).get(0));
    }
    @Test
    public void checkActivity6() {
        Assert.assertTrue(2 == AchievementsLogic.checkTranspostActivity(user , activity6).get(0));
    }

    @Test // id 0
    public void checkOther() {

        user.getActivities().add(activity);
        user.setTotalCarbonSaved(100.0);



        Assert.assertTrue(0 == AchievementsLogic.checkOther(user).get(0));

    }

    @Test
    public void checkOther1(){
        user.getActivities().add(activity);
        user.getActivities().add(activity1);
        user.getActivities().add(activity2);
        user.getActivities().add(activity4);
        user.getActivities().add(activity4);
        user.getActivities().add(activity4);


        Assert.assertTrue(1 == AchievementsLogic.checkOther(user).get(0));
    }

    @Test
    public void checkOther2(){
        user.addFriend("test");

        Assert.assertTrue(8 == AchievementsLogic.checkOther(user).get(0));
    }

    @Test
    public void checkTranspostActivity() {

        AchievementsLogic.checkTranspostActivity(user , activity5);
        AchievementsLogic.checkTranspostActivity(user , activity5);
        AchievementsLogic.checkTranspostActivity(user , activity5);
        AchievementsLogic.checkTranspostActivity(user , activity5);
        AchievementsLogic.checkTranspostActivity(user , activity5);
        AchievementsLogic.checkTranspostActivity(user , activity5);

        Assert.assertTrue(4 == AchievementsLogic.checkTranspostActivity(user , activity5).get(0));

    }

    @Test
    public void testvegan() {

        user.setMeatAndDairyConsumption("vegan");

        Assert.assertTrue(14 == AchievementsLogic.checkOther(user).get(0));


    }

    @Test
    public void tescar() {

        user.setCarType("small");

        Assert.assertTrue(13 == AchievementsLogic.checkOther(user).get(0));


    }

    @Test
    public void testfriend() {

        user.getFriends().add("test1");
        Assert.assertTrue(8 == AchievementsLogic.checkOther(user).get(0));

    }
    @Test
    public void testfriend1() {

        for (int k = 0 ; k < 10 ; k++ ) {
            user.getFriends().add("test" + k);
        }
        Assert.assertTrue(8 == AchievementsLogic.checkOther(user).get(0));
    }
}