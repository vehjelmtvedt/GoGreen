package data;

import data.*;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;

@SuppressWarnings({"ConstantConditions", "EqualsBetweenInconvertibleTypes", "EqualsWithItself"})
public class UserTest {

    private final User userOne = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
    private final User emptyUser = new User();
    private final User activeUser = new User("Active", "User", 20, "active_user@email.com", "active_user", "pwd123");

    @Test
    public void testConstructor() { Assert.assertNotNull(userOne);}

    @Test
    public void testEmptyConstructor() { Assert.assertNotNull(emptyUser);}

    @Test
    public void getFirstNameSuccess() {
        Assert.assertEquals("Vetle", userOne.getFirstName());
    }

    @Test
    public void getFirstNameFail() {
        Assert.assertNotEquals("Evaldas", userOne.getFirstName());
    }

    @Test
    public void getLastNameSuccess() {
        Assert.assertEquals("Hjelmtvedt", userOne.getLastName());
    }

    @Test
    public void getLastNameFail() {
        Assert.assertNotEquals("random", userOne.getLastName());
    }

    @Test
    public void getAgeSuccess() {
        Assert.assertEquals(19, userOne.getAge());
    }

    @Test
    public void getAgeFail() {
        Assert.assertNotEquals(18, userOne.getAge());
    }

    @Test
    public void getEmailSuccess() {
        Assert.assertEquals("vetle@hjelmtvedt.com", userOne.getEmail());
    }

    @Test
    public void getEmailFail() {
        Assert.assertNotEquals("evaldas@email.com", userOne.getEmail());
    }

    @Test
    public void getPasswordSuccess() {
        Assert.assertEquals("password123", userOne.getPassword());
    }

    @Test
    public void getPasswordFail() {
        Assert.assertNotEquals("password124", userOne.getPassword());
    }

    @Test
    public void getLoginStreak() { Assert.assertEquals(0,userOne.getLoginStreak());}

    @Test
    public void setLoginStreak() {
        userOne.incLoginStreak();
        Assert.assertEquals(1,userOne.getLoginStreak());
    }

    @Test
    public void resetLoginStreak() {
        userOne.incLoginStreak();
        userOne.resetLoginStreak();
        Assert.assertEquals(0,userOne.getLoginStreak());
    }

    @Test
    public void setPasswordSuccess() {
        userOne.setPassword("pwd123");
        Assert.assertEquals("pwd123", userOne.getPassword());
    }

    @Test
    public void getUsernameSuccess() { Assert.assertEquals(userOne.getUsername(),"test"); }

    @Test
    public void getFriendRequestsSuccessEmpty() {
        Assert.assertEquals(0, userOne.getFriendRequests().size());
    }

    @Test
    public void getElectricityDailyConsumptionSuccess(){
        userOne.setElectricityDailyConsumption(70000);
        Assert.assertEquals(70000, (int) userOne.getElectricityDailyConsumption());
    }

    @Test
    public void getElectricityDailyConsumptionFail(){
        Assert.assertNotEquals(50000, userOne.getElectricityDailyConsumption());
    }

    @Test
    public void setElectricityDailyConsumptionSuccess() {
        userOne.setElectricityDailyConsumption(50000);
        Assert.assertEquals(50000,(int) userOne.getElectricityDailyConsumption());
    }

    @Test
    public void getHeatingOilDailyConsumptionSuccess() {
        userOne.setHeatingOilDailyConsumption(500);
        Assert.assertEquals("heatingOilDailyConsumption is not equal to 500", 500, userOne.getHeatingOilDailyConsumption(), 0.0);
    }

    @Test
    public void getHeatingOilDailyConsumptionFail() {
        Assert.assertNotEquals(200, userOne.getHeatingOilDailyConsumption());
    }

    @Test
    public void setHeatingOilDailyConsumptionSuccess() {
        userOne.setHeatingOilDailyConsumption(200);
        Assert.assertEquals("heatingOilDailyConsumption is not equal to 200", 200, userOne.getHeatingOilDailyConsumption(), 0.0);
    }

    @Test
    public void getCarTypeSuccess() {
        userOne.setCarType("small");
        Assert.assertEquals("small", userOne.getCarType());
    }

    @Test
    public void getCarTypeFail() {
        Assert.assertNotEquals("medium", emptyUser.getCarType());
    }

    @Test
    public void setCarTypeSuccess() {
        userOne.setCarType("medium");
        Assert.assertEquals("medium", userOne.getCarType());
    }

    @Test
    public void getDailyCarKilometresSuccess() {
        userOne.setDailyCarKilometres(7);
        Assert.assertEquals(7, userOne.getDailyCarKilometres());
    }

    @Test
    public void getDailyCarKilometresFail() {
        Assert.assertNotEquals(5, userOne.getDailyCarKilometres());
    }

    @Test
    public void setDailyCarKilometresSuccess() {
        userOne.setDailyCarKilometres(12);
        Assert.assertEquals(12, userOne.getDailyCarKilometres());
    }

    @Test
    public void getMeatAndDairyConsumptionSuccess() {
        userOne.setMeatAndDairyConsumption("average");
        Assert.assertEquals("average", userOne.getMeatAndDairyConsumption());
    }

    @Test
    public void getMeatAndDairyConsumptionFail() {
        Assert.assertNotEquals("below average", userOne.getMeatAndDairyConsumption());
    }

    @Test
    public void setMeatAndDairyConsumptionSuccess() {
        userOne.setMeatAndDairyConsumption("vegan");
        Assert.assertEquals("vegan", userOne.getMeatAndDairyConsumption());
    }

    @Test
    public void getLocallyProducedFoodConsumptionSuccess() {
        userOne.setLocallyProducedFoodConsumption("almost all");
        Assert.assertEquals("almost all", userOne.getLocallyProducedFoodConsumption());
    }

    @Test
    public void getLocallyProducedFoodConsumptionFail() {
        Assert.assertNotEquals("above average", userOne.getLocallyProducedFoodConsumption());
    }

    @Test
    public void setLocallyProducedFoodConsumptionSuccess() {
        userOne.setLocallyProducedFoodConsumption("very little");
        Assert.assertEquals("very little", userOne.getLocallyProducedFoodConsumption());
    }

    @Test
    public void getOrganicFoodConsumptionSuccess() {
        userOne.setOrganicFoodConsumption("none");
        Assert.assertEquals("none", userOne.getOrganicFoodConsumption());
    }

    @Test
    public void getOrganicFoodConsumptionFail() {
        Assert.assertNotEquals("some", userOne.getOrganicFoodConsumption());
    }

    @Test
    public void setOrganicFoodConsumptionSuccess() {
        userOne.setOrganicFoodConsumption("some");
        Assert.assertEquals("some", userOne.getOrganicFoodConsumption());
    }

    @Test
    public void getProcessedFoodConsumptionSuccess() {
        userOne.setProcessedFoodConsumption("above average");
        Assert.assertEquals("above average", userOne.getProcessedFoodConsumption());
    }

    @Test
    public void getProcessedFoodConsumptionFail() {
        Assert.assertNotEquals("average", userOne.getProcessedFoodConsumption());
    }

    @Test
    public void setProcessedFoodConsumptionSuccess() {
        userOne.setProcessedFoodConsumption("average");
        Assert.assertEquals("average", userOne.getProcessedFoodConsumption());
    }

    @Test
    public void getTotalCarbonSavedSuccess() {
        userOne.setTotalCarbonSaved(50.55);
        Assert.assertEquals(50.55, userOne.getTotalCarbonSaved(), 0.0);
    }

    @Test
    public void getTotalCarbonSavedFail() {
        Assert.assertNotEquals(50.6, userOne.getTotalCarbonSaved(), 0.0);
    }

    @Test
    public void setTotalCarbonSavedSuccess() {
        userOne.setTotalCarbonSaved(60.1);
        Assert.assertEquals(60.1, userOne.getTotalCarbonSaved(), 0.0);
    }

    @Test
    public void getFriendRequestsSuccessNotEmpty() {
        userOne.newFriendRequest("testName");
        Assert.assertEquals(1, userOne.getFriendRequests().size());
        Assert.assertTrue(userOne.getFriendRequests().contains("testName"));
    }

    @Test
    public void testAddFriend() {
        userOne.addFriend("friend1");
        Assert.assertTrue(userOne.getFriends().contains("friend1"));
    }

    @Test
    public void toStringSuccess() {
        userOne.addFriend("friend1");
        Assert.assertEquals(userOne.toString(),"First name: Vetle\nLast name: " +
            "Hjelmtvedt\nAge: 19\nEmail: vetle@hjelmtvedt.com\nUsername: test\nPassword: password123\nFriend emails: \n"
                + "-friend1\n");
    }

    @Test
    public void testNewFriendRequest() {
        userOne.newFriendRequest("testFriend");
        Assert.assertEquals(1, userOne.getFriendRequests().size());
        Assert.assertTrue(userOne.getFriendRequests().contains("testFriend"));
    }

    @Test
    public void testDeleteFriendRequest() {
        userOne.newFriendRequest("testFriend");
        userOne.newFriendRequest("testFriend2");
        Assert.assertTrue(userOne.getFriendRequests().contains("testFriend2"));
        userOne.deleteFriendRequest("testFriend");
        Assert.assertTrue(userOne.getFriendRequests().contains("testFriend2"));
    }

    @Test
    public void testEqualsSame() {
        Assert.assertEquals(userOne, userOne);
    }

    @Test
    public void testEqualsNull() {
        Assert.assertNotEquals(null, userOne);
    }

    @Test
    public void testEqualsAnotherClass() {
        Assert.assertNotEquals(userOne, new LoginDetails());
    }

    @Test
    public void testEqualsEmpty() {
        Assert.assertNotEquals(userOne, emptyUser);
    }


    @Test
    public void testEqualsDiffEmail() {
        User userOne2 = new User("Vetle", "Hjelmtvedt", 19, "vetle2@hjelmtvedt.com","test", "password123");
        Assert.assertNotEquals(userOne, userOne2);
    }

    @Test
    public void testEqualsDifferentUser() {
        Assert.assertNotEquals(userOne, activeUser);
    }

    @Test
    public void testGetLastLoginDate() {
        Assert.assertNotNull(userOne.getLastLoginDate());
    }

    @Test
    public void testGetActivitiesEmpty() {
        Assert.assertNotNull(userOne.getActivities());
    }

    @Test
    public void testGetActivities() {
        Activity activity1 = new EatVegetarianMeal();
        Activity activity2 = new EatVegetarianMeal();
        activeUser.addActivity(activity1);
        activeUser.addActivity(activity2);
        Assert.assertEquals(2, activeUser.getActivities().size());
    }

    @Test
    public void testAddActivity() {
        Activity activity = new EatVegetarianMeal();
        activeUser.addActivity(activity);
        Activity lastActivity = activeUser.getActivities().get(activeUser.getActivities().size()-1);
        Assert.assertEquals(activity, lastActivity);
    }

    @Test
    public void testRemoveActivity() {
        Activity activity = new EatVegetarianMeal();
        activeUser.addActivity(activity);
        activeUser.removeActivity(activity);

        Assert.assertEquals(0, activeUser.getActivities().size());
    }

    @Test
    public void testSimilarActivities() {
        Activity activity1 = new EatVegetarianMeal();
        activity1.setCarbonSaved(5);
        Activity activity2 = new EatVegetarianMeal();
        activity2.setCarbonSaved(10);
        Activity activity3 = new EatVegetarianMeal();
        activity3.setCarbonSaved(15);

        activeUser.addActivity(activity1);
        activeUser.addActivity(activity2);
        activeUser.addActivity(activity3);

        Assert.assertEquals(2, activeUser.getSimilarActivities(activity2).size());
    }

    @Test
    public void testSimilarActivitiesNone() {
        Assert.assertEquals(0, activeUser.getSimilarActivities(new EatVegetarianMeal()).size());
    }

    @Test
    public void testSimilarActivitiesNonMatching() {
        Activity activity1 = new EatVegetarianMeal();
        Activity activity2 = new EatVegetarianMeal();
        Activity activity3 = new BuyLocallyProducedFood();

        activeUser.addActivity(activity1);
        activeUser.addActivity(activity2);
        activeUser.addActivity(activity3);

        ArrayList<Activity> expected = new ArrayList<>();

        Assert.assertEquals(expected, activeUser.getSimilarActivities(activity3));
    }
}