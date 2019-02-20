package Backend.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public class DBServiceTest {
    @Autowired
    DBService dbService;

    User testUser = new User("Test", "User", 24, "test@email.com", "pwd");
    User testUserNonExistant = new User("This User", "Will Not Exist", 55,
            "non-exist@email.com", "pwd123");
    User testUserHasFriends = new User("Person", "With Friends", 42,
            "fperson@email.com", "pwd456");

    @Before
    public void setup()
    {
        dbService.addUser(testUser);

        testUserHasFriends.addFriend(testUser.getEmail());

        dbService.addUser(testUserHasFriends);
    }

    @Test
    public void getUserNull()
    {
        assertNull(dbService.getUser(testUserNonExistant.getEmail()));
    }

    @Test
    public void testAddUser()
    {
        assertNotNull(dbService.getUser(testUser.getEmail()));
    }

    @Test
    public void testDeleteUser()
    {
        dbService.addUser(testUserNonExistant);
        dbService.deleteUser(testUserNonExistant.getEmail());
        assertNull(dbService.getUser(testUserNonExistant.getEmail()));
    }

    @Test
    public void testGrantAccessNull()
    {
        assertFalse(dbService.grantAccess(testUserNonExistant.getEmail(), testUserNonExistant.getPassword()));
    }

    @Test
    public void testAuthenticationGrant()
    {
        assertTrue(dbService.grantAccess(testUser.getEmail(), "pwd"));
    }

    @Test
    public void testAuthenticationReject()
    {
        assertFalse(dbService.grantAccess(testUser.getEmail(), "someRandomPWD"));
    }

    @Test
    public void testEncryption()
    {
        String oldPwd = testUserNonExistant.getPassword();
        dbService.addUser(testUserNonExistant);
        assertNotEquals(testUserNonExistant.getPassword(), oldPwd);

        // Delete user after the test, because this user shouldn't be in the db
        dbService.deleteUser(testUserNonExistant.getEmail());
    }

    @Test
    public void testFriendsNoFriends()
    {
        assertEquals(new ArrayList<User>(), dbService.getFriends(testUser.getEmail()));
    }

    @Test
    public void testFriendsNull()
    {
        assertEquals(new ArrayList<User>(), dbService.getFriends(testUserNonExistant.getEmail()));
    }

    @Test
    public void testFriends()
    {
        // Rewrite this test to be more helpful after User equals implementation
        assertEquals(1, dbService.getFriends(testUserHasFriends.getEmail()).size());
    }
}