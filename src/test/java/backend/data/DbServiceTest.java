package backend.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class DbServiceTest {
    @Autowired
    private DbService dbService;

    private final User testUser = new User("Test", "User", 24, "test@email.com","test_user", "pwd");
    private final User testUserNonExistent = new User("This User", "Will Not Exist", 55,
            "non-exist@email.com","test_user_non_exist", "pwd123");
    private final User testUserHasFriends = new User("Person", "With Friends", 42,
            "fperson@email.com","test_user_friends", "pwd456");


    // --- Declare new test users for friend test functionality ---
    private User testUser2 = new User("Friend", "User", 22, "testF@email.com", "test_userF", "pwd");
    private User testUser3 = new User("Friended", "User", 21, "testFr@email.com", "test_userFr", "pwd");

    @Before
    public void setup() {
        dbService.addUser(testUser);

        testUserHasFriends.addFriend(testUser.getEmail());

        dbService.addUser(testUserHasFriends);

        dbService.addUser(testUser2);
        dbService.addUser(testUser3);
    }

    @Test
    public void getUserNull() {
        assertNull(dbService.getUser(testUserNonExistent.getEmail()));
    }

    @Test
    public void testGetUser() {
        // User added in setup()
        assertNotNull(dbService.getUser(testUser.getEmail()));
    }

    @Test
    public void testGetUserByUsername() {
        // User added in setup()
        assertNotNull(dbService.getUserByUsername(testUser.getUsername()));
    }

    @Test
    public void testDeleteUser() {
        dbService.addUser(testUserNonExistent);
        dbService.deleteUser(testUserNonExistent.getEmail());
        assertNull(dbService.getUser(testUserNonExistent.getEmail()));
    }

    @Test
    public void testGrantAccessNull() {
        assertFalse(dbService.grantAccess(testUserNonExistent.getEmail(), testUserNonExistent.getPassword()));
    }

    @Test
    public void testAuthenticationGrant() {
        assertTrue(dbService.grantAccess(testUser.getEmail(), "pwd"));
    }

    @Test
    public void testAuthenticationReject() {
        assertFalse(dbService.grantAccess(testUser.getEmail(), "someRandomPWD"));
    }

    @Test
    public void testEncryption() {
        String oldPwd = testUserNonExistent.getPassword();
        dbService.addUser(testUserNonExistent);
        assertNotEquals(testUserNonExistent.getPassword(), oldPwd);

        // Delete user after the test, because this user shouldn't be in the db
        dbService.deleteUser(testUserNonExistent.getEmail());
    }

    @Test
    public void testFriendsNoFriends() {
        assertEquals(new ArrayList<User>(), dbService.getFriends(testUser.getEmail()));
    }

    @Test
    public void testFriendsNull() {
        assertEquals(new ArrayList<User>(), dbService.getFriends(testUserNonExistent.getEmail()));
    }

    @Test
    public void testFriends() {
        // Rewrite this test to be more helpful after User equals implementation
        assertEquals(1, dbService.getFriends(testUserHasFriends.getEmail()).size());
    }


    // TBD tests
    @Test
    public void testBefriendUsersNull1() {
        dbService.addUser(testUser2);
        dbService.acceptFriendRequest(testUser2.getUsername(), null);
        Assert.assertEquals(0, dbService.getUser(testUser2.getEmail()).getFriends().size());
        dbService.deleteUser(testUser2.getEmail());
    }

    @Test
    public void testBefriendUsersNull2() {
        dbService.addUser(testUser2);
        dbService.acceptFriendRequest(null, testUser2.getUsername());
        Assert.assertEquals(0, dbService.getUser(testUser2.getEmail()).getFriends().size());
        dbService.deleteUser(testUser2.getEmail());
    }

    @Test
    public void testBefriendUsers() {
        dbService.addUser(testUser2);
        dbService.addUser(testUser3);
        dbService.addFriendRequest(testUser2.getUsername(), testUser3.getUsername());
        Assert.assertEquals(1, dbService.getUser(testUser3.getEmail()).getFriendRequests().size());
        dbService.acceptFriendRequest(dbService.getUser(testUser2.getEmail()).getUsername(), dbService.getUser(testUser3.getEmail()).getUsername());
        Assert.assertEquals("test_userFr",dbService.getUser(testUser2.getEmail()).getFriends().get(0));
        Assert.assertEquals("test_userF",dbService.getUser(testUser3.getEmail()).getFriends().get(0));
        Assert.assertEquals(0, dbService.getUser(testUser3.getEmail()).getFriendRequests().size());
        dbService.deleteUser(testUser2.getEmail());
        dbService.deleteUser(testUser3.getEmail());
    }

    @Test
    public void testBefriendUsersBothNull() {
        assertEquals("Invalid username", dbService.acceptFriendRequest(null, null));
    }

    @Test
    public void testAddFriendRequestNull1() {
        dbService.addUser(testUser2);
        dbService.addFriendRequest(null, testUser2.getUsername()); //false, true
        Assert.assertEquals(0, dbService.getUser(testUser2.getEmail()).getFriendRequests().size());
        dbService.deleteUser(testUser2.getEmail());
    }

    @Test
    public void testAddFriendRequestNull2() {
        dbService.addUser(testUser2);
        dbService.addFriendRequest(testUser2.getUsername(), null); //true, false
        Assert.assertEquals(0, dbService.getUser(testUser2.getEmail()).getFriendRequests().size());
        dbService.deleteUser(testUser2.getEmail());
    }

    @Test
    public void testAddFriendRequest() {
        dbService.addUser(testUser2);
        dbService.addUser(testUser3);
        dbService.addFriendRequest(testUser2.getUsername(), testUser3.getUsername()); //true true
        Assert.assertEquals(1, dbService.getUser(testUser3.getEmail()).getFriendRequests().size());
        dbService.deleteUser(testUser2.getEmail());
        dbService.deleteUser(testUser3.getEmail());
    }

    @Test
    public void testAddFriendRequestBothNull() { //false, false
        assertEquals("Invalid username", dbService.addFriendRequest(null, null));
    }

    @Test
    public void testRejectFriendRequest() {
        dbService.addUser(testUser2);
        dbService.addUser(testUser3);
        dbService.addFriendRequest(testUser2.getUsername(), testUser3.getUsername());
        Assert.assertEquals(1, dbService.getUser(testUser3.getEmail()).getFriendRequests().size());
        dbService.rejectFriendRequest(dbService.getUser(testUser2.getEmail()).getUsername(), dbService.getUser(testUser3.getEmail()).getUsername());
        Assert.assertEquals(0, dbService.getUser(testUser3.getEmail()).getFriendRequests().size());
        dbService.deleteUser(testUser2.getEmail());
        dbService.deleteUser(testUser3.getEmail());
    }

    @Test
    public void testRejectFriendRequestNull() {
        dbService.addUser(testUser2);
        dbService.rejectFriendRequest(null, testUser2.getUsername());
        Assert.assertEquals(0, dbService.getUser(testUser2.getEmail()).getFriendRequests().size());
        dbService.deleteUser(testUser2.getEmail());
    }

    @Test
    public void testRejectFriendRequestNull2() {
        dbService.addUser(testUser2);
        dbService.rejectFriendRequest(testUser2.getUsername(), null);
        Assert.assertEquals(0, dbService.getUser(testUser2.getEmail()).getFriendRequests().size());
        dbService.deleteUser(testUser2.getEmail());
    }

    @Test
    public void testRejectFriendRequestBothNull() {
        assertEquals("Invalid username", dbService.rejectFriendRequest(null, null));
    }
}