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
import java.util.List;

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

    private List<User> regexTestUsers = new ArrayList<User>();
    private String[] regexTestUsernames = {"a_user", "abcdefg_user", "bcd_user", "b_user", "def", "powerUser",
            "casual_user", "123user456", "UsEr", "soomeone", "anyone", "abcdefghuser123ab", "idontknow", "i_am_user_566",
            "regular"};

    @Before
    public void setup() {
        dbService.addUser(testUser);

        testUserHasFriends.addFriend(testUser.getEmail());

        dbService.addUser(testUserHasFriends);

        dbService.addUser(testUser2);
        dbService.addUser(testUser3);
    }

    @Before
    public void setupRegexUsers() {
        String emailFormat = "regexTest%d@email.com";

        for (int i = 0; i < regexTestUsernames.length; ++i) {
            String email = String.format(emailFormat, i);
            User regexUser = new User("Regex", "Test", 20, email, regexTestUsernames[i], "pwd");
            dbService.addUser(regexUser);
        }
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

    @Test
    public void testRegexNoMatch() {
        List<String> result = dbService.getMatchingUsers("random-pattern-not-exist");

        assertEquals(0, result.size());
    }

    private List<String> returnExpectedRegex(String username) {
        List<String> matching = new ArrayList<String>();
        List<User> users = dbService.getAllUsers();

        for (User u : users) {
            if (u.getUsername().toLowerCase().contains(username)) {
                matching.add(u.getUsername());
            }
        }

        return matching;
    }

    @Test
    public void testRegexMatch1() {
        List<String> result = dbService.getMatchingUsers("user");
        List<String> expected = returnExpectedRegex("user");

        assertEquals(expected, result);
    }

    @Test
    public void testRegexMatch2() {
        List<String> result = dbService.getMatchingUsers("def");
        List<String> expected = returnExpectedRegex("def");

        assertEquals(expected, result);
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