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
public class UserRequestHandlerTest
{
    @MockBean
    private DbService dbService;

    @InjectMocks
    @Resource
    UserRequestHandler userRequestHandler;

    private final User testUser = new User("Test", "User", 24, "test@email.com","dummy", "pwd");
    private final User testUser2 = new User("Test2", "User2", 24, "test2@email.com","dummy2", "pwd2");


    @Test
    public void testSignupExists()
    {
        Mockito.when(dbService.getUserByEmail(testUser.getEmail())).thenReturn(testUser);
        assertEquals("Email exists", userRequestHandler.signupController(testUser));
    }

    @Test
    public void testSignupDoesNotExist()
    {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(null);
        assertEquals("success", userRequestHandler.signupController(testUser));
    }

    @Test
    public void testLoginSuccess() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, userRequestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testLoginFail() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(null);
        assertEquals(null, userRequestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void signUpFailUsername() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertEquals("Username exists", userRequestHandler.signupController(testUser));
    }

    @Test
    public void testAddFriendRequestOK() {
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(true);
        assertEquals(true, userRequestHandler.friendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testAddFriendRequestNotOK() {
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), "Invalid")).thenReturn(false);
        assertEquals(false, userRequestHandler.friendRequest(testUser.getUsername(), "Invalid"));
    }

    @Test
    public void testAcceptFriendRequestOK() {
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(true); //test2 accepts test
        assertEquals(true, userRequestHandler.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testAcceptFriendRequestInvalid() {
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), "invalid")).thenReturn(false); //test2 accepts test
        assertEquals(false, userRequestHandler.acceptFriendRequest(testUser.getUsername(), "invalid"));
    }

    @Test
    public void testRejectFriendRequestOK() {
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(true); //test2 rejects test
        assertEquals(true, userRequestHandler.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testRejectFriendRequestInvalid() {
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), "invalid")).thenReturn(false); //test2 accepts test
        assertEquals(false, userRequestHandler.rejectFriendRequest(testUser.getUsername(), "invalid"));
    }

    @Test
    public void testValidateEmail() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals("OK", userRequestHandler.validateUser(testUser.getEmail()));
    }

    @Test
    public void testValidateUsername() {
        Mockito.when(dbService.getUser(testUser.getUsername())).thenReturn(testUser);
        assertEquals("OK", userRequestHandler.validateUser(testUser.getUsername()));
    }

    @Test
    public void testInvalidUsernameAndEmail() {
        assertEquals("NONE", userRequestHandler.validateUser("invalid"));
    }

    @Test
    public void testAddActivity() {
        EatVegetarianMeal act = new EatVegetarianMeal();
        Mockito.when(dbService.addActivityToUser(testUser.getUsername(), act)).thenReturn(testUser);
        assertEquals(testUser, userRequestHandler.addActivity(act, testUser.getUsername()));
    }

    @Test
    public void testAddActivityNotValidUsername() {
        EatVegetarianMeal act = new EatVegetarianMeal();
        assertEquals(null, userRequestHandler.addActivity(act, "invalid"));
    }

    @Test
    public void testAddActivityNotValidActivity() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertEquals(null, userRequestHandler.addActivity(null, testUser.getUsername()));
        assertEquals(0, dbService.getUserByUsername(testUser.getUsername()).getActivities().size());
    }

    @Test
    public void getFriends() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        List<User> testList = new ArrayList();
        testList.add(testUser);
        Mockito.when(dbService.getFriends(testUser.getUsername())).thenReturn(testList);
        assertEquals(testList, userRequestHandler.getFriends(new LoginDetails(testUser.getUsername(),
                testUser.getPassword())));
    }

    @Test
    public void getFriendsNull() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(null);
        assertEquals(null, userRequestHandler.getFriends(new LoginDetails(testUser.getUsername(),
                testUser.getPassword())));
    }

    @Test
    public void editProfileInt() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.editProfile(testUser,"age",24)).thenReturn(testUser);
        assertEquals(24, userRequestHandler.editProfile(new LoginDetails(testUser.getUsername()
                ,testUser.getPassword()),"age","24","Integer").getAge());
    }
    @Test
    public void editProfileDouble() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.editProfile(testUser,"totalCarbonSaved",0.0)).thenReturn(testUser);
        assertEquals(0.0, userRequestHandler.editProfile(new LoginDetails(testUser.getUsername()
                ,testUser.getPassword()),"totalCarbonSaved","0.0","Double").getTotalCarbonSaved());
    }
    @Test
    public void editProfileAuthFail() {
        Mockito.when(dbService.grantAccess(testUser.getUsername(),testUser.getPassword())).thenReturn(null);
        assertEquals(null, userRequestHandler.editProfile(new LoginDetails(testUser.getUsername()
                ,testUser.getPassword()),"firstName","Test5","String"));
    }
    
    @Test
    public void forgotPass() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        testUser.setSecurityQuestionAnswer("A");
        testUser.setSecurityQuestionId(1);
        Boolean bool = true;
        assertEquals(userRequestHandler.forgotPass(testUser.getEmail(),"A",1,"ASD"),bool);
    }

    @Test
    public void forgotPassWrongAnswer() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        testUser.setSecurityQuestionAnswer("A");
        testUser.setSecurityQuestionId(1);
        dbService.addUser(testUser);
        Boolean bool = false;
        assertEquals(userRequestHandler.forgotPass(testUser.getEmail(), "B", 1, "ASD"), bool);
    }

    @Test
    public void forgotPassWrongId() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        testUser.setSecurityQuestionAnswer("A");
        testUser.setSecurityQuestionId(2);
        dbService.addUser(testUser);
        Boolean bool = false;
        assertEquals(userRequestHandler.forgotPass(testUser.getEmail(), "A", 1, "ASD"), bool);
    }

    @Test
    public void forgotPassNull() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(null);
        assertEquals(null, userRequestHandler.forgotPass(testUser.getEmail(),"A",1,"A"));
    }

    @Test
    public void getRank() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(),testUser.getPassword())).thenReturn(testUser);
        Mockito.when(dbService.getUserRank(testUser.getEmail())).thenReturn(5);
        Integer x = 5;
        assertEquals(userRequestHandler.getRank(new LoginDetails(testUser.getEmail(),testUser.getPassword())),x);
    }

    @Test
    public void getRankFail() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(),testUser.getPassword())).thenReturn(null);
        assertEquals(userRequestHandler.getRank(new LoginDetails(testUser.getEmail(),testUser.getPassword())),null);
    }

    @Test
    public void testSecurityQuestionFalse() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        testUser.setSecurityQuestionId(1);
        testUser.setSecurityQuestionAnswer("F");
        Boolean b = false;
        assertEquals(userRequestHandler.forgotPass(testUser.getEmail(),"A",2,"ABC"),b);
    }
}