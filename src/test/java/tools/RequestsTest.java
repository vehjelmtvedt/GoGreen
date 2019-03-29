package tools;


import backend.RequestHandler;
import backend.Server;
import backend.DbService;
import data.Achievement;
import data.EatVegetarianMeal;
import data.LoginDetails;
import data.User;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import tools.Requests;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Server.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class RequestsTest {

    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    RequestHandler requestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com","dummy", "pwd");
    private final User testUser2 = new User("Test2", "User2", 24, "test2@email.com","dummy2", "pwd2");
    private User testUser3 = new User("Test3", "User3", 24, "test3@email.com","dummy3", "pwd3");

    @Before
    public void setup() {
        dbService.addUser(testUser);
        testUser3.addFriend(testUser.getUsername());
        dbService.addUser(testUser2);
        dbService.addUser(testUser3);
    }



    @Test
    public void testSignupRequest(){
        assertEquals("success", Requests.signupRequest(testUser));
    }

    @Test
    public void testSignupRequestUsernameExists() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertEquals("Username exists", Requests.signupRequest(testUser));
    }

    @Test
    public void testSignupRequestEmailExists() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(null);
        Mockito.when(dbService.getUserByEmail(testUser.getEmail())).thenReturn(testUser);
        assertEquals("Email exists", Requests.signupRequest(testUser));
    }

    @Test
    public void loginRequest() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, Requests.loginRequest(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void loginRequestFail() {
        assertEquals(null, Requests.loginRequest(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testSendFriendRequestValid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testSendFriendRequestInvalid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.addFriendRequest("invalid", testUser2.getUsername())).thenReturn(null);
        assertEquals(null, Requests.sendFriendRequest("invalid", testUser2.getUsername()));
    }

    @Test
    public void testAcceptFriendRequest() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testRejectFriendRequest() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testValidateUserRequestEmail() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertTrue(Requests.validateUserRequest(testUser.getEmail()));
    }

    @Test
    public void testValidateUserRequestUsername() {
        Mockito.when(dbService.getUser(testUser.getUsername())).thenReturn(testUser);
        assertTrue(Requests.validateUserRequest(testUser.getUsername()));
    }

    @Test
    public void testValidateUserRequestInvalid() {
        assertFalse(Requests.validateUserRequest("Invalid"));
    }

    @Test
    public void testAddActivityRequest() {
        EatVegetarianMeal activity = new EatVegetarianMeal();

        Mockito.when(dbService.addActivityToUser(testUser.getUsername(), activity)).thenReturn(testUser);

        assertEquals(testUser, Requests.addActivityRequest(activity, testUser.getUsername()));
    }

    @Test
    public void testAddActivityRequestInvalidUser() {
        EatVegetarianMeal activity = new EatVegetarianMeal();
        assertEquals(null, Requests.addActivityRequest(activity, "invalid"));
    }

    @Test
    public void testGetFriendsRequest() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<User> testList = new ArrayList();
        testList.add(testUser);
        Mockito.when(dbService.getFriends(testUser.getUsername())).thenReturn(testList);
        assertEquals(testList, Requests.getFriends(new LoginDetails(testUser.getUsername(),
                testUser.getPassword())));
        assertEquals(1, Requests.getFriends(new LoginDetails(testUser.getUsername(), testUser.getPassword())).size());
    }

    @Test
    public void testGetMatchingUsersRequest() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<String> testList = new ArrayList();
        testList.add(testUser.getUsername());
        Mockito.when(dbService.getMatchingUsers(testUser.getUsername())).thenReturn(testList);
        assertEquals(testList,Requests.getMatchingUsersRequest(testUser.getUsername(), new LoginDetails(testUser.getUsername(),
                testUser.getPassword())));
    }

    @Test
    public void testGetAllAchievements() {
        List<Achievement> testList = new ArrayList();
        testList.add(new Achievement());
        Mockito.when(dbService.getAchievements()).thenReturn(testList);
        assertEquals(testList.get(0).getId(),Requests.getAllAchievements().get(0).getId());
    }

    @Test
    public void testGetTopUsers() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        List<User> testList = new ArrayList<>();
        testList.add(testUser);
        Mockito.when(dbService.getTopUsers(1)).thenReturn(testList);
        assertEquals(testList,Requests.getTopUsers(new LoginDetails(testUser.getUsername(),testUser.getPassword()),1));
    }

    @Test
    public void testforgotPass() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(), testUser.getPassword())).thenReturn(null);
        Boolean bool = false;
        assertEquals(null,Requests.forgotPass(testUser.getEmail(),1,"A","B"));
    }
}
