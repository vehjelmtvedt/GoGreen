package backend;


import data.Achievement;
import data.EatVegetarianMeal;
import data.LoginDetails;
import data.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
public class RequestHandlerTest
{
    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    RequestHandler requestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com","dummy", "pwd");
    private final User testUser2 = new User("Test2", "User2", 24, "test2@email.com","dummy2", "pwd2");


    @Test
    public void testSignupExists()
    {
        Mockito.when(dbService.getUserByEmail(testUser.getEmail())).thenReturn(testUser);
        assertEquals("Email exists", requestHandler.signupController(testUser));
    }

    @Test
    public void testSignupDoesNotExist()
    {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(null);
        assertEquals("success", requestHandler.signupController(testUser));
    }

    @Test
    public void testLoginSuccess() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testLoginFail() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(null);
        assertEquals(null, requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void signUpFailUsername() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertEquals("Username exists",requestHandler.signupController(testUser));
    }

    @Test
    public void testAddFriendRequestOK() {
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(true);
        assertEquals(true, requestHandler.friendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testAddFriendRequestNotOK() {
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), "Invalid")).thenReturn(false);
        assertEquals(false, requestHandler.friendRequest(testUser.getUsername(), "Invalid"));
    }

    @Test
    public void testAcceptFriendRequestOK() {
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(true); //test2 accepts test
        assertEquals(true, requestHandler.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testAcceptFriendRequestInvalid() {
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), "invalid")).thenReturn(false); //test2 accepts test
        assertEquals(false, requestHandler.acceptFriendRequest(testUser.getUsername(), "invalid"));
    }

    @Test
    public void testRejectFriendRequestOK() {
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(true); //test2 rejects test
        assertEquals(true, requestHandler.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testRejectFriendRequestInvalid() {
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), "invalid")).thenReturn(false); //test2 accepts test
        assertEquals(false, requestHandler.rejectFriendRequest(testUser.getUsername(), "invalid"));
    }

    @Test
    public void testValidateEmail() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals("OK", requestHandler.validateUser(testUser.getEmail()));
    }

    @Test
    public void testValidateUsername() {
        Mockito.when(dbService.getUser(testUser.getUsername())).thenReturn(testUser);
        assertEquals("OK", requestHandler.validateUser(testUser.getUsername()));
    }

    @Test
    public void testInvalidUsernameAndEmail() {
        assertEquals("NONE", requestHandler.validateUser("invalid"));
    }

    @Test
    public void testAddActivity() {
        EatVegetarianMeal act = new EatVegetarianMeal();
        Mockito.when(dbService.addActivityToUser(testUser.getUsername(), act)).thenReturn(testUser);
        assertEquals(testUser, requestHandler.addActivity(act, testUser.getUsername()));
    }

    @Test
    public void testAddActivityNotValidUsername() {
        EatVegetarianMeal act = new EatVegetarianMeal();
        assertEquals(null, requestHandler.addActivity(act, "invalid"));
    }

    @Test
    public void testAddActivityNotValidActivity() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertEquals(null, requestHandler.addActivity(null, testUser.getUsername()));
        assertEquals(0, dbService.getUserByUsername(testUser.getUsername()).getActivities().size());
    }

    @Test
    public void testUserSearch() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<String> testList = new ArrayList();
        testList.add(testUser.getUsername());
        Mockito.when(dbService.getMatchingUsers(testUser.getUsername())).thenReturn(testList);
        assertEquals(testList,requestHandler.userSearch(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),testUser.getUsername()));

    }

    @Test
    public void testUserSearchNull() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(null);
        assertEquals(null,requestHandler.userSearch(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),testUser.getUsername()));
    }

    @Test
    public void retrieveTopUsers() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<User> testList = new ArrayList();
        testList.add(testUser);
        Mockito.when(dbService.getTopUsers(1)).thenReturn(testList);
        assertEquals(testList,requestHandler.getTopUsers(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),1));
    }

    @Test
    public void retrieveTopUsersNull() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(null);
        assertEquals(null,requestHandler.getTopUsers(new LoginDetails(testUser.getUsername(),
                testUser.getPassword()),1));
    }

    @Test
    public void getFriends() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<User> testList = new ArrayList();
        testList.add(testUser);
        Mockito.when(dbService.getFriends(testUser.getUsername())).thenReturn(testList);
        assertEquals(testList,requestHandler.getFriends(new LoginDetails(testUser.getUsername(),
                testUser.getPassword())));
    }

    @Test
    public void getFriendsNull() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(null);
        assertEquals(null,requestHandler.getFriends(new LoginDetails(testUser.getUsername(),
                testUser.getPassword())));
    }

    @Test
    public void getAllAchievements() {
        List<Achievement> testList = new ArrayList();
        testList.add(new Achievement());
        Mockito.when(dbService.getAchievements()).thenReturn(testList);
        assertEquals(testList, requestHandler.getAllAchievements());
    }

    @Test
    public void editProfileInt() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.editProfile(testUser,"age",24)).thenReturn(testUser);
        assertEquals(24,requestHandler.editProfile(new LoginDetails(testUser.getUsername()
                ,testUser.getPassword()),"age","24","Integer").getAge());
    }
    @Test
    public void editProfileDouble() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.editProfile(testUser,"totalCarbonSaved",0.0)).thenReturn(testUser);
        assertEquals(0.0,requestHandler.editProfile(new LoginDetails(testUser.getUsername()
                ,testUser.getPassword()),"totalCarbonSaved","0.0","Double").getTotalCarbonSaved());
    }
    @Test
    public void editProfileAuthFail() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(null);
        assertEquals(null,requestHandler.editProfile(new LoginDetails(testUser.getUsername()
                ,testUser.getPassword()),"firstName","Test5","String"));
    }
    
    @Test
    public void forgotPass() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        testUser.setSecurityQuestionAnswer("A");
        testUser.setSecurityQuestionId(1);
        Boolean bool = true;
        assertEquals(requestHandler.forgotPass(testUser.getEmail(),"A",1,"ASD"),bool);
    }

    @Test
    public void forgotPassNull() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(null);
        assertEquals(null,requestHandler.forgotPass(testUser.getEmail(),"A",1,"A"));
    }

    @Test
    public void forgotPassWrongAnswer() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        testUser.setSecurityQuestionAnswer("A");
        testUser.setSecurityQuestionId(1);
        Boolean bool = false;
        assertEquals(requestHandler.forgotPass(testUser.getEmail(),"B",1,"ASD"),bool);
    }

    @Test
    public void getTotalCO2Saved() {
        Mockito.when(dbService.getTotalCO2Saved()).thenReturn(100.5);
        assertEquals(requestHandler.getTotalCO2Saved(),100.5);
    }

    @Test
    public void getTotalUsers() {
        Mockito.when(dbService.getTotalUsers()).thenReturn(10);
        assertEquals(requestHandler.getTotalUsers(),10);
    }

    @Test
    public void getAverageCO2Saved() {
        Mockito.when(dbService.getAverageCO2Saved()).thenReturn(25.2);
        assertEquals(requestHandler.getAverageCO2Saved(),25.2);
    }

    @Test
    public void getRank() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(),testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.getUserRank(testUser.getEmail())).thenReturn(5);
        Integer x = 5;
        assertEquals(requestHandler.getRank(new LoginDetails(testUser.getEmail(),testUser.getPassword())),x);
    }

    @Test
    public void getRankFail() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(),testUser.getPassword())).thenReturn(null);
        assertEquals(requestHandler.getRank(new LoginDetails(testUser.getEmail(),testUser.getPassword())),null);
    }

    @Test
    public void testSecurityQuestionFalse() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        testUser.setSecurityQuestionId(1);
        testUser.setSecurityQuestionAnswer("F");
        Boolean b = false;
        assertEquals(requestHandler.forgotPass(testUser.getEmail(),"A",2,"ABC"),b);
    }
}