package backend;


import backend.data.DbService;
import backend.data.LoginDetails;
import backend.data.User;
import ch.qos.logback.core.net.AbstractSSLSocketAppender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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

//    @Test
//    public void InvalidFriendrequest() {
//        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
//        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
//        assertEquals("Not a valid username", requestHandler.friendRequest(testUser.getEmail(), "dummy"));
//    }
//
//    @Test
//    public void ValidFriendRequest() {
//        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
//        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
//        assertEquals("OK", requestHandler.friendRequest(testUser.getEmail(), testUser2.getEmail()));
//    }
//
//    @Test
//    public void getFriendRequests()  {
//        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
//        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
//        requestHandler.friendRequest(testUser.getEmail(), testUser2.getEmail());
//        Assert.assertEquals(1, requestHandler.getAllFriendRequests(testUser2.getEmail()).size());
//    }

//    @Test
//    public void acceptFriendRequest() {
//        Mockito.when(dbService.getUser(testUser.getEmail())).thenReturn(testUser);
//        Mockito.when(dbService.getUser(testUser2.getEmail())).thenReturn(testUser2);
//        requestHandler.friendRequest(testUser.getEmail(), testUser2.getEmail());
//        Assert.assertEquals(1, requestHandler.getAllFriendRequests(testUser2.getEmail()).size());
//        Assert.assertEquals("OK", requestHandler.acceptFriend(testUser2.getEmail(), testUser.getEmail()));
//        Assert.assertEquals(0, testUser2.getFriendRequests().size());
//        Assert.assertEquals(1, testUser.getFriends().size());
//        Assert.assertEquals(1, testUser2.getFriends().size());
//
//    }



}