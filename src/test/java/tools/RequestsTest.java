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
        assertEquals("success", Requests.instance.signupRequest(testUser));
    }

    @Test
    public void testSignupRequestUsernameExists() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertEquals("Username exists", Requests.instance.signupRequest(testUser));
    }

    @Test
    public void testSignupRequestEmailExists() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(null);
        Mockito.when(dbService.getUserByEmail(testUser.getEmail())).thenReturn(testUser);
        assertEquals("Email exists", Requests.instance.signupRequest(testUser));
    }

    @Test
    public void loginRequest() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, Requests.instance.loginRequest(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void loginRequestFail() {
        assertEquals(null, Requests.instance.loginRequest(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testSendFriendRequestValid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.instance.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testSendFriendRequestInvalid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.addFriendRequest("invalid", testUser2.getUsername())).thenReturn(null);
        assertEquals(null, Requests.instance.sendFriendRequest("invalid", testUser2.getUsername()));
    }

    @Test
    public void testAcceptFriendRequest() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.instance.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.instance.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testRejectFriendRequest() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.instance.sendFriendRequest(testUser.getUsername(), testUser2.getUsername()));
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, Requests.instance.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testValidateUserRequestEmail() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertTrue(Requests.instance.validateUserRequest(testUser.getEmail()));
    }

    @Test
    public void testValidateUserRequestUsername() {
        Mockito.when(dbService.getUser(testUser.getUsername())).thenReturn(testUser);
        assertTrue(Requests.instance.validateUserRequest(testUser.getUsername()));
    }

    @Test
    public void testValidateUserRequestInvalid() {
        assertFalse(Requests.instance.validateUserRequest("Invalid"));
    }

    @Test
    public void testAddActivityRequest() {
        EatVegetarianMeal activity = new EatVegetarianMeal();

        Mockito.when(dbService.addActivityToUser(testUser.getUsername(), activity)).thenReturn(testUser);

        assertEquals(testUser, Requests.instance.addActivityRequest(activity, testUser.getUsername()));
    }

    @Test
    public void testAddActivityRequestInvalidUser() {
        EatVegetarianMeal activity = new EatVegetarianMeal();
        assertEquals(null, Requests.instance.addActivityRequest(activity, "invalid"));
    }

    @Test
    public void testGetFriendsRequest() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<User> testList = new ArrayList();
        testList.add(testUser);
        Mockito.when(dbService.getFriends(testUser.getUsername())).thenReturn(testList);
        assertEquals(testList, Requests.instance.getFriends(new LoginDetails(testUser.getUsername(),
                testUser.getPassword())));
        assertEquals(1, Requests.instance.getFriends(new LoginDetails(testUser.getUsername(), testUser.getPassword())).size());
    }

    @Test
    public void testGetMatchingUsersRequest() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<String> testList = new ArrayList();
        testList.add(testUser.getUsername());
        Mockito.when(dbService.getMatchingUsers(testUser.getUsername())).thenReturn(testList);
        assertEquals(testList,Requests.instance.getMatchingUsersRequest(testUser.getUsername(), new LoginDetails(testUser.getUsername(),
                testUser.getPassword())));
    }

    @Test
    public void testGetAllAchievements() {
        List<Achievement> testList = new ArrayList();
        testList.add(new Achievement());
        Mockito.when(dbService.getAchievements()).thenReturn(testList);
        assertEquals(testList.get(0).getId(),Requests.instance.getAllAchievements().get(0).getId());
    }

    @Test
    public void testGetTopUsers() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        List<User> testList = new ArrayList<>();
        testList.add(testUser);
        Mockito.when(dbService.getTopUsers(1)).thenReturn(testList);
        assertEquals(testList,Requests.instance.getTopUsers(new LoginDetails(testUser.getUsername(),testUser.getPassword()),1));
    }

    @Test
    public void testEditProfile() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.editProfile(testUser,"firstName","test")).thenReturn(testUser);
        assertEquals("Test",Requests.instance.editProfile(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),"firstName","test").getFirstName());
    }
    
    @Test
    public void testforgotPass() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(), testUser.getPassword())).thenReturn(null);
        Boolean bool = false;
        assertEquals(null,Requests.instance.forgotPass(testUser.getEmail(),1,"A","B"));
    }

    @Test
    public void getTotalUsers() {
        Mockito.when(dbService.getTotalUsers()).thenReturn(10);
        assertEquals(10,Requests.getTotalUsers());
    }

    @Test
    public void getAverageCO2Saved() {
        Mockito.when(dbService.getAverageCO2Saved()).thenReturn(10.5);
        assertEquals(10.5,Requests.getAverageCO2Saved());
    }

    @Test
    public void getTotalCO2Saved() {
        Mockito.when(dbService.getTotalCO2Saved()).thenReturn(100.5);
        assertEquals(100.5,Requests.getTotalCO2Saved());
    }


}
