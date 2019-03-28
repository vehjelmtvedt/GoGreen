package backend;

import data.Achievement;
import data.User;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class DbServiceTest {
    @Autowired
    private DbService dbService;

    private static final User testUser = new User("Test", "User", 24, "test@email.com","test_user", "pwd");
    private static final User testUserNonExistent = new User("This User", "Will Not Exist", 55,
            "non-exist@email.com","test_user_non_exist", "pwd123");
    private static final User testUserHasFriends = new User("Person", "With Friends", 42,
            "fperson@email.com","test_user_friends", "pwd456");


    // --- Declare new test uesrs for friend test functionality ---
    private static final User testUser2 = new User("Friend", "User", 22, "testF@email.com", "test_userF", "pwd");
    private static final User testUser3 = new User("Friended", "User", 21, "testFr@email.com", "test_userFr", "pwd");

    private static List<User> regexTestUsers = new ArrayList<User>();
    private static String[] regexTestUsernames = {"a_user", "abcdefg_user", "bcd_user", "b_user", "def", "powerUser",
            "casual_user", "123user456", "UsEr", "soomeone", "anyone", "abcdefghuser123ab", "idontknow", "i_am_user_566",
            "regular"};
    private static double[] CO2TestScores = {2500.0, 300.0, 543.0, 900.0, 125.0, 9990.0, 12532.0, 1255.0, 4532.0, 1000000.0,
        4321.0, 500.0, 1000000.0, 55555.0, 90043.0};

    @Before
    public void setup() {
        dbService.addUser(testUser);

        testUserHasFriends.addFriend(testUser.getUsername());

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
            regexUser.setTotalCarbonSaved(CO2TestScores[i]);
            dbService.addUser(regexUser);
        }
    }


    @Test
    public void getUserNull() {
        assertNull(dbService.getUser(testUserNonExistent.getEmail()));
    }

    @Test
    public void addUserExisting() {
        String password = testUser.getPassword();
        dbService.addUser(testUser);
        assertEquals(password, dbService.getUser(testUser.getEmail()).getPassword());
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
        assertEquals(null,dbService.grantAccess(testUserNonExistent.getEmail(), testUserNonExistent.getPassword()));
    }

    @Test
    public void testAuthenticationGrant() {
        assertEquals(testUser.getUsername(),dbService.grantAccess(testUser.getEmail(), "pwd").getUsername());
    }

    @Test
    public void testAuthenticationReject() {
        assertEquals(null,dbService.grantAccess(testUser.getEmail(), "someRandomPWD"));
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
        assertEquals(1, dbService.getFriends(testUserHasFriends.getUsername()).size());
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
        assertEquals(null, dbService.acceptFriendRequest(null, null));
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
        assertEquals(null, dbService.addFriendRequest(null, null));
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
        assertEquals(null, dbService.rejectFriendRequest(null, null));
    }

    @Test
    public void testGetAchievements() {
        List<Achievement> achievements = dbService.getAchievements();
        assertNotEquals(0, achievements.size());
    }

    private List<Double> getTopUserScores(int top) {
        List<User> topUsers = dbService.getTopUsers(top);

        return topUsers.stream() // Convert to User stream
                .map(User::getTotalCarbonSaved) // Map to totalCarbonSaved score
                .collect(Collectors.toList());
    }

    private List<Double> getExpectedScores(int top) {
        return Arrays.stream(CO2TestScores).boxed() // Convert CO2TestScores Array to Stream<Double>
                .sorted(Comparator.reverseOrder()) // Sort in DESC order
                .limit(top) // Take only 10 entries
                .collect(Collectors.toList()); // Collect to List
    }

    @Test
    public void testGetTop10UserScores() {
        assertEquals(getExpectedScores(10), getTopUserScores(10));
    }

    @Test
    public void testGetTop5UserScores() {
        assertEquals(getExpectedScores(5), getTopUserScores(5));
    }

    @Test
    public void testGetTopUserScore() {
        assertEquals(getExpectedScores(1), getTopUserScores(1));
    }

    @Test
    public void testGetNoUserTop() {
        assertEquals(new ArrayList<Double>(), getExpectedScores(0));
    }

    @Test
    public void testEditProfile() {
        assertEquals(dbService.editProfile(testUser,"age",15).getAge(),15);
    }
}