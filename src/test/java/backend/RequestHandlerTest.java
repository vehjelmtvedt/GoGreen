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
        assertEquals("email exists", requestHandler.signupController(testUser));
    }

    @Test
    public void testSignupDoesNotExist()
    {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(null);
        assertEquals("success", requestHandler.signupController(testUser));
    }

    @Test
    public void testLoginSuccess() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(true);
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void testLoginFail() {
        Mockito.when(dbService.grantAccess(testUser.getEmail(), testUser.getPassword())).thenReturn(false);
        assertEquals(null, requestHandler.loginController(new LoginDetails(testUser.getEmail(), testUser.getPassword())));
    }

    @Test
    public void signUpFailUsername() {
        Mockito.when(dbService.getUser(testUser.getUsername())).thenReturn(testUser);
        assertEquals("username exists",requestHandler.signupController(testUser));
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
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn("OK");
        assertEquals("OK", requestHandler.friendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testAddFriendRequestNotOK() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), "Invalid")).thenReturn("Invalid username");
        assertEquals("Invalid username", requestHandler.friendRequest(testUser.getUsername(), "Invalid"));
    }

    @Test
    public void testAcceptFriendRequestOK() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn("OK"); //test requests test2
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn("OK"); //test2 accepts test
        assertEquals("OK", requestHandler.acceptFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testAcceptFriendRequestInvalid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.acceptFriendRequest(testUser.getUsername(), "invalid")).thenReturn("Invalid username"); //test2 accepts test
        assertEquals("Invalid username", requestHandler.acceptFriendRequest(testUser.getUsername(), "invalid"));
    }

    @Test
    public void testRejectFriendRequestOK() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.addFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn("OK"); //test requests test2
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername())).thenReturn("OK"); //test2 rejects test
        assertEquals("OK", requestHandler.rejectFriendRequest(testUser.getUsername(), testUser2.getUsername()));
    }

    @Test
    public void testRejectFriendRequestInvalid() {
        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
        Mockito.when(dbService.rejectFriendRequest(testUser.getUsername(), "invalid")).thenReturn("Invalid username"); //test2 accepts test
        assertEquals("Invalid username", requestHandler.rejectFriendRequest(testUser.getUsername(), "invalid"));
    }
}