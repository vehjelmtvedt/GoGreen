package backend.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;

public class UserTest {

    User userOne = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
    User emptyUser = new User();

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
    public void getLastameFail() {
        Assert.assertNotEquals("evaldaslastname", userOne.getLastName());
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
        Assert.assertEquals(70000, userOne.getElectricityDailyConsumption());
    }

    @Test
    public void getElectricityDailyConsumptionFail(){
        Assert.assertNotEquals(50000, userOne.getElectricityDailyConsumption());
    }

    @Test
    public void setElectricityDailyConsumptionSuccsess() {
        userOne.setElectricityDailyConsumption(50000);
        Assert.assertEquals(50000, userOne.getElectricityDailyConsumption());
    }

    @Test
    public void getHeatingOilDailyConsumptionSuccess() {
        userOne.setHeatingOilDailyConsumption(500);
        Assert.assertTrue("heatingOilDailyConsumption is not equal to 500", userOne.getHeatingOilDailyConsumption() == 500);
    }

    @Test
    public void getHeatingOilDailyConsumptionFail() {
        Assert.assertNotEquals(200, userOne.getHeatingOilDailyConsumption());
    }

    @Test
    public void setHeatingOilDailyConsumptionSuccsess() {
        userOne.setHeatingOilDailyConsumption(200);
        Assert.assertTrue("heatingOilDailyConsumption is not equal to 200", userOne.getHeatingOilDailyConsumption() == 200);
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
        Assert.assertTrue(userOne.getTotalCarbonSaved() == 50.55);
    }

    @Test
    public void getTotalCarbonSavedFail() {
        Assert.assertFalse(userOne.getTotalCarbonSaved() == 50.6);
    }

    @Test
    public void setTotalCarbonSavedSuccess() {
        userOne.setTotalCarbonSaved(60.1);
        Assert.assertTrue(userOne.getTotalCarbonSaved() == 60.1);
    }

    @Test
    public void getFriendRequestsSuccessNotEmpty() {
        userOne.newFriendRequest("testname");
        Assert.assertEquals(1, userOne.getFriendRequests().size());
        Assert.assertEquals("testname", userOne.getFriendRequests().get(0));
    }

    @Test
    public void testAddFriend() {
        userOne.addFriend("friend1");
        Assert.assertEquals(userOne.getFriends().get(0),"friend1");
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
        userOne.newFriendRequest("testfriend");
        Assert.assertEquals(1, userOne.getFriendRequests().size());
        Assert.assertEquals("testfriend", userOne.getFriendRequests().get(0));
    }

    @Test
    public void testDeleteFriendRequest() {
        userOne.newFriendRequest("testfriend");
        userOne.newFriendRequest("testfriend2");
        Assert.assertEquals("testfriend2", userOne.getFriendRequests().get(1));
        userOne.deleteFriendRequest("testfriend");
        Assert.assertEquals("testfriend2", userOne.getFriendRequests().get(0));
    }


}