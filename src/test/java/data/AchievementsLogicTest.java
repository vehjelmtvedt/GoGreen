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


    InstallSolarPanels installSolarPanels = new InstallSolarPanels();
    RecyclePlastic recyclePlastic = new RecyclePlastic();
    RecyclePaper recyclePaper = new RecyclePaper();
    LowerHomeTemperature lowerHomeTemperature = new LowerHomeTemperature();


    User user = new User("Active", "User", 20, "active_user@email.com", "active_user", "pwd123");


    @Test
    public void checkActivity() {


        Assert.assertTrue( AchievementsLogic.checkFoodActivity(user , activity).contains(6));

    }


    @Test
    public void checkActivity1() {

        Assert.assertTrue(AchievementsLogic.checkFoodActivity(user , activity1).contains(15));

    }

    @Test
    public void checkActivity2() {

        Assert.assertTrue( AchievementsLogic.checkFoodActivity(user , activity2).contains(16));
    }


    @Test
    public void checkActivity3() {
        Assert.assertTrue( AchievementsLogic.checkTranspostActivity1(user , activity3).contains(24));
    }


    @Test
    public void checkActivity4() {
        Assert.assertTrue( AchievementsLogic.checkFoodActivity(user , activity4).contains(17));
    }

    @Test
    public void checkActivity5() {
        Assert.assertTrue(AchievementsLogic.checkTranspostActivity(user , activity5).contains(4));
    }
    @Test
    public void checkActivity6() {
        Assert.assertTrue(AchievementsLogic.checkTranspostActivity(user , activity6).contains(2));
    }

    @Test // id 0
    public void checkOther() {

        user.getActivities().add(activity);
        user.setTotalCarbonSaved(100.0);



        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(0));

    }

    @Test
    public void checkOther1(){

        for (int i = 0 ; i < 6 ; i++) {
            user.addActivity(new UseTrainInsteadOfCar());
        }


        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(1));
    }

    @Test
    public void checkOther2(){
        user.addFriend("test");

        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(8));
    }

    @Test
    public void checkTranspostActivity() {

        for (int i = 0 ; i < 6 ; i++) {
            user.addActivity(new UseBusInsteadOfCar());
        }

        Assert.assertTrue(AchievementsLogic.checkTranspostActivity(user , activity5).contains(5));

    }

    @Test
    public void testvegan() {

        user.setMeatAndDairyConsumption("vegan");

        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(14));


    }

    @Test
    public void tescar() {
        user.setCarType("small");

        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(13));


    }

    @Test
    public void testfriend() {

        user.getFriends().add("test1");
        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(8));

    }
    @Test
    public void testfriend1() {

        for (int k = 0 ; k <= 10 ; k++ ) {
            user.getFriends().add("test" + k);
        }
        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(9));
    }

    @Test
    public void testusebike5times() {

        for (int i = 0 ; i < 5 ; i++) {
            user.addActivity(new UseBikeInsteadOfCar());
        }

        Assert.assertTrue(AchievementsLogic.checkTranspostActivity(user , new UseBikeInsteadOfCar()).contains(3));

    }

    @Test
    public void testadd50activities() {

        for (int i = 0 ; i < 50 ; i++) {
            user.addActivity(new UseBikeInsteadOfCar());
        }

        Assert.assertTrue(AchievementsLogic.checkTranspostActivity1(user , new UseBikeInsteadOfCar()).contains(7));

    }

    @Test
    public void testusetrain5times() {

        for (int i = 0 ; i < 5 ; i++) {
            user.addActivity(new UseTrainInsteadOfCar());
        }

        Assert.assertTrue(AchievementsLogic.checkTranspostActivity1(user , new UseTrainInsteadOfCar()).contains(25));

    }

    @Test
    public void test() {

        AchievementsLogic achievementsLogic = new AchievementsLogic();

        Assert.assertNotNull(achievementsLogic);

    }

    @Test
    public void checkotherActivities() {
        Assert.assertTrue(AchievementsLogic.checkotherActivities(user , installSolarPanels).contains(12));
     }

    @Test
    public void checkotherActivities1() {
        Assert.assertTrue(AchievementsLogic.checkotherActivities(user , recyclePaper).contains(28));
    }


    @Test
    public void checkotherActivities2() {
        Assert.assertTrue(AchievementsLogic.checkotherActivities(user , lowerHomeTemperature).contains(30));
    }
    @Test
    public void checkotherActivities3() {
        Assert.assertTrue(AchievementsLogic.checkotherActivities(user , recyclePlastic).contains(29));
    }
}