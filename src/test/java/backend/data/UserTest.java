package backend.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserTest {

    User userOne = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com", "password123");
    User empty = new User();

    @Test
    public void testConstructor() { Assert.assertNotNull(userOne);}

    @Test
    public void testEmptyConstructor() { Assert.assertNotNull(empty);}

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
    public void testSetPassword() {
        userOne.setPassword("pwd123");
        Assert.assertEquals("pwd123", userOne.getPassword());
    }

    @Test
    public void toStringSuccess() {
        userOne.addFriend("friend@email.com");
        Assert.assertEquals(userOne.toString(),"First name: Vetle\nLast name: " +
            "Hjelmtvedt\nAge: 19\nEmail: vetle@hjelmtvedt.com\nPassword: password123\nFriend emails: \n-friend@email.com\n" );
    }

    @Test
    public void getFriends() {Assert.assertEquals(new ArrayList<>(), userOne.getFriends());}

    @Test
    public void addFriend() {
        userOne.addFriend("vehjelmtvedt");
        Assert.assertEquals("vehjelmtvedt", userOne.getFriends().get(0));
    }


    @Test
    public void testGetFriendRequestsEmpty() {
        Assert.assertEquals(new ArrayList<>(), userOne.getFriendRequests());
    }

    @Test
    public void testAddOneFriendRequest() {
        userOne.newFriendRequest("vehjelmtvedt");
        Assert.assertEquals("vehjelmtvedt", userOne.getFriendRequests().get(0));
    }

    @Test
    public void testDeleteOneFriendRequest() {
        userOne.newFriendRequest("vehjelmtvedt");
        userOne.deleteFriendRequest("vehjelmtvedt");
        Assert.assertEquals(new ArrayList<>(), userOne.getFriendRequests());
    }



}