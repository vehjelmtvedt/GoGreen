package backend;


import backend.data.DbService;
import backend.data.LoginDetails;
import backend.data.User;
import ch.qos.logback.core.net.AbstractSSLSocketAppender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
        Mockito.when(dbService.getUser(testUser.getUsername())).thenReturn(null);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
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
    public void testgetUser() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, requestHandler.getUser(testUser.getEmail()));
    }

    @Test
    public void testAddFriendRequestOK() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2);
        assertEquals(testUser2, requestHandler.friendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testAddFriendRequestNotOK() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), "Invalid")).thenReturn(null);
        assertEquals(null, requestHandler.friendRequest(testUser.getUsername(), "Invalid"));
    }

    @Test
    public void testAcceptFriendRequestOK() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2); //test requests test2
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2); //test2 accepts test
        assertEquals(testUser2, requestHandler.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testAcceptFriendRequestInvalid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), "invalid")).thenReturn(null); //test2 accepts test
        assertEquals(null, requestHandler.acceptFriendRequest(testUser.getUsername(), "invalid"));
    }

    @Test
    public void testRejectFriendRequestOK() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2); //test requests test2
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn(testUser2); //test2 rejects test
        assertEquals(testUser2, requestHandler.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testRejectFriendRequestInvalid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), "invalid")).thenReturn(null); //test2 accepts test
        assertEquals(null, requestHandler.rejectFriendRequest(testUser.getUsername(), "invalid"));
    }

    @Test
    public void testValidateEmail() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals("OK", requestHandler.validateUser(testUser.getEmail()));
    }

    @Test
    public void testValidateUsername() {
        Mockito.when(dbService.getUserByUsername(testUser.getUsername())).thenReturn(testUser);
        assertEquals("OK", requestHandler.validateUser(testUser.getUsername()));
    }

    @Test
    public void testInvalidUsernameAndEmail() {
        assertEquals("NONE", requestHandler.validateUser("invalid"));
    }

}