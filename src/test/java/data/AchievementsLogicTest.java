package data;

import org.junit.Assert;

import org.junit.Test;

public class AchievementsLogicTest {

    private final Activity activity = new EatVegetarianMeal();
    private final Activity activity1 = new BuyLocallyProducedFood();
    private final Activity activity2 = new BuyNonProcessedFood();
    private final Activity activity3 = new UseTrainInsteadOfCar();
    private final Activity activity4 = new BuyOrganicFood();
    private final Activity activity5 = new UseBusInsteadOfCar();
    private final Activity activity6 = new UseBikeInsteadOfCar();


    private final InstallSolarPanels installSolarPanels = new InstallSolarPanels();
    private final RecyclePlastic recyclePlastic = new RecyclePlastic();
    private final RecyclePaper recyclePaper = new RecyclePaper();
    private final LowerHomeTemperature lowerHomeTemperature = new LowerHomeTemperature();


    private final User user = new User("Active", "User", 20, "active_user@email.com", "active_user", "pwd123");


    @Test
    public void checkActivity() {


        Assert.assertTrue( AchievementsLogic.checkFoodActivity(activity).contains(6));

    }


    @Test
    public void checkActivity1() {

        Assert.assertTrue(AchievementsLogic.checkFoodActivity(activity1).contains(15));

    }

    @Test
    public void checkActivity2() {

        Assert.assertTrue( AchievementsLogic.checkFoodActivity(activity2).contains(16));
    }


    @Test
    public void checkActivity3() {
        Assert.assertTrue( AchievementsLogic.checkTransportActivity1(user, activity3).contains(24));
    }


    @Test
    public void checkActivity4() {
        Assert.assertTrue( AchievementsLogic.checkFoodActivity(activity4).contains(17));
    }

    @Test
    public void checkActivity5() {
        Assert.assertTrue(AchievementsLogic.checkTransportActivity(user , activity5).contains(4));
    }
    @Test
    public void checkActivity6() {
        Assert.assertTrue(AchievementsLogic.checkTransportActivity(user , activity6).contains(2));
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
    public void checkTransportActivity() {

        for (int i = 0 ; i < 6 ; i++) {
            user.addActivity(new UseBusInsteadOfCar());
        }

        Assert.assertTrue(AchievementsLogic.checkTransportActivity(user , activity5).contains(5));

    }

    @Test
    public void testVegan() {

        user.setMeatAndDairyConsumption("vegan");

        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(14));


    }

    @Test
    public void testCar() {
        user.setCarType("small");

        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(13));


    }

    @Test
    public void testFriend() {

        user.getFriends().add("test1");
        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(8));

    }
    @Test
    public void testFriend1() {

        for (int k = 0 ; k <= 10 ; k++ ) {
            user.getFriends().add("test" + k);
        }
        Assert.assertTrue(AchievementsLogic.checkOther(user).contains(9));
    }

    @Test
    public void testUseBike5times() {

        for (int i = 0 ; i < 5 ; i++) {
            user.addActivity(new UseBikeInsteadOfCar());
        }

        Assert.assertTrue(AchievementsLogic.checkTransportActivity(user , new UseBikeInsteadOfCar()).contains(3));

    }

    @Test
    public void testAdd50activities() {

        for (int i = 0 ; i < 50 ; i++) {
            user.addActivity(new UseBikeInsteadOfCar());
        }

        Assert.assertTrue(AchievementsLogic.checkTransportActivity1(user , new UseBikeInsteadOfCar()).contains(7));

    }

    @Test
    public void testUseTrain5times() {

        for (int i = 0 ; i < 5 ; i++) {
            user.addActivity(new UseTrainInsteadOfCar());
        }

        Assert.assertTrue(AchievementsLogic.checkTransportActivity1(user , new UseTrainInsteadOfCar()).contains(25));

    }

    @Test
    public void test() {

        AchievementsLogic achievementsLogic = new AchievementsLogic();

        Assert.assertNotNull(achievementsLogic);

    }

    @Test
    public void checkOtherActivities() {
        Assert.assertTrue(AchievementsLogic.checkOtherActivities(installSolarPanels).contains(12));
     }

    @Test
    public void checkOtherActivities1() {
        Assert.assertTrue(AchievementsLogic.checkOtherActivities(recyclePaper).contains(28));
    }


    @Test
    public void checkOtherActivities2() {
        Assert.assertTrue(AchievementsLogic.checkOtherActivities(lowerHomeTemperature).contains(30));
    }
    @Test
    public void checkOtherActivities3() {
        Assert.assertTrue(AchievementsLogic.checkOtherActivities(recyclePlastic).contains(29));
    }
}