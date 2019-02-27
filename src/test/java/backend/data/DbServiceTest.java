package backend.data;

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


    // --- Declare new test uesrs for friend test functionality ---
    private final User testUser2 = new User("Friend", "User", 22, "testF@email.com", "test_userF", "pwd");
    private final User testUser3 = new User("Friended", "User", 21, "testFr@email.com", "test_userFr", "pwd");

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
    public void testBefriendUsersNull() {
        dbService.befriendUsers(testUser2.getEmail(), testUserNonExistent.getEmail());

        // --- Some assert here ---
    }

    @Test
    public void testBefriendUsers() {
        dbService.befriendUsers(testUser2.getEmail(), testUser3.getEmail());

        // --- Some assert here ---
    }

    @Test
    public void testAddFriendRequestNull() {
        dbService.addFriendRequest(testUser2.getEmail(), testUserNonExistent.getEmail());

        // --- Some assert here ---
    }

    @Test
    public void testAddFriendRequest() {
        dbService.addFriendRequest(testUser2.getEmail(), testUser3.getEmail());

        // --- Some assert here ---
    }

    @Test
    public void testRejectFriendRequestNull() {
        dbService.rejectFriendReqeuest(testUser2.getEmail(), testUserNonExistent.getEmail());

        // --- Some assert here ---
    }

    @Test
    public void testRejectFriendRequest() {
        dbService.rejectFriendReqeuest(testUser2.getEmail(), testUser3.getEmail());

        // --- Some assert here ---
    }
}