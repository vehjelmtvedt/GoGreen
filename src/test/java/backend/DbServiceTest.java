package backend;

import data.Achievement;
import data.BuyLocallyProducedFood;
import data.EatVegetarianMeal;
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


import java.util.*;
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
            regexTestUsers.add(regexUser);
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
        User test = new User("a","a",1,"a@A.com","a","abc");
        dbService.addUser(test);
        assertEquals(test.getUsername(),dbService.grantAccess(test.getEmail(), "abc").getUsername());
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
        Assert.assertEquals(true,dbService.getUser(testUser2.getEmail()).getFriends().contains("test_userFr"));
        Assert.assertEquals(true,dbService.getUser(testUser3.getEmail()).getFriends().contains("test_userF"));
        Assert.assertEquals(0, dbService.getUser(testUser3.getEmail()).getFriendRequests().size());
        dbService.deleteUser(testUser2.getEmail());
        dbService.deleteUser(testUser3.getEmail());
    }

    @Test
    public void testBefriendUsersBothNull() {
        assertEquals(false, dbService.acceptFriendRequest(null, null));
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
        assertEquals(false, dbService.addFriendRequest(null, null));
    }

    @Test
    public void friendRequestEachOther() {
        dbService.addFriendRequest(testUser.getUsername(),testUser2.getUsername());
        dbService.addFriendRequest(testUser2.getUsername(),testUser.getUsername());
        User testUserF = dbService.getUser(testUser.getEmail());
        assertEquals(testUserF.getFriends().contains(testUser2.getUsername()),true);
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
        assertEquals(false, dbService.rejectFriendRequest(null, null));
    }

    @Test
    public void testGetAchievements() {
        List<Achievement> achievements = dbService.getAchievements();
        assertNotEquals(0, achievements.size());
    }

    private List<Double> getTopUserScores(List<User> topUsers) {
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
        assertEquals(getExpectedScores(10), getTopUserScores(dbService.getTopUsers(10)));
    }

    @Test
    public void testGetTop5UserScores() {
        assertEquals(getExpectedScores(5), getTopUserScores(dbService.getTopUsers(5)));
    }

    @Test
    public void testGetTopUserScore() {
        assertEquals(getExpectedScores(1), getTopUserScores(dbService.getTopUsers(1)));
    }

    @Test
    public void testGetNoUserTop() {
        assertEquals(new ArrayList<Double>(), getExpectedScores(0));
    }

    @Test
    public void testEditProfile() {
        assertEquals(dbService.editProfile(testUser,"age",15).getAge(),15);
    }

    @Test
    public void testEditProfileWrongField() {
        assertEquals(null,dbService.editProfile(testUser,"asd",10));
    }

    @Test
    public void testEditProfilePassword() { assertEquals(24,dbService.editProfile(testUser,"password","abc").getAge());}

    @Test
    public void testGetTopFriendsEmpty() {
        List<User> friends = dbService.getTopFriends(testUser.getUsername(), 5);

        Assert.assertEquals(0, friends.size());
    }

    @Test
    public void testGetTopFriends() {
        // Add all RegexTestUsers (expect 1st) as friends to 1st User
        for (int i = 1; i < regexTestUsers.size(); ++i) {
            regexTestUsers.get(0).addFriend(regexTestUsers.get(i).getUsername());
        }

        // Update user in database
        dbService.addUser(regexTestUsers.get(0));

        // Get top friends of User
        List<User> friends = dbService.getTopFriends(regexTestUsers.get(0).getUsername(), 5);
        // Get total CO2 saved of friends
        List<Double> result = getTopUserScores(friends);
        // Get expected scores (note that since the first user has a relatively low CO2 saved,
        // the user will not be included here)
        List<Double> expected = getExpectedScores(5);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetTopFriendsNoUser() {
        List<User> friends = dbService.getTopFriends(testUserNonExistent.getUsername(), 5);
        Assert.assertEquals(new ArrayList<User>(), friends);
    }

    @Test
    public void addAchievemnt() {

        ArrayList arrayList = new ArrayList();
        arrayList.add(0);

        dbService.addAchievement(testUser , arrayList , new Date(1,1,1));

        Assert.assertNotNull(testUser.getProgress().getAchievements().get(0));


    }

    @Test
    public void addAchievemntPoints() {

        ArrayList arrayList = new ArrayList();
        arrayList.add(0);

        dbService.addAchievement(testUser , arrayList , new Date(1,1,1));



        Assert.assertTrue(testUser.getProgress().getAchievements().size() == 1);


    }

    public void testGetRankNull() {
        Assert.assertEquals(-1, dbService.getUserRank(testUserNonExistent.getUsername()));
    }

    @Test
    public void testGetRankTop5() {
        User top5User = dbService.getTopUsers(15).get(4);
        int rank = dbService.getUserRank(top5User.getUsername());

        Assert.assertEquals(5, rank);
    }

    @Test
    public void testGetRankTop1() {
        User top1User = dbService.getTopUsers(15).get(0);
        int rank = dbService.getUserRank(top1User.getUsername());

        Assert.assertEquals(1, rank);
    }

    @Test
    public void testLoginStreakNull() {
        dbService.grantAccess(testUser.getEmail(),"ggg");
        dbService.grantAccess(testUser.getEmail(),"ggg");
        dbService.grantAccess(testUser.getEmail(),"ggg");
        dbService.grantAccess(testUser.getEmail(),"ggg");
        assertEquals(dbService.grantAccess(testUser.getEmail(),testUser.getPassword()),null);
    }

    @Test
    public void testAddActivityNull() {
        assertEquals(dbService.addActivityToUser(testUser.getUsername(),null),null);
    }

    @Test
    public void testAddActivityUserNull() {
        assertEquals(dbService.addActivityToUser(testUser.getEmail(),new BuyLocallyProducedFood()),null);
    }

    @Test
    public void testDeleteUserNonExistent() {
        dbService.deleteUser(testUserNonExistent.getEmail());
        assertEquals(dbService.getUser(testUserNonExistent.getEmail()),null);
    }

    @Test
    public void getUserRankNull() {
        assertEquals(dbService.getUserRank("asdfg"),-1);
    }

    @Test
    public void testCheckLeaderBoardsTop1() {
        User user = dbService.getTopUsers(1).get(0);
        ArrayList<Integer> result = dbService.checkLeaderboards(user);
        Assert.assertTrue(result.contains(10));
    }

    @Test
    public void testCheckLeaderBoardsTop2() {
        User user = dbService.getTopUsers(2).get(1);
        ArrayList<Integer> result = dbService.checkLeaderboards(user);
        Assert.assertTrue(result.contains(11));
    }

    @Test
    public void testCheckLeaderBoardsTop3() {
        User user = dbService.getTopUsers(3).get(2);
        ArrayList<Integer> result = dbService.checkLeaderboards(user);
        Assert.assertTrue(result.contains(18));
    }

    @Test
    public void testCheckLeaderBoardsTop5() {
        User user = dbService.getTopUsers(5).get(4);
        ArrayList<Integer> result = dbService.checkLeaderboards(user);
        Assert.assertTrue(result.contains(27));
    }

    @Test
    public void testCheckLeaderBoardsTop10() {
        User user = dbService.getTopUsers(10).get(9);
        ArrayList<Integer> result = dbService.checkLeaderboards(user);
        Assert.assertTrue(result.contains(26));
    }

    @Test
    public void testCheckLeaderBoardsNone() {
        User user = dbService.getTopUsers(11).get(10);
        ArrayList<Integer> result = dbService.checkLeaderboards(user);
        Assert.assertTrue(result.isEmpty());
    }
}