package backend.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    @Test
    public void testGetLastLoginDate() {
        // Test prone to failure on >1second executions. Consider using Mockito to test this.
        User newUser = new User("FirstName", "LastName", 25, "test@email.com", "test_user", "pwd123");
        Date dateNow = Calendar.getInstance().getTime();
        Assert.assertEquals(dateNow, newUser.getLastLoginDate());
    }

    @Test
    public void testSetLastLoginDate() {
        userOne.setLastLoginDate();
        Date dateNow = Calendar.getInstance().getTime();
        Assert.assertEquals(dateNow, userOne.getLastLoginDate());
    }
}