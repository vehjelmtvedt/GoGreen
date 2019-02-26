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

    private final User testUser = new User("Test", "User", 24, "test@email.com","dummy", "pwd");
    private final User testUserNonExistent = new User("This User", "Will Not Exist", 55,
            "non-exist@email.com","dummy", "pwd123");
    private final User testUserHasFriends = new User("Person", "With Friends", 42,
            "fperson@email.com","dummy", "pwd456");

    @Before
    public void setup() {
        dbService.addUser(testUser);

        testUserHasFriends.addFriend(testUser.getEmail());

        dbService.addUser(testUserHasFriends);
    }

    @Test
    public void getUserNull() {
        assertNull(dbService.getUser(testUserNonExistent.getEmail()));
    }

    @Test
    public void testAddUser() {
        assertNotNull(dbService.getUser(testUser.getEmail()));
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
}